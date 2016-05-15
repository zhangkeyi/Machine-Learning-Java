package fpgrowth;

import java.util.HashMap;
import java.util.Map;

public class Node
{
	private String name;
	private int count;
	private Node parent;
	private Map<String,Node> childs = new HashMap<>();
	private Node nodelink;
	
	public Node()
	{}
	
	public Node(String name)
	{
		this.name = name;
	}
	
	public Node(String name, int count, Node parent, Node nodelink)
	{
		super();
		this.name = name;
		this.count = count;
		this.parent = parent;
		this.nodelink = nodelink;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public void setParent(Node parent)
	{
		this.parent = parent;
	}
	
	public Map<String, Node> getChilds()
	{
		return childs;
	}
	
	public void addChild(Node node)
	{
		if(node == null)
			return;
		childs.put(node.getName(), node);
	}
	
	public Node getNodelink()
	{
		return nodelink;
	}
	
	public void setNodelink(Node nodelink)
	{
		this.nodelink = nodelink;
	}
	
	

}
