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

import org.wangk.comper.feature.model.Config;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.misc.Predicate;
import org.wangk.comper.model.WKQuestionMeta;
import org.wangk.comper.util.Pair;

/**
 * 训练器，对一组题进行过滤，交叉，变异等操作
 * 
 * @author BowenCai
 *
 */
public interface ITrainer extends Refreshable {
	
	public List<Group> getInitGroupList();
	
	/**
	 * @param predicate
	 * @return remaining
	 */
	public List<Group> filter(Predicate<WKQuestionMeta> predicate, List<Group> groups);
	
	public void crossOver(Pair<Group, Group> pair);
	
	public void crossOver(Group group1, Group group2);
	
	public void variate(Group group, float ratio);

	/**
	 * 对表中每一组都按照设定的比率变异
	 * @param groups
	 * @param ratio
	 */
	public void bulkVariate(List<Group> groups, float ratio);
	
	public Config getConfig();

	public void setConfig(Config c);

	public IRandomGenerator getRandomGenerator();

	public void setRandomGenerator(IRandomGenerator randomGenerator);

}
