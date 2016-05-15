package fpgrowth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FPGrowth
{
	private Data dataset;
	private int minsupport;
	
	public FPGrowth(Data dataset, int minsupport)
	{
		super();
		this.dataset = dataset;
		this.minsupport = minsupport;
	}
	
	public int getMinsupport()
	{
		return minsupport;
	}
	
	public void setMinsupport(int minsupport)
	{
		this.minsupport = minsupport;
	}
	
	public Data getDataset()
	{
		return dataset;
	}
	
	public Set<Set<String>> getFreqitemlist()
	{
		return freqitemlist;
	}

	/*
	 * 根据支持度由高到低排序
	 */
	public void sort(List<String> itemset)
	{
		Collections.sort(itemset, new Comparator<String>()
				{
			         public int compare(String o1,String o2)
			         {
			        	 int num1 = dataset.getItemcount().get(o1);
			        	 int num2 = dataset.getItemcount().get(o2);
			        	 if(num1>=num2)
			        		 return -1;
			        	 else
			        		 return 1;
			         }
				});
	}
	/*
	 * 更新头指针
	 */
    public void updataHeader(Node node,Node targetnode)
    {
    	while(node.getNodelink() != null)
    		node = node.getNodelink();
    	node.setNodelink(targetnode);
    }
    
    public void updataTree(List<String> itemset,Node fathernode,Map<String,Node> headerlist,int cnt)
    {
    	if(itemset.size() == 0)
    		return;
    	String item_0 = itemset.get(0);
    	
    	if((fathernode.getChilds() != null)&&(fathernode.getChilds().containsKey(item_0)))
    	{
    		int count = fathernode.getChilds().get(item_0).getCount()+cnt;
    		fathernode.getChilds().get(item_0).setCount(count);
    	}
    	else
    	{
    		Node newNode = new Node(item_0,cnt,fathernode,null);
    		fathernode.addChild(newNode);
    		if(headerlist.get(item_0) == null)
    			headerlist.get(item_0).setNodelink(newNode);
    		else
    			updataHeader(headerlist.get(item_0),fathernode.getChilds().get(item_0));		
    	}
    	
    	if(itemset.size()>1)
    	{
    		itemset.remove(item_0);
    		updataTree(itemset,fathernode.getChilds().get(item_0),headerlist,cnt);
    	}
    }
    /*
     * 对事物集生成FP树
     */
    public Node creatTree(Map<Items,Integer> data,Map<String,Node> headerlist)
    {
    	if(data.size() == 0)
    		return null;
    	/**对每个元素进行计数*/
    	Map<String,Integer> itemcount = new HashMap<>();
    	for(Items items : data.keySet())
    	{
    		for(String str : items.getElements())
    		{
    			if(itemcount.containsKey(str))
    			{
    				int count = itemcount.get(str)+data.get(items);
    				itemcount.put(str, count);
    			}
    			else
    				itemcount.put(str, data.get(items));
    		}
    	}
    	/**生成头指针列表*/
    	for(String element : itemcount.keySet())
    	{
    		if(itemcount.get(element)>minsupport)
    		{
    			Node node = new Node(element,itemcount.get(element),null,null);
    			headerlist.put(element, node);
    		}
    	}
    	
    	Node root = new Node("Null",0,null,null);
    	/**第二遍遍历数据集*/
    	for(Items items : data.keySet())
    	{
    		Set<String> elements = new HashSet<>();
    		elements.addAll(items.getElements());
    		elements.retainAll(headerlist.keySet());
    		
    		List<String> elements_ = new ArrayList<>();
    		elements_.addAll(elements);
    		
    		sort(elements_);
    		updataTree(elements_,root,headerlist,data.get(items));
    	}
    	return root;
    }
    /*
     * 给定一节点，往上回溯
     */
    public void ascendTree(Node node,List<String> prefixpath)
    {
    	if((node.getParent() != null)&&(!node.getParent().getName().equals("Null")))
    	{
    		prefixpath.add(node.getParent().getName());
    		ascendTree(node.getParent(),prefixpath);
    	}
    	else
    		return;
    }
    /*
     * 给定一头指针，找出所有前缀路径
     */
    public Map<Items,Integer> findPrefixPath(String element,Map<String,Node> headerlist)
    {
    	Map<Items,Integer> prefixpathlist = new HashMap<>();
    	
    	Node node = headerlist.get(element);
		while(node.getNodelink() != null)
		{
			node = node.getNodelink();
			
			List<String> prefixpath = new ArrayList<>();
			ascendTree(node,prefixpath);
			Items items = new Items(prefixpath);
			prefixpathlist.put(items, node.getCount());
		}
    	
    	return prefixpathlist;
    }
    
    private Set<Set<String>> freqitemlist = new HashSet<>();
    public void mineTree(Node node,Map<String,Node> headerlist,Set<String> freqitem)
    {
    	for(String element : headerlist.keySet())
    	{
    		Set<String> newfreqitem = new HashSet<>();
    		newfreqitem.addAll(freqitem);
    		newfreqitem.add(element);
    		freqitemlist.add(newfreqitem);
    		/**生成条件模式基*/
    		Map<Items,Integer> prefixpathlist = findPrefixPath(element,headerlist);
    		/**构建条件fp树,重新生成头指针*/
    		Map<String,Node> myheader = new HashMap<>();
    		Node mytree = creatTree(prefixpathlist,myheader);
    		/**迭代*/
    		if(prefixpathlist.size() != 0)
    		{
    			mineTree(mytree,myheader,newfreqitem);
    		}
    	}
    }
    
    public void fpGrowth()
    {
    	Map<String,Node> headerlist = new HashMap<>();
    	Node tree = creatTree(dataset.getDatamap(),headerlist);
    	Set<String> itemslist = new HashSet<>();
    	mineTree(tree,headerlist,itemslist);
    	for(Set<String> items : freqitemlist)
    	{
    		for(String ele : items)
    		{
    			System.out.print(ele+",");
    		}
    		System.out.println();
    	}
    }

	public void test()
	{
//		/**测试过滤非频繁项*/
//		Set<String> freitem = filtItem();
//		for(String str : freitem)
//		{
//			System.out.println(str+","+dataset.getItemcount().get(str));
//		}
//		/**测试头指针更新*/
//		Node node1 = new Node("1");
//		Node node2 = new Node("2");
//		Node node3 = new Node("3");
//	    Node node4 = new Node("4");
//	    updataHeader(node1,node2);
//	    System.out.println(node1.getName()+"->"+node1.getNodelink().getName()+"->"+node1.getNodelink().getNodelink().getName());
//		/**测试排序*/
//		for(Items items : dataset.getData())
//		{
//			sort(items.getElements());
//			for(String str : items.getElements())
//			{
//				System.out.print(str+","+dataset.getItemcount().get(str));
//			}
//			System.out.println();
//		}
//		System.out.println("over!"+root.getName());
//		/**遍历链表*/
//		for(String name : headerlist.keySet())
//		{
//			Node node = headerlist.get(name);
//			System.out.print(node.getName()+","+node.getCount()+"->");
//			while(node.getNodelink() != null)
//			{
//			//	System.out.print(node.getName()+","+node.getCount()+"->");
//				node = node.getNodelink();
//				System.out.print(node.getName()+","+node.getCount()+"->");
//			}
//			System.out.println();
//		}
//		/**打印树节点*/
//		printNode(root);
//		Map<String,Node> headerlist = new HashMap<>();
//		Node root = creatTree(dataset.getData(),headerlist);
//		for(String name : headerlist.keySet())
//			{
//				Node node = headerlist.get(name);
//				System.out.print(node.getName()+","+node.getCount()+"->");
//				while(node.getNodelink() != null)
//				{
//				//	System.out.print(node.getName()+","+node.getCount()+"->");
//					node = node.getNodelink();
//					System.out.print(node.getName()+","+node.getCount()+"->");
//				}
//				System.out.println();
//			}
//		printNode(root);
		fpGrowth();
		
	}
	
	public void printNode(Node node)
	{
		System.out.print(node.getName()+":" + node.getCount());
		for(String str : node.getChilds().keySet())
		{
			System.out.print("{");
			printNode(node.getChilds().get(str));
			System.out.print("}");
		}
	}
	
	
	public static void main(String[] args)
	{
		long time1 = System.currentTimeMillis();
		Data data = new Data("C:/Users/keyi/Desktop/machinelearninginaction/Ch12/kosarak.dat");
		FPGrowth fp = new FPGrowth(data,10000);
		fp.test();
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);
	}
}