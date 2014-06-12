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
package org.wangk.comper.context;


import java.io.File;
import java.io.InputStream;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Assemble java beans
 * 
 * @author BowenCai
 *
 */
public interface IBeanAssembler {
	
	public static final String LOGGER_NAME = "IBeanAssembler";
	
	public void setClassLoader(@Nonnull ClassLoader loader);
	@Nonnull
	public ClassLoader getClassLoader();
	
	/**
	 * build all beans.
	 * 
	 * Because this function is invoked only at the very beginning of the application
	 *   no exception is handled in the assembling of java beans 
	 * exception is thrown directly to the higher level.
	 * 
	 * @throws Exception 
	 */
	public void 		assemble(@Nonnull final InputStream in) throws Exception;
	public void			assemble(@Nonnull final File file) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return null if not found or exception is thrown in creating non-singleton bean
	 * @throws Exception when creating beans (if bean is not singleton)
	 */
	@Nullable
	public<T> T 		getBean(@Nonnull String id);
	
	@Nullable
	public Pod 			getPod(@Nonnull String id);
	
	
	/**
	 * @param clazz
	 * @return a set of beans of that is instance of this class (including derived)
	 */
	@Nullable
	public Set<Object>	getBeans(@Nonnull Class<?> clazz);
	
	/**
	 * the newly added bean is singleton
	 * @param id
	 * @param bean
	 * @param lifeSpan bean lifes
	 * @return true bean added, false cannot add bean(already exists)
	 */
	public boolean addBean(@Nonnull String id, @Nonnull Object bean, @Nonnull int lifeSpan);
	
	/**
	 * default life Integer.MAX_VALUE
	 * @param id
	 * @param bean
	 * @return
	 */
	public boolean 		addBean(@Nonnull String id, @Nonnull Object bean);
	
	/**
	 * 
	 * @param id
	 * @return bean reference if found, null if not found
	 * @throws Exception 
	 */
	public void 		removeBean(@Nonnull String id);
	
	/**
	 * update singleton
	 * @param id
	 * @param bean
	 * @throws Exception
	 */
	public<T> void 		updateBean(@Nonnull String id, @Nonnull T bean); 
	
	/**
	 * if contains bean of this id
	 * @param id
	 * @return
	 */
	public boolean 		contains(@Nonnull String id);
	
	public boolean 		isSingletion(@Nonnull String id);

	public boolean 		contains(@Nonnull Class<?> clazz);

	/**
	 * 
	 * @param visitor
	 * @throws Exception when creating beans (if bean is not singleton)
	 */
	public void inTake(@Nonnull IAssemlberVisitor visitor);

}
