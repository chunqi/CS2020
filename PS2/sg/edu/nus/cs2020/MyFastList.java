package sg.edu.nus.cs2020;

public class MyFastList extends FixedLengthList
{
	protected boolean[] m_bucket;
	
	//Call parent constructor with parameter
	public MyFastList(int length)
	{
		super(length);
		m_bucket = new boolean[1000];
	}
	
	@Override
	public boolean search(int key)
	{
		if(this.m_bucket[key] == true) return true;
		else return false;
	}
	
	@Override
	public boolean add(int key)
	{		
		m_max++;
		if(m_max < m_length)
		{
			m_list[m_max] = key;
			m_bucket[key] = true;
			return true;
		}
		else
		{
			System.out.println("Error: list length exceeded.");
			return false;
		}
	}
}