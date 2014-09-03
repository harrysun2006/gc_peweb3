/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.util.CollectionUtils;

/**
 * <a href="StringUtil.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * 
 */
public class StringUtil {

	public static final String AMPERSAND = "&";

	public static final String AMPERSAND_ENCODED = "&amp;";

	public static final String APOSTROPHE = "'";

	public static final String AT = "@";

	public static final String BACK_SLASH = "\\";

	public static final String BETWEEN = "BETWEEN";

	public static final String BLANK = "";

	public static final String CDATA_OPEN = "<![CDATA[";

	public static final String CDATA_CLOSE = "]]>";

	public static final String CLOSE_BRACKET = "]";

	public static final String CLOSE_CURLY_BRACE = "}";

	public static final String CLOSE_PARENTHESIS = ")";

	public static final String COLON = ":";

	public static final String COMMA = ",";

	public static final String COMMA_AND_SPACE = ", ";

	public static final String DASH = "-";

	public static final String DOUBLE_APOSTROPHE = "''";

	public static final String DOUBLE_CLOSE_BRACKET = "]]";

	public static final String DOUBLE_CLOSE_CURLY_BRACE = "}}";

	public static final String DOUBLE_OPEN_BRACKET = "[[";

	public static final String DOUBLE_OPEN_CURLY_BRACE = "{{";

	public static final String DOUBLE_SLASH = "//";

	public static final String EQUAL = "=";

	public static final String GREATER_THAN = ">";

	public static final String GREATER_THAN_OR_EQUAL = ">=";

	public static final String FALSE = "false";

	public static final String FORWARD_SLASH = "/";

	public static final String FOUR_SPACES = "    ";

	public static final String IS_NOT_NULL = "IS NOT NULL";

	public static final String IS_NULL = "IS NULL";

	public static final String LESS_THAN = "<";

	public static final String LESS_THAN_OR_EQUAL = "<=";

	public static final String LIKE = "LIKE";

	public static final String MINUS = "-";

	public static final String NBSP = "&nbsp;";

	public static final String NEW_LINE = "\n";

	public static final String NOT_EQUAL = "!=";

	public static final String NOT_LIKE = "NOT LIKE";

	public static final String NULL = "null";

	public static final String OPEN_BRACKET = "[";

	public static final String OPEN_CURLY_BRACE = "{";

	public static final String OPEN_PARENTHESIS = "(";

	public static final String PERCENT = "%";

	public static final String PERIOD = ".";

	public static final String PIPE = "|";

	public static final String PLUS = "+";

	public static final String POUND = "#";

	public static final String QUESTION = "?";

	public static final String QUOTE = "\"";

	public static final String RETURN = "\r";

	public static final String RETURN_NEW_LINE = "\r\n";

	public static final String SEMICOLON = ";";

	public static final String SLASH = FORWARD_SLASH;

	public static final String SPACE = " ";

	public static final String STAR = "*";

	public static final String TAB = "\t";

	public static final String TILDE = "~";

	public static final String TRUE = "true";

	public static final String UNDERLINE = "_";

	public static final String UTC = "UTC";

	public static final String UTF8 = "UTF-8";

	public static String add(String s, String add) {
		return add(s, add, COMMA);
	}

	public static String add(String s, String add, String delimiter) {
		return add(s, add, delimiter, false);
	}

	public static String add(String s, String add, String delimiter,
			boolean allowDuplicates) {

		if ((add == null) || (delimiter == null)) {
			return null;
		}

		if (s == null) {
			s = BLANK;
		}

		if (allowDuplicates || !contains(s, add, delimiter)) {
			StringBuffer sm = new StringBuffer();

			sm.append(s);

			if (isBlank(s) || s.endsWith(delimiter)) {
				sm.append(add);
				sm.append(delimiter);
			} else {
				sm.append(delimiter);
				sm.append(add);
				sm.append(delimiter);
			}

			s = sm.toString();
		}

		return s;
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuffer sm = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(
					1);

			if (hex.length() < 2) {
				sm.append("0");
			}

			sm.append(hex);
		}

