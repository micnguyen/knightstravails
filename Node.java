package knightstravails;

import java.util.ArrayList;

/**
 * Node class to represent a node used to construct a tree. Contains a link to chessgrid (also cotnaining chesspiece)
 * and links to previous parent nodes.
 * @author micnguyen
 *
 */
public class Node
{
	private ChessGrid chessGrid;
	private int depth;
	private ArrayList<Node> listOfParentNodes;

	public Node(ChessGrid chessGrid, int depth, ArrayList<Node> parentNodes)
	{
		this.chessGrid = chessGrid;
		this.depth = depth;
		this.listOfParentNodes = parentNodes;
	}

	// Returns the respective chessGrid in this Node.
	public ChessGrid getChessGrid()
	{
		return chessGrid;
	}

	/**
	 * Method to generate all the next valid possible moves this chess piece on this chess grid can move to.
	 * Does so by looking at the chess piece's movement matrix, generates all locations from that, and removes
	 * invalid locatiosn (outside of board, previously been to locations)
	 * @return An ArrayList of strings containing each valid possible location to go to
	 */
	public ArrayList<String> generateValidPossibleLocations()
	{
		// Get the specific location components for processing later
		char locationLetter = chessGrid.getLocationLetter();
		int locationNumber = chessGrid.getLocationNumber();

		// The allowable movement axis of the specific piece type on this location
		//System.out.println(locationLetter + " " + locationNumber);
		int[][] movementAxis = chessGrid.getOccupyingChessPiece().getRowColMovement();

		// An ArrayList of the valid location
		ArrayList<String> possibleNewLocations = new ArrayList<String>();

		// For all of the possible movements that piece can make, apply it to
		// the current location and see if the new location is valid (on the board)
		for (int i = 0; i < movementAxis.length; i++)
		{
			// Grab the corresponding movement adjustment from the piece
			int rowIncrement = movementAxis[i][0];
			int colIncrement = movementAxis[i][1];

			// Modify the 'startingLocation' with the movement axis adjustments
			// of the piece
			char newLetterLocation = (char) (locationLetter + colIncrement);
			int newNumberLocation = locationNumber + rowIncrement;
			String locationNew = newLetterLocation + "" + newNumberLocation;

			// Check if the generated grid location is valid
			// If it is valid, add it to our string array of valid grid
			// locations
			if (knightstravails.validLocation(locationNew))
			{
				//System.out.println("Made this: " + locationNew);
				possibleNewLocations.add(locationNew);
			}
		}

		// For each of the possible valid locations, look through the parent nodes (previously been to locations)
		// and remove them from the allowable locations to prevent loops.
		for(int i = 0; i < listOfParentNodes.size(); i++)
		{
			for(int j = 0; j < possibleNewLocations.size(); j++)
			{
				if(listOfParentNodes.get(i).getChessGrid().getFullLocation().equalsIgnoreCase(possibleNewLocations.get(j)))
				{
					possibleNewLocations.remove(j);
				}
			}
		}

		return possibleNewLocations;
	}

	public int getNodeDepth()
	{
		return this.depth;
	}

	/**
	 * Return the list of parent nodes but also appends itself to the list.
	 * Used primarily to construct future nodes that have this node as its parent
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Node> getParentNodesIncludingSelf()
	{
		ArrayList<Node> parentsIncSelf = (ArrayList<Node>) listOfParentNodes.clone();
		parentsIncSelf.add(this);
		return parentsIncSelf;
	}

	/**
	 * Print out to stdout the traversal from root to current path.
	 * @param includeStart boolean to skip the root node if desired
	 */
	public void printTraversalFromParents(boolean includeStart)
	{
		int counterToSkipStart = 1;
		if(includeStart)
		{
			counterToSkipStart = 0;
		}

		for (int i = counterToSkipStart; i < listOfParentNodes.size(); i++)
		{
			System.out.print(listOfParentNodes.get(i).getChessGrid().getFullLocation() + ", ");
		}
		System.out.print(chessGrid.getFullLocation() + "\n");
	}

}
