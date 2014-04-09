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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import org.wangk.comper.util.Str;


/**
 * No exception type convert.
 * 
 * why not exception?
 * because exception throwing is just too expensive,
 * when dealing with user input in a web environment, 
 * constant exception become unacceptable.
 * So instead of throwing exceptions every where, 
 * null return value is used to indicate exception/error.
 * 
 * 
 * @author BowenCai
 *
 */
public class Converter {
	
	/**
	 *  exception free string cast
	 *  
	 * @param var
	 * @param type
	 * @return value or null if cast failed
	 */
	@Nullable
	public static<T> Object castStr(String var, Class<T> type) {
		
		var = var.trim();
		if (type == String.class) {
			return var;
			
		} else if (type == byte[].class || type == Byte[].class) {
			return var.getBytes();
		 
		} else if (type == Boolean.class || type == boolean.class) {
			return toBool(var);
			
		} else if (type == Character.class || type == char.class) {
			if (var.length() == 1){
				return var.charAt(0);
			} else {
				return null;
			}
			
		} else if (type == char[].class || type == Character[].class) {
			return var.toCharArray();
			
		} else if (type == Short.class || type == short.class) {
			return toShort(var);
			
		}  else if (type == Integer.class || type == int.class) {
			return toInteger(var);
			
		}  else if (type == Long.class || type == long.class) {
			return toLong(var);
			
		}  else if (type == Float.class || type == float.class) {
			return toFloat(var);
			
		}  else if (type == Double.class || type == double.class) {
			return toDouble(var);
			
		} else if (type == Date.class) {
			return toDate(var);
		} else if (type == java.math.BigDecimal.class) {
			try {
				return new BigDecimal(var);
			} catch (Exception e) {
				return null;
			}
		} else if (type == java.math.BigInteger.class) {
			try {
				return new BigInteger(var);
			} catch (Exception e) {
				return null;
			}
		} else if (type.isEnum()) {
			T[] es = type.getEnumConstants();
			for (T t : es) {
				if (t.toString().equalsIgnoreCase(var)) {
					return t;
				}
			}
			return null;
		} else {
			return var;
		}
	}
	

	
	@Nullable
	public static Number castNumber(Number var, Class<?> type) {
		
		if (var.getClass() == type) {
			return var;
		} else if (type == Short.class || type == short.class) {
			return var.shortValue();
			
		}  else if (type == Integer.class || type == int.class) {
			return var.intValue();
			
		}  else if (type == Long.class || type == long.class) {
			return var.longValue();
			
		}  else if (type == Float.class || type == float.class) {
			return var.floatValue();
			
		}  else if (type == Double.class || type == double.class) {
			return var.doubleValue();
		} else {
			return var;
		}
	}
//	public static final String STR_MAX_SHORT = Integer.toString(Short.MAX_VALUE);
//	public static final String STR_MIN_SHORT = Integer.toString(Short.MIN_VALUE);
//	public static final String STR_MAX_INT = Integer.toString(Integer.MAX_VALUE);
//	public static final String STR_MIN_INT = Integer.toString(Integer.MIN_VALUE);
//	public static final String STR_MAX_LONG = Long.toString(Long.MAX_VALUE);
//	public static final String STR_MIN_LONG = Long.toString(Long.MIN_VALUE);

//-----------------------------------------------------------------------------
//			exception free c-style conversion
//-----------------------------------------------------------------------------
	
	@Nullable
	public static Long toLong(String string) {
		if (string == null) {
			return null;
		}
		string = string.trim();
		long var;
		if (Str.Patterns.INTERGER.matcher(string).matches()) {
			try {
				var = Long.parseLong(string);
 			} catch (Exception e) {
 				return null;
 			}
			return new Long(var);
			
		} else {
			return null;
		}
	}
	@Nullable
	public static Integer toInteger(String string) {
		Long var = toLong(string);
		return var == null ? null : var.intValue();
	}
	@Nullable
	public static Short toShort(String string) {
		Long var = toLong(string);
		return var == null ? null : var.shortValue();
	}

	@Nullable
	public static Double toDouble(String string) {
		if (string == null) {
			return null;
		}
		string = string.trim();
		Double var;
		if (Str.Patterns.FLOAT_NUMBER.matcher(string).matches()) {
			try {
				var = Double.valueOf(string);
 			} catch (Exception e) {
 				return null;
 			}
			return var;
			
		} else {
			return null;
		}
	}
	@Nullable
	public static Float toFloat(String string) {
		Double var = toDouble(string);
		return var == null ? null : var.floatValue();
	}
	
