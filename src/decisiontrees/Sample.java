package trees;

import java.util.ArrayList;
import java.util.List;

public class Sample {
	
	private List<String> property = new ArrayList<>();//һ������������
	private String label;//�����ı�ǩ
	
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
