package kNN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class kNN {
	
	private Data data;
	private int k;
	private String path;
	private List<Point> trainset = new ArrayList<>();
	
	public kNN(String path,int k)
	{
		data = new Data(path);
		this.k = k;
	}
	
	public double disBetweenPoint(Point o1,Point o2)
	{
		double distance;
		double sum = 0;
		for(int i = 0;i<o1.getProperty().size();i++)
		{
			sum += Math.pow((o1.getProperty().get(i) - o2.getProperty().get(i)),2);
		}
		distance = Math.pow(sum, 0.5);
		return distance;
	}
	
	public String knn(Point point)
	{
		String label = null;
		Map<Double,Point> maplist = new HashMap<>();
		List<Double> distlist = new ArrayList<>();
		for(Point dot : trainset)
		{
			double dis = disBetweenPoint(point,dot);
			distlist.add(dis);
			maplist.put(dis, dot);
		}
		Collections.sort(distlist);
		Map<String,Integer> countlist = new HashMap<>();
		for(int i = 0;i<k;i++)
		{
			String label_ = maplist.get(distlist.get(i)).getLabel();
			if(countlist.containsKey(label_))
			{
				int count = countlist.get(label_)+1;
				countlist.put(label_, count);
			}
			else
				countlist.put(label_, 1);
			
		}
		int max = 0;
		for(String str : countlist.keySet())
		{
			if(countlist.get(str)>max)
			{
				max = countlist.get(str);
				label = str;
			}
		}
		
		return label;
	}
	
	public void test(double rate)
	{
		int number = (int)(data.getDataSet().size()*rate);
		int[] list = (new RandCent(0,data.getDataSet().size(),number)).randCreate();
		for(int i = 0;i<list.length;i++)
		{
			trainset.add(data.getDataSet().get(list[i]));
		}
		
		List<Point> testset = new ArrayList<>();
		testset.addAll(data.getDataSet());
		testset.removeAll(trainset);
		int errorrate = 0;
		for(Point point : testset)
		{
			for(double num : point.getProperty())
			{
				System.out.print(num+",");
			}
			System.out.print(point.getLabel()+",");
			String result = knn(point);
			System.out.println("test:"+result);
			if(!result.equals(point.getLabel()))
				errorrate++;
		}
		System.out.println("the error rate:"+(double)errorrate/testset.size());
		System.out.println(testset.size());
	}
	
	public static void main(String[] args)
	{
		kNN knn = new kNN("C:/Users/keyi/Desktop/machinelearninginaction/Ch02/datingTestSet.txt",3);
		knn.test(0.9);
	}

}
