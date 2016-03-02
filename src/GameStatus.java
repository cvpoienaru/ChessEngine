import java.util.ArrayList;


public class GameStatus {

	private static boolean blackCheck;
	private static boolean blackCheckMate;
	private static boolean blackStaleMate;
	private static boolean whiteCheck;
	private static boolean whiteCheckMate;
	private static boolean whiteStaleMate;
	
	private static void initialize(){
		blackCheck = false;
		blackCheckMate = false;
		blackStaleMate = false;
		whiteCheck = false;
		whiteCheckMate = false;
		whiteStaleMate = false;
	}
	
	public static void update(int color){
		initialize();
		int currentColor, opponentColor;
		PieceSet currentSet = (color != PieceColor.BLACK) ? Board.getBlackSet() : Board.getWhiteSet();
		PieceSet opponentSet = (color != PieceColor.BLACK) ? Board.getWhiteSet() : Board.getBlackSet();
		currentColor = currentSet.getColor();
		opponentColor = opponentSet.getColor();
		Piece auxKing = currentSet.getAvailablePieces().get(0);
		
		if (Piece.canBeCaptured(auxKing, auxKing.getPosition(), opponentColor) != null){
			if (currentColor == PieceColor.BLACK){
				blackCheck = true;
			}
			if (currentColor == PieceColor.WHITE){
				whiteCheck = true;
			}
		}
		
		if (currentColor == PieceColor.BLACK && blackCheck && auxKing.getCaptureFreeSquares().isEmpty()){
			blackCheckMate = true;
		} else {
			if (currentColor == PieceColor.WHITE && whiteCheck && auxKing.getCaptureFreeSquares().isEmpty()){
				whiteCheckMate = true;
			}
		}
		
		if (currentColor == PieceColor.BLACK && !blackCheck && auxKing.getCaptureFreeSquares().isEmpty()){
			int possibleMoves=0;
			ArrayList<Piece> available = currentSet.getAvailablePieces();
			for (Piece p : available){
				possibleMoves+=p.getValidSquares().size();
			}
			
			if (possibleMoves==0)
				blackStaleMate=true;
		} else {
			if (currentColor == PieceColor.WHITE && !whiteCheck && auxKing.getCaptureFreeSquares().isEmpty()){
				int possibleMoves=0;
				ArrayList<Piece> available = currentSet.getAvailablePieces();
				for (Piece p : available){
					possibleMoves+=p.getValidSquares().size();
				}
			
				if (possibleMoves==0)
					whiteStaleMate=true;
			}
		}
		/*
		System.out.println(
				"\n blackCheck: " 		+ blackCheck 		+
				"\n blackCheckMate: " 	+ blackCheckMate 	+
				"\n blackStaleMate: " 	+ blackStaleMate 	+
				"\n whiteCheck: " 		+ whiteCheck 		+
				"\n whiteCheckMate: " 	+ whiteCheckMate 	+
				"\n whiteStaleMate: " 	+ whiteStaleMate 	+ '\n');
		*/
	}
	
	public static boolean isBlackCheck() {
		return GameStatus.blackCheck;
	}

	public static boolean isBlackCheckMate() {
		return GameStatus.blackCheckMate;
	}

	public static boolean isBlackStaleMate() {
		return GameStatus.blackStaleMate;
	}

	public static boolean isWhiteCheck() {
		return GameStatus.whiteCheck;
	}

	public static boolean isWhiteCheckMate() {
		return GameStatus.whiteCheckMate;
	}

	public static boolean isWhiteStaleMate() {
		return GameStatus.whiteStaleMate;
	}

	public static boolean isAnyChecked() {
		return GameStatus.blackCheck || GameStatus.whiteCheck;
	}
	
	public static boolean isAnyCheckMated() {
		return GameStatus.blackCheckMate || GameStatus.whiteCheckMate;
	}
	
	public static boolean isAnyStaleMated() {
		return GameStatus.blackStaleMate || GameStatus.whiteStaleMate;
	}
	
	
	
}
