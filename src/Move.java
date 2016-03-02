
public class Move {
	
	private Square startSquare;
	private Square endSquare;
	private char specialMove;
	
	
	/** Constructor that gets a string */
	public Move(String moveString) {
		if ( moveString.length() < 4 ) {
			this.startSquare = null;
			this.endSquare = null;
			this.specialMove = 0;
		}
		else {
			this.startSquare = Board.getFromString(moveString.substring(0,2));
			this.endSquare = Board.getFromString(moveString.substring(2,4));
			if ( moveString.length() == 5 ) this.specialMove = moveString.charAt(4);
			else this.specialMove = 0;
		}
	}
	
	/** Constructor that gets start and end square */
	public Move(Square startSquare, Square endSquare) {
		this.startSquare = startSquare;
		this.endSquare = endSquare;
	}
	
	public Move reverse() {
		return new Move(this.endSquare, this.startSquare);
	}

	public Square getStartSquare() {
		return this.startSquare;
	}
	
	public Square getEndSquare() {
		return this.endSquare;
	}
	
	@Override
	public String toString() {
		return ("move " + startSquare.getCoords() + endSquare.getCoords()).toLowerCase() + '\n';
	}

	public char getSpecialMove() {
		return specialMove;
	}

	public void setSpecialMove(char specialMove) {
		this.specialMove = specialMove;
	}
}