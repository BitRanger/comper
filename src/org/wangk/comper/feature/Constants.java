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
package org.wangk.comper.feature;

import org.wangk.comper.util.Assert;

/**
 * 做计算时的常量
 * 
 * @author BowenCai
 *
 */
public class Constants {

	public static final float WEIGHT_DIFFICULTY = 0.4F;
	public static final float WEIGHT_COVERAGE = 0.6F;
	public static final int NUM_GROUP = 6;

	public static final int MAX_RESULT = 64;
	public static final int MAX_GROUP = 64;
	public static final int MAX_EVOLUTION = 16384;
	
	static {//	static_assert
		
//		Assert.isTrue(0 < NUM_EVOLUTION && NUM_EVOLUTION < 1024,
//				"Number of evolution must be greater than 0 and less than 1024");
		
		Assert.isTrue(WEIGHT_COVERAGE + WEIGHT_DIFFICULTY - 1F < 0.0001, 
				"sum of weight of covrage and weight of difficulty must be 1."
				+ "\r\n and now they are " + WEIGHT_COVERAGE + " and " + WEIGHT_DIFFICULTY);

//		Assert.isTrue(0 < NUM_RESULT && NUM_RESULT < 64,
//				"Number of result must be greater than 0 and less than 64");
		
		Assert.isTrue(0 < MAX_GROUP && MAX_GROUP < 1024,
				"Number of group must be greater than 0 and less than 1024");
	}
}
