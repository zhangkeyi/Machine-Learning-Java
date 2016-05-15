package trees;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tree {
	
	private LoadData loaddata;//数据集、特征集、特征的取值
	private TreeNode root;//根节点
	
	public Tree(LoadData loaddata)
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
	/*
	 * 输入：数据集
	 * 输出：熵
	 * 计算输入数据集的香农熵
	 */
	public float calcShannonEnt(List<Sample> dataset)
	{
		float shannonent = 0;//香农熵
		int size = dataset.size();//数据集的大小
		HashMap<String,Integer> labellist = new HashMap<String,Integer>();//标签集，<标签名，对应的个数>
		for(Sample sample : dataset)
		{
			String label = sample.getLabel();//样本的标签
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
	/*
	 * 输入：数据集，特征，特征的取值
	 * 输出：划分后的数据集
	 * 根据特征划分数据集
	 */
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
	/*
	 * 输入：数据集，数据的特征集
	 * 输出：特征
	 * 选取最佳特征
	 */
	public int chooseBestFeature(List<Sample> dataset,Set<Integer> featureset)
	{
		int bestfeature = 0;
		float max = 0;
	    for(int feature : featureset)
		{
			 HashMap<String,Integer> valuelist = new HashMap<String,Integer>();//特征的取值
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
			 float condientropy = 0;//条件经验熵
			 for(String value : valuelist.keySet())
			 {
				condientropy += ((double)valuelist.get(value)/dataset.size())*calcShannonEnt(splitDataSet(dataset,feature,value));
			 }
			 
			 float inforgain = calcShannonEnt(dataset)-condientropy;//计算信息增益
             /**取信息增益最大的特征*/			
			 if(inforgain>max)
			 {
				 max = inforgain;
				 bestfeature = feature;
			 }
		}
		return bestfeature;
	}
	/*
	 * 输入：数据集
	 * 递归生成决策树
	 * ID3算法
	 */
	public void creatTree(TreeNode node,List<Sample> dataset)
	{
		Set<String> labellist = new HashSet<>();
		for(Sample sample : dataset)
		{
	      labellist.add(sample.getLabel());
		}
		/**递归的出口*/
		if(labellist.size() == 1)
		{
			node.setNodeName((dataset.get(0).getLabel()));
			return;
		}
		
		int feature = chooseBestFeature(dataset,loaddata.getFeatureList().keySet());//选取特征
		node.setNodeName(String.valueOf(feature));//节点的名字（特征）
		HashSet<TreeNode> childs = new HashSet<>();
		/**对每个特征值生成子节点，并对每个子节点递归*/
		for(String value : loaddata.getFeatureList().get(feature))
		{
			List<Sample> dataset_ = splitDataSet(dataset,feature,value);
			TreeNode child  = new TreeNode(value);
			creatTree(child,dataset_);
			childs.add(child);
		}
		node.setChilds(childs);
	}
	/*
	 * 递归打印一棵树
	 */
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
	//	testTree(root,loaddata.getData().get(0));
	}
	
	public void testTree(TreeNode node,Sample sample)
	{
	
		if(node.getChilds() == null)
		{
			System.out.println("测试结果为："+node.getNodeName());
			if(node.getNodeName().equals(sample.getLabel()))
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
		Tree tree = new Tree(loaddata);
		tree.creatDecisionTree();
	
	}

}
