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
package org.blacklancer.comper.db.orm;

import org.blacklancer.comper.db.jdbc.stmt.UpdateStatementCreator;


public interface ObjectMapping<T> {

	
	UpdateStatementCreator insert(T obj);

	UpdateStatementCreator update(T obj);
	
}
