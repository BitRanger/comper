
package com.caibowen.comper.feature;

import com.caibowen.comper.util.Assert;

/**
 * 做计算时的常量
 *
 * @author BowenCai
 *
 */
public class Constants {

  public static final float WEIGHT_DIFFICULTY = 0.4F;
  public static final float WEIGHT_COVERAGE = 0.6F;
  public static final int NUM_GROUP = 6;

  public static final int MAX_RESULT = 64;
  public static final int MAX_GROUP = 64;
  public static final int MAX_EVOLUTION = 16384;

  /**
   * 结果集大小
   */
  public int numResult = 4;

  /**
   * 训练次数
   */
  public int numTraining = 256;

  /**
   * 最多训练次数
   */
  public int maxTraining = 1024;

  /**
   * 中间结果大小
   */
  public int numGroup = 16;

  /**
   * 中间结果最大值
   */
  public int maxGroup = 128;

  /**
   * 两两交叉的次数
   */
  public int numCrossOver = 16;

  /**
   * 变异的组的比率
   */
  public float ratioVariantGroup = 0.0F;

  /**
   * 一组内变异的比率
   */
  public float ratioVariant;

  static {//	static_assert

//		Assert.isTrue(0 < NUM_EVOLUTION && NUM_EVOLUTION < 1024,
//				"Number of evolution must be greater than 0 and less than 1024");

    Assert.isTrue(WEIGHT_COVERAGE + WEIGHT_DIFFICULTY - 1F < 0.0001,
        "sum of weight of covrage and weight of difficulty must be 1."
            + "\r\n and now they are " + WEIGHT_COVERAGE + " and " + WEIGHT_DIFFICULTY);

//		Assert.isTrue(0 < NUM_RESULT && NUM_RESULT < 64,
//				"Number of result must be greater than 0 and less than 64");

    Assert.isTrue(0 < MAX_GROUP && MAX_GROUP < 1024,
        "Number of group must be greater than 0 and less than 1024");
  }
}
