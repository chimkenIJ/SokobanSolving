import java.util.ArrayList;

public class State {
    /* attributes to represent current state */
    private State parent;                // ← reference to parent.  Null if no parent.
    private String[][] board;
    public int stepsNeeded;
    public int pR;
    public int pC;
    int counter;

    public State(State parent, String[][] initBoard, int stepsNeeded, int r, int c) {
        this.parent = parent;
        this.board = initBoard;
        System.out.println("run");
        this.stepsNeeded = stepsNeeded;
        this.pR = r;
        this.pC = c;
        //test
    }

    public State(State toCopy) {
        this.board = copy(toCopy.board);
        this.parent = toCopy.parent;
        this.stepsNeeded = toCopy.stepsNeeded;
        this.pR = toCopy.pR;
        this.pC = toCopy.pC;
        this.counter = toCopy.counter;
    }
    public State getParent() {
        return this.parent;
    }

    public int getDepth() {
        return this.counter;
    }

    public int getScore() {
        int xb = 0, xs = 0, yb = 0, ys = 0;
        //for 1 box
        for(int i = 0; i<board.length; i++) {
            for(int j = 0; j<board[0].length; j++) {
                if(board[i][j].equals("$")) {
                    xb = i;
                    yb = j;
                }
                if(board[i][j].equals(".")) {
                    xs = i;
                    ys = j;
                }
            }
        }
        return Math.abs(xb-xs) + Math.abs(yb-ys);
    }

    public String[][] copy(String[][] arr) {
        String[][] copy = new String[arr.length][arr[1].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }

    public String[][] getBoard() {
        return this.board;
    }


    public void setParent(State p) {
        this.parent = p;
    }

    public boolean isGoal() {
        // return true if this state is the goal

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(".") || board[i][j].equals("+")) return false;
            }
        }

        return true;
    }

    public State copy() {
        // return a copy of the current state
        return new State(this.parent, this.board, this.stepsNeeded, this.pR, this.pC);
        // ** be careful:  you must run the constructor
        //  here!! Don’t return a reference to this **
    }

    public ArrayList<State> getNextStates() {
        ArrayList<State> stateList = new ArrayList<State>();
        String[] movesArr = {"w", "a", "s", "d"};
        for (int i = 0; i < movesArr.length; i++) {
            if (isValidMove(movesArr[i])) {
                State newState = new State(this);
                newState.applyMove(movesArr[i]);
                newState.setParent(this);
                stateList.add(newState);
            }
        }

        // return list of next states from current
        return stateList;
        // ** be careful: return COPIES **
        // ** remember to set the parent of each copy to this object **
    }




    public String toString() {
        // what the current state should look like when
        // printed
        return "";
    }


    public ArrayList<String> getNextMoves() {        // return list of valid moves
        return null;
    }

    public void print(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
    public void print() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void applyMove(String move) {
        // apply move m to current state


        if (this.isValidMove(move)) {
            if (move.equals("a")) {
                if (board[pR][pC - 1].equals("$") || board[pR][pC - 1].equals("*")) {
                    if (board[pR][pC - 2].equals(".")) {
                        board[pR][pC - 2] = "*";
                    } else {
                        board[pR][pC - 2] = "$";
                    }
                }
                if (board[pR][pC - 1].equals(".")) {
                    board[pR][pC - 1] = "+";
                } else {
                    board[pR][pC - 1] = "@";
                }

                if (board[pR][pC].equals("+")) {
                    board[pR][pC] = ".";
                } else {
                    board[pR][pC] = " ";
                }
                pC--;
            }
            if (move.equals("d")) {
                if (board[pR][pC + 1].equals("$") || board[pR][pC + 1].equals("*")) {
                    if (board[pR][pC + 2].equals(".")) {
                        board[pR][pC + 2] = "*";
                    } else {
                        board[pR][pC + 2] = "$";
                    }
                }
                if (board[pR][pC + 1].equals(".")) {
                    board[pR][pC + 1] = "+";
                } else {
                    board[pR][pC + 1] = "@";
                }
                if (board[pR][pC].equals("+")) {
                    board[pR][pC] = ".";
                } else {
                    board[pR][pC] = " ";
                }
                pC++;
            }
            if (move.equals("w")) {
                if (board[pR - 1][pC].equals("$") || board[pR - 1][pC].equals("*")) {
                    if (board[pR - 2][pC].equals(".")) {
                        board[pR - 2][pC] = "*";
                    } else {
                        board[pR - 2][pC] = "$";
                    }
                }
                if (board[pR - 1][pC].equals(".")) {
                    board[pR - 1][pC] = "+";
                } else {
                    board[pR - 1][pC] = "@";
                }
                if (board[pR][pC].equals("+")) {
                    board[pR][pC] = ".";
                } else {
                    board[pR][pC] = " ";
                }
                pR--;
            }
            if (move.equals("s")) {
                if (board[pR + 1][pC].equals("$") || board[pR + 1][pC].equals("*")) {
                    if (board[pR + 2][pC].equals(".")) {
                        board[pR + 2][pC] = "*";
                    } else {
                        board[pR + 2][pC] = "$";
                    }
                }
                if (board[pR + 1][pC].equals(".")) {
                    board[pR + 1][pC] = "+";
                } else {
                    board[pR + 1][pC] = "@";
                }
                if (board[pR][pC].equals("+")) {
                    board[pR][pC] = ".";
                } else {
                    board[pR][pC] = " ";
                }
                pR++;
            }
            counter++;
        }
    }

  /*
  u = up;
  l = left;
  d = down;
  r = right;
  */

    public boolean isValidMove(String move) {
        // return true if move is valid
        if (move.equals("a")) {
            if (pC >= 1) {
                if (board[pR][pC - 1].equals("$") || board[pR][pC - 1].equals("*")) {
                    if (board[pR][pC - 2].equals("#") ||
                            board[pR][pC - 2].equals("$") || board[pR][pC - 2].equals("*")) {
                        return false;
                    }
                }
                if (board[pR][pC - 1].equals("#")) return false;
            }
            else{ return false;}
        }
        if (move.equals("d")) {
            if (pR < board[0].length - 1) {
                if (board[pR][pC + 1].equals("$") || board[pR][pC + 1].equals("*")) {
                    if (board[pR][pC + 2].equals("#") ||
                            board[pR][pC + 2].equals("$") || board[pR][pC + 2].equals("*")) {
                        return false;
                    }
                }
                if (board[pR][pC + 1].equals("#")) return false;
            }
            else{ return false;}
        }
        if (move.equals("w")) {
            if (pR >= 1) {
                if (board[pR - 1][pC].equals("$") || board[pR - 1][pC].equals("*")) {
                    if (board[pR - 2][pC].equals("#") ||
                            board[pR - 2][pC].equals("$") || board[pR - 2][pC].equals("*")) {
                        return false;
                    }
                }
                if (board[pR - 1][pC].equals("#")) return false;
            }
            else{ return false;}
        }
        if (move.equals("s")) {
            if (pR < board[0].length - 1) {
                if (board[pR + 1][pC].equals("$") || board[pR + 1][pC].equals("*")) {
                    if (board[pR + 2][pC].equals("#") ||
                            board[pR + 2][pC].equals("$") || board[pR + 2][pC].equals("*")) {
                        return false;
                    }
                }
                if (board[pR + 1][pC].equals("#")) return false;
            }
            else{ return false;}
        }
        return true;
    }

    @Override
    public boolean equals(Object input) {
        State state = (State) (input);
        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {
                if (this.board[i][j] != state.getBoard()[i][j]) return false;
            }
        }
        return true;
    }
}


