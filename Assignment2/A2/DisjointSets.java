// Name: Alex Hale
// ID: 260672475
// Collaborators: none

package A2;
import java.io.*;
import java.util.*;


/****************************
*
* COMP251 template file
*
* Assignment 2, Question 1
*
*****************************/


public class DisjointSets {

    private int[] par;
    private int[] rank;
    
    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSets(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }
    
    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }
    
    /* find resentative of element i */
    public int find(int i) {
    	if (par[i] == i) {
    		// if we're at a root, return it
    		return i;
    	} else {
    		// otherwise, perform path compression:
    			// make all nodes on the "find path" direct children of the root
    		par[i] = find(par[i]);
    		return par[i];
    	}       
    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {
    	if (rank[i] > rank[j]) {
    		// merge the set with the smaller rank into the set with the larger rank
            // make all nodes that have par[x] = par[i] to now have par[x] = par[j]
            int old = par[j];
            for (int t = 0; t < par.length; t++) {
                if (par[t] == old) {
                    par[t] = par[i];
                }
            }

            rank[j] = rank[i];
    		
            return par[i];			// return the resulting root of the merge (the representative)
    	} else if (rank[i] == rank[j]) {
            // make all nodes that have par[x] = par[i] to now have par[x] = par[j]
            int old = par[i];
            for (int t = 0; t < par.length; t++) {
                if (par[t] == old) {
                    par[t] = par[j];
                }
            }

            rank[j]++;              // when rank[i] == rank[j], the rank of the new root increases by 1

            return par[j];
        } else {
            // merge the set with the smaller rank into the set with the larger rank
            // make all nodes that have par[x] = par[i] to now have par[x] = par[j]
            int old = par[i];
            for (int t = 0; t < par.length; t++) {
                if (par[t] == old) {
                    par[t] = par[j];
                }
            }
	
            rank[i] = rank[j];      // update the ranks

    		return par[j];			// return the resulting root of the merge (the representative)
    	}
    }
    
    public static void main(String[] args) {
        
        DisjointSets myset = new DisjointSets(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);
        
    }

}
