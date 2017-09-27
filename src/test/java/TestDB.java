import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.caibowen.comper.config.InputStreamCallback;
import com.caibowen.comper.config.InputStreamProvider;
import com.caibowen.comper.config.InputStreamSupport;
import com.caibowen.comper.db.jdbc.JdbcAux;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by bowen.cai on 9/15/2017.
 */
public class TestDB {

  @Test
  public void t1() throws Throwable {

    ComboPooledDataSource ds = new ComboPooledDataSource();
    ds.setDriverClass("org.sqlite.JDBC");
    ds.setJdbcUrl("jdbc:sqlite:comper.db");

    final ClassLoader loader = Thread.currentThread().getContextClassLoader();
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


    JdbcAux jdbc = new JdbcAux();
    jdbc.setDataSource(ds);

    for (String s : builder.toString().split(";")) {
      if (s.length() > 10) {
        jdbc.execute(s);
      }
    }

  }
}
