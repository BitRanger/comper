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
