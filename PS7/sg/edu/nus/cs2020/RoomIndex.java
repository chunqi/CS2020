package sg.edu.nus.cs2020;

/**
 * Utility class to encapsulate the row,col-indices of a room
 * and includes functions to move to another room with enumerated directions
 * @author chunqi
 *
 */
public class RoomIndex {
	//Enumerated directions
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;
	public static final int WEST = 4;
	
	//Public visibility for easier referencing
	public int row;
	public int col;
	
	public RoomIndex(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Move to another room in the given direction, 
	 * NOT bounds-checked
	 * @param dir Enumerated direction to move in
	 * @return New destination RoomIndex object
	 */
	public RoomIndex move(int dir) {
		int newRow = row;
		int newCol = col;
		
		if(dir == NORTH) newRow--;
		else if(dir == SOUTH) newRow++;
		else if(dir == EAST) newCol++;
		else if(dir == WEST) newCol--;
		
		return new RoomIndex(newRow, newCol);
	}
}