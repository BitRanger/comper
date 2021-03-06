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
 * 
 * these properties serves as the XLD
 * @author BowenCai
 *
 */
public class XMLTags {

	public static final String ROOT = "beans";
	
	public static final String BEAN = "bean";
	
	public static final String BEAN_ID = "id";
	public static final String BEAN_CLASS = "class";
	public static final String BEAN_PROPERTY = "property";

	/**
	 * specify singleton bean, false by default
	 */
	public static final String BEAN_SINGLETON = "singleton";
	/**
	 * 
	 */
	public static final String BEAN_LIFE_SPAN = "lifespan";
	
	public static final String PROPERTY_NAME = "name";

	public static final String PROPERTY_OBJ = "instance";
	public static final String PROPERTY_LIST = "list";
	public static final String PROPERTY_MAP  = "props";
	public static final String PROPERTY_MAP_PROP  = "prop";
	public static final String PROPERTY_MAP_KEY  = "key";
	public static final String PROPERTY_VALUE = "value";
	public static final String PROPERTY_REF = "ref";
}
