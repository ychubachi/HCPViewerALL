/*
 * IteratorTest.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package hacking.testcodes;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * IteratorTest
 * Hashtableから取得したvaluesのCollectionからvalueをIteratorから消すと
 * Entry自体が消えてしまう仕様を確認するテスト．
 */
public class IteratorTest {

  public static void main(String[] args) {
    new IteratorTest().run();
  }

  public void run() {
    HashMap<String, String> map = new HashMap<String, String>();

    map.put("A", "1");
    map.put("B", "2");
    map.put("C", "3");
    map.put("D", "4");
    map.put("A", "5");

    System.out.println(map);

    Collection<String> values = map.values();

    System.out.println("-----");
    for (Iterator<String> i = values.iterator(); i.hasNext();) {
      String value = i.next();
      System.out.println(value);
    }

    System.out.println("-----");
    for (Iterator<String> i = values.iterator(); i.hasNext();) {
      String value = i.next();
      if (value.equals("5")) {
	i.remove();
	continue;
      }
      System.out.println(value);
    }

    System.out.println(map);
  }

}
