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
	
	private List<Sample> dataset = new ArrayList<>();//���ݼ�
	private HashMap<String,Integer> labellist = new HashMap<String,Integer>();//���ǩ�ʵ�
	private String path;//�����ļ���·��
	private HashMap<Integer,Set<String>> featurelist = new HashMap<Integer,Set<String>>();//����������������ȡֵ
	
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
	 * �������ݲ��������ǩ
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
			String[] tokens = line.split("\t");//���ݿո�ָ����ݣ����洢�����б�
			int length = tokens.length;//�����б�ĳ���
			String[] property = new String[length-1];//��������
			String label = tokens[length-1];//���ݵı�ǩ
			for(int i=0;i<length-1;i++)
			{
				property[i] = tokens[i];		
			}
			Sample sample = new Sample();
			sample.setProperty(property);
			sample.setLabel(label);
			dataset.add(sample);
			/**�������ǩ������*/
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
	 * ����������
	 */
	public void creatFeature()
	{
		if(dataset.size() == 0)
			return;
		int size = dataset.get(0).getProperty().size();//һ�����ݵ������ĸ���
		for(int i=0;i<size;i++)
		{
			Set<String> feature = new HashSet<>();//������ȡֵ
			/**����������ȡֵ��*/
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
