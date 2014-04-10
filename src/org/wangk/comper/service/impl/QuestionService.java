package org.wangk.comper.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.wangk.comper.feature.model.Group.Summary;
import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.model.WKQuestionMeta;


public class QuestionService {

	ConcurrentHashMap<Integer, WKQuestionMeta> AllQuestion;
	
	public HashMap<QuestionType, ConcurrentHashMap<Integer, WKQuestionMeta>> typeMap;
	
	public QuestionService() {
		
		typeMap.put(QuestionType.MULTI_CHOICE,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.FILL_BLANKS,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.TRUE_FALSE,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.SIMPLE_QA,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.EXPLAINATION,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		typeMap.put(QuestionType.APPLICATION,  new ConcurrentHashMap<Integer, WKQuestionMeta>(256));
		
	}
	
	public void loadAll() {
		
	}
	
	/**
	 * 
	 * @param questionMeta
	 * @return a set of questions of the same type and score
	 */
	public Set<WKQuestionMeta> getCandidates(WKQuestionMeta questionMeta) {
		ConcurrentHashMap<Integer, WKQuestionMeta> oneType = typeMap.get(questionMeta.type);
		Set<WKQuestionMeta> cadi = new HashSet<>();
		for (WKQuestionMeta qMeta : oneType.values()) {
			if (qMeta.score == questionMeta.score) {
				cadi.add(qMeta);
			}
		}
		return cadi;
	}
}
