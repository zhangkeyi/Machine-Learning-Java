package fpgrowth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Data
{
	private Map<Items,Integer> datamap = new HashMap<>();
	private String _path;
	private Map<String,Integer> itemcount = new HashMap<>();
	
	public Data(String _path)
	{
		super();
		this._path = _path;
		loadData();
	}

	public Map<Items, Integer> getDatamap()
	{
		return datamap;
	}


	public String get_path()
	{
		return _path;
	}

	public void set_path(String _path)
	{
		this._path = _path;
	}

	public Map<String, Integer> getItemcount()
	{
		return itemcount;
	}

	public void loadData()
	{
		Scanner in = null;
		try
		{
			in = new Scanner(new FileInputStream(_path));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count = 0;
		while(in.hasNext()&&count<100000)
		{
			String line = in.nextLine();
			String[] tokens = line.split(" ");
			for(int i=0;i<tokens.length;i++)
			{
				if(itemcount.containsKey(tokens[i]))
				{
					int newcount = itemcount.get(tokens[i])+1;
					itemcount.put(tokens[i], newcount);
				}
				else
				{
					itemcount.put(tokens[i],1);
				}
			}
			Items affair = new Items(tokens);
			if(datamap.containsKey(affair))
			{
				int cnt = datamap.get(affair)+1;
				datamap.put(affair, cnt);
			}
			else
				datamap.put(affair, 1);
			count++;
		}
		
	}
	
//	public static void main(String[] args)
//	{
//		long time1 = System.currentTimeMillis();
//		Data data = new Data("C:/Users/keyi/Desktop/machinelearninginaction/Ch12/data.txt");
//		Map<Items,Integer> dataset = data.getDatamap();
//		for(Items items : dataset.keySet())
//		{
//			for(String a : items.getElements())
//			{
//				System.out.print(a+",");
//			}
//			System.out.println(dataset.get(items));
//		}
////		System.out.println("the size : "+dataset.size());
////		Map<String,Integer> itemcount = data.getItemcount();
////		for(String str : itemcount.keySet())
////		{
////			System.out.println("item: "+ str + ","+"count: "+ itemcount.get(str));
////		}
//		long time2 = System.currentTimeMillis();
//		System.out.println("the time of load data : "+ (time2-time1));
//	}
}
