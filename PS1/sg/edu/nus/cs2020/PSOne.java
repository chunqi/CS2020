package sg.edu.nus.cs2020;

public class PSOne
{
	static int MysteryFunction(int argA, int argB)
	{
		int c = 1;
		int d = argA;
		int e = argB;
		while (e > 0)
		{
			if (2*(e/2) !=e)
			{
				c = c*d;
			}
			d = d*d;
			e = e/2;
		}
		return c;
	}
	
	public static void main(String args[])
	{
		int output = MysteryFunction(2, 3);
		System.out.printf("The answer is: " + output + ".");
	}
}
