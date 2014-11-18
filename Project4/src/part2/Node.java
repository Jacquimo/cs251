package part2;

import java.util.ArrayList;

/**
 * 
 * @author TODO ghousto
 * @version TODO 11/18/14
 *
 */
public class Node {
    
	protected int id;
	protected double profit;
	protected ArrayList<Node> neighbors;
	
	public Node(int nodeID) {
		this(nodeID, 0.0);
	}
	
	public Node(int nodeID, double nodeProfit) {
		id = nodeID;
		profit = nodeProfit;
		neighbors = new ArrayList<Node>();
	}
	
	/**
     * compute the size of the tree rooted at this node
     * @return number of nodes in the tree rooted at this node
     */
    public int size() {
        //TODO add your code here
        return 0;
    }
    /**
     * add node n to this node's children
     * @param n
     */
    public void addChild(Node n) {
        neighbors.add(n);
    }
}
