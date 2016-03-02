import java.util.ArrayList;

public class Knight extends Piece {

	public Knight(Square position, int color) {
		super(position, color);
	}

	
	
	
	@Override
	public ArrayList<Square> getValidSquares() {
		int row = this.getPosition().getNumber();
		int column = this.getPosition().getLetter();
		int oponentColor = (this.getColor() == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE; 
		Square intermediate;
		ArrayList<Square> result = new ArrayList<Square>();
		
		
		
		
			
		
		for(int i = -2; i <= 2; i++) { 
			for(int j = -2; j <= 2; j++) {
				if(Math.abs(i) != Math.abs(j) && i != 0 && j != 0) {
					if(Board.isSquareValid(row + i, column + j)){
						intermediate = Board.translate(column + j, row + i);
						if(intermediate.getPiece() == null || intermediate.getPiece().getColor() == oponentColor)
							result.add(intermediate);
					}
				} 
			} 
		}
		
		return result;
	}

}