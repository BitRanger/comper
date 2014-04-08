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
package org.wangk.comper.feature.model;

import java.io.Serializable;


public class Group implements Serializable, Comparable<Group> {

	private static final long serialVersionUID = -2604770597064509121L;
//	Map<QuestionType, List<WKQuestionMeta>> qs;
	
	private float adaptability;
	
	
	@Override
	public int compareTo(Group o) {
		throw new UnsupportedOperationException();
	}
	
	public float getAdaptability() {
		return adaptability;
	}
	public void setAdaptability(float adaptability) {
		this.adaptability = adaptability;
	}
	
	
//	MULTI_CHOICE(2),
//	FILL_BLANKS(4),
//	TRUE_FALSE(8),
//	SIMPLE_QA(16),
//	EXPLAINATION(32),
//	APPLY_KNOWLEGE(64);
	
}
