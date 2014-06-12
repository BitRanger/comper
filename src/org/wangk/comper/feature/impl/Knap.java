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
package org.wangk.comper.feature.impl;

import java.util.ArrayList;
import java.util.List;
import org.wangk.comper.model.WKQuestionMeta;


/**
 * 
 * 装箱问题，从  sourceQ 里选择 qNumber 道题，这些题的总分刚好是totalScore
 *
 */
class Knap {
	
	public List<WKQuestionMeta> sourceQ;
	public int qNumber;
	public int totalScore;
	

	ArrayList<WKQuestionMeta> ret = new ArrayList<>(30);
	public ArrayList<WKQuestionMeta> getResult() {
//		System.out.println(totalScore + "  " + qNumber);
		int sz = sourceQ.size();
		knap(totalScore, sz);
		while (ret.size() != qNumber && sz >= qNumber) {
			sz --;
			ret.clear();
			knap(totalScore, sz);
		}
		return ret;
	}
	
	private boolean knap(int totalS, int n) {
		if (totalS == 0) {
			return true;
		} else if (totalS < 0 || (totalS > 0 && n < 1)) {
			return false;
		} else if (knap(totalS - sourceQ.get(n - 1).score, n - 1)) {
//			System.out.println(String.format("result:n=%d,w[%d]=%d", n, n-1, sourceQ.get(n-1).score));
			ret.add(sourceQ.get(n-1));
			return true;
		} else {
			return (knap(totalS, n - 1));
		}
	 }
	
//	public static void main(String...a) {
//		
//		final int SZ = 20;
//		ArrayList<WKQuestionMeta> w = new ArrayList<>();
//		for (int i = 0; i < SZ - 5; i++) {
//			WKQuestionMeta meta = new WKQuestionMeta();
//			meta.score = 3;
//			w.add(meta);
//		}
//		for (int i = SZ - 5; i < SZ; i++) {
//			WKQuestionMeta meta = new WKQuestionMeta();
//			meta.score = 5;
//			w.add(meta);
//		}
//
//		w.get(12).score = 12;
////		for (WKQuestionMeta wkQuestionMeta : w) {
////			System.err.print(wkQuestionMeta.score);
////			System.err.print("  ");
////		}
//		
//		Knap nKnap = new Knap();
//		nKnap.sourceQ = w;
//		nKnap.totalScore = 15;
//		nKnap.qNumber = 4;
//		List<WKQuestionMeta> r = nKnap.getResult();
//		for (WKQuestionMeta wkQuestionMeta : r) {
//			System.err.print(wkQuestionMeta.score);
//			System.err.print("  ");
//		}
//	}
}
