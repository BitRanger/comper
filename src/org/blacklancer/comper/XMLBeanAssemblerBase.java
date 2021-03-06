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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.annotation.Nonnull;

import org.blacklancer.comper.util.Str;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * base class of XMLBeanAssemble
 * parse xml and build beans
 * 
 * @author BowenCai
 *
 */
public abstract class XMLBeanAssemblerBase implements IBeanAssembler {
	
	protected Map<String, Pod> podMap = new ConcurrentHashMap<>(64);
	protected ClassLoader classLoader;

	@Override
	public void setClassLoader(ClassLoader loader) {
		this.classLoader = loader;
	}
	
	@Nonnull
	@Override
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}
	/**
	 * xml bean factory being singleton implies that this function is not
	 * reenterable, thus it is thread safe
	 * 
	 * @param beanList
	 * @throws Exception
	 */
	protected void doAssemble(Document doc) throws Exception {

		NodeList nodeList = doc.getChildNodes();
		Node beanNode = null;
		
		// escape comments
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node tn = nodeList.item(i);
			if (tn != null && tn.getNodeType() == Node.ELEMENT_NODE) {
				beanNode = tn.getFirstChild();
				break;
			}
		}
		if (beanNode == null) {
			throw new IllegalArgumentException("no bean definition found");
		}
		
		while (beanNode.getNextSibling() != null) {

			beanNode = beanNode.getNextSibling();
			Element beanElem;
			if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
				beanElem = (Element) beanNode;
			} else {
				continue;
			}
			int lifeSpan;
			String strLife = beanElem.getAttribute(XMLTags.BEAN_LIFE_SPAN);
			if (Str.Utils.notBlank(strLife)) {
				lifeSpan = Converter.toInteger(strLife);
			} else {
				lifeSpan = Integer.MAX_VALUE;
			}
			
			String bnId = beanElem.getAttribute(XMLTags.BEAN_ID);
			Pod pod = null;

			String bnScope = beanElem.getAttribute(XMLTags.BEAN_SINGLETON);
			boolean isSingleton = true;
			if (Str.Utils.notBlank(bnScope)) {
				isSingleton = Converter.toBool(bnScope);
			}
			Object bean = null;
			if (isSingleton) {
				bean = buildBean(beanElem);
				pod = new Pod(bnId, null, bean, lifeSpan);
			} else {
				pod = new Pod(bnId, beanElem, null, lifeSpan);
			}

			if (Str.Utils.notBlank(bnId)) {
				podMap.put(bnId, pod);
			}
			
			Logger.getLogger(LOGGER_NAME).info("Add Bean: id[" 
					+ bnId + "] class[" + 
					(bean != null ? bean.getClass().getName() : "unknown")
					+ "] singleton[" + isSingleton 
					+ "] lifeSpan[" + lifeSpan + "]"
					);
			
		}
	}
	
	/**
	 * after process (often with life circle managemetn )is done in Pod
	 * @see Pod
	 * @param object
	 */
	protected void preprocess(Object beanObj) {
		if (beanObj instanceof IBeanAssemblerAware) {
			((IBeanAssemblerAware) beanObj).setBeanAssembler(this);
			Logger.getLogger(LOGGER_NAME).info(
					"IBeanAssemblerAware bean[" 
					+ beanObj.getClass().getSimpleName() 
					+ "] beanAssembler setted");
		}
		if (beanObj instanceof BeanClassLoaderAware) {
			((BeanClassLoaderAware)beanObj).setBeanClassLoader(this.classLoader);
			Logger.getLogger(LOGGER_NAME).info(
					"BeanClassLoaderAware bean[" 
					+ beanObj.getClass().getSimpleName() 
					+ "] ClassLoader setted");
		}
	}
	
	/**
	 * For each property, there are 3 notation:
	 * 
	 * 1. <property name="Xyz"><value="Xyz"/></porperty>
	 * 
	 * 2. <property name="Xyz" value="Xyz"/>
	 * 
	 * 3. no property is needed.
	 */
	protected Object buildBean(Element beanElem) throws Exception {

		Class<?> bnClass = getClass(beanElem);
		int modi = bnClass.getModifiers();

		if (Modifier.isAbstract(modi)) {
			throw new IllegalStateException("class[" + bnClass.getName()
					+ "] is abstract and cannot be instantiated");
		} else if (Modifier.isInterface(modi)) {
			throw new IllegalStateException("class[" + bnClass.getName()
					+ "] is interface and cannot be instantiated");
		}

		Object beanObj = bnClass.newInstance();
		preprocess(beanObj);
		
		Logger.getLogger(LOGGER_NAME).info(
				"bean class[" 
				+ bnClass.getSimpleName() 
				+ "] created");
		
		NodeList propLs = beanElem.getElementsByTagName(XMLTags.BEAN_PROPERTY);
		if (propLs == null || propLs.getLength() == 0) {
			// no property
			return beanObj;
		}
		
		// get top level properties only, skip properties in sub beans
		Node next = beanElem.getFirstChild();
		while (next.getNextSibling() != null) {
			Element prop;
			next = next.getNextSibling();

			if (next.getNodeType() == Node.ELEMENT_NODE) {
				prop = (Element) next;
			} else {
				continue;
			}

			String propName = prop.getAttribute(XMLTags.PROPERTY_NAME);
			if (!Str.Utils.notBlank(propName)) {
				throw new NullPointerException(
						"Property name is empty.Element: ["
								+ prop.getNodeName() + "]");
			}
			propName = propName.trim();
			
//System.out.println("class [" + bnClass.getSimpleName() + "] prop[" + propName + "]");

			NodeList varList = prop.getChildNodes();
			if (varList == null || varList.getLength() == 0) {
				// property inside, one string value or one ref
				String varObj = prop.getAttribute(XMLTags.PROPERTY_OBJ);
				String varStr = prop.getAttribute(XMLTags.PROPERTY_VALUE);
				String varRef = prop.getAttribute(XMLTags.PROPERTY_REF);
				
				if (Str.Utils.notBlank(varStr)) {
					// e.g., <property name="number" value="5"/>
					// str value will casted to param type if needed
					BeanEditor.setBeanProperty(beanObj, propName, varStr.trim());
					continue;
					
				} else if (Str.Utils.notBlank(varRef)) {
					// e.g., <property name="bean" ref="someOtherBean"/>
					Object ref = getBean(varRef.trim());
					BeanEditor.setBeanProperty(beanObj, propName, ref);
					continue;
					
				} else if (Str.Utils.notBlank(varObj)) {
					// e.g. <property name="injector" object="com.caibowen.gplume.core.Injector"/>
					Class<?> klass = this.classLoader.loadClass(varObj.trim());
					Object obj = klass.newInstance();
					preprocess(obj);
					BeanEditor.setBeanProperty(beanObj, propName, obj);
					continue;
					
				} else {
					throw new IllegalArgumentException("No value for property["
							+ propName + "] in class [" + bnClass.getName()
							+ "]");
				}

			} else {// properties outside
				
				// check if is list
				boolean isList = false;
				boolean isMap = false;
				/**
				 * 
				 * prop
				 * |
				 * first child -> next
				 * 					|
				 * 				    <list> or <value>
				 */
				Node iter = prop.getFirstChild().getNextSibling();

				if (XMLTags.PROPERTY_LIST.equals(iter.getNodeName())) {
					isList = true;
					isMap = false;
				} else if (XMLTags.PROPERTY_MAP.equals(iter.getNodeName())) {
					isList = false;
					isMap = true;
				}

				if (isMap) {
					/**
					 * <list> or <map>
					 * |
					 * first child -> next
					 * 					|
					 * 				    <key> or <value>
					 */
					iter = iter.getFirstChild().getNextSibling();
					Properties properties = new Properties();
					
					while (iter != null && iter.getNodeType() == Node.ELEMENT_NODE) {
						Element elemBn = (Element) iter;
						if (!XMLTags.PROPERTY_MAP_PROP.equals(elemBn.getNodeName())) {
							throw new IllegalArgumentException(
							"cannot have [" + elemBn.getNodeName() + "] under tag <props>, must be <prop> only");
						}
						String mapK = elemBn.getAttribute(XMLTags.PROPERTY_MAP_KEY);
						if (!Str.Utils.notBlank(mapK)) {
							throw new NullPointerException("empty map key for property[" + propName + "]");
						}
						mapK = mapK.trim();
						String mapV = elemBn.getTextContent();
						if (!Str.Utils.notBlank(mapV)) {
							mapV = elemBn.getAttribute(XMLTags.PROPERTY_VALUE);
							if (!Str.Utils.notBlank(mapV)) {
								throw new NullPointerException(
										"empty map value of key [" 
											+ mapK + "] for property[" + propName + "]");
							}
						}
						mapV = mapV.trim();
						properties.setProperty(mapK, mapV);
						// skip node of #text
						iter = iter.getNextSibling().getNextSibling();
					}
					BeanEditor.setBeanProperty(beanObj, propName, properties);
					
				} else { // single value or list
					if (isList) {
						iter = iter.getFirstChild().getNextSibling();
					}
					// first get all no matter is string literal, ref or new beans
					ArrayList<Object> beanList = new ArrayList<Object>(8);
					while (iter != null && iter.getNodeType() == Node.ELEMENT_NODE) {

						Element elemBn = (Element) iter;
						
						if (XMLTags.BEAN.equals(elemBn.getNodeName())) {
							beanList.add(buildBean(elemBn));

						} else if (XMLTags.PROPERTY_REF.equals(elemBn.getNodeName())) {
							beanList.add(getBean(elemBn.getTextContent().trim()));

						} else if (XMLTags.PROPERTY_VALUE.equals(elemBn.getNodeName())) {
							beanList.add(elemBn.getTextContent().trim());
						} else {
							throw new IllegalArgumentException("Unknown property["
									+ iter.getNodeName() + "]");
						}
						// skip node of #text
						iter = iter.getNextSibling().getNextSibling();
					}// while
					
					// second, use this list
					if (isList) {
						// set list or array
						BeanEditor.setListProperty(beanObj, propName, beanList);
						
					} else {
						if (beanList.size() == 1) {
							BeanEditor.setBeanProperty(beanObj, propName, beanList.get(0));
							
						} else {
							throw new IllegalArgumentException(
									"Bean number miss match for property[" + propName
											+ "] in class ["
											+ bnClass.getName() + "]\n"
											+ "needs 1 actual " + beanList.size()
											+ "beans : " + beanList.toString());
						}
					}
				}

			} // props

		} // for properties

		return beanObj;
	}
	

	protected  Class<?> getClass(Element element) throws ClassNotFoundException {
		String clazzName = element.getAttribute(XMLTags.BEAN_CLASS).trim();
		return this.classLoader.loadClass(clazzName);
	}
}
