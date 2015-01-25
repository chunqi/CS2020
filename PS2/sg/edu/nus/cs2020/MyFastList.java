package sg.edu.nus.cs2020;

import java.util.Arrays;

public class MyFastList extends FixedLengthList
{
	private boolean m_sorted = false;
	
	//Call parent constructor with parameter
	public MyFastList(int length)
	{
		super(length);
	}
	
	@Override
	public boolean search(int key)
	{
		//Sort list
		if(this.m_sorted == false)
		{
			Arrays.sort(super.m_list);
			this.m_sorted = true;
		}
		
		//Binary search through entire array
		int result = Arrays.binarySearch(super.m_list, key);
		if(result >= 0) return true;
		else return false;
	}
}