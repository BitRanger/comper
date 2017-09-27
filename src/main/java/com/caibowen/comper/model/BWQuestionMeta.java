
package com.caibowen.comper.model;

import com.caibowen.comper.feature.model.QuestionType;

import java.io.Serializable;


/**
 *
 * 试题的元信息
 */
public class BWQuestionMeta implements Serializable /*, Cloneable*/, Comparable<BWQuestionMeta> {

  private static final long serialVersionUID = 8796584201528492642L;

  public int id;
  public int id_paper;
  public QuestionType type;
  public float difficulty;
  public int score;
  public int id_chapter;

  /**
   * 警告：此处的 hashCode， equals compareTo 不一致！
   */
  @Override
  public int compareTo(BWQuestionMeta o) {
    return o.id - this.id;
  }

  //	@Override
//	public BWQuestionMeta clone() {
//		try {
//			super.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Float.floatToIntBits(difficulty);
    result = prime * result + id;
    result = prime * result + id_chapter;
    result = prime * result + id_paper;
    result = prime * result + score;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BWQuestionMeta other = (BWQuestionMeta) obj;
    if (Float.floatToIntBits(difficulty) != Float
        .floatToIntBits(other.difficulty))
      return false;
    if (id != other.id)
      return false;
    if (id_chapter != other.id_chapter)
      return false;
    if (id_paper != other.id_paper)
      return false;
    if (score != other.score)
      return false;
    if (type != other.type)
      return false;
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "q [id=" + id + ", id_paper=" + id_paper
        + ", type=" + type + ", difficulty=" + difficulty + ", score="
        + score + ", id_chapter=" + id_chapter + "]";
  }

  public String printStr() {
    return "paper ID:" + id_paper
        + "  chapter ID:" + id_chapter
        + "  difficulty:" + difficulty
        + "  score:" + score;
  }
}
