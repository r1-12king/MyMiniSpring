package com.minis.util;

/**
 * 用给定的模式匹配字符串。
 * 模式格式: "xxx*", "*xxx", "*xxx*" 以及 "xxx*yyy"，*代表若干个字符。
 */
public abstract class PatternMatchUtils {

	/**
	 * Match a String against the given pattern, supporting the following simple
	 * pattern styles: "xxx*", "*xxx", "*xxx*" and "xxx*yyy" matches (with an
	 * arbitrary number of pattern parts), as well as direct equality.
	 *
	 * @param pattern the pattern to match against
	 * @param str the String to match
	 * @return whether the String matches the given pattern
	 */
	public static boolean simpleMatch( String pattern,  String str) {
		// 先判断串或者模式不是否为空
		if (pattern == null || str == null) {
			return false;
		}

		// 判断模式是否以*开头
		int firstIndex = pattern.indexOf('*');
		if (firstIndex == -1) {
			return pattern.equals(str);
		}

		// 是否首字符就是*,意味着这个是*XXX格式
		if (firstIndex == 0) {
			// 模式就是*,通配全部串
			if (pattern.length() == 1) {
				return true;
			}
			// 尝试查找下一个*
			int nextIndex = pattern.indexOf('*', 1);
			// 如果没有，判断str是否以pattern.substring(1)结尾
			if (nextIndex == -1) {
				return str.endsWith(pattern.substring(1));
			}
			// 截取两个*之间的部分
			String part = pattern.substring(1, nextIndex);
			if (part.isEmpty()) {
				// 这部分为空，形如**，则移到后面的模式进行匹配
				return simpleMatch(pattern.substring(nextIndex), str);
			}
			int partIndex = str.indexOf(part);
			while (partIndex != -1) {
				//模式串移位到第二个*之后，目标字符串移位到字串之后，递归再进行匹配
				if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
					return true;
				}
				partIndex = str.indexOf(part, partIndex + 1);
			}
			return false;
		}
		// 对不是*开头的模式，前面部分要精确匹配，然后后面的子串重新递归匹配
		return (str.length() >= firstIndex &&
				pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex)) &&
				simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
	}

	/**
	 * Match a String against the given patterns, supporting the following simple
	 * pattern styles: "xxx*", "*xxx", "*xxx*" and "xxx*yyy" matches (with an
	 * arbitrary number of pattern parts), as well as direct equality.
	 * @param patterns the patterns to match against
	 * @param str the String to match
	 * @return whether the String matches any of the given patterns
	 */
	public static boolean simpleMatch( String[] patterns, String str) {
		if (patterns != null) {
			for (String pattern : patterns) {
				if (simpleMatch(pattern, str)) {
					return true;
				}
			}
		}
		return false;
	}

}

