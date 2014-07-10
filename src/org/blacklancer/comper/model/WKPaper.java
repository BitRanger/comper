/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package org.blacklancer.comper.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

public class WKPaper implements Serializable {
	
	private static final long serialVersionUID = 4110862638764903675L;

	@Generated(value = { "" })
	public int				id;
	
	
	public String			name;
	public String			description;
	public String			name_publisher;

	public int 				score;

	
	@Generated(value = { "" })
	public Timestamp 		time_published;
	
	// calculated
	public float			difficulty = -1.0F;
	// calculated
	public List<Integer>	chapterList = new ArrayList<Integer>();


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Timestamp getTime_published() {
		return time_published;
	}


	public void setTime_published(Timestamp time_published) {
		this.time_published = time_published;
	}


	public String getName_publisher() {
		return name_publisher;
	}


	public void setName_publisher(String name_publisher) {
		this.name_publisher = name_publisher;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public float getDifficulty() {
		return difficulty;
	}


	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}


	public List<Integer> getChapterList() {
		return chapterList;
	}


	public void setChapterList(List<Integer> chapterList) {
		this.chapterList = chapterList;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((chapterList == null) ? 0 : chapterList.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + Float.floatToIntBits(difficulty);
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((name_publisher == null) ? 0 : name_publisher.hashCode());
		result = prime * result + score;
		result = prime * result
				+ ((time_published == null) ? 0 : time_published.hashCode());
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
		WKPaper other = (WKPaper) obj;
		if (chapterList == null) {
			if (other.chapterList != null)
				return false;
		} else if (!chapterList.equals(other.chapterList))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (Float.floatToIntBits(difficulty) != Float
				.floatToIntBits(other.difficulty))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (name_publisher == null) {
			if (other.name_publisher != null)
				return false;
		} else if (!name_publisher.equals(other.name_publisher))
			return false;
		if (score != other.score)
			return false;
		if (time_published == null) {
			if (other.time_published != null)
				return false;
		} else if (!time_published.equals(other.time_published))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WKPaper [id=" + id + ", name=" + name + ", description="
				+ description + ", score=" + score + ", name_publisher="
				+ name_publisher + ", time_published=" + time_published
				+ ", difficulty=" + difficulty + ", chapterList=" + chapterList
				+ "]";
	}
	

}
