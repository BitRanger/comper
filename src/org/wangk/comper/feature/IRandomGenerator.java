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
import java.util.Set;

import org.wangk.comper.feature.model.Group;
import org.wangk.comper.util.Pair;

/**
 * 随即发生器，根据需要随即挑选题
 * 
 * @author BowenCai
 *
 */
public interface IRandomGenerator extends IRefreshable{


	public int 				pickInt(int limit);
	public<T> T 			pickSingle(List<T> list);
	
	public<T> Pair<T, T> 	pickFrom(List<T> groupList);
	
	public<T> Set<T> 		pickFrom(List<T> groupList, float ratio);
	
	
	public Config getConfig();

	public void setConfig(Config config);

}