// Name: Alex Hale
// ID: 260672475
// Collaborators: none

import java.io. * ;
import java.util. * ;
import java.util.regex. * ;
import java.math. * ;
import static java.lang.System.out;

public class islands {

	private static Integer nbProblems;
	private static ArrayList<ArrayList<ArrayList<Boolean>>> allData = new ArrayList<ArrayList<ArrayList<Boolean>>>();
    private static int[] result;

	public static void main(String[] args) {
		gatherInputData();
        numIslands();
        sendOutputData();
	}

    public static void gatherInputData() {
        try {
            Scanner f = new Scanner(new File("testIslands.txt"));
            String[] ln = f.nextLine().split("\\s+");                   /*first line is the number of problems in the file*/
            nbProblems = Integer.parseInt(ln[0]);

            for (int i = 0; i < nbProblems; i++) {                      // iterate through the line for each problem
                ln = f.nextLine().split("\\s+");

                ArrayList<ArrayList<Boolean>> problemData = new ArrayList<ArrayList<Boolean>>();
                String data;
                for (int j = 0; j < Integer.parseInt(ln[0]); j++) {
                    data = f.nextLine();

                    ArrayList<Boolean> lineData = new ArrayList<Boolean>();
                    for (int t = 0; t < Integer.parseInt(ln[1]); t++) {
                        if (data.charAt(t) == '-') {
                            lineData.add(false);        // false == white
                        } else {
                            lineData.add(true);         // true == black
                        }
                    }
                    problemData.add(lineData);
                }
                allData.add(problemData);
            }

            /*Sanity checks*/
            if (allData.size() != nbProblems){
                throw new RuntimeException("There are " + allData.size() + " problems stored while the file specifies " + nbProblems + " problems.");
            }

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }
    }

    // determine the number of separate islands in each problem
	public static void numIslands() {
        result = new int[nbProblems];

        for (int i = 0; i < nbProblems; i++) {
            // create a holding array to work in
            ArrayList<ArrayList<Integer>> islands = new ArrayList<ArrayList<Integer>>();
            Map<Integer, Integer> pairs = new HashMap<Integer, Integer>();

            // fill the holding array with zeros
                // TODO remove this if we're in a time crunch, it's probably not necessary
            for (int j = 0; j < allData.get(i).size(); j++) {
                ArrayList<Integer> temp = new ArrayList<Integer>();
                for (int t = 0; t < allData.get(i).get(j).size(); t++) {
                    temp.add(0);
                }
                islands.add(temp);
            }

            // check the 4 adjacent pixels to see if any of them are part of an island
            for (int j = 0; j < allData.get(i).size(); j++) {
                for (int t = 0; t < allData.get(i).get(j).size(); t++) {
                    if (allData.get(i).get(j).get(t) == true) {
                        int[] connecting = new int[2];

                        // look at the adjacent pixels to see if they belong to an island
                        if (j - 1 >= 0) {
                            // if we're not already at the top row, check the pixel above
                            connecting[0] = islands.get(j-1).get(t);
                        }
                        if (t - 1 >= 0) {
                            // if we're not already at the leftmost column, check the pixel to the left
                            connecting[1] = islands.get(j).get(t-1);
                        }

                        if (connecting[0] == 0 && connecting[1] == 0) {
                            // we've found a new island
                            result[i]++;
                            islands.get(j).set(t, result[i]);
                        } else if (connecting[0] == connecting[1]) {
                            // no need to adjust result (existing island)
                            islands.get(j).set(t, connecting[0]);
                        } else if (connecting[0] != 0 && connecting[1] != 0) {
                            // set this pixel to belong to either of the 2 islands, doesn't matter which
                            islands.get(j).set(t, connecting[0]);

                            if (pairs.containsKey(connecting[0]) && pairs.get(connecting[0]) != connecting[1]) {
                        		// this pair has not yet been found, save it
                        		pairs.put(connecting[0], connecting[1]);
                            } else if (pairs.containsKey(connecting[1]) && pairs.get(connecting[1]) != connecting[0]) {
                            	pairs.put(connecting[1], connecting[0]);
                            } else if (!pairs.containsKey(connecting[0]) && !pairs.containsKey(connecting[1])) {
                            	pairs.put(connecting[0], connecting[1]);
                            }
                        }
                    } else {
                        islands.get(j).set(t, 0);
                    }
                }
            }

            System.out.println(pairs.toString());	// TODO remove
            System.out.println();

            // adjust the total pairs to remove all the matches
            result[i] -= pairs.size();
                // TODO verify that this logic works
        }
	}

    public static void sendOutputData() {
        // print number of islands in each problem on individual lines in a file called testIslands_solution.txt
        //File file = new File(testIslands_solution.txt);
        //file.createNewFile();
        try {
            FileWriter fw = new FileWriter("testIslands_solution.txt");
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