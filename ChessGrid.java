package knightstravails;

/**
 * To represent a chess grid in the knightstravails program. A chessGrid piece
 * contains a location in algebraic notation and an occupying chess piece.
 * 
 * @author micnguyen
 * 
 */
public class ChessGrid
{
	private char locationLetter;
	private int locationNumber;
	private ChessPiece occupyingChessPiece;

	public ChessGrid(ChessPiece chessPiece, char locationLetter, int locationNumber)
	{
		this.occupyingChessPiece = chessPiece;
		this.locationLetter = locationLetter;
		this.locationNumber = locationNumber;
	}

	/**
	 * Set a ChessPiece on this chessGrid
	 * @param selectedChessPiece a selected ChessPiece
	 */
	public void setChessPiece(ChessPiece selectedChessPiece)
	{
		this.occupyingChessPiece = selectedChessPiece;
	}

	public ChessPiece getOccupyingChessPiece()
	{
		return this.occupyingChessPiece;
	}

	public String getFullLocation()
	{
		return locationLetter + "" + locationNumber;
	}

	public char getLocationLetter()
	{
		return locationLetter;
	}

	public int getLocationNumber()
	{
		return locationNumber;
	}

}
