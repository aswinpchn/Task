package com.aswin;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Scanner;

public class Generate {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.println("This is a generation script.");
    System.out.println("Enter the folder name you want to use.");

    String folderName = scanner.nextLine();

    /* Logic to handle folder and file presence scenarios. */
    try {
      File myObj = new File(folderName + "/filename.txt");

      if(!myObj.getParentFile().exists()) {
        myObj.getParentFile().mkdir();
      }

      if (!myObj.exists()) {
        myObj.createNewFile();
        System.out.println("File created: " + myObj.getAbsoluteFile());

        long startTime = System.currentTimeMillis();

        generate(myObj); /* Actual log generation logic. */

        long endTime = System.currentTimeMillis();
        System.out.println("Log generation ran in: " +  (endTime - startTime) / (double)1000 + " seconds."); /* Printing metrics for query timings. */

      } else {
        System.out.println("File already exists. You can try a different folder name");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    System.out.println("Folder is created");

  }

  /* Log generation logic, Input is a file object */
  private static void generate(File myObj) {

    try {
      FileWriter fr = new FileWriter(myObj, true);

      fr.write("TIMESTAMP    IP           CPU_ID    CPU_USAGE \n");

      LocalDateTime d = LocalDateTime.of(2014, 10, 31, 0, 0);

      while(d.isBefore(LocalDateTime.of(2014, 11, 1, 0, 0))) {

        for(int i = 0; i < 1000; i++) {

          String ts = String.valueOf(d.toEpochSecond(ZoneOffset.UTC));
          String ip = ipGenerator(i);

          for(int j = 0; j < 2; j++) {

            String id = String.valueOf(j);
            String usage = String.valueOf((int)(Math.random() * 100));

            StringBuilder sb = new StringBuilder();
            sb.append(ts + ' ' + ip + ' ' + id + ' ' + usage + '\n');

            fr.append(sb.toString());
          }
        }

        d = d.plusMinutes(1);
      }

      fr.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static String ipGenerator(int i) {

    int[] ip = new int[] {192, 168, 0, 0};

    if(i <= 255 ) {
      ip[3] = i;
    } else if(i <= 511) {
      ip[2] = 1;
      ip[3] = i % 256;
    } else if(i <= 767) {
      ip[2] = 2;
      ip[3] = i % 256;
    } else {
      ip[2] = 3;
      ip[3] = i % 256;
    }

    return new String(String.valueOf(ip[0]) + '.' + String.valueOf(ip[1]) + '.' + String.valueOf(ip[2]) + '.' + String.valueOf(ip[3]));
  }
}
