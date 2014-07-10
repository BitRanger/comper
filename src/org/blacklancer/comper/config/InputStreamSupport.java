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
package org.blacklancer.comper.config;

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
