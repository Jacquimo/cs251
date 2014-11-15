package part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 * @author ghousto
 * @version 11/14/14
 *
 */
public class Tree {

	private Node[] allNodes;
	private Node root;
	private int size;
	
	public Tree(int numOfNodes) {
		size = numOfNodes;
		allNodes = new Node[size];
		// Initialize all the nodes up front
		for (int i = 0; i < size; ++i)
			allNodes[i] = new Node(i);
	}
	
    /**
     * compute the size of this tree
     * @return number of nodes in tree
     */
    public int size() {
        //TODO add your code here
        return 0;
    }

    /**
     * read a rooted tree from file
     * @param f
     * @return tree rooted at node 0
     */
    public static Tree readTreeFromFile(File f) {
    	// Initialize file scanner
    	Scanner reader = null;
    	try {
			reader = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	Tree tree = null;
    	
    	// Parse first line (i.e. the size of the tree)
    	if (reader.hasNextLine()) {
    		String size = reader.nextLine();
    		tree = new Tree(Integer.parseInt(size));
    	}
    	
    	// Assume that the 0 element is the root
    	tree.root = tree.allNodes[0];
    	
    	// Build all of the connections of the tree
    	while (reader.hasNextLine()) {
    		String parentLine = reader.nextLine();
    		String[] entryStrings = parentLine.split(" ");
    		// Get the parent node
    		int nodeNum = Integer.parseInt(entryStrings[0]); // assumes there is at least 1 node specified
    		Node parent = tree.allNodes[nodeNum];
    		
    		// Parse all children and add them to the parent
    		for (int i = 1; i < entryStrings.length; ++i) {
    			int nodeID = Integer.parseInt(entryStrings[i]);
    			parent.addChild(tree.allNodes[nodeID]);
    		}
    	}
        
        return tree;
    }
    /**
     * recursively compute the height of the tree
     * @param t - Tree
     * @return height of the tree
     */
    public static int recursiveHeight(Tree t) {
        return recursiveHelper(t.root);
    }
    
    public static int recursiveHelper(Node node) {
    	// May not even need this basis case, as the recursion should never occur on a null node
    	if (node == null) return 0;
    	
    	int maxHeightOfChild = -1; // Initialized to -1 so that if no children, returns height of 0 for node
    	ArrayList<Node> children = node.children;
    	
    	// for-loop should only occur if there are non-null children
    	// Get maximum child height
    	for (int i = 0; i < children.size(); ++i) {
    		int childHeight = recursiveHelper(children.get(i));
    		if (childHeight > maxHeightOfChild)
    			maxHeightOfChild = childHeight;
    	}
    	
    	// Add 1 b/c the height is max child height plus 1 to include the child
    	return maxHeightOfChild + 1; 
    }
    
    /**
     * iteratively compute the height of the tree
     * Use BFS to iteratively traverse tree and determine height. Can easily use BFS because there are no cycles
     * @param t - Tree
     * @return height of the tree
     */
    public static int iterativeHeight(Tree t) {
        int height = 0;

        Queue<Node> queue = new LinkedList<Node>(t.root.children);
        
        while(!queue.isEmpty()) {
        	++height;
        	// Iterate over all the elements in queue and add their children to the queue (guaranteed no cycles)
        	int numNodes = queue.size();
        	for(int i = 0; i < numNodes; ++i) {
        		Node node = queue.remove();
        		queue.addAll(node.children);
        	}
        }
        
        return height;
    }
}





