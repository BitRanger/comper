/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.wangk.comper.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.wangk.comper.context.config.InputStreamCallback;
import org.wangk.comper.context.config.InputStreamProvider;
import org.wangk.comper.context.config.InputStreamSupport;
import org.wangk.comper.dao.DAOChapter;
import org.wangk.comper.dao.DAOPaper;
import org.wangk.comper.dao.DAOQuestion;
import org.wangk.comper.dao.QuestionService;
import org.wangk.comper.db.jdbc.JdbcAux;
import org.wangk.comper.feature.TrainingField;

import com.mchange.v2.c3p0.jboss.C3P0PooledDataSource;


/**
 * 
 * life circle management
 * 
 * @author BowenCai
 *
 */
public final class AppContext {
	
//	public static final boolean	DEBUG_FLAG = true;
	
//-----------------------------------------------------------------------------
//			global properties
	
	public static class defaults {
		
		public static Locale locale = Locale.US;
		public static TimeZone timeZone = TimeZone.getTimeZone("GMT");
		
		static Calendar calendar = Calendar.getInstance(timeZone);
	}

	public static final ThreadLocal<Calendar> LOCAL_CALENDAR = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			return Calendar.getInstance(defaults.timeZone);
		}
	};
	
	// StadardCharset is not supported by GAE
	public static final Charset CHARSET = Charset.forName("UTF-8");

//-----------------------------------------------------------------------------
//				3 components	
	
	/**
	 * config file location, written in web.xml
	 */
	public static final String MANIFEST = "manifest";
	public static final String L10Files = "l10files";
	public static final String LOCALE 	= "locale";
	public static final String TIME_ZONE = "timezone";
	
	public static final IBeanAssembler		beanAssembler = XMLBeanAssembler.getInstance();
	
	public static Date now() {
		return defaults.calendar.getTime();
	}

	public static void setUp() {
		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		beanAssembler.setClassLoader(loader);
		InputStreamSupport streamSupport = new InputStreamSupport();
		streamSupport.setStreamProvider(new InputStreamProvider() {
			@Override
			public InputStream getStream(String path) {
				return loader.getResourceAsStream(path);
			}
			@Override
			public String getContextPath() {
				return null;
			}
		});
		streamSupport.doInStream("context.xml", new InputStreamCallback() {
			@Override
			public void doWithStream(InputStream stream) throws Exception {
				beanAssembler.assemble(stream);
			}
		});
		

		
		JdbcAux jdbc = beanAssembler.getBean("jdbcAux");
//		final String buf;
//		try {
//			buf = new String(Files.readAllBytes(
//					new File("./create_table_sqlite.sql").toPath()
////					new File("sql/create_table_sqlite.sql").toPath()
//					));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		for (String  s : buf.split(";")) {
//			if (s.length() > 10) {
//				jdbc.execute(s);
//			}
//		}

		final StringBuilder builder = new StringBuilder(1024);
		streamSupport.doInStream("create_table_sqlite.sql", new InputStreamCallback() {
			@Override
			public void doWithStream(InputStream stream) throws Exception {
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
				String line;
				while (null != (line = reader.readLine())) {
					builder.append(line);
				}
			}
		});
	}
	
	private AppContext(){}
}




