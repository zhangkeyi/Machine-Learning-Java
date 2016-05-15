package trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeC4_5 {
	
	private LoadData loaddata;
	private TreeNode root;
	
	public TreeC4_5(LoadData loaddata)
	{
		this.loaddata = loaddata;
		root = new TreeNode();
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
			 HashMap<String,Integer> valuelist = new HashMap<String,Integer>();//特征的取值
			 float fature_entropy = 0;//特征的值的熵
			 /**生成指定特征的取值集*/
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
			 float condientropy = 0;//条件熵
			 for(String value : valuelist.keySet())
			 {
				double prob = (double)valuelist.get(value)/dataset.size();
				condientropy += prob*calcShannonEnt(splitDataSet(dataset,feature,value));
				fature_entropy -= (prob)*(Math.log(prob)/Math.log(2));
			 }
			 
			 float inforgain = calcShannonEnt(dataset)-condientropy;//计算信息增益
			 float inforgain_ratio = inforgain/fature_entropy;//计算信息增益比
			 System.out.println(inforgain_ratio+"|"+feature);
			
			 if(inforgain_ratio>max)
			 {
				 max = inforgain_ratio;
				 bestfeature = feature;
			 }
		}
		return bestfeature;
	}
	
	public void creatTree(TreeNode node,List<Sample> dataset)
	{
		Set<String> labellist = new HashSet<>();
		for(Sample sample : dataset)
		{
	      labellist.add(sample.getLabel());
		}
		if(labellist.size() == 1)
		{
			node.setNodeName((dataset.get(0).getLabel()));
			return;
		}
		
		int feature = chooseBestFeature(dataset,loaddata.getFeatureList().keySet());
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
		/**取训练集*/
		creatTree(root,loaddata.getData());
		printChilds(root);
	}
	
	public static void main(String[] args)
	{
		LoadData loaddata = new LoadData("C:/Users/keyi/Desktop/machinelearninginaction/Ch03/lenses_1.txt");
		TreeC4_5 tree = new TreeC4_5(loaddata);
		tree.creatDecisionTree();

		

	}
	

}
