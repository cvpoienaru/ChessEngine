import java.util.ArrayList;

public abstract class Piece implements Comparable<Piece> {
	
	private Square position;
	private int color;
	
	public Piece(Square position, int color) {
		
		this.position = position;
		this.color = color;
		this.position.setPiece(this);
	}
	
	public abstract ArrayList<Square> getValidSquares();
	
	public ArrayList<Square> getAtackSquares() {
		
		ArrayList<Square> result = new ArrayList<Square>();
		ArrayList<Square> validSquares = this.getValidSquares();
		int opponentColor = (this.color == PieceColor.BLACK) ? PieceColor.WHITE : PieceColor.BLACK;
				
		for(Square square : validSquares)
			if(square.getPiece() != null && square.getPiece().getColor() == opponentColor)
				result.add(square);
		
		return result;
	}
	
	public ArrayList<Square> getCaptureFreeSquares() {
		
		ArrayList<Square> result = new ArrayList<Square>();
		ArrayList<Square> validSquares = this.getValidSquares();
		int opponentColor = (this.color == PieceColor.BLACK) ? PieceColor.WHITE : PieceColor.BLACK;
		
		for(Square square : validSquares) {		
			if(Piece.canBeCaptured(this, square, opponentColor) == null)
				result.add(square);
		}
		
		return result;
	}
	
	public static Square checkDirection(Piece piece, Square square, int letter, int number,
			int opponentColor, int... opponents) {
		
		Square intermediate = square;
		while(Board.isSquareValid(intermediate.getLetter() + letter,
				intermediate.getNumber() + number)) {
			
			intermediate = Board.translate(intermediate.getLetter() + letter,
					intermediate.getNumber() + number);
			
			if(intermediate.getPiece() != null) {
				if(intermediate.getPiece().getColor() == opponentColor) {
					for(int opponent : opponents) {
						if(PieceType.getType(intermediate.getPiece()) == opponent)
							return intermediate;
					}
					break;
				}
				
				if(intermediate.getPiece() == piece)
					continue;
				break;
			}
		}
		
		intermediate = square;
		while(Board.isSquareValid(intermediate.getLetter() - letter,
				intermediate.getNumber() - number)) {
			
			intermediate = Board.translate(intermediate.getLetter() - letter,
					intermediate.getNumber() - number);
			
			if(intermediate.getPiece() != null) {
				if(intermediate.getPiece().getColor() == opponentColor) {
					for(int opponent : opponents) {
						if(PieceType.getType(intermediate.getPiece()) == opponent)
							return intermediate;
					}
					break;
				}
				
				if(intermediate.getPiece() == piece)
					continue;
				break;
			}
		}
		
		return null;
	}

	public static Square canBeCaptured(Piece piece, Square square, int opponentColor) {
		
		int k = 0;
		int number = square.getNumber();
		int letter = square.getLetter();
		int myColor = (opponentColor == PieceColor.BLACK) ? PieceColor.WHITE : PieceColor.BLACK;
		Square intermediate;
		
		if(myColor == PieceColor.WHITE) {
			if(Board.isSquareValid(letter + 1, number + 1)) {
				intermediate = Board.translate(letter + 1, number + 1);
				if(intermediate.getPiece() != null
						&& intermediate.getPiece().getColor() == opponentColor
						&& PieceType.getType(intermediate.getPiece()) == PieceType.PAWN)
					return intermediate;
			}
			
			if(Board.isSquareValid(letter - 1, number + 1)) {
				intermediate = Board.translate(letter - 1, number + 1);
				if(intermediate.getPiece() != null
						&& intermediate.getPiece().getColor() == opponentColor
						&& PieceType.getType(intermediate.getPiece()) == PieceType.PAWN)
					return intermediate;
			}
		}
		else {
			if(Board.isSquareValid(letter + 1, number - 1)) {
				intermediate = Board.translate(letter + 1, number - 1);
				if(intermediate.getPiece() != null
						&& intermediate.getPiece().getColor() == opponentColor
						&& PieceType.getType(intermediate.getPiece()) == PieceType.PAWN)
					return intermediate;
			}
			
			if(Board.isSquareValid(letter - 1, number - 1)) {
				intermediate = Board.translate(letter - 1, number - 1);
				if(intermediate.getPiece() != null
						&& intermediate.getPiece().getColor() == opponentColor
						&& PieceType.getType(intermediate.getPiece()) == PieceType.PAWN)
					return intermediate;
			}
		}
		
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(!(i == 0 && j == 0)) {
					
					if(Board.isSquareValid(letter + i, number + j)) {
						intermediate = Board.translate(letter + i, number + j);
						if(intermediate.getPiece() != null
								&& intermediate.getPiece().getColor() == opponentColor
								&& PieceType.getType(intermediate.getPiece()) == PieceType.KING)
							return intermediate;
					}
					
					if(k < 4) {
						Square s = null;
						if(Math.abs(i) == Math.abs(j)) {
							s = Piece.checkDirection(piece, square, i, j, opponentColor, PieceType.QUEEN, PieceType.BISHOP);
							if(s != null)
								return s;
						}
						else {
							s = Piece.checkDirection(piece, square, i, j, opponentColor, PieceType.QUEEN, PieceType.ROOK);
							if(s != null)
								return s;
						}
					}
					
					k++;
				}
			}
		}
		
		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				if(Math.abs(i) != Math.abs(j) && i != 0 && j != 0) {
					if(Board.isSquareValid(letter + i, number + j)) {
						intermediate = Board.translate(letter + i, number + j);
						if(intermediate.getPiece() != null
								&& intermediate.getPiece().getColor() == opponentColor
								&& PieceType.getType(intermediate.getPiece()) == PieceType.KNIGHT)
							return intermediate;
					}
				}
			}
		}
			
		return null;
	}
	
	public Square getPosition() {
		
		return this.position;
	}
	
	public void setPosition(Square position) {
		
		this.position = position;
	}
	
	public int getColor() {
		
		return this.color;
	}
	
	@Override
	public int compareTo(Piece piece) {
		if(this.position.compareTo(piece.getPosition()) == 0 && this.color == piece.color)
			return 0;
		return -1;
	}
}