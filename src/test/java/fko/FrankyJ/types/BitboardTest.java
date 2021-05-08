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

import org.junit.jupiter.api.Test;

import static fko.FrankyJ.types.Bitboard.*;
import static fko.FrankyJ.types.Direction.*;
import static fko.FrankyJ.types.Square.*;
import static org.junit.jupiter.api.Assertions.*;

class BitboardTest {

  @Test
  void pushSquare() {
    assertEquals(Bitboard.BbZero | SqE4.Bb, Bitboard.pushSquare(Bitboard.BbZero, SqE4));
  }

  @Test
  void popSquare() {
    assertEquals(Bitboard.BbZero & ~SqE4.Bb, Bitboard.popSquare(Bitboard.BbZero, SqE4));
  }

  @Test
  void has() {
    assertTrue(Bitboard.has(Bitboard.pushSquare(Bitboard.BbZero, SqE4), SqE4));
    assertFalse(Bitboard.has(Bitboard.pushSquare(Bitboard.BbZero, SqE4), Square.SqE5));
  }

  @Test
  void shiftBitboard() {
    assertEquals(SqE5.Bb, Bitboard.shiftBitboard(SqE4.Bb, North));
    assertEquals(SqA1.Bb, Bitboard.shiftBitboard(SqA2.Bb, South));
    assertEquals(SqH1.Bb, Bitboard.shiftBitboard(SqG1.Bb, East));
    assertEquals(SqF1.Bb, Bitboard.shiftBitboard(SqG1.Bb, West));
    assertEquals(SqB2.Bb, Bitboard.shiftBitboard(SqA1.Bb, Northeast));
    assertEquals(SqA1.Bb, Bitboard.shiftBitboard(SqB2.Bb, Southwest));
    assertEquals(SqNone.Bb, Bitboard.shiftBitboard(SqA1.Bb, Southwest));
    assertEquals(SqNone.Bb, Bitboard.shiftBitboard(SqH8.Bb, Northeast));
    assertEquals(SqNone.Bb, Bitboard.shiftBitboard(SqA8.Bb, Northwest));
    assertEquals(SqNone.Bb, Bitboard.shiftBitboard(SqE1.Bb, South));
    assertEquals(SqNone.Bb, Bitboard.shiftBitboard(SqH8.Bb, North));
    assertEquals(SqNone.Bb, Bitboard.shiftBitboard(SqH8.Bb, East));
  }

  @Test
  void lsb_msb() {
    assertEquals(SqA1, Bitboard.lsb(SqA1.Bb));
    assertEquals(SqA1, Bitboard.msb(SqA1.Bb));
    assertEquals(SqH8, Bitboard.lsb(SqH8.Bb));
    assertEquals(SqH8, Bitboard.msb(SqH8.Bb));
    assertEquals(SqA1, Bitboard.lsb(SqH8.Bb | SqA1.Bb));
    assertEquals(SqH8, Bitboard.msb(SqH8.Bb | SqA1.Bb));
  }

  @Test
  void popLsb_popCount() {
    long b = BbAll;
    int counter = 0;
    while (b != BbZero) {
      final Square sq = lsb(b);
      assertEquals(counter, sq.ordinal());
      assertEquals(64 - counter, Bitboard.popCount(b));
      b = Bitboard.popSquare(b, sq);
      counter++;
    }
    assertEquals(64, counter);
  }

  @Test
  void testToString() {
    assertEquals("0000000000000000000000000000000000000000000000000000000011111111",
                 Bitboard.toString(Rank.r1.Bb));
    assertEquals("0000000100000001000000010000000100000001000000010000000100000001",
                 Bitboard.toString(File.a.Bb));
  }

  @Test
  void toBoardString() {
    // @formatter:off
    assertEquals(  "+---+---+---+---+---+---+---+---+\n"
                 + "|   |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "|   |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "|   |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "|   |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "|   |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "|   |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "|   |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X | X | X | X | X | X | X | X |\n"
                 + "+---+---+---+---+---+---+---+---+\n", Bitboard.toBoardString(Rank.r1.Bb));

    assertEquals(  "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n"
                 + "| X |   |   |   |   |   |   |   |\n"
                 + "+---+---+---+---+---+---+---+---+\n", Bitboard.toBoardString(File.a.Bb));
    // @formatter:on
  }

  @Test
  void toGroupedString() {
    assertEquals("11111111.00000000.00000000.00000000.00000000.00000000.00000000.00000000 (255)",
                 Bitboard.toGroupedString(Rank.r1.Bb));
    assertEquals("10000000.10000000.10000000.10000000.10000000.10000000.10000000.10000000 (72340172838076673)",
                 Bitboard.toGroupedString(File.a.Bb));
  }
}
