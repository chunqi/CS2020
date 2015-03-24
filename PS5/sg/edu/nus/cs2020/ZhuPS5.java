package sg.edu.nus.cs2020;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.CRC32;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class ZhuPS5
{
	public static void main(String[] args)
	{
		//Initialize and start stopwatch
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		String filename;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//For debugging with sample input
		if(args.length > 0) filename = args[0];
		else filename = "sg/edu/nus/cs2020/4.in.txt";
		
		//Instantiate FileReader and BufferedReader
		try
		{
			fileReader = new FileReader(filename);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error: File <" + filename + "> not found.");
		}
		
		bufferedReader = new BufferedReader(fileReader);
		
		//Instantiate DuplicatesCounter and pass it the BufferedReader
		DuplicatesCounter counter = new DuplicatesCounter(bufferedReader);
		
		//Print the duplicates count
		System.out.println(counter.getDuplicatesCount());
		
		//Stop the stopwatch and output time elapsed
		stopWatch.stop();
		BigDecimal timeElapsed = new BigDecimal(stopWatch.getTime());
		System.out.println("Time taken: " + timeElapsed.toPlainString() + "s");
	}
	
	/**
	 * This class takes in a BufferedReader object pointing to a text file that contains as the first line
	 * n the number of lines of data, and subsequently n lines of data. Each line of data is hashed by
	 * first producing an array of character counts which is then hashed. The hash is stored in a
	 * HashMap together with the count of lines which have the same hash. The total pairs count is then
	 * calculated by applying arithmetic series sum over all key counts which are greater than 1.
	 * @author chunqi
	 *
	 */
	public static class DuplicatesCounter
	{
		//Private variables used in computing result
		private int m_size;
		private int m_duplicatesCount;
		private BufferedReader m_bufferedReader;
		private Map<Integer, Integer> m_map;
		private HashFunction m_murmur;
		
		/**
		 * Getter function for duplicates count
		 * @return m_duplicatesCount
		 */
		public int getDuplicatesCount()
		{
			return m_duplicatesCount;
		}
		
		/**
		 * Constructor takes in a BufferedReader and attempts to parse the first line as an integer
		 * and is stored as the size. It then instantiates the HashMap with capacity 2 * size at
		 * default 0.75 loading ratio and calls countDuplicates().
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
			m_map = new HashMap<Integer, Integer>(2 * m_size);
			
			//Instantiate murmur hash function
			m_murmur = Hashing.murmur3_32();
			
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
					//String key;
					//if(nextLine.length() > 100) key = getHashLong(getCharCounts(nextLine));
					//else key = getHashShort(getCharCounts(nextLine));
					
					//String key = getHashShort(getCharCounts(nextLine));
					//long key = getHashCRC(getCharCounts(nextLine));
					//int key = getHashPrimeModulo(getCharCounts(nextLine));
					int key = getHashMurmur(getCharCounts(nextLine));
					
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
		 * Calculate the character counts of the given string which is assumed ASCII 128
		 * @param s
		 * @return int[128] of character counts corresponding to each character code
		 */
		public int[] getCharCounts(String s)
		{
			int[] charCounts = new int[128];
			
			for(int x = 0; x < s.length(); x++)
			{
				//Increment count of char code
				charCounts[s.charAt(x)]++;
			}
			
			return charCounts;
		}
		
		/**
		 * Calculates the integer hash given the character counts of a string. Using MurmurHash3 from
		 * Google.
		 * @param charCount
		 * @return
		 */
		public int getHashMurmur(int[] charCount)
		{
			Hasher hasher = m_murmur.newHasher();
			
			for(int x = 0; x < charCount.length; x++)
			{
				hasher.putInt(charCount[x]);
			}
			
			HashCode hash = hasher.hash();
			
			return hash.asInt();
		}
		
		/**
		 * Calculates the hash of an integer array using the prime modulo function. Prone to collisions
		 * @param charCount
		 * @return
		 */
		public int getHashPrimeModulo(int[] charCount)
		{
			//Random positive odd integer
			int a = 3;
			//Mersenne prime < MAX INT
			int p = 524287;
			int hash = charCount[0];
			int length = charCount.length;
			
			for(int x = 1; x < length; x++)
			{
				hash = ((hash * a) + charCount[x]) % p;
			}
			
			return hash;
		}
		
		/**
		 * Generates a hash by concatenating all the character counts into a long string. Should be used
		 * when the string length is short so that MD5 overhead outweighs string building.
		 * @param charCount
		 * @return
		 */
		public String getHashShort(int[] charCount)
		{
			StringBuilder hash = new StringBuilder();
			
			for(int x = 0; x < charCount.length; x++)
			{
				hash.append(Integer.toString(charCount[x]));
			}
			
			return hash.toString();
		}
		
		/**
		 * Calculates the hash using the built in CRC function. Prone to collision. +1 pair on dataset
		 * 4.
		 * @param charCount
		 * @return
		 */
		public long getHashCRC(int[] charCount)
		{
			CRC32 crc = new CRC32();
			for(int x = 0; x < charCount.length; x++)
			{
				crc.update(charCount[x]);
			}
			return crc.getValue();
		}
		
		/**
		 * Calculates MD5 hash given an int[] of character counts by first converting it into a byte array
		 * which is then digested.
		 * @param charCount
		 * @return The MD5 hash in ASCII
		 */
		public String getHashLong(int[] charCount)
		{
			//Convert all the elements into bytes
			byte[] inputBytes = new byte[charCount.length];
			Integer charCountX;
			
			for(int x = 0; x < charCount.length; x++)
			{
				charCountX = charCount[x];
				inputBytes[x] = charCountX.byteValue();
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
				
				//Debug functions for converting bytes to hexadecimal display
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
}
