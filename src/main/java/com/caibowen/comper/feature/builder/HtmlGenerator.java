
package com.caibowen.comper.feature.builder;

import com.caibowen.comper.dao.DAOChapter;
import com.caibowen.comper.feature.Config;
import com.caibowen.comper.feature.model.TestPaper;
import com.caibowen.comper.model.BWChapter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import com.caibowen.comper.AppContext;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class HtmlGenerator implements IHtmlGen {

  private static final long serialVersionUID = 1520627184223289437L;
  ArrayList<TestPaper> paperLs = new ArrayList<>(10);
  @Override
  public boolean add(TestPaper paper) {
    paperLs.add(paper);
    return true;
  }

  @Override
  public void clear() {
    paperLs.clear();
  }

  Config config;

  /* (non-Javadoc)
   * @see org.blacklancer.comper.feature.builder.IHtmlGen#setConfig(org.blacklancer.comper.feature.Config)
   */
  @Override
  public void setConfig(Config config) {
    this.config = config;
  }

  /* (non-Javadoc)
   * @see org.blacklancer.comper.feature.builder.IHtmlGen#output(java.lang.String)
   */
  @Override
  public void output(String path) {

    HashMap<String, Object> model = new HashMap<>(48);
//		model.put("cfg", config);
    model.put("overall_difficulty", config.getDifficulty());
    model.put("total_score", config.getTotalScore());

    ArrayList<BWChapter> chapters = new ArrayList<>(config.getChapterIdSet().size());
    DAOChapter daoChapter = AppContext.beanAssembler.getBean("daoChapter");
    for (Integer id : config.getChapterIdSet()) {
      chapters.add(daoChapter.get(id));
    }
    model.put("chapters", chapters);

//		model.put("type_score_num", new ArrayList<>(config.typeScoreAndNum.values()));
    model.put("type_score_num", config.typeScoreAndNum.entrySet());
    model.put("paper_ls", paperLs);
    model.put("date", new Date().toString());

    Configuration fmCfg = new Configuration();
    PrintWriter writer = null;
    fmCfg.setDefaultEncoding("utf-8");
    Template template = null;
    try {
//			 border=1 cellspacing=0 cellpadding=0 
//				       style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;'

      fmCfg.setDirectoryForTemplateLoading(new File("template"));
      template = fmCfg.getTemplate("output_temp.html");

      writer = new PrintWriter(new File(path + ".html"));
      template.process(model, writer);
      writer.flush();
      writer.close();

    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }

  }
}




