// Name: Alex Hale
// ID: 260672475
// Collaborators: none

import java.util.*;

public class BellmanFord{

	
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */

        int nbNodes = g.getNbNodes();
        int nbEdges = g.getEdges().size();
        distances = new int[nbNodes];
        predecessors = new int[nbNodes];
        this.source = source;

        // initialize distances as infinite except the distance from the source to the source
        for (int i = 0; i < distances.length; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        distances[source] = 0;

        // relax all edges nbNodes - 1 times
        for (int i = 0; i < nbNodes; i++) {
            for (Edge e : g.getEdges()) {
                // relax edge
                if (distances[e.nodes[0]] != Integer.MAX_VALUE && (distances[e.nodes[0]] + e.weight) < distances[e.nodes[1]]) {
                    distances[e.nodes[1]] = distances[e.nodes[0]] + e.weight;
                }
            }
        }

        // check if g contains a negative cycle
        for (Edge e : g.getEdges()) {
            if (distances[e.nodes[0]] != Integer.MAX_VALUE && (distances[e.nodes[0]] + e.weight) < distances[e.nodes[1]]) {
                // if there's a negative weight cycle, throw an exception and don't allow the constructor to terminate
                throw new NegativeWeightException("This graph contains a negative-weight cycle!");
            }
        }

        // fill predecessors array
        for (int i = 0; i < nbNodes; i++) {
            // default value indicating no predecessor
            predecessors[i] = -1;
            
            if (distances[i] == Integer.MAX_VALUE) {
                // if the distance is still infinite, this node is not reachable
                predecessors[i] = Integer.MAX_VALUE;
            } else {
                // scan the distances
                for (int j = 0; j < nbNodes; j++) {
                    // if there's an edge from node j to node i
                    Edge e = g.getEdge(j, i);

                    if (e != null && e.weight != 0) {

                        // if the shortest-path distance at node j is e.weight less than the shortest-path
                        // distance at node i, j is i's predecessor
                        if (distances[j] == distances[i] - e.weight) {
                            predecessors[i] = j;
                        }
                    }
                }
            }
        }
    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If path doesn't exist an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        ArrayList<Integer> path = new ArrayList<Integer>();
        int current = destination;
        path.add(current);
        int iterations = 0;
        while (path.get(path.size() - 1) != this.source && iterations < distances.length - 1) {
            if (current == -1 || current == Integer.MAX_VALUE) {
                throw new PathDoesNotExistException("No path from source to destination found.");
            } else {
                current = predecessors[current];
                path.add(current);
                iterations++;
            }   
        }

        if (path.get(path.size() - 1) != this.source) {
            // a path doesn't exist, throw an exception
            throw new PathDoesNotExistException("No path from source to destination found.");
        }

        // construct int array, ordered from source to destination
        int[] result = new int[path.size()];
        for (int i = path.size() - 1; i >= 0; i--) {
            result[i] = path.get(0);
            path.remove(0);
        }

        return result;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
