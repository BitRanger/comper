
package com.caibowen.comper.feature.impl;

import com.caibowen.comper.model.BWQuestionMeta;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * 装箱问题，从  sourceQ 里选择 qNumber 道题，这些题的Total Score刚好是totalScore
 *
 */
class Knap {

  public List<BWQuestionMeta> sourceQ;
  public int qNumber;
  public int totalScore;


  ArrayList<BWQuestionMeta> ret = new ArrayList<>(30);

  public ArrayList<BWQuestionMeta> getResult() {
//		System.out.println(totalScore + "  " + qNumber);
    int sz = sourceQ.size();
    knap(totalScore, sz);
    while (ret.size() != qNumber && sz >= qNumber) {
      sz--;
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
      ret.add(sourceQ.get(n - 1));
      return true;
    } else {
      return (knap(totalS, n - 1));
    }
  }

//	public static void main(String...a) {
//		
//		final int SZ = 20;
//		ArrayList<BWQuestionMeta> w = new ArrayList<>();
//		for (int i = 0; i < SZ - 5; i++) {
//			BWQuestionMeta meta = new BWQuestionMeta();
//			meta.score = 3;
//			w.add(meta);
//		}
//		for (int i = SZ - 5; i < SZ; i++) {
//			BWQuestionMeta meta = new BWQuestionMeta();
//			meta.score = 5;
//			w.add(meta);
//		}
//
//		w.get(12).score = 12;
////		for (BWQuestionMeta wkQuestionMeta : w) {
////			System.err.print(wkQuestionMeta.score);
////			System.err.print("  ");
////		}
//		
//		Knap nKnap = new Knap();
//		nKnap.sourceQ = w;
//		nKnap.totalScore = 15;
//		nKnap.qNumber = 4;
//		List<BWQuestionMeta> r = nKnap.getResult();
//		for (BWQuestionMeta wkQuestionMeta : r) {
//			System.err.print(wkQuestionMeta.score);
//			System.err.print("  ");
//		}
//	}
}
