package part2;

/**
 * 
 * @author TODO username
 * @version TODO date
 *
 */
public class PebbleGame {
    // TODO


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
     * @param t - Tree
     * @return pebbling achieving maximal profit
     */
    public static boolean[] bruteForceOptimalPebbling(Tree t) {
        //TODO add your code here
        return null;
    }
    /**
     * determine if pebbling is valid for tree
     * @param t - Tree
     * @param pebble - pebbling
     * @return true if pebbling is valid
     */
    public static boolean isValid(Tree t, boolean[] pebble) {
        //TODO add your code here
        return false;
    }
    /**
     * compute profit of pebbled tree
     * @param t - Tree
     * @param pebble
     * @return the total profit of the pebbling.
     */
    public static double profit(Tree t, boolean[] pebble) {
        double profit = 0.0;
        
        for (int i = 0; i < pebble.length; ++i)
        	if (pebble[i]) {
        		profit += t.allNodes[i].profit;
        	}
        
        return profit;
    }
}
