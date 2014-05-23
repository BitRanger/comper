package org.wangk.comper.feature.builder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.dao.DAOChapter;
import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.model.TestPaper;
import org.wangk.comper.model.WKChapter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;



public class HtmlGenerator extends ArrayList<TestPaper> {

	private static final long serialVersionUID = 1520627184223289437L;
	
	Config config;
	public void setConfig(Config config) {
		this.config = config;
	}

	public void output(String path) {

		HashMap<String, Object> model = new HashMap<>(48);
//		model.put("cfg", config);
		model.put("overall_difficulty", config.getDifficulty());
		model.put("total_score", config.getTotalScore());

		ArrayList<WKChapter> chapters = new ArrayList<>(config.getChapterIdSet().size());
		DAOChapter daoChapter =  AppContext.daoChapter;
		for (Integer id : config.getChapterIdSet()) {
			chapters.add(daoChapter.get(id));
		}
		model.put("chapters", chapters);

//		model.put("type_score_num", new ArrayList<>(config.typeScoreAndNum.values()));
		model.put("type_score_num", config.typeScoreAndNum.entrySet());
		model.put("papers", this);
		model.put("date", new Date().toString());
		
		Configuration fmCfg = new Configuration();
		PrintWriter writer = null;
		fmCfg.setDefaultEncoding("utf-8");
		Template template = null;
		try { 
			writer = new PrintWriter(new File(path));
			fmCfg.setDirectoryForTemplateLoading(new File("template"));
			template = fmCfg.getTemplate("gen.html");
			template.process(model, writer);
			
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		
	}
}




