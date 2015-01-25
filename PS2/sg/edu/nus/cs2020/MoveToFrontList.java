package sg.edu.nus.cs2020;

public class MoveToFrontList extends FixedLengthList
{
	//Call parent constructor
	public MoveToFrontList(int length)
	{
		super(length);
	}
	
	@Override
	public boolean search(int key)
	{
		//Iterate through entire array
		for(int x = 0; x < this.m_length; x++)
		{
			//Key is found
			if(this.m_list[x] == key)
			{
				//Shift left half of array from until x down by 1
				//Googled for java array copy and got this in Java doc
				System.arraycopy(this.m_list, 0, this.m_list, 1, x);
				
				//Put key at front of list
				this.m_list[0] = key;
				
				return true;
			}
		}
		
		//Key is not found
		return false;
	}
	
	/* Search with negated signals to represent not found
	@Override
	public boolean search(int key)
	{
		for(int x = 0; x < this.m_length; x++)
		{
			//Key is found
			if(this.m_list[x] == key || this.m_list[x] == -key)
			{
				//Store original value
				int originalValue = this.m_list[x];
				
				//Shift left half of array down by 1
				//Usage with reference to Java doc
				System.arraycopy(this.m_list, 0, this.m_list, 1, x);
				
				//Put key at front of list
				this.m_list[0] = originalValue;
				
				if(originalValue >= 0) return true;
				else return false;
			}
		}
		
		//Fall through if key is not found
		System.arraycopy(this.m_list, 0, this.m_list, 1, this.m_length - 1);
		this.m_list[0] = (-key);
		return false;
	}
	*/
}
