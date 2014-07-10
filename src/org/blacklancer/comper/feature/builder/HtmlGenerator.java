/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package org.blacklancer.comper.feature.builder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.blacklancer.comper.AppContext;
import org.blacklancer.comper.dao.DAOChapter;
import org.blacklancer.comper.feature.Config;
import org.blacklancer.comper.feature.model.TestPaper;
import org.blacklancer.comper.model.WKChapter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;



public class HtmlGenerator extends ArrayList<TestPaper> implements IHtmlGen {

	private static final long serialVersionUID = 1520627184223289437L;
	
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




