package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import sg.edu.nus.cs2020.TSPMap.Point;

public class TSPGraph implements IApproximateTSP {
	//Draw the MST ourselves
	private final boolean DEBUG = true;
	
	private int m_numCities;
	private ArrayList<Edge> m_MSTEdges;
	private HashMap<Integer, Boolean> m_inMST;
	private TSPMap m_map;
	private PriorityQueue<Edge> m_pq;
	
	/**
	 * Class to encapsulate information of edges.
	 * Implements comparable to be used in a PQ.
	 * @author chunqi
	 *
	 */
	private class Edge implements Comparable<Edge> {
		//Edge weight
		public double w;
		
		//Edge connects a to b
		public int a;
		public int b;
		
		public Edge(int a, int b, double w) {
			this.a = a;
			this.b = b;
			this.w = w;
		}
		
		/**
		 * Checks if this edge connects to the given city
		 * @param city
		 * @return
		 */
		public boolean connects(int city) {
			if(a == city || b == city) return true;
			else return false;
		}
		
		/**
		 * Gives the other city that is connected by this edge
		 * @param fromCity
		 * @return
		 */
		public int connectedTo(int fromCity) {
			if(fromCity == a) return b;
			else if(fromCity == b) return a;
			else throw new IllegalArgumentException("Edge does not connect to this city");
		}
		
		@Override
		public int compareTo(Edge edge) {
			return Double.compare(w, edge.w);
		}
	}
	
	@Override
	public void initialize(TSPMap map) {
		m_numCities = map.getCount();
		m_MSTEdges = new ArrayList<Edge>();
		m_inMST = new HashMap<Integer, Boolean>(2 * m_numCities);
		m_map = map;
		m_pq = new PriorityQueue<Edge>(2 * m_numCities * m_numCities);
	}
	
	@Override
	public void MST() {
		/**
		 * Using Prim's algorithm
		 */
		
		//Draw the visualization ourselves
		ArrayList<Point> points = new ArrayList<Point>();
		if(DEBUG) {
			StdDraw.setCanvasSize(500, 500);
			StdDraw.setScale(0, 200);
			StdDraw.clear();
			
			//Get all points
			for(int x = 0; x < m_numCities; x++) points.add(m_map.getPoint(x));
			
			//Draw city 0 with green
			StdDraw.setPenColor(StdDraw.GREEN);
			StdDraw.circle(points.get(0).getX(), points.get(0).getY(), 0.5);
			
			//Draw all other points with blue
			StdDraw.setPenColor(StdDraw.BLUE);
			for (int x = 1; x < points.size(); x++) {
				StdDraw.circle(points.get(x).getX(), points.get(x).getY(), 0.5);
			}
			
			//Set to red for edge drawing
			StdDraw.setPenColor(StdDraw.RED);
		}
		
		//Add all edges connecting city 0 to the PQ
		for(int x = 0; x < m_numCities; x++) {
			m_pq.add(new Edge(0, x, m_map.pointDistance(0, x)));
		}
		
		//Start from city 0
		m_inMST.put(0, true);
		
		//Build the MST
		while(m_inMST.size() < m_numCities) {
			//Get the lowest weight edge
			Edge edge = m_pq.poll();
			
			//Check edge does not connect 2 cities already in MST
			if(m_inMST.containsKey(edge.a) && m_inMST.containsKey(edge.b)) {
				continue;
			}
			else {
				int newCity = m_inMST.containsKey(edge.a) ? edge.b : edge.a;
				int MSTCity = newCity == edge.a ? edge.b : edge.a;
				
				//Add the edge to the MST
				m_map.setLink(newCity, MSTCity, !DEBUG);
				m_MSTEdges.add(edge);
				m_inMST.put(newCity, true);
				
				//Add all edges of the newly added city to PQ
				//NOTE: Starts from 1 as 0 is our starting city and is always included in MST
				for(int x = 1; x < m_numCities; x++) {
					//Do not add if connecting a city already in the MST
					if(x == newCity || m_inMST.containsKey(x)) {
						continue;
					}
					else {
						m_pq.add(new Edge(newCity, x, m_map.pointDistance(newCity, x)));
					}
				}
				
				if(DEBUG) {
					//Draw the newly added edge
					StdDraw.line(
							points.get(edge.a).getX(),
							points.get(edge.a).getY(),
							points.get(edge.b).getX(),
							points.get(edge.b).getY());
				}
				
				//Sleep for 250ms to visualize MST
				try {
					Thread.sleep(250);
				} catch(InterruptedException e) {
				    Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	@Override
	public void TSP() {
		ArrayList<Integer> visitSequence = new ArrayList<Integer>();
		
		//Recursive pre-order DFS
		_visit(0, visitSequence);
		
		//Tour ends back at city 0
		visitSequence.add(0);
		
		//Set the links in the map
		for(int x = 0; x < visitSequence.size() - 1; x++) {
			m_map.setLink(visitSequence.get(x), visitSequence.get(x + 1));
			
			//Sleep for 250ms to visualize tour
			try {
				Thread.sleep(250);
			} catch(InterruptedException e) {
			    Thread.currentThread().interrupt();
			}
		}
	}
	
	/**
	 * Recursive pre-order DFS
	 * @param city
	 * @param visitSequence
	 */
	private void _visit(int city, ArrayList<Integer> visitSequence) {
		//Add this city to visit sequence
		visitSequence.add(city);
		
		//Find all edges connecting this city
		for(int x = 0; x < m_MSTEdges.size(); x++) {
			Edge edge = m_MSTEdges.get(x);
			int fromCity = m_map.getLink(city);
			
			//Don't traverse back up the tree
			if(edge.connects(city) && !edge.connects(fromCity)) {
				_visit(edge.connectedTo(city), visitSequence);
			}
		}
	}
	
	@Override
	public boolean isValidTour() {
		//Keep track of visited cities
		boolean[] visited = new boolean[m_numCities];
		int visitCount = 0;
		
		//Start traversal at 0
		int next = 0;
		while(true) {
			//Get the next city
			next = m_map.getLink(next);
			
			//Valid tour if next city is city 0 again and we have visited count - 1 other cities
			if(next == 0 && visitCount == m_numCities - 1) return true;
			//Unconnected city or revisiting a city
			else if(next == -1 || visited[next]) return false;
			//Add city to visited cities
			else {
				visited[next] = true;
				visitCount++;
			}
		}
	}
	
	@Override
	public double tourDistance() {
		//Check if it is a valid tour
		if(isValidTour()) {
			double distance = 0;
			int prevCity = 0;
			
			//E = V in a valid tour
			for(int x = 0; x < m_numCities; x++) {
				int nextCity = m_map.getLink(prevCity);
				distance += m_map.pointDistance(prevCity, nextCity);
				prevCity = nextCity;
			}
			
			return distance;
		}
		else return -1;
	}
}
