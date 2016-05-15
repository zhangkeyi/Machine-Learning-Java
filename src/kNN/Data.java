package kNN;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Data {
	
	private List<Point> dataset = new ArrayList<>();
	private String path;
	private double[] min;
	private double[] max;
	
	public Data(String path)
	{
		this.path = path;
		loadData();
		getMin_Max();
		autoNorm();
	}
	
	public String getPath()
	{
		return path;
	}
	
	public List<Point> getDataSet()
	{
		return dataset;
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
			double[] property = new double[tokens.length-1];
			String label = tokens[tokens.length-1];
			for(int i=0;i<tokens.length-1;i++)
			{
				property[i] = Float.parseFloat(tokens[i]);		
			}
			Point dot = new Point(property,label);
			dataset.add(dot);
		}
	}
	
	public void getMin_Max()
	{
		if(dataset.size() == 0)
			loadData();
		min = new double[dataset.get(0).getProperty().size()];
		max = new double[dataset.get(0).getProperty().size()];
		for(int i = 0;i<min.length;i++)
		{
			min[i] = Double.MAX_VALUE;
			max[i] = Double.MIN_VALUE;
		}
		for(Point point : dataset)
		{
			List<Double> property = point.getProperty();
			for(int i = 0;i<property.size();i++)
			{
				if(property.get(i)<min[i])
					min[i] = property.get(i);
				if(property.get(i)>max[i])
					max[i] = property.get(i);
			}
		}
	}
	
	public double[] getMin()
	{
		return min;
	}
	
	public double[] getMax()
	{
		return max;
	}

	public void autoNorm()
	{
		if(dataset.size() == 0)
			return;
		for(Point point : dataset)
		{
			List<Double> property = point.getProperty();
			List<Double> newproperty = new ArrayList<>();
			for(int i = 0;i<property.size();i++)
			{
				double newvalue = (property.get(i)-min[i])/(max[i]-min[i]);
				newproperty.add(newvalue);
			}
			point.setProperty(newproperty);
		}
	}
	public static void main(String[] args)
	{
		Data data = new Data("C:/Users/keyi/Desktop/machinelearninginaction/Ch02/datingTestSet.txt");
		List<Point> dataset = data.getDataSet();
		System.out.println(dataset.size());
//		for(Point point : dataset)
//		{
//			List<Double> property = point.getProperty();
//			for(double a : property)
//			{
//				System.out.print(a+",");
//			}
//			System.out.println(point.getLabel());
//		}
//		double[] min = data.getMin();
//		System.out.println("各特征最小值为：");
//		for(int i = 0;i<min.length;i++)
//		{
//			System.out.print(min[i]+",");
//		}
//		double[] max = data.getMax();
//		System.out.println("各特征最大值为：");
//		for(int i = 0;i<max.length;i++)
//		{
//			System.out.print(max[i]+",");
//		}
	}
	
	
	

}
