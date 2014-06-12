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
package org.wangk.comper.feature;

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
