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
	private static ArrayList<ArrayList<Boolean>> pebbles = new ArrayList<ArrayList<Boolean>>();

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

                ArrayList<Boolean> temp = new ArrayList<Boolean>();
                for (int j = 0; j < 12; j++) {                          // store the locations of the pebbles
                    if (Integer.parseInt(ln[j]) == 0) {
                        temp.add(false);
                    } else {
                        temp.add(true);
                    }
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
		// TODO find the min number of pebbles left in each problem
        System.out.println(pebbles.toString());     // TODO remove this test
	}

    public static void sendOutputData() {
        // TODO print min pebbles left in each problem on individual lines in a file called testMancala_solution.txt
    }
}