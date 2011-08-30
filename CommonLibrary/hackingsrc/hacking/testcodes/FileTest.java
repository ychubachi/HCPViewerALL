/*
 * FileTest.java
 * Created on 2010/02/13 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package hacking.testcodes;

import java.io.File;

import clib.common.filesystem.CFileSystem;
import clib.common.filesystem.CPath;

/**
 * FileTest
 */
public class FileTest {

  /**
   * @param args
   */
  public static void main(String[] args) {
    File file = new File("");
    System.out.println(file.getPath());
    
    File file2 = new File("a/b");
    System.out.println(file2.getPath());

    File root = new File("/");
    System.out.println(root.getAbsolutePath());

    CPath path = new CPath(new File("").getAbsolutePath());
    System.out.println(path.getParentPath());

    CPath path2 = new CPath("/");
    System.out.println(path2.toString());
    
    CPath path3 = CFileSystem.getExecuteDirectory().getAbsolutePath();
    System.out.println(path3.toString());
  }

}
