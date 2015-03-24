package sg.edu.nus.cs2020;

public class TestClass
{
	public static <T extends Comparable<T>> boolean lessThan(T a, T b)
	{
		return false;
	}
	
	public static <T> boolean moreThan(T a, T b)
	{
		return true;
	}
}
