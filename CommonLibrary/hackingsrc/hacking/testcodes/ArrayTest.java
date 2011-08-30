/*
 * ArrayTest.java
 * Created on 2010/02/13 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package hacking.testcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayTest
 */
public class ArrayTest {

  /**
   * リストの最後の要素を取り除いたListを返す
   * (ArrayList専用)
   * 
   * @param <T>
   * @param list
   * @return
   */
  public static <T> List<T> chopped(List<T> list) {
    List<T> copyPath = new ArrayList<T>(list);
    int index = copyPath.size() - 1;
    if (index >= 0) {
      copyPath.remove(index);
    }
    return copyPath;
  }

  // 単体テスト
  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    System.out.println(list);

    // choppedのテスト
    List<String> chopped = ArrayTest.chopped(list);
    System.out.println(chopped);
    List<String> chopped2 = ArrayTest.chopped(chopped);
    System.out.println(chopped2);
    List<String> chopped3 = ArrayTest.chopped(chopped2);
    System.out.println(chopped3);
  }
}
