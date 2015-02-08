package sg.edu.nus.cs2020;

import java.util.Arrays;

public class CloudSorter
{
	private boolean m_isSorted;
	
	/**
	 * Performs cloud based sorting on the input file and outputs the sorted list using the specified number of servers.
	 * <br><br>
	 * Does bubble sort on the pages with a phase complexity of O(k) given 2k pages and k servers
	 * @param inFile
	 * @param outFile
	 * @param numServers
	 * @return
	 */
	public void cloudSort(String inFile, String outFile, int numServers)
	{
		//Initialize cloud manager using AmazonEC2 and 1000 page size as default
		CloudManager manager = new CloudManager(ICloudManager.CloudProvider.AmazonEC2, 1000);
		manager.initiliazeCloud(inFile, numServers);
		
		//Store for easy reference
		int numPages = manager.numPages();
		
		//Also serves as the server id
		int serversLeft = numServers - 1;
		
		//Bubble sort on pages requires as many iterations as there are pages
		for(int x = 0; x < numPages; x++)
		{
			/*
			//DEBUG: Prints all elements
			for(int a = 0; a < manager.numElements(); a++)
			{
				System.out.print(manager.getElement(a) + " ");
			}
			System.out.println();
			*/
			
			//Each server can handle two pages so we only need half as many servers as there are pages
			for(int y = 0; y < (numPages + 1) / 2; y++)
			{
				//In each iteration the offset is increased by one
				int pageOne = (x + (y * 2)) % numPages;
				int pageTwo = (pageOne + 1) % numPages;
				
				//Check for the last server only as the sorting is strictly ascending without
				//regard for the page indices
				if(pageTwo < pageOne) pageTwo = pageOne;
				
				//If there are no servers left execute the current phase first
				if(serversLeft < 0)
				{
					manager.executePhase();
					serversLeft = numServers - 1;
				}
				
				//Schedule sort for the server
				manager.scheduleSort(serversLeft, pageOne, pageTwo);
				serversLeft--;
				
				//DEBUG: Prints the scheduling of each server and which pages
				//System.out.println("Schedule " + y + ": " + pageOne + " & " + pageTwo + "(" + numPages + ")");
			}
			
			//Clear scheduled work to prevent double scheduling pages
			manager.executePhase();
			serversLeft = numServers - 1;
			
			//DEBUG: Prints the current phase
			//System.out.println("Phase: " + x);
		}
		
		//manager.getStatus();
		manager.shutDown(outFile);
		
		m_isSorted = manager.isSorted();
	}
	
	public boolean isSorted() {return m_isSorted;}
	
	/**
	 * Checks if the array is correctly sorted by sampling k elements using binary search.
	 * @param array The array to check
	 * @param k Number of elements to sample
	 * @return True if the array is probably sorted, false if it is definitely not
	 */
	public boolean isSorted(int[] array, int k)
	{
		for(int x = 0; x < k; x++)
		{
			int randIndex = (int) (Math.random() * (array.length - 1));
			int foundPos = Arrays.binarySearch(array, array[randIndex]);
			
			//Found position is negative if binary search could not find the key
			if(foundPos != randIndex) return false;
		}
		
		//All samples check out
		return true;
	}
}