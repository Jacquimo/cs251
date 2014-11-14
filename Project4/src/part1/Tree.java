package part1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author ghousto
 * @version 11/14/14
 *
 */
public class Tree {

	private ArrayList<Node> adjacencyLS;
	private Node root;
	
	public Tree(int numOfNodes) {
		adjacencyLS = new ArrayList<Node>(numOfNodes);
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
    	
    	if (reader.hasNextLine()) {
    		String rootNode = reader.nextLine();
    		String[] entrieStrings = rootNode.split(" ");
    	}
    	
    	// Parse all nodes and put their connections into the 
        
        return tree;
    }
    /**
     * recursively compute the height of the tree
     * @param t - Tree
     * @return height of the tree
     */
    public static int recursiveHeight(Tree t) {
        //TODO add your code here
        return 0;
    }
    /**
     * iteratively compute the height of the tree
     * @param t - Tree
     * @return height of the tree
     */
    public static int iterativeHeight(Tree t) {
        //TODO add your code here
        return 0;
    }
}
