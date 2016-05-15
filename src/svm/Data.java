package svm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
	
	private Point dot;
	private List<Point> dataset = new ArrayList<>();
	private String path;
	
	public Data(String path)
	{
		this.path = path;
		loadData();
	}
	
	public List<Point> getData()
	{
		return dataset;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void loadData()
	{
		Scanner sc = null;
		try {
			sc = new Scanner(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(sc.hasNext())
		{
			String line = sc.nextLine();
			String[] tokens = line.split("\t");
			int len = tokens.length;
			List<Double> property = new ArrayList<>();
			for(int i = 0;i<len-1;i++)
			{
				property.add(Double.parseDouble(tokens[i]));
			}
			int label = Integer.parseInt(tokens[len-1]);
			dot = new Point(property,label);
			dataset.add(dot);
		}
	}
	
//	public static void main(String[] args)
//	{
//		Data data = new Data("C:/Users/keyi/Desktop/machinelearninginaction/Ch06/testSet.txt");
//		List<Point> dataset = data.getData();
//		for(Point point : dataset)
//		{
//			List<Double> property = point.getProperty();
//			for(double a : property)
//			{
//				System.out.print(a+",");
//			}
//			System.out.println(point.getLabel());
//		}
//		System.out.println(dataset.size());
//	}

}
