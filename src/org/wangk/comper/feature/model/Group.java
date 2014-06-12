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
package org.wangk.comper.feature.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;

import org.wangk.comper.model.WKQuestionMeta;


/**
 * 代表一套试卷
 * @author BowenCai
 *
 */
public class Group implements Serializable, Comparable<Group> {

	private static final long serialVersionUID = -2604770597064509121L;

	public EnumMap<QuestionType, ArrayList<WKQuestionMeta>> typeMap;
	
	public Group() {
		typeMap = new EnumMap<QuestionType, ArrayList<WKQuestionMeta>>(QuestionType.class);
		
		typeMap.put(QuestionType.MULTI_CHOICE,  new ArrayList<WKQuestionMeta>(256));
		typeMap.put(QuestionType.FILL_BLANKS,  new ArrayList<WKQuestionMeta>(256));
		typeMap.put(QuestionType.TRUE_FALSE,  new ArrayList<WKQuestionMeta>(256));
		typeMap.put(QuestionType.SIMPLE_QA,  new ArrayList<WKQuestionMeta>(256));
		typeMap.put(QuestionType.EXPLAINATION,  new ArrayList<WKQuestionMeta>(256));
		typeMap.put(QuestionType.APPLICATION,  new ArrayList<WKQuestionMeta>(256));
	}
	
//	/**
//	 * 每种题型
//	 */
//	public List<WKQuestionMeta> multiChoiceLs;
//	public List<WKQuestionMeta> fillblanksLs;
//	public List<WKQuestionMeta> trueFalseLs;
//	public List<WKQuestionMeta> simpleQALs;
//	public List<WKQuestionMeta> explainationLs;
//	public List<WKQuestionMeta> applicationLs;
	
	/**
	 * 记录卷子信息
	 * @author BowenCai
	 *
	 */
	public static class Summary {
		
		public int stamp = -1;
		public float coverage = -1.0F;
		public float difficulty = -1.0F;
		
		public float adaptability = -1.0F;
	}
	public Summary summary = new Summary();
	
	/**
	 * 根据 适应度 排序， 从大到小
	 */
	@Override
	public int compareTo(Group o) {
		if (this.summary.adaptability < o.summary.adaptability) {
			return -1;
		} else if (this.summary.adaptability > o.summary.adaptability) {
			return 1;
		}
		int thisBits = Float.floatToIntBits(this.summary.adaptability);
		int anotherBits = Float.floatToIntBits(o.summary.adaptability);
        return (thisBits == anotherBits ?  0 : // Values are equal
            (thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
             1));
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeMap == null) ? 0 : typeMap.hashCode());
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
		if (!(obj instanceof Group)) {
			return false;
		}
		Group other = (Group) obj;
		if (typeMap == null) {
			if (other.typeMap != null) {
				return false;
			}
		} else if (!typeMap.equals(other.typeMap)) {
			return false;
		}
		return true;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(1024);
		builder.append(new Date().toString()).append('\n')
		.append("Adaptability:").append(this.summary.adaptability).append('\n');
		
		for (QuestionType tp : this.typeMap.keySet()) {
			ArrayList<WKQuestionMeta> ls = typeMap.get(tp);
			if (ls.size() > 0) {
				builder.append(tp.toString()).append(':').append('\n');
				for (WKQuestionMeta wkQuestionMeta : ls) {
					builder.append('\t').append(wkQuestionMeta.printStr()).append('\n');
				}
				builder.append('\n');
			}
		}
		return builder.toString();
//		return "multiChoice=" + typeMap.get(QuestionType.MULTI_CHOICE) 
//				+ ", fillblanksLs="
//				+ typeMap.get(QuestionType.MULTI_CHOICE) + ", trueFalseLs=" + typeMap.get(QuestionType.TRUE_FALSE)
//				+ ", simpleQALs=" + typeMap.get(QuestionType.SIMPLE_QA) + ", explainationLs="
//				+ typeMap.get(QuestionType.EXPLAINATION) + ", applicationLs=" + typeMap.get(QuestionType.APPLICATION)
//				+ ", summary=" + summary + "]";
	}
	


	
}
