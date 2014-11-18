package part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author ghousto
 * @version 11/18/14
 *
 */
public class Tree {
	
	protected Node[] allNodes;
	protected int size;
	protected Node root;
	
	public Tree(int numOfNodes) {
		size = numOfNodes;
		allNodes = new Node[size];
		// Initialize all the nodes up front
		for (int i = 0; i < size; ++i)
			allNodes[i] = new Node(i);
		root = null;
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
    	//tree.root = tree.allNodes[0];
    	
    	// Build all of the connections of the tree
    	while (reader.hasNextLine()) {
    		String parentLine = reader.nextLine();
    		String[] entryStrings = parentLine.split(" ");
    		
    		// Get the parent node
    		int nodeNum = Integer.parseInt(entryStrings[0]); // assumes there is at least 1 node specified
    		Node parent = tree.allNodes[nodeNum];
    		parent.profit = Double.parseDouble(entryStrings[1]); // assign the profit to the node
    		
    		// Parse all children and add them to the parent
    		for (int i = 2; i < entryStrings.length; ++i) {
    			int nodeID = Integer.parseInt(entryStrings[i]);
    			parent.addChild(tree.allNodes[nodeID]);
    		}
    	}
        
    	reader.close();
        return tree;
    }
}
