/*******
 * Read input from io.in
 * Use: io.out.println to ouput your result to STDOUT.
 * Use: io.err.println to ouput debugging information to STDERR.
 * ***/
package com.isograd.exercise;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
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
    sc.nextInt();

    int bestSum = 0;
    while (sc.hasNextLine()) {
      line = sc.nextLine();
      String[] parts = line.split(" ");
      double sum = Arrays.stream(parts)
          .filter(s -> !s.isEmpty())
          .map(Integer::parseInt)
          .reduce(0, Integer::sum).doubleValue() / ((double) parts.length);
      bestSum = Math.max((int) Math.ceil(sum), bestSum);
      /* Lisez les données et effectuez votre traitement */
    }
    io.out.println(bestSum);
    /* Vous pouvez aussi effectuer votre traitement une fois que vous avez lu toutes les données.*/
  }
}

