package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.List;

public class SortersTest
{
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
	
	public boolean checkSorted(ISort sorter, int size)
	{
		//Create a new random test case
		Integer[] test = arrayRandom(size);
		sorter.sort(test);
		
		//Use overloaded checkSorted function to check correctness
		return checkSorted(test);
	}
	
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
	
	//Creates a test case of size with random numbers between 0 and size
	private static Integer[] arrayRandom(int size)
	{
		Integer[] array = new Integer[size];
		
		//Floating point rounding will result in repeated numbers for large size
		for(int x = 0; x < size; x++) array[x] = (int) (Math.random() * size);
		
		return array;
	}
	
	//Same as above except each element is indexed to test for sorting stability
	private static IndexedInteger[] arrayRandomIndexed(int size)
	{
		IndexedInteger[] array = new IndexedInteger[size];
		
		for(int x = 0; x < size; x++) array[x] = new IndexedInteger(x, (int) (Math.random() * size));
		
		return array;
	}
	
	//Creates a test case where the array is already sorted from 1 to size
	private static Integer[] arraySorted(int size)
	{
		Integer[] array = new Integer[size];
		
		for(int x = 0; x < size; x++) array[x] = x + 1;
		
		return array;
	}
	
	
	//Creates a test case where the array is reverse sorted from size to 1
	private static Integer[] arrayReverse(int size)
	{
		Integer[] array = new Integer[size];
		
		for(int x = 0; x < size; x++) array[x] = size - x;
		
		return array;
	}
	
	//Creates a test case where the array is almost fully sorted
	//but array[0] set to size if front is true
	//or array[size - 1] set to 0 if front is false
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
	
	static class IndexedInteger implements Comparable<IndexedInteger>
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
	
	public static void main (String[] args)
	{
		StopWatch stopWatch = new StopWatch();
		
		//Create a list of test cases using the functions
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
		
		//Test the sort stability of each sorter
		testStability(sorters, sorterNames, TEST_MED_SIZE);
		
		//Go through each test case
		for(int x = 0; x < testCases.size(); x++)
		{
			System.out.println("=" + testNames[x] + "=");
			//For each sorter
			for(int y = 0; y < sorters.size(); y++)
			{
				//Make a copy of the test case since sort is in-place
				Integer[] testCase = testCases.get(x);
				Integer[] testCopy = new Integer[testCase.length];
				System.arraycopy(testCase, 0, testCopy, 0, testCase.length);
				
				//Time the sort
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
