package apriori;

import java.util.List;

public class Rules {
	
	private List<Integer> left;//���������ǰ��
	private List<Integer> right;//��������ĺ��
	private double conf;//����Ŀ��Ŷ�
	
	
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
