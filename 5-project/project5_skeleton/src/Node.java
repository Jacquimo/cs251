import java.util.ArrayList;


public class Node {
	protected int id;
	protected ArrayList<Node> parents;
	protected ArrayList<Node> children;
	
	public Node(int id) {
		this.id = id;
		children = new ArrayList<Node>();
		parents = new ArrayList<Node>();
	}
}
