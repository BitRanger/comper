
package com.caibowen.comper;

import com.caibowen.comper.config.InputStreamCallback;
import com.caibowen.comper.config.InputStreamSupport;
import com.caibowen.comper.db.jdbc.JdbcAux;
import com.caibowen.comper.config.InputStreamProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


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
  public static final String LOCALE = "locale";
  public static final String TIME_ZONE = "timezone";

  public static final IBeanAssembler beanAssembler = XMLBeanAssembler.getInstance();

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

  private AppContext() {
  }
}




