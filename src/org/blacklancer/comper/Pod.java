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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.w3c.dom.Element;

/**
 * manage bean life circle
 * 
 * @author BowenCai
 *
 */
public class Pod {

	private final int lifeSpan;
	private AtomicInteger age;
	private String beanId;
	
	/**
	 * if singleton, log description, instance == null
	 */
	private Element description;
	
	/**
	 * if singleton, description == null
	 */
	private Object instance;

	/**
	 * 
	 * @param id if empty, this bean will not be stored
	 * @param d
	 * @param instance
	 */
	Pod(@Nullable String id, Element d, Object instance, int lifeSp) {
		
		if (d != null && instance != null || d == null && instance == null) {
			throw new IllegalStateException("cannot decide whether bean is singleton");
		}
		this.beanId = id;
		this.description = d;
		this.instance = instance;
		this.lifeSpan = lifeSp;
		this.age = new AtomicInteger(0);
		process(beanId, instance);
	}
	
	/**
	 * process bean after creation and properties set.
	 * @param id
	 * @param bean
	 */
	static void process(String id, Object bean) {
		if (bean != null && bean instanceof BeanIDAware) {
			((BeanIDAware)bean).setBeanID(id);
		}

		if (bean != null && bean instanceof InitializingBean) {
			try {
				((InitializingBean)bean).afterPropertiesSet();
				Logger.getLogger(IBeanAssembler.LOGGER_NAME).info(
						"bean [" + bean.getClass().getSimpleName() 
						+ "] initialized");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	void addAge(int i) {
		this.age.addAndGet(i);
	}
	/**
	 * destroy this bean
	 * @throws Exception
	 */
	void destroy() throws Exception {
		
		Logger.getLogger(IBeanAssembler.LOGGER_NAME).info(
		"bean id[" + beanId 
		+ "] class[" + (instance != null ? instance.getClass().getSimpleName() : "unknown")
		+ "] destroyed");
		
		if (instance != null && instance instanceof DisposableBean) {
			((DisposableBean)instance).destroy();
		}
		instance = null;
		description = null;
		this.beanId = null;
	}
	
	void setInstance(Object instance) {
		this.instance = instance;
	}
	
	Element getDescription() {
		return description;
	}
	
	/**
	 * get bean instance internal, with out add age
	 * @return
	 */
	Object getInternal() {
		return instance;
	}
//---------------------------------------------------------
	
	public Object getInstance() {
		if(age.get() < lifeSpan) {
			age.incrementAndGet();
			return instance;
		} else {
			try {
				this.destroy();
			} catch (Exception e) {
				throw new RuntimeException(
						"Error destroying bean pod id[" + beanId +"]", e);
			}
			return null;
		}
	}

	public String getBeanId() {
		return beanId;
	}
	
	public boolean isSingleton() {
		return instance != null;
	}
	
	/**
	 * @return the age
	 */
	public int getAge() {
		return age.get();
	}

	/**
	 * @return the lifeSpan
	 */
	public int getLifeSpan() {
		return lifeSpan;
	}
}
