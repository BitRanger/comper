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
package org.wangk.comper.feature.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.feature.ITrainer;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.misc.Predicate;
import org.wangk.comper.model.WKQuestionMeta;
import org.wangk.comper.service.impl.QuestionService;
import org.wangk.comper.util.Assert;
import org.wangk.comper.util.Pair;

public class Trainer implements ITrainer {
	
	private Config config;

	@Inject private IRandomGenerator 	randomGenerator;
	@Inject private QuestionService	questionSerive;

	@Override
	public List<Group> getInitGroupList(int size) {
		return null;
	}
	
	@Override
	public List<Group> recruit(int size) {
		return null;
	}	
	
	@Override
	public List<Group> teamUp(List<Group> t1, List<Group> t2) {
		return null;
	}
	/**
	 * @param predicate
	 * @return remaining
	 */
	@Override
	public List<Group> filter(Predicate<WKQuestionMeta> predicate, List<Group> groups) {
		return null;
	}
	
	@Override
	public void crossOver(Pair<Group, Group> pair){
		crossOver(pair.first, pair.second);
	}
	
	/**
	 * 将两份卷子进行交叉，
	 * 每种题型都交叉
	 */
	@Override
	public void crossOver(Group group1, Group group2) {
		
		Assert.isTrue(group1.slots.size() == group2.slots.size(), 
				"two group size mismatch");
		
		for (int i = 0; i < group1.slots.size(); i++) {
			List<WKQuestionMeta> g1OneType = group1.slots.get(i);
			List<WKQuestionMeta> g2OneType = group2.slots.get(i);
			Assert.isTrue(g1OneType.size() == g2OneType.size());
			final int cutPoint = randomGenerator.pickInt(g2OneType.size());
			for (int j = cutPoint; j < g1OneType.size(); j++) {
				g2OneType.set(j, g1OneType.get(j));
			}
			for (int j = 0; j < cutPoint; j++) {
				g1OneType.set(j, g2OneType.get(j));
			}
		}
	}
	
	@Override
	public void variate(Group group, final float ratio){
		
		for (List<WKQuestionMeta> oneType : group.slots) {

			List<Integer> idxLs = randomGenerator.pickIdexes(oneType, ratio);
			for (Integer i : idxLs) {
				WKQuestionMeta ques = oneType.get(i);
				Set<WKQuestionMeta> cadi = questionSerive.getCandidates(ques);
				oneType.set(i, randomGenerator.pickSingle(cadi));
			}
		}
	}
	
	@Override
	public void bulkVariate(Set<Group> groups, float ratio){
		for (Group group : groups) {
			variate(group, ratio);
		}
	}
	
	
	@Override
	public void refresh(Config config) {
		this.config = config;
		randomGenerator.refresh(config);
	}
	
	@Override
	public Config getConfig() {
		return config;
	}

	@Override
	public void setConfig(Config c){
		this.config = c;
	}


	@Override
	public IRandomGenerator getRandomGenerator() {
		return randomGenerator;
	}

	@Override
	public void setRandomGenerator(IRandomGenerator randomGenerator) {
		this.randomGenerator = randomGenerator;
	}

	public IQuestionService getQuestionSerive() {
		return questionSerive;
	}

	public void setQuestionSerive(IQuestionService questionSerive) {
		this.questionSerive = questionSerive;
	}


}
