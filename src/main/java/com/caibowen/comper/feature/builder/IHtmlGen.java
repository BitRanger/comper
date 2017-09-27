
package com.caibowen.comper.feature.builder;

import com.caibowen.comper.feature.Config;
import com.caibowen.comper.feature.model.TestPaper;

public interface IHtmlGen {

  public abstract void setConfig(Config config);

  public abstract void output(String path);

  public abstract boolean add(TestPaper paper);

  public abstract void clear();
}
