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
   * ���X�g�̍Ō�̗v�f����菜����List��Ԃ�
   * (ArrayList��p)
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

  // �P�̃e�X�g
  public static void main(String[] args) {
    ArrayList<String> list = new ArrayList<String>();
    list.add("A");
    list.add("B");
    System.out.println(list);

    // chopped�̃e�X�g
    List<String> chopped = ArrayTest.chopped(list);
    System.out.println(chopped);
    List<String> chopped2 = ArrayTest.chopped(chopped);
    System.out.println(chopped2);
    List<String> chopped3 = ArrayTest.chopped(chopped2);
    System.out.println(chopped3);
  }
}
