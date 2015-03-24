package sg.edu.nus.cs2020;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RotatedSearchTest
{
	@Test
	public void testIntegers()
	{
		Integer[] integers = {39, 47, 53, 3, 13, 14, 16, 18, 25, 31};
		assertEquals(integers[2], RotatedSearch.searchMax(integers));
		// Expected: 53
	}
	
	@Test
	public void testDoubles()
	{
		Double[] doubles = {16.69, 23.89, 27.61, 33.05, 34.48, 36.63, 46.62, 5.96, 8.3, 11.44};
		assertEquals(doubles[6], RotatedSearch.searchMax(doubles));
		// Expected: 46.62
	}
	
	@Test
	public void testStrings()
	{
		String[] names = {"Franny", "Glen", "Harry", "Isabelle", "Julia", "Alice", "Bob", "Collin", "David", "Elissa"};
		assertEquals(names[4], RotatedSearch.searchMax(names));
		// Expected: Julia
	}
}
