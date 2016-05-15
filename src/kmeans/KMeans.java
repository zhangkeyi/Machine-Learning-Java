package kmeans;

import java.util.ArrayList;
import java.util.List;

public class KMeans {

	private int k;                //�������
	private String filename;
	private List<Point> datamat = new ArrayList<Point>();   //���ݾ���
	private List<Point> centroids = new ArrayList<Point>(); //�������
	private LoadData loaddata;    //�������ݵĶ���
	
	public KMeans(int k,String filename)
	{
		this.k = k;
		this.filename = filename;
	}
	
	public List<Point> getDatamat()
	{
		return datamat;
	}
	
	public void loadDataSet()
	{
		loaddata = new LoadData(filename);
		datamat = loaddata.getData();
	}
	
	public float distEclud(Point a,Point b)
	{
		float sum = 0;
		for(int i = 0;i<a.getProperty().size();i++)
		{
			sum += Math.pow(a.getProperty().get(i)-b.getProperty().get(i), 2);
		}
		return (float)Math.sqrt(sum);
	}
	/**��ʼ������*/
	public void initCentroids()
	{
		List<float[]> a = new ArrayList<>();
		float[] min = loaddata.getMin();
		float[] max = loaddata.getMax();
		for(int i = 0;i<min.length;i++)
		{
			a.add((new RandCent(min[i],max[i],k)).randCreate());  //������ɷ�Χ�ڵ�k�������
		}
		float[][] b = new float[a.get(0).length][a.size()];
		for(int i = 0;i<a.get(0).length;i++)
		{
			for(int j = 0;j<a.size();j++)
			{
				b[i][j] = a.get(j)[i];
			}
		}
		for(int i = 0 ;i<b.length;i++)
		{
			centroids.add(new Point(b[i]));
		}
		
	}
	
	public void kmeansAlgorithm()
	{
		loadDataSet();//���ݼ���
		boolean clusterchanged = true;//ѭ����־λ
		int o = 0; //ѭ������
		initCentroids();//��ʼ������
		while(clusterchanged)
		{
			clusterchanged = false;
			int i = 0;
			/**�������ݼ�*/
			for(Point a : datamat)
			{
				int j = 0;
				float mindist = Float.MAX_VALUE;
				int minindex = 0;
				for(Point b : centroids)
				{
					float distance = distEclud(a,b);
					if(distance<mindist)
					{
						mindist = distance;
						minindex = j;
					}
					j++;
					
				}
				if(a.getIndex() != minindex)
				{
					clusterchanged = true;
					a.setIndex(minindex);
				}
				i++;
			}
			/**���¼�������*/
			int k = 0;
			for(Point c : centroids)
			{
				int count = 0;
				float[] sum = new float[c.getProperty().size()];
				for(int r = 0;r<c.getProperty().size();r++)
				{
					sum[r] = 0;
				}
				for(Point d : datamat)
				{
					if(d.getIndex() == k)
					{
						for(int r = 0;r<d.getProperty().size();r++)
						{
							sum[r] += d.getProperty().get(r) ;
						}
						count++;
					}
				}
				/**����ÿһά��ƽ����*/
				for(int t = 0;t<c.getProperty().size();t++)
				{
					sum[t] /= count;
				}
				c.setProperty(sum);
				k++;
				//System.out.println(count);
			}
			o++;
			
			for(Point t : datamat)
			{
				for(int q = 0;q<t.getProperty().size();q++)
				{
					System.out.print(t.getProperty().get(q)+"|");
				}
				System.out.print(t.getIndex());
			
				System.out.println();
			}
			System.out.println("��"+o+"�ε���");
			
		}
	
	
	}
	
	public static void main(String[] args)
	{
		KMeans means = new KMeans(4,"C:/Users/keyi/Desktop/machinelearninginaction/Ch10/testSet.txt");
		means.kmeansAlgorithm();
		System.out.println("end!");
	}
}
