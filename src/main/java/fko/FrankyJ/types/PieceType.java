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

// PieceType is a set of enums for piece types in chess
public enum PieceType {

  // @formatter:off
  PtNone   (false, 0,    0, "PtNone", '-'),
  King     (false, 0, 2000,   "King", 'K'),
  Pawn     (false, 0,  100,   "Pawn", 'P'),
  Knight   (false, 1,  320, "Knight", 'N'),
  Bishop   (true,  1,  330, "Bishop", 'B'),
  Rook     (true,  2,  500,   "Rook", 'R'),
  Queen    (true,  4,  900,  "Queen", 'Q');
  // @formatter:on

  // pre-filled list with all squares
  public static final PieceType[] values;

  static {
    values = PieceType.values();
  }

  public final boolean sliding;
  public final int gamePhaseValue;
  public final int value;
  public final String name;
  public final char symbol;

  PieceType(final boolean sliding, final int gamePhaseValue, final int value, final String name, final char symbol) {
    this.sliding = sliding;
    this.gamePhaseValue = gamePhaseValue;
    this.value = value;
    this.name = name;
    this.symbol = symbol;
  }

  public static PieceType getPieceType(int pt) {
    if (pt > Queen.ordinal()) return PtNone;
    return PieceType.values[pt];
  }

}
