package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	protected Maze m_maze;
	//Distance to each room, Integer.MAX_VALUE for unexplored rooms
	protected int m_dist[][];
	protected Queue<Node> m_queue;
	protected int m_runCount;
	protected RoomIndex m_start;
	protected RoomIndex m_end;
	
	@Override
	public void initialize(Maze maze) {
		this.m_maze = maze;
	}
	
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if(this.m_maze == null) {
			throw new Exception("Error: MazeSolver has not been initialized");
		}
		
		//Check if the start and end coordinates are within the maze
		if(startRow < 0 || startCol < 0
			|| endRow < 0 || endCol < 0
			|| startRow >= m_maze.getRows() || startCol >= m_maze.getColumns()
			|| endRow >= m_maze.getRows() || endCol >= m_maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		
		//Record the start and end locations
		m_start = new RoomIndex(startRow, startCol);
		m_end = new RoomIndex(endRow, endCol);
		
		//For debugging
		m_runCount = 0;
		
		//Reset distance to each room to Integer.MAX_VALUE
		m_dist = new int[m_maze.getRows()][m_maze.getColumns()];
		for(int x = 0; x < m_dist.length; x++) {
			Arrays.fill(m_dist[x], Integer.MAX_VALUE);
		}
		
		//Reset the queue
		m_queue = new LinkedList<Node>();
		
		//Add starting room into queue
		m_queue.add(new Node(m_start, new ArrayList<RoomIndex>(), 0, 0));
		
		//We keep the result and let BFS run fully to get the steps to all reachable rooms
		Integer shortestPath = null;
		
		while(m_queue.size() > 0) {
			//Get the first element from the queue
			Node node = m_queue.poll();
			
			//Skip if we have already visited this room
			int dist = m_dist[node.roomIndex.row][node.roomIndex.col];
			if(dist == Integer.MAX_VALUE) {
				m_dist[node.roomIndex.row][node.roomIndex.col] = node.path.size() - 1;
			} else {
				continue;
			}
			
			//For debugging
			m_runCount++;
			
			//We have found the end point
			if(node.roomIndex.row == endRow && node.roomIndex.col == endCol) {
				//Mark the path that was taken by this node
				_markPath(node.path);
				
				//Store the length of the path to the end room
				shortestPath = node.path.size();
			}
			
			//Add all possible moves into the BFS queue
			m_queue.addAll(node.moveAll(m_maze));
		}
		
		return shortestPath;
	}
	
	public int getRunCount() {return m_runCount;}
	
	/**
	 * Utility function to mark the rooms that are on the shortest path
	 * @param path The shortest path
	 */
	protected void _markPath(ArrayList<RoomIndex> path) {
		//Mark all rooms on the path
		for(int x = 0; x < path.size(); x++) {
			RoomIndex roomIndex = path.get(x);
			m_maze.getRoom(roomIndex.row, roomIndex.col).onPath = true;
		}
		
		//Mark the end room
		m_maze.getRoom(m_end.row, m_end.col).onPath = true;
	}
	
	/**
	 * Utility function to print the distance of all rooms from start room
	 */
	public void printDistances() {
		for(int x = 0; x < m_maze.getRows(); x++) {
			for(int y = 0; y < m_maze.getColumns(); y++) {
				System.out.print(m_dist[x][y] + 1 + "\t");
			}
			System.out.println();
		}
	}
	
	@Override
	/**
	 * Checks through all distances of rooms and counts those that are k-hops away
	 * @param k
	 * @return
	 * @throws Exception
	 */
	public Integer numReachable(int k) throws Exception {
		int count = 0;
		for(int x = 0; x < m_maze.getRows(); x++) {
			for(int y = 0; y < m_maze.getColumns(); y++) {
				if(m_dist[x][y] == k - 1) count++;
			}
		}
		
		return count;
	}
}