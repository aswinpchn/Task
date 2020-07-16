package com.aswin;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query {

  public static void main(String[] args) {

    System.out.println("Enter the folder name that you want to query the logs for?");

    Scanner scanner = new Scanner(System.in);
    String folderName = scanner.nextLine();


    try (BufferedReader br = new BufferedReader(new FileReader("./" + folderName + "/filename.txt"))) {

      System.out.println("The folder is found, you can now query in the our command tool!");
      System.out.println("You have two options 1. Query, 2. Exit");



      /* Storing the log date in a Map data structure. */
      Map<Long, List> storage = new LinkedHashMap<>();
      br.readLine(); /* Skip the heading line. */
      for (String line = null; (line = br.readLine()) != null;) {

        int k = line.indexOf(" ");
        List<String> list = storage.get(Long.parseLong(line.substring(0, k)));
        if(list != null) {
          list.add(line);
        } else {
          List<String> l = new ArrayList<>();
          l.add(line);
          storage.put(Long.parseLong(line.substring(0, k)), l);
        }
      }




      while(true) {

        System.out.print(">");
        String input = scanner.nextLine();

        long startTime = System.currentTimeMillis();
        String[] splitInput = input.split(" ");

        /* Handle exit command. */
        if(splitInput.length == 1) {
          if(splitInput[0].toLowerCase().equals("exit")) {
            System.out.println("Exiting the console.");
            return;
          } else {
            throw new IllegalArgumentException("Input has one parameter, but it's not 'exit'");
          }
        }




        /* Validating all the parameters for Query command */
        if(splitInput.length != 7) {
          throw new IllegalArgumentException("The input should be of 5 parameters for Query.");
        }

        if(!splitInput[0].toLowerCase().equals("query")) {
          throw new IllegalArgumentException("Query command, should have'query' as first parameter.");
        }

        if(!isIPAddressValid(splitInput[1])) {
          throw new IllegalArgumentException("IP address is invalid.");
        }

        if(!splitInput[2].equals("1") && !splitInput[2].equals("0")) {
          throw new IllegalArgumentException("Cpu ID should be 0 or 1.");
        }

        String startString = splitInput[3] + ' ' + splitInput[4]; /* Time will be of two parts as per the requirements. */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime = LocalDateTime.parse(startString, formatter);

        String endString = splitInput[5] + ' ' +  splitInput[6];
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime endDateTime = LocalDateTime.parse(endString, formatter);

        if(endDateTime.compareTo(startDateTime) < 0)
          throw new IllegalArgumentException("Start date is greater than End date");



        /* Searching the log for CPU usage as per the query parameters. */
        Long start = startDateTime.toEpochSecond(ZoneOffset.UTC);
        Long end = endDateTime.toEpochSecond(ZoneOffset.UTC);

        StringBuilder result = new StringBuilder(""); /* Stores the results. */
        result.append("CPU-" + splitInput[2] + " usage on " + splitInput[1] + "\n");

        for(Long i = start; i <= end ; i += 60) {
          List<String> current = storage.get(i);

          for(int j = 0; j < current.size(); j++) {
            String[] data = current.get(j).split(" ");

            if(data[1].equals(splitInput[1]) && data[2].equals(splitInput[2])) {

              formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
              LocalDateTime d = LocalDateTime.ofEpochSecond(i, 0, ZoneOffset.UTC);

              result.append("( " + d.format(formatter) + ", " + data[3] + "% ), ");
            }
          }
        }

        System.out.println(result.toString()); /* Printing results. */

        long endTime = System.currentTimeMillis();
        System.out.println("This query ran in: " +  (endTime - startTime) / (double)1000 + " seconds."); /* Printing metrics for query timings. */
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (DateTimeParseException e) {
      e.printStackTrace();
    }
  }

  /* Checks if the ip address is valid. */
  private static boolean isIPAddressValid(String s) {
    if(s == null)
      return false;

    final String IPV4_REGEX =
      "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

    Matcher matcher = IPv4_PATTERN.matcher(s);

    return matcher.matches();
  }
}
