package fpgrowth;

import java.util.ArrayList;
import java.util.List;

public class Items {
	
	private List<String> elements = new ArrayList<>();
	
	public Items(String[] b)
	{
		if(b.length != 0)
		{
			for(int i = 0;i<b.length;i++)
			{
				elements.add(b[i]);
			}
		}
	}
	
	public Items(List<String> elements)
	{
		super();
		this.elements = elements;
	}

    public List<String> getElements()
	{
		return elements;
	}

    public void setElements(List<String> elements)
	{
		this.elements = elements;
	}

    public void setElements(String[] a)
	{
		if(a == null)
			return;
		elements.clear();
		for(int i = 0;i<a.length;i++)
		{
			elements.add(a[i]);
		}
	}
}
