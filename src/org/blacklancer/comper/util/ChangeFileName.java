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
package org.blacklancer.comper.util;

import java.io.File;
import java.io.FileFilter;

public class ChangeFileName {

	public static void main(String[] args) {
		System.out.println("ChangeFileName.main()");
		
		File dir = new File("");
		if (!dir.exists()) {
			System.out.println("No such dir");
		}
		if (!dir.isDirectory()) {
			System.out.println("Not a dir");
		}
		File[] imgs = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".jsp")
						|| pathname.getName().endsWith(".JPG");
			}
		});
		for (int i = 0; i < imgs.length; i++) {
			File file = imgs[i];
			System.out.println(file.getName());
//			File nf = new File(file.getName());
//			Files.move(source, target, options)
		}
	}

}
