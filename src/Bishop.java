import java.util.ArrayList;

public class Bishop extends Piece {

	public Bishop(Square position, int color) {
		super(position, color);
	}

	@Override
	public ArrayList<Square> getValidSquares() {
		int row = this.getPosition().getNumber();
		int column = this.getPosition().getLetter();
		int oponentColor = (this.getColor() == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE; 
		Square intermediate;
		ArrayList<Square> result = new ArrayList<Square>();
		
		/*
		 * Checks availability diagonally left-down
		 */
		
		for(int i=column, j=row; i>Letter.A && j>1; i--, j--) {
			intermediate = Board.translate(i-1, j-1);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor){
				result.add(intermediate);
				break;
			} else
				break;
		}
		
		/*
		 * Checks availability diagonally left-up;
		 */
		
		for(int i=column, j=row; i>Letter.A && j<8; i--, j++) {
			intermediate = Board.translate(i-1, j+1);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor){
				result.add(intermediate);
				break;
			} else
				break;
		}
		
		/*
		 * Checks availability diagonally right-up;
		 */
		
		for(int i=column, j=row; i<Letter.H && j<8; i++, j++) {
			intermediate = Board.translate(i+1, j+1);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor){
				result.add(intermediate);
				break;
			} else
				break;
		}
		
		/*
		 * Checks availability diagonally right-down;
		 */
		
		for(int i=column, j=row; i<Letter.H && j>1; i++, j--) {
			intermediate = Board.translate(i+1, j-1);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor){
				result.add(intermediate);
				break;
			} else
				break;
		}
	
		return result;
	}

}