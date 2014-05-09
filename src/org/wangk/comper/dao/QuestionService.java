package org.wangk.comper.dao;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.model.WKQuestionMeta;
import org.wangk.comper.util.Assert;

/**
 * 
 * @author BowenCai
 *
 */
public class QuestionService {

	@Inject DAOQuestion daoQuestion;
	
	ConcurrentHashMap<Integer, WKQuestionMeta> AllQuestion;
	
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
	public void loadAll() {
		Assert.notNull(daoQuestion);
		
		List<WKQuestionMeta> all = daoQuestion.getAll();
		for (WKQuestionMeta meta : all) {
			typeMap.get(meta.type).put(meta.id, meta);
		}
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
}
