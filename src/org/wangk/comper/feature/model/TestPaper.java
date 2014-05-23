package org.wangk.comper.feature.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.wangk.comper.util.Pair;


public class TestPaper implements Serializable{

	private static final long serialVersionUID = -2128372310492633570L;
	
	public ArrayList<Pair<QuestionType, ArrayList<TestQuestion>>> ls = new ArrayList<>();

	public float coverage = -1.0F;
	public float difficulty = -1.0F;
	public float adaptability = -1.0F;
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder(2048);
		b.append("适应度[").append(adaptability).append("]\r\n");
		b.append("难度[").append(difficulty)
		.append("]  覆盖率[").append(coverage).append("]\r\n");
		
		for (Pair<QuestionType, ArrayList<TestQuestion>> p : this.ls) {
			if (p.second.size() > 0) {
				b.append("    类型[").append(p.first.getZhName()).append("]\r\n");
				for (TestQuestion q : p.second) {
					b.append("        知识点[").append(q.chapter.name)
							.append("] 所在试卷[").append(q.paper.name)
							.append("] 难度[").append(q.difficulty)
							.append("] 分值[").append(q.score)
							.append("]\r\n        ");
					b.append("内容[").append(q.content).append("]\r\n        答案[")
							.append(q.answer).append("]\r\n        备注[")
							.append(q.comment).append("]\r\n\r\n");
				}
//				b.append("");
			}
		}
		return b.toString();
	}


	public void add(Pair<QuestionType, ArrayList<TestQuestion>> p) {
		ls.add(p);
	}
	/**
	 * @return the coverage
	 */
	public float getCoverage() {
		return coverage;
	}

	/**
	 * @param coverage the coverage to set
	 */
	public void setCoverage(float coverage) {
		this.coverage = coverage;
	}

	/**
	 * @return the difficulty
	 */
	public float getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the adaptability
	 */
	public float getAdaptability() {
		return adaptability;
	}

	/**
	 * @param adaptability the adaptability to set
	 */
	public void setAdaptability(float adaptability) {
		this.adaptability = adaptability;
	}

	/**
	 * @return the ls
	 */
	public ArrayList<Pair<QuestionType, ArrayList<TestQuestion>>> getLs() {
		return ls;
	}

	/**
	 * @param ls the ls to set
	 */
	public void setLs(ArrayList<Pair<QuestionType, ArrayList<TestQuestion>>> ls) {
		this.ls = ls;
	}
	
}
