package sg.edu.nus.cs2020;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class takes in a BufferedReader object pointing to a text file that contains as the first line
 * n the number of lines of data, and subsequently n lines of data. Each line of data is hashed by
 * first producing an array of character counts which is then fed to MD5. The hash is stored in a
 * HashMap together with the count of lines which have the same hash. The total pairs count is then
 * calculated by applying arithmetic series sum over all key counts which are greater than 1.
 * @author chunqi
 *
 */
public class DuplicatesCounter
{
	//Private variables used in computing result
	private int m_size;
	private int m_duplicatesCount;
	private BufferedReader m_bufferedReader;
	private Map<String, Integer> m_map;
	
	/**
	 * Getter function for duplicates count
	 * @return m_duplicatesCount
	 */
	public int getDuplicatesCount()
	{
		return m_duplicatesCount;
	}
	
	/**
	 * Constructor takes in a BufferedReader and attempts to parse the first line as an integer and is
	 * stored as m_size. It then instantiates the HashMap with capacity 2 * m_size at default 0.75
	 * loading ratio and calls countDuplicates().
	 * @param bufferedReader
	 */
	public DuplicatesCounter(BufferedReader bufferedReader)
	{
		//Check that our BufferedReader has been instantiated properly
		if(bufferedReader == null) throw new IllegalArgumentException("Error: BufferedReader is null");
		
		m_bufferedReader = bufferedReader;
		
		//Get the first line and attempt to parse it as an integer
		try
		{
			m_size = Integer.parseInt(bufferedReader.readLine());
		}
		catch (NumberFormatException e)
		{
			System.out.println("Error: First line contains malformed integer.");
		}
		catch (IOException e)
		{
			System.out.println("Error: Failed to read line");
		}
		
		//Instantiate HashMap with 2 * m_size capacity
		m_map = new HashMap<String, Integer>(2 * m_size);
		
		countDuplicates();
	}
	
	/**
	 * Counts the number of duplicate pairs by calculating hash of the character counts of each line
	 * and adding the count to the HashMap. Pair count is then calculated by accumulating the AP sum of
	 *  the count - 1 of each key.
	 */
	private void countDuplicates()
	{
		//Iterate through the database
		for(int x = 0; x < m_size; x++)
		{
			String nextLine = null;
			
			//Attempt to get the next line
			try
			{
				nextLine = m_bufferedReader.readLine();
			}
			catch (IOException e)
			{
				System.out.println("Error: Failed to read line");
			}
			
			if(nextLine != null)
			{
				//Calculate the hash of the string as the hash of its character counts
				String key;
				if(nextLine.length() > 100) key = getHashLong(getCharCounts(nextLine));
				else key = getHashShort(getCharCounts(nextLine));
				
				//String key = getHashShort(getCharCounts(nextLine));
				
				//If the key is already in the map we increment its count
				if(m_map.containsKey(key))
				{
					m_map.put(key, m_map.get(key) + 1);
				}
				//Otherwise we add the new key with count of 1
				else m_map.put(key, 1);
			}
		}
		
		//Iterate over all the keys of the HashMap
		Iterator<Integer> iter = m_map.values().iterator();
		while(iter.hasNext())
		{
			int count = iter.next();
			
			//We add the AP sum of count - 1 since 2 => 1 pair, 3 => 2+1 pairs etc is an AP of n - 1
			if(count > 1) m_duplicatesCount += apSum(count - 1);
		}
	}
	
	/**
	 * Calculates the AP sum of 1 to n with d = 1
	 * @param n The number of terms to sum
	 * @return
	 */
	private int apSum(int n)
	{
		//AP sum is (n * (a1 + an)) / 2
		return (n * (1 + n)) / 2;
	}
	
	/**
	 * Calculate the character counts of the given string which is assumed ASCII 128 but modulo just in
	 * case
	 * @param s
	 * @return int[128] of character counts corresponding to each character code
	 */
	public int[] getCharCounts(String s)
	{
		int[] charCounts = new int[128];
		
		for(int x = 0; x < s.length(); x++)
		{
			//Increment count of char code
			charCounts[((int) s.charAt(x))]++;
		}
		
		return charCounts;
	}
	
	/**
	 * Generates a hash by concatenating all the character counts into a long string. Should be used
	 * when the string length is short so that MD5 overhead outweighs string building.
	 * @param charCount
	 * @return
	 */
	public static String getHashShort(int[] charCount)
	{
		StringBuilder hash = new StringBuilder();
		
		for(int x = 0; x < charCount.length; x++)
		{
			hash.append(Integer.toString(charCount[x]));
		}
		
		return hash.toString();
	}
	
	/**
	 * Calculates MD5 hash given an int[] of character counts by first converting it into a byte array
	 * which is then digested.
	 * @param charCount
	 * @return The MD5 hash in ASCII
	 */
	public static String getHashLong(int[] charCount)
	{
		//Convert all the elements into bytes
		byte[] inputBytes = new byte[charCount.length];
		
		for(int x = 0; x < charCount.length; x++)
		{
			inputBytes[x] = new Integer(charCount[x]).byteValue();
		}
		
		//Get MD5 instance
		MessageDigest md5 = null;
		
		try
		{
			md5 = MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException e)
		{
			System.out.println("Error: Failed to get MD5 instance");
		}
		
		//Digest bytes
		if(md5 != null)
		{
			byte[] output = md5.digest(inputBytes);
			
			/*StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < output.length; i++) {
	          sb.append(Integer.toString((output[i] & 0xff) + 0x100, 16).substring(1));
	        }
			return sb.toString();*/
			
			return new String(output);
		}
		else return null;
	}
}
