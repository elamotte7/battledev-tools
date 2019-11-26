/*******
 * Read input from io.in
 * Use: io.out.println to ouput your result to STDOUT.
 * Use: io.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IsoContest {

  /**
   * Proxy to handle I/O
   */
  public static class Printer {

    public InputStream in = System.in;
    public PrintStream out = System.out;
    public PrintStream err = System.err;
  }

  public static Printer io = new Printer();


  public static void main(String[] argv) throws Exception {
    String line;
    Scanner sc = new Scanner(io.in);

    while (sc.hasNextLine()) {
      line = sc.nextLine();
      io.out.println(line);
      /* Lisez les données et effectuez votre traitement */
    }
    /* Vous pouvez aussi effectuer votre traitement une fois que vous avez lu toutes les données.*/
  }
}

