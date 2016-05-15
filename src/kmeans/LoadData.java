package kmeans;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadData {

	private Point dot;
	private String filename;
	private List<Point> datamat;
	private float[] min;
	private float[] max;

	public LoadData(String filename)
	{
		this.filename = filename;
		datamat = new ArrayList<Point>();
		this.readData();
		min = new float[datamat.get(0).getProperty().size()];
		max = new float[datamat.get(0).getProperty().size()];
		this.most_min_max();
	}
	
	
	public List<Point> getData()
	{
		return datamat;
	}
	
	public String getFilePath()
	{
		return filename;
	}
	
	public float[] getMin()
	{
		return min;
	}
	
	public float[] getMax()
	{
		return max;
	}
	public void readData()
	{
		Scanner in = null;
		try {
			in = new Scanner(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(in.hasNext())
		{
			String line = in.nextLine();
			String[] tokens = line.split("\t");
			float[] property = new float[tokens.length];
			for(int i=0;i<tokens.length;i++)
			{
				property[i] = Float.parseFloat(tokens[i]);		
			}
			dot = new Point(property);
			datamat.add(dot);
		}
		
	}
	
	public void most_min_max()
	{
		for(int i=0;i<datamat.get(0).getProperty().size();i++)
		{
			min[i] = Float.MAX_VALUE;
			max[i] = Float.MIN_VALUE;
		}
		for(Point a : datamat)
		{
			for(int i = 0;i<datamat.get(0).getProperty().size();i++)
			{
				if(a.getProperty().get(i)<=min[i])
					min[i] = a.getProperty().get(i);
				if(a.getProperty().get(i)>=max[i])
					max[i] = a.getProperty().get(i);
					
			}
		}
	}
	/*
	public static void main(String[] args)
	{
		LoadData load = new LoadData("C:/Users/keyi/Desktop/machinelearninginaction/Ch10/testSet.txt");
		System.out.println(load.getFilePath());
		
	}
	*/
	
   
	
	
}
