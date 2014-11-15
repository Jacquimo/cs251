package part1;

import java.util.ArrayList;

/**
 * 
 * @author TODO username
 * @version TODO date
 *
 */
public class Node {
    
	protected int nodeNum;
	protected ArrayList<Node> children;

	
	public Node(int nodeNumber) {
		children = new ArrayList<Node>();
		nodeNum = nodeNumber;
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
        children.add(n);
    }
}
