/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributor:
 *     WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.core;

import java.io.InputStream;

import org.wangk.comper.context.AppContext;
import org.wangk.comper.context.FileInputStreamProvider;
import org.wangk.comper.context.InputStreamCallback;
import org.wangk.comper.context.InputStreamSupport;
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
