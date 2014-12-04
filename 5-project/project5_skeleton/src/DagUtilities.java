import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DagUtilities {

    /** Task 1
     * This function performs a toplogical sort on a graph
     * 
     * @param dag G
     * @return an array containing the topological numbers of each vertex in graph G
     */
    public static int[] topologicalSort(Digraph G) {
        // TODO add your code here
        return null;
    }

    /**
     * Task 2
     * This function determines the length of the longest path
     * 
     * @param dag G
     * @return length of the longest path
     */
    public static int longestPath(Digraph G) {
        return -1;
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
        		//Node parent = graph.adj[parser.nextInt()];
        		parser.nextInt();
        		node.in_deg++;
        	}
        	parser.close();
        	
        	if (node.in_deg == 0)
        		graph.sources.add(node);
        	
        	// Update adjacency list for children (Out) and the respective in-degree and out-degree of the respective nodes
        	parser = new Scanner(reader.nextLine());
        	parser.next();
        	while (parser.hasNextInt()) {
        		Node child = graph.adj[parser.nextInt()];
        		node.children.add(child);
        		node.out_deg++;
        	}
        	parser.close();
        		
        }
    	
    	reader.close();
    	return graph;
    }

}
