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
package org.wangk.comper.context.config;

import java.io.IOException;
import java.io.InputStream;


/**
 * 
 * @author BowenCai
 *
 */
public class InputStreamSupport {
	
	InputStreamProvider streamProvider;


	public InputStreamProvider getStreamProvider() {
		return streamProvider;
	}
	public void setStreamProvider(InputStreamProvider streamProvider) {
		this.streamProvider = streamProvider;
	}


	public void doInStream(String path, InputStreamCallback callback) {
		Exception ex = null;
		InputStream inputStream = null;
		try {
			inputStream = streamProvider.getStream(path);
			callback.doWithStream(inputStream);
			
		} catch (IOException e) {
			ex = e;
		} catch (Exception e) {
			ex = e;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					ex = e;
				}
			}
		}
		if (ex != null) {
			throw new RuntimeException("IO Error", ex);
		}
		
	}
}
