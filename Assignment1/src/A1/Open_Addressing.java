package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }
    }

    /**
     * Implements the hash function g(k) = (h(k) + i) mod 2^r
     */
    public int probe(int key, int i) {
        // TODO "Note that the value of A must be updated when you change w."
        return (((A*key) % (2*w)) + i) % m;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        int i = 0;                      // serves as both # of collisions and i for linear probing
        boolean slotFound = false;
        while (!slotFound && (i < m - 1)) {     // TODO should this be m or m-1?
            // generate hash with probe()
            int slotToTry = probe(key, i);

            // check if that slot is already full
            if (isSlotEmpty(slotToTry)) {
                // empty => fill it
                Table[slotToTry] = key;
                slotFound = true;
            } else {
                // full => try again
                i++;
            }
        }
        
        return i;
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        int i = 0;                      // serves as both # of collisions and i for linear probing
        boolean keyFound = false;
        while (!keyFound && (i < m - 1)) {  // TODO should this be m or m-1?
            // generate hash with probe()
            int slotToTry = probe(key, i);

            // check if the key is there
            if (Table[slotToTry] == key) {
                // found it
                Table[slotToTry] = -1;
                keyFound = true;
            } else {
                // not found => try again
                i++;
            }
        }
        return i;
    }
}