package knightstravails;

import java.util.ArrayList;

/**
 * A ChessBoard is a collection of ChessGrids as well as operations to manage
 * and run the BFS tree search with Nodes.
 * 
 * @author micnguyen
 * 
 */
public class ChessBoard
{
	private static ArrayList<Node> frontier = new ArrayList<Node>();
	private String endingLocation;
	// private ChessPiece selectedChessPiece;
	private static ChessGrid[][] chessGridArray;
	// Depth limit of BFS search to not go any further down paths of this depth
	// Since paths do not go back to any of their previous positions, deepest
	// limit is size of grid but a sufficiently large value is enough.
	private static final int BFS_DEPTH_LIMIT = 30;

	public ChessBoard(ChessPiece selectedChessPiece, String startingLocation, String endingLocation, int rowSize, int colSize)
	{
		this.endingLocation = endingLocation;

		// Construct the chess board with given dimensions
		chessGridArray = new ChessGrid[colSize][rowSize];

		// Generate each chess grid with the proper algebraic notation
		for (int i = 0; i < colSize; i++)
		{
			for (int j = 0; j < rowSize; j++)
			{
				char locationLetter = (char) ('A' + i);
				int locationNumber = 1 + j;
				// create the chess grid and place no chesspiece unit on it
				chessGridArray[i][j] = new ChessGrid(null, locationLetter, locationNumber);
			}
		}

		// Get the starting location's chessGrid object and placing the starting
		// chess piece on it. A little bit messy - can clean up, reason for it being messy:
		// a method that uses accepts multiple strings. As such, the chessGrid[]
		// will always have 1 entry.
		ArrayList<String> startingLocationArrayForm = new ArrayList<String>();
		startingLocationArrayForm.add(startingLocation);
		ChessGrid[] selectedChessGrid = getGridsBasedOffLocation(startingLocationArrayForm);
		selectedChessGrid[0].setChessPiece(selectedChessPiece);

		// Construct a Node object with the starting chess location, a depth of
		// 1 and no parents for this node.
		// and then begin the BFS tree search.
		Node startingNode = new Node(selectedChessGrid[0], 1, new ArrayList<Node>());
		bfsTreesearch(startingNode);
	}

	/**
	 * Breadth first depth limited search to find the endingLocation from the
	 * startingNode location. While the frontier (list of un-opened nodes) is
	 * not empty, pop off each node on that frontier, generate its children and
	 * add it to the frontier until the end goal is found or you hit the depth
	 * limit, or when a node cannot generate any more children
	 * 
	 * @param startingNode
	 *            The starting node with the starting chessGrid location
	 */
	private void bfsTreesearch(Node startingNode)
	{
		boolean foundGoal = false;
		Node finalNode = null; // the found final node (if not found, stays as
								// null)

		// Add the startingNode to the frontier (a list of unopened nodes)
		frontier.add(startingNode);

		while (!frontier.isEmpty())
		{
			if (knightstravails.DEBUG)
			{
				printCurrentFrontier();
			}

			// Pop off the node from the frontier
			Node chosenNodeFromFrontier = frontier.remove(0);

			if (knightstravails.DEBUG)
			{
				System.out.print("Popped off node: " + chosenNodeFromFrontier.getChessGrid().getFullLocation() + " from frontier. Current Path: ");
				chosenNodeFromFrontier.printTraversalFromParents(true);
				System.out.println("");
			}

			// if the currently expanded node's location matches the ending
			// location, set a flag, set the found node
			// and break the while loop
			if (chosenNodeFromFrontier.getChessGrid().getFullLocation().equalsIgnoreCase(endingLocation))
			{
				foundGoal = true;
				finalNode = chosenNodeFromFrontier;
				break;
			}

			// For that chosen node and it's respective chess piece, get a list
			// of all possible
			// locations that the piece can go to. This list of locations is
			// completely valid
			// (on board, does not contain previously visited locations)
			ArrayList<String> nextValidLocations = chosenNodeFromFrontier.generateValidPossibleLocations();

			if (knightstravails.DEBUG)
			{
				printPossiblePaths(chosenNodeFromFrontier, nextValidLocations);
			}

			// Get the specific ChessGrid objects of the list of valid locations
			ChessGrid[] nextValidLocations2 = getGridsBasedOffLocation(nextValidLocations);

			// If the expanded node is at the given depth limit, do not expand
			// it to find it's children.
			// Otherwise, with the list of valid locations, generate a children
			// node and add it to the frontier.
			if (!(chosenNodeFromFrontier.getNodeDepth() >= BFS_DEPTH_LIMIT))
			{
				// For all the valid locations list
				for (int i = 0; i < nextValidLocations2.length; i++)
				{
					// Create a node of the valid location with an increased
					// depth and a list of its parents (including the currently
					// expanded node itself)
					Node possiblePathsFromCurrentNode = new Node(nextValidLocations2[i], chosenNodeFromFrontier.getNodeDepth() + 1, chosenNodeFromFrontier.getParentNodesIncludingSelf());

					// Set the pieces on the new nodes' chess piece
					possiblePathsFromCurrentNode.getChessGrid().setChessPiece(chosenNodeFromFrontier.getChessGrid().getOccupyingChessPiece());

					// Add the new node to the frontier at the end
					frontier.add(frontier.size(), possiblePathsFromCurrentNode);
				}
			}
		}

		// If the goal is found, print out all the necessary information by
		// tracking back the node's list of ancestors
		if (foundGoal)
		{
			if (knightstravails.DEBUG)
			{
				System.out.println("Ended on desired ending location: " + finalNode.getChessGrid().getFullLocation() + " with depth: " + finalNode.getNodeDepth());
			}

			System.out.print("Traversal path (ex. starting location): ");
			finalNode.printTraversalFromParents(false);
		}
		else
		{
			System.out.println("No path could be found or none within the depth limit of " + BFS_DEPTH_LIMIT + ". Exitting... ");
		}

	}

	/**
	 * Print to stdout the possible paths a node can take
	 * 
	 * @param chosenNodeFromFrontier
	 *            The current node
	 * @param nextValidLocations
	 *            The next locations the node's chess piece can go to
	 */
	private void printPossiblePaths(Node chosenNodeFromFrontier, ArrayList<String> nextValidLocations)
	{
		System.out.print("Node: " + chosenNodeFromFrontier.getChessGrid().getFullLocation() + " has generated possible paths: ");
		for (int i = 0; i < nextValidLocations.size(); i++)
		{
			System.out.print(nextValidLocations.get(i) + ", ");
		}
		System.out.println("");
	}

	/**
	 * Method to specifically return a ChessGrid array that contains specific
	 * references to the same ChessGrid objects with the same location on the
	 * ChessBoard. ie: any references to B5 will reference the same B5
	 * ChessGrid.
	 * 
	 * @param locations
	 *            : An ArrayList containing valid positions on the board
	 * @return An array of ChessGrid containing references to valid ChessGrid
	 *         positions on the board.
	 */
	private static ChessGrid[] getGridsBasedOffLocation(ArrayList<String> locations)
	{
		ChessGrid[] foundChessGridsMatchingNewLocations = new ChessGrid[locations.size()];

		// For every entry in the locations ArrayList<String>(which are all
		// valid positions)
		// find the corresponding ChessGrid object on the ChessBoard
		for (int i = 0; i < locations.size(); i++)
		{
			char letterLocation = locations.get(i).charAt(0);
			int numberLocation = Integer.parseInt("" + locations.get(i).substring(1));

			foundChessGridsMatchingNewLocations[i] = chessGridArray[letterLocation - 65][numberLocation - 1];
		}

		return foundChessGridsMatchingNewLocations;
	}

	/**
	 * Print the current frontier of nodes/chess grid locations to stdout
	 */
	private void printCurrentFrontier()
	{
		System.out.print("Current Frontier: ");
		for (int i = 0; i < frontier.size(); i++)
		{
			System.out.print(frontier.get(i).getChessGrid().getFullLocation() + ", ");
		}
		System.out.println("");
	}

}
