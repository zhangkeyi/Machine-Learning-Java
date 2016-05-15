package bayes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LoadData {
	
	private List<Document> datamat = new ArrayList<>();//���ݼ�
	private HashSet<String> wordlist = new HashSet<>();//�ʵ�
	private List<File> documentlist = new ArrayList<>();//�ĵ�·���б�
	
	public LoadData()
	{}
	
	public List<Document> getData()
	{
		return datamat;
	}
	
	public HashSet<String> getWordVector()
	{
		return wordlist;
	}
	
	public List<File> getDocumentList()
	{
		return documentlist;
	}
	/*
	 * ��ȡ�ļ����µ��ļ��б�
	 */
	public void readFile(String path)
	{
		File  f = new File(path);  
	    File[] files = f.listFiles(); 
	    for (File file : files) 
	    {  
	        documentlist.add(file);  
	    }  
	}
	/*
	 * �������ݲ�����
	 */
	public void loadData()
	{
		if(documentlist.size() == 0)
			return;
		for(File file : documentlist)
		{
			String path = file.getAbsolutePath();
			Document document = new Document(path);
			datamat.add(document);
			if(path.contains("spam"))
				document.setClasses(1);
	    }
		
	}
	/*
	 * ����ʵ�
	 */
	public void creatWordList()
	{
		if(datamat.size() == 0)
			loadData();
		for(Document document : datamat)
		{
			List<String> data = document.getDocumentData();
			for(String word : data)
				wordlist.add(word);
		}
		
	}
	/*
	 * ��ÿһ���ʼ�ת��������
	 */
	public void creatWordVector()
	{
		if(wordlist.size() == 0)
			creatWordList();
		for(Document document : datamat)
		{
			document.creatWordVector(wordlist);
		}
	}
	
	
//	public static void main(String[] args)
//	{
//		LoadData load = new LoadData();
//		load.readFile("C:/Users/keyi/Desktop/machinelearninginaction/Ch04/email/ham/");
//		load.readFile("C:/Users/keyi/Desktop/machinelearninginaction/Ch04/email/spam/");
//		List<File> list = load.getDocumentList();
//		for(File file : list)
//		{
//			System.out.println(file.getAbsolutePath());
//		}
//		load.creatWordVector();
//		System.out.println("�ʵ�ĳ���Ϊ��"+load.getWordVector().size());
//		System.out.println("load data is ok!");
//    }
	
}
