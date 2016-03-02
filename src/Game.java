import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class Game {

	private static int mode = GameMode.DEFAULT;
	private static long whiteClock;
	private static long blackClock;
	private static Stack<Move> history = new Stack<Move>();

	@SuppressWarnings("unused")
	private static long myClock;
	private static PieceSet mySet;
	private static PieceSet opponentSet;
	private static int turn;
	private static int myColor;
	@SuppressWarnings("unused")
	private static int opponentColor;

	/** Creates a new game:
	 * <br> - Clears the table
	 * <br> - Sets new piece sets in the default positions
	 * <br> - Sets WHITE on turn
	 * <br> - Resets clocks
	 */
	public static void initialize() {
		Board.initialize();
		Game.whiteClock = Game.blackClock = Game.myClock = 0;
		Game.mode = GameMode.DEFAULT;
		Game.mySet = Board.getBlackSet();
		Game.opponentSet = Board.getWhiteSet();
		Game.turn = PieceColor.WHITE;
		Game.myClock = Game.blackClock;
		//TODO: set myColor, opponentColor
		Game.myColor = PieceColor.BLACK;
		Game.opponentColor = PieceColor.WHITE;
		//TODO: Check what set should I be
	}

	public static int getTurn() { return turn; }



	/** Verifies if the move made by the opponent is valid:
	 * <br> - If the move is valid, it replies with another move.
	 * <br> - If the move is invalid, it returns a message, pointing it out.
	 * @param moveString opponent's move
	 * @return My move , OR error message
	 */
	public static String replyToMove(String moveString) {
		Move move = new Move ( moveString );
		// The move is valid ONLY IF :
		if (move.getStartSquare() == null ||				// it has valid start
			move.getEndSquare() == null ||					// and end positions,
			move.getStartSquare().getPiece() == null || 	// the piece on the start position has
			move.getStartSquare().getPiece().getColor() != Game.turn || // colour of the turn				
			move.getStartSquare().getPiece().getValidSquares().indexOf(move.getEndSquare()) < 0 )
				return "Illegal move: " + moveString + '\n';	//^^ and the end square is a valid move
		
		/*
		// Verific ca adversarul nu s-a pus singur in sah
		move(move);
		Piece kinginturn = (turn==PieceColor.WHITE ? Board.getWhiteSet().getAvailablePieces().get(0)
												   : Board.getBlackSet().getAvailablePieces().get(0));
		int colorNotInTurn = turn==PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
		
		if ( Piece.canBeCaptured(kinginturn, kinginturn.getPosition(), colorNotInTurn) != null )
			return "Illegal move: " + moveString + '\n';
		*/
		
		moveToWinboard(move); // If WinBoard sent a valid move, the move is made
		history.push(move);
		if ( Game.mode == GameMode.FORCE) return "";	// We don't move when in FORCE mode

		return think(); // Must be changed with a method that returns a clever move.
	}

	
	private static String think(){
		if(Game.mySet.getColor()==PieceColor.BLACK && GameStatus.isBlackCheck() || 
		   Game.mySet.getColor()==PieceColor.WHITE && GameStatus.isWhiteCheck()	){
			 return SpecialMoves.outOfCheck();
		} else return getRandomMove();
	}
	
	
	/** Returns a random move (and changes the turn) */
	// It changes the turn because it calls the makeMove() method
	public static String getRandomMove() {
		Random randGen = new Random();
		Piece pieceToMove;
		Move randMove = null;

		while(true) {
			int randomIndex;
			int tries = 100;
			do { randomIndex = randGen.nextInt(mySet.getAvailablePieces().size());
				pieceToMove = mySet.getAvailablePieces().get(randomIndex);
				tries--;
			} while ( ( PieceType.getType(pieceToMove) == PieceType.KING ? // Repeta cat timp piesa
							pieceToMove.getCaptureFreeSquares().size() == 0 : // alesa nu are mutari
							pieceToMove.getValidSquares().size() == 0 ) // posibile, (pt rege: nu are
						&& tries > 0);							// mutari in care sa nu intre in sah)
	
			if ( tries == 0 ) return "resign";
			
			ArrayList<Square> possibleMoves = ( PieceType.getType(pieceToMove) == PieceType.KING ?
					pieceToMove.getCaptureFreeSquares() : pieceToMove.getValidSquares() );
			randMove = new Move (	pieceToMove.getPosition(),
										possibleMoves.get((randGen.nextInt(possibleMoves.size()))));
			
			int opponentColor = mySet.getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
			Piece oldPiece = move(randMove);
			if(Piece.canBeCaptured(mySet.getAvailablePieces().get(0),
					mySet.getAvailablePieces().get(0).getPosition(),opponentColor) != null) {
				undo(randMove, oldPiece);
				continue;
			}
			undo(randMove, oldPiece);
			
			Piece p = randMove.getStartSquare().getPiece();
			
			// If the pawn reached the end of the table, he becomes a queen
			if(PieceType.getType(p) == PieceType.PAWN && 
			  (randMove.getEndSquare().getNumber() == 1 || randMove.getEndSquare().getNumber() == 8) ) {
				randMove.setSpecialMove('q');
			}
			
			break;
		}
		
		return moveToWinboard(randMove);
	}

	public static PieceSet getMySet() {
		return mySet;
	}

	/** Makes a move, changes the turn, and returns the move to winboard.
	 * @return the move in a string interpretation*/
	// We are SURE the move is VALID. It has been checked in earlier methods
	public static String moveToWinboard(Move move) {
		// Move the piece and change the turn
		simulateMove(move);

		// Tell winboard about the move
		return move.toString();
	}
	
	/** Makes a move on the table and changes the turn (stores move in history) */
	public static void simulateMove(Move move) {
		
		// Check if there is an ENEMY PIECE on END POSITION and CAPTURE it
		if ( move.getEndSquare().getPiece() != null ) 
			if ( turn == myColor )
				opponentSet.capturePiece(move.getEndSquare().getPiece());
			else
				mySet.capturePiece(move.getEndSquare().getPiece());
		
		move(move);
		
		// Mark the piece as moved, if it'a a king, rook, or a pawn, so they can't make any
				// more special moves.
		switch(PieceType.getType(move.getEndSquare().getPiece())) {
			case PieceType.PAWN:
				((Pawn)move.getEndSquare().getPiece()).makeMove();
				if ( move.getSpecialMove() != 0 )
					SpecialMoves.pawnPromotion( (Pawn)(move.getEndSquare().getPiece()), 
							move.getSpecialMove() );
				break;
			case PieceType.KING:
				// Checking if a castling move has been made
				if ( move.getStartSquare().getLetter() - move.getEndSquare().getLetter() == 2 )
					SpecialMoves.bigCastling();
				if ( move.getStartSquare().getLetter() - move.getEndSquare().getLetter() == -2 )
					SpecialMoves.smallCastling();
					
				((King)move.getEndSquare().getPiece()).makeMove();
				
				
				break;
			case PieceType.ROOK:
				((Rook)move.getEndSquare().getPiece()).makeMove();
				break;
		}
		GameStatus.update(move.getEndSquare().getPiece().getColor());
		Game.changeTurn(); // The turn will only get changed AFTER I think my next move.
	}
	
	/**Moves the piece on the board without changing the turn, and stores it
	 * in the history*/
	public static Piece move(Move move) {
		
		// Place the piece on the new spot
		Piece oldPiece = move.getEndSquare().getPiece();
		move.getStartSquare().getPiece().setPosition(move.getEndSquare()); // We move our piece
		move.getEndSquare().setPiece(move.getStartSquare().getPiece()); // on the end square
		move.getStartSquare().setPiece(null); // And remove it from the initial square
		
		return oldPiece;
		//history.add(move);
	}
	
	public static void undo(Move move, Piece oldPiece) {
		Move reverse = move.reverse();
		Game.move(reverse);
		if ( oldPiece != null ) oldPiece.getPosition().setPiece(oldPiece);
		// TODO: Pawn promotion ?!
		/*switch(PieceType.getType(reverse.getEndSquare().getPiece())) {
			case PieceType.PAWN:
				((Pawn)reverse.getEndSquare().getPiece()).undoMove();
				((Pawn)reverse.getEndSquare().getPiece()).undoMove();
				if ( reverse.getSpecialMove() != 0 )
					SpecialMoves.pawnPromotion( (Pawn)(reverse.getEndSquare().getPiece()), 
							reverse.getSpecialMove() );
				break;
			case PieceType.KING:
				((King)reverse.getEndSquare().getPiece()).undoMove();
				((King)reverse.getEndSquare().getPiece()).undoMove();
				break;
			case PieceType.ROOK:
				((Rook)reverse.getEndSquare().getPiece()).undoMove();
				((Rook)reverse.getEndSquare().getPiece()).undoMove();
				break;
		}*/
	}

	public static void setDefaultMode() {
		mode=GameMode.DEFAULT;
		mySet = turn == PieceColor.WHITE ? Board.getWhiteSet() : Board.getBlackSet(); 
		myClock = turn == PieceColor.WHITE ? Game.whiteClock : Game.blackClock;
	}

	public static void setForceMode() {
		mode=GameMode.FORCE;
	}

	public static void setTurn(int color) {
		Game.mySet = (color == PieceColor.WHITE) ? Board.getBlackSet() : Board.getWhiteSet();
		Game.myClock = (color == PieceColor.WHITE) ? Game.blackClock : Game.whiteClock;
	}

	/** Switches turns */
	public static void changeTurn() {
		Game.turn = (Game.turn == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
	}

	public static boolean validMove(Move move){
		if(move.getStartSquare().getPiece() != null &&  move.getStartSquare().getPiece().getColor() == turn){
			ArrayList<Square> possibleMoves = move.getStartSquare().getPiece().getValidSquares();
			if(possibleMoves.contains(move.getEndSquare())){
				return true;
				}
			}

		return false;
	}

	public static  Stack<Move> getHistory() {
		return history;
	}
}