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

import java.util.List;
import java.util.Set;

import org.blacklancer.comper.feature.model.Group;
import org.blacklancer.comper.util.Pair;

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
