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
package org.wangk.comper.dao;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.feature.model.TestPaper;
import org.wangk.comper.feature.model.TestQuestion;
import org.wangk.comper.model.WKQuestionContent;
import org.wangk.comper.model.WKQuestionMeta;
import org.wangk.comper.util.Assert;
import org.wangk.comper.util.Pair;
import org.wangk.comper.util.Triple;

/**
 * 
 * @author BowenCai
 *
 */
public class QuestionService {

	@Inject DAOQuestion daoQuestion;
	@Inject DAOChapter 	daoChapter;
	@Inject DAOPaper	daoPaper;
//	ConcurrentHashMap<Integer, WKQuestionMeta> AllQuestion;
	
	public EnumMap<QuestionType, ConcurrentHashMap<Integer, WKQuestionMeta>> typeMap;
	
	public QuestionService() {
		typeMap = new EnumMap<QuestionType, ConcurrentHashMap<Integer, WKQuestionMeta>>(QuestionType.class);
		
		typeMap.put(QuestionType.MULTI_CHOICE,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.FILL_BLANKS,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.TRUE_FALSE,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.SIMPLE_QA,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.EXPLAINATION,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.APPLICATION,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		
	}
	/**
	 * 从数据库里读出所有题，分类存到槽里
	 */
	private boolean loaded = false;
	public void loadAll(boolean force) {
		if(force || !loaded) {
			List<WKQuestionMeta> all = daoQuestion.getAll();
			for (WKQuestionMeta meta : all) {
				typeMap.get(meta.type).put(meta.id, meta);
			}
		}
	}
	
	public void loadAll() {
		Assert.notNull(daoQuestion);
		if (loaded) {
			return;
		}
		loadAll(true);
		loaded = true;
	}
	
	public Map<Integer, TestQuestion> getFullQuestion() {
		HashMap<Integer, TestQuestion> mp = new HashMap<>(2048);
		for (ConcurrentHashMap<Integer, WKQuestionMeta> typeQ : typeMap.values()) {
			for (WKQuestionMeta meta : typeQ.values()) {
				mp.put(meta.id, getQuestion(meta));
			}
		}
		return mp;
	}
	
	
	public TestQuestion getQuestion(WKQuestionMeta meta) {
		if (daoChapter == null) {
			daoChapter = AppContext.beanAssembler.getBean("daoChapter");
//			System.out.println("QuestionService.getQuestion()");
//			System.out.println(daoChapter);
		}
		if (daoPaper == null) {
			daoPaper = AppContext.beanAssembler.getBean("daoPaper");
		}
		TestQuestion q = new TestQuestion();
		Triple<String, String, String> t = daoQuestion.getContent(meta.id);
		q.answer = t.second;
		q.comment = t.third;
		q.content = t.first;
		q.difficulty = meta.difficulty;
		q.chapter = daoChapter.get(meta.id_chapter);
		q.paper = daoPaper.get(meta.id_paper);
		q.score = meta.score;
		
		return q;
	}
	
	public void delete(WKQuestionMeta meta) {
		daoQuestion.delete(meta.id);
//		loadAll(true);
		typeMap.get(meta.type).remove(meta.id);
	}
	
	/**
	 * 选出类型一致，分值相同的题
	 * @param questionMeta
	 * @return a set of questions of the same type and score
	 */
	public Set<WKQuestionMeta> getCandidates(WKQuestionMeta questionMeta) {
		Assert.notNull(questionMeta);
		ConcurrentHashMap<Integer, WKQuestionMeta> oneType = typeMap.get(questionMeta.type);
		Set<WKQuestionMeta> cadi = new HashSet<WKQuestionMeta>(64);
		for (WKQuestionMeta qMeta : oneType.values()) {
			if (qMeta.score == questionMeta.score
					&& !qMeta.equals(questionMeta)) {
				cadi.add(qMeta);
			}
		}
		return cadi;
	}

	public DAOQuestion getDaoQuestion() {
		return daoQuestion;
	}

	public void setDaoQuestion(DAOQuestion daoQuestion) {
		this.daoQuestion = daoQuestion;
	}
	
	
	public TestPaper toPaper(Group group) {
		TestPaper paper = new TestPaper();
		paper.adaptability = group.summary.adaptability;
		paper.coverage = group.summary.coverage;
		paper.difficulty = group.summary.difficulty;
		
		for (Map.Entry<QuestionType, ArrayList<WKQuestionMeta>> e : group.typeMap.entrySet()) {
			if (e.getValue().size() > 0) {
				ArrayList<TestQuestion> qs = new ArrayList<>();
				Pair<QuestionType, ArrayList<TestQuestion>> p = new Pair<>();
				p.first = e.getKey();
				for (WKQuestionMeta m : e.getValue()) {
					qs.add(getQuestion(m));
				}
				p.second = qs;
				paper.add(p);
			}
		}
		return paper;
	}

	/**
	 * @return the daoChapter
	 */
	public DAOChapter getDaoChapter() {
		return daoChapter;
	}

	/**
	 * @param daoChapter the daoChapter to set
	 */
	public void setDaoChapter(DAOChapter daoChapter) {
		this.daoChapter = daoChapter;
	}

	/**
	 * @return the daoPaper
	 */
	public DAOPaper getDaoPaper() {
		return daoPaper;
	}

	/**
	 * @param daoPaper the daoPaper to set
	 */
	public void setDaoPaper(DAOPaper daoPaper) {
		this.daoPaper = daoPaper;
	}
}



