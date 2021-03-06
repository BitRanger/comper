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

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Usage:
 * 
 * 1. For Object
 * 
 * Setters can only set bean, short/Short, int/Integer ... double/Double, Date
 * List<String>, 
 * List<Object>
 * 
 * For XML files:
 * 
 * 1. bean inside property will not be added to factory, even an id is specified
 * and you cannot get it later
 * 	e.g. even there is an id="c++11" in a subBean, you cannot call getBean("c++11")// returns null;
 * 
 * 3. top level bean without an id will be build, but will not be added to Ioc container.
 * 
 * 4. addBean("id", obj) returns false if id already exists
 * 
 * example
 * 	<bean id="course" class="model.Course">
		<property name="courseName" value="Programming"/>
		<property name="referenceBook" ref="jcip"/>
		<property name="mainBooks">
		  <list>
			<bean  id="c++11" class="model.Book"><!-- will not be registered in factory >
				<property name="name" value="the c++ programming Language"/>
				<property name="author" value="B.S."/>
				<property name="publisher">
					<ref>
						publisher
					</ref>
				</property>
			</bean>
			<ref>someOtherBook</ref>
		</list>
		</property>
	</bean>
*/

/**
 * 
 * @author BowenCai
 * 
 * @version 1.0
 * @since 2013-12-24
 * 
 */
public class XMLBeanAssembler extends XMLBeanAssemblerBase
								implements Serializable {
	
	private static final long serialVersionUID = 1895612360389006713L;
	
	/**
	 * This is a compile flag.
	 * When this flag is enabled,fields that do not have a correspondent setter 
	 * will be set directly, regardless of its qualifier.
	 * 
	 * However, In some environment, e.g., Google App Engine, 
	 * you cannot reflect on private field on some classes 
	 * due to different security policy.
	 * So it is recommanded that this flag is not open.
	 * 
	 */
	public static final boolean	REFLECT_ON_PRIVATE = false;
	
	private static XMLBeanAssembler handle = null;
	private XMLBeanAssembler() {}
	
	synchronized public static XMLBeanAssembler getInstance() {
		if(handle == null) {
			handle = new XMLBeanAssembler();
		}
		return handle;
	}

	public void assemble(@Nonnull final InputSource in) throws Exception {
		
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(in);
		doc.getDocumentElement().normalize();
		super.doAssemble(doc);
		Logger.getLogger(LOGGER_NAME).info("Created [" + podMap.size() + "] beans");
	}
	
	@Override
	public void assemble(@Nonnull final InputStream in) throws Exception {
		assemble(new InputSource(in));
	}
	
	@Override
	public void	assemble(@Nonnull final File file) throws Exception{
		assemble(new InputSource(file.toURI().toASCIIString()));
	}
	
	/**
	 * @return null if not found or exception is thrown in creating non-singleton bean
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	@Override
	public <T> T getBean(@Nonnull String id) {

		Pod pod = super.podMap.get(id);
		if (pod == null) {
			return null;
		}
		if (pod.isSingleton()) {
			Object bn = pod.getInstance();
			if (bn == null) {
				super.podMap.remove(id);
				return null;
			} else {
				return (T)bn;
			}
		} else {
			try {
				return (T) buildBean(pod.getDescription());
			} catch (Exception e) {
				throw new RuntimeException(
						"faild building non-singleton bean of id[" + id + "]", e);
			}
		}
	}

	@Nullable
	@Override
	public Pod 	getPod(@Nonnull String id) {
		return podMap.get(id);
	}
	
	@Override
	public Set<Object> getBeans(@Nonnull Class<?> clazz) {
		Set<Object> set = new HashSet<>(16);
		for (Pod pod : super.podMap.values()) {
			Object bean = pod.getInternal();
			if (clazz.isInstance(bean)) {
				pod.addAge(1);
				set.add(bean);
			}
		}
		return set;
	}
	
	@Override
	public boolean contains(@Nonnull Class<?> clazz) {
		Set<Object> beans = getBeans(clazz);
		return beans != null && beans.size() > 0;
	}

	
	@Override
	public void removeBean(@Nonnull String id) {
		Pod pod = super.podMap.remove(id);
		if (pod != null) {
			try {
				pod.destroy();
			} catch (Exception e) {
				throw new RuntimeException(
						"faild destroy bean of id[" + id + "]", e);
			}
		}
	}

	@Override
	public <T> void updateBean(@Nonnull String id, @Nonnull T bean) {

		Pod oldPod = super.podMap.get(id);
		if (oldPod == null || oldPod.getInstance() == null) {
			throw new NullPointerException("cannot find bean[" + id + "]");
		}
		
		oldPod.setInstance(bean);
		super.podMap.put(id, oldPod);
	}

	@Override
	public boolean addBean(@Nonnull String id, @Nonnull Object bean) {
		return addBean(id, bean, Integer.MAX_VALUE);
	}
	
	/**
	 * new bean must not be non-singleton
	 */
	@Override
	public boolean addBean(@Nonnull String id, 
							@Nonnull Object bean, 
							@Nonnull int lifeSpan) {
		
		if (contains(id)) {
			return false;
		}
		Pod pod = new Pod(id, null, bean, lifeSpan);
		super.podMap.put(id, pod);
		return true;
	}
	
	@Override
	public boolean contains(String id) {
		return null != super.podMap.get(id);
	}

	@Override
	public boolean isSingletion(String id) {
		Pod pod = super.podMap.get(id);
		if (pod != null) {
			return super.podMap.get(id).isSingleton();
		} else {
			throw new NullPointerException("cannot find bean[" + id + "]");
		}
	}
	
	/**
	 * @param visitor
	 * @throws Exception 
	 */
	@Override
	public void inTake(IAssemlberVisitor visitor) {
		Exception ex = null;
		String id = null;
		for (Map.Entry<String, Pod> entry : super.podMap.entrySet()) {
			Pod pod = entry.getValue();
			/**
			 * create singleton bean
			 */
			try {
				visitor.visit(getBean(pod.getBeanId()));
			} catch (Exception e) {
				/**
				 * continue visiting.
				 * log only the first exception.
				 */
				if (ex != null) {
					ex = e;
					id = pod.getBeanId();
				}
			}
		}
		if (ex != null) {
			throw new RuntimeException(
					"exception when visiting bean of id[" + id + "]", ex);
		}
	}

}