	@Nullable
	public static Boolean toBool(String string) {
		if (string == null) {
			return null;
		}
		string = string.trim();
		
		boolean isTrue = string.equalsIgnoreCase("true") 
				|| string.equalsIgnoreCase("on")
				|| string.equalsIgnoreCase("yes") 
				|| string.equalsIgnoreCase("1");
		
		if (isTrue) {
			return Boolean.TRUE;
			
		} else {
			boolean isFalse = string.equalsIgnoreCase("false") 
					|| string.equalsIgnoreCase("off")
					|| string.equalsIgnoreCase("no") 
					|| string.equalsIgnoreCase("0");
			if (isFalse) {
				return Boolean.FALSE;
			} else {
				return null;
			}
			
		}
	}
	
//	public static boolean isLeapYear(int year) {
//		return ((year & 3) == 0)
//				&& ((year % 100) != 0 || (year % 400) == 0);
//	}
	
//	public static void main(String...a) {
//		
//	}
	/**
	 * cast String to to date(year, month, day), not hour, minutes, or second !
	 * 
	 * <pre>
	 * toDate("2014-2-3") => Mon Feb 03 00:00:00 CST 2014
	 * 
	 * toDate("2014-2") => Sat Feb 01 00:00:00 CST 2014
	 * 
	 * toDate("2014") => Wed Jan 01 00:00:00 CST 2014
	 * 
	 * </pre>
	 * 
	 * @param text
	 * @return null if failed
	 */
	public static Date toDate(String text) {
		if (text == null) {
			return null;
		}
		text = text.trim();
		if (text.length() > 7) {
			if (PTN_FORMAT_DAY.matcher(text).matches()) {
				try {
					return FORMAT_DAY.parse(text);
				} catch (Exception e) {}
			}
		} else if (text.length() > 5) {
			if (PTN_FORMAT_MONTH.matcher(text).matches()) {
				try {
					return FORMAT_MONTH.parse(text);
				} catch (Exception e) {}
			}
		} else if (text.length() > 3) {
			if (PTN_FORMAT_YEAR.matcher(text).matches()) {
				try {
					return FORMAT_YEAR.parse(text);
				} catch (Exception e) {}
			}
		}
		return null;
	}
	
	public static String toSimpleDate(Date date) {
		
		if (date != null) {
			return FORMAT_DAY.format(date);
		} else {
			return null;
		}
	}
	
	private static final Pattern PTN_FORMAT_DAY;
	private static final Pattern PTN_FORMAT_MONTH;
	private static final Pattern PTN_FORMAT_YEAR;

	private static final SimpleDateFormat FORMAT_YEAR;
	private static final SimpleDateFormat FORMAT_MONTH;
	private static final SimpleDateFormat FORMAT_DAY;
	
	
	private static final HashMap<String, Class<?>> TYPE_MAP;
	static {
		PTN_FORMAT_DAY = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");
		PTN_FORMAT_MONTH = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])");
		PTN_FORMAT_YEAR = Pattern.compile("((19|20)\\d\\d)");
		
		FORMAT_YEAR = new SimpleDateFormat("yyyy");
		FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");
		FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		
		
		TYPE_MAP = new HashMap<String, Class<?>>();
		
		TYPE_MAP.put("byte", byte.class);
		TYPE_MAP.put("Byte", Byte.class);
		TYPE_MAP.put("byte[]", byte[].class);
		TYPE_MAP.put("Byte[]", Byte[].class);
		
		TYPE_MAP.put("boolean", boolean.class);
		TYPE_MAP.put("Boolean", Boolean.class);
		TYPE_MAP.put("boolean[]", boolean[].class);
		TYPE_MAP.put("Boolean[]", Boolean[].class);

		TYPE_MAP.put("char", char.class);
		TYPE_MAP.put("Character", Character.class);
		TYPE_MAP.put("char[]", char[].class);
		TYPE_MAP.put("Character[]", Character[].class);
		
		TYPE_MAP.put("short", short.class);
		TYPE_MAP.put("Short", Short.class);
		TYPE_MAP.put("short[]", short[].class);
		TYPE_MAP.put("Short[]", Short[].class);
		
		TYPE_MAP.put("int", int.class);
		TYPE_MAP.put("Integer", Integer.class);
		TYPE_MAP.put("int[]", int[].class);
		TYPE_MAP.put("Integer[]", Integer[].class);
		
		TYPE_MAP.put("long", long.class);
		TYPE_MAP.put("Long", Long.class);
		TYPE_MAP.put("long[]", long[].class);
		TYPE_MAP.put("Long[]", Long[].class);

		TYPE_MAP.put("float", float.class);
		TYPE_MAP.put("Float", Float.class);
		TYPE_MAP.put("float[]", float[].class);
		TYPE_MAP.put("Float[]", Float[].class);
		
		TYPE_MAP.put("double", double.class);
		TYPE_MAP.put("Double", Double.class);
		TYPE_MAP.put("double[]", double[].class);
		TYPE_MAP.put("Double[]", Double[].class);
		
		TYPE_MAP.put("string", String.class);
		TYPE_MAP.put("String", String.class);

		TYPE_MAP.put("enum", Enum.class);
		TYPE_MAP.put("Enum", Enum.class);
		
		TYPE_MAP.put("date", java.util.Date.class);
		TYPE_MAP.put("Date", java.util.Date.class);
		
	}
	

	@Nullable
	public static Class<?> getClass(String typeName) {
		return TYPE_MAP.get(typeName);
	}

	
	private Converter(){}
}
