package sg.edu.nus.cs2020;

public class Tower implements Comparable<Tower>
{
	private int m_left;
	private int m_right;
	private int m_coverage;
	
	/**
	 * Constructor for a tower using {@code location}, {@code range} and {@code highwayLength} that is converted into left coverage, right coverage and coverage amount
	 * <br><br>Parameters are not checked
	 * @param location The location of the tower
	 * @param range The range of the tower
	 * @param highwayLength The length of the highway
	 */
	public Tower(int location, int range, int highwayLength)
	{
		m_left = location - range;
		if(m_left < 0) m_left = 0;
		
		m_right = location + range - 1;
		if(m_right >= highwayLength) m_right = highwayLength - 1;
		
		m_coverage = m_right - m_left + 1;
		if(range == 0) m_coverage = 0;
	}
	
	/**
	 * Constructor for a tower using {@code left} and {@code right} coverage and calculates coverage amount
	 * @param left The leftmost index of the highway section that the tower covers
	 * @param right The rightmost index of the highway section that the tower covers
	 */
	public Tower(int left, int right)
	{
		m_left = left;
		m_right = right;
		m_coverage = right - left + 1;
	}
	
	public int getLeft() {return m_left;}
	public int getRight() {return m_right;}
	public int getCoverage() {return m_coverage;}
	
	@Override
	public int compareTo(Tower other)
	{
		if(this.getLeft() > other.getLeft()) return 1;
		else if(this.getLeft() == other.getLeft()) return 0;
		else return -1;
	}
	
	/**
	 * Merges the provided towers into a single tower that combines the range of both towers
	 * @param left The left tower
	 * @param right The right tower
	 * @return
	 */
	public static Tower mergeTowers(Tower left, Tower right)
	{
		//Check that left tower is overlapping or adjacent to the right tower
		if(left.getRight() + 1 < right.getLeft())
		{
			throw new IllegalArgumentException("Left tower does not overlap right tower");
		}
		
		//Determine larger right coverage
		int largerRight = left.getRight() < right.getRight() ? right.getRight() : left.getRight();
		
		return new Tower(left.getLeft(), largerRight);
	}
}
