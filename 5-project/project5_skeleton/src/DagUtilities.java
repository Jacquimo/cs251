import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class DagUtilities {

    /** Task 1
     * This function performs a toplogical sort on a graph
     * 
     * @param dag G
     * @return an array containing the topological numbers of each vertex in graph G
     */
    public static int[] topologicalSort(Digraph G) {
        int[] top = new int[G.v];
        int count = 0;
        Queue<Node> src = (Queue<Node>)((LinkedList<Node>) G.sources).clone();
    	
        while (src.size() > 0) {
        	int numSrc = src.size();
        	for (int i = 0; i < numSrc; ++i) {
        		// Update topological sort number for source
        		Node source = src.remove();
        		top[source.id] = count;
        		G.topSort[count] = source;
        		++count;
        		
        		// reset in-degrees array so that it can be used again (in other functions)
				// thereby, when the function ends, the graph will end exactly in the same state it began
				G.in_degs[source.id] = source.parents.size(); 
        		
        		// Loop over children to adjust in-degree values and update sources queue
        		for (int j = 0; j < source.children.size(); ++j) {
        			Node child = source.children.get(j);
        			G.in_degs[child.id]--;
        			if (G.in_degs[child.id] <= 0)
        				src.add(child);
        		}
        	}
        }
        
        return top;
    }

    /**
     * Task 2
     * This function determines the length of the longest path
     * 
     * @param dag G
     * @return length of the longest path
     */
    public static int longestPath(Digraph G) {
        // Get topological sorting to use during weight relaxation
    	Node[] topSort;
        if (G.topSort[0] == null)
        	DagUtilities.topologicalSort(G);
        topSort = G.topSort;
        int[] longPaths = new int[G.v];
        
        for (int i = 0; i < topSort.length; ++i) {
        	Node current = topSort[i];
        	int relaxWeight = longPaths[current.id] + 1;
        	
        	// Relax the distance to all child vertices as appropriate
        	for (int j = 0; j < current.children.size(); ++j) {
        		Node child = current.children.get(j);
        		if (relaxWeight > longPaths[child.id]) {
        			longPaths[child.id] = relaxWeight;
        		}
        	}
        }
    	
        G.longPaths = longPaths;
        return longPaths[topSort[topSort.length - 1].id];
    }

    /**
     * Task 3
     * This fundtion  generates a schedule that completes all manufacturing steps in the shortest
     * production span.
     * 
     * @param dag G
     * @return a schedule
     */
    public static Schedule minProdSpanSchedule(Digraph G) {
        //TODO add your code here
        return null;
    }

    /**
     * Task 4
     * This function generates a schedule using at most k stations. 
     * @param dag G
     * @param the number of stations k
     * @return a schedule
     */
    public static Schedule spanKStations(Digraph G, int k) {
        //TODO add your code here
        return null;
    }

    /**
     * This function reads a graph from an input file
     * 
     * @param file
     * @return dag G
     */
    public static Digraph readGraphFromFile(File file) {
        Scanner reader = null;
    	try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	// Get vertex and edge info necessary to initialize Digraph
    	int vertices = -1;
    	int edges = -1;
    	Scanner headerString = new Scanner(reader.nextLine());
    	vertices = headerString.nextInt();
    	headerString.next();
    	edges = headerString.nextInt();
    	headerString.close();
    	
    	// Create digraph
        Digraph graph = new Digraph(vertices, edges);
        
        // Read from the file and update the nodes
        for (int i = 0; i < graph.v; ++i) {
        	Node node = graph.adj[i];
        	reader.nextLine();
        	
        	// Update the adjacency list for parents (In) and the respective in-degree and out-degree of the respective nodes
        	Scanner parser = new Scanner(reader.nextLine());
        	parser.next();
        	while (parser.hasNextInt()) {
        		Node parent = graph.adj[parser.nextInt()];
        		//parser.nextInt();
        		//node.in_deg++;
        		node.parents.add(parent);
        	}
        	parser.close();
        	
        	graph.in_degs[node.id] = node.parents.size();
        	if (node.parents.size() == 0)
        		graph.sources.add(node);
        	
        	// Update adjacency list for children (Out) and the respective in-degree and out-degree of the respective nodes
        	parser = new Scanner(reader.nextLine());
        	parser.next();
        	while (parser.hasNextInt()) {
        		Node child = graph.adj[parser.nextInt()];
        		node.children.add(child);
        		//node.out_deg++;
        	}
        	parser.close();
        	
        	if (node.children.size() <= 0)
        		graph.sink = node;
        }
    	
    	reader.close();
    	return graph;
    }

}
