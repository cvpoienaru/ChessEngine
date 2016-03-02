import java.util.ArrayList;

public class Pawn extends Piece {
	
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

	public Pawn(Square position, int color) {
		super(position, color);
	}

	@Override
	public ArrayList<Square> getValidSquares() {
		ArrayList<Square> result = new ArrayList<Square>();
		int opponentColor = (this.getColor() == PieceColor.BLACK) ? PieceColor.WHITE : PieceColor.BLACK;
		int number = this.getPosition().getNumber();
		int letter = this.getPosition().getLetter();
		int howToMove1, howToMove2;
		Square intermediate;
		
		howToMove1 = (this.getColor() == PieceColor.WHITE) ? number + 1 : number - 1;
		for(int i = -1; i <= 1; i++) {
			if(Board.isSquareValid(letter + i, howToMove1)) {
				intermediate = Board.translate(letter + i, howToMove1);
				//Merg o patratica inainte
				if(i == 0) {
					if(intermediate.getPiece() == null)
						result.add(intermediate);
				}
				//Atac stanga - dreapta
				else {
					if(intermediate.getPiece() != null
							&& intermediate.getPiece().getColor() == opponentColor)
						result.add(intermediate);
				}
			}
		}
		
		//Daca nu a mai fost mutat si pot muta peste 2 patratele
		howToMove2 = (this.getColor() == PieceColor.WHITE) ? number + 2 : number - 2;
		if(!this.hasMoved() && Board.isSquareValid(letter, howToMove2)) {
			intermediate = Board.translate(letter, howToMove2);
			if(intermediate.getPiece() == null && result.contains(Board.translate(letter, howToMove1)))
				result.add(intermediate);
		}
		
//		Move move = Game.getHistory().peek();
//		Square square = this.getColor() == PieceColor.WHITE ? 
//				Board.translate(move.getStartSquare().getNumber() + 1, move.getStartSquare().getLetter()):
//					Board.translate(move.getStartSquare().getNumber() - 1, move.getStartSquare().getLetter());
//				
//		if(PieceType.getType(move.getEndSquare().getPiece()) == PieceType.PAWN &&
//				Math.abs(move.getStartSquare().getNumber() - move.getEndSquare().getNumber()) == 2)
//			result.add(square);
//		
		return result;
	}

}