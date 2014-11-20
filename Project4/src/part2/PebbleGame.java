package part2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * 
 * @author ghousto
 * @version 11/18/14
 *
 */
public class PebbleGame {

    /**
     * compute optimal pebbling recursively
     * @param t - Tree to pebble
     * @return optimal pebbling of tree rooted at given node
     */
    public static boolean[] recursiveOptimalPebbling(Tree t) {
    	// Randomly select a root node if one hasn't been selected elsewhere
    	if (t.root == null)
    		t.root = t.allNodes[(new Random()).nextInt(t.allNodes.length)];
    	
    	Node root = t.root;
    	double[] peb = new double[t.allNodes.length];
    	double[] notpeb = new double[t.allNodes.length];
    	
    	// Because of recursion, these 2 calls should fill every single entry in the pebbled and not-pebbled arrays
    	root.pebbledProfit(peb, notpeb, root);
    	root.notpebbledProfit(peb, notpeb, root);
    	
    	boolean[] pebbling = new boolean[t.allNodes.length];
    	pebbling = optimalPebbling(pebbling, peb, notpeb, root);
    	
        return pebbling;
    }
    
    /**
     * Given precalculated pebbled and not-pebbled profit arrays, use a BFS traversal to determine optimal pebbling of nodes
     * @param pebbling - boolean array containing whether a node is pebbled or not
     * @param peb - array containing the optimal profits if each node is pebbled
     * @param notpeb - array containing the optimal profits if each node isn't pebbled
     * @param root - the root node of the tree
     * @return
     */
    private static boolean[] optimalPebbling(boolean[] pebbling, double[] peb, double[] notpeb, Node root) {
    	if (root == null)
    		return pebbling;
    	
    	Queue<Node> bfsNodes = new LinkedList<Node>();
    	bfsNodes.add(root);
    	
    	while (!bfsNodes.isEmpty()) {
    		Node node = bfsNodes.remove();
    		ArrayList<Node> children = node.children;
    		
    		// If not-pebbling and pebbling have equal profits, the default behavior is to not pebble
    		if (notpeb[node.id] > peb[node.id])
    			bfsNodes.addAll(children);
    		else {
    			pebbling[node.id] = true;
    			// To skip chilren check (and default make them not-pebbled) add the children's children to BFS
    			for (int i = 0; i < children.size(); ++i)
    				bfsNodes.addAll(children.get(i).children);
    		}
    		
    	}
    	
    	return pebbling;
    }
    
    /**
     * use brute force to find optimal pebbling
     * uses bit-shifting and bit-masking to attain best performance since this performance will be awful already
     * @param t - Tree
     * @return pebbling achieving maximal profit
     */
    public static boolean[] bruteForceOptimalPebbling(Tree t) {
    	// This will only work for up to 63 nodes because of the limit of the long data type (i.e. overflow)
    	//long outerBound = 1 << t.allNodes.length; // 2^n
    	
    	// Use BigInteger class to get 2^n w/ 1 extra high-order bit so that it's not negative
    	BigInteger outerBound = BigInteger.valueOf(3 << (t.allNodes.length + 1)); // use decimal 3 so that I get 1 more bit field than I need
    	outerBound = outerBound.xor(BigInteger.valueOf(1 << (t.allNodes.length + 1))); // clear high-order bit so that number isn't negative
    	boolean[] bestPebbling = null;
    	double maxProfit = -1.0;
    	BigInteger bigZero = BigInteger.valueOf(0);
    	BigInteger bigOne = BigInteger.valueOf(1);
    	
    	// loop of bit-pebbling values
        for (BigInteger i = BigInteger.valueOf(1); outerBound.compareTo(i) > 0; i = i.add(bigOne)) {
        	boolean[] pebbling = new boolean[t.allNodes.length];
        	
        	// Build the pebbling array from the bits in the counter 'i'
        	for (int j = 0; j < t.allNodes.length; ++j) {
        		//boolean shouldPebble = ((1 << j) & i) != 0;
        		boolean shouldPebble = i.and(BigInteger.valueOf(1 << j)).compareTo(bigZero) != 0;
        		if (shouldPebble)
        			pebbling[j] = true;
        	}
        	
        	// Check for validity and if this is the best profit tree we've seen so far
        	if (isValid(t, pebbling)) {
        		double profit = profit(t, pebbling);
        		if (profit > maxProfit) {
        			maxProfit = profit;
        			bestPebbling = pebbling;
        		}
        	}
        }
        
        return bestPebbling;
    }
    /**
     * determine if pebbling is valid for tree
     * @param t - Tree
     * @param pebble - pebbling
     * @return true if pebbling is valid
     */
    public static boolean isValid(Tree t, boolean[] pebble) {
    	if (t == null || pebble == null)
    		return false;
    	
        for (int i = 0; i < pebble.length; ++i) {
        	
        	if (pebble[i]) {
        		ArrayList<Node> pNeighbors = t.allNodes[i].neighbors;
        		for (int j = 0; j < pNeighbors.size(); ++j) {
        			if (pebble[pNeighbors.get(j).id])
        				return false;
        		}
        	}
        }
        return true;
    }
    /**
     * compute profit of pebbled tree
     * @param t - Tree
     * @param pebble
     * @return the total profit of the pebbling.
     */
    public static double profit(Tree t, boolean[] pebble) {
    	if (t == null || pebble == null)
    		return 0.0;
    	
    	double profit = 0.0;
        
        for (int i = 0; i < pebble.length; ++i)
        	if (pebble[i]) {
        		profit += t.allNodes[i].profit;
        	}
        
        return profit;
    }
}
