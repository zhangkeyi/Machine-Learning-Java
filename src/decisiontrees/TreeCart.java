package trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TreeCart {
	
	private LoadData loaddata;
	private TreeNode root;
	
	public TreeCart(LoadData loaddata)
	{
		this.loaddata = loaddata;
		root = new TreeNode();
	}
	
	public LoadData getLoadDate()
	{
		return loaddata;
	}
	
	public TreeNode getTree()
	{
		return root;
	}
	
	public float calcShannonEnt(List<Sample> dataset)
	{
		float shannonent = 0;
		int size = dataset.size();
		HashMap<String,Integer> labellist = new HashMap<String,Integer>();
		for(Sample sample : dataset)
		{
			String label = sample.getLabel();
			if(labellist.containsKey(label))
			{
				int count = labellist.get(label)+1;
				labellist.put(label, count);
			}
			else
			{
				labellist.put(label, 1);
			}
		}
		for(Integer count : labellist.values())
		{
			double prob = (double)count/size;
			shannonent -= prob*(Math.log(prob)/Math.log(2));
		}
		return shannonent;
	}
	
	public List<Sample> splitDataSet(List<Sample> dataset,int feature,String featvalue)
	{
		List<Sample> retdataset = new ArrayList<>();
		for(Sample sample : dataset)
		{
			if(sample.getProperty().get(feature).equals(featvalue))
				retdataset.add(sample);
		}
	    return retdataset;
	}
	
	public int chooseBestFeature(List<Sample> dataset,Set<Integer> featureset)
	{
		int bestfeature = 0;
		float max = 0;
	    for(int feature : featureset)
		{
			 HashMap<String,Integer> valuelist = new HashMap<String,Integer>();//������ȡֵ
			 /**����ָ��������ȡֵ��*/
			 for(Sample sample : dataset)
			 {
				 String value = sample.getProperty().get(feature);
				 if(valuelist.containsKey(value))
				 {
					 int count = valuelist.get(value)+1;
					 valuelist.put(value, count); 
				 }
				 else
				 {
					 valuelist.put(value, 1);
				 }
			 }
			 float condientropy = 0;//������
			 for(String value : valuelist.keySet())
			 {
				condientropy += ((double)valuelist.get(value)/dataset.size())*calcShannonEnt(splitDataSet(dataset,feature,value));
			 }
			 
			 float inforgain = calcShannonEnt(dataset)-condientropy;//������Ϣ����
			
			 if(inforgain>max)
			 {
				 max = inforgain;
				 bestfeature = feature;
			 }
		}
		return bestfeature;
	}
	
	public int getMaxLabel(List<Sample> dataset)
	{
		Map<String,Integer> labellist = new HashMap<>();
		/**ͳ�����ݼ�����ĸ���*/
		for(Sample sample : dataset)
		{
			String label = sample.getLabel();
			if(labellist.containsKey(label))
			{
				int count = labellist.get(label)+1;
				labellist.put(label, count);
			}
			else
			{
				labellist.put(label, 1);
			}
		}
		int max = 0;
		for(String label : labellist.keySet())
		{
			if(labellist.get(label)>max)
			{
				max = labellist.get(label);
			}
		}
		return max;
	}
	
	public void creatTree(TreeNode node,List<Sample> dataset)
	{
		Map<String,Integer> labellist = new HashMap<>();
		/**ͳ�����ݼ�����ĸ���*/
		for(Sample sample : dataset)
		{
			String label = sample.getLabel();
			if(labellist.containsKey(label))
			{
				int count = labellist.get(label)+1;
				labellist.put(label, count);
			}
			else
			{
				labellist.put(label, 1);
			}
		}
		
		double accuracy = 0;
		String maxlabel;
		int max = 0;
		for(String label : labellist.keySet())
		{
			if(labellist.get(label)>max)
			{
				max = labellist.get(label);
			}
		}
		accuracy = (double)max/dataset.size();//����ǰ����
		System.out.println("����ǰ�ľ���"+accuracy);
		
		int feature = chooseBestFeature(dataset,loaddata.getFeatureList().keySet());
		/**���㻮�ֺ�ľ���*/
		int count = 0;//������ȷ�ĸ���
		for(String value : loaddata.getFeatureList().get(feature))
		{
			List<Sample> dataset_ = splitDataSet(dataset,feature,value);
			count += getMaxLabel(dataset_);
		}
		double accuracy_ = (double)count/dataset.size();
		System.out.println("����֮��ľ��ȣ�"+accuracy_);
		if(accuracy_<accuracy)
		{
			node.setNodeName((dataset.get(0).getLabel()));
			return;
		}
		
		if(labellist.size() == 1)
		{
			node.setNodeName((dataset.get(0).getLabel()));
			
			return;
		}
			
		/**���ֽڵ�*/
		node.setNodeName(String.valueOf(feature));
		HashSet<TreeNode> childs = new HashSet<>();
		for(String value : loaddata.getFeatureList().get(feature))
		{
			List<Sample> dataset_ = splitDataSet(dataset,feature,value);
			TreeNode child  = new TreeNode(value);
			creatTree(child,dataset_);
			childs.add(child);
		}
		node.setChilds(childs);
	}
	
	public void printChilds(TreeNode node)
	{
		System.out.print(node.getValue());
		System.out.print(node.getNodeName());
		if(node.getChilds()==null)
			return;
		
		for(TreeNode child : node.getChilds())
		{
			System.out.print("{");
			printChilds(child);
			System.out.print("}");
		}
	}
	
	public void creatDecisionTree()
	{
		/**ȡѵ����*/
		creatTree(root,loaddata.getData());
		printChilds(root);
		testTree(root,loaddata.getData().get(0));
	}
	
	public void testTree(TreeNode node,Sample sample)
	{
		if(node.getChilds() == null)
		{
			System.out.println("���Խ��Ϊ��"+node.getNodeName());
			return;
		}
		for(TreeNode child : node.getChilds())
		{
			if(child.getValue().equals(sample.getProperty().get(Integer.parseInt(node.getNodeName()))))
			{
				testTree(child,sample);
			}
		}
	}

	public static void main(String[] args)
	{
		LoadData loaddata = new LoadData("C:/Users/keyi/Desktop/machinelearninginaction/Ch03/lenses.txt");
		TreeCart tree = new TreeCart(loaddata);
		tree.creatDecisionTree();
		
	}

}
