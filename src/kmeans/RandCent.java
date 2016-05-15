package kmeans;

public class RandCent {

	private float min;
	private float max;
	private int k;
	
	public RandCent(float min,float max,int k)
	{
		this.min = min;
		this.max = max;
		this.k = k;
	}
	
	public float[] randCreate()
	{
		float[] randpoints = new float[k];
		if(k>(max - min + 1)||max < min)
			return null;
		int count = 0;
		while(count < k)
		{
			float num = (float)(Math.random()*(max - min) + min);
			boolean flag = true;
			for(int i = 0;i<k;i++)
			{
				if(num == randpoints[i])
				{
					flag = false;
					break;
				}
			}
			if(flag)
			{
				randpoints[count] = num;
				count++;
			}
		}
		
		return randpoints;
	}
}
