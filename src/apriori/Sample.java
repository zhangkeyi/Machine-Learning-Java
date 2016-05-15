package apriori;

import java.util.ArrayList;
import java.util.List;

public class Sample {
	
	private List<Integer> items = new ArrayList<>();
	
	public Sample(int[] b)
	{
		for(int i = 0;i<b.length;i++)
		{
			items.add(b[i]);
		}
	}
	
	public List<Integer> getItems()
	{
		return items;
	}
	
	public void setItems(int[] b)
	{
		if(b.length == 0)
			return;
		for(int i = 0;i<b.length;i++)
		{
			items.add(b[i]);
		}
	}

}
