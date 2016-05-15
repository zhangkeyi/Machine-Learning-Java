package regression;

import java.util.ArrayList;
import java.util.List;

public class Point {
	
	private List<Float> property = new ArrayList<>();

//	public Point(List<Float> property) {
//		super();
//		this.property = property;
//	}
//
//	public List<Float> getProperty() {
//		return property;
//	}
//
//	public void setProperty(List<Float> property) {
//		this.property = property;
//	}
	
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
	
    public void setProperty(float[] b)
	{
		property.clear();
		for(int i=0;i<b.length;i++)
		{
			property.add(b[i]);
		}
		
	}
	
	
	
}
