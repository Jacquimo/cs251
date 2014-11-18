package part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author TODO ghousto
 * @version TODO 11/18/14
 *
 */
public class Tree {
	
	private Node[] allNodes;
	private int size;
	protected Boolean[] pebbling;
	
	public Tree(int numOfNodes) {
		size = numOfNodes;
		allNodes = new Node[size];
		// Initialize all the nodes up front
		for (int i = 0; i < size; ++i)
			allNodes[i] = new Node(i);
		
		pebbling = new Boolean[numOfNodes];
	}
	
    /**
     * read and root a free tree from file
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
}
