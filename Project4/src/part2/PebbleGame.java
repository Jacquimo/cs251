package part2;

import java.util.ArrayList;

/**
 * 
 * @author TODO ghousto
 * @version TODO 11/18/14
 *
 */
public class PebbleGame {

    /**
     * compute optimal pebbling recursively
     * @param t - Tree to pebble
     * @return optimal pebbling of tree rooted at given node
     */
    public static boolean[] recursiveOptimalPebbling(Tree t) {
        //TODO add your code here
        return null;
    }
    /**
     * use brute force to find optimal pebbling
     * uses bit-shifting and bit-masking to attain best performance since this performance will be awful already
     * @param t - Tree
     * @return pebbling achieving maximal profit
     */
    public static boolean[] bruteForceOptimalPebbling(Tree t) {
    	// This will only work for up to 63 nodes because of the limit of the long data type (i.e. overflow)

    	long outerBound = 1 << t.allNodes.length; // 2^n
    	boolean[] bestPebbling = null;
    	double maxProfit = -1.0;
    	
        for (long i = 1; i < Math.abs(outerBound); ++i) { // loop of bit-pebbling values
        	boolean[] pebbling = new boolean[t.allNodes.length];
        	
        	// Build the pebbling array from the bits in the counter 'i'
        	for (int j = 0; j < t.allNodes.length; ++j) {
        		boolean shouldPebble = ((1 << j) & i) != 0;
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
