/*
 * FileIOTest.java
 * Created on 2010/02/15 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package hacking.testcodes;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * FileIOTest
 */
public class FileIOTest {
  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(
	"src/clib/testcodes/FileIOTest.java"));
    while (br.ready()) {
      System.out.println("!" + br.readLine());
    }
    br.close();
  }
}
