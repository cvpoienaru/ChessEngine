import java.util.ArrayList;

public class King extends Piece {
	
	private int moved;

	public boolean hasMoved() {
		return !(moved == 0);
	}

	public void makeMove() {
		this.moved ++;
	}
	
	public void undoMove() {
		this.moved --;
	}

	public King(Square position, int color) {
		super(position, color);
	}

	@Override
	public ArrayList<Square> getValidSquares() {
		ArrayList<Square> result = new ArrayList<Square>();
		int opponentColor = (this.getColor() == PieceColor.BLACK) ? PieceColor.WHITE : PieceColor.BLACK;
		int row = this.getPosition().getNumber();
		int column = this.getPosition().getLetter();
		Square intermediate;
		
		for( int i = -1; i <= 1; i++)
			for( int j = -1; j <= 1; j++)
				if(!(i == 0 && j == 0)) {
					if(Board.isSquareValid(column + i, row + j)){
						intermediate = Board.translate(column + i, row + j);
						if(intermediate.getPiece() == null || intermediate.getPiece().getColor() == opponentColor)
							result.add(intermediate);
					}	
				}
		
		if ( isKingsideCastlingPossible()  ) result.add(Board.translate(column + 2, row));
		if ( isQueensideCastlingPossible() ) result.add(Board.translate(column - 2, row));
		
		return result;
	}

	/*If both 2 squares to the right are clear and the rook on the third hasn't moved,
	 * castling is possible */
	private boolean isKingsideCastlingPossible() {
	//Checking if:
		// The king hasn't moved
		if ( this.hasMoved() == true ) return false;
		// The king isn't checked
		if ( Piece.canBeCaptured(this, this.getPosition(), this.getColor() == PieceColor.BLACK ?
						PieceColor.WHITE : PieceColor.BLACK) != null )
			return false;
		
		Square sq;
		// None of the squares from the king to destination are attackable
		//    nor do they have pieces on
		for ( int i = Letter.F ; i < Letter.H ; i++ ) {
			sq = Board.translate(i, this.getPosition().getNumber());
			if ( sq.getPiece() != null ) return false;
			if ( Piece.canBeCaptured(null, sq, this.getColor() == PieceColor.BLACK ?
					PieceColor.WHITE : PieceColor.BLACK ) != null )
				return false;
		}
		sq = Board.translate( Letter.H, this.getPosition().getNumber());
		// 4th square to the left has a rook that hasn't moved on it
		return (PieceType.getType( sq.getPiece()) == PieceType.ROOK)
				&& ((Rook) sq.getPiece()).hasMoved() == false;
	}
	
	private boolean isQueensideCastlingPossible() {
		
	//Checking if:
		// The king hasn't moved
		if ( this.hasMoved() == true ) return false;
		// The king isn't checked
		if ( Piece.canBeCaptured(this, this.getPosition(), this.getColor() == PieceColor.BLACK ?
						PieceColor.WHITE : PieceColor.BLACK) != null )
			return false;
		
		Square sq;
		// None of the squares from the king to destination are attackable
		//    nor do they have pieces on
		for ( int i = Letter.D ; i > Letter.B ; i-- ) {
			sq = Board.translate(i, this.getPosition().getNumber());
			if ( sq.getPiece() != null ) return false;
			if ( Piece.canBeCaptured(null, sq, this.getColor() == PieceColor.BLACK ?
					PieceColor.WHITE : PieceColor.BLACK ) != null )
				return false;
		}
		sq = Board.translate( Letter.A, this.getPosition().getNumber());
		// 4th square to the left has a rook that hasn't moved on it
		return (PieceType.getType( sq.getPiece()) == PieceType.ROOK)
				&& ((Rook) sq.getPiece()).hasMoved() == false;
	}
}