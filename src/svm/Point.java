package svm;

import java.util.ArrayList;
import java.util.List;

public class Point {

	private List<Double> property = new ArrayList<>();
	private int label;
	
	public Point(List<Double> property,int label)
	{
		this.property = property;
		this.label = label;
	}
	
	public List<Double> getProperty()
	{
		return property;
	}
	
	public int getLabel()
	{
		return label;
	}
	
	
}
