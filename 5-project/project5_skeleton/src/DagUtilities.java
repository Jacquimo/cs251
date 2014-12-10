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
    	
    	if (G.topSort == null) {
    		int count = 0;
    		Queue<Node> src = (Queue<Node>)((LinkedList<Node>) G.sources).clone();
    		Node[] topSort = new Node[G.v];
    		G.topSort = topSort;
    	
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
    	}
    	else {
    		for (int k = 0; k < G.topSort.length; ++k) {
    			top[G.topSort[k].id] = k;
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
        // If the longest paths have already been computed, just return the longest path to the sink
    	if (G.longPaths != null)
        	return G.longPaths[G.topSort[G.topSort.length - 1].id];
    	
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
        // return the longest path to the sink (the last node in the topological sort), since there is guaranteed to only be 1 sink in the graph
        return longPaths[topSort[topSort.length - 1].id];
    }

    /**
     * Task 3
     * This function  generates a schedule that completes all manufacturing steps in the shortest
     * production span.
     * 
     * @param dag G
     * @return a schedule
     */
    public static Schedule minProdSpanSchedule(Digraph G) {
    	Schedule sch = null;
    	Node[] top = G.topSort;
    	
    	// Set the span to be the longest path to the sink + 1
    	if (G.longPaths == null)
        	sch = new Schedule(longestPath(G) + 1);
    	else
    		sch = new Schedule(G.longPaths[top[top.length - 1].id] + 1);
    	
    	Queue<Node> bfs = new LinkedList<Node>();
    	int[] parentsRemoved = new int[top.length];
    	
    	// Add all of the sources to the BFS queue
    	// Sources will be the first 'k' nodes in the topological sort without parents, where k <= n - 1 (as there must always be 1 sink)
    	for (int i = 0; i < top.length && top[i].parents.isEmpty(); ++i) {
    		bfs.add(top[i]);
    	}
    	
    	// Perform the BFS traversal of the graph
    	
    	int step = 0;
    	while (!bfs.isEmpty()) {
    		int sizeOfStep = bfs.size();
    		
    		// Add the nodes to the schedule and update the BFS queue with the node's children
    		for (int j = 0; j < sizeOfStep; ++j) {
    			Node node = bfs.remove();
    			sch.addToSchedule(step, node.id);
    			
    			// Update BFS queue with children as appropriate
    			for (int k = 0; k < node.children.size(); ++k) {
    				Node child = node.children.get(k);
    				
    				parentsRemoved[child.id]--;
    				
    				// If all of the parent tasks are scheduled to be deleted, then this task can be completed
    				if (parentsRemoved[child.id] * -1 >= child.parents.size())
    					bfs.add(child);
    			}
    		}
    		
    		++step;
    	}
        
        return sch;
    }

    /**
     * Task 4
     * This function generates a schedule using at most k stations. 
     * @param dag G
     * @param the number of stations k
     * @return a schedule
     */
    public static Schedule spanKStations(Digraph G, int k) {    	
    	
    	if (k == 1)
    		return retTopSortSchedule(G);
    	
    	if (G.topSort == null)
    		topologicalSort(G);
    	
    	Node[] top = G.topSort;
    	int stationsUsed[] = new int[top.length - 1]; // The number 'm' stations in use at a certain level/time step, where m < k
    	int largestParentLevel[] = new int[top.length];
    	int step[] = new int[top.length]; // the step at which a specified node is executed
    	
    	for (int i = 0; i < top.length; ++i) {
    		Node node = top[i];
    		
    		// Determine step to place node at based on parent step and if current steps are full
    		int level = largestParentLevel[node.id] + 1;
    		if (node.parents.isEmpty())
    			level = 0;
    		
    		while (stationsUsed[level] >= k)
    			++level;
    		//sch.addToSchedule(level, node.id);
    		step[node.id] = level;
    		stationsUsed[level]++;
    		
    		// Update largestParentLevel values for the children
    		for (int j = 0; j < node.children.size(); ++j) {
    			Node child = node.children.get(j);
    			if (largestParentLevel[child.id] < level)
    				largestParentLevel[child.id] = level;
    		}
    	}
    	
    	// Create a schedule with enough steps to accomodate the sink node
    	Schedule sch = new Schedule(step[top[top.length - 1].id] + 1);
    	for (int i = 0; i < step.length; ++i)
    		sch.addToSchedule(step[i], i);
    	
        return sch;
    }
    
    private static Schedule retTopSortSchedule(Digraph G) {
    	Schedule schedule = new Schedule(G.v);
    	Node top[] = G.topSort;
    	
    	for (int i = 0; i < top.length; ++i)
    		schedule.addToSchedule(i, top[i].id);
    	
    	return schedule;
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
