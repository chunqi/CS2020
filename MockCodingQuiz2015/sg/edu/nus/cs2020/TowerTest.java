package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class TowerTest
{
	@Test
	public void towerConstructorTest()
	{
		Block a = new Block(1, 1);
		Block b = new Block(1, 1);
		Block c = new Block(1, 1);
		Tower base = new Tower(a, b, 0.25);
		Tower entire = new Tower(base, c, 0.75);
		
		double length = entire.getLength();
		double centerOfMass = entire.getCenterOfMass();
		
		System.out.println(length);
		System.out.println(centerOfMass);
		
		assertEquals(0, Double.compare(1.75, length));
		assertEquals(0, Double.compare(2.5/3, centerOfMass));
	}
	
	@Test
	public void threeBlockLengthTest()
	{
		IBody tower = Tower.buildTower(3);
		
		double length = tower.getLength();
		double centerOfMass = tower.getCenterOfMass();
		
		System.out.println(length);
		System.out.println(centerOfMass);
		
		assertEquals(0, Double.compare(1.75, length));
		assertEquals(0, Double.compare(2.5/3, centerOfMass));
	}
	
	@Test
	public void thousandBlockLengthTest()
	{
		IBody tower = Tower.buildTower(1000);
		
		double length = tower.getLength();
		double centerOfMass = tower.getCenterOfMass();
		
		//Sum of (0.5)^x from 0 to infinity = 2
		System.out.println(length);
		
		//Should be ever so slightly greater than 0.5
		System.out.println(centerOfMass);
	}
}