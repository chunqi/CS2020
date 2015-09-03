package sg.edu.nus.cs2020;

//  Import file handling classes
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * class HerbertLog
 * The HerbertLog class records the jobs worked by Herbert, and
 * the wages paid to Herbert, over the last period of employment.
 * The constructor opens the specified log-file, and the get(.) method
 * returns records from the file.
 * 
 */

public class HerbertLog
{
	/**
	 *  Public static final constants
	 */
	
	// Separator character used in the database file
	public static final String SEP = ":";
	// Length of each record in the database
	public static final int rLength = 18;
	// Padding character for database file
	public static final char PADDING = '.';

	/**
	 * Private state for the HerbertLog
	 */
	
	// Filename where the database can be found
	private String m_name = null;
	// Variable that points to the database, once opened
	private File m_file = null;
	// Variable for reading from the database file
	private RandomAccessFile m_inRAM = null;
	// Size of the database: number of available records
	private long m_numMinutes = 0;
	
	/**
	 * Debugging information
	 */
	
	// Number of "get" operations performed on the database
	// Note this is primarily for debugging.
	protected long m_numGets = 0;
	
	/**
	 * Constructor 
	 * @param filename File where the database can be found.
	 * The specified file must exist, and must contain records
	 * in the proper format. 
	 **/
	
	HerbertLog(String fileName)
	{
		// Save the filename
		m_name = fileName;
		// Next, we open the file
		try
		{
			// Open the file
			m_file = new File(m_name);
			m_inRAM = new RandomAccessFile(m_file, "r");
			
			// Calculate the number of records in the database by
			// dividing the number of characters by the length of each record
			long numChars = m_inRAM.length();
			m_numMinutes = numChars/rLength;
		}
		catch(IOException e)
		{
			System.out.println("Error opening file: " + e);
		}
	}
	
	/**
	 * size
	 * @return the number of records in the database
	 */
	
	public long numMinutes()
	{
		return m_numMinutes;
	}

	/**
	 * numGets : primarily for debugging
	 * @return number of times get has been called
	 */
	
	public long numGets()
	{
		return m_numGets;
	}

	/**
	 * get
	 * @param i specified the record number to retrieve, starting from 0
	 * @return the specified record, if it exists, or null otherwise
	 */
	
	public Record get(long i)
	{
		// Increment the number of "get" operations
		m_numGets++;
		
		// Check for errors: if i is too large or too small, fail
		if (i > numMinutes()) return null;
		if (i < 0) return null;
		
		// Retrieve the proper record
		try
		{
			// First, calculate the offset into the file, and seek to that location
			long numChars = i*rLength;			
			m_inRAM.seek(numChars);
			
			// Next, read in rLength bytes
			// Recall that rLength is the length of one record
			byte[] entry = new byte[rLength];
			m_inRAM.read(entry);
			
			// Now, convert the string to a record.
			// Convert it to a string...
			String line = new String(entry);
			// .. parse the string using the record separator
			String[] tokens = line.split(SEP);
			// Every record should have 2 or 3 components
			assert(tokens.length==2 || tokens.length==3);
			// The first token is the name
			String name = tokens[0];
			// The second token is the height
			int height = Integer.parseInt(tokens[1]);
			return new Record(name, height);
			
		}
		catch(IOException e)
		{
			System.out.println("Error getting data from file: " + e);
		}
		// If the record wasn't found, for any reason, return null
		return null;
	}
	
	//Wrapper function to call the different versions
	public int calculateSalary()
	{
		return calculateSalaryV1();
	}
	
	private int calculateSalaryV1()
	{
		String employer = null;
		int salary = 0;
		
		//Iterate through all records
		for(long x = this.numMinutes() - 1; x >= 0; x--)
		{
			Record record = this.get(x);
			if(record.getName().equals(employer)) continue;
			else
			{
				salary += record.getWages();
				employer = record.getName();
			}
		}
		
		return salary;
	}
	
	//Cache all records we get, also maintain a list of start indices
	private List<Record> cache = new ArrayList<Record>();
	private List<Integer> cacheIndex = new ArrayList<Integer>();
	private List<Integer> startIndex = new ArrayList<Integer>();
	
