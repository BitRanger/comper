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
package org.wangk.comper.context;

import java.io.InputStream;

import org.wangk.comper.context.config.FileInputStreamProvider;
import org.wangk.comper.context.config.InputStreamCallback;
import org.wangk.comper.context.config.InputStreamSupport;
import org.wangk.comper.util.Str;

public class AppBooter {

	public static final String configFilePath = "";
	
	public static void main(String[] a) {
		
		InputStreamSupport streamSupport = new InputStreamSupport();
		streamSupport.setStreamProvider( new FileInputStreamProvider());

		AppContext.beanAssembler.setClassLoader(AppBooter.class.getClassLoader());
		
		if (Str.Utils.notBlank(configFilePath)) {
			// build beans
			streamSupport.doInStream(configFilePath,
					new InputStreamCallback() {
						@Override
						public void doWithStream(InputStream stream)
								throws Exception {
							AppContext.beanAssembler.assemble(stream);
						}
					});
		}
		
	}
}
