package sg.edu.nus.cs2020;

import java.util.ArrayList;

import org.junit.Test;

public class QuestionGameTest
{
	@Test
	public void SmallExampleTest()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/SmallExample");
		try
		{
			game.playGame();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestDB1Test()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/TestDB_1.txt");
		try
		{
			game.playGame();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestDB2Test()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/TestDB_2.txt");
		try
		{
			game.playGame();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestDB3Test()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/TestDB_3.txt");
		try
		{
			game.playGame();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestDB4Test()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/TestDB_4.txt");
		try
		{
			game.playGame();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestDB5Test()
	{
		QuestionGame game = new QuestionGame("sg/edu/nus/cs2020/TestDB_5.txt");
		try
		{
			game.playGame();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void TestAllStats()
	{
		ArrayList<Integer> min = new ArrayList<Integer>();
		ArrayList<Integer> max = new ArrayList<Integer>();
		ArrayList<Integer> total = new ArrayList<Integer>();
		ArrayList<QuestionGame> games = new ArrayList<QuestionGame>();
		
		games.add(new QuestionGame("sg/edu/nus/cs2020/SmallExample"));
		games.add(new QuestionGame("sg/edu/nus/cs2020/TestDB_1.txt"));
		games.add(new QuestionGame("sg/edu/nus/cs2020/TestDB_2.txt"));
		games.add(new QuestionGame("sg/edu/nus/cs2020/TestDB_3.txt"));
		games.add(new QuestionGame("sg/edu/nus/cs2020/TestDB_4.txt"));
		games.add(new QuestionGame("sg/edu/nus/cs2020/TestDB_5.txt"));
		
		for(int x = 0; x < games.size(); x++)
		{
			min.add(9999);
			max.add(0);
			total.add(0);
		}
		
		try
		{
			for(int x = 0; x < games.size(); x++)
			{
				QuestionGame game = games.get(x);
				
				for(int y = 0; y < game.m_objects.size(); y++)
				{
					game.RestartGame(y);
					
					int guessCount = game.playGame();
					
					if(guessCount > max.get(x)) max.set(x, guessCount);
					if(guessCount < min.get(x)) min.set(x, guessCount);
					total.set(x, total.get(x) + guessCount);
				}
				
				System.out.println("===DB" + x + "===");
				System.out.println("Num Objects: " + game.m_objects.size());
				System.out.println("Max Guesses: " + max.get(x));
				System.out.println("Min Guesses: " + min.get(x));
				System.out.println("Avg Guesses: " + (double)(total.get(x) / game.m_objects.size()));
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
