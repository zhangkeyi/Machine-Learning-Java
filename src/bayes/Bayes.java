package bayes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Bayes {
	
	private LoadData loaddata;//数据加载对象
	private List<Document> trainset = new ArrayList<>();//训练集
	private List<Document> testset = new ArrayList<>();	//测试集
	private double[] p0;//类0条件概率
	private double[] p1;//类1条件概率
	private double pclass0;//类0概率
	 
	public Bayes()
	{
		loadData();
	}
	
	public void loadData()
	{
		loaddata = new LoadData();
		loaddata.readFile("C:/Users/keyi/Desktop/machinelearninginaction/Ch04/email/ham/");
		loaddata.readFile("C:/Users/keyi/Desktop/machinelearninginaction/Ch04/email/spam/");
		loaddata.creatWordVector();
	}
	/*
	 * @param i the number of test set
	 * 构造测试集和训练集
	 */
	public void creatTestSet(int i)
	{
		RandCent rand = new RandCent(0,2000,i);
		int[] randnum = rand.randCreate();
		for(int k = 0;k<loaddata.getData().size();k++)
		{
			trainset.add(loaddata.getData().get(k));
		}
		
		for(int k = 0;k<randnum.length;k++)
		{
			Document document = loaddata.getData().get(randnum[k]);
			testset.add(document);
		}
		trainset.removeAll(testset);
    }
	/*
	 * 向量求和
	 */
	public int sumVector(List<Integer> list)
	{
		if(list.size() == 0)
			return 0;
		int sum = 0;
		for(Integer a : list)
			sum += a;
		return sum;
	}
	/*
	 * 返回单词的类条件概率
	 */
	public double getProbability(String word,int classes)
	{
		double p = 0;
		HashSet<String> wordlist = loaddata.getWordVector();
		int i = 0;
		for(String word_ : wordlist)
		{
			if(word.equals(word_))
			{
				if(classes == 0)
					p = p0[i];
				else
					p = p1[i];
			}
			i++;
		}
		return p;
	}
	/*
	 * 训练分类器
	 */
	public void trainAlgorithm()
	{
		int wordlistsize = loaddata.getWordVector().size();   //词典大小
	    int[] p0num =new int[wordlistsize];//各词在类0中出现的次数
	    int[] p1num = new int[wordlistsize];//各词在类1中出现的次数
	    /**初始化每个单词出现一次*/
	    for(int i = 0;i<wordlistsize;i++)
	    {
	    	p0num[i] = p1num[i] = 1;
	    }
	    int p0denom = 2;//类0的总词数
	    int p1denom = 2;//类1的总词数
	    int class0count = 0;//类0文档数
	    /**统计每个词在各类出现的次数*/
	    for(Document document : trainset)
	    {
	    	List<Integer> wordvector = document.getWordVector();//得到文档的词向量
	    	if(document.getClasses() == 0)
	    	{
	    		for(int i = 0;i<p0num.length;i++)
	    		{
	    			p0num[i] += wordvector.get(i);
	    		}
	    		p0denom += sumVector(wordvector);
	    		class0count++;
	    	}
	    	else
	    	{
	    		for(int i = 0;i<p1num.length;i++)
	    		{
	    			p1num[i] += wordvector.get(i);
	    		}
	    		p1denom += sumVector(wordvector);
	    	}
	    	pclass0 = (double)class0count/(trainset.size());//类0的概率
	    }
	    p0 = new double[wordlistsize];
	    p1 = new double[wordlistsize];
	    /**计算类条件概率*/
	    for(int i = 0;i<p0.length;i++)
	    {
	    	p0[i] = (double)p0num[i]/p0denom;
	    	p1[i] = (double)p1num[i]/p1denom;
	    }
		
	}
	/*
	 * 分类函数
	 */
	public int classifyEail(Document document)
	{
		List<String> dataset = document.getDocumentData();
		double p0 = 0;
		double p1 = 0;
		for(String word : dataset)
		{
			p0 += Math.log(getProbability(word,0));
			p1 += Math.log(getProbability(word,1));
		}
		p0 += Math.log(pclass0);
		p1 += Math.log((double)(1-pclass0));
//		int i = p0>p1?0:1;
//		return i;
		if(p0>p1)
			return 0;
		else
			return 1;
	}
	/*
	 * 测试分类器
	 */
	public void testAlgorithm()
	{
		creatTestSet(100);//构造测试集
		trainAlgorithm();//训练分类器
		int count  = 0;//计数器
		for(Document document : testset)
		{
			int testresult = classifyEail(document);
			if(testresult == document.getClasses())
				count++;
			System.out.println("document:"+document.getDocumentPath());
			System.out.println("测试结果："+testresult+" "+"文档类型："+document.getClasses());
		}
		System.out.println("正确率为："+(double)count/(testset.size()));
	}
	
	public static void main(String[] args)
	{
     		Bayes bayes = new Bayes();
		    bayes.testAlgorithm();
	}

}
