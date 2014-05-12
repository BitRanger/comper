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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.util.Assert;
import org.wangk.comper.util.Pair;

public class RandomGenerator implements IRandomGenerator {



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
	public<T> T pickSingle(Set<T> set) {
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int idx = random.nextInt(set.size());
		int i = 0;
		for (T t : set) {
			if (idx == i) {
				return t;
			}
			i++;
		}
		return set.iterator().next();
	}

	@Override
	public<T> List<Integer> pickIdexes(List<T> groupList, float ratio) {
		
		ThreadLocalRandom random = ThreadLocalRandom.current();
		final int $ = groupList.size();
		final int sz = (int) ($ * ratio);
		Set<Integer> set = new HashSet<>($);
		while (set.size() < sz) {
			set.add(random.nextInt($));
		}
		return new ArrayList<>(set);
	}
	/**
	 * 选一对
	 */
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
	
	/**
	 * 按比率选出一部分，选出的无重复
	 */
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
	
}
