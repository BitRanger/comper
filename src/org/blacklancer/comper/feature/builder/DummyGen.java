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
package org.blacklancer.comper.feature.builder;

import org.blacklancer.comper.feature.Config;
import org.blacklancer.comper.feature.model.TestPaper;




public class DummyGen implements IHtmlGen {

	@Override
	public void setConfig(Config config) {
		
	}

	@Override
	public void output(String path) {
		
	}

	@Override
	public boolean add(TestPaper paper) {
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
