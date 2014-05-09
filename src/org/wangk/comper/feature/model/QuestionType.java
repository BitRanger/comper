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


/**
 * 题型
 *
 */
public enum QuestionType {
	
	/**
	 * 单选
	 */
	MULTI_CHOICE(2),
	/**
	 * 填空题
	 */
	FILL_BLANKS(4),
	
	/**
	 * 判断题
	 */
	TRUE_FALSE(8),
	
	/**
	 * 简答题
	 */
	SIMPLE_QA(16),
	
	/**
	 * 解释题
	 */
	EXPLAINATION(32),
	
	/**
	 * 应用题
	 */
	APPLICATION(64);
	
	private final int value;

	private QuestionType(final int newValue) {
		value = newValue;
	}
	public int intValue() {
		return value;
	}
	
	public static QuestionType lookup(int i) {
		switch (i) {
		case 2:
			return QuestionType.MULTI_CHOICE;
		case 4:
			return QuestionType.FILL_BLANKS;
		case 8:
			return QuestionType.TRUE_FALSE;
		case 16:
			return QuestionType.SIMPLE_QA;
		case 32:
			return QuestionType.EXPLAINATION;
		case 64:
			return QuestionType.APPLICATION;
		}

		throw new IllegalArgumentException(
				"Cannot find question type of int value[" + i + "]");
	}
}


