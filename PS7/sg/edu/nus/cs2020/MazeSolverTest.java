package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class MazeSolverTest {
	private final String _DIR_PREFIX = "mazes/";
	
	private int _makeTest(
			String mazeFile,
			int startRow,
			int startCol,
			int endRow,
			int endCol,
			int superpowers) {
		Integer shortestPath = null;
		Maze maze = null;
		
		try {
			maze = Maze.readMaze(_DIR_PREFIX + mazeFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			if(superpowers > 0) {
				MazeSolverWithPower solver = new MazeSolverWithPower();
				solver.initialize(maze);		
				shortestPath = solver.pathSearch(startCol, startCol, endRow, endCol, superpowers);
				//System.out.println("Run count: " + solver.getRunCount());
			}
			else {
				MazeSolver solver = new MazeSolver();
				solver.initialize(maze);
				shortestPath = solver.pathSearch(startRow, startCol, endRow, endCol);
				//System.out.println("Run count: " + solver.getRunCount());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MazePrinter.printMaze(maze);
		System.out.println("Shortest path: " + shortestPath);
		
		return shortestPath == null ? -1 : shortestPath;
	}
	
	@Test
	public void mazeTest() {
		assertEquals(10, _makeTest("maze-test.txt", 0, 0, 2, 2, 0));
	}
	
	@Test
	public void spiralTest() {
		assertEquals(20, _makeTest("maze-spiral.txt", 0, 0, 2, 2, 0));
	}
	
	@Test
	public void emptyTest() {
		assertEquals(4, _makeTest("maze-empty.txt", 0, 0, 2, 2, 0));
	}
	
	@Test
	public void exampleTest() {
		assertEquals(8, _makeTest("maze-example.txt", 0, 0, 4, 4, 0));
	}
	
	@Test
	public void myTest() {
		assertEquals(12, _makeTest("mymaze.txt", 0, 0, 7, 3, 0));
	}
	
	@Test
	public void superSpiralP1Test() {
		assertEquals(8, _makeTest("maze-spiral.txt", 0, 0, 2, 2, 1));
	}
	
	@Test
	public void superSpiralP2Test() {
		assertEquals(4, _makeTest("maze-spiral.txt", 0, 0, 2, 2, 2));
	}
	
	@Test
	public void stepsTest() {
		Maze maze;
		try {
			maze = Maze.readMaze(_DIR_PREFIX + "maze-example.txt");
			MazeSolver solver = new MazeSolver();
			
			solver.initialize(maze);
			solver.pathSearch(0, 0, 0, 0);
			
			MazePrinter.printMaze(maze);
			
			solver.printDistances();
			for(int x = 0; x < 10; x++) {
				System.out.println(x + " Step(s): " + solver.numReachable(x));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
