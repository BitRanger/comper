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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.w3c.dom.ls.LSInput;
import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.util.Assert;
import org.wangk.comper.util.Pair;

public class RandomGenerator implements IRandomGenerator {

	private Config config;


	@Override
	public int 	pickInt(int limit) {
		return ThreadLocalRandom.current().nextInt(limit);
	}
	
	@Override
	public<T> T pickSingle(List<T> list) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return list.get(random.nextInt(list.size()));
	}
	
	@Override
	public<T> Pair<T, T> pickFrom(List<T> groupList) {
		Assert.notEmpty(groupList);
		
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int idx1 = random.nextInt(groupList.size());
		int idx2 = idx1;
		while (idx2 == idx1) {
			idx2 = random.nextInt(groupList.size());
		}
		return new Pair<T, T>(groupList.get(idx1), groupList.get(idx2));
	}
	
	@Override
	public<T> Set<T> pickFrom(List<T> groupList, float ratio) {
		Assert.notEmpty(groupList);
		
		ThreadLocalRandom random = ThreadLocalRandom.current();
		final int newSz = (int) (ratio * groupList.size());
		Set<T> newSet = new HashSet<T>(newSz * 2);
		while (newSet.size() < newSz) {
			newSet.add(groupList.get(random.nextInt(groupList.size())));
		}
		return newSet;
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
	public void setConfig(Config config) {
		this.config = config;
	}

}
