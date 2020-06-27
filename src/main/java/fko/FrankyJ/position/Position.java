/*
 *  MIT License
 *
 *  Copyright (c) 2020 Frank Kopp
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 *
 */

package fko.FrankyJ.position;


import fko.FrankyJ.types.*;

import java.util.Arrays;

import static fko.FrankyJ.position.PosValues.posEndValue;
import static fko.FrankyJ.position.PosValues.posMidValue;
import static fko.FrankyJ.types.Square.*;

public class Position {

  /**
   * Standard Board Setup as FEN
   */
  public static final String STANDARD_BOARD_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

  /**
   * Max History
   */
  public static final int MAX_HISTORY = 512;

  /**
   * Max game phase value
   */
  public static final int GAME_PHASE_MAX = 24;

  // Convenience constants
  static final int WHITE = Color.White.ordinal();
  static final int BLACK = Color.Black.ordinal();

  // History Stack
  HistoryStack[] historyStack = new HistoryStack[MAX_HISTORY];

  // history counter
  int historyCounter = 0;

  // Flag for boolean states with undetermined state
  enum Flag {TBD, TRUE, FALSE}

  // The zobrist key to use as a hash key in transposition tables
  // The zobrist key will be updated incrementally every time one of the the
  // state variables change.
  long zobristKey = 0;

  // We also maintain a zobrist key for all pawns to support a pawn
  // evaluation table
  long pawnKey = 0;

  // **********************************************************
  // Board State
  // unique chess position (exception is 3-fold repetition
  // which is also not represented in a FEN string)

  Piece[] board           = new Piece[Square.values.length - 1];
  int     castlingRights  = CastlingRights.CastlingNone;
  Color   nextPlayer      = Color.White;
  Square  enPassantSquare = SqNone;
  int     halfMoveClock   = 0;
  int     moveNumber      = 1;

  // Board State END ------------------------------------------
  // **********************************************************

  // **********************************************************
  // Extended Board State -------------------------------------
  // not necessary for a unique position

  Square[] kingSquare = new Square[Color.values.length];
  long[][] piecesBb   = new long[Color.values.length][PieceType.values.length];
  long[]   occupiedBb = new long[Color.values.length];

  // Extended Board State END ---------------------------------
  // **********************************************************

  // **********************************************************
  // Calculated by doMove/undoMove
  // Material value of pieces on board
  int[] material        = new int[Color.values.length];
  int[] materialNonPawn = new int[Color.values.length];
  // Positional value will always be up to date
  int[] psqMidValue     = new int[Color.values.length];
  int[] psqEndValue     = new int[Color.values.length];
  // Game phase value
  int   gamePhase       = 0;
  // caches a hasCheck and hasMate Flag for the current position. Will be set
  // after a call to hasCheck() and reset to TBD every time a move is made or
  // unmade.
  Flag  hasCheckFlag    = Flag.TBD;
  // Calculated by doMove/undoMove END
  // **********************************************************

  // **********************************************************
  // Constructors START -----------------------------------------

  /**
   * Creates a standard board position and initializes it with standard chess setup.
   */
  public Position() {
    this(STANDARD_BOARD_FEN);
  }

  /**
   * Creates a standard board position and initializes it with a fen position
   *
   * @param fen
   */
  public Position(String fen) {
    setupBoard(fen);
  }

