package sg.edu.nus.cs2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class MazeSolverWithPower extends MazeSolver implements IMazeSolverWithPower {
	@Override
	public Integer pathSearch(
			int startRow,
			int startCol,
			int endRow,
			int endCol,
			int superpowers) throws Exception {
		/**
		 * Code is essentially the same as MazeSolver (without power) as the
		 * support classes all support BFS frontier with or without super powers
		 */
		
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
		m_queue.add(new Node(m_start, new ArrayList<RoomIndex>(), 0, superpowers));
		
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
}
