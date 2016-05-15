package trees;

import java.util.ArrayList;
import java.util.List;

public class Sample {
	
	private List<String> property = new ArrayList<>();//一个样本的属性
	private String label;//样本的标签
	
	public List<String> getProperty()
	{
		return property;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public void setProperty(String[] b)
	{
		property.clear();
		for(int i=0;i<b.length;i++)
		{
			property.add(b[i]);
		}
		
	}

}
