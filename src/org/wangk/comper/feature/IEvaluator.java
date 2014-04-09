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
	 * @param groupList
	 * @return
	 */
	public boolean isQualified(List<Group>  group);
	
	public Config getConfig();

	public void setConfig(Config config);
	
}
