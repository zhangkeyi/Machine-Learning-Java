package bayes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Document {
	
	private String path;//�ĵ�·��
	private List<Integer> wordvector = new ArrayList<>();//�ĵ���������
	private List<String> dataset = new ArrayList<>();//�ĵ��ַ�����
	private int classes = 0;//�ĵ�����
	
	public Document(String path)
	{
		this.path = path;
		readData();
	}
	/*
	 * @return path the path of document
	 */
	public String getDocumentPath()
	{
		return path;
	}
	/*
	 * @return wordvector a vector of document
	 */
	public List<Integer> getWordVector()
	{
		return wordvector;
	}
	/*
	 * @return dataset
	 */
	public List<String> getDocumentData()
	{
		return dataset;
	}
	/*
	 * @return classes the class of document
	 */
	public int getClasses()
	{
		return classes;
	}
	/*
	 * @param classess set the class of document
	 */
	public void setClasses(int classes)
	{
		this.classes = classes;
	}
	/*
	 * ���ݶ�ȡ�������ָ���Ϊ�����ʡ���������������ַ��������ʳ���>2��ͳһΪСд��ĸ
	 */
	public void readData()
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
			String[] tokens = line.split("\\W+");
			for(int i = 0 ;i<tokens.length;i++)
			{
				if(tokens[i].length()>2)
				{
					String string = tokens[i].toLowerCase();
					dataset.add(string);
				}
			}
			
		}
	}
	/*
	 * ���ĵ�ת��Ϊ��������
	 */
	public void creatWordVector(HashSet<String> wordlist)
	{
		if(wordlist.size() == 0)
			return;
		/**��ʼ���ĵ��������ʴ�ģ��*/
        for(int k = 0;k<wordlist.size();k++)
        {
        	wordvector.add(0);
        }
		int i = 0;
		for(String word : dataset)
		{
			int j = 0;
			for(String word_ : wordlist)
			{
			
				if(word.equals(word_))
				{
					int count = wordvector.get(j)+1;
					wordvector.set(j, count);
				}
				j++;
			}
			i++;
		}
	}
	
//	public static void main(String[] args)
//	{
//		Document doc = new Document("C:/Users/keyi/Desktop/machinelearninginaction/Ch04/email/ham/2.txt");
//		doc.readData();
//		List<String> data = doc.getDocumentData();
//		for(String string : data)
//		{
//			System.out.print(string+"|");
//		}
//	}

}
