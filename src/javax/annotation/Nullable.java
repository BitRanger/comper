/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributor:
 *     WangKang. - initial API and implementation
 ******************************************************************************/
package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 注明某值可能为空
 * @author BowenCai
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Nullable {

}
