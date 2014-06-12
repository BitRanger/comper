/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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



public class HtmlGenerator extends ArrayList<TestPaper> implements IHtmlGen {

	private static final long serialVersionUID = 1520627184223289437L;
	
	Config config;
	/* (non-Javadoc)
	 * @see org.wangk.comper.feature.builder.IHtmlGen#setConfig(org.wangk.comper.feature.Config)
	 */
	@Override
	public void setConfig(Config config) {
		this.config = config;
	}

	/* (non-Javadoc)
	 * @see org.wangk.comper.feature.builder.IHtmlGen#output(java.lang.String)
	 */
	@Override
	public void output(String path) {

		HashMap<String, Object> model = new HashMap<>(48);
//		model.put("cfg", config);
		model.put("overall_difficulty", config.getDifficulty());
		model.put("total_score", config.getTotalScore());

		ArrayList<WKChapter> chapters = new ArrayList<>(config.getChapterIdSet().size());
		DAOChapter daoChapter =  AppContext.beanAssembler.getBean("daoChapter");
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
//			 border=1 cellspacing=0 cellpadding=0 
//				       style='border-collapse:collapse;border:none;mso-border-alt:solid windowtext .5pt;'
			
			fmCfg.setDirectoryForTemplateLoading(new File("template"));
			template = fmCfg.getTemplate("gen.html");

			writer = new PrintWriter(new File(path + ".html"));
			template.process(model, writer);
			writer.close();
			
			template = fmCfg.getTemplate("gen_doc.html");
			writer = new PrintWriter(new File(path + ".doc"));
			template.process(model, writer);
			writer.close();
			
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		
	}
}




