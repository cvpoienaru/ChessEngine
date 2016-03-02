
public class Square implements Comparable<Square> {
	
	private int letter;
	private int number;
	private Piece piece;
	
	public Square(int letter, int number) {
		this.letter = letter;
		this.number = number;
		this.piece = null;
	}
	
	/** Returns this square's letter index on the table */
	public int getLetter() {
		return this.letter;
	}
	
	/** Returns this square's number index on the table */
	public int getNumber() {
		return this.number;
	}
	
	/** Returns the piece that sits on this square */
	public Piece getPiece() {
		return this.piece;
	}
	
	/** Sets a piece on this square */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public String getCoords() {
		return "" + ((char) (this.letter+96)) + this.number;
	}
	
	/** Returns a string interpretation of the piece on this square */
	public String toString() {
		if ( this.piece == null ) return "❤"; // ❤ â�¤
		
		// Return white pieces
		if ( this.piece.getColor() == PieceColor.WHITE) {
			 if(this.piece instanceof Pawn)		return "♙";
		else if(this.piece instanceof Bishop)	return "♗";
		else if(this.piece instanceof Knight)	return "♘";
		else if(this.piece instanceof Rook)		return "♖";
		else if(this.piece instanceof King)		return "♔";
		else if(this.piece instanceof Queen)	return "♕";
	}
	
	// Return black pieces
		 	  if( this.piece instanceof Pawn )	return "♟";
		 else if( this.piece instanceof Bishop )return "♝";
		 else if( this.piece instanceof Knight )return "♞";
		 else if( this.piece instanceof Rook )	return "♜";
		 else if( this.piece instanceof King )	return "♚";
		 else if( this.piece instanceof Queen )	return "♛";
		
		// Program never reaches this far.
		return "";
	}

	@Override
	public int compareTo(Square square) {
		if(square.getLetter() != this.getLetter())
			return square.getLetter() - this.getLetter();
		return square.getNumber() - this.getNumber();
	}
}