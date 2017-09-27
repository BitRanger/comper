
package com.caibowen.comper.model;

public class BWQuestionContent {

  public String content;
  public String answer;
  public String comment;

  /**
   * @return the content
   */
  public String getContent() {
    return content;
  }

  /**
   * @param content the content to set
   */
  public void setContent(String content) {
    this.content = content;
  }

  /**
   * @return the answer
   */
  public String getAnswer() {
    return answer;
  }

  /**
   * @param answer the answer to set
   */
  public void setAnswer(String answer) {
    this.answer = answer;
  }

  /**
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * @param comment the comment to set
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((answer == null) ? 0 : answer.hashCode());
    result = prime * result + ((comment == null) ? 0 : comment.hashCode());
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof BWQuestionContent)) {
      return false;
    }
    BWQuestionContent other = (BWQuestionContent) obj;
    if (answer == null) {
      if (other.answer != null) {
        return false;
      }
    } else if (!answer.equals(other.answer)) {
      return false;
    }
    if (comment == null) {
      if (other.comment != null) {
        return false;
      }
    } else if (!comment.equals(other.comment)) {
      return false;
    }
    if (content == null) {
      if (other.content != null) {
        return false;
      }
    } else if (!content.equals(other.content)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "BWQuestionContent [content=" + content + ", answer=" + answer
        + ", comment=" + comment + "]";
  }
}
