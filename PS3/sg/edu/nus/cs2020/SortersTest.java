package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.List;

/**
 * A class containing methods to test the stability and correctness of sorters that implement the {@link ISort} interface. The {@link #main} method profiles and outputs performance numbers.
 * @author Zhu Chunqi
 */
public class SortersTest
{
	/**
	 * Checks if the provided sorter is stable when sorting a random array of the chosen size
	 * @param sorter Sorter object implementing the {@link ISort} interface
	 * @param size Size of test array to use
	 * @return {@code true} if sorter is stable, {@code false} otherwise
	 */
	public static boolean isStable(ISort sorter, int size)
	{
		//Create a new random indexed test case
		IndexedInteger[] test = arrayRandomIndexed(size);
		sorter.sort(test);
		
		for(int x = 1; x < test.length; x++)
		{
			//If the values are the same
			if(test[x].getValue() == test[x - 1].getValue())
			{
				//Smaller index later means unstable sort
				if(test[x].getIndex() < test[x - 1].getIndex()) return false;
				else continue;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if the provided sorter correctly sorts a random array of the chosen size
	 * <br><br>
	 * Uses the overloaded function {@link #checkSorted(Integer[])} internally
	 * @param sorter Sorter object implementing the {@link ISort} interface
	 * @param size Size of test array to use
	 * @return {@code true} if test array is correctly sorted, {@code false} otherwise
	 */
	public boolean checkSorted(ISort sorter, int size)
	{
		//Create a new random test case
		Integer[] test = arrayRandom(size);
		sorter.sort(test);
		
		//Use overloaded checkSorted function to check correctness
		return checkSorted(test);
	}
	
	/**
	 * Checks if the provided integer array is correctly sorted in ascending order
	 * @param array The integer array to check
	 * @return {@code true} if array is sorted in ascending order, {@code false} otherwise
	 */
	private static boolean checkSorted(Integer[] array)
	{
		//Checks that array is sorted in ascending order
		for(int x = 1; x < array.length; x++)
		{
			if(array[x] >= array[x-1]) continue;
			else return false;
		}
		
		return true;
	}
	
	//Convenience constants for test case sizes
	private static final int TEST_ONE_SIZE = 1;
	private static final int TEST_SML_SIZE = 1000;
	private static final int TEST_MED_SIZE = 10000;
	private static final int TEST_MAX_SIZE = 100000;
	
	/**
	 * Creates a test array containing random (may be repeated) elements between {@code 0} and {@code size}
	 * @param size Size of array to generate
	 * @return An array of integers of the chosen size
	 */
	private static Integer[] arrayRandom(int size)
	{
		Integer[] array = new Integer[size];
		
		//Floating point rounding will result in repeated numbers for large size
		for(int x = 0; x < size; x++) array[x] = (int) (Math.random() * size);
		
		return array;
	}
	
	/**
	 * Creates a test array containing random (may be repeated) elements between {@code 0} and {@code size}
	 * <br><br>
	 * Uses {@link IndexedInteger} as array elements
	 * @param size Size of array to generate
	 * @return An array of {@link IndexedIntegers} of the chosen size
	 */
	private static IndexedInteger[] arrayRandomIndexed(int size)
	{
		IndexedInteger[] array = new IndexedInteger[size];
		
		for(int x = 0; x < size; x++) array[x] = new IndexedInteger(x, (int) (Math.random() * size));
		
		return array;
	}
	
	/**
	 * Creates a test array that is sorted in ascending order containing elements from {@code 0} to {@code size - 1}
	 * @param size Size of array to generate
	 * @return An array of integers of the chosen size
	 */
	private static Integer[] arraySorted(int size)
	{
		Integer[] array = new Integer[size];
		
		for(int x = 0; x < size; x++) array[x] = x + 1;
		
		return array;
	}
	
	/**
	 * Creates a test array that is sorted in descending order containing elements from {@code size} to {@code 1}
	 * @param size Size of array to generate
	 * @return An array of integers of the chosen size
	 */
	private static Integer[] arrayReverse(int size)
	{
		Integer[] array = new Integer[size];
		
		for(int x = 0; x < size; x++) array[x] = size - x;
		
		return array;
	}
	
	/**
	 * Creates a test array that is sorted in ascending order containing elements from {@code 0} to {@code size - 1} but with a single element in the wrong position
	 * @param size  Size of array to generate
	 * @param front If {@code true}, {@code array[0]} is set to {@code size}, otherwise {@code array[size - 1]} is set to {@code 0}
	 * @return  An array of integers of the chosen size
	 */
	private static Integer[] arrayOneOff(int size, boolean front)
	{
		
		Integer[] array = new Integer[size];
		
		for(int x = 0; x < size; x++)
		{
			array[x] = x;
		}
		
		if(front) array[0] = size;
		else array[size - 1] = 0;
		
		return array;
	}
	
	/**
	 * Internally used utility class to allow integer elements to be indexed and check for sorting stability
	 * @author Zhu Chunqi
	 */
	private static class IndexedInteger implements Comparable<IndexedInteger>
	{
		private int index;
		private int value;
		
		public IndexedInteger(int index, int value)
		{
			this.index = index;
			this.value = value;
		}
		
		public int getIndex() {return this.index;}
		public int getValue() {return this.value;}
		
		public int compareTo(IndexedInteger other)
		{
			if(this.value < other.getValue()) return -1;
			else if(this.value == other.getValue()) return 0;
			else return 1;
		}
	}
	
	/**
	 * Programmatically test a provided array of sorters for sorting stability and prints results
	 * <br><br>
	 * Uses the function {@link #isStable} internally
	 * @param sorters Array of sorters which implement the ISort interface
	 * @param sorterNames Array of strings containing the names of the sorters (Must be the same length as sorters)
	 * @param size Number of entries in the test array
	 */
	private static void testStability(List<ISort> sorters, String[] sorterNames, int size)
	{
		for(int x = 0; x < sorters.size(); x++)
		{
			String result;
			if(isStable(sorters.get(x), size)) result = "YStable";
			else result = "NStable";
			System.out.println(sorterNames[x] + " is " + result);
		}
	}
	
	/**
	 * Performs several actions:
	 * <br>
	 * 1. Creates and instantiates a list of test arrays<br>
	 * 2. Creates and instantiates one of each unknown sorter<br>
	 * 3. Tests all sorters for sorting stability<br>
	 * 4. Prints the time taken for each sorter to sort each test case and also check for correctness
	 * @param args Command line arguments (unused)
	 */
	public static void main (String[] args)
	{
		StopWatch stopWatch = new StopWatch();
		
		//Create a list of test cases using the test case creation functions
		List<Integer[]> testCases = new ArrayList<Integer[]>();
		testCases.add(arrayOneOff(TEST_MAX_SIZE, true));
		testCases.add(arrayOneOff(TEST_MAX_SIZE, false));
		testCases.add(arrayRandom(TEST_MED_SIZE));
		testCases.add(arrayRandom(TEST_MED_SIZE));
		testCases.add(arraySorted(TEST_MAX_SIZE));
		testCases.add(arrayReverse(TEST_MAX_SIZE));
		String[] testNames = {"ArrayOneOff(f)", "ArrayOneOff(b)", "ArrayRandom", "ArrayRandom", "ArraySorted", "ArrayReverse"};
		
		//Create a list of sorters for easier reference
		List<ISort> sorters = new ArrayList<ISort>();
		sorters.add(new SorterA());
		sorters.add(new SorterB());
		sorters.add(new SorterC());
		sorters.add(new SorterD());
		sorters.add(new SorterE());
		sorters.add(new SorterF());
		String[] sorterNames = {"SorterA", "SorterB", "SorterC", "SorterD", "SorterE", "SorterF"};
		
		//Test and print the sort stability of each sorter
		testStability(sorters, sorterNames, TEST_MED_SIZE);
		
		//Go through each test case
		for(int x = 0; x < testCases.size(); x++)
		{
			System.out.println("=" + testNames[x] + "=");
			
			//Go through each sorter
			for(int y = 0; y < sorters.size(); y++)
			{
				//Make a copy of the test case since the sorters sort in-place
				Integer[] testCase = testCases.get(x);
				Integer[] testCopy = new Integer[testCase.length];
				System.arraycopy(testCase, 0, testCopy, 0, testCase.length);
				
				//Time the sorter
				stopWatch.reset();
				stopWatch.start();
				sorters.get(y).sort(testCopy);
				stopWatch.stop();
				
				//Check if the result is actually sorted
				String result;
				if(checkSorted(testCopy)) result = "PASS";
				else result = "FAIL";
				
				System.out.println(sorterNames[y] + " " + result + ": " + stopWatch.getTime());
			}
		}
	}
}
