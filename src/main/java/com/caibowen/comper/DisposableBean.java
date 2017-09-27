
package com.caibowen.comper;


/**
 * similar to Spring::DisposableBean
 * @author BowenCai
 *
 */
public interface DisposableBean {

  void destroy() throws Exception;
}
