package org.wangk.comper.feature.model;

import org.wangk.comper.model.WKChapter;
import org.wangk.comper.model.WKPaper;

public class TestQuestion {

	public WKChapter chapter;
	public WKPaper paper;
	
	public float			difficulty;
	public int				score;
	
	public String content;
	public String answer;
	public String comment;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestQuestion [chapter=" + chapter.name + ", paper=" + paper.name
				+ ", difficulty=" + difficulty + ", score=" + score
				+ ", content=" + content + ", answer=" + answer + ", comment="
				+ comment + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((chapter == null) ? 0 : chapter.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + Float.floatToIntBits(difficulty);
		result = prime * result + ((paper == null) ? 0 : paper.hashCode());
		result = prime * result + score;
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
		if (!(obj instanceof TestQuestion)) {
			return false;
		}
		TestQuestion other = (TestQuestion) obj;
		if (answer == null) {
			if (other.answer != null) {
				return false;
			}
		} else if (!answer.equals(other.answer)) {
			return false;
		}
		if (chapter == null) {
			if (other.chapter != null) {
				return false;
			}
		} else if (!chapter.equals(other.chapter)) {
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
		if (Float.floatToIntBits(difficulty) != Float
				.floatToIntBits(other.difficulty)) {
			return false;
		}
		if (paper == null) {
			if (other.paper != null) {
				return false;
			}
		} else if (!paper.equals(other.paper)) {
			return false;
		}
		if (score != other.score) {
			return false;
		}
		return true;
	}
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
	/**
	 * @return the chapter
	 */
	public WKChapter getChapter() {
		return chapter;
	}
	/**
	 * @param chapter the chapter to set
	 */
	public void setChapter(WKChapter chapter) {
		this.chapter = chapter;
	}
	/**
	 * @return the paper
	 */
	public WKPaper getPaper() {
		return paper;
	}
	
	/**
	 * @param paper the paper to set
	 */
	public void setPaper(WKPaper paper) {
		this.paper = paper;
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
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

}
