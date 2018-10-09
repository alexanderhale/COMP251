package A1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import A1.main.*;
public class Tester {

	public static void main(String[] args) {
		System.out.println("### This is the assignment tester ###");
		System.out.println("This is in no way a definitive test for correctness of your assignment");
		System.out.println("Please add extra tests here to test your own code");
		
		System.out.println("Running checks");
		Open_Addressing openMap = new Open_Addressing(10, 0);
		Chaining chainingMap = new Chaining(10, 0);
		
		int chain = chainingMap.chain(1);
		System.out.println("Chaining chain check: " + (chain == 13));
		
		chainingMap.insertKey(1);
		System.out.println("Chaining insert check: " + (chainingMap.Table.get(13).size() == 1));
		
		int probe = openMap.probe(1, 1);
		System.out.println("Open Addressing probe check: " + (probe == 14));
		
		openMap.insertKey(0);
		System.out.println("Open Addressing insert check: " + (openMap.Table[0] == 0));
		
		openMap.insertKey(1);
		openMap.removeKey(1);
		System.out.println("Open Addressing remove check: " + (openMap.Table[14] != 1));
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/n_comparison.csv")));
			String line;
			List<Integer> lengths = new LinkedList<>();
			String firstLine = "";
			while( (line = br.readLine())!=null ) {
				if(firstLine.equals(""))
					firstLine = line;
				lengths.add(line.split(",").length);
			}
			System.out.println("n_comparison file check:"+(
					firstLine.equals("Alpha,0.0625,0.125,0.1875,0.25,0.3125,0.375,0.4375,0.5,0.5625,0.625,0.6875,0.75,0.8125,0.875,0.9375,1.0")
					&& lengths.stream()
							.map(l->l==lengths.get(0))
							.reduce((acc,x)->acc&&x)
							.get()
					));
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("You have not created n_comparison.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/remove_collisions.csv")));
			String line;
			List<Integer> lengths = new LinkedList<>();
			while( (line = br.readLine())!=null ) {
				lengths.add(line.split(",").length);
			}
			System.out.println("remove_collisions file check:"+lengths.stream()
																	.map(l->l==lengths.get(0))
																	.reduce((acc,x)->acc&&x)
																	.get());
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("You have not created remove_collisions.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/w_comparison.csv")));
			String line;
			List<Integer> lengths = new LinkedList<>();
			while( (line = br.readLine())!=null ) {
				lengths.add(line.split(",").length);
			}
			System.out.println("w_comparison file check:"+lengths.stream()
																	.map(l->l==lengths.get(0))
																	.reduce((acc,x)->acc&&x)
																	.get());
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("You have not created w_comparison.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
