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

import org.wangk.comper.feature.model.Group;

/**
 * 评估中间结果
 * @author BowenCai
 *
 */
public interface IEvaluator extends IRefreshable {
	
	/**
	 * 评估一个组的适应度，并将适应度设置给该组
	 * @param group
	 * @return
	 */
	public void evaluate(Group group);
	/**
	 * 评估一个表的组，依次将结果设置给每个组
	 * @param groups
	 */
	public void bulkEvaluate(List<Group> groups);

	/**
	 * 评估该组是否符合预设的要求
	 * 
	 * 先对每组卷子进行评级，再按适应度从小到大排序
	 * 如果最后一个（适应度最大的那个）卷子在误差范围内，就是合格的
	 *
	 * @param groupList
	 * @return
	 */
	public boolean isQualified(List<Group>  group);
	
	public Config getConfig();

	public void setConfig(Config config);
	
}
