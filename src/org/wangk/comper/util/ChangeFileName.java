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
