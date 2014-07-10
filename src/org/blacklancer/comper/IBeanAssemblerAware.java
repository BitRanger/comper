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
package org.blacklancer.comper;


/**
 * BeanAssemblerAware objects will retain a reference to the BeanFactory that created it
 * 
 * @author BowenCai
 *
 * @see Injector
 */
public interface IBeanAssemblerAware {
	
	public void setBeanAssembler(IBeanAssembler factory);
}
