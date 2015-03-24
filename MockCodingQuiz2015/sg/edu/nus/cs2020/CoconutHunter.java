package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.List;

public class CoconutHunter
{
	/**
	 * Find the lighter coconut by binary search
	 * @param coconuts
	 * @return
	 */
	public static int findLightCoconut(BunchOfCoconuts coconuts)
	{
		
		List<Integer> leftList;
		List<Integer> rightList;
		
		int numCoconuts = coconuts.getNumCoconuts();
		
		if(numCoconuts == 1) return 0;
		else
		{
			int leftIndex = 0;
			int rightIndex = numCoconuts - 1;
			int middleIndex;
			int balanceResult;
			
			while(true)
			{
				middleIndex = leftIndex + ((rightIndex - leftIndex) / 2);
				
				leftList = buildList(leftIndex, middleIndex);
				rightList = buildList(middleIndex + 1, rightIndex);
				
				if(leftList.size() > rightList.size()) leftList.remove(leftList.size() - 1);
				
				balanceResult = coconuts.balance(leftList, rightList);
				
				if(balanceResult == 0) return middleIndex;
				else if(balanceResult == -1)
				{
					if(leftList.size() == 1) return leftList.get(0);
					else rightIndex = middleIndex;
				}
				else
				{
					if(rightList.size() == 1) return rightList.get(0);
					else leftIndex = middleIndex + 1;
				}
			}
		}
	}
	
	private static List<Integer> buildList(int from, int to)
	{
		List<Integer> list = new ArrayList<Integer>();
		
		for(int x = from; x <= to; x++) list.add(x);
		
		return list;
	}

	public static void main(String[] args)
	{
		BunchOfCoconuts coconuts = new BunchOfCoconuts(101, 51);
		System.out.println(findLightCoconut(coconuts));
		System.out.println("Times balance used: " + coconuts.getNumBalance());
	}
}