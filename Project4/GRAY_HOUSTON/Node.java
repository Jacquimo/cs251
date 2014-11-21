package part2;

import java.util.ArrayList;

/**
 * 
 * @author ghousto
 * @version 11/18/14
 *
 */
public class Node {

	protected int id;
	protected double profit;
	protected ArrayList<Node> neighbors;
	protected ArrayList<Node> children; 
	
	public Node(int nodeID) {
		this(nodeID, 0.0);
	}
	
	public Node(int nodeID, double nodeProfit) {
		id = nodeID;
		profit = nodeProfit;
		neighbors = new ArrayList<Node>();
		children = null;
	}

	
	public double pebbledProfit(double[] peb, double[] notpeb, Node parent) {
		// If the profit has already been calculated (i.e. it has a non-zero value), return it
		if (peb[id] > 0.000001)
			return peb[id];
		
		// Calculate the profit and then store it in the array for future reference
		double totalProfit = this.profit;
		if (children == null) {
			children = (ArrayList<Node>) neighbors.clone();
			// The parent node needs to be removed from the list of neighbors. After this operation, because of
			// the structure of the graphs, all remaining neighbors will become children of this node
			children.remove(parent); 
		}
		
		for (int i = 0; i < children.size(); ++i) {
			Node child = children.get(i);
			totalProfit += child.notpebbledProfit(peb, notpeb, this);
		}
		
		peb[id] = totalProfit;
		return totalProfit;
	}
	
	public double notpebbledProfit(double[] peb, double[] notpeb, Node parent) {
		if (notpeb[id] > 0.000001)
			return notpeb[id];
		
		double totalProfit = 0.0;
		if (children == null) {
			children = (ArrayList<Node>) neighbors.clone();
			// The parent node needs to be removed from the list of neighbors. After this operation, because of
			// the structure of the graphs, all remaining neighbors will become children of this node
			children.remove(parent); 
		}
		
		for (int i = 0; i < children.size(); ++i) {
			Node child = children.get(i);
			double pebProfit = child.pebbledProfit(peb, notpeb, this);
			double notpebProfit = child.notpebbledProfit(peb, notpeb, this);
			
			if (pebProfit > notpebProfit)
				totalProfit += pebProfit;
			else
				totalProfit += notpebProfit;
		}
		
		notpeb[id] = totalProfit;
		return totalProfit;
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
