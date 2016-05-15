package bayes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Document {
	
	private String path;//文档路径
	private List<Integer> wordvector = new ArrayList<>();//文档数字向量
	private List<String> dataset = new ArrayList<>();//文档字符数据
	private int classes = 0;//文档类型
	
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
	 * 数据读取条件：分隔符为除单词、数字以外的任意字符串；单词长度>2；统一为小写字母
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
	 * 将文档转换为数字向量
	 */
	public void creatWordVector(HashSet<String> wordlist)
	{
		if(wordlist.size() == 0)
			return;
		/**初始化文档向量，词袋模型*/
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
