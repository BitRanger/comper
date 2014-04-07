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
package javax.annotation.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation can be applied to the value() element of an annotation that
 * is annotated as a TypeQualifier.
 * 
 * For example, the following defines a type qualifier such that if you know a
 * value is {@literal @Foo(1)}, then the value cannot be {@literal @Foo(2)} or {{@literal @Foo(3)}.
 * 
 * <code>
 * @TypeQualifier @interface Foo {
 *     @Exclusive int value();
 *     }
 *  </code>
 * 
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Exclusive {

}
