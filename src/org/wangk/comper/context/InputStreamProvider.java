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
package org.wangk.comper.context;

import java.io.InputStream;

/**
 * 
 * @author BowenCai
 *
 */
public interface InputStreamProvider {
	
	public InputStream	getStream(String path);

	public String getContextPath();
}
