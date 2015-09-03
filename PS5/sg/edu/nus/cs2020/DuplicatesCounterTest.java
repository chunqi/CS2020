package sg.edu.nus.cs2020;

import static org.junit.Assert.*;
import sg.edu.nus.cs2020.ZhuPS5.DuplicatesCounter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;

public class DuplicatesCounterTest
{	
	/*
	@Test
	public void hashTest()
	{
		int[] input = {65};
		String output = DuplicatesCounter.getHashLong(input);
		
		System.out.println(output);
		
		//MD5 hash of 'A' in ASCII
		assertEquals(0, output.compareTo("�bp��Y5�.��)"));
	}
	*/
	
	private int _getDuplicatesCount(String filename)
	{
		//Initialize and start stopwatch
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try
		{
			fileReader = new FileReader(filename);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error: File <" + filename + "> not found.");
		}
		
		bufferedReader = new BufferedReader(fileReader);	
		DuplicatesCounter counter = new DuplicatesCounter(bufferedReader);
		
		//Stop the stopwatch and output time elapsed
		stopWatch.stop();
		BigDecimal timeElapsed = new BigDecimal(stopWatch.getTime());
		System.out.println("<" + filename + "> took: " + timeElapsed.toPlainString() + "s");
		
		return counter.getDuplicatesCount();
	}
	
	private int _getAnswer(String filename)
	{
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try
		{
			fileReader = new FileReader(filename);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error: File <" + filename + "> not found.");
		}
		
		bufferedReader = new BufferedReader(fileReader);
		int answer = 0;
		
		try
		{
			answer = Integer.parseInt(bufferedReader.readLine());
			bufferedReader.close();
		}
		catch (NumberFormatException e)
		{
			System.out.println("Error: NumberFormatException");
		}
		catch (IOException e)
		{
			System.out.println("Error: IOException");
		}
		
		return answer;
	}
	
	@Test
	public void duplicatesCountTest2()
	{
		int duplicatesCount = _getDuplicatesCount("2.in.txt");
		int answer = _getAnswer("2.out.txt");
		//System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest4()
	{
		int duplicatesCount = _getDuplicatesCount("4.in.txt");
		int answer = _getAnswer("4.out.txt");
		//System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest5()
	{
		int duplicatesCount = _getDuplicatesCount("5.in.txt");
		int answer = _getAnswer("5.out.txt");
		//System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest6()
	{
		int duplicatesCount = _getDuplicatesCount("6.in.txt");
		int answer = _getAnswer("6.out.txt");
		//System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest7()
	{
		int duplicatesCount = _getDuplicatesCount("7.in.txt");
		int answer = _getAnswer("7.out.txt");
		//System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest8()
	{
		int duplicatesCount = _getDuplicatesCount("8.in.txt");
		int answer = _getAnswer("8.out.txt");
		//System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
}
