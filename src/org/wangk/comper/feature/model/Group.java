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
package org.wangk.comper.feature.model;

import java.io.Serializable;
import java.util.List;

import org.wangk.comper.model.WKQuestionMeta;


public class Group implements Serializable, Comparable<Group> {

	private static final long serialVersionUID = -2604770597064509121L;
//	Map<QuestionType, List<WKQuestionMeta>> qs;
	
	public List<List<WKQuestionMeta> > allMetaLs;
	public Summary summary = new Summary();
	
	public List<WKQuestionMeta> multiChoiceLs;
	public List<WKQuestionMeta> fillblanksLs;
	public List<WKQuestionMeta> trueFalseLs;
	public List<WKQuestionMeta> simpleQALs;
	public List<WKQuestionMeta> explainationLs;
	public List<WKQuestionMeta> applicationLs;
	
	public static class Summary {
		
		public int stamp = -1;
		public float coverage = -1.0F;
		public float difficulty = -1.0F;
		
		public float adaptability = -1.0F;
	}
	
	@Override
	public int compareTo(Group o) {
		if (this.summary.adaptability > o.summary.adaptability) {
			return 1;
		} else if (this.summary.adaptability < o.summary.adaptability) {
			return -1;
		}
		int thisBits = Float.floatToIntBits(this.summary.adaptability);
		int anotherBits = Float.floatToIntBits(o.summary.adaptability);
        return (thisBits == anotherBits ?  0 : // Values are equal
            (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
             1));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((allMetaLs == null) ? 0 : allMetaLs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Group)) {
			return false;
		}
		Group other = (Group) obj;
		if (allMetaLs == null) {
			if (other.allMetaLs != null) {
				return false;
			}
		} else if (!allMetaLs.equals(other.allMetaLs)) {
			return false;
		}
		return true;
	}
	


	
}
