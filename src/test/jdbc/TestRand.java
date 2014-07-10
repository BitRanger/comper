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
package test.jdbc;

import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRand {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
//		SecureRandom random = SecureRandom
		for (int i = 0; i < 32; i++) {
			
		ThreadLocalRandom random = ThreadLocalRandom.current();
		int idx1 = random.nextInt(32);
		int idx2 = idx1;
		while (idx2 == idx1) {
			idx2 = random.nextInt(32);
		}
		System.out.println(idx1 + "  " + idx2);
		}
	}

}
