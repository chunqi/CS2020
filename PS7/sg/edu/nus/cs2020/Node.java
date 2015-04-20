package sg.edu.nus.cs2020;

import java.util.ArrayList;

/**
 * Utility class to encapsulate the BFS frontier information into a single object
 * @author chunqi
 *
 */
public class Node {
	//Enumerations for using power
	public static final boolean USE_POWER = true;
	public static final boolean DONT_USE = false;
	
	//Row, col of the current room
	public RoomIndex roomIndex;
	
	//Path of rooms visited to get here
	public ArrayList<RoomIndex> path;
	
	//Number of times power is used
	private final int powerUsed;
	
	//Number of times power is allowed to be used
	private final int powerAllowed;
	
	public Node(RoomIndex roomIndex, ArrayList<RoomIndex> path, int powerUsed, int powerAllowed) {
		this.roomIndex = roomIndex;
		this.path = path;
		this.powerUsed = powerUsed;
		this.powerAllowed = powerAllowed;
	}
	
	/**
	 * Move in the direction indicated and try to use power
	 * @param dir Direction to try moving
	 * @param hasWall Is there a wall in that direction
	 * @param canUsePower Is power available AND it is not an edge wall
	 * @return New Node in the indicated direction, or null if no move possible (at edge or no power)
	 */
	private Node _moveTo(int dir, boolean hasWall, boolean canUsePower) {
		//Prepare the new Node
		RoomIndex newRoomIndex = roomIndex.move(dir);
		ArrayList<RoomIndex> newPath = new ArrayList<RoomIndex>(path);
		newPath.add(roomIndex);
		
		//Check if there is a wall to use power on
		if(hasWall && canUsePower) return new Node(newRoomIndex, newPath, powerUsed + 1, powerAllowed);
		//No wall we can just move into it
		else if(hasWall == false) return new Node(newRoomIndex, newPath, powerUsed, powerAllowed);
		//No move possible
		else return null;
	}
	
	/**
	 * Try all possible moves both with and without using power, WITH bounds checking
	 * @param maze Maze object
	 * @return List of all possible Nodes for BFS to explore
	 */
	public ArrayList<Node> moveAll(Maze maze) {
		ArrayList<Node> newNodes = new ArrayList<Node>();
		
		//Get current room reference for wall checking
		Room currRoom = maze.getRoom(roomIndex.row, roomIndex.col);
		
		//Is there still power to use?
		boolean canPower = powerUsed < powerAllowed ? true : false;
		
		//Is the direction movable?
		//If it is an edge then there is surely a wall
		boolean canNorth = roomIndex.row == 0 ? false : true;
		boolean canSouth = roomIndex.row == maze.getRows() - 1 ? false : true;
		boolean canEast = roomIndex.col == maze.getColumns() - 1 ? false : true;
		boolean canWest = roomIndex.col == 0 ? false : true;
		
		Node newNode;
		
		//Check north
		newNode = _moveTo(RoomIndex.NORTH, currRoom.hasNorthWall(), canPower && canNorth);
		if(newNode != null) newNodes.add(newNode);
		
		//Check south
		newNode = _moveTo(RoomIndex.SOUTH, currRoom.hasSouthWall(), canPower && canSouth);
		if(newNode != null) newNodes.add(newNode);
		
		//Check east
		newNode = _moveTo(RoomIndex.EAST, currRoom.hasEastWall(), canPower && canEast);
		if(newNode != null) newNodes.add(newNode);
		
		//Check west
		newNode = _moveTo(RoomIndex.WEST, currRoom.hasWestWall(), canPower && canWest);
		if(newNode != null) newNodes.add(newNode);
		
		return newNodes;
	}
}
