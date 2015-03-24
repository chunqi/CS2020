package sg.edu.nus.cs2020;

import java.util.Iterator;

import org.junit.Test;

public class CATest
{
	@Test
	public void testSierpinski()
	{
		CA ca = new CA(31);
		ca.initialize(new int[] {0, 1, 0, 0, 1, 0, 0, 0});
		Iterator<String> caIter = ca.iterator();
		
		for(int x = 0; x < 17; x++)
		{
			if(caIter.hasNext())
			{
				System.out.println(caIter.next());
			}
		}
	}
	
	@Test
	public void testInteresting()
	{
		CA ca = new CA(31);
		ca.initialize(new int[] {0, 1, 1, 1, 1, 0, 0, 0});
		Iterator<String> caIter = ca.iterator();
		
		for(int x = 0; x < 15; x++)
		{
			if(caIter.hasNext())
			{
				System.out.println(caIter.next());
			}
		}
	}
}
