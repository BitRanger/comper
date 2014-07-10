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
package org.blacklancer.comper;

import java.io.InputStream;

import org.blacklancer.comper.config.FileInputStreamProvider;
import org.blacklancer.comper.config.InputStreamCallback;
import org.blacklancer.comper.config.InputStreamSupport;
import org.blacklancer.comper.util.Str;

@Deprecated
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
