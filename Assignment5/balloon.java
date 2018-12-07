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
	private static int[] result;

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
		result = new int[nbProblems];

		for (int i = 0; i < nbProblems; i++) {
			// repeat until all balloons popped (i.e. removed)
			while (balloons.get(i).size() > 0) {
				// we've used an arrow
				result[i]++;

				// find the highest unpopped balloon
				int max = Collections.max(balloons.get(i));

				// pop the first occurence of a balloon at that height
				int indexOfPopped = balloons.get(i).indexOf(max);
				balloons.get(i).remove(indexOfPopped);

				// check if there's a balloon at height max-1 that is to the right of the balloon we just popped
				max--;
				while (max > 0 && !balloons.get(i).isEmpty() &&
					   balloons.get(i).subList(indexOfPopped, balloons.get(i).size()).contains(max)) {
					// if such a balloon exists, pop that one too
					int oldIndexOfPopped = indexOfPopped;
					indexOfPopped = balloons.get(i).subList(oldIndexOfPopped, balloons.get(i).size()).indexOf(max) + balloons.get(i).subList(0, oldIndexOfPopped).size();
					balloons.get(i).remove(indexOfPopped);

					max--;
				}
			}
		}
	}

    public static void sendOutputData() {
        // print number of arrows required for each problem on individual lines in a file called testBalloons_solution.txt
        //File file = new File(testIslands_solution.txt);
        //file.createNewFile();
        try {
            FileWriter fw = new FileWriter("testBalloons_solution.txt");
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