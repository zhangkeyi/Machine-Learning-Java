package bayes;

public class RandCent {

	private int min;
	private int max;
	private int k;
	
	public RandCent(int min,int max,int k)
	{
		this.min = min;
		this.max = max;
		this.k = k;
	}
	
	public int[] randCreate()
	{
		int[] randpoints = new int[k];
		if(k>(max - min + 1)||max < min)
			return null;
		int count = 0;
		while(count < k)
		{
			int num = (int)(Math.random()*(max - min) + min);
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