  /**
   * Copy constructor - creates a copy of the given Position
   *
   * @param op
   */
  public Position(Position op) {
    // TODO

    //    if (op == null) throw new NullPointerException("Parameter op may not be null");
    //
    //    // x88 board
    //    System.arraycopy(op.x88Board, 0, this.x88Board, 0, op.x88Board.length);
    //
    //    // game state
    //    this.nextHalfMoveNumber = op.nextHalfMoveNumber;
    //    this.nextPlayer = op.nextPlayer;
    //    this.zobristKey = op.zobristKey;
    //
    //    this.castlingWK = op.castlingWK;
    //    this.castlingWQ = op.castlingWQ;
    //    this.castlingBK = op.castlingBK;
    //    this.castlingBQ = op.castlingBQ;
    //    this.enPassantSquare = op.enPassantSquare;
    //    this.halfMoveClock = op.halfMoveClock;
    //
    //    this.hasCheck = op.hasCheck;
    //    this.hasMate = op.hasMate;
    //
    //    // history
    //    this.historyCounter = op.historyCounter;
    //    System.arraycopy(op.zobristKeyHistory, 0, zobristKeyHistory, 0, zobristKeyHistory.length);
    //
    //    System.arraycopy(op.castlingWK_History, 0, castlingWK_History, 0, castlingWK_History.length);
    //    System.arraycopy(op.castlingWQ_History, 0, castlingWQ_History, 0, castlingWQ_History.length);
    //    System.arraycopy(op.castlingBK_History, 0, castlingBK_History, 0, castlingBK_History.length);
    //    System.arraycopy(op.castlingBQ_History, 0, castlingBQ_History, 0, castlingBQ_History.length);
    //    System.arraycopy(op.enPassantSquare_History, 0, enPassantSquare_History, 0,
    //                     enPassantSquare_History.length);
    //    System.arraycopy(op.halfMoveClockHistory, 0, halfMoveClockHistory, 0,
    //                     halfMoveClockHistory.length);
    //
    //    System.arraycopy(op.hasCheckFlagHistory, 0, hasCheckFlagHistory, 0, hasCheckFlagHistory.length);
    //    System.arraycopy(op.hasMateFlagHistory, 0, hasMateFlagHistory, 0, hasMateFlagHistory.length);
    //
    //    // move history
    //    System.arraycopy(op.moveHistory, 0, moveHistory, 0, op.moveHistory.length);
    //
    //    // copy piece lists and bitboards
    //    for (int i = 0; i <= 1; i++) { // foreach color
    //      // piece lists
    //      this.pawnSquares[i] = op.pawnSquares[i].clone();
    //      this.knightSquares[i] = op.knightSquares[i].clone();
    //      this.bishopSquares[i] = op.bishopSquares[i].clone();
    //      this.rookSquares[i] = op.rookSquares[i].clone();
    //      this.queenSquares[i] = op.queenSquares[i].clone();
    //      this.kingSquares[i] = op.kingSquares[i];
    //      // bitboards
    //      this.occupiedBitboards[i] = op.occupiedBitboards[i];
    //      this.occupiedBitboardsR90[i] = op.occupiedBitboardsR90[i];
    //      this.occupiedBitboardsL90[i] = op.occupiedBitboardsL90[i];
    //      this.occupiedBitboardsR45[i] = op.occupiedBitboardsR45[i];
    //      this.occupiedBitboardsL45[i] = op.occupiedBitboardsL45[i];
    //
    //      System.arraycopy(op.piecesBitboards[i], 0, this.piecesBitboards[i], 0,
    //                       PieceType.values.length);
    //  }
    //
    //  // copy material and gamePhase values
    //  material = new int[2];
    //    this.material[WHITE] = op.material[WHITE];
    //    this.material[BLACK] = op.material[BLACK];
    //    this.gamePhase = op.gamePhase;
  }

  public void doMove(int move) {

    final Square fromSq = Move.fromSquare(move);
    final Square toSq = Move.toSquare(move);

    // Save state of board for undo
    final int i = historyCounter++;
    historyStack[i].zobristKeyHistory = zobristKey;
    historyStack[i].pawnZobristKeyHistory = pawnKey;
    historyStack[i].moveHistory = move;
    historyStack[i].fromPieceHistory = board[fromSq.ordinal()];
    historyStack[i].capturedPieceHistory = board[toSq.ordinal()];
    historyStack[i].castlingRightsHistory = castlingRights;
    historyStack[i].enPassantHistory = enPassantSquare;
    historyStack[i].halfMoveClockHistory = halfMoveClock;
    historyStack[i].hasCheckFlagHistory = hasCheckFlag;

    switch (Move.typeOf(move)) {

      case Normal -> {
        if (castlingRights != 0) {
          int cr = CastlingRights.castlingRights[fromSq.ordinal()] | CastlingRights.castlingRights[toSq.ordinal()];
          if (cr != 0) {
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// out
            castlingRights &= ~cr;
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights]; // in
          }
        }
        if (enPassantSquare != SqNone) {
          zobristKey = zobristKey ^ Zobrist.enPassantFile_Zobrist[enPassantSquare.file.ordinal()];// out
          enPassantSquare = SqNone;
        }
        if (board[toSq.ordinal()] != Piece.PieceNone) {// capture
          removePiece(toSq);
          halfMoveClock = 0;// reset half move clock because of capture
        } else if (board[fromSq.ordinal()].pieceType == PieceType.Pawn) {
          halfMoveClock = 0;                // reset half move clock because of pawn move
          if (fromSq.distance(toSq) == 2) { // pawn double - set en passant
            // set new en passant target field - always one "behind" the toSquare
            enPassantSquare = toSq.pawnPush(board[fromSq.ordinal()].color.flip());
            zobristKey = zobristKey ^ Zobrist.enPassantFile_Zobrist[enPassantSquare.file.ordinal()];// in
          }
        } else {
          halfMoveClock++;
        }
        movePiece(fromSq, toSq);
      }

      case Promotion -> {
        if (board[toSq.ordinal()] != Piece.PieceNone) {
          removePiece(toSq);
        }
        if (castlingRights != 0) {
          int cr = CastlingRights.castlingRights[fromSq.ordinal()] | CastlingRights.castlingRights[toSq.ordinal()];
          if (cr != 0) {
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// out
            castlingRights &= ~cr;
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights]; // in
          }
        }
        if (enPassantSquare != SqNone) {
          zobristKey = zobristKey ^ Zobrist.enPassantFile_Zobrist[enPassantSquare.file.ordinal()];// out
          enPassantSquare = SqNone;
        }
        removePiece(fromSq);
        putPiece(Piece.getPiece(nextPlayer, Move.promotionTypeOf(move)), toSq);
        halfMoveClock = 0;// reset half move clock because of pawn move
      }

