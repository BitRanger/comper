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
package org.wangk.comper.util;

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
