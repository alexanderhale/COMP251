package A2;
import java.util.*;
import A2.DisjointSets;
import A2.WGraph;

public class Kruskal{

    // find a MST of graph g using Kruskal's algorithm
    public static WGraph kruskal(WGraph g){
        // sort the edges in g
        ArrayList<Edge> edges = g.listOfEdgesSorted();

        // counter keep track of which edges we've tried
        int i = 0;

        // create an empty WGraph
        WGraph A = new WGraph();
        // create an empty DisjointSet
        DisjointSets pGlobal = new DisjointSets(g.getNbNodes());

        while (A.getNbNodes() < g.getNbNodes()) {       // while A isn't yet a spanning tree
            if (IsSafe(pGlobal, edges.get(i))) {
                // if the next edge in the list is safe to add, add it
                A.addEdge(edges.get(i));
            }
            // increment so we select the next edge in the list next time
            i++;
        }
        return A;       // return the MST
    }

    // determine whether an edge e is safe to be added to a disjoint set p
    public static Boolean IsSafe(DisjointSets p, Edge e){
        // let (S, V-S) be any cut that respects A
        // let (u, v) be a light edge crossing (S, V-S)
            // therefore (u, v) is safe for A

        // if (u, v) is a light edge connecting one connected component to another
        // connected component in (V, A), then (u, v) is safe for A

        // store representative nodes for future comparison
        int iRep = p.find(e.nodes[0]);
        int jRep = p.find(e.nodes[1]);

        // add e to p
        p.union(e.nodes[0], e.nodes[1]);

        // if neither of the representatives changed, then a cycle must've been made within one of them
        if (iRep == p.find(e.nodes[0]) && jRep == p.find(e.nodes[0])) {
            return false;
        } else {
            return true;
        }
        // TODO fix this, it's definitely not right
            // an edge can be added to a component without making a cycle and the component can keep the same representative node
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
