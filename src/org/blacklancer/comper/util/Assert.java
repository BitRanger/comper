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
/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.blacklancer.comper.util;

import java.util.Collection;
import java.util.Map;

public abstract class Assert {

	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}


	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}


	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "[Assertion failed] - this argument is required; it must not be null");
	}


	public static void notBlank(String text, String message) {
		if (!Str.Utils.notBlank(text)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notBlank(String text) {
		notBlank(text,
				"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}



	/**
	 * Assert that an array has elements; that is, it must not be
	 * {@code null} and must have at least one element.
	 * <pre class="code">Assert.notEmpty(array, "The array must have elements");</pre>
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object array is {@code null} or has no elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (array == null || array.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Object[] array) {
		notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}


	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (Object element : array) {
				if (element == null) {
					throw new IllegalArgumentException(message);
				}
			}
		}
	}

	public static void noNullElements(Object[] array) {
		noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
	}


	public static void notEmpty(Collection<?> collection, String message) {
		if (collection == null || collection.size() == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}


	public static void notEmpty(Map<?,?> map, String message) {
		if (map == null || map.size() == 0) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void notEmpty(Map<?,?> map) {
		notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}


	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(
					(Str.Utils.notBlank(message) ? message + " " : "") +
					"Object of class [" + (obj != null ? obj.getClass().getName() : "null") +
					"] must be an instance of " + type);
		}
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * @param superType the super type to check
	 * @param subType the sub type to check
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	/**
	 * Assert that {@code superType.isAssignableFrom(subType)} is {@code true}.
	 * <pre class="code">Assert.isAssignable(Number.class, myClass);</pre>
	 * @param superType the super type to check against
	 * @param subType the sub type to check
	 * @param message a message which will be prepended to the message produced by
	 * the function itself, and which may be used to provide context. It should
	 * normally end in a ": " or ". " so that the function generate message looks
	 * ok when prepended to it.
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(message + subType + " is not assignable to " + superType);
		}
	}


	/**
	 * Assert a boolean expression, throwing {@code IllegalStateException}
	 * if the test result is {@code false}. Call isTrue if you wish to
	 * throw IllegalArgumentException on an assertion failure.
	 * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalStateException if expression is {@code false}
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * Assert a boolean expression, throwing {@link IllegalStateException}
	 * if the test result is {@code false}.
	 * <p>Call {@link #isTrue(boolean)} if you wish to
	 * throw {@link IllegalArgumentException} on an assertion failure.
	 * <pre class="code">Assert.state(id == null);</pre>
	 * @param expression a boolean expression
	 * @throws IllegalStateException if the supplied expression is {@code false}
	 */
	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

}
