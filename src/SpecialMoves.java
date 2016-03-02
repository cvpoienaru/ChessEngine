import java.util.ArrayList;
import java.util.Random;

public class SpecialMoves {
	
	public static void pawnPromotion(Pawn pawn, char c){
	
		Square intermediate = pawn.getPosition();
		PieceSet set = pawn.getColor() == PieceColor.WHITE ?  
				Board.getWhiteSet() : Board.getBlackSet();
		set.capturePiece(pawn);
		Piece piece;
		
		switch (c) {
			case 'r': piece = new Rook	(intermediate, set.getColor());
				break;
			case 'k': piece = new Knight(intermediate, set.getColor());
				break;
			case 'b': piece = new Bishop(intermediate, set.getColor());
				break;
			default:  piece = new Queen	(intermediate, set.getColor());
		}
		
		set.addPiece(piece);
	}
	
	/**Moves the rook in order to perform a Kingside Castling*/
	public static void smallCastling(){
		int row = Game.getTurn() == PieceColor.WHITE ? 1 : 8;
		Game.move( new Move( Board.translate(Letter.H, row),  Board.translate(Letter.F, row) ));
	}

	/**Moves the rook in order to perform a Queenside Castling*/
	public static void bigCastling() {
		int row = Game.getTurn() == PieceColor.WHITE ? 1 : 8;
		Game.move( new Move( Board.translate(Letter.A, row),  Board.translate(Letter.D, row) ));
	}
	
	public static String outOfCheck() {
		
		Piece auxKing = Game.getMySet().getAvailablePieces().get(0);
		ArrayList<Square> captureFreeSquares = auxKing.getCaptureFreeSquares();
		int opponentColor = auxKing.getColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
		Square intermediate;
		
		if(captureFreeSquares.size() != 0) {
			// Move the king out of check.
			Move move;
			Random randGen = new Random();
			int m = randGen.nextInt(captureFreeSquares.size());
			move = new Move(auxKing.getPosition(), captureFreeSquares.get(m));
			Game.moveToWinboard(move);
			return move.toString() + "\n";
		}
		
		// Try to capture the piece that threatens the king.
		Square s = Piece.canBeCaptured(auxKing, auxKing.getPosition(), opponentColor);
		
		for(Piece p : Game.getMySet().getAvailablePieces()) {
			if(PieceType.getType(p) == PieceType.KING)
				continue;
			for(Square sqr : p.getValidSquares())
				if(sqr == s) {
					Move move = new Move(p.getPosition(), s);
					Game.moveToWinboard(move);
					return move.toString() + "\n";
				}
		}
		
		// Try to block the piece that threatens the king.
		// A knight cannot be blocked.
		if(PieceType.getType(s.getPiece()) == PieceType.KNIGHT)
			return "resign\n";
		
		int letterDirection = s.getLetter() - auxKing.getPosition().getLetter();
		int numberDirection = s.getNumber() - auxKing.getPosition().getNumber();
		letterDirection = letterDirection < 0 ? -1 : (letterDirection > 0 ? 1 : 0);
		numberDirection = numberDirection < 0 ? -1 : (numberDirection > 0 ? 1 : 0);
		
		intermediate = Board.translate(auxKing.getPosition().getLetter() + letterDirection,
				auxKing.getPosition().getNumber() + numberDirection);
		while(intermediate.compareTo(s) != 0) {
			for(Piece p : Game.getMySet().getAvailablePieces()) {
				if(PieceType.getType(p) == PieceType.KING)
					continue;
				
				for(Square sqr : p.getValidSquares())
					if(sqr == intermediate) {
						Move move = new Move(p.getPosition(), intermediate);
						Game.moveToWinboard(move);
						return move.toString() + "\n";
					}
			}
			
			intermediate = Board.translate(intermediate.getLetter() + letterDirection,
					intermediate.getNumber() + numberDirection);
		}
		
		return "resign\n";
	}
}