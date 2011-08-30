/*
 * CList.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.collections;

import java.util.ArrayList;
import java.util.List;

import clib.common.string.CStringChopper;

/**
 * CList
 */
public class CList {

	/**
	 * リストをパス区切りの文字列に変換する
	 * 
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String toString(List<?> list, String separator) {
		String str = "";
		for (Object object : list) {
			str += object.toString() + separator;
		}
		return CStringChopper.chopped(str);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> convert(List<?> list,  Class clazz) {
		ArrayList<T> newList = new ArrayList<T>();
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			if (clazz.isInstance(o)) {
				newList.add((T) o);
			}
		}
		return newList;
	}

	public static <T> List<T> createList(T object) {
		ArrayList<T> newList = new ArrayList<T>();
		newList.add(object);
		return newList;
	}

	// 単体テスト
	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<String>();
		System.out.println(CList.toString(list, "/"));
		list.add("A");
		System.out.println(CList.toString(list, "/"));
		list.add("B");
		System.out.println(CList.toString(list, "/"));
		list.add("C");
		System.out.println(CList.toString(list, "/"));
	}
}