	private int calculateSalaryV2()
	{
		Record currRecord = this.get(0);
		
		//0 is always the start index for the first person
		startIndex.add(0);
		
		//Handle single record case separately
		if(this.numMinutes() == 1)
		{
			return currRecord.getWages();
		}
		
		Record lastRecord = this.get(this.numMinutes() - 1);
		
		//Skipping mechanism, numbers chosen sort of equivalent to binary search
		int[] toSkip = {0, 1, 2, 5, 11, 21, 57, 115, 231, 463, 927, 1855, 3711, 7423, 14847};
		int skipIndex = 0;
		int recordIndex = 0;
		int salary = 0;
		
		//Debugging variables
		int maxSkipIndex = 0;
		int maxLength = 0;
		int minLength = 1000000;
		int numRecords = 0;
		
		//Cache first and last records
		cache.add(currRecord);
		cache.add(lastRecord);
		cacheIndex.add(0);
		cacheIndex.add((int) this.numMinutes() - 1);
		
		while(!currRecord.getName().equals(lastRecord.getName()))
		{
			//Calculate index of record to skip to
			int skipRecordIndex = recordIndex + toSkip[skipIndex] + 1;
			
			//Bounds check on record index
			if(skipRecordIndex >= this.numMinutes())
			{
				skipIndex--;
				continue;
			}
			
			//Get the record from cache if available
			Record skipRecord = null;
			
			for(int x = 0; x < cacheIndex.size(); x++)
			{
				if(cacheIndex.get(x) == skipRecordIndex)
				{
					skipRecord = cache.get(x);
				}
			}
			
			//Not in cache store the index as well as the record
			if(skipRecord == null)
			{
				skipRecord = this.get(skipRecordIndex);
				cache.add(skipRecord);
				cacheIndex.add(skipRecordIndex);
			}
			
			//Checking the name of the current record and the skipped to record
			if(skipRecord.getName().equals(currRecord.getName()))
			{
				//Still the same person so we increment skipIndex and try again
				if(skipIndex < toSkip.length - 1)
				{
					skipIndex++;
					if(skipIndex > maxSkipIndex) maxSkipIndex = skipIndex;
				}
				currRecord = skipRecord;
				recordIndex = skipRecordIndex;
			}
			else
			{
				//Reached the next person we need to skip less records
				if(skipIndex > 0) skipIndex--;
				else
				{
					//Skipping 0 records and reached the next person? Bingo
					salary += currRecord.getWages();
					currRecord = skipRecord;
					
					//Record skipRecordIndex which is the start of the next person
					this.startIndex.add(skipRecordIndex);
					
					//Record max and min lengths
					int startIndexLast = this.startIndex.size() - 1;
					int prevIndex = this.startIndex.get(startIndexLast - 1);
					int currIndex = this.startIndex.get(startIndexLast);
					int length = currIndex - prevIndex;
					if(length > maxLength) maxLength = length;
					if(length < minLength) minLength = length;
					//System.out.println(length);
					numRecords++;
					
					//Set to maximum skip index seen
					skipIndex = maxSkipIndex - 1;
					//maxSkipIndex = 0;
				}
			}
		}
		
		//Debugging prints
		//System.out.println("maxLength: " + maxLength);
		//System.out.println("minLength: " + minLength);
		//System.out.println("numRecords: " + numRecords);
		
		//Remember to add last record
		salary += lastRecord.getWages();
		
		return salary;
	}
	
	public int calculateMinimumWork(int goal)
	{
		int minMinutes = 0;
		int salaryCount = 0;
		
		//Calculate max salary available
		int maxSalary = this.calculateSalary();
		
		if(maxSalary < goal) return -1;
		
		//Calculate number of records each person has
		List<Integer> recordLengths = new ArrayList<Integer>();
		for(int x = 1; x < this.startIndex.size(); x++)
		{
			recordLengths.add(startIndex.get(x) - startIndex.get(x - 1));
		}
		recordLengths.add((int) this.numMinutes() - startIndex.get(startIndex.size() - 1));
		
		while(salaryCount < goal)
		{
			minMinutes++;
			for(int x = 0; x < startIndex.size(); x++)
			{
				
			}
		}
		
		return minMinutes;
	}
}
