package trees;

import java.util.HashSet;
import java.util.List;

public class TreeNode {
	
	
	private String value;//��һ�ڵ��ȡֵ
	private String nodename;//�ڵ�����ֻ��߷���ı�ǩ
	private HashSet<TreeNode> childs;
	
	public TreeNode()
	{}
	
	public TreeNode(String value)
	{
		this.value = value;
	}
	public TreeNode(String value,String nodename)
	{
		this.value = value;
		this.nodename = nodename;
	}
	
	public String getNodeName()
	{
		return nodename;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public HashSet<TreeNode> getChilds()
	{
		return childs;
	}
	
	public void setChilds(HashSet<TreeNode> childs)
	{
		this.childs = childs;
	}
	
	public void setNodeName(String name)
	{
		this.nodename = name;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	

}
