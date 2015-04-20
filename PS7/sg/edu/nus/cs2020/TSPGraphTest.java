package sg.edu.nus.cs2020;

import static org.junit.Assert.*;

import org.junit.Test;

public class TSPGraphTest {
	@Test
	/**
	 * Infinite loop at the end to retain graph
	 */
	public void drawTest() {
		// Open a new map
		TSPMap map = new TSPMap("mst_data/funnypoints.txt");
		
		// Set up the links in order
		for (int i=0; i<map.getCount()-1; i++){
			map.setLink(i, i+1, false);
			
			//Sleep for 250ms to visualize graph drawing
			/*try {
				Thread.sleep(250);
			} catch(InterruptedException e) {
			    Thread.currentThread().interrupt();
			}*/
		}
		map.setLink(map.getCount()-1, 0);
		
		while(true);
	}
	
	@Test
	public void emptyIsValidTest() {
		TSPMap map = new TSPMap("mst_data/hundredpoints.txt");
		TSPGraph tspGraph = new TSPGraph();
		tspGraph.initialize(map);
		
		assertEquals(false, tspGraph.isValidTour());
	}
	
	@Test
	public void smallCycleIsValidTest() {
		TSPMap map = new TSPMap("mst_data/hundredpoints.txt");
		map.setLink(0, 1);
		map.setLink(1, 2);
		map.setLink(2, 3);
		map.setLink(3, 0);
		map.redraw();
		
		TSPGraph tspGraph = new TSPGraph();
		tspGraph.initialize(map);
		
		assertEquals(false, tspGraph.isValidTour());
	}
	
	@Test
	public void fullTourIsValidTest() {
		TSPMap map = new TSPMap("mst_data/hundredpoints.txt");
		for(int x = 0; x < map.getCount() - 1; x++) {
			map.setLink(x, x + 1, false);
		}
		map.setLink(map.getCount() - 1, 0, false);
		map.redraw();
		
		TSPGraph tspGraph = new TSPGraph();
		tspGraph.initialize(map);
		
		assertEquals(true, tspGraph.isValidTour());
	}
	
	@Test
	public void hundredPointsMSTTest() {
		TSPMap map = new TSPMap("mst_data/funnypoints.txt");
		
		TSPGraph tspGraph = new TSPGraph();
		tspGraph.initialize(map);
		
		tspGraph.MST();
	}
	
	@Test
	public void hundredPointsTSPTest() {
		TSPMap map = new TSPMap("mst_data/hundredpoints.txt");
		
		TSPGraph tspGraph = new TSPGraph();
		tspGraph.initialize(map);
		
		tspGraph.MST();
		tspGraph.TSP();
		
		assertEquals(true, tspGraph.isValidTour());
		System.out.println("Tour distance: " + tspGraph.tourDistance());
	}
}
