// Name: Alex Hale
// ID: 260672475
// Collaborators: none

import java.io. * ;
import java.util. * ;
import java.util.regex. * ;
import java.math. * ;
import static java.lang.System.out;

public class islands {

	private Integer nbProblems;
	private ArrayList<ArrayList<ArrayList<Boolean>>> pebbles = new ArrayList<ArrayList<ArrayList<Boolean>>>();

	public static void main(String[] args) {
		try {
            Scanner f = new Scanner(new File("testIslands.txt"));
            String[] ln = f.nextLine().split("\\s+"); 					/*first line is the number of problems in the file*/
            this.nbProblems = Integer.parseInt(ln[0]);

            // TODO FINISH THIS FILE FROM THIS POINT ONWARD

            for (int i = 0; i < nbProblems; i++) {						// iterate through the line for each problem
            	ln = f.nextLine().split("\\s+");

            	ArrayList<Boolean> temp = new ArrayList<Boolean>();
            	for (int j = 0; j < 12; j++) {							// store the locations of the pebbles
            		Integer value = Integer.parseInt(ln[i]);

            		if (value == 0) {
            			temp.add(false);
            		} else {
            			temp.add(true);
            		}
            	}
            	pebbles.add(temp);
            }

            /*Sanity checks*/
            if (pebbles.size() != nbProblems){
                throw new RuntimeException("There are " + pebbles.size() + " problems stored while the file specifies " + this.nbProblems + " problems.");
            }

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }

        minPebbles();
	}

	void minPebbles() {
		// TODO print number of arrows required for each problem on individual lines in a file called testBalloons_solution.txt
	}

}