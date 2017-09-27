
package com.caibowen.comper.feature.model;


/**
 * Type
 *
 */
public enum QuestionType {

  /**
   * 单选
   */
  MULTI_CHOICE(2, "Multi-Choice"),
  /**
   * Fill Blanks
   */
  FILL_BLANKS(4, "Fill Blanks"),

  /**
   * True or False
   */
  TRUE_FALSE(8, "True or False"),

  /**
   * Q&A
   */
  SIMPLE_QA(16, "Q&A"),

  /**
   * Analyze
   */
  ANALYZE(32, "Analyze"),

  /**
   * Application
   */
  APPLICATION(64, "Application");

  public final int value;
  public final String name;

  private QuestionType(final int newValue, String n) {
    value = newValue;
    name = n;
  }

  public int intValue() {
    return value;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  public static QuestionType lookup(int i) {
    switch (i) {
      case 2:
        return QuestionType.MULTI_CHOICE;
      case 4:
        return QuestionType.FILL_BLANKS;
      case 8:
        return QuestionType.TRUE_FALSE;
      case 16:
        return QuestionType.SIMPLE_QA;
      case 32:
        return QuestionType.ANALYZE;
      case 64:
        return QuestionType.APPLICATION;
    }

    throw new IllegalArgumentException(
        "Cannot find question type of int value[" + i + "]");
  }
}


