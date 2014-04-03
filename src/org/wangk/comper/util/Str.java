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
package org.wangk.comper.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;


/**
 * 
 * @author BowenCai
 *
 */
public class Str {
	
	public static class Patterns{

		public static final Pattern POST_URI_TITLE = Pattern.compile(
				"^[A-Za-z0-9_.-]{3,200}$");

		public static final Pattern ENTITY_FIELD_NAME = Pattern.compile(
				"^[A-Za-z][A-Za-z0-9_]{3,500}$");
	
		public static final Pattern URL = Pattern.compile(
				"^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
				Pattern.CASE_INSENSITIVE);

		public static final Pattern ASCII = Pattern.compile("\\A\\p{ASCII}*\\z");
		
		public static final Pattern PART_URI = Pattern.compile(
				"^/[\\w\\-\\.~/_=#]{1,64}$");

		public static final Pattern REST_URI = Pattern.compile(
				"^/[\\w\\-\\.~/_=#{}:]{1,64}$");
		public static final Pattern COOKIE_NAME = Pattern.compile(
			"^(([\\w\\-]+|\\{([a-zA-Z][\\w]*)\\})(;*)/?)+(\\.\\w+$)?|^/$");
		
		/**
		 * from Struts2
		 */
		public static final Pattern EMAIL = Pattern.compile(
				"\\b^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*"
				+"@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$\\b"
		);

		public static final Pattern INTERGER = Pattern.compile("(-?[0-9]*)");
		
		public static final Pattern FLOAT_NUMBER = Pattern.compile(
				"(([1-9][0-9]*\\.?[0-9]*)|(\\.[0-9]+))([Ee][+-]?[0-9]+)?");

		private Patterns(){}
	}

//	public static void main(String[] args) {
//		System.out.println(Patterns.INTERGER.matcher(null).matches());
//	}
	
	public static class Utils {
	
		/**
		 * String will be trimmed
		 * @param s
		 * @return s != null && s.trim().length() > 0;
		 */
		public static boolean notBlank(final String s) {
			if (s != null) {
				
				int right = s.length();
				int left = 0;
				while (left < right && s.charAt(left) <= ' ') {
					left++;
				}
				while (left < right && s.charAt(right - 1) <= ' ') {
					right--;
				}
				return left != right;
			}
			return false;
 		}

		
	    public static Locale parseLocale(String str) {
	        if (str == null) {
	            return null;
	        }
	        if (str.contains("#")) { // LANG-879 - Cannot handle Java 7 script & extensions
	        	return null;
	        }
	        final int len = str.length();
	        if (len < 2) {
	        	return null;
	        }
	        final char ch0 = str.charAt(0);
	        if (ch0 == '_') {
	            if (len < 3) {
	            	return null;
	            }
	            final char ch1 = str.charAt(1);
	            final char ch2 = str.charAt(2);
	            if (!Character.isUpperCase(ch1) || !Character.isUpperCase(ch2)) {
	            	return null;
	            }
	            if (len == 3) {
	            	return null;
	            }
	            if (len < 5) {
	            	return null;
	            }
	            if (str.charAt(3) != '_') {
	            	return null;
	            }
	            return new Locale("", str.substring(1, 3), str.substring(4));
	        }
	        final char ch1 = str.charAt(1);
	        if (!Character.isLowerCase(ch0) || !Character.isLowerCase(ch1)) {
	        	return null;
	        }
	        if (len == 2) {
	            return new Locale(str);
	        }
	        if (len < 5) {
	        	return null;
	        }
	        if (str.charAt(2) != '_') {
	        	return null;
	        }
	        final char ch3 = str.charAt(3);
	        if (ch3 == '_') {
	            return new Locale(str.substring(0, 2), "", str.substring(4));
	        }
	        final char ch4 = str.charAt(4);
	        if (!Character.isUpperCase(ch3) || !Character.isUpperCase(ch4)) {
	        	return null;
	        }
	        if (len == 5) {
	            return new Locale(str.substring(0, 2), str.substring(3, 5));
	        }
	        if (len < 7) {
	        	return null;
	        }
	        if (str.charAt(5) != '_') {
	        	return null;
	        }
	        return new Locale(str.substring(0, 2), str.substring(3, 5), str.substring(6));
	    }
	}
	
	public static final class date {
		private static final WeakCache<Date, String> CACHE_ISO8601 = new WeakCache<Date, String>();
		private static final SimpleDateFormat DATE_FORMAT_ISO8601;
		static {
			DATE_FORMAT_ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			DATE_FORMAT_ISO8601.setTimeZone(TimeZone.getTimeZone("UTC"));
		}
		public static String iso8601(Date date) {
			String string = CACHE_ISO8601.get(date);
			if (string == null) {
				synchronized (CACHE_ISO8601) {
					string = CACHE_ISO8601.get(date);
					if (string == null) {
						string = DATE_FORMAT_ISO8601.format(date);
						CACHE_ISO8601.put(date, string);
					}
				}
			}
			return string;
		}
		
		private static final WeakCache<Date, String> CACHE_RFC822 = new WeakCache<Date, String>();
		private static final SimpleDateFormat RFC822_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
		
		public static String rfc822(Date date) {
			String string = CACHE_RFC822.get(date);
			if (string == null) {
				synchronized (CACHE_RFC822) {
					string = CACHE_RFC822.get(date);
					if (string == null) {
						string = RFC822_FORMAT.format(date);
						CACHE_RFC822.put(date, string);
					}
				}
			}
			return string;
		}
	}
	
	private Str(){}

}
