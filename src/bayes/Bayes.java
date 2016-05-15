package bayes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Bayes {
	
	private LoadData loaddata;//���ݼ��ض���
	private List<Document> trainset = new ArrayList<>();//ѵ����
	private List<Document> testset = new ArrayList<>();	//���Լ�
	private double[] p0;//��0��������
	private double[] p1;//��1��������
	private double pclass0;//��0����
	 
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
	 * ������Լ���ѵ����
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
	 * �������
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
	 * ���ص��ʵ�����������
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
	 * ѵ��������
	 */
	public void trainAlgorithm()
	{
		int wordlistsize = loaddata.getWordVector().size();   //�ʵ��С
	    int[] p0num =new int[wordlistsize];//��������0�г��ֵĴ���
	    int[] p1num = new int[wordlistsize];//��������1�г��ֵĴ���
	    /**��ʼ��ÿ�����ʳ���һ��*/
	    for(int i = 0;i<wordlistsize;i++)
	    {
	    	p0num[i] = p1num[i] = 1;
	    }
	    int p0denom = 2;//��0���ܴ���
	    int p1denom = 2;//��1���ܴ���
	    int class0count = 0;//��0�ĵ���
	    /**ͳ��ÿ�����ڸ�����ֵĴ���*/
	    for(Document document : trainset)
	    {
	    	List<Integer> wordvector = document.getWordVector();//�õ��ĵ��Ĵ�����
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
	    	pclass0 = (double)class0count/(trainset.size());//��0�ĸ���
	    }
	    p0 = new double[wordlistsize];
	    p1 = new double[wordlistsize];
	    /**��������������*/
	    for(int i = 0;i<p0.length;i++)
	    {
	    	p0[i] = (double)p0num[i]/p0denom;
	    	p1[i] = (double)p1num[i]/p1denom;
	    }
		
	}
	/*
	 * ���ຯ��
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
	 * ���Է�����
	 */
	public void testAlgorithm()
	{
		creatTestSet(100);//������Լ�
		trainAlgorithm();//ѵ��������
		int count  = 0;//������
		for(Document document : testset)
		{
			int testresult = classifyEail(document);
			if(testresult == document.getClasses())
				count++;
			System.out.println("document:"+document.getDocumentPath());
			System.out.println("���Խ����"+testresult+" "+"�ĵ����ͣ�"+document.getClasses());
		}
		System.out.println("��ȷ��Ϊ��"+(double)count/(testset.size()));
	}
	
	public static void main(String[] args)
	{
     		Bayes bayes = new Bayes();
		    bayes.testAlgorithm();
	}

}
