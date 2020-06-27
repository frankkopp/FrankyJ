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

import fko.FrankyJ.types.Move;
import fko.FrankyJ.types.MoveType;
import fko.FrankyJ.types.PieceType;
import org.junit.jupiter.api.Test;

import static fko.FrankyJ.types.Color.Black;
import static fko.FrankyJ.types.Color.White;
import static fko.FrankyJ.types.Piece.*;
import static fko.FrankyJ.types.Square.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionTest {

  @Test
  void Constructor() {
    Position p = new Position();
    assertEquals(White, p.nextPlayer);

    p = new Position("r3k2r/1ppn3p/2q1q1n1/8/2q1Pp2/6R1/p1p2PPP/1R4K1 b kq e3 10 113");
    assertEquals(Black, p.nextPlayer);
    assertEquals(SqE3, p.enPassantSquare);
    assertEquals(WhiteKing, p.board[SqG1.ordinal()]);
    assertEquals(BlackKing, p.board[SqE8.ordinal()]);
    assertEquals(WhiteRook, p.board[SqG3.ordinal()]);
    assertEquals(BlackQueen, p.board[SqC6.ordinal()]);

  }

  @Test
  void doUndo() {
    Position p = new Position("r3k2r/1ppn3p/2q1q1n1/8/2q1Pp2/6R1/p1p2PPP/1R4K1 b kq e3 10 113");
    assertEquals(Black, p.nextPlayer);
    assertEquals(SqE3, p.enPassantSquare);
    assertEquals(WhiteKing, p.board[SqG1.ordinal()]);
    assertEquals(BlackKing, p.board[SqE8.ordinal()]);
    assertEquals(WhiteRook, p.board[SqG3.ordinal()]);
    assertEquals(BlackQueen, p.board[SqC6.ordinal()]);
    p.doMove(Move.createMove(SqC2, SqB1, MoveType.Promotion, PieceType.Queen));
    p.undoMove();
    assertEquals(Black, p.nextPlayer);
    assertEquals(SqE3, p.enPassantSquare);
    assertEquals(WhiteKing, p.board[SqG1.ordinal()]);
    assertEquals(BlackKing, p.board[SqE8.ordinal()]);
    assertEquals(WhiteRook, p.board[SqG3.ordinal()]);
    assertEquals(BlackQueen, p.board[SqC6.ordinal()]);
  }

  @Test
  void PositionTimingTest() {
    final int rounds = 10;
    final long iterations = 20_000_000L;

    Position p = new Position("r3k2r/1ppn3p/4q1n1/8/4Pp2/3R4/p1p2PPP/R5K1 b kq e3 0 1");
    final int move1 = Move.createMove(SqF4, SqE3, MoveType.EnPassant);
    final int move2 = Move.createMove(SqF2, SqE3);
    final int move3 = Move.createMove(SqE8, SqG8, MoveType.Castling);
    final int move4 = Move.createMove(SqD3, SqC3);
    final int move5 = Move.createMove(SqC2, SqC1, MoveType.Promotion, PieceType.Queen);

    for (int r = 1; r <= rounds; r++) {
      System.out.println("Round " + r);
      long start = System.nanoTime();
      for (long i = 0; i < iterations; i++) {
        p.doMove(move1);
        p.doMove(move2);
        p.doMove(move3);
        p.doMove(move4);
        p.doMove(move5);
        p.undoMove();
        p.undoMove();
        p.undoMove();
        p.undoMove();
        p.undoMove();
      }
      long stop = System.nanoTime();
      long elapsed = stop - start;
      System.out.println("Test took    " + String.format("%,d", elapsed) + " ns for " + String.format("%,d", iterations) + " iterations");
      System.out.println("Test took    " + String.format("%,d", (elapsed / (5 * iterations))) + " ns per test");
      System.out.println("Test per sec " + String.format("%,d", (long) (iterations * 5 * 1e9) / elapsed) + " tps");
    }
  }
}
