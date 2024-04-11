import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Main {
    static int MAX_DEPTH = 10;
    public static void main(String[] args) {
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


        String[] split = easy2.split("\n");
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
        //State finish = solveBreadth(start);
        //State finish = solveDepth(start);
        State finish = solveGreedy(start);
        ArrayList<State> solutionPath = new ArrayList<State>();
        System.out.println("Least moves needed" + isPlural(finish.getDepth(), "is")
                + finish.getDepth() + isPlural(finish.getDepth(), "move") + ".");
        while (!(finish.equals(saved))) {
            solutionPath.add(finish);
            finish = finish.getParent();
        }
        Collections.reverse(solutionPath);
        for (State state : solutionPath) {
            state.print();
        }
    }


    public static State solveBreadth(State state) {
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

    public static State solveDepth(State state) {
        ArrayList<State> made = new ArrayList<State>();
        made.add(state);
        ArrayList<State> toVisit = new ArrayList<State>();
        toVisit.add(state);
        ArrayList<State> nextStates;

        while (toVisit.size() > 0) {
            State current = toVisit.remove(toVisit.size() - 1);
            if (current.isGoal()) {
                return current;
            }
            nextStates = current.getNextStates();
            if (current.getDepth() < MAX_DEPTH) {
                for (State nextState : nextStates) {
                    if (!made.contains(nextState)) {
                        toVisit.add(nextState);
                        made.add(nextState);
                    }

                }
            }
        }
        return null;
    }

    public static State solveGreedy(State state) {
        ArrayList<State> closed = new ArrayList<State>();
        ArrayList<State> open = new ArrayList<State>();
        open.add(state);
        ArrayList<State> nextStates;

        while (open.size() > 0) {
            State current = open.remove(getMostPromising(open));
            closed.add(current);
            if (current.isGoal()) {
                return current;
            }
            nextStates = current.getNextStates();
            for (State nextState : nextStates) {
                if (!open.contains(nextState) && !closed.contains(nextState)) {
                    addIntoSort(open, nextState);
                }

            }
        }
        return null;
    }

    public static void print(ArrayList<State> list) {
        for (State state : list) {
            System.out.print(state.getScore() + " ");
        }
        System.out.println("");
    }

    public static void addIntoSort(ArrayList<State> list, State state) {
        int i = 0;
        if(list.size()==0) {
            list.add(state);
            return;
        }
        while (i < list.size()) {
            if (list.get(i).getScore() >= state.getScore()) {
                list.add(i, state);
                return;
            }
            i++;
        }
        list.add(list.size() - 1, state);
    }

    public static int getMostPromising(ArrayList<State> list) {
        int min = list.get(0).getScore();
        int index = 0;
        for (int i = 1; i < list.size(); i++) {
            if (min < list.get(i).getScore()) {
                min = list.get(i).getScore();
                index = i;
            }
        }
        return index;
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
