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

package fko.FrankyJ.types;

import static fko.FrankyJ.types.Color.*;
import static fko.FrankyJ.types.PieceType.*;

/**
 * All pieces of a chess game
 */
public enum Piece {
  // @formatter:off
  PieceNone      (NoColor, PtNone, '-', '-', "-"),
  WhiteKing      (White,   King  , 'K', 'K', "♔"),
  WhitePawn      (White,   Pawn  , 'P', 'P', "♙"),
  WhiteKnight    (White,   Knight, 'N', 'N', "♘"),
  WhiteBishop    (White,   Bishop, 'B', 'B', "♗"),
  WhiteRook      (White,   Rook  , 'R', 'R', "♖"),
  WhiteQueen     (White,   Queen , 'Q', 'Q', "♕"),
  BlackKing      (Black,   King  , 'k', 'k', "-"),
  BlackPawn      (Black,   Pawn  , 'p', '*', " "),
  BlackKnight    (Black,   Knight, 'n', 'n', "♚"),
  BlackBishop    (Black,   Bishop, 'b', 'b', "♟"),
  BlackRook      (Black,   Rook  , 'r', 'r', "♞"),
  BlackQueen     (Black,   Queen , 'q', 'q', "♝");
  // @formatter:on

  // pre-filled list with all squares
  public static final Piece[] values;

  static {
    values = Piece.values();
  }

  public final Color     color;
  public final PieceType pieceType;
  public final char      symbol;
  public final char      symbol2;
  public final String    unicodeSymbol;

  Piece(final Color color, final PieceType pieceType, final char symbol, final char symbol2, final String unicodeSymbol) {
    this.color = color;
    this.pieceType = pieceType;
    this.symbol = symbol;
    this.symbol2 = symbol2;
    this.unicodeSymbol = unicodeSymbol;
  }

  public static Piece getPiece(Color c, PieceType pt) {
    return Piece.values[c.ordinal() * 6 + pt.ordinal()];
  }

  public static Piece getPiece(final char string) {
    return switch (string) {
      case 'K' -> WhiteKing;
      case 'P' -> WhitePawn;
      case 'N' -> WhiteKnight;
      case 'B' -> WhiteBishop;
      case 'R' -> WhiteRook;
      case 'Q' -> WhiteQueen;
      case 'k' -> BlackKing;
      case 'p' -> BlackPawn;
      case 'n' -> BlackKnight;
      case 'b' -> BlackBishop;
      case 'r' -> BlackRook;
      case 'q' -> BlackQueen;
      default -> PieceNone;
    };
  }
}
