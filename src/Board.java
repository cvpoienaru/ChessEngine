import java.util.ArrayList;


public class Board {
	
	private static Square[][] board = null;
	private static PieceSet blackSet = null;
	private static PieceSet whiteSet = null;
	
	/**Initializez the chess table: A 9x9 matrix of squares
	 * <br> Line 1 and column 1 are unused, so they remain set to null
	 * <br> All the other squares of the matrix are initialized as emty squares.
	 * 
	 * Se initializeaza cele 2 seturi de piese (se aseaza pe tabla)
	 */
	public static void initialize() {
		Board.generateTable();
		Board.placeInitialPieces();
	}
	
	/** Generates a blank table, initializes the two sets.
	 * <br>NO pieces are placed on the board.
	 */ // Aceasta metoda trebuie sa fie PRIVATA. O tinem publica pentru testare.
	public /*private*/ static void generateTable() {
		Board.board = new Square[9][9];
		for(int number = 1; number <= 8; number++ )
			for(int letter = Letter.A; letter <= Letter.H; letter++)
				Board.board[number][letter] = new Square( letter , 9 - number );
		Board.blackSet = new PieceSet();
		Board.whiteSet = new PieceSet();
	}
	
	/**Places pieces on the table in the start positions. */
	private static void placeInitialPieces() {
		Board.blackSet.setInitialPieces(PieceColor.BLACK);
		Board.whiteSet.setInitialPieces(PieceColor.WHITE);
	}
	
	/**Gettting a letter and a number (int), it returns the square at the
	 * <br> specified indexation.
	 * @param letter
	 * @param number
	 * @return Square at [ letter ] [ number ]
	 */
	public static Square translate(int letter, int number) {
		return Board.board[9 - number][letter];
	}
	
	/** Returns the game board */
	public static Square[][] get() {
		return Board.board;
	}
	
	/** Return the black pieces set */
	public static PieceSet getBlackSet() {
		return Board.blackSet;
	}
	
	/** Return the white pieces set */
	public static PieceSet getWhiteSet() {
		return Board.whiteSet;
	}
	
	/** Given 2 indexes, says if the required position is valid. */
	public static boolean isSquareValid(int letter, int number) {
		return ( letter > 0 && letter < 9 && number > 0 && number < 9);
	}
	
	/**The COLOR set will include PIECE
	 * @param color WHITE / BLACK
	 * @param piece Queen / Pawn / Knight etc
	 */
	public static void addPiece(Piece piece) {
		if ( piece.getColor() == PieceColor.WHITE)
			Board.whiteSet.addPiece(piece);
		else
			Board.blackSet.addPiece(piece);
	}
	
	/** Given a string of 2 characters ( one letter , one diggit )
	 * <br> it returns the square at the required position, or NULL if
	 * <br> the position is invalid.
	 * @param position
	 * @return square at 'position' , or NULL
	 */
	public static Square getFromString(String position) {
		int letter = ( (int) ( position.charAt(0) -'a' ) ) + 1;
		int number = ( (int) ( position.charAt(1) -'1' ) ) + 1;
		if ( Board.isSquareValid(letter, number) )
			return Board.translate(letter, number);
		return null;
	}
	
	
	
	
	
	public static String printTable() {
		StringBuilder sb = new StringBuilder();
		
		for ( int i = 1 ; i <= 8 ; i++ ) {
			for ( int j = 1 ; j <= 8 ; j++ )
				sb.append(" " + Board.get()[i][j].toString() +" ");
				sb.append("\n");
			}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public static String printTableShowMoves(ArrayList<Square> checkedMoves) {
		StringBuilder sb = new StringBuilder();
		for ( int i = 1 ; i <= 8 ; i++ ) {
			for ( int j = 1 ; j <= 8 ; j++ )
				if ( checkedMoves.contains( Board.get()[i][j] ) ) sb.append(" * ");
				else
					sb.append(" " + Board.get()[i][j].toString() +" ");
			sb.append("\n");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
}