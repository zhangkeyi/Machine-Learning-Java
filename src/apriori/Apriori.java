package apriori;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Apriori {
	
	private Data data;
	private double minsupport;
	private double minconf;
	private Map<List<Integer>,Double> maplist = new HashMap<>();
	private List<Rules> rulelist = new ArrayList<>();
	
	public Apriori(Data data,double minsupport,double minconf)
	{
		this.data = data;
		this.minsupport = minsupport;
		this.minconf = minconf;
	}
	
	public List<Rules> getRuleList()
	{
		return rulelist;
	}
	
	public Map<List<Integer>,Double> getMaplist()
	{
		return maplist;
	}
	/*
	 * 产生大小为1的所有候选项集的集合
	 */
	public Set<List<Integer>> creatCandidate_1(List<Sample> dataset)
	{
		if(dataset.size() == 0)
			return null;
		Set<List<Integer>> itemset = new HashSet<>();
		int lenth = dataset.get(0).getItems().size();
		for(int i = 0;i<lenth;i++)
		{
			List<Integer> item = new ArrayList<>();
			item.add(i);
			itemset.add(item);
		}
		return itemset;
	}
	/*
	 * affairset:事物集
	 * ck:候选集
	 * 产生过滤后的频繁项集
	 */
	public List<List<Integer>> scanData(List<List<Integer>> affairset,Set<List<Integer>> ck)
	{
		List<List<Integer>> retlist = new ArrayList<>();
		for(List<Integer> list : ck)
		{
			int count = 0;
			for(List<Integer> affair : affairset)
			{
				if(affair.containsAll(list))
					count++;
			}
			double support = (double)count/affairset.size();
			if(support>minsupport)
				retlist.add(list);
		}
		
		return retlist;
	}
	/*
	 * Lk:频繁项集列表
	 * k:项集元素个数
	 * 由k-1项集产生k-项集
	 */
	public Set<List<Integer>> aprioriGen(List<List<Integer>> Lk,int k)
	{
		Set<List<Integer>> retlist = new HashSet<>();
        int len = Lk.size();
		for(int i = 0 ;i<len;i++)
		{
			for(int j = i+1;j<len;j++)
			{
			   if(Lk.get(i).containsAll(Lk.get(j).subList(0, k-2)))
			   {
			         List<Integer> list = new ArrayList<>();
			    	 list.addAll(Lk.get(i));
			    	 list.add(Lk.get(j).get(k-2));
			    	 Collections.sort(list);
			    	 retlist.add(list);
			    }
			 }
		 }
         return retlist;
	}
	
	public List<List<List<Integer>>> apriori(List<Sample> dataset)
	{
		List<List<List<Integer>>> relist = new ArrayList<>();
		Set<List<Integer>> Candidate = creatCandidate_1(dataset);//产生1-项集
		System.out.println("1-itemset");
		List<List<Integer>> L = scanData(data.getAffairset(),Candidate);//产生频繁1-项集
		System.out.println("1-frequent itemset");
		relist.add(L);
		int k = 2;
		int cout=0;
		while(true)
		{
			System.out.println(cout++);
			if(L.size()<1)
				break;
			Candidate = aprioriGen(L,k);//产生候选k-项集
			L = scanData(data.getAffairset(),Candidate);//产生频繁k-项集
			relist.add(L);
			k += 1;
		}
		System.out.println("creat frequent itemset over!");
		return relist;
	}
	/*
	 * 计算每个频繁项的支持度
	 */
	public void calcSupport(List<List<Integer>> affairset,List<List<List<Integer>>> frequentitemset)
	{
		for(List<List<Integer>> listk : frequentitemset)
		{
			for(List<Integer> listset : listk)
			{
				int count = 0;
				for(List<Integer> affair : affairset)
				{
					if(affair.containsAll(listset))
						count++;
				}
				double support = (double)count/affairset.size();
				maplist.put(listset, support);
			}
		}
	}
	
	/*
	 * freqitemset:频繁项集
	 * right:规则后件
	 * 递归产生关联规则
	 */
	public void creatRules(List<Integer> freqitemset,List<List<Integer>> right_m)
	{
		int k = freqitemset.size();//频繁项集大小
		int m = right_m.get(0).size();//规则后件大小
		if(k<=m+1)
			return;
		Set<List<Integer>> right_m_1 = aprioriGen(right_m,m+1);
		List<List<Integer>> right_m_1_l = new ArrayList<>();
		for(List<Integer> right : right_m_1)
		{
			right_m_1_l.add(right);
			List<Integer> left = new ArrayList<>();
			left.addAll(freqitemset);
			left.removeAll(right);
			Collections.sort(left);
			double conf = maplist.get(freqitemset)/maplist.get(left);
			if(conf>=minconf)
			{
				Rules rule = new Rules(left,right);
				rule.setConf(conf);
				rulelist.add(rule);
			}
		}
		creatRules(freqitemset,right_m_1_l);
		
	}
	
	public void creatAllRules(List<List<List<Integer>>> listset)
	{
		if(listset.size() == 0)
			return;
		for(List<List<Integer>> listk : listset)
		{
			/**for every k itemset*/
			for(List<Integer> list : listk)
			{
				if(list.size()>=2)
				{
					List<List<Integer>> right_1 = new ArrayList<>();
				    for(int a : list)
				    {
					    List<Integer> right = new ArrayList<>();
					    right.add(a);
					    /**添加后件为一项的关联规则*/
					    List<Integer> left = new ArrayList<>();
					    left.addAll(list);
					    left.removeAll(right);
					    double conf = maplist.get(list)/maplist.get(left);
					    if(conf>=minconf)
					    {
					    	Rules rule = new Rules(left,right);
					    	rule.setConf(conf);
					    	rulelist.add(rule);
					    }
					    
					    right_1.add(right);
				    }
				    creatRules(list,right_1);
				}
				
			}
		}
	}
	
	

	public static void main(String[] args)
	{
		Data data = new Data("C:/Users/keyi/Desktop/machinelearninginaction/Ch11/dataset_617317/617317/datamat.txt");
		Apriori apriori = new Apriori(data,0.1,0.8);
		List<List<List<Integer>>> listset = apriori.apriori(data.getData());
		apriori.calcSupport(data.getAffairset(), listset);
		Map<List<Integer>,Double> maplist = apriori.getMaplist();
//		int coutn = 1;
//		for(List<List<Integer>> listk : listset)
//		{
//			System.out.println(coutn+"------项集");
//			for(List<Integer> list : listk)
//			{
//				for(int a : list)
//				{
//					System.out.print(a+",");
//				}
//				System.out.println("support"+maplist.get(list));
//			}
//			coutn++;
//		}
		apriori.creatAllRules(listset);
		List<Rules> ruleslist = apriori.getRuleList();
		for(Rules rule : ruleslist)
		{
			List<Integer> left = rule.getLeft();
			List<Integer> right = rule.getRight();
			for(int a : left)
			{
				System.out.print(a+",");
			}
			System.out.print("-->");
			for(int a : right)
			{
				System.out.print(a+",");
			}
			System.out.println("con:"+rule.getConf());
		}
	}

	
	
}