		return sm.toString();
	}

	public static boolean contains(String s, String text) {
		return contains(s, text, COMMA);
	}

	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		StringBuffer sm = null;

		if (!s.endsWith(delimiter)) {
			sm = new StringBuffer();

			sm.append(s);
			sm.append(delimiter);

			s = sm.toString();
		}

		sm = new StringBuffer();

		sm.append(delimiter);
		sm.append(text);
		sm.append(delimiter);

		String dtd = sm.toString();

		int pos = s.indexOf(dtd);

		if (pos == -1) {
			sm = new StringBuffer();

			sm.append(text);
			sm.append(delimiter);

			String td = sm.toString();

			if (s.startsWith(td)) {
				return true;
			}

			return false;
		}

		return true;
	}

	public static int count(String s, String text) {
		if ((s == null) || (text == null)) {
			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());

			count++;
		}

		return count;
	}

	public static boolean endsWith(String s, char end) {
		return endsWith(s, (new Character(end)).toString());
	}

	public static boolean endsWith(String s, String end) {
		if ((s == null) || (end == null)) {
			return false;
		}

		if (end.length() > s.length()) {
			return false;
		}

		String temp = s.substring(s.length() - end.length(), s.length());

		if (temp.equalsIgnoreCase(end)) {
			return true;
		} else {
			return false;
		}
	}

	public static String extractChars(String s) {
		if (s == null) {
			return BLANK;
		}

		StringBuffer sm = new StringBuffer();

		char[] c = s.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (isChar(c[i])) {
				sm.append(c[i]);
			}
		}

		return sm.toString();
	}

	public static String extractDigits(String s) {
		if (s == null) {
			return BLANK;
		}

		StringBuffer sm = new StringBuffer();

		char[] c = s.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (isDigit(c[i])) {
				sm.append(c[i]);
			}
		}

		return sm.toString();
	}

	public static String extractFirst(String s, String delimiter) {
		if (s == null) {
			return null;
		} else {
			String[] array = split(s, delimiter);

			if (array.length > 0) {
				return array[0];
			} else {
				return null;
			}
		}
	}

	public static String extractLast(String s, String delimiter) {
		if (s == null) {
			return null;
		} else {
			String[] array = split(s, delimiter);

			if (array.length > 0) {
				return array[array.length - 1];
			} else {
				return null;
			}
		}
	}

	public static String highlight(String s, String keywords) {
		return highlight(s, keywords, "<b>", "</b>");
	}

	public static String highlight(String s, String keywords, String highlight1,
			String highlight2) {

		if (s == null) {
			return null;
		}

		// The problem with using a regexp is that it searches the text in a
		// case insenstive manner but doens't replace the text in a case
		// insenstive manner. So the search results actually get messed up. The
		// best way is to actually parse the results.

		// return s.replaceAll(
		// "(?i)" + keywords, highlight1 + keywords + highlight2);

		StringBuffer sm = new StringBuffer(SPACE);

		StringTokenizer st = new StringTokenizer(s);

		while (st.hasMoreTokens()) {
			String token = st.nextToken();

			if (token.equalsIgnoreCase(keywords)) {
				sm.append(highlight1);
				sm.append(token);
				sm.append(highlight2);
			} else {
				sm.append(token);
			}

			if (st.hasMoreTokens()) {
				sm.append(SPACE);
			}
		}

		return sm.toString();
	}

	public static String lowerCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toLowerCase();
		}
	}

	public static String merge(List list) {
		return merge(list, COMMA);
	}

	public static String merge(List list, String delimiter) {
		return merge((Object[]) list.toArray(new Object[list.size()]), delimiter);
	}

	public static String merge(Object[] array) {
		return merge(array, COMMA);
	}

	public static String merge(Object[] array, String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sm = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			sm.append(String.valueOf(array[i]).trim());

			if ((i + 1) != array.length) {
				sm.append(delimiter);
			}
		}

		return sm.toString();
	}

	public static String read(ClassLoader classLoader, String name)
			throws IOException {

		return read(classLoader, name, false);
	}

	public static String read(ClassLoader classLoader, String name, boolean all)
			throws IOException {

		if (all) {
			StringBuffer sm = new StringBuffer();

			Enumeration enu = classLoader.getResources(name);

			while (enu.hasMoreElements()) {
				URL url = (URL) enu.nextElement();

				InputStream is = url.openStream();

				String s = read(is);

				if (s != null) {
					sm.append(s);
					sm.append(NEW_LINE);
				}

				is.close();
			}

			return sm.toString().trim();
		} else {
			InputStream is = classLoader.getResourceAsStream(name);

			String s = read(is);

			is.close();

			return s;
		}
	}

	public static String read(InputStream is) throws IOException {
		StringBuffer sm = new StringBuffer();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		String line = null;

		while ((line = br.readLine()) != null) {
			sm.append(line).append('\n');
		}

		br.close();

		return sm.toString().trim();
	}

	public static String remove(String s, String remove) {
		return remove(s, remove, COMMA);
	}

	public static String remove(String s, String remove, String delimiter) {
		if ((s == null) || (remove == null) || (delimiter == null)) {
			return null;
		}

		if (!isBlank(s) && !s.endsWith(delimiter)) {
			s += delimiter;
		}

		StringBuffer sm = new StringBuffer();

		sm.append(delimiter);
		sm.append(remove);
		sm.append(delimiter);

		String drd = sm.toString();

		sm = new StringBuffer();

		sm.append(remove);
		sm.append(delimiter);

		String rd = sm.toString();

		while (contains(s, remove, delimiter)) {
			int pos = s.indexOf(drd);

			if (pos == -1) {
				if (s.startsWith(rd)) {
					int x = remove.length() + delimiter.length();
					int y = s.length();

					s = s.substring(x, y);
				}
			} else {
				int x = pos + remove.length() + delimiter.length();
				int y = s.length();

				sm = new StringBuffer();

				sm.append(s.substring(0, pos));
				sm.append(s.substring(x, y));

				s = sm.toString();
			}
		}

		return s;
	}

	public static String replace(String s, char oldSub, char newSub) {
		return replace(s, oldSub, new Character(newSub).toString());
	}

	public static String replace(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		StringBuffer sm = new StringBuffer();

		char[] c = s.toCharArray();

		for (int i = 0; i < c.length; i++) {
			if (c[i] == oldSub) {
				sm.append(newSub);
			} else {
				sm.append(c[i]);
			}
		}

		return sm.toString();
	}

	public static String replace(String s, String oldSub, String newSub) {
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		int y = s.indexOf(oldSub);

		if (y >= 0) {
			StringBuffer sm = new StringBuffer();

			int length = oldSub.length();
			int x = 0;

			while (x <= y) {
				sm.append(s.substring(x, y));
				sm.append(newSub);
				x = y + length;
				y = s.indexOf(oldSub, x);
			}

			sm.append(s.substring(x));

			return sm.toString();
		} else {
			return s;
		}
	}

	public static String replace(String s, String[] oldSubs, String[] newSubs) {
		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	/**
	 * Returns a string with replaced values. This method will replace all text in
	 * the given string, between the beginning and ending delimiter, with new
	 * values found in the given map. For example, if the string contained the
	 * text <code>[$HELLO$]</code>, and the beginning delimiter was
	 * <code>[$]</code>, and the ending delimiter was <code>$]</code>, and
	 * the values map had a key of <code>HELLO</code> that mapped to
	 * <code>WORLD</code>, then the replaced string will contain the text
	 * <code>[$WORLD$]</code>.
	 * 
	 * @param s
	 *          the original string
	 * @param begin
	 *          the beginning delimiter
	 * @param end
	 *          the ending delimiter
	 * @param values
	 *          a map of old and new values
	 * @return a string with replaced values
	 */
	public static String replaceValues(String s, String begin, String end,
			Map values) {

		if ((s == null) || (begin == null) || (end == null) || (values == null)
				|| (values.size() == 0)) {

			return s;
		}

		StringBuffer sm = new StringBuffer(s.length());

		int pos = 0;

		while (true) {
			int x = s.indexOf(begin, pos);
			int y = s.indexOf(end, x + begin.length());

			if ((x == -1) || (y == -1)) {
				sm.append(s.substring(pos, s.length()));

				break;
			} else {
				sm.append(s.substring(pos, x + begin.length()));

				String oldValue = s.substring(x + begin.length(), y);

				String newValue = (String) values.get(oldValue);

				if (newValue == null) {
					newValue = oldValue;
				}

				sm.append(newValue);

				pos = y;
			}
		}

		return sm.toString();
	}

	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] c = s.toCharArray();
		char[] reverse = new char[c.length];

		for (int i = 0; i < c.length; i++) {
			reverse[i] = c[c.length - i - 1];
		}

		return new String(reverse);
	}

	public static String shorten(String s) {
		return shorten(s, 20);
	}

	public static String shorten(String s, int length) {
		return shorten(s, length, "...");
	}

	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	public static String shorten(String s, int length, String suffix) {
		if (s == null || suffix == null) {
			return null;
		}

		if (s.length() > length) {
			for (int j = length; j >= 0; j--) {
				if (Character.isWhitespace(s.charAt(j))) {
					length = j;

					break;
				}
			}

			StringBuffer sm = new StringBuffer();

			sm.append(s.substring(0, length));
			sm.append(suffix);

			s = sm.toString();
		}

		return s;
	}

	public static String[] split(String s) {
		return split(s, COMMA);
	}

	public static String[] split(String s, String delimiter) {
		if (s == null || delimiter == null) {
			return new String[0];
		}

		s = s.trim();

		if (!s.endsWith(delimiter)) {
			StringBuffer sm = new StringBuffer();

			sm.append(s);
			sm.append(delimiter);

			s = sm.toString();
		}

		if (s.equals(delimiter)) {
			return new String[0];
		}

		List nodeValues = new ArrayList();

		if (delimiter.equals("\n") || delimiter.equals("\r")) {
			try {
				BufferedReader br = new BufferedReader(new StringReader(s));

				String line = null;

				while ((line = br.readLine()) != null) {
					nodeValues.add(line);
				}

				br.close();
			} catch (IOException ioe) {
			}
		} else {
			int offset = 0;
			int pos = s.indexOf(delimiter, offset);

			while (pos != -1) {
				nodeValues.add(new String(s.substring(offset, pos)));

				offset = pos + delimiter.length();
				pos = s.indexOf(delimiter, offset);
			}
		}

		return (String[]) nodeValues.toArray(new String[nodeValues.size()]);
	}

	public static boolean[] split(String s, boolean x) {
		return split(s, COMMA, x);
	}

	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static double[] split(String s, double x) {
		return split(s, COMMA, x);
	}

	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static float[] split(String s, float x) {
		return split(s, COMMA, x);
	}

	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static int[] split(String s, int x) {
		return split(s, COMMA, x);
	}

	public static int[] split(String s, String delimiter, int x) {
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = x;

			try {
				value = Integer.parseInt(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static long[] split(String s, long x) {
		return split(s, COMMA, x);
	}

	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static short[] split(String s, short x) {
		return split(s, COMMA, x);
	}

	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	public static boolean startsWith(String s, String start) {
		if ((s == null) || (start == null)) {
			return false;
		}

		if (start.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, start.length());

		if (temp.equalsIgnoreCase(start)) {
			return true;
		} else {
			return false;
		}
	}

	public static String trim(String s) {
		return trim(s, null);
	}

	public static String trim(String s, char c) {
		return trim(s, new char[] { c });
	}

	public static String trim(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] charArray = s.toCharArray();

		int len = charArray.length;

		int x = 0;
		int y = charArray.length;

		for (int i = 0; i < len; i++) {
			char c = charArray[i];

			if (_isTrimable(c, exceptions)) {
				x = i + 1;
			} else {
				break;
			}
		}

		for (int i = len - 1; i >= 0; i--) {
			char c = charArray[i];

			if (_isTrimable(c, exceptions)) {
				y = i;
			} else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		} else {
			return s;
		}
	}

	public static String trimLeading(String s) {
		return trimLeading(s, null);
	}

	public static String trimLeading(String s, char c) {
		return trimLeading(s, new char[] { c });
	}

	public static String trimLeading(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] charArray = s.toCharArray();

		int len = charArray.length;

		int x = 0;
		int y = charArray.length;

		for (int i = 0; i < len; i++) {
			char c = charArray[i];

			if (_isTrimable(c, exceptions)) {
				x = i + 1;
			} else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		} else {
			return s;
		}
	}

	public static String trimTrailing(String s) {
		return trimTrailing(s, null);
	}

	public static String trimTrailing(String s, char c) {
		return trimTrailing(s, new char[] { c });
	}

	public static String trimTrailing(String s, char[] exceptions) {
		if (s == null) {
			return null;
		}

		char[] charArray = s.toCharArray();

		int len = charArray.length;

		int x = 0;
		int y = charArray.length;

		for (int i = len - 1; i >= 0; i--) {
			char c = charArray[i];

			if (_isTrimable(c, exceptions)) {
				y = i;
			} else {
				break;
			}
		}

		if ((x != 0) || (y != len)) {
			return s.substring(x, y);
		} else {
			return s;
		}
	}

	public static String upperCase(String s) {
		if (s == null) {
			return null;
		} else {
			return s.toUpperCase();
		}
	}

	public static String wrap(String text) {
		return wrap(text, 80, "\n");
	}

	public static String wrap(String text, int width, String lineSeparator) {
		if (text == null) {
			return null;
		}

		StringBuffer sm = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new StringReader(text));

			String s = BLANK;

			while ((s = br.readLine()) != null) {
				if (s.length() == 0) {
					sm.append(lineSeparator);
				} else {
					String[] tokens = s.split(SPACE);
					boolean firstWord = true;
					int curLineLength = 0;

					for (int i = 0; i < tokens.length; i++) {
						if (!firstWord) {
							sm.append(SPACE);
							curLineLength++;
						}

						if (firstWord) {
							sm.append(lineSeparator);
						}

						sm.append(tokens[i]);

						curLineLength += tokens[i].length();

						if (curLineLength >= width) {
							firstWord = true;
							curLineLength = 0;
						} else {
							firstWord = false;
						}
					}
				}
			}
		} catch (IOException ioe) {
		}

		return sm.toString();
	}

	private static boolean _isTrimable(char c, char[] exceptions) {
		if ((exceptions != null) && (exceptions.length > 0)) {
			for (int i = 0; i < exceptions.length; i++) {
				if (c == exceptions[i]) {
					return false;
				}
			}
		}

		return Character.isWhitespace(c);
	}

	public static boolean isBlank(String text) {
		return (text == null) || text.trim().length() <= 0;
	}

	public static boolean isChar(char c) {
		return Character.isLetter(c);
	}

	public static boolean isChar(String s) {
		if (isBlank(s)) {
			return false;
		}
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (!isChar(c[i])) {
				return false;
			}
		}
		return true;
	}

	public static boolean isDigit(char c) {
		int x = (int) c;
		if ((x >= 48) && (x <= 57)) {
			return true;
		}
		return false;
	}

	public static boolean isDigit(String s) {
		if (isBlank(s)) {
			return false;
		}
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (!isDigit(c[i])) {
				return false;
			}
		}
		return true;
	}

	public static String left(String s, int len) {
		if (s == null)
			return null;
		if (len > s.length())
			len = s.length();
		return s.substring(0, len);
	}

	public static String right(String s, int len) {
		if (s == null)
			return null;
		if (len > s.length())
			len = s.length();
		return s.substring(s.length() - len, s.length());
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for <code>toString()</code> implementations.
	 * @param coll the Collection to display
	 * @param delim the delimiter to use (probably a ",")
	 * @param prefix the String to start each element with
	 * @param suffix the String to end each element with
	 * @return the delimited String
	 */
	public static String collectionToDelimitedString(Collection coll, String delim, String prefix, String suffix) {
		if (CollectionUtils.isEmpty(coll)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Iterator it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a Collection as a delimited (e.g. CSV)
	 * String. E.g. useful for <code>toString()</code> implementations.
	 * @param coll the Collection to display
	 * @param delim the delimiter to use (probably a ",")
	 * @return the delimited String
	 */
	public static String collectionToDelimitedString(Collection coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}

	/**
	 * Convenience method to return a Collection as a CSV String.
	 * E.g. useful for <code>toString()</code> implementations.
	 * @param coll the Collection to display
	 * @return the delimited String
	 */
	public static String collectionToCommaDelimitedString(Collection coll) {
		return collectionToDelimitedString(coll, ",");
	}
	/**
	 * 判断两个字符串是否相等
	 * @param value
	 * @param find
	 * @return
	 */
	public static boolean equals(String value,String find){
		if(value == null && find == null)
			return true;
		else if(value!=null && value.equals(find))
			return true;
		else
			return false;
	}

}