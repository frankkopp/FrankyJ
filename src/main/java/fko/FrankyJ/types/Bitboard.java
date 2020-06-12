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

// Bitboards are 64 bit long with 1 bit for each square on the board.
public class Bitboard {

  private static final long NotMostSignificantBitMask = ~(1L << 63);
  private static final long NotRank8Mask              = ~Rank.r8.Bb;
  private static final long NotFileAMask              = ~File.a.Bb;
  private static final long NotFileHMask              = ~File.h.Bb;

  public  static final long BbZero = 0L;
  public  static final long BbOne  = 1L;
  public  static final long BbAll  = ~0L;

  private Bitboard() {
  }
  /**
   * PushSquare sets the corresponding bit of the bitboard for the square
   */
  static public long pushSquare(long bitboard, Square sq) {
    return bitboard | sq.Bb;
  }

  /**
   * PopSquare removes the corresponding bit of the bitboard for the square
   */
  static public long popSquare(long bitboard, Square sq) {
    return bitboard & ~sq.Bb;
  }

  /**
   * Has tests if a square (bit) is set
   */
  static public boolean has(long bitboard, Square sq) {
    return (bitboard & sq.Bb) != 0;
  }

  /**
   * ShiftBitboard shifting all bits of a bitboard in the given direction by 1 square
   */
  static public long shiftBitboard(long bitboard, Direction d) {
    // move the bits and clear the left our right file
    // after the shift to erase bits jumping over
    return switch (d) {
      case North -> (NotRank8Mask & bitboard) << 8;
      case East -> (NotMostSignificantBitMask & bitboard) << 1 & NotFileAMask;
      case South -> bitboard >>> 8;
      case West -> (bitboard >>> 1) & NotFileHMask;
      case Northeast -> (NotRank8Mask & bitboard) << 9 & NotFileAMask;
      case Southeast -> (bitboard >>> 7) & NotFileAMask;
      case Southwest -> (bitboard >>> 9) & NotFileHMask;
      case Northwest -> (bitboard << 7) & NotFileHMask;
    };
  }

  /**
  * Lsb returns the least significant bit of the 64-bit Bb.
  * This translates directly to the Square which is returned.
  * If the bitboard is empty SqNone will be returned.
  * Lsb() indexes from 0-63 - 0 being the the lsb and
  * equal to SqA1
  */
  static public Square lsb(long bitboard) {
    return Square.getSquare(Long.numberOfTrailingZeros(bitboard));
  }

/**
 * Msb returns the most significant bit of the 64-bit Bb.
 * This translates directly to the Square which is returned.
 * If the bitboard is empty SqNone will be returned.
 * Msb() indexes from 0-63 - 63 being the the msb and
 * equal to SqH8
 */
  static public Square msb(long bitboard) {
    return Square.getSquare(63 - Long.numberOfLeadingZeros(bitboard));
  }

/**
 * PopCount returns the number of one bits ("population count") in bitboard.
 * This equals the number of squares set in a Bitboard
 */
  static public int popCount(long bitboard) {
    return Long.bitCount(bitboard);
  }

  /**
   * returns a string representation of the 64 bits
   */
  static public String toString(long bitboard) {
    return "0".repeat(Long.numberOfLeadingZeros(bitboard)) + Long.toBinaryString(bitboard);
  }

  /**
   * returns a string representation of the Bb
   * as a board off 8x8 squares
   */
  static public String toBoardString(long bitboard) {
    StringBuilder sb = new StringBuilder();
    sb.append("+---+---+---+---+---+---+---+---+\n");
    for (int r = 7; r >= 0; --r) {
      for (int f = 0; f <= 7; ++f) {
        sb.append(
          Bitboard.has(bitboard, Square.getSquare(File.getFile(f), Rank.getRank(r))) ? "| X " : "|   ");
      }
      sb.append("|\n+---+---+---+---+---+---+---+---+\n");
    }
    return sb.toString();
  }

  /**
   * StringGrouped returns a string representation of the 64 bits grouped in 8.
   * Order is LSB to msb ==> A1 B1 ... G8 H8
   */
  static public String toGroupedString(long bitboard) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 64; i++) {
      if (i > 0 && i % 8 == 0) {
        sb.append(".");
      }
      sb.append(Bitboard.has(bitboard, Square.getSquare(i)) ? "1" : "0");
    }
    sb.append(" (" + bitboard + ")");
    return sb.toString();
  }


  static {

  }

}
