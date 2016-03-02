import java.util.ArrayList;

public class Queen extends Piece {

	public Queen(Square position, int color) {
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
		 * Checks availability upwards
		 */
		
		for(int i=row; i<8; i++) {
			intermediate = Board.translate(column, i+1);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor) {
				result.add(intermediate);
				break;
			} else
				break;
		}
		
		/*
		 * Checks availability downwards
		 */
		
		for(int i=row; i>1; i--) {
			intermediate = Board.translate(column, i-1);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor){
				result.add(intermediate);
				break;
			} else
				break;
		}
		
		/*
		 * Checks availability sideways to left
		 */
		
		for(int i=column; i>Letter.A; i--) {
			intermediate = Board.translate(i-1, row);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor){
				result.add(intermediate);
				break;
			} else
				break;
		}
		
		/*
		 * Checks availability sideways to right
		 */
		
		for(int i=column; i<Letter.H; i++) {
			intermediate = Board.translate(i+1, row);
			if(intermediate.getPiece() == null) {
				result.add(intermediate);
			} else if (intermediate.getPiece().getColor() == oponentColor){
				result.add(intermediate);
				break;
			} else
				break;
		}
		
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