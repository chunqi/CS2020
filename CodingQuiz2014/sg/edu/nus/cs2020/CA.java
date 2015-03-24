package sg.edu.nus.cs2020;

import java.util.Arrays;
import java.util.Iterator;

public class CA implements ICA
{
	private int m_size;
	private int[] m_rule;
	private int[] m_state;
	private int[] m_modState;
	private boolean m_isInitialized = false;
	
	public CA(int size)
	{
		m_size = size;
		m_state = new int[size + 2];
		m_modState = new int[size + 2];
	}
	
	private class CAIterator implements Iterator<String>
	{
		//Don't run step on initial next
		private boolean m_runStep = false;
		
		private CA m_ref;
		
		public CAIterator(CA ref)
		{
			m_ref = ref;
		}
		
		@Override
		public boolean hasNext()
		{
			//Always able to return the next step of CA
			return true;
		}
		
		@Override
		public String next()
		{
			//Run CA step and return new state
			if(m_runStep) step();
			else m_runStep = true;
			
			return m_ref.toString();
		}
		
		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}

	}
	
	@Override
	public Iterator<String> iterator()
	{
		return new CAIterator(this);
	}

	@Override
	public void initialize(int[] rule)
	{
		//Argument error checking
		if(rule.length != 8) throw new IllegalArgumentException("Rule must be exactly 8 digits.");
		for(int x = 0; x < rule.length; x++)
		{
			if(rule[x] != 0 && rule[x] != 1) throw new IllegalArgumentException("Digits must be 0 or 1.");
		}
		
		//Set the middle element(s) to 1
		m_state[(m_size + 1) / 2] = 1;
		if(m_size % 2 == 0) m_state[((m_size + 1) / 2) + 1] = 1;
		
		m_rule = rule;
		m_isInitialized = true;
	}

	@Override
	public void step()
	{
		//Throw exception if not yet initialized
		if(m_isInitialized == false) throw new IllegalStateException("CA has not been initialized.");
		
		//Execute the rule
		for(int x = 1; x < m_size + 1; x++)
		{
			m_modState[x] = m_rule[m_state[x-1]*4 + m_state[x]*2 + m_state[x+1]*1];
		}
		
		//Swap the new state in
		System.arraycopy(m_modState, 0, m_state, 0, m_size + 2);
	}
	
	@Override
	public String toString()
	{
		String string = "";
		
		for(int x = 1; x < m_size + 1; x++)
		{
			if(m_state[x] == 1) string += "1";
			else string += " ";
		}
		
		return string;
	}
}
