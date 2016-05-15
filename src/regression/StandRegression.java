package regression;

import java.util.List;

public class StandRegression {

	private float alpha;                   //学习率
	private List<Point> datamat;
	private float[] theta;                //回归参数
	private String filename;
	
	
	public StandRegression(float a,String filename)
	{
		this.alpha = a;
		this.filename = filename;
		loadData();
		theta = new float[datamat.get(0).getProperty().size()-1];
	}
	
	public float getStepSize()
	{
		return alpha;
	}
	
	public String getFilePath()
	{
		return filename;
	}
	
	public float[] getResult()
	{
		return theta;
	}
	
	public void loadData()
	{
		LoadData load = new LoadData(filename);
		datamat = load.getData();
	}
	
	public void batchRegress()
	{
		
		int times = 0;               //迭代次数
		float temjtheta = 0;         //存放误差的临时变量
		float tolerate = 0;          //前后两次误差的改变量
        boolean flag = true;
		while(flag)
		{
			float jtheta = 0;       //残差平方和
			/**计算回归参数*/
			for(int i = 0;i<theta.length;i++)
			{
				float sum = 0;
				for(int j = 0;j<datamat.size();j++)
				{
					float h = 0;
					/**计算h(theta)*/
					for(int k = 0;k<theta.length;k++)
					{
						h += theta[k]*datamat.get(j).getProperty().get(k);
					}
					sum += (datamat.get(j).getProperty().get(theta.length) - h)*(datamat.get(j).getProperty().get(i));
				}
				theta[i] += alpha*sum;
			}
			
			/**计算残差平方和*/
			for(int j = 0;j<datamat.size();j++)
			{
				float h = 0;
				for(int k = 0;k<theta.length;k++)
				{
					h += theta[k]*datamat.get(j).getProperty().get(k);
				}
				jtheta += Math.pow(h-datamat.get(j).getProperty().get(theta.length), 2);
				
			}
			
			tolerate = Math.abs(jtheta - temjtheta);
			temjtheta = jtheta;
			if(tolerate<0.00001)
				flag = false;
			
			times++;
			
			System.out.println("第"+times+"次");
			System.out.println(jtheta/2);
			for(int i = 0;i<theta.length;i++)
			{
				System.out.println(theta[i]);
			}
		
		}
		
 	}
	
	public void randomRegress()
	{
		int times = 0;                 //迭代次数
		float temjtheta = 0;           //存放误差的临时变量
		float tolerate = 0;            //前后误差的改变量
        boolean flag = true;           //循环标志位
        while(flag)
        {
            float jtheta = 0;
            /**计算回归参数*/
			for(int i = 0;i<theta.length;i++)
			{
				float h = 0;
				int j = (int)(Math.random()*datamat.size());
				/**h(theta)*/
				for(int k = 0;k<theta.length;k++)
				{
					h += theta[k]*datamat.get(j).getProperty().get(k);
				}
				float sum = (datamat.get(j).getProperty().get(theta.length) - h)*(datamat.get(j).getProperty().get(i));
				theta[i] += alpha*sum;
				
			}
			
            /**计算误差*/
			for(int j = 0;j<datamat.size();j++)
			{
				float h = 0;
				for(int k = 0;k<theta.length;k++)
				{
					h += theta[k]*datamat.get(j).getProperty().get(k);
				}
				jtheta += Math.pow(h-datamat.get(j).getProperty().get(theta.length), 2);
				
			}
			
			tolerate = Math.abs(jtheta - temjtheta);
			temjtheta = jtheta;
			if(tolerate<0.00001)
				flag = false;
			
			times++;
			
			System.out.println("第"+times+"次");
			System.out.println(jtheta/2);
			for(int i = 0;i<theta.length;i++)
			{
				System.out.println(theta[i]);
			}
        }
	}
	
	public static void main(String[] args)
	{
//		StandRegression standregression = new StandRegression((float)0.009,"C:/Users/keyi/Desktop/machinelearninginaction/Ch08/ex0.txt");
//		standregression.randomRegress();
		StandRegression standregression = new StandRegression((float)0.007 ,"C:/Users/keyi/Desktop/machinelearninginaction/Ch08/ex0.txt");
		standregression.batchRegress();
		float[] w = standregression.getResult();
		
	}
}
