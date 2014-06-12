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
package org.wangk.comper.model;

import java.io.Serializable;

import org.wangk.comper.feature.model.QuestionType;


/**
 * 
 * 试题的元信息
 */
public class WKQuestionMeta implements Serializable /*, Cloneable*/, Comparable<WKQuestionMeta> {
	
	private static final long serialVersionUID = 8796584201528492642L;
	
	public int				id;
	public int				id_paper;
	public QuestionType		type;
	public float			difficulty;
	public int				score;
	public int				id_chapter;
	
	/**
	 * 警告：此处的 hashCode， equals compareTo 不一致！
	 */
	@Override
	public int compareTo(WKQuestionMeta o) {
		return o.id - this.id;
	}
//	@Override
//	public WKQuestionMeta clone() {
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
		WKQuestionMeta other = (WKQuestionMeta) obj;
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
		return 		"paper ID:" + id_paper
				+ "  chapter ID:" + id_chapter
				+ "  difficulty:" + difficulty 
				+ "  score:" + score;
	}
}
