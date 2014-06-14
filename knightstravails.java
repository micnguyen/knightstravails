package knightstravails;

/**
 * KnightsTravails program to find shortest path between 2 chess locations given
 * in algebraic notation. Implements breadth first depth limited search using
 * Node class to find shortest path.
 * 
 * @author micnguyen
 */
public class knightstravails
{
	// Chess Grid size
	static int CHESSGRID_ROW_SIZE;
	static int CHESSGRID_COL_SIZE;

	public static boolean DEBUG;

	public static void main(String args[])
	{
		printArguments(args);

		try
		{
			if(!validInput(args))
			{
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			System.out.println("Invalid input. Consult instructions. Exitting...");
			return;
		}

		String startingLocation = args[2];
		String endingLocation = args[3];
		System.out.println("Starting location: " + startingLocation + ". Ending Location: " + endingLocation + ".");
		
		// Select the Knight (N) as the selected piece to play with
		ChessPiece selectedChessPiece = new ChessPiece('N');
		new ChessBoard(selectedChessPiece, startingLocation, endingLocation, CHESSGRID_ROW_SIZE, CHESSGRID_COL_SIZE);
		
	}

	/**
	 * Validate the user input. Can only have 2 locations that are valid (on the board).
	 * and must contain all information
	 * @param args The user input
	 * @return boolean if user input is valid
	 */
	private static boolean validInput(String args[])
	{
		CHESSGRID_ROW_SIZE = Integer.parseInt(args[0]);
		CHESSGRID_COL_SIZE = Integer.parseInt(args[1]);
		
		if (args.length == 5 && validLocation(args[2]) && validLocation(args[3]))
		{
			if(Integer.parseInt(args[4]) == 1)
			{
				DEBUG = true;
			}
			else
			{
				DEBUG = false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Print user input to stdout
	 * @param args the user input
	 */
	private static void printArguments(String args[])
	{
		System.out.print("Input: ");

		for (int i = 0; i < args.length; i++)
		{
			System.out.print(args[i] + " ");
		}

		System.out.print("\n");
	}

	/**
	 * Determine if a given a chess location given in algebraic notation is a valid location.
	 * The location must be on the board (determined by the grid size) for it to be deemed as valid.
	 * Note: Only works for single digit letter combinations. Z is therefore the highest dimensions.
	 * @param location a chess location in algebraic notation
	 * @return boolean if the location is valid
	 */
	public static boolean validLocation(String location)
	{
		char letterLocation = location.charAt(0);
		int numberLocation = Integer.parseInt(location.substring(1));

		// Checking if the letter component is within range based off grid size.
		// 65 is ASCII DEC value of 'A'.
		if (letterLocation >= 65 && letterLocation <= (65 + CHESSGRID_COL_SIZE - 1))
		{
			// Checking if the number component is within range based off grid
			// size. 49 is ASCII DEC value of '1'.
			if (numberLocation >= 1 && numberLocation <= CHESSGRID_ROW_SIZE)
			{
				return true;
			}
		}

		// If it reaches here, location is invalid
		return false;
	}

}
