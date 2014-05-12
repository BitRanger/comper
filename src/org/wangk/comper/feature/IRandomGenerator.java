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

import java.util.List;
import java.util.Set;

import org.wangk.comper.feature.model.Group;
import org.wangk.comper.util.Pair;

/**
 * 随即发生器，根据需要随即挑选题
 * 
 * @author BowenCai
 *
 */
public interface IRandomGenerator {


	public int 				pickInt(int limit);
	public<T> T 			pickSingle(List<T> list);
	public<T> T 			pickSingle(Set<T> list);
	
	public<T> Pair<T, T> 	pickFrom(List<T> groupList);
	
	public<T> Set<T> 		pickFrom(List<T> groupList, float ratio);

	/**
	 * 随机选一组元素，返回元素的下标
	 * 返回值为下标集合，与容器所装内容无关
	 * 
	 * @param groupList
	 * @param ratio
	 * @return
	 */
	public<T> List<Integer> pickIdexes(List<T> groupList, float ratio);
	
}
