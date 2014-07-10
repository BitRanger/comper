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

import java.io.InputStream;


/**
 * 
 * @author BowenCai
 *
 */
public class ClassLoaderInputStreamProvider implements InputStreamProvider{
	
	ClassLoader classLoader;
	
	public ClassLoaderInputStreamProvider(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public InputStream getStream(String path) {
		return classLoader.getResourceAsStream(path);
	}

	@Override
	public String getContextPath() {
//		classLoader.
		return null;
	}

}
