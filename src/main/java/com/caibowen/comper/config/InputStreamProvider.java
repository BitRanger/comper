
package com.caibowen.comper.config;

import java.io.InputStream;

/**
 *
 * @author BowenCai
 *
 */
public interface InputStreamProvider {

  public InputStream getStream(String path);

  public String getContextPath();
}
