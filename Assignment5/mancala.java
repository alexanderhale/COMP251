// Name: Alex Hale
// ID: 260672475
// Collaborators: none

import java.io. * ;
import java.util. * ;
import java.util.regex. * ;
import java.math. * ;
import static java.lang.System.out;

public class mancala {

	private static Integer nbProblems;
	private static ArrayList<ArrayList<Integer>> pebbles = new ArrayList<ArrayList<Integer>>();
    private static int[] result;

	public static void main(String[] args) {
		gatherInputData();
        minPebbles();
        sendOutputData();
	}

    public static void gatherInputData() {
        try {
            Scanner f = new Scanner(new File("testMancala.txt"));
            String[] ln = f.nextLine().split("\\s+");                   /*first line is the number of problems in the file*/
            nbProblems = Integer.parseInt(ln[0]);

            for (int i = 0; i < nbProblems; i++) {                      // iterate through the line for each problem
                ln = f.nextLine().split("\\s+");

                ArrayList<Integer> temp = new ArrayList<Integer>();
                for (int j = 0; j < 12; j++) {                          // store the locations of the pebbles
                    temp.add(Integer.parseInt(ln[j]));
                }
                pebbles.add(temp);
            }

            /*Sanity checks*/
            if (pebbles.size() != nbProblems){
                throw new RuntimeException("There are " + pebbles.size() + " problems stored while the file specifies " + nbProblems + " problems.");
            }

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }
    }

	public static void minPebbles() {
        result = new int[nbProblems];

        // iterate through all the problems
        for (int i = 0; i < nbProblems; i++) {
            result[i] = pebblesRemaining(pebbles.get(i));
        }
	}

    public static int pebblesRemaining(ArrayList<Integer> currentBoard) {
        // find all possible moves of this current board
        // make each move in a recursive call
        // choose the case with the minimum pebbles remaining

        // base case
        if (!moveAvailable(currentBoard)) {
            return numPebbles(currentBoard);
        } else {
            ArrayList<Integer> possibleMins = new ArrayList<Integer>(); 

            // induction step
            int slot0;
            int slot1;
            int slot2;

            for (int i = 2; i < currentBoard.size(); i++) {
                slot0 = currentBoard.get(i - 2);
                slot1 = currentBoard.get(i - 1);
                slot2 = currentBoard.get(i);

                if (slot0 == 1 && slot1 == 1 && slot2 == 0) {
                    ArrayList<Integer> tempBoard = new ArrayList<Integer>();
                    tempBoard.addAll(currentBoard);
                    tempBoard.set(i, 1);          // make move
                    tempBoard.set(i - 1, 0);
                    tempBoard.set(i - 2, 0);
                    possibleMins.add(pebblesRemaining(tempBoard));  // recurse with new board
                } 

                if (slot0 == 0 && slot1 == 1 && slot2 == 1) {
                    ArrayList<Integer> tempBoard = new ArrayList<Integer>();
                    tempBoard.addAll(currentBoard);
                    tempBoard.set(i, 0);          // make move
                    tempBoard.set(i - 1, 0);
                    tempBoard.set(i - 2, 1);
                    possibleMins.add(pebblesRemaining(tempBoard));  // recurse with new board
                }
            }

            return Collections.min(possibleMins);
        }
    }

    public static boolean moveAvailable(ArrayList<Integer> currentBoard) {
        if (numPebbles(currentBoard) < 2) {
            return false;
        } else {
            int slot0;
            int slot1;
            int slot2;

            for (int i = 2; i < currentBoard.size(); i++) {
                slot0 = currentBoard.get(i - 2);
                slot1 = currentBoard.get(i - 1);
                slot2 = currentBoard.get(i);

                if ((slot0 == 1 && slot1 == 1 && slot2 == 0) || (slot0 == 0 && slot1 == 1 && slot2 == 1)) {
                    return true;        // skippable stone found
                }
            }

            return false;   // no skippable stone found
        }
    }

    public static int numPebbles(ArrayList<Integer> currentBoard) {
        int result = 0;

        for (int i = 0; i < currentBoard.size(); i++) {
            result += currentBoard.get(i);
        }

        return result;
    }

    public static void sendOutputData() {
        // print min pebbles left in each problem on individual lines in a file called testMancala_solution.txt
        try {
            FileWriter fw = new FileWriter("testMancala_solution.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < nbProblems; i++) {
                bw.write(result[i] + "\n");
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}