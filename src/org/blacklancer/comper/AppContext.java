/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package org.blacklancer.comper;

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

import org.blacklancer.comper.config.InputStreamCallback;
import org.blacklancer.comper.config.InputStreamProvider;
import org.blacklancer.comper.config.InputStreamSupport;
import org.blacklancer.comper.dao.DAOChapter;
import org.blacklancer.comper.dao.DAOPaper;
import org.blacklancer.comper.dao.DAOQuestion;
import org.blacklancer.comper.dao.QuestionService;
import org.blacklancer.comper.db.jdbc.JdbcAux;
import org.blacklancer.comper.feature.TrainingField;

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
//		String buf = null;
//		try {
//			buf = new String(Files.readAllBytes(
//					new File("./create_table_sqlite.sql").toPath()
////					new File("sql/create_table_sqlite.sql").toPath()
//					));
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
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
		for (String s : builder.toString().split(";")) {
			if (s.length() > 10) {
				jdbc.execute(s);
			}
		}
	}
	
	private AppContext(){}
}




