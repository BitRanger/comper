/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *    WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.model;

import java.io.Serializable;

import org.wangk.comper.feature.model.QuestionType;


/**
 * 
 * 试题的元信息
 */
public class WKQuestionMeta implements Serializable, Comparable<WKQuestionMeta> {
	
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
}
