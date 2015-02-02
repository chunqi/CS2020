package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class TowerTest
{
	@Test
	public void towerCoverageInMiddle()
	{
		Tower tower = new Tower(5, 2, 10);
		assertEquals("Tower in middle", 4, tower.getCoverage());
	}
	
	@Test
	public void towerCoverageInLeft()
	{
		Tower tower2 = new Tower(0, 2, 10);
		assertEquals("Tower left edge", 2, tower2.getCoverage());
	}
	
	@Test
	public void towerCoverageRightEdge()
	{
		Tower tower = new Tower(10, 2, 10);
		assertEquals("Tower right edge", 2, tower.getCoverage());
	}
	
	@Test
	public void towerCoverageZeroRange()
	{
		Tower tower = new Tower(5, 0, 10);
		assertEquals("Tower 0 range", 0, tower.getCoverage());
	}
}
