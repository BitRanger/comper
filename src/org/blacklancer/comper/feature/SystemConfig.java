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
package org.blacklancer.comper.feature;

import java.io.Serializable;


/**
 * 系统设定，主要是计算时的各种参数
 * 
 * @author BowenCai
 *
 */
public class SystemConfig implements Serializable {
	
	private static final long serialVersionUID = 8511259440940328750L;
	
	public static SystemConfig getDefault() {
		SystemConfig config = new SystemConfig();
		return config;
	}
	
	public static final float TOLERANCE = 0.35F;
	public static final int NUM_RESULT = 4;
	
	public float DIFF_LOWER = 0.35F;
	public float DIFF_UPPER = 0.95F;
	
	public float weightCoverage = 0.6F;
	public float weightDifficulty = 0.4F;
	/**
	 * 结果集大小
	 */
	public int numResult = 4;
	
	/**
	 * 训练次数
	 */
//	public int numTraining = 256;
	
	/**
	 * 最多训练次数
	 */
	public int maxTraining = 256;
	
	/**
	 * 中间结果大小 
	 */
	public int numGroup = 144;

	/**
	 * 中间结果最大值
	 */
	public int maxGroup = 192;
	
	/**
	 * 两两交叉的次数
	 */
	public int numCrossOver = 96;
	
	/**
	 * 变异的组占总组数的比率
	 */
	public float ratioVariantGroup = 0.64F;
	
	/**
	 * 一组内变异的题比率
	 */
	public float ratioVariant = 0.4F;
}
