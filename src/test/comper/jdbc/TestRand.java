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
package test.comper.jdbc;

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
