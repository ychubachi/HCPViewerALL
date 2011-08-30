package clib.common.string;

public class CStringMatcher {

	/**
	 * @__ <Javaの仕様>
	 * @__ ^ 行の先頭
	 * @__$ 行の末尾
	 * @__. 改行以外の任意の１文字
	 * @__[] []でくくられた中にある任意の１文字
	 * @__[^] []でくくられた中にない任意の１文字
	 * @__* 直前の文字の０個以上の並び
	 * @__+ 直前の文字の１個以上の並び
	 * @__? 直前の文字が０個または１個
	 * @__{a 直前の文字のa個の並び
	 * @__{a, 直前の文字のa個以上の並び
	 * @__{a,b 直前の文字のa個以上、b個以下の並び
	 * @__| ２者択一の演算子
	 */
	public static boolean wildcardMatches(String target, String pattern) {
		String regex = pattern;
		regex = regex.replaceAll("[.]", "[.]");
		regex = regex.replaceAll("[*]", ".*");
		return target.matches(regex);
	}

	public static void main(String[] args) {
		test("a.doc", "*.doc", true);
		test("b.doc", "*.doc", true);
		test(".doc", "*.doc", true);
		test("a.do", "*.doc", false);
		test("doc.do", "*.doc", false);
	}

	public static void test(String text, String pattern, boolean expected) {
		if (CStringMatcher.wildcardMatches(text, pattern) == expected) {
			System.out.println("PASS");
		} else {
			System.out.println("FAIL");
		}
	}
}
