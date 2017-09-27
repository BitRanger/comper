
package com.caibowen.comper;


/**
 * BeanAssemblerAware objects will retain a reference to the BeanFactory that created it
 *
 * @author BowenCai
 *
 * @see Injector
 */
public interface IBeanAssemblerAware {

  public void setBeanAssembler(IBeanAssembler factory);
}
