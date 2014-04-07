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

import org.wangk.comper.feature.IRandomGenerator;
import org.wangk.comper.feature.model.Config;
import org.wangk.comper.feature.model.Group;
import org.wangk.comper.util.Pair;

public class RandomGenerator implements IRandomGenerator {

	private Config config;

	@Override
	public Pair<Group, Group> pickFrom(List<Group> groupList) {
		return null;
	}
	
	@Override
	public List<Group> pickFrom(List<Group> groupList, float ratio) {
		return null;
	}
	
	@Override
	public void refresh(Config config) {
		
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
