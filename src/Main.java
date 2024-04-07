import java.io.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        String hard =
                "100         \n" +
                        "        ####\n" +
                        "########  ##\n" +
                        "#        ###\n" +
                        "# @$$ ## ..#\n" +
                        "# $$ ##  ..#\n" +
                        "#       ####\n" +
                        "########### ";
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
        String[][] secondSplit = new String[split.length][split[1].length()];
        for (int i = 0; i < split.length; i++) {
            secondSplit[i] = split[i].split("");
        }
        String[][] first = removeLine(secondSplit);

        int playerRow = getPlayer(first, "row");
        int playerCol = getPlayer(first, "col");

        State start = new State(null, first, Integer.parseInt(secondSplit[0][0]), playerRow, playerCol);
        State saved = new State(start);

        System.out.println("Player Coordinates: " + start.pR + ", " + start.pC);
        System.out.println("--");
        start.print(start.getBoard());
        System.out.println("--");
/*
//playable version
        while (!(start.isGoal())) {
            Scanner keyboard = new Scanner(System.in);
            String ans = keyboard.nextLine();
            start.applyMove(ans);
            System.out.println("\nPlayer coordinates: " + start.pR + ", " + start.pC);
            System.out.println("--");
            start.print(start.getBoard());
            System.out.println("--");
        }
        System.out.println("\nLevel finished, with "
                + start.counter + isPlural(start.counter, "move")
                + "! Least moves needed" + isPlural(start.stepsNeeded, "is")
                + start.stepsNeeded + isPlural(start.stepsNeeded, "move") + ".");
*/
        State finish = solve(start);
        ArrayList<State> solutionPath = new ArrayList<State>();

        while (!(finish.equals(saved))) {
            solutionPath.add(finish);
            finish = finish.getParent();
        }
        Collections.reverse(solutionPath);
        for (State state : solutionPath) {
            state.print();
        }
    }


    public static State solve(State state) {
        ArrayList<State> made = new ArrayList<State>();
        made.add(state);
        ArrayList<State> toVisit = new ArrayList<State>();
        toVisit.add(state);
        ArrayList<State> nextStates;

        while (toVisit.size() > 0) {
            State current = toVisit.remove(0);
            if (current.isGoal()) {
                return current;
            }
            nextStates = current.getNextStates();
            for (State nextState : nextStates) {
                if (!made.contains(nextState)) {
                    toVisit.add(nextState);
                    made.add(nextState);
                }

            }
        }
        return null;
    }

    public static String isPlural(int num, String str) {
        if (str.equals("is")) {
            if (num > 1) {
                return " are ";
            } else {
                return " is ";
            }
        }
        if (num > 1) {
            return " " + str + "s";
        }
        return " " + str;
    }

    public static int getPlayer(String[][] arr, String which) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j].equals("+") || (arr[i][j].equals("@"))) {
                    if (which.equals("row")) {
                        return i;
                    } else {
                        return j;
                    }
                }
            }
        }
        return -1;
    }


    public static String[][] removeLine(String[][] arr) {
        String[][] returnArr = new String[arr.length - 1][arr[1].length];
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                returnArr[i - 1][j] = arr[i][j];
            }
        }
        return returnArr;
    }
}
