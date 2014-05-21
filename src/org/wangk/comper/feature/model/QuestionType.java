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
	MULTI_CHOICE(2, "\u9009\u62E9\u9898"),
	/**
	 * 填空题
	 */
	FILL_BLANKS(4, "\u586B\u7A7A\u9898"),
	
	/**
	 * 判断题
	 */
	TRUE_FALSE(8, "\u5224\u65AD\u9898"),
	
	/**
	 * 简答题
	 */
	SIMPLE_QA(16, "\u7B80\u7B54\u9898"),
	
	/**
	 * 解释题
	 */
	EXPLAINATION(32, "\u89E3\u91CA\u9898"),
	
	/**
	 * 应用题
	 */
	APPLICATION(64, "\u5E94\u7528\u9898");
	
	private final int value;
	private final String zhName;
	private QuestionType(final int newValue, String n) {
		value = newValue;
		zhName = n;
	}
	public int intValue() {
		return value;
	}
	
	/**
	 * @return the zhName
	 */
	public String getZhName() {
		return zhName;
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


