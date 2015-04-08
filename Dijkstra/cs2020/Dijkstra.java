package cs2020;

public class Dijkstra
{
	private String[] m_countries = {
		"Singapore",
		"Rome",
		"Paris",
		"New York",
		"Milan",
		"London",
		"Berlin",
		"Tokyo",
		"Kalamazoo"
	};
	
	private int[][] m_adjacency;
	private int[] m_minDist;
	private int m_start;
	private int m_dest;
	
	private boolean[] m_coupon;
	private boolean[] m_visited;
	
	private int getCountryIndex(String country)
	{
		for(int x = 0; x < m_countries.length; x++)
		{
			if(m_countries[x].equals(country)) return x;
		}
		return -1;
	}
	
	private void addEdge(String countryA, String countryB, int cost)
	{
		m_adjacency[getCountryIndex(countryA)][getCountryIndex(countryB)] = cost;
	}
	
	private int getMinVertex()
	{
		int minIndex = -1;
		int minDist = Integer.MAX_VALUE;
		
		for(int x = 0; x < m_countries.length; x++)
		{
			if(m_visited[x] == false && m_minDist[x] < minDist)
			{
				minDist = m_minDist[x];
				minIndex = x;
			}
		}
		
		return minIndex;
	}
	
	public Dijkstra()
	{
		//TODO: Change these to for different start / end points on the graph
		m_start = getCountryIndex("Singapore");
		m_dest = getCountryIndex("Kalamazoo");
		
		//Initialize variables
		m_minDist = new int[m_countries.length];
		m_visited = new boolean[m_countries.length];
		m_coupon = new boolean[m_countries.length];
		m_adjacency = new int[m_countries.length][m_countries.length];
		for(int x = 0; x < m_minDist.length; x++)
		{
			if(x == m_start) m_minDist[x] = 0;
			else m_minDist[x] = Integer.MAX_VALUE;
		}
		
		//Add the adjacency rules and cost
		addEdge("London", "Paris", 200);
		addEdge("London", "Rome", 40);
		addEdge("London", "New York", 200);
		addEdge("Milan", "London", 200);
		addEdge("New York", "Kalamazoo", 200);
		addEdge("Paris", "London", 120);
		addEdge("Paris", "Berlin", 70);
		addEdge("Paris", "Tokyo", 700);
		addEdge("Rome", "Milan", 150);
		addEdge("Singapore", "Paris", 500);
		addEdge("Singapore", "Rome", 200);
		addEdge("Singapore", "New York", 250);
		
		//Run Dijkstra's
		run();
	}
	
	private void run()
	{	
		for(int x = 0; x < m_countries.length; x++)
		{
			int currVertex = getMinVertex();
			
			//Stop node expansion if we reach the destination
			if(currVertex == m_dest) break;
			
			for(int adjVertex = 0; adjVertex < m_countries.length; adjVertex++)
			{
				boolean usedCoupon = false;
				int cost = m_adjacency[currVertex][adjVertex];
				
				if(cost > 0)
				{
					//Discount immediately eligible edge if coupon not yet used on current path
					if(cost >= 250 && m_coupon[currVertex] == false)
					{
						usedCoupon = true;
						cost -= 200;
					}
					
					int calcDist = m_minDist[currVertex] + cost;
					
					if(calcDist < m_minDist[adjVertex])
					{
						m_minDist[adjVertex] = calcDist;
						m_coupon[adjVertex] = usedCoupon;
					}
				}
			}
			
			m_visited[currVertex] = true;
		}
		
		System.out.println("Minimum cost to " + m_countries[m_dest] + ": " + m_minDist[m_dest]);
	}
	
	public static void main(String args[])
	{
		Dijkstra dj = new Dijkstra();
	}
}
