package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoverageCalculator
{
	private int m_highwayLength;
	private int m_coverage = 0;
	private boolean m_invalid = false;
	private List<Tower> m_towers;
	
	/**
	 * Constructor stores the {@code highwayLength} and initializes the {@code m_towers} {@link ArrayList}
	 * @param highwayLength Length of the highway
	 */
	public CoverageCalculator(int highwayLength)
	{
		m_highwayLength = highwayLength;
		m_towers = new ArrayList<Tower>();
	}
	
	/**
	 * Adds a tower to the internal list of towers, checking iteratively through the list for overlaps and merges them.
	 * Also calculates the total coverage.<br><br>
	 * Does not add tower to internal list if tower range is {@code 0}
	 * @param location The location of the tower to be added
	 * @param range The range of the tower
	 */
	public void addTower(int location, int range)
	{
		//Check that location is valid
		if(location > m_highwayLength || location < 0)
		{
			m_invalid = true;
			throw new IllegalArgumentException("Error: invalid location.");
		}
		
		//Check that range is valid
		if(range < 0)
		{
			m_invalid = true;
			throw new IllegalArgumentException("Error: invalid range.");
		}
		
		//Do nothing if range is 0
		if(range == 0) return;
		
		Tower newTower = new Tower(location, range, m_highwayLength);
		
		//Add the tower to the list of towers
		m_towers.add(newTower);
		
		//Sort the list of towers by left coverage
		//Reference: http://stackoverflow.com/questions/18441846/how-sort-a-arraylist-in-java
		Collections.sort(m_towers);
		
		//Set initial coverage to the leftmost tower coverage
		int coverage = m_towers.get(0).getCoverage();
		int index = 0;
		
		//Iterate through the list in-place and merge all overlapping towers
		while(index < m_towers.size() - 1)
		{
			Tower left = m_towers.get(index);
			Tower next = m_towers.get(index + 1);
			
			//If the next tower is overlapping or adjacent to left tower
			if(left.getRight() + 1 >= next.getLeft())
			{
				//Merge the towers
				Tower mergedTower = Tower.mergeTowers(left, next);
				
				//Add the difference in coverage to cumulative coverage
				coverage += mergedTower.getCoverage() - left.getCoverage();
				
				//Replace left tower with merged tower
				m_towers.set(index, mergedTower);
				
				//Remove next tower
				m_towers.remove(index + 1);
			}
			//If left tower is stand alone
			else
			{
				//Add the next tower's coverage
				coverage += next.getCoverage();
				
				//Increment counter only for stand alone tower
				index++;
			}
		}
		
		//Update the total coverage calculated
		m_coverage = coverage;
	}
	
	/**
	 * Gets the cumulative coverage of all the added towers
	 * @return The total coverage or {@code 0} if there was an error
	 */
	public int getCoverage()
	{
		if(m_invalid) return 0;
		else return m_coverage;
	}
}