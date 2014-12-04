import java.util.ArrayList;


public class Node {
	protected int id;
	protected int out_deg;
	protected int in_deg;
	protected ArrayList<Node> children;
	
	public Node(int id) {
		this.id = id;
		out_deg = 0;
		in_deg = 0;
		children = new ArrayList<Node>();
	}
}
