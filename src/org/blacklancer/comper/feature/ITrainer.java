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
import org.blacklancer.comper.misc.Predicate;
import org.blacklancer.comper.model.WKQuestionMeta;
import org.blacklancer.comper.util.Pair;

/**
 * 训练器，对一组题进行过滤，交叉，变异等操作
 * 
 * @author BowenCai
 *
 */
public interface ITrainer extends IRefreshable {

	/**
	 * 增加一批新的group，新group符合config要求，即题数和分值一定
	 * @param size
	 * @return
	 */
	public List<Group> recruit(int size);
	
	public List<Group> getInitGroupList(int size);

	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return 将两队组到一起，去除重复元素
	 */
	public List<Group> teamUp(List<Group> t1, List<Group> t2);
	
	/**
	 * @param predicate
	 * @return remaining
	 */
	public List<Group> filter(Predicate<WKQuestionMeta> predicate, List<Group> groups);
	
	/**
	 * 交叉
	 * @param pair
	 */
	public void crossOver(Pair<Group, Group> pair);
	
	public void crossOver(Group group1, Group group2);
	
	
	/**
	 * 变异
	 * @param group
	 * @param ratio
	 */
	public void variate(Group group, float ratio);

	/**
	 * 对表中每一组都按照设定的比率变异
	 * @param groups
	 * @param ratio
	 */
	public void bulkVariate(Set<Group> groups, float ratio);
	
	
	public Config getConfig();

	public void setConfig(Config c);

	public IRandomGenerator getRandomGenerator();

	public void setRandomGenerator(IRandomGenerator randomGenerator);

	public void increase(List<Group> ls);
	public void reduct(List<Group> ls);
}
