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
package org.wangk.comper.context;


import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @author BowenCai
 *
 */
public interface IBeanAssembler extends Serializable {

	/**
	 * these properties serves as the XLD
	 */
	public static final String ROOT = "beans";
	
	public static final String BEAN = "bean";
	public static final String BEAN_ID = "id";
	public static final String BEAN_CLASS = "class";
	public static final String BEAN_PROPERTY = "property";

	/**
	 * specify singleton bean, false by default
	 */
	public static final String BEAN_SINGLETON = "singleton";
	
	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_LIST = "list";
	public static final String PROPERTY_VALUE = "value";
	public static final String PROPERTY_REF = "ref";
	

	public void setClassLoader(ClassLoader loader);
	/**
	 * build all beans.
	 * No exception is handled in the assembling of java beans 
	 * exception is thrown directly to the higher level.
	 * 
	 * This is because this function is invoked only at the very beginning of the application
	 * 
	 * @throws Exception 
	 */
	public void 		assemble(final InputStream in) throws Exception;
	public void			assemble(final File file) throws Exception;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception when creating beans (if bean is not singleton)
	 */
	public<T> T 		getBean(String id);
	
	/**
	 * @param clazz
	 * @return beans of this type
	 */
	public Set<Object>	getBeans(Class<?> clazz);
	
	/**
	 * the newly added bean is singleton
	 * @param id
	 * @param bean
	 * @return true bean added, false cannot add bean(already exists)
	 */
	public boolean 		addBean(String id, Object bean);
	
	/**
	 * 
	 * @param id
	 * @return bean reference if found, null if not found
	 */
	public<T> T 		removeBean(String id);
	
	/**
	 * update singleton
	 * @param id
	 * @param bean
	 * @throws Exception
	 */
	public<T> void 		updateBean(String id, T bean) throws Exception; 
	
	public boolean 		contains(String id);
	
	public boolean 		isSingletion(String id);
	
	public static interface Visitor extends Serializable {
		public void visit(Object bean);
	}
	
	/**
	 * 
	 * @param visitor
	 * @throws Exception when creating beans (if bean is not singleton)
	 */
	public void inTake(Visitor visitor);

}
