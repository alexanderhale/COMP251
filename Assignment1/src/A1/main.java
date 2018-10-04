package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;
import java.io.*;
import java.util.*;

public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;
        int m = power2((int) (w - 1) / 2 + 1);

        for (int n : nList) {

            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */
            alphaList.add((double) n / (double) m);
            
            int chainTot = 0;
            int probeTot = 0;

            for (int i = 0; i < n; i++) {
                chainTot += MyChainTable.insertKey(keysToInsert[i]);
                probeTot += MyProbeTable.insertKey(keysToInsert[i]);
            }
            avColListChain.add((double) chainTot / (double) n);
            avColListProbe.add((double) probeTot / (double) n);
        }

        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
        /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please note the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

        Open_Addressing MyProbeTable = new Open_Addressing(w, 137);
        for (int i = 0; i < 16; i++) {
            MyProbeTable.insertKey(keysToInsert[i]);
        }

        for(int i = 0; i < 16; i++) {
            removeCollisions.add((double) MyProbeTable.removeKey(keysToInsert[i]));
            removeIndex.add((double) keysToInsert[i]);
        }

        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

        /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        // array of 10 different values of w
            // TODO decide whether these values for 
        int[] ws = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512};

        // generate 16 random keys, ensuring that no 2 keys are the same
            // why 16? Because I decided it should be 16
                // TODO make sure the values of w are reasonable for 16 keys, or determine whether we need to vary the number of keys based on the value of w
        int[] randomKeys = new int[16];
        for (int i = 0; i < 16; i++) {
            randomKeys[i] = generateRandom(0, 200, -1);
        }

        for (int i = 0; i < ws.length; i++) {
            for (int k = 0; k < 10; k++) {
                Chaining MyChainTable2 = new Chaining(ws[i], -1);
                Open_Addressing MyProbeTable2 = new Open_Addressing(ws[i], -1);

                for (int j = 0; j < randomKeys.length; j++) {
                    alphaList2.add((double) randomKeys.length / (double) power2((int) (ws[i] - 1) / 2 + 1));
                    avColListChain2.add((double) MyChainTable2.insertKey(randomKeys[j]));
                    avColListProbe2.add((double) MyProbeTable2.insertKey(randomKeys[j]));
                }
            }
            // TODO remove these blanks if they're not needed
            alphaList2.add(0.0);
            avColListChain2.add(0.0);
            avColListProbe2.add(0.0);
        }

        //TODO ADD YOUR CODE HERE
        generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);
    }

}
