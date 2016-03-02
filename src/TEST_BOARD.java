import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class TEST_BOARD {

	public static void main (String[] args) {
		
		// Generating an clear table
		Board.generateTable();
		int white = PieceColor.WHITE,
			black = PieceColor.BLACK;
		Piece checkme = null;
		
		
				/* PIECE SETS ON THE TABLE */
		// SET 1: All pieces on start position
		/*
		 * Board.initialize();
		 */
		
		
		// SET 2: White queen in the middle, 2 pawns (1 white, 1 black) in it's way
		/*
		checkme = new Queen (Board.translate(Letter.D, 4), white);
		Board.addPiece(checkme );
		Board.addPiece(new Pawn (Board.translate(Letter.G, 7), white) );
		Board.addPiece(new Pawn (Board.translate(Letter.D, 6), black) );
		Board.addPiece(new Pawn (Board.translate(Letter.C, 3), black) );
		Board.addPiece(new Pawn (Board.translate(Letter.D, 1), black) );
		Board.addPiece(new Pawn (Board.translate(Letter.G, 1), black) );
		*/
		
		// SET 3: Horsie
		/*
		checkme = new Knight (Board.translate(Letter.D, 4), white);
		Board.addPiece(checkme );
		Board.addPiece(new Queen (Board.translate(Letter.F, 3), white) );
		Board.addPiece(new Bishop (Board.translate(Letter.E, 6), black) );
		*/
		
		// SET 4: King
		/*
		checkme = new King (Board.translate(Letter.D, 4), white);
		Board.addPiece(checkme );
		Board.addPiece(new Queen (Board.translate(Letter.E, 4), white) );
		Board.addPiece(new Bishop (Board.translate(Letter.E, 5), black) );
		*/
		
		// SET 5: Neboonule
		
		/*
		checkme = new Bishop (Board.translate(Letter.D, 7), white);
		Board.addPiece(checkme );
		Board.addPiece(new Queen (Board.translate(Letter.F, 5), white) );
		Board.addPiece(new Bishop (Board.translate(Letter.E, 8), black) );
		*/
		
		
		// SET 6: ROOK and RUUL
		/*
		checkme = new Rook (Board.translate(Letter.D, 7), white);
		Board.addPiece(checkme );
		Board.addPiece(new Queen (Board.translate(Letter.D, 5), white) );
		Board.addPiece(new Bishop (Board.translate(Letter.D, 8), black) );
		*/
		
		
		
		// SET 7: Peeon
		/*
		checkme = new Pawn (Board.translate(Letter.D, 2), white);
		Board.addPiece( checkme );
		Board.addPiece(new Queen (Board.translate(Letter.E, 3), black) );
		Board.addPiece(new Bishop (Board.translate(Letter.C, 3), black) );
		
		*/
		// CAN BE CAPTURED
		/*
		checkme = new Queen (Board.translate(Letter.D, 4), white);
		Board.addPiece(checkme);
		

		Board.addPiece(new Knight	(Board.translate(Letter.C, 2), black) );
		Board.addPiece(new Bishop	(Board.translate(Letter.H, 3), black) );
		Board.addPiece(new Queen	(Board.translate(Letter.H, 7), black) );
		Board.addPiece(new Rook		(Board.translate(Letter.D, 8), black) );
		Board.addPiece(new Rook		(Board.translate(Letter.E, 7), black) );
		Board.addPiece(new Rook		(Board.translate(Letter.H, 5), black) );
		Board.addPiece(new King		(Board.translate(Letter.B, 8), white) );
		Board.addPiece(new Pawn		(Board.translate(Letter.C, 7), black) );
		*/
		
		//SAH
//		int rowNumber = 8;
//		checkme = new King		(Board.translate(Letter.E, rowNumber), black);
//		Board.addPiece(new Bishop	(Board.translate(Letter.C, rowNumber), black)); 
//		Board.addPiece(new Bishop	(Board.translate(Letter.F, rowNumber), black));
//		Board.addPiece(new Queen	(Board.translate(Letter.D, rowNumber), black));
//
//		Board.addPiece(new Pawn		(Board.translate(Letter.D, 7), black) );
//		Board.addPiece(new Pawn		(Board.translate(Letter.E, 7), black) );
//		
//		
//		
		
		
		
		// CASTLING
		/*
		checkme = new King			(Board.translate(Letter.E, 8), black);
		Rook rook1 = new Rook		(Board.translate(Letter.A, 8), black);
		Rook rook2 = new Rook		(Board.translate(Letter.H, 8), black);
		//rook1.setMoved(true);
		//rook2.setMoved(true);
		//((King) checkme).setMoved(true);
		
		Board.addPiece( rook1 );
		Board.addPiece( rook2 );
		
		Board.addPiece( new Rook	(Board.translate(Letter.D, 3), white));
		//Board.addPiece(new Pawn		(Board.translate(Letter.E, 6), white) );
		//Board.addPiece(new Rook		(Board.translate(Letter.C, 4), white) );
		//Board.addPiece(new Rook		(Board.translate(Letter.G, 4), white) );
		*/
		
		
		// Dont move and get checked!
		checkme = new King			(Board.translate(Letter.B, 8), black);
		Board.addPiece(new Knight	(Board.translate(Letter.D, 8), black) );
		Board.addPiece(new Queen	(Board.translate(Letter.F, 7), white) );
		Board.addPiece(new Queen	(Board.translate(Letter.B, 7), white) );
		
		
		// ----------------------------------------------------------------------------------------------------------------------------
		
		// OUTPUT
		Writer out=null;
	
		// BoardTest.out : ASCII ART TABLE INTERPRETATION
		try {	out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("BoardTest.out"), "UTF-8")); out.write( Board.printTable() ); } catch (IOException e) {} finally { try { out.close(); } catch (IOException e) {} }
		
		// BoardTest.txt : ASCII ART: TABLE INTERPRETATION & checkme's CAPTURE FREE squares
		 try {	out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("BoardTest.txt"), "UTF-8")); out.write( Board.printTableShowMoves(checkme.getCaptureFreeSquares() )); } catch (IOException e) {} finally { try { out.close(); } catch (IOException e) {} }
		
		// BoardTest.txt : ASCII ART: TABLE INTERPRETATION & checkme's POSSIBLE MOVES squares
		// try {	out = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("BoardTest.txt"), "UTF-8")); out.write( Board.printTableShowMoves(checkme.getValidSquares() )); } catch (IOException e) {} finally { try { out.close(); } catch (IOException e) {} }
		
	}
	
}
