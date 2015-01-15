package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.List;

public class DocumentDistance
{
	private static String[] fileNames = {"hamlet", "henryv", "macbeth", "vortigern", "cromwell", "mystery"};
	
	public static void main(String[] args)
	{
		//A list to contain of all the files we are comparing
		List<EvenBetterVectorTextFile> files = new ArrayList<EvenBetterVectorTextFile>();
		for(int x = 0; x < fileNames.length; x++)
		{
			files.add(new EvenBetterVectorTextFile("texts/"+fileNames[x]+".txt"));
		}
		
		//We compare every file to each other to get the angles between them
		double[] angles = new double[fileNames.length * fileNames.length];
		for(int x = 0; x < fileNames.length; x++)
		{
			System.out.println("== " + fileNames[x] + " ==");
			for(int y = 0; y < fileNames.length; y++)
			{
				//Not calculating the file against itself
				//Auto initialised as 0
				if(x != y)
				{
					angles[x * fileNames.length + y] = EvenBetterVectorTextFile.Angle(files.get(x), files.get(y));
				}
				System.out.println(fileNames[y] + ": " + angles[x * fileNames.length + y]);
			}
		}
		
		//This function outputs the testing of a manually guessed threshold
		testThreshold(angles);
	} 
	
	private static void testThreshold(double[] angles)
	{
		//The threshold value after manually adjusting such that hamlet, macbeth and henryv all
		//pass against each other and all fail on vortigern
		double guess = 0.417;
		System.out.println("== Guess: " + guess + " ==");
		
		for(int x = 0; x < fileNames.length; x++)
		{
			System.out.println("== " + fileNames[x] + " ==");
			for(int y = 0; y < fileNames.length; y++)
			{
				String statusString;
				double angle = angles[x * fileNames.length + y];
				if(angle < guess) statusString = "PASS";
				else statusString = "FAIL";
				System.out.println(fileNames[y] + ": " + statusString + " " + angle);
			}
		}
		
		/*
		 * The mystery text is also likely to be Shakespeare' work as it is similar enough to the 3
		 * confirmed works and is over the threshold against vortigern which is a forged work. The
		 * consistency of the 3 confirmed works and the forged one is also maintained at the threshold.
		 * The classifications are as follows:
		 * Shakespeare's: hamlet, henryv, macbeth, mystery
		 * Not Shakespeare's: vortigern, cromwell
		 */
	}
}
