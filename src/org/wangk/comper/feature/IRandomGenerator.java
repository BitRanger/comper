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
