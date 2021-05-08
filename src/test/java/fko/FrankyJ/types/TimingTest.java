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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

public class TimingTest {

  @Test
  @Disabled
  void TimingEnumVsConst() {
    final int rounds = 10;
    final long iterations = 10_000_000_000L;

    long res = 0;

    for (int r = 1; r <= rounds; r++) {
      System.out.println("Round " + r);
      long start = System.nanoTime();
      for (long i = 0; i < iterations; i++) {
        res = File.a.ordinal();
      }
      long stop = System.nanoTime();
      long elapsed = stop - start;
      System.out.println("Test took    " + String.format("%,d", elapsed) + " ns for " + String.format("%,d", iterations) + " iterations");
      System.out.println("Test took    " + String.format("%,d", (elapsed / iterations)) + " ns per test");
      System.out.println("Test per sec " + String.format("%,d", (long)(iterations * 1e9) / elapsed) + " tps");
    }

    for (int r = 1; r <= rounds; r++) {
      System.out.println("Round " + r);
      long start = System.nanoTime();
      for (long i = 0; i < iterations; i++) {
        res = Bitboard.BbZero;
      }
      long stop = System.nanoTime();
      long elapsed = stop - start;
      System.out.println("Test took    " + String.format("%,d", elapsed) + " ns for " + String.format("%,d", iterations) + " iterations");
      System.out.println("Test took    " + String.format("%,d", (elapsed / iterations)) + " ns per test");
      System.out.println("Test per sec " + String.format("%,d", (long)(iterations * 1e9) / elapsed) + " tps");
    }

    System.out.println(res);
  }

  @Test
  @Disabled
  void enumMap() {
    final int rounds = 5;
    final long iterations = 1_000_000L;

    Piece res = Piece.PieceNone;
    Piece[] board = new Piece[64];
    EnumMap<Square, Piece> board2 = new EnumMap<>(Square.class);

    for (int r = 1; r <= rounds; r++) {
      System.out.println("Round " + r);
      long start = System.nanoTime();
      for (long i = 0; i < iterations; i++) {
        for (Square sq : Square.values) {
          if (sq == Square.SqNone) continue;
          board[sq.ordinal()] = Piece.WhiteKing;
        }
        for (Square sq : Square.values) {
          if (sq == Square.SqNone) continue;
          board[sq.ordinal()] = Piece.BlackKing;
        }
        for (Square sq : Square.values) {
          if (sq == Square.SqNone) continue;
          res = board[sq.ordinal()];
        }
      }
      long stop = System.nanoTime();
      long elapsed = stop - start;
      System.out.println("Test took    " + String.format("%,d", elapsed) + " ns for " + String.format("%,d", iterations) + " iterations");
      System.out.println("Test took    " + String.format("%,d", (elapsed / iterations)) + " ns per test");
      System.out.println("Test per sec " + String.format("%,d", (long)(iterations * 1e9) / elapsed) + " tps");
    }

    System.out.println();

    for (int r = 1; r <= rounds; r++) {
      System.out.println("Round " + r);
      long start = System.nanoTime();
      for (long i = 0; i < iterations; i++) {
        final int sqnone = Square.SqNone.ordinal();
        for (int sq = 0; sq < sqnone; sq++) {
          board[sq] = Piece.WhiteKing;
        }
        for (int sq = 0; sq < sqnone; sq++) {
          board[sq] = Piece.BlackKing;
        }
        for (int sq = 0; sq < sqnone; sq++) {
          res = board[sq];
        }
      }
      long stop = System.nanoTime();
      long elapsed = stop - start;
      System.out.println("Test took    " + String.format("%,d", elapsed) + " ns for " + String.format("%,d", iterations) + " iterations");
      System.out.println("Test took    " + String.format("%,d", (elapsed / iterations)) + " ns per test");
      System.out.println("Test per sec " + String.format("%,d", (long)(iterations * 1e9) / elapsed) + " tps");
    }

    System.out.println();

    for (int r = 1; r <= rounds; r++) {
      System.out.println("Round " + r);
      long start = System.nanoTime();
      for (long i = 0; i < iterations; i++) {
        for (Square sq : Square.values) {
          if (sq == Square.SqNone) continue;
          board2.put(sq, Piece.WhiteKing);
        }
        for (Square sq : Square.values) {
          if (sq == Square.SqNone) continue;
          board2.put(sq, Piece.BlackKing);
        }
        for (Square sq : Square.values) {
          if (sq == Square.SqNone) continue;
          res = board2.get(sq);
        }
      }
      long stop = System.nanoTime();
      long elapsed = stop - start;
      System.out.println("Test took    " + String.format("%,d", elapsed) + " ns for " + String.format("%,d", iterations) + " iterations");
      System.out.println("Test took    " + String.format("%,d", (elapsed / iterations)) + " ns per test");
      System.out.println("Test per sec " + String.format("%,d", (long)(iterations * 1e9) / elapsed) + " tps");
    }

    System.out.println(res);
  }
}
