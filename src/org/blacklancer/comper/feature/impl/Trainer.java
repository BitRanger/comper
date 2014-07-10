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
package org.blacklancer.comper.feature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.blacklancer.comper.dao.QuestionService;
import org.blacklancer.comper.feature.Config;
import org.blacklancer.comper.feature.IRandomGenerator;
import org.blacklancer.comper.feature.ITrainer;
import org.blacklancer.comper.feature.model.Group;
import org.blacklancer.comper.feature.model.QuestionType;
import org.blacklancer.comper.misc.Predicate;
import org.blacklancer.comper.model.WKQuestionMeta;
import org.blacklancer.comper.util.Assert;
import org.blacklancer.comper.util.Pair;

public class Trainer implements ITrainer {
	
	private Config config;

	@Inject private IRandomGenerator 	randomGenerator;
	@Inject private QuestionService		questionSerive;

	@Override
	public List<Group> getInitGroupList(int size) {
//		System.out.println();
//		System.out.println(size);
		Assert.notNull(config);
		questionSerive.loadAll();
		Set<Group> groups = new HashSet<>(size);
		while (groups.size() < size) {
			Group group = new Group();
			//注意，使用config的type列表，因为有些题型没被选入
			for (QuestionType tp : config.getTypeScoreAndNum().keySet()) {
				group.typeMap.put(tp, getFor(tp));
			}
			variate(group, 0.3F);
			groups.add(group);
		}
		return new ArrayList<>(groups);
	}
	
	private ArrayList<WKQuestionMeta> getFor(QuestionType qType) {
		Knap knap = new Knap();
		ArrayList<WKQuestionMeta> multiC = new ArrayList<WKQuestionMeta>(
				questionSerive.typeMap.get(qType).values());
		
		knap.sourceQ = multiC;
		knap.qNumber = config.getTypeScoreAndNum()
				.get(qType).second;
		knap.totalScore = config.getTypeScoreAndNum().get(qType).first;
		return knap.getResult();
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
		
		Assert.isTrue(group1.typeMap.size() == group2.typeMap.size(), 
				"two group size mismatch");
		
		for (QuestionType ty : config.getTypeScoreAndNum().keySet()) {
			
			List<WKQuestionMeta> g1OneType = group1.typeMap.get(ty);
			List<WKQuestionMeta> g2OneType = group2.typeMap.get(ty);
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
		
		for (List<WKQuestionMeta> oneType : group.typeMap.values()) {

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
	public void increase(List<Group> ls) {
		int pad = config.internal.maxGroup - ls.size();
		if (pad > 3) {
			ls.addAll(getInitGroupList(pad));
		}
	}
	
	@Override
	public void reduct(List<Group> ls) {
		//去重
		Set<Group> set = new HashSet<>(ls);
		ls.clear();
		ls.addAll(set);
		
		Collections.sort(ls);
		while(ls.size() > config.internal.numGroup) {
//			System.out.println("Trainer.reduct()");
//			System.out.println(ls.get(0).summary.adaptability);
			ls.remove(0);
		}
	}
	
	@Override
	public void refresh(Config config) {
		this.config = config;
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

	public QuestionService getQuestionSerive() {
		return questionSerive;
	}

	public void setQuestionSerive(QuestionService questionSerive) {
		this.questionSerive = questionSerive;
	}


}
