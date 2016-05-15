package kmeans;

import java.util.ArrayList;
import java.util.List;

public class Point {
	
	private List<Float> property = new ArrayList<>();
	private int index = 0;
	
	public Point(float[] b)
	{
		for(int i=0;i<b.length;i++)
		{
			property.add(b[i]);
		}
	}
	
	public List<Float> getProperty()
	{
		return property;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int i)
	{
		this.index = i;
	}
	
	public void setProperty(float[] b)
	{
		property.clear();
		for(int i=0;i<b.length;i++)
		{
			property.add(b[i]);
		}
		
	}
	
	
	
	

}
