import java.util.ArrayList;

public class PieceSet {

	private static final int NO_OF_PIECES = 16;

	private ArrayList<Piece> available = null;
	private ArrayList<Piece> captured = null;
	private int color = 0;

	/**Initializes this piece set ( with NO pieces ) */
	public PieceSet() {
		this.available = new ArrayList<Piece>(PieceSet.NO_OF_PIECES); 
		this.captured = new ArrayList<Piece>(PieceSet.NO_OF_PIECES);
	}

	/**This set gets 16 pieces placed on the initial positions
	 * <br> according to the specified color 
	 * @param color PieceColor.WHITE or PieceColor.BLACK 
	 */
	public void setInitialPieces(int color) {
		int rowNumber = (color == PieceColor.WHITE) ? 1 : 8;
		this.addPiece(new King		(Board.translate(Letter.E, rowNumber), color));
		this.addPiece(new Rook		(Board.translate(Letter.A, rowNumber), color)); 	
		this.addPiece(new Rook		(Board.translate(Letter.H, rowNumber), color)); 
		this.addPiece(new Bishop	(Board.translate(Letter.C, rowNumber), color)); 
		this.addPiece(new Bishop	(Board.translate(Letter.F, rowNumber), color));
		this.addPiece(new Knight	(Board.translate(Letter.B, rowNumber), color)); 
		this.addPiece(new Knight	(Board.translate(Letter.G, rowNumber), color)); 
		this.addPiece(new Queen		(Board.translate(Letter.D, rowNumber), color));

		rowNumber = (color == PieceColor.WHITE) ? 2 : 7; 
		for (int letter = Letter.A; letter <= Letter.H; letter++)
			this.available.add(new Pawn(Board.translate(letter, rowNumber), color));
		this.setColor(color);
	}

	public void addPiece(Piece piece) {
		this.available.add(piece);
	}

	public ArrayList<Piece> getAvailablePieces() {
		return this.available;
	}

	public ArrayList<Piece> getCapturedPieces() {
		return this.captured;
	}

	public void capturePiece(Piece piece) {
		for(int i = 0; i < this.available.size(); i++)
			if(this.available.get(i).compareTo(piece) == 0) {
				this.available.get(i).getPosition().setPiece(null);
				this.captured.add(this.available.remove(i));
				break;
			}
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
}