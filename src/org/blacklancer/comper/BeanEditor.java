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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 
 * @author BowenCai
 *
 */
public class BeanEditor {

	/**
	 * set property, 
	 * first look for public setter for this name
	 * second, look for public field of this name
	 * the property can be virtual, that is, no underlying field exists.
	 * like : 
	 * public void setProp(Type var) {
	 *      // there is no Type prop.
	 * 		// doSomeThingElse
	 * }
	 * @param bean
	 * @param propName
	 * @param var
	 * @throws Exception
	 */
	public static void setBeanProperty(@Nullable Object bean,
										@Nonnull String propName, 
										Object var) throws Exception {

		Class<?> bnClass = bean.getClass(); 
		Method setter = TypeTraits.findSetter(bnClass, propName);
		if (setter == null) {
			setField(bean, propName, var);
		} else {
			callSetter(bean, propName, var);
		}
	}

	/**
	 * assign list or array
	 * 
	 * @param bnClass
	 * @param bean
	 * @param propName
	 * @param varList
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void setListProperty(@Nullable Object bean,
										@Nonnull String propName, 
										List<?> varList) throws Exception {

		Class<?> bnClass = bean.getClass();
		// try get setter
		Method setter = TypeTraits.findSetter(bnClass, propName);
		// try convert list as String list
		Object var = null;
		try {
			var = Converter.translateList((List<String>)varList, bnClass, propName);
		} catch (Exception e) {
			var = null;
		}
		
		// get right var
		if (var != null) {
			if (setter != null) {
				invokeIfValid(setter, bean, var);
			} else {
				// no setter, try set public field
				setField(bean, propName, var);
			}
			
		} else {// failed convert list
			if (setter != null) {
				invokeIfValid(setter, bean, varList);
			} else {
				throw new IllegalArgumentException(
						"no public setter or filed found for [" + propName + "] in object[" + bean);
			}
			
		}
	}
//		
//		
//		
//		
//		if (setter == null) {
//			Field field = bnClass.getField(propName);
//			if (field.getType().isAssignableFrom(varList.getClass())) {
//				setField(bean, propName, varList);
//			} else {
//				@SuppressWarnings("unchecked")
//				Object var = Converter.translateList((List<String>) varList,
//														bnClass, propName);
//				setField(bean, propName, var);
//			}
//			
//		} else {
//			Class<?>[] paramTypes = setter.getParameterTypes();
//			if (paramTypes != null && paramTypes.length == 1) {
//				Class<?> targetClass = null;
//				try {
//					targetClass = bnClass.getDeclaredField(propName).getType();
//				} catch (Exception e) {}
//				if (targetClass == null) {
//					targetClass = paramTypes[0];
//				}
//				
//				if (Klass.isAssignable(varList.getClass(), targetClass)) {
//					setter.invoke(bean, varList);
//System.out.println("1BeanEditor.setListProperty()");
//				} else {
//					@SuppressWarnings("unchecked")
//					Object var = Converter.translateList((List<String>) varList,
//															bnClass, propName);
//					callSetter(bean, propName, var);
//System.out.println("2BeanEditor.setListProperty()");
//				}
//			} else {
//				throw new IllegalArgumentException(
//					"more than one parameter in setter[" + setter.getName() +"]"
//					+ "in class[" + bean.getClass().getName() + "]");
//			}
//		}
//	}
	
	
	public static void invokeIfValid(@Nonnull Method setter, 
										@Nullable Object obj, 
										@Nonnull Object var) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// get setter, check again
		Class<?>[] paramTypes = setter.getParameterTypes();
		if (paramTypes != null && paramTypes.length == 1) {
			if (Klass.isAssignable(var.getClass(), paramTypes[0])) {
				// every thing is OK
				if (Modifier.isStatic(setter.getModifiers())) {
					setter.invoke(null, var);
				} else {
					setter.invoke(obj, var);
				}
			} else {
				throw new IllegalArgumentException(
					"cannot call setter[" 
						+ setter.getName() + "] in object [" 
						+ obj + "]"
						+ "] setter parameter type[" + paramTypes[0].getName()
						+ "] value type [" + var.getClass().getName() + "]");
			}
		} else {
			throw new IllegalArgumentException(
				"more than one parameter in setter[" + setter.getName() +"]"
				+ "in object [" + obj + "]");
		}
	}
	
	/**
	 * set property by set public filed
	 * @param obj
	 * @param fieldName
	 * @param var
	 */
	public static void setField(@Nonnull Object obj, 
								@Nonnull String fieldName, 
								Object var) {

		try {
			Field field = obj.getClass().getField(fieldName);
			if (var instanceof String) {
				var = Converter.slient.translateStr((String)var, field.getType());
				if (var == null) {
					throw new IllegalArgumentException("cannot translate[" 
							+ var + "] to [" + field.getType() + "] in field[" 
							+ fieldName + "] in class[" + obj.getClass().getName() +"]");
				}
			}
			if (Modifier.isStatic(field.getModifiers())) {
				field.set(null, var);
			} else {
				field.set(obj, var);
			}
		} catch (Exception e) {
			throw new IllegalStateException(
			"in class [" + obj.getClass().getName()
			+ "]  cannot find public setter for [" + fieldName 
			+ "] and cannot find public field of this name", e);
		}
	}

	/**
	 * find setter by name
	 * set property by public setter
	 * 
	 * @param obj
	 * @param propName
	 * @param var
	 * @throws NoSuchMethodException
	 */
	public static void callSetter(@Nullable Object obj, 
									@Nonnull String propName, 
									Object var) throws NoSuchMethodException {
		
		Method setter = TypeTraits.findSetter(obj.getClass(), propName);
		
		Class<?>[] paramTypes = setter.getParameterTypes();
		if (paramTypes.length != 1) {
			throw new IllegalStateException("in class [" + obj.getClass().getName()
					+ "]   setter [" + setter.getName()
					+ "] has more than one parameters");
		}
		try {
			if (var instanceof String) {
				var = Converter.slient
							.translateStr((String) var, paramTypes[0]);
				if (var == null) {
					throw new IllegalArgumentException("cannot translate["
							+ var + "] to ["
							+ paramTypes[0].getClass().getName()
							+ "] in field[" + propName + "] in class["
							+ obj.getClass().getName() + "]");
				}
			}
			if (Modifier.isStatic(setter.getModifiers())) {
				setter.invoke(null, var);
			} else {
				setter.invoke(obj, var);
			}
		} catch (Exception e) {
			throw new RuntimeException(
				"cannot set bean [" 
				+ obj.getClass().getSimpleName() 
				+  "] with setter [" + setter.getName() + "]"
				+ " require parameter type[" + paramTypes[0].getSimpleName() 
				+ "] get property [" + (var == null ? "null" : var.getClass().getSimpleName())
				+ "]", e);
			}
	}
	
}
