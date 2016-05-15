package kmeans;

import java.util.ArrayList;
import java.util.List;

public class KMeans {

	private int k;                //聚类簇数
	private String filename;
	private List<Point> datamat = new ArrayList<Point>();   //数据矩阵
	private List<Point> centroids = new ArrayList<Point>(); //存放质心
	private LoadData loaddata;    //加载数据的对象
	
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
	/**初始化质心*/
	public void initCentroids()
	{
		List<float[]> a = new ArrayList<>();
		float[] min = loaddata.getMin();
		float[] max = loaddata.getMax();
		for(int i = 0;i<min.length;i++)
		{
			a.add((new RandCent(min[i],max[i],k)).randCreate());  //随机生成范围内的k个随机数
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
		loadDataSet();//数据加载
		boolean clusterchanged = true;//循环标志位
		int o = 0; //循环次数
		initCentroids();//初始化质心
		while(clusterchanged)
		{
			clusterchanged = false;
			int i = 0;
			/**遍历数据集*/
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
			/**重新计算质心*/
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
				/**计算每一维的平均数*/
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
			System.out.println("第"+o+"次迭代");
			
		}
	
	
	}
	
	public static void main(String[] args)
	{
		KMeans means = new KMeans(4,"C:/Users/keyi/Desktop/machinelearninginaction/Ch10/testSet.txt");
		means.kmeansAlgorithm();
		System.out.println("end!");
	}
}
