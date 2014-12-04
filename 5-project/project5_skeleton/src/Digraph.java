import java.util.LinkedList;
import java.util.Queue;


/**
 * 
 * This is the graph structure
 *
 */
public class Digraph
{	
	protected Node[] adj; // "adjacency list" - indexable array of all of the nodes
	protected int v; // the number of vertices
	protected int e; // the number of edges
	protected Queue<Node> sources;
	
	public Digraph(int n, int m) {
		v = n;
		e = m;
		sources = new LinkedList<Node>();
		
		// Make the adjacency list and create all the Node objects
		adj = new Node[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new Node(i);
	}
}