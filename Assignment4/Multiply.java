// Name: Alex Hale
// ID: 260672475
// Collaborators: none

import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }

    // size = bit length of integers x and y
    // return result[0] = result of multiplication, result[1] = # of brute force operations undertaken
    public static int[] naive(int size, int x, int y) {      
        int[] result = new int[2];
    	int operations = 0;

    	if (size == 1) {
    		result[0] = x * y;
    		result[1] = 1;			// multiplication of two 1-bit numbers costs 1 operation
    	} else {
    		// implement divide-and-conquer naive multiplication algorithm
    		int m = (int) Math.ceil((double) size / (double) 2);
    		int pow = (int) Math.pow((double) 2, (double) m);
			int a = x / pow;
			int b = x % pow;
			int c = y / pow;
			int d = y % pow;
			int[] e = naive(m, a, c);
			int[] f = naive(m, b, d);
			int[] g = naive(m, b, c);
			int[] h = naive(m, a, d);

			result[0] = (int) (Math.pow((double) 2, (double) 2*m) * e[0]) + (pow * (g[0] + h[0])) + f[0];
			
			// naive algorithm costs 3 operations times the size of the integers being multiplied
			result[1] = e[1] + f[1] + g[1] + h[1] + (3 * m);
    	}

        return result;
    }

    // size = bit length of integers x and y
    // return result[0] = result of multiplication, result[1] = # of brute force operations undertaken
    public static int[] karatsuba(int size, int x, int y) {
    	int[] result = new int[2];
        
        if (size == 1) {
        	result[0] = x * y;
        	result[1] = 1;			// multiplication of two 1-bit numbers costs 1 operation
        } else {
        	// implement divide-and-conquer karatsuba multiplication algorithm
        	int m = (int) Math.ceil((double) size / (double) 2);   	
        	int pow = (int) Math.pow((double) 2, (double) m);		
        	int a = x / pow; 				            			
        	int b = x % pow;			 							
        	int c = y / pow;			 							
        	int d = y % pow;			 							

        	int[] e = karatsuba(m, a, c);
        	int[] f = karatsuba(m, b, d);
        	int[] g = karatsuba(m, a - b, c - d);

        	result[0] = (int) (Math.pow((double) 2, (double) 2*m) * e[0]) + (pow * (e[0] + f[0] - g[0])) + f[0];
        	
        	// naive algorithm costs 6 operations times the size of the integers being multiplied
        	result[1] = e[1] + f[1] + g[1] + (6 * m);
        }
        
        return result;
        
    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);
                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}