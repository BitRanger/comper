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

import org.blacklancer.comper.feature.model.Group;

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
