/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.service;

import java.util.List;
import java.util.Map;

import org.wangk.comper.feature.model.QuestionType;
import org.wangk.comper.model.WKQuestionMeta;
import org.wangk.comper.util.Pair;
import org.wangk.comper.util.Triple;

public interface IQuestionService extends IDAO<WKQuestionMeta> {
	
	public List<WKQuestionMeta> getAll();
	
	public List<WKQuestionMeta> getListOfType(QuestionType type);
	
	public List<WKQuestionMeta> getCandidates(WKQuestionMeta question);
	
	public Pair<WKQuestionMeta, Triple<String, String, String>>
								getContentAnsComment(int id);
	
	public Map<WKQuestionMeta, Triple<String, String, String>> 
								LoadContentAnsComment(List<WKQuestionMeta> metaList);

}
