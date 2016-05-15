package svm;

import java.util.List;
import java.util.Random;

public class SmoP {
	
	private Data _data;
	private List<Point> dataset;
	private double[] alphas;
	private double[] Ek;
	private double b = 0;
	private double _toler;
	private double _C;
	private double[] ws;
	
	public SmoP(Data data,double toler,double c)
	{
		_data = data;
		_toler = toler;
		_C = c;
		dataset = _data.getData();
		alphas = new double[dataset.size()];
		Ek = new double[dataset.size()];
	}
	/*
	 * calc p1*p2
	 */
	public double calcInnerProduct(Point p1,Point p2)
	{
		double innerp = 0;
		int size = p1.getProperty().size();
		for(int i = 0;i<size;i++)
		{
			innerp += p1.getProperty().get(i)*p2.getProperty().get(i);
		}
		return innerp;
	}
	/*
	 * 计算预测值与真实值之间的误差
	 */
	public double calcEk(int k)
	{
		double Ek = 0;
		double gx = 0;
		for(int i = 0;i<alphas.length;i++)
		{
			gx += alphas[i]*dataset.get(i).getLabel()*calcInnerProduct(dataset.get(i),dataset.get(k));
		}
		gx += b;
		Ek = gx - dataset.get(k).getLabel();
		return Ek;
	}
	/*
	 * 输入第一个alpha的索引
	 * 返回第二个alpha的索引
	 */
	public int selectJ(int i,double Ei)
	{
		int index = 0;
		double maxdeltaE = 0;
	//	Ek[i] = Ei;
		int count = 0;
		for(int n = 0;n<Ek.length;n++)
		{
			if(Ek[i]>0)
				count++;
		}
		if(count>1)
		{
			for(int k = 0;k<alphas.length;k++)
		    {
			  if(k == i)
				  continue;
			  double E = calcEk(k);
			  double deltaE = Math.abs(Ei - E);
			  if(deltaE>maxdeltaE)
			  {
				maxdeltaE = deltaE;
				index = k;
			  }
		    }
		}
		else
		{
			index = (new Random()).nextInt(alphas.length);
		}
		
		return index;
	}
	
	public double clipAlpha(double aj,double H,double L)
	{
		double a = 0;
		if(aj>H)
			a = H;
		if(aj<L)
			a = L;
		if(aj>=L&&aj<=H)
			a = aj;
		return a;
	}
	
	public int innerL(int i)
	{
		double Ei = calcEk(i);
		if(((dataset.get(i).getLabel()*Ei< -_toler)&&(alphas[i]<_C))||((dataset.get(i).getLabel()*Ei>_toler)&&(alphas[i]>0)))
		{
			int j = selectJ(i,Ei);
			double Ej = calcEk(j);
			double alphaIold = alphas[i];
			double alphaJold = alphas[j];
			double L,H;
			if(dataset.get(i).getLabel() != dataset.get(j).getLabel())
			{
				L = ((alphas[j]-alphas[i])>0?(alphas[j]-alphas[i]):0);
				H = ((_C+alphas[j]-alphas[i])<_C?(_C+alphas[j]-alphas[i]):_C);
			}
			else
			{
				L = ((alphas[j]+alphas[i]-_C)>0?(alphas[j]+alphas[i]-_C):0);
				H = ((alphas[j]+alphas[i])<_C?(alphas[j]+alphas[i]):_C);
			}
			if(L==H)
				return 0;
			double eta = 2.0*calcInnerProduct(dataset.get(i),dataset.get(j))-calcInnerProduct(dataset.get(i),dataset.get(i))-
					calcInnerProduct(dataset.get(j),dataset.get(j));
			if(eta>=0)
				return 0;
			alphas[j] -= dataset.get(j).getLabel()*(Ei - Ej)/eta;//剪辑前的alpha
			alphas[j] = clipAlpha(alphas[j],H,L);//
			Ek[j] = calcEk(j);
			if(Math.abs(alphas[j] - alphaJold)<0.00001)
				return 0;
			alphas[i] += dataset.get(j).getLabel()*dataset.get(i).getLabel()*(alphaJold - alphas[j]);
			Ek[i] = calcEk(i);
			double b1 = b - Ei - dataset.get(i).getLabel()*(alphas[i] - alphaIold)*calcInnerProduct(dataset.get(i),dataset.get(i))-
					dataset.get(j).getLabel()*(alphas[j] - alphaJold)*calcInnerProduct(dataset.get(i),dataset.get(j));
			double b2 = b - Ej - dataset.get(i).getLabel()*(alphas[i] - alphaIold)*calcInnerProduct(dataset.get(i),dataset.get(j))-
					dataset.get(j).getLabel()*(alphas[j] - alphaJold)*calcInnerProduct(dataset.get(j),dataset.get(j));
			if(alphas[i]>0 && alphas[i]<_C)
				b = b1;
			else if(alphas[j]>0 && alphas[j]<_C)
				b = b2;
			else
				b = (b1+b2)/2;
			return 1;
			
		}
		else return 0;
	}
	
	public void smoP(int maxIter)
	{
		int iter = 0;
		int alphaPairsChanged = 0;
		boolean entireSet = true;
		while((iter<maxIter)&&((alphaPairsChanged>0)||(entireSet)))
		{
			alphaPairsChanged = 0;
			if(entireSet)
			{
				for(int i = 0;i<alphas.length;i++)
				{
					alphaPairsChanged += innerL(i);
				}
				iter++;
			}
			else
			{
				for(int i = 0;i<alphas.length;i++)
				{
					if(alphas[i]>0 && alphas[i]<_C)
					{
						alphaPairsChanged += innerL(i);
					}
				}
				iter++;
			}
			if(entireSet)
				entireSet = false;
			else if(alphaPairsChanged == 0)
				entireSet = true;
		}
		System.out.println("sssssssssss"+iter);
	}
	
	public void calcWs()
	{
		ws = new double[dataset.get(0).getProperty().size()];
		for(int i = 0;i<ws.length;i++)
		{
			for(int j = 0;j<alphas.length;j++)
			{
				ws[i] += alphas[j]*dataset.get(j).getLabel()*dataset.get(j).getProperty().get(i);
			}
		}
			
	}
	
	public void test()
	{
		smoP(40);
		for(int i = 0;i<alphas.length;i++)
		{
			if(alphas[i]>0)
			{
				System.out.println(alphas[i]+","+Ek[i]);
			}
			
		}
		System.out.println("b:"+b);
		calcWs();
		System.out.print("w:");
		for(int i = 0;i<ws.length;i++)
		{
			System.out.print(ws[i]+",");
		}
	}
	
	public static void main(String[] args)
	{
		long time1 = System.currentTimeMillis();
		Data data = new Data("C:/Users/keyi/Desktop/machinelearninginaction/Ch06/testSet.txt");
		SmoP smo = new SmoP(data,0.001,0.6);
		smo.test();
		long time2 = System.currentTimeMillis();
		long time = time2 - time1;
		System.out.println("time:" + time);
	}
	
	
}
