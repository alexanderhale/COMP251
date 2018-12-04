// Name: Alex Hale
// ID: 260672475
// Collaborators: none

import java.io. * ;
import java.util. * ;
import java.util.regex. * ;
import java.math. * ;
import static java.lang.System.out;

public class balloon {

	private static Integer nbProblems;
	private static ArrayList<ArrayList<Integer>> balloons = new ArrayList<ArrayList<Integer>>();

	public static void main(String[] args) {
        gatherInputData();
        calculateArrows();
        sendOutputData();
	}

    public static void gatherInputData() {
        try {
            Scanner f = new Scanner(new File("testBalloons.txt"));
            String[] ln = f.nextLine().split("\\s+");                   /*first line is the number of problems in the file*/
            nbProblems = Integer.parseInt(ln[0]);

            Integer balloonsInThisProblem;
            ln = f.nextLine().split("\\s+");
            for (int i = 0; i < nbProblems; i++) {                      // find the number of balloons in each problem
                balloonsInThisProblem = Integer.parseInt(ln[i]);

                String [] ln_2 = f.nextLine().split("\\s+");
                ArrayList<Integer> temp = new ArrayList<Integer>();
                for (int j = 0; j < balloonsInThisProblem; j++) {       // store the heights of the balloons
                    temp.add(Integer.parseInt(ln_2[j]));
                }
                balloons.add(temp);
            }

            /*Sanity checks*/
            if (balloons.size() != nbProblems){
                throw new RuntimeException("There are " + balloons.size() + " problems stored while the file specifies " + nbProblems + " problems.");
            }

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }
    }

	public static void calculateArrows() {
		// TODO calculate minimum number of arrows to pop all balloons
        System.out.println(balloons.toString());     // TODO remove this test
	}

    public static void sendOutputData() {
        // TODO print number of arrows required for each problem on individual lines in a file called testBalloons_solution.txt
    }

}