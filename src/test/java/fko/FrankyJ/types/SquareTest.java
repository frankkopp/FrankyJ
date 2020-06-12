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

import static fko.FrankyJ.types.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SquareTest {

  @Test
  void getSquareTest() {
    assertEquals(SqA1, getSquare(0));
    assertEquals(SqH8, getSquare(63));
    assertEquals(SqNone, getSquare(64));
    assertEquals(SqNone, getSquare(99));
    assertEquals(SqA1, getSquare(File.getFile(0), Rank.getRank(0)));
    assertEquals(SqA1, getSquare(File.a, Rank.r1));
    assertEquals(SqNone, getSquare(File.NoFile, Rank.r1));
  }

  @Test
  void getSquareFileRankTest() {
    assertEquals(File.a, SqA1.file);
    assertEquals(Rank.r1, SqA1.rank);
    assertEquals(Rank.NoRank, SqNone.rank);
  }

  @Test
  void bitboard() {
    assertEquals(1L, SqA1.Bb);
    assertEquals(1L << 63, SqH8.Bb);
    assertEquals(0b10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000L, SqH8.Bb);
  }

  @Test
  void to() {
    assertEquals(SqA2, SqA1.to(Direction.North));
    assertEquals(SqB1, SqA1.to(Direction.East));
    assertEquals(SqNone, SqA1.to(Direction.West));
    assertEquals(SqE5, SqE4.to(Direction.North));
    assertEquals(SqF5, SqE4.to(Direction.Northeast));
    assertEquals(SqF4, SqE4.to(Direction.East));
    assertEquals(SqF3, SqE4.to(Direction.Southeast));
    assertEquals(SqE3, SqE4.to(Direction.South));
    assertEquals(SqD3, SqE4.to(Direction.Southwest));
    assertEquals(SqD4, SqE4.to(Direction.West));
    assertEquals(SqD5, SqE4.to(Direction.Northwest));
  }

  @Test
  void pawnPush() {
    assertEquals(SqA3, SqA2.pawnPush(Color.White));
    assertEquals(SqA1, SqA2.pawnPush(Color.Black));
    assertEquals(SqNone, SqA1.pawnPush(Color.Black));
  }

  @Test
  void string() {
    assertEquals("a1", SqA1.toString());
    assertEquals("h8", SqH8.toString());
    assertEquals("--", SqNone.toString());
  }

}
