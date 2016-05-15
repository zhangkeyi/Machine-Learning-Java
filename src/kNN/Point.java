package kNN;

import java.util.ArrayList;
import java.util.List;

public class Point {
	
	private List<Double> property = new ArrayList<>();
	private String label;
	
	public Point(double[] b,String label)
	{
		for(int i = 0;i<b.length;i++)
		{
			property.add(b[i]);
		}
		this.label = label;
	}
	
	public List<Double> getProperty()
	{
		return property;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setProperty(List<Double> property)
	{
		this.property = property;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
 
}
