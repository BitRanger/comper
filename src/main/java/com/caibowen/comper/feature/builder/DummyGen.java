
package com.caibowen.comper.feature.builder;

import com.caibowen.comper.feature.Config;
import com.caibowen.comper.feature.model.TestPaper;


public class DummyGen implements IHtmlGen {

  @Override
  public void setConfig(Config config) {

  }

  @Override
  public void output(String path) {

  }

  @Override
  public boolean add(TestPaper paper) {
    return false;
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub

  }

}
