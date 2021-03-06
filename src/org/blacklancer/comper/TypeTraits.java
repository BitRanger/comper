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
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Traits type information, as well as providing reflection utilities
 * 
 * @author BowenCai
 *
 */
public final class TypeTraits {
	
	
	public static List<Class<?>> 
	findParamTypes(Class<?> klass, String fieldName) throws NoSuchFieldException {
		
		Set<Field> fields = Klass.getEffectiveField(klass);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				Type type = field.getGenericType();
				if (type instanceof ParameterizedType) {
					Type[] argTps = ((ParameterizedType)type).getActualTypeArguments();
					ArrayList<Class<?>> klasses = new ArrayList<>(argTps.length);
					for (Type t : argTps) {
						klasses.add((Class<?>)t);
					}
					return klasses;
				}
			}
		}
		// no such field
		throw new NoSuchFieldException(
				"cannnot find field of name[" + fieldName 
				+ "] in class [" + klass.getName() + "]");
	}
	
	/**
	 * find setter by fieldName from public methods of the class
	 * parameter type and number and return type is not checked
	 * 
	 * @param clazz
	 * @param fielddName
	 * @return
	 * @throws NoSuchMethodException 
	 */
	@Nullable
	public static Method findSetter(Class<?> clazz, String fieldName) throws NoSuchMethodException {

		String setterName = String.format("set%C%s",
				fieldName.charAt(0), fieldName.substring(1));
		for (Method method : clazz.getMethods()) {

			if(method.getName().equals(setterName)
				&& method.getReturnType().getName().equals("void")) {
				return method;
			}
		}
		return null;
	}
	
	/**
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchMethodException 
	 */
	@Nullable
	public static Method findGetter(Class<?> clazz, String fieldName) throws NoSuchMethodException {

		Field[] fields = clazz.getDeclaredFields();
		Class<?> fieldClazz = null;

		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				fieldClazz = field.getType();
				break;
			}
		}
		
		if (fieldClazz != null) {
			// boolean: try isXyz
			if (fieldClazz.equals(Boolean.class)
					|| fieldClazz.equals(boolean.class)) {

				String getterName = String.format("is%C%s",
						fieldName.charAt(0), fieldName.substring(1));

				for (Method method : clazz.getMethods()) {

					if (method.getName().equals(getterName)
							&& method.getReturnType().equals(fieldClazz)) {
						return method;
					}
				}
				// no isXyz for bool, try getXyz
			} else {

				String getterName = String.format("get%C%s",
						fieldName.charAt(0), fieldName.substring(1));

				for (Method method : clazz.getMethods()) {

					if (method.getName().equals(getterName)
							&& method.getReturnType().equals(fieldClazz)) {
						return method;
					}
				}
			} // else
		}
		
		return null;
	}
	
	public static Class<?> getClass(Type type, int i) {
		
        if (type instanceof ParameterizedType) {
        	
            return getGenericClass((ParameterizedType) type, i);
            
        } else if (type instanceof TypeVariable) {
        	
            return (Class<?>) getClass(((TypeVariable<?>) type).getBounds()[0], 0);
        
        } else {
            return (Class<?>) type;
        }
    }

    public static Class<?> getGenericClass(ParameterizedType parameterizedType, int i) {

        Object genericClass = parameterizedType.getActualTypeArguments()[i];
        
        if (genericClass instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) genericClass).getRawType();
            
        } else if (genericClass instanceof GenericArrayType) {
            return (Class<?>) ((GenericArrayType) genericClass).getGenericComponentType();
            
        } else if (genericClass instanceof TypeVariable) {

            return (Class<?>) getClass(((TypeVariable<?>) genericClass).getBounds()[0], 0);
            
        } else {
            return (Class<?>) genericClass;
        }
    }

    public static void assignField(Object object,
    								String fieldName, 
    								Object var) throws IllegalAccessException, NoSuchFieldException {
    	
    	assignField(object, fieldName, var, false);
    }
	
    /**
     * set field of this name with this var
     * @param object
     * @param fieldName
     * @param var
     * @param refPrivate whether set accessble or not
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static void assignField(Object object,
    								String fieldName, 
    								Object var,
    								boolean refPrivate) throws IllegalAccessException, NoSuchFieldException {
    	
    	Class<?> clazz = object.getClass();
    	Field[] fields = clazz.getDeclaredFields();
    	
    	for (Field field : fields) {
			if (field.getName().equals(fieldName)) {

		    	Class<?> fieldClazz = field.getType();
//System.out.println(" field class " + fieldClazz.getName());
				Object realVar = null;
				
				if (var instanceof String) {
					realVar = Converter.slient.translateStr((String)var, fieldClazz);
				} else if (var instanceof Number) {
					realVar = Converter.castNumber((Number)var, fieldClazz);
				} else {
					realVar = var;
				}
				if (Klass.isAssignable(fieldClazz, realVar.getClass())) {
					if ( !field.isAccessible()) {
						if (refPrivate) {
							field.setAccessible(true);
						} else {
							throw new IllegalAccessException("field is private");
						}
					}
					field.set(object, realVar);
				} else {
					throw new IllegalArgumentException(
							"cannot assign " + realVar + " to field " + fieldName);
				}
				return;
			}
		}
    	throw new NoSuchFieldException(
    			"cannot find [" + fieldName + "] in class [" + clazz.getName() + "]");
	}

	private TypeTraits(){}
}







