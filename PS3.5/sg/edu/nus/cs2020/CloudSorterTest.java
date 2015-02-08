package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class CloudSorterTest
{
	private final String PATH_PREPEND = "sg/edu/nus/cs2020/";
	private final String PATH_APPEND = ".txt";
	private final String PATH_INPUT_PREPEND = "SampleDataFiles/";
	private final String PATH_OUTPUT_PREPEND = "Output/";
	private final String[] TEST_CASES = {
			"10Ints",
			"10IntsSorted",
			"10IntsAlmostSorted1",
			"10IntsAlmostSorted5",
			"10IntsAlmostSorted20",
			"100Ints",
			"100IntsSorted",
			"100IntsAlmostSorted10",
			"100IntsAlmostSorted50",
			"100IntsAlmostSorted100",
			"103Ints",
			"1000Ints",
			"1000IntsSorted",
			"1000IntsAlmostSorted1",
			"1000IntsAlmostSorted5",
			"1000IntsAlmostSorted10",
			"10000Ints",
			"10000IntsSorted",
			"10000IntsAlmostSorted5",
			"10000IntsAlmostSorted10",
			"10000IntsAlmostSorted100",
			"100000Ints",
			"100000IntsSorted",
			"100000IntsAlmostSorted10",
			"100000IntsAlmostSorted50",
			"1000000Ints",
			"1000000IntsSorted",
			"1000000IntsAlmostSorted50",
			"1000000IntsAlmostSorted100",
			"1000000IntsAlmostSorted500",
			"ProblemSetPi"
	};
	
	@Test
	public void testCloudSorter()
	{
		for(int x = 0; x < TEST_CASES.length; x++)
		{
			String inFile = PATH_PREPEND + PATH_INPUT_PREPEND + TEST_CASES[x] + PATH_APPEND;
			String outFile = PATH_PREPEND + PATH_OUTPUT_PREPEND + TEST_CASES[x] + PATH_APPEND;
			CloudSorter cloudSorter = new CloudSorter();
			boolean isSorted;
			
			cloudSorter.cloudSort(inFile, outFile, 1000);
			isSorted = cloudSorter.isSorted();
			
			System.out.println(TEST_CASES[x]);
			if(isSorted) System.out.println("SORTED");
			else
			{
				System.out.println("NOT SORTED");
			}
			assertEquals("isSorted", true, isSorted);
		}
	}
}
