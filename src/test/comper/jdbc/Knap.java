package test.comper.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Knap {

	@Before
	public void setUp() throws Exception {
//		HashMap<String, String> map = new HashMap<>();
//		map.put("1", "1111");
//		map.put("2", "211");
//		map.put("3", "311");
//		map.put("4", "411");
//		List<String> ls = new ArrayList<String>(map.values());
//		System.err.println(ls.get(3));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Random random = new Random();
		for (int i = 0; i < w.length - 5; i++) {
			w[i] = 3;//random.nextInt(20);
		}
		for (int i = w.length - 5; i < w.length; i++) {
			w[i] = 5;
		}
		w[12] = 7;
		for (int i : w) {
			System.out.print(i);
			System.out.print(" ");
		}
		System.out.println("\r\nKnap.test()");
		int sz = 20;
//		good.add(0);
		while (good.size() != 5) {
//			sz -= good.get(good.size() - 1);
			sz --;
			good.clear();
			knap(15, sz);
			System.out.println(good);
		}
//		System.out.println(good);
	}
	
	
	List<Integer> good = new ArrayList<>(10);
	int[] w = new int[20];
	
	 boolean knap(int t, int n) {
		if (t == 0) {
			return true;
		} else if (t < 0 || (t > 0 && n < 1)) {
			return false;
		} else if (knap(t - w[n - 1], n - 1)) {
//			System.out.println(String.format("result:n=%d,w[%d]=%d", n, n-1, w[n-1]));
			good.add(w[n-1]);
			return true;
		} else {
			return (knap(t, n - 1));
		}
	 }

}
