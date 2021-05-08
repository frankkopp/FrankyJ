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


import static fko.FrankyJ.types.MoveType.Normal;
import static fko.FrankyJ.types.MoveType.Promotion;

// Move is a 32bit int type for encoding chess moves as a primitive data type
// 16 bits for move encoding - 16 bits for sort value
//  MoveNone Move = 0
//  BITMAP 32-bit
//  |-value ------------------------|-Move -------------------------|
//  3 3 2 2 2 2 2 2 2 2 2 2 1 1 1 1 | 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0 0
//  1 0 9 8 7 6 5 4 3 2 1 0 9 8 7 6 | 5 4 3 2 1 0 9 8 7 6 5 4 3 2 1 0
//  --------------------------------|--------------------------------
//                                  |                     1 1 1 1 1 1  to
//                                  |         1 1 1 1 1 1              from
//                                  |     1 1                          promotion piece type (pt-2 > 0-3)
//                                  | 1 1                              move type
//  1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 |                                  move sort value
public class Move {

  public static final int NoMove = 0;

  public static int createMove(final Square from, final Square to) {
    return createMove(from, to, Normal, PieceType.PtNone, Value.ValueNA);
  }

  public static int createMove(final Square from, final Square to, final MoveType mt) {
    return createMove(from, to, mt, PieceType.PtNone, Value.ValueNA);
  }

  public static int createMove(final Square from, final Square to, final MoveType mt, final int value) {
    return createMove(from, to, mt, PieceType.PtNone, value);
  }

  public static int createMove(final Square from, final Square to, final MoveType mt, final PieceType pt) {
    return createMove(from, to, mt, pt, Value.ValueNA);
  }

  /**
   * Creates a move int by encoding the different parts bit wise into the int.
   * Is overloaded for convenience with different sets of arguments.
   */
  public static int createMove(final Square from, final Square to, final MoveType mt, final PieceType pt, final int value) {
    int prom = 0;
    if (pt.ordinal() > PieceType.Knight.ordinal()) {
      prom = pt.ordinal() - PieceType.Knight.ordinal();
    }
    // promType will be reduced to 2 bits (4 values) Knight, Bishop, Rook, Queen
    // therefore we subtract the Knight value from the promType to get
    // value between 0 and 3 (0b00 - 0b11)
    return to.ordinal() |
           from.ordinal() << FROM_SHIFT |
           prom << PROM_TYPE_SHIFT |
           mt.ordinal() << MOVE_TYPE_SHIFT |
           (value - Value.ValueNA) << VALUE_SHIFT;
  }

  /**
   * returns the from square of the move
   */
  public static Square fromSquare(int move) {
    return Square.getSquare((move & FROM_MASK) >>> FROM_SHIFT);
  }

  /**
   * returns the target square of the move
   */
  public static Square toSquare(int move) {
    return Square.getSquare(move & TO_MASK);
  }

  /**
   * PromotionType returns the PieceType considered for promotion when
   * move type is also MoveType.Promotion.
   * Must be ignored when move type is not MoveType.Promotion.
   */
  public static PieceType promotionTypeOf(int move) {
    return PieceType.getPieceType(((move & PROM_TYPE_MASK) >>> PROM_TYPE_SHIFT) + PieceType.Knight.ordinal());
  }

  /**
   * MoveType returns the type of the move as defined in MoveType
   */
  public static MoveType typeOf(int move) {
    return MoveType.getMoveType((move & MOVE_TYPE_MASK) >>> MOVE_TYPE_SHIFT);
  }

  /**
   * return the move without any value (value = Value.ValueNA)
   */
  public static int moveOf(int move) {
    return move & MOVE_MASK;
  }

  /**
   * return the value encoded into the move.
   */
  public static int valueOf(int move) {
    return ((move & VALUE_MASK) >>> VALUE_SHIFT) + Value.ValueNA;
  }

  /**
   * return the value encoded into the move.
   */
  public static int setValueOf(int move, int value) {
    // can't store a value on MoveNone
    if (moveOf(move) == NoMove) return move;
    // when saving a value to a move we shift value to a positive integer
    // (0-VALUE_NONE) and encode it into the move. For retrieving we then shift
    // the value back to a range from VALUE_NONE to VALUE_INF
    return moveOf(move) | ((value - Value.ValueNA) << VALUE_SHIFT);
  }

  public static boolean validMove(int move) {
    return move != NoMove &&
           fromSquare(move) != Square.SqNone &&
           toSquare(move) != Square.SqNone &&
           (typeOf(move) != Promotion || (typeOf(move) == Promotion && promotionTypeOf(move) != PieceType.PtNone)) &&
           (valueOf(move) == Value.ValueNA || Value.validValue(valueOf(move)));
  }

  public static String toString(int move) {
    if (moveOf(move) == NoMove) return "no move";
    return fromSquare(move).toString() +
           toSquare(move).toString() +
           (typeOf(move) == Promotion ? promotionTypeOf(move).symbol : "");
  }

  public static String toVerboseString(int move) {
    if (moveOf(move) == NoMove) return "no move";
    return String.format("Move {to: %s from: %s type: %s %s value: %s (int: %d)}",
                         fromSquare(move), toSquare(move), typeOf(move).name(),
                         typeOf(move) == Promotion ? "prom: " + promotionTypeOf(move).name : "",
                         valueOf(move),
                         move);
  }

  public static String toBitString(int move) {
    StringBuilder sb = new StringBuilder();
    int i = 31;
    // value
    for (; i >= 16; i--) {
      sb.append((move & (1 << i)) != 0 ? "1" : "0");
    }
    sb.append(".");
    // move type
    for (; i >= 14; i--) {
      sb.append((move & (1 << i)) != 0 ? "1" : "0");
    }
    sb.append(".");
    // prom type
    for (; i >= 12; i--) {
      sb.append((move & (1 << i)) != 0 ? "1" : "0");
    }
    sb.append(".");
    // from
    for (; i >= 6; i--) {
      sb.append((move & (1 << i)) != 0 ? "1" : "0");
    }
    sb.append(".");
    // to
    for (; i >= 0; i--) {
      sb.append((move & (1 << i)) != 0 ? "1" : "0");
    }
    sb.append(" (value.movetype.promtype.from.to)");
    return sb.toString();
  }

  // Move bit shift and mask constants
  private static final int FROM_SHIFT      = 6;
  private static final int PROM_TYPE_SHIFT = 12;
  private static final int MOVE_TYPE_SHIFT = 14;
  private static final int VALUE_SHIFT     = 16;

  private static final int SQUARE_MASK    = 0b111111;
  private static final int TO_MASK        = SQUARE_MASK;
  private static final int FROM_MASK      = SQUARE_MASK << FROM_SHIFT;
  private static final int PROM_TYPE_MASK = 0b11 << PROM_TYPE_SHIFT;
  private static final int MOVE_TYPE_MASK = 0b11 << MOVE_TYPE_SHIFT;

  private static final int MOVE_MASK  = 0xFFFF;               // first 16-bit
  private static final int VALUE_MASK = 0xFFFF << VALUE_SHIFT;// second 16-bit
}
