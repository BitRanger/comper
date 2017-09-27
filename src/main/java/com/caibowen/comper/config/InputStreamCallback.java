
package com.caibowen.comper.config;

import java.io.InputStream;


/**
 *
 * @author BowenCai
 *
 */
public interface InputStreamCallback {

  public void doWithStream(InputStream stream) throws Exception;
}
