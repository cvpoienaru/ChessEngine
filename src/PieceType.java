
public class PieceType {

	public static final int PAWN = 0;
	public static final int KING = 1;
	public static final int QUEEN = 2;
	public static final int ROOK = 3;
	public static final int BISHOP = 4;
	public static final int KNIGHT = 5;

	public static int getType(Piece piece) {
		if(piece instanceof Pawn)
			return PieceType.PAWN;
		if(piece instanceof King)
			return PieceType.KING;
		if(piece instanceof Queen)
			return PieceType.QUEEN;
		if(piece instanceof Rook)
			return PieceType.ROOK;
		if(piece instanceof Bishop)
			return PieceType.BISHOP;
		return PieceType.KNIGHT;
	}
}