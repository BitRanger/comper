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
		return null;
	}
	
	/**
	 * 结果集大小
	 */
	public int numResult = 4;
	
	/**
	 * 训练次数
	 */
	public int numTraining = 256;
	
	/**
	 * 最多训练次数
	 */
	public int maxTraining = 1024;
	
	/**
	 * 中间结果大小 
	 */
	public int numGroup = 16;

	/**
	 * 中间结果最大值
	 */
	public int maxGroup = 128;
	
	/**
	 * 两两交叉的次数
	 */
	public int numCrossOver = 16;
	
	/**
	 * 变异的组的比率
	 */
	public float ratioVariantGroup = 0.0F;
	
	/**
	 * 一组内变异的比率
	 */
	public float ratioVariant;
}
