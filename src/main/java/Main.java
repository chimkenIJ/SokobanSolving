import java.io.*;

import java.util.Scanner;


public class Main {
   public static void main(String[] args) throws IOException {
       String[][] arr = {
               {"        ####"},
               {"########  ##"},
               {"#          ###"},
               {"# @$$ ##   ..#"},
               {"# $$   ##  ..#"},
               {"#         ####"},
               {"###########   "}};
     /*
     #   (hash)      Wall
     .   (period)    Empty goal
     @   (at)        Player on floor
     +   (plus)      Player on goal
     $   (dollar)    Box on floor
     *   (asterisk)  Box on goal
     */

     String easy1 = 
       "5         \n" +
       "##########\n" + 
       "#        #\n" + 
       "#  $  +  #\n" + 
       "#        #\n" + 
       "##########";


     String easy2 = 
        "1         \n" +
        "#####\n" + 
        "#.$@#\n" + 
        "#####";

     


     String[] split = easy1.split("\n");
     String [][] secondSplit = new String[split.length][split[1].length()];
     for(int i = 0; i<split.length; i++) {
       secondSplit[i] = split[i].split("");
     }
     String[][] first = removeLine(secondSplit);

     int playerRow = getPlayer(first,"row");
     int playerCol = getPlayer(first,"col");

     State start = new State(null, first, Integer.parseInt(secondSplit[0][0]), playerRow, playerCol);
     System.out.println("Player Coordinates: " + start.pR + ", " + start.pC);
      System.out.println("--");
 start.print(start.board);
      System.out.println("--");



     while(!(start.isGoal())) {
        Scanner keyboard = new Scanner(System.in);
        String ans = keyboard.nextLine();
        start.applyMove(ans);
        System.out.println("\nPlayer coordinates: " + 
                          start.pR + ", " + start.pC);
        System.out.println("--");
        start.print(start.board);
        System.out.println("--");
     }
     System.out.println("\nLevel finished, with " + start.counter + 
                        isPlural(start.counter, "move") + "! Least moves needed" + isPlural(start.stepsNeeded, "is") + start.stepsNeeded + isPlural(start.stepsNeeded, "move") + ".");
     

   }
public static String isPlural(int num, String str) {
  if(str.equals("is")) {
    if (num>1) {
      return " are ";
    }
    else {
      return " is ";
    }
  }
  if (num>1) {
    return " " + str + "s";
  }
return " " + str;
}
  
  public static int getPlayer(String[][] arr, String which) {
    for (int i = 0; i < arr.length; i++) {
      for(int j = 0; j<arr[i].length; j++) 
      {
        if(arr[i][j].equals("+") || (arr[i][j].equals("@"))) {
          if(which.equals("row")) {
            return i;
          }
          else {
            return j;
          }
        }
      }
}
    return -1;
  }

 
  public static String[][] removeLine(String[][] arr) {
    String[][] returnArr = new String[arr.length-1][arr[1].length];
    for (int i = 1; i < arr.length; i++) {
      for(int j = 0; j< arr[i].length; j++) {
        returnArr[i-1][j] = arr[i][j];
      }
}
    return returnArr;
  }

   /*private static String readFile(String filePath) {
       StringBuilder sb = new StringBuilder();

       try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {

           String line = br.readLine();
           while (line != null) {
               sb.append(line).append(System.getProperty("line.separator"));
               line = br.readLine();
           }

       } catch (Exception errorObj) {
           System.err.println("Couldn't read file: " + filePath);
           errorObj.printStackTrace();
       }

       return sb.toString();
   }

   public static void writeDataToFile(String filePath, String data) throws IOException {
       try (FileWriter f = new FileWriter(filePath);
            BufferedWriter b = new BufferedWriter(f);
            PrintWriter writer = new PrintWriter(b);) {


           writer.println(data);


       } catch (IOException error) {
           System.err.println("There was a problem writing to the file: " + filePath);
           error.printStackTrace();
       }
   }*/
}
