package pfu.common.base.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import java.util.List;

/**
 * 字符串工具类。
 *
 * @author chngzhen@outlook.com
 * @since 2019-08-20
 */
public class StringUtils {

	public static void main(String[] args) throws Exception {
		System.out.println(toShortPinyin("出水阀"));
		System.out.println(toShortPinyin("出水阀279"));
		System.out.println(toShortPinyin("出水阀,279"));
		System.out.println(toShortPinyin("出水阀！279"));
	}

	public static String toShortPinyin(String str) throws Exception {
		if (null == str) return null;
		str = str.trim();
		if (0 >= str.length()) return null;
		char[] chs = str.toCharArray();

		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

		StringBuilder r = new StringBuilder();
		for (char ch : chs) {
			String[] pys = PinyinHelper.toHanyuPinyinStringArray(ch, defaultFormat);
			if (null == pys || 0 >= pys.length) {
				if ((ch >= 48 && ch <= 57) || (ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122)) {
					r.append(ch);
				}
			}
			else r.append(pys[0].charAt(0));
		}
		return r.toString();
	}

	public static String list2String(List<Integer> list) {
		if (0 < list.size()) {
			StringBuilder builder = new StringBuilder(256);
			for (Integer item : list) builder.append(",").append(item);
			return builder.length() > 0 ? builder.substring(1) : "";
		}
		return "";
	}

	/**
	 * 判断一组字符串是否均不为空。
	 *
	 * @param strs 字符串组
	 * @return {@code true} 表示没有空值
	 */
	public static boolean areNotEmpty(String... strs) {
		boolean areNotEmpty = true;
		for (int i = strs.length - 1; areNotEmpty && i >= 0; i--) {
			areNotEmpty = !isEmpty(strs[i]);
		}
		return areNotEmpty;
	}

	/**
	 * 判断字符串是否为空。
	 *
	 * @param str 字符串
	 * @return {@code true} 表示为空
	 */
	public static boolean isEmpty(String str) {
		return null == str || str.trim().isEmpty();
	}

	/**
	 * 首字母小写驼峰命名法转化。
	 * <p></p>
	 * 根据非字母字符将字符串分隔成子串数组，每组的首字母大写，其余字母小写。最终字符串的首字母小写。
	 *
	 * @param str 字符串。例如 {@code user_name}
	 * @return 转化后的字符串。例如 {@code userName}
	 * @see #underScoreCase(String)
	 */
	public static String lowerCamelCase(String str) {
		String[] words = str.split("[^A-Za-z$]");
		if (0 == words.length) throw new RuntimeException("字符串必须包含字母");

		StringBuilder newStr = new StringBuilder(words[0].toLowerCase());
		for (int i = 1; i< words.length; i++) {
			if (0 == words[i].length()) continue;
			newStr.append(words[i].substring(0, 1).toUpperCase()).append(words[i].substring(1).toLowerCase());
		}
		return newStr.toString();
	}

	/**
	 * 下划线命名法转化。
	 * <p></p>
	 * 大写字母的前面插入下划线，并将所有字母小写。
	 *
	 * @param str 字符串。例如 {@code userName}
	 * @return 转化后的字符串。例如 {@code user_name}
	 * @see #lowerCamelCase(String)
	 */
	public static String underScoreCase(String str) {
		if (0 == str.length()) throw new RuntimeException("字符串不能为空");

		StringBuilder newStr = new StringBuilder();
		for (int i=0; i<str.length(); i++) {
			String ch = str.substring(i, i+1);
			if (0 == i) {
				newStr.append(ch.toLowerCase());
			} else if (0 <= ch.compareTo("A") && 0 > ch.compareTo("a")) {
				newStr.append("_").append(ch.toLowerCase());
			} else {
				newStr.append(ch);
			}
		}
		return newStr.toString();
	}


}
