/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.wangk.comper.feature.builder;

import org.wangk.comper.feature.Config;
import org.wangk.comper.feature.model.TestPaper;




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
