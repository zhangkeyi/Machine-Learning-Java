package regression;

import java.util.List;

public class StandRegression {

	private float alpha;                   //ѧϰ��
	private List<Point> datamat;
	private float[] theta;                //�ع����
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
		
		int times = 0;               //��������
		float temjtheta = 0;         //���������ʱ����
		float tolerate = 0;          //ǰ���������ĸı���
        boolean flag = true;
		while(flag)
		{
			float jtheta = 0;       //�в�ƽ����
			/**����ع����*/
			for(int i = 0;i<theta.length;i++)
			{
				float sum = 0;
				for(int j = 0;j<datamat.size();j++)
				{
					float h = 0;
					/**����h(theta)*/
					for(int k = 0;k<theta.length;k++)
					{
						h += theta[k]*datamat.get(j).getProperty().get(k);
					}
					sum += (datamat.get(j).getProperty().get(theta.length) - h)*(datamat.get(j).getProperty().get(i));
				}
				theta[i] += alpha*sum;
			}
			
			/**����в�ƽ����*/
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
			
			System.out.println("��"+times+"��");
			System.out.println(jtheta/2);
			for(int i = 0;i<theta.length;i++)
			{
				System.out.println(theta[i]);
			}
		
		}
		
 	}
	
	public void randomRegress()
	{
		int times = 0;                 //��������
		float temjtheta = 0;           //���������ʱ����
		float tolerate = 0;            //ǰ�����ĸı���
        boolean flag = true;           //ѭ����־λ
        while(flag)
        {
            float jtheta = 0;
            /**����ع����*/
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
			
            /**�������*/
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
			
			System.out.println("��"+times+"��");
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
