package trees;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class LoadData {
	
	private List<Sample> dataset = new ArrayList<>();//数据集
	private HashMap<String,Integer> labellist = new HashMap<String,Integer>();//类标签词典
	private String path;//数据文件的路径
	private HashMap<Integer,Set<String>> featurelist = new HashMap<Integer,Set<String>>();//特征集及各特征的取值
	
	public LoadData(String path)
	{
		this.path = path;
		loadData();
		creatFeature();
	}
	
	public List<Sample> getData()
	{
		return dataset;
	}
	
	public HashMap<String,Integer> getLabelList()
	{
		return labellist;
	}
	
	public String getFilePath()
	{
		return path;
	}
	
	public  HashMap<Integer,Set<String>> getFeatureList()
	{
		return featurelist; 
	}
	/*
	 * 加载数据并生成类标签
	 */
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
			String[] tokens = line.split("\t");//根据空格分隔数据，并存储数组列表
			int length = tokens.length;//数据列表的长度
			String[] property = new String[length-1];//属性数组
			String label = tokens[length-1];//数据的标签
			for(int i=0;i<length-1;i++)
			{
				property[i] = tokens[i];		
			}
			Sample sample = new Sample();
			sample.setProperty(property);
			sample.setLabel(label);
			dataset.add(sample);
			/**生成类标签并计数*/
			if(labellist.containsKey(label))
			{
				int newvalue = labellist.get(label)+1;
				labellist.put(tokens[length-1],newvalue);
			}
			else 
			{
				labellist.put(label, 1);
			}
		}
	}
	/*
	 * 生成特征集
	 */
	public void creatFeature()
	{
		if(dataset.size() == 0)
			return;
		int size = dataset.get(0).getProperty().size();//一条数据的特征的个数
		for(int i=0;i<size;i++)
		{
			Set<String> feature = new HashSet<>();//特征的取值
			/**生成特征的取值集*/
			for(Sample sample : dataset)
			{
				feature.add(sample.getProperty().get(i));
			}
			featurelist.put(i, feature);
		}
	}
	
	
	
//	public static void main(String[] args)
//	{
//		LoadData load = new LoadData("C:/Users/keyi/Desktop/machinelearninginaction/Ch03/lenses.txt");
//		load.creatFeature();
//		System.out.println("lllllllllllllllllllllll");
//    }
	
}
