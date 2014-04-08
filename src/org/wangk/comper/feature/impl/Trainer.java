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

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.feature.ITrainer;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.misc.Predicate;
import org.wangk.comper.model.WKQuestionMeta;
import org.wangk.comper.util.Pair;

public class Trainer implements ITrainer {
	
	private Config config;

	@Inject private IRandomGenerator 	randomGenerator;
	

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
	
	@Override
	public void crossOver(Group group1, Group group2){
		
	}
	
	@Override
	public void variate(Group group, float ratio){
		
	}
	
	@Override
	public void bulkVariate(Set<Group> groups, float ratio){
		
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


}
