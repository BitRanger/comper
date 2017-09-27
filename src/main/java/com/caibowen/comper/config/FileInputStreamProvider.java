
package com.caibowen.comper.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 *
 * @author BowenCai
 *
 */
public class FileInputStreamProvider implements InputStreamProvider {

  @Override
  public InputStream getStream(String path) {
    try {
      return new FileInputStream(path);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getContextPath() {
    return new File("").getParent();
  }

}
