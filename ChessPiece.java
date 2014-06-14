package knightstravails;

/**
 * A chess piece for use in the knightstravails program. 
 * User configurable pieces with editable movement matrices.
 * @author micnguyen
 * 
 */
public class ChessPiece
{
	private char pieceType;
	private int[][] rowColMovement;

	public ChessPiece(char chessPieceType)
	{
		pieceType = chessPieceType;
		setMovementOnType(pieceType);
	}

	/**
	 * Applies a movement matrix depending on the piece type. It is of the format [row][col] setting out the
	 * available positions a chess can travel to from it's current location.
	 * @param chessPieceName The abbreviation for the selected piece type
	 */
	private void setMovementOnType(char chessPieceName)
	{
		switch (chessPieceName)
		{
		case 'K':
			break;
		case 'Q':
			break;
		case 'R':
			break;
		case 'B':
			break;
		case 'N':
			rowColMovement = new int[8][2];
			rowColMovement[0] = new int[] { 2, -1 };
			rowColMovement[1] = new int[] { 2, 1 };
			rowColMovement[2] = new int[] { -2, -1 };
			rowColMovement[3] = new int[] { -2, 1 };
			rowColMovement[4] = new int[] { 1, -2 };
			rowColMovement[5] = new int[] { 1, 2 };
			rowColMovement[6] = new int[] { -1, -2 };
			rowColMovement[7] = new int[] { -1, 2 };
			break;
		case 'P':
			break;
		}
	}

	public char getPieceType()
	{
		return pieceType;
	}

	public int[][] getRowColMovement()
	{
		return rowColMovement;
	}

}
