package apriori;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Data {

	private List<Sample> dataset = new ArrayList<>();
	private List<List<Integer>> affairset = new ArrayList<>();
	private String path;
	
	public Data(String path)
	{
		this.path = path;
		loadData();
	}
	
	public List<Sample> getData()
	{
		return dataset;
	}
	
	public List<List<Integer>> getAffairset()
	{
		return affairset;
	}
	
	public void loadData()
	{
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(in.hasNext())
		{
			String line = in.nextLine();
			String[] tokens = line.split("\t");                       
			int[] items = new int[tokens.length];
			List<Integer> affair = new ArrayList<>();
			for(int i = 0;i<items.length;i++)
			{
				items[i] = Integer.parseInt(tokens[i].trim());
				if(items[i]==1)
					affair.add(i);
			}
			Sample sample = new Sample(items);
			dataset.add(sample);
			affairset.add(affair);
		}
	}
	
//	public void creatAffair()
//	{
//		if(dataset.size() == 0)
//			return;
//		for(Sample sample : dataset)
//		{
//			int i = 0;
//			Set<Integer> affair = new HashSet<>();
//			for(int a : sample.getItems())
//			{
//				if(a == 1)
//					affair.add(i);
//				i++;
//			}
//			affairset.add(affair);
//		}
//	}
	
	public String getPath()
	{
		return path;
	}
	
//	public static void main(String[] args)
//	{
//		Data data = new Data("C:/Users/keyi/Desktop/machinelearninginaction/Ch11/dataset_617317/617317/datamat.txt");
//		List<List<Integer>> affairs = data.getAffairset();
//		for(List<Integer> affair : affairs)
//		{
//			for(int a : affair)
//			{
//				System.out.print(a+",");
//			}
//			System.out.println();
//		}
//		
//	}
}