      case EnPassant -> {
        final Square capSq = toSq.pawnPush((board[fromSq.ordinal()].color.flip()));
        if (enPassantSquare != SqNone) {
          zobristKey = zobristKey ^ Zobrist.enPassantFile_Zobrist[enPassantSquare.file.ordinal()];// out
          enPassantSquare = SqNone;
        }
        removePiece(capSq);
        movePiece(fromSq, toSq);
        halfMoveClock = 0;// reset half move clock because of pawn move
      }

      case Castling -> {
        switch (toSq) {
          case SqG1 -> {
            movePiece(fromSq, toSq);                              // King
            movePiece(SqH1, SqF1);                              // Rook
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// out
            castlingRights &= ~CastlingRights.CastlingWhite;
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// in;
          }
          case SqC1 -> {
            movePiece(fromSq, toSq);                              // King
            movePiece(SqA1, SqD1);                              // Rook
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// out
            castlingRights &= ~CastlingRights.CastlingWhite;
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// in
          }
          case SqG8 -> {
            movePiece(fromSq, toSq);                              // King
            movePiece(SqH8, SqF8);                              // Rook
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// out
            castlingRights &= ~CastlingRights.CastlingBlack;
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// in
          }
          case SqC8 -> {
            movePiece(fromSq, toSq);                              // King
            movePiece(SqA8, SqD8);                              // Rook
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// out
            castlingRights &= ~CastlingRights.CastlingBlack;
            zobristKey ^= Zobrist.castling_Zobrist[castlingRights];// in
          }
          default -> throw new IllegalArgumentException("Invalid castle move!");
        }
        if (enPassantSquare != SqNone) {
          zobristKey = zobristKey ^ Zobrist.enPassantFile_Zobrist[enPassantSquare.file.ordinal()];// out
          enPassantSquare = SqNone;
        }
        halfMoveClock++;
      }
    }
    // update additional state info
    hasCheckFlag = Flag.TBD;
    if (nextPlayer == Color.Black) moveNumber++;
    nextPlayer = nextPlayer.flip();
    zobristKey ^= Zobrist.nextPlayer_Zobrist;
  }

  public void undoMove() {
    // Restore state part 1
    historyCounter--;
    if (nextPlayer == Color.White) moveNumber--;
    nextPlayer = nextPlayer.flip();

    final int i = this.historyCounter;
    int move = this.historyStack[i].moveHistory;

    // undo piece move / restore board
    // ignore Zobrist Key as it will be restored via history
    switch (Move.typeOf(move)) {
      case Normal -> {
        movePiece(Move.toSquare(move), Move.fromSquare(move));
        if (this.historyStack[i].capturedPieceHistory != Piece.PieceNone) {
          putPiece(this.historyStack[i].capturedPieceHistory, Move.toSquare(move));
        }
      }
      case Promotion -> {
        removePiece(Move.toSquare(move));
        putPiece(Piece.getPiece(nextPlayer, PieceType.Pawn), Move.fromSquare(move));
        if (this.historyStack[i].capturedPieceHistory != Piece.PieceNone) {
          putPiece(this.historyStack[i].capturedPieceHistory, Move.toSquare(move));
        }
      }
      case EnPassant -> {
        movePiece(Move.toSquare(move), Move.fromSquare(move));
        putPiece(Piece.getPiece(nextPlayer.flip(), PieceType.Pawn), Move.toSquare(move).pawnPush(nextPlayer.flip()));
      }
      case Castling -> {
        movePiece(Move.toSquare(move), Move.fromSquare(move));// King
        switch (Move.toSquare(move)) {
          case SqG1 -> movePiece(SqF1, SqH1);
          case SqC1 -> movePiece(SqD1, SqA1);
          case SqG8 -> movePiece(SqF8, SqH8);
          case SqC8 -> movePiece(SqD8, SqA8);
          default -> throw new IllegalArgumentException("Invalid castle move!");
        }
      }
    }

    // restore state part 2
    castlingRights = this.historyStack[i].castlingRightsHistory;
    enPassantSquare = this.historyStack[i].enPassantHistory;
    halfMoveClock = this.historyStack[i].halfMoveClockHistory;
    zobristKey = this.historyStack[i].zobristKeyHistory;
    pawnKey = this.historyStack[i].pawnZobristKeyHistory;
    hasCheckFlag = this.historyStack[i].hasCheckFlagHistory;
  }

  private void movePiece(final Square fromSq, final Square toSq) {
    putPiece(removePiece(fromSq), toSq);
  }

  // puts a piece on the board and updates all required data
  private void putPiece(final Piece piece, final Square sq) {
    // piece board
    board[sq.ordinal()] = piece;
    if (piece.pieceType == PieceType.King) {
      kingSquare[piece.color.ordinal()] = sq;
    }
    // bitboards
    piecesBb[piece.color.ordinal()][piece.pieceType.ordinal()] |= sq.Bb;
    occupiedBb[piece.color.ordinal()] |= sq.Bb;
    // zobrist
    zobristKey ^= Zobrist.pieceZobrist[piece.ordinal()][sq.ordinal()];
    if (piece.pieceType == PieceType.Pawn) {
      pawnKey ^= Zobrist.pieceZobrist[piece.ordinal()][sq.ordinal()];
    }
    // game phase
    gamePhase += piece.pieceType.gamePhaseValue;
    if (gamePhase > GAME_PHASE_MAX) {
      gamePhase = GAME_PHASE_MAX;
    }
    // material
    material[piece.color.ordinal()] += piece.pieceType.value;
    if (piece.pieceType.gamePhaseValue > 0) {
      materialNonPawn[piece.color.ordinal()] += piece.pieceType.value;
    }
    // position value
    psqMidValue[piece.color.ordinal()] += posMidValue[piece.ordinal()][sq.ordinal()];
    psqEndValue[piece.color.ordinal()] += posEndValue[piece.ordinal()][sq.ordinal()];
  }

  // removes a piece from the board and updates all required data
  private Piece removePiece(final Square sq) {
    Piece removed = board[sq.ordinal()];
    // piece board
    board[sq.ordinal()] = Piece.PieceNone;
    // bitboards
    piecesBb[removed.color.ordinal()][removed.pieceType.ordinal()] &= ~sq.Bb;
    occupiedBb[removed.color.ordinal()] &= sq.Bb;
    // zobrist
    zobristKey ^= Zobrist.pieceZobrist[removed.ordinal()][sq.ordinal()];
    if (removed.pieceType == PieceType.Pawn) {
      pawnKey ^= Zobrist.pieceZobrist[removed.ordinal()][sq.ordinal()];
    }
    // game phase
    gamePhase -= removed.pieceType.gamePhaseValue;
    if (gamePhase < 0) {
      gamePhase = 0;
    }
    // material
    material[removed.color.ordinal()] -= removed.pieceType.value;
    if (removed.pieceType.gamePhaseValue > 0) {
      materialNonPawn[removed.color.ordinal()] -= removed.pieceType.value;
    }
    // position value
    psqMidValue[removed.color.ordinal()] -= posMidValue[removed.ordinal()][sq.ordinal()];
    psqEndValue[removed.color.ordinal()] -= posEndValue[removed.ordinal()][sq.ordinal()];
    return removed;
  }


  private void initBoard() {
    for (int i = 0; i < MAX_HISTORY; i++) {
      historyStack[i] = new HistoryStack();
    }
    Arrays.fill(board, Piece.PieceNone);
  }

  private void setupBoard(String fen) {

    initBoard();

    // We will analyse the fen and only require the initial board layout part.
    // All other parts will have defaults. E.g. next player is white, no castling, etc.

    fen = fen.trim();
    String[] fenParts = fen.split(" ");

    if (fenParts.length == 0) {
      throw new IllegalArgumentException("FEN must not be empty. ");
    }

    // make sure only valid chars are used
    if (fenParts[0].matches("[^0-8pPnNbBrRqQkK/]+")) {
      throw new IllegalArgumentException("FEN position contains illegal characters: " + fenParts[0]);
    }

    // a fen start at A8 - which is file==0 and rank==7
    int file = 0;
    int rank = 7;

    // loop over fen characters
    for (char c : fenParts[0].toCharArray()) {
      if (Character.isDigit(c)) { // is number
        file += Character.getNumericValue(c);
        if (file > 8) {
          throw new IllegalArgumentException(String.format("too many squares (%d) in rank %d:  %s", file, rank + 1, fenParts[0]));
        }
      } else if (c == '/') { // find rank separator
        if (file < 8) {
          throw new IllegalArgumentException(String.format("not enough squares (%d) in rank %d:  %s", file, rank + 1, fenParts[0]));
        }
        if (file > 8) {
          throw new IllegalArgumentException(String.format("too many squares (%d) in rank %d:  %s", file, rank + 1, fenParts[0]));
        }
        // reset file counter and decrease rank
        file = 0;
        rank--;
        if (rank < 0) {
          throw new IllegalArgumentException(String.format("too many ranks (%d):  %s", 8 - rank, fenParts[0]));
        }
      } else { // find piece type from piece symbol
        Piece piece = Piece.getPiece(c);
        if (piece == Piece.PieceNone) {
          throw new IllegalArgumentException(String.format("invalid piece character '%s' in %s", c, fenParts[0]));
        }
        if (file > 7) {
          throw new IllegalArgumentException(String.format("too many squares (%d) in rank %d:  %s", file + 1, rank + 1, fenParts[0]));
        }
        Square currentSquare = getSquare(File.getFile(file), Rank.getRank(rank));
        if (currentSquare == SqNone) {
          throw new IllegalArgumentException(String.format("invalid square %d (%s): %s", currentSquare.ordinal(), currentSquare.toString(), fenParts[0]));
        }
        this.putPiece(piece, currentSquare);
        file++;
      }
    }
    if (file != 8 || rank != 0) {
      throw new IllegalArgumentException(String.format("not reached last square (file=%d, rank=%d) after reading fen", file, rank));
    }

    // set defaults
    this.moveNumber = 1;
    this.enPassantSquare = SqNone;

    // everything below is optional as we can apply defaults

    // next player
    if (fenParts.length >= 2) {
      if (!fenParts[1].matches("([wb])")) {
        throw new IllegalArgumentException(String.format("fen next player contains invalid characters: %s", fenParts[1]));
      }
      switch (fenParts[1]) {
        case "w" -> this.nextPlayer = Color.White;
        case "b" -> {
          this.nextPlayer = Color.Black;
          this.zobristKey ^= Zobrist.nextPlayer_Zobrist;
        }
      }
    }

    // castling rights
    if (fenParts.length >= 3) {
      if (!fenParts[2].matches("^(K?Q?k?q?|-)$")) {
        throw new IllegalArgumentException(String.format("fen castling rights contains invalid characters: %s", fenParts[2]));
      }
      // are there  rights to be encoded?
      if (!fenParts[2].equals("-")) {
        for (char c : fenParts[2].toCharArray()) {
          switch (c) {
            case 'K' -> this.castlingRights = CastlingRights.addCastlingRight(this.castlingRights, CastlingRights.CastlingWhiteOO);
            case 'Q' -> this.castlingRights = CastlingRights.addCastlingRight(this.castlingRights, CastlingRights.CastlingWhiteOOO);
            case 'k' -> this.castlingRights = CastlingRights.addCastlingRight(this.castlingRights, CastlingRights.CastlingBlackOO);
            case 'q' -> this.castlingRights = CastlingRights.addCastlingRight(this.castlingRights, CastlingRights.CastlingBlackOOO);
          }
        }
      }
      this.zobristKey ^= Zobrist.castling_Zobrist[this.castlingRights];
    }

    // en passant
    if (fenParts.length >= 4) {
      if (!fenParts[3].matches("^([a-h][1-8]|-)$")) {
        throw new IllegalArgumentException(String.format("fen en passant contains invalid characters: %s", fenParts[3]));
      }
      if (!fenParts[3].equals("-")) {
        this.enPassantSquare = makeSquare(fenParts[3]);
      }
    }

    // half move clock (50 moves rule)
    if (fenParts.length >= 5) {
      try {
        halfMoveClock = Integer.parseInt(fenParts[4]);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(String.format("fen half move clock is not a number: %s", fenParts[4]));
      }
    }

    // move number
    if (fenParts.length >= 6) {
      try {
        moveNumber = Integer.parseInt(fenParts[4]);
        if (moveNumber == 0) moveNumber = 1;
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(String.format("fen move number is not a number: %s", fenParts[5]));
      }
    }

  }

}
