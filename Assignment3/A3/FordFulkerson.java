// Name: Alex Hale
// ID: 260672475
// Collaborators: none

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;	// TODO remove

public class FordFulkerson {

	// finds a path using a DFS between source and destination through non-zero weight edges
		// returns an ArrayList of integers with the list of unique nodes belonging to the path found
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> stack = new ArrayList<Integer>();						// path from source to destination
		ArrayList<Integer> discovered = new ArrayList<Integer>();					// list of nodes that have been found, but whose neighbours haven't yet been discovered
		ArrayList<Integer> neighboursDiscovered = new ArrayList<Integer>();			// list of nodes whose neighbours have been discovered
		stack.add(source);															// first element in the path
		discovered.add(source);
		int current;

		while (!stack.isEmpty()) {
			System.out.println("stack: " + stack.toString());		// TODO remove
			System.out.println("discovered: " + discovered.toString());		// TODO remove
			System.out.println("neighboursDiscovered: " + neighboursDiscovered.toString());		// TODO remove

			// peek the last element of the stack (i.e. the element whose neighbours are waiting to be explored)
			current = stack.get(stack.size() - 1);
			stack.remove(stack.indexOf(current));

			// if this node's neighbours haven't been discovered, we'd better discover them!
			if (!neighboursDiscovered.contains(current)) {
				// list to store the neighbours of the currently selected node
				ArrayList<Integer> neighbours = new ArrayList<Integer>();

				// look at all the edges connecting to the currently selected node
				for (Edge e : graph.getEdges()) {
					// determine whether the new node is stored in this edge's nodes[0] or nodes[1]
					int secondNode;
					if (e.nodes[0] == current) {
						secondNode = e.nodes[1];
					} else {
						secondNode = e.nodes[0];
					}

					// if there is an adjacent node whose neighbours haven't yet been discovered and that edge has non-zero weight
					if (!discovered.contains(secondNode) && graph.getEdge(current, secondNode) != null && graph.getEdge(current, secondNode).weight != 0) {
						neighbours.add(secondNode);				// add it to the list of neighbours
						discovered.add(secondNode);				// also add it to the list of nodes we've found
					}
				}

				if (neighbours.contains(destination)) {
					// if we've reached the destination, add it to the stack
					stack.add(destination);

					// remove any nodes that we found but didn't explore their neighbours, because they're not on the path
					// TODO replace
					for (Integer i : discovered) {
						if (!neighboursDiscovered.contains(i)) {
							stack.remove(stack.indexOf(i));
						}
					}

					// the stack contains the path we followed from source -> destination, and no other nodes
					return stack;
				} else {
					stack.addAll(neighbours);
				}

				// we've discovered all of this node's neighbours
				neighboursDiscovered.add(current);
			}
		}

		/*ArrayList<Integer> stack = new ArrayList<Integer>();		// path from source to destination
		ArrayList<Integer> visited = new ArrayList<Integer>();		// 
		stack.add(source);
		int current;

		while (!stack.isEmpty()) {
			// TimeUnit.SECONDS.sleep(1);

			current = stack.get(stack.size() - 1);
			stack.remove(stack.indexOf(current));

			if (!visited.contains(current)) {
				System.out.println(current + " ");
				visited.add(current);
			}

			// look at all the edges connecting to the currently selected node
			for (Edge e : graph.getEdges()) {
				if (e.nodes[0] == current && !visited.contains(e.nodes[1]) && e.weight != 0) {
					stack.add(e.nodes[1]);
				}
			}
		}*/

		// if no path is found to the destination, return an empty ArrayList
		return new ArrayList<Integer>();
	}
	
	// compute and return an integer corresponding to the max flow of the graph
		// if unable to compute maximum flow, output -1 (don't throw an exception)
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260672475"; 	// initialized with my McGill ID
		int maxFlow = 0;					// start max flow at 0

		// check for errors
		if (graph.getEdges().isEmpty()) {
			maxFlow = -1;
		} else {
			WGraph gf = new WGraph(graph);				// create G_f (residual graph) for manipulation

			// while there's a path from the source to the sink, keep augmenting the flow
			ArrayList<Integer> path = pathDFS(source, destination, gf);
			while (path != new ArrayList<Integer>()) {
				// System.out.println(path.toString());; 				// TODO remove

				// find the smallest (i.e. restrictive) capacity on the path
				int smallestCapacity = 0;
				for (int i = 1; i < path.size(); i++) {
					int firstNode = path.get(i - 1);
					int secondNode = path.get(i);

					if (gf.getEdge(firstNode, secondNode).weight > smallestCapacity) {
						smallestCapacity = gf.getEdge(firstNode, secondNode).weight;
					}
				}

				// add the restrictive capacity to the accumulated max flow
				maxFlow += smallestCapacity;

				// update the flow in the main graph for all the edges on the path
				for (int i = 1; i < path.size(); i++) {
					int firstNode = path.get(i - 1);
					int secondNode = path.get(i);

					graph.setEdge(firstNode, secondNode, smallestCapacity);
					gf.setEdge(firstNode, secondNode, (gf.getEdge(firstNode, secondNode).weight - smallestCapacity));
				}


				// refresh path for next iteration
				path = pathDFS(source, destination, gf);
			}
		}
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesnt exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
