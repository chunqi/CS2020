package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class DuplicatesCounterTest
{
	private static final String _DIR_PREPEND = "sg/edu/nus/cs2020/";
	
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
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try
		{
			fileReader = new FileReader(_DIR_PREPEND + filename);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error: File <" + filename + "> not found.");
		}
		
		bufferedReader = new BufferedReader(fileReader);	
		DuplicatesCounter counter = new DuplicatesCounter(bufferedReader);
		
		return counter.getDuplicatesCount();
	}
	
	private int _getAnswer(String filename)
	{
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		try
		{
			fileReader = new FileReader(_DIR_PREPEND + filename);
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
		System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest4()
	{
		int duplicatesCount = _getDuplicatesCount("4.in.txt");
		int answer = _getAnswer("4.out.txt");
		System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest5()
	{
		int duplicatesCount = _getDuplicatesCount("5.in.txt");
		int answer = _getAnswer("5.out.txt");
		System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest6()
	{
		int duplicatesCount = _getDuplicatesCount("6.in.txt");
		int answer = _getAnswer("6.out.txt");
		System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest7()
	{
		int duplicatesCount = _getDuplicatesCount("7.in.txt");
		int answer = _getAnswer("7.out.txt");
		System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
	
	@Test
	public void duplicatesCountTest8()
	{
		int duplicatesCount = _getDuplicatesCount("8.in.txt");
		int answer = _getAnswer("8.out.txt");
		System.out.println(duplicatesCount);
		assertEquals(answer, duplicatesCount);
	}
}
