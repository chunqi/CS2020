package sg.edu.nus.cs2020;

import java.util.*;

public class Tower implements IBody
{
	public static final double MASS = 1;
	public static final double LENGTH = 1;
	
	//Private variables to keep track of tower properties
	private double m_mass;
	private double m_length;
	private double m_centerOfMass;
	private List<Block> m_blocks;
	
	/**
	 * Constructor that takes 2 blocks places the second block on top of the first block with d the
	 * distance between the left edges of both blocks. Center of mass is calculated from a weighted
	 * average.
	 * @param blockA
	 * @param blockB
	 * @param d
	 */
	public Tower(IBody blockA, IBody blockB, double d)
	{
		//Mass is combined
		m_mass = blockA.getMass() + blockB.getMass();
		
		//Length increases by d each time
		m_length = blockB.getLength() + d;
		
		//Center of mass by weighted average of center of masses
		m_centerOfMass = ((blockA.getCenterOfMass() * blockA.getMass()) +
				((d + blockB.getCenterOfMass()) * blockB.getMass())) /
				(blockA.getMass() + blockB.getMass());
		
		//Rebuild the internal blocks list
		m_blocks = new ArrayList<Block>();
		
		//Left block is a pure block
		if(Double.compare(MASS, blockA.getMass()) == 0)
		{
			m_blocks.add(new Block(blockA.getMass(), blockA.getLength()));
		}
		//If its a Tower instead, cast to tower and ask for blocks list
		else
		{
			m_blocks.addAll(((Tower) blockA).getBlocks());
		}
		
		//Right block is a pure block
		if(Double.compare(MASS, blockB.getMass()) == 0)
		{
			m_blocks.add(new Block(blockB.getMass(), blockB.getLength()));
		}
		else
		{
			m_blocks.addAll(((Tower) blockB).getBlocks());
		}
	}
	
	/**
	 * Builds a tower of n blocks from top to bottom, with d = (0.5) ^ x where x is the number of
	 * layers below the topmost block. Each lower block is placed on the left.
	 * @param numBlocks
	 * @return
	 */
	static public IBody buildTower(int numBlocks)
	{
		//Instantiate tower to trivial 1 block case
		IBody tower = new Block(MASS, LENGTH);
		
		//Build tower downwards and leftwards by adding existing tower to a single block a distance
		//of d = (0.5)^x where x is the number of layers down, top layer being 0.
		for(int x = 1; x < numBlocks; x++)
		{
			tower = new Tower(new Block(MASS, LENGTH), tower, Math.pow(0.5, new Double(x)));
		}
		
		return tower;
	}
	
	public static void main(String[] args)
	{
		IBody tower = buildTower(1000);
		System.out.println("Length: " + tower.getLength());
		System.out.println("COM: " + tower.getCenterOfMass());
		
		for(Block b : tower)
		{
			System.out.println("Another block. Length = " + b.getLength()
					+ ", COM = " + b.getCenterOfMass());
		}
	}
	
	/**
	 * Iterator class for the tower returning in left to right order the individual blocks that make up
	 * the tower
	 * @author chunqi
	 *
	 */
	private class TowerIterator implements Iterator<Block>
	{
		private List<Block> m_blocks;
		private int m_index = 0;
		
		/**
		 * Constructor takes in a List of Block objects
		 * @param blocks
		 */
		public TowerIterator(List<Block> blocks)
		{
			m_blocks = blocks;
		}
		
		@Override
		public boolean hasNext()
		{
			if(m_index < m_blocks.size()) return true;
			else return false;
		}
		
		@Override
		public Block next()
		{
			if(m_index < m_blocks.size())
			{
				m_index++;
				return m_blocks.get(m_index - 1);
			}
			else throw new NoSuchElementException();
		}
		
		@Override
		public void remove() {throw new UnsupportedOperationException("Error: Remove not supported.");}
	}
	
	//Start of Getters
	@Override
	public Iterator<Block> iterator()
	{
		return new TowerIterator(m_blocks);
	}
	
	@Override
	public double getCenterOfMass() {return m_centerOfMass;}
	
	@Override
	public double getLength() {return m_length;}
	
	@Override
	public double getMass() {return m_mass;}
	
	public List<Block> getBlocks() {return m_blocks;}
	//End of Getters
}
