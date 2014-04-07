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
import java.sql.Date;

public class WKChapter implements Serializable {

	private static final long serialVersionUID = -1959747962303882199L;
	
	public int 				id;
	public Date 			time_create;
	public String 			name;
	public String 			description;
}
