package apriori;

import java.util.List;

public class Rules {
	
	private List<Integer> left;//描述规则的前件
	private List<Integer> right;//描述规则的后件
	private double conf;//规则的可信度
	
	
	public Rules()
	{}
	
	public Rules(List<Integer> left,List<Integer> right)
	{
		this.left = left;
		this.right = right;
	}
	
	public List<Integer> getLeft()
	{
		return left;
	}
	
	public List<Integer> getRight()
	{
		return right;
	}
	
	public double getConf()
	{
		return conf;
	}
	
	public void setConf(double conf)
	{
		this.conf = conf;
	}
	
	public void setLeft(List<Integer> left)
	{
		this.left = left;
	}
	
	public void setRight(List<Integer> right)
	{
		this.right = right;
	}

}
