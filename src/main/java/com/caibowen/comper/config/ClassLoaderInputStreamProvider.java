
package com.caibowen.comper.config;

import java.io.InputStream;


/**
 *
 * @author BowenCai
 *
 */
public class ClassLoaderInputStreamProvider implements InputStreamProvider {

  ClassLoader classLoader;

  public ClassLoaderInputStreamProvider(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public InputStream getStream(String path) {
    return classLoader.getResourceAsStream(path);
  }

  @Override
  public String getContextPath() {
//		classLoader.
    return null;
  }

}
