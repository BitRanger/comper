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

import org.wangk.comper.feature.IEvaluator;
import org.wangk.comper.feature.model.Config;
import org.wangk.comper.feature.model.Group;



public class Evaluator implements IEvaluator{
	
	private Config config;
	
	@Override
	public float evaluate(Group group) {
		return 0;
	}
	
	@Override
	public void bulkEvaluate(List<Group> groups) {
	}
	
	@Override
	public boolean isQualified(List<Group> groupList) {
		return false;
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
