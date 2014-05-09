/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.context;

import java.io.InputStream;
import java.nio.charset.Charset;
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
	
	public static DAOPaper daoPaper;
	public static DAOChapter daoChapter;
	public static DAOQuestion daoQuestion;
	public static JdbcAux jdbc;
	public static  QuestionService questionService;
	
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
		
		jdbc = beanAssembler.getBean("jdbcAux");
		daoChapter = beanAssembler.getBean("daoChapter");
		daoPaper = beanAssembler.getBean("daoPaper");
		daoQuestion = beanAssembler.getBean("daoQuestion");
		questionService = beanAssembler.getBean("questionService");
	}
	
	private AppContext(){}
}




