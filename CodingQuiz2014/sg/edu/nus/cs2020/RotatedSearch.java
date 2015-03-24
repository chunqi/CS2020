package sg.edu.nus.cs2020;

public class RotatedSearch
{
	/**
	 * Binary search to find the maximum element within a sorted array which may be shifted on a wrap around basis.
	 * @param sortedArray The array to search in, must implement Comparable.
	 * @return The maximum element within the array, null if not found.
	 */
	public static <T extends Comparable<T>> T searchMax(T[] sortedArray)
	{
		int length = sortedArray.length;
		
		//Special case where the sequence is not shifted or length 1
		if(sortedArray[0].compareTo(sortedArray[length - 1]) <= 0)
		{
			return sortedArray[length - 1];
		}
		
		int start = 0;
		int end = length - 1;
		int middle;
		
		//Binary search for the wrap around point where A[n] > A[n+1]
		while(end >= start)
		{
			middle = start + ((end - start) / 2);
			
			//Check the middle index
			if(sortedArray[middle].compareTo(sortedArray[middle + 1]) > 0)
			{
				return sortedArray[middle];
			}
			
			//A[start] < A[middle] is a sorted sequence, search in other half
			if(sortedArray[start].compareTo(sortedArray[middle]) < 0)
			{
				start = middle + 1;
			}
			//A[start] > A[middle] the wrap around is within this half
			else
			{
				end = middle;
			}
		}
		
		//Should not reach here
		return null;
	}
}