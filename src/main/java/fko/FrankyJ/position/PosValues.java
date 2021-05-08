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

import fko.FrankyJ.types.Piece;
import fko.FrankyJ.types.Square;

import static fko.FrankyJ.position.Position.GAME_PHASE_MAX;

public class PosValues {

  public static int[][]   posMidValue = new int[Piece.values.length][Square.values.length];
  public static int[][]   posEndValue = new int[Piece.values.length][Square.values.length];
  public static int[][][] posValue    = new int[Piece.values.length][Square.values.length][GAME_PHASE_MAX + 1];

  // @formatter:off
  // PAWN Tables
  private static final int[] pawnsMidGame = {
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  5,  5,  5,  5,  5,  5,  0,
      5,  5, 10, 30, 30, 10,  5,  5,
      0,  0,  0, 25, 25,  0,  0,  0,
      5, -5,-10,  0,  0,-10, -5,  5,
      5, 10, 10,-30,-30, 10, 10,  5,
      0,  0,  0,  0,  0,  0,  0,  0
  };
  private static final int[] pawnsEndGame = {
      0,  0,  0,  0,  0,  0,  0,  0,
      90, 90, 90, 90, 90, 90, 90, 90,
      40, 50, 50, 60, 60, 50, 50, 40,
      20, 30, 30, 40, 40, 30, 30, 20,
      10, 10, 20, 20, 20, 10, 10, 10,
      5, 10, 10, 10, 10, 10, 10,  5,
      5, 10, 10, 10, 10, 10, 10,  5,
      0,  0,  0,  0,  0,  0,  0,  0
  };

  // KNIGHT Tables
  private static final int[] knightMidGame = {
    -50,-40,-30,-30,-30,-30,-40,-50,
      -40,-20,  0,  0,  0,  0,-20,-40,
      -30,  0, 10, 15, 15, 10,  0,-30,
      -30,  5, 15, 20, 20, 15,  5,-30,
      -30,  0, 15, 20, 20, 15,  0,-30,
      -30,  5, 10, 15, 15, 10,  5,-30,
      -40,-20,  0,  5,  5,  0,-20,-40,
      -50,-40,-20,-30,-30,-20,-40,-50,
  };
  private static final int[] knightEndGame = {
    -50,-40,-30,-30,-30,-30,-40,-50,
      -40,-20,  0,  0,  0,  0,-20,-40,
      -30,  0, 10, 15, 15, 10,  0,-30,
      -30,  0, 15, 20, 20, 15,  0,-30,
      -30,  0, 15, 20, 20, 15,  0,-30,
      -30,  0, 10, 15, 15, 10,  0,-30,
      -40,-20,  0,  0,  0,  0,-20,-40,
      -50,-40,-20,-30,-30,-20,-40,-50,
  };

  // BISHOP Tables
  private static final int[] bishopMidGame = {
    -20,-10,-10,-10,-10,-10,-10,-20,
      -10,  0,  0,  0,  0,  0,  0,-10,
      -10,  0,  5, 10, 10,  5,  0,-10,
      -10,  5,  5, 10, 10,  5,  5,-10,
      -10,  0, 10, 10, 10, 10,  0,-10,
      -10, 10, 10, 10, 10, 10, 10,-10,
      -10,  5,  0,  0,  0,  0,  5,-10,
      -20,-10,-40,-10,-10,-40,-10,-20,
  };
  private static final int[] bishopEndGame = {
    -20,-10,-10,-10,-10,-10,-10,-20,
      -10,  0,  0,  0,  0,  0,  0,-10,
      -10,  0,  5,  5,  5,  5,  0,-10,
      -10,  0,  5, 10, 10,  5,  0,-10,
      -10,  0,  5, 10, 10,  5,  0,-10,
      -10,  0,  5,  5,  5,  5,  0,-10,
      -10,  0,  0,  0,  0,  0,  0,-10,
      -20,-10,-10,-10,-10,-10,-10,-20,
  };

  // ROOK Tables
  private static final int[] rookMidGame  = {
    5,  5,  5,  5,  5,  5,  5,  5,
      10, 10, 10, 10, 10, 10, 10, 10,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      -15,-10, 15, 15, 15, 15,-10,-15,
  };
  private static final int[] rookEndGame  = {
    5,  5,  5,  5,  5,  5,  5,  5,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
      0,  0,  0,  0,  0,  0,  0,  0,
  };

  // Queen Tables
  private static final int[] queenMidGame = {
    -20,-10,-10, -5, -5,-10,-10,-20,
      -10,  0,  0,  0,  0,  0,  0,-10,
      -10,  0,  0,  0,  0,  0,  0,-10,
      -5,  0,  2,  2,  2,  2,  0, -5,
      -5,  0,  5,  5,  5,  5,  0, -5,
      -10,  0,  5,  5,  5,  5,  0,-10,
      -10,  0,  5,  5,  5,  5,  0,-10,
      -20,-10,-10, -5, -5,-10,-10,-20
  };
  private static final int[] queenEndGame = {
    -20,-10,-10, -5, -5,-10,-10,-20,
      -10,  0,  0,  0,  0,  0,  0,-10,
      -10,  0,  5,  5,  5,  5,  0,-10,
      -5,  0,  5,  5,  5,  5,  0, -5,
      -5,  0,  5,  5,  5,  5,  0, -5,
      -10,  0,  5,  5,  5,  5,  0,-10,
      -10,  0,  0,  0,  0,  0,  0,-10,
      -20,-10,-10, -5, -5,-10,-10,-20
  };

  // King Tables
  private static final int[] kingMidGame  = {
    -30,-40,-40,-50,-50,-40,-40,-30,
      -30,-40,-40,-50,-50,-40,-40,-30,
      -30,-40,-40,-50,-50,-40,-40,-30,
      -30,-40,-40,-50,-50,-40,-40,-30,
      -20,-30,-30,-40,-40,-30,-30,-20,
      -10,-20,-20,-30,-30,-30,-20,-10,
      0,  0,-20,-20,-20,-20,  0,  0,
      20, 50,  0,-20,-20,  0, 50, 20
  };
  private static final int[] kingEndGame  = {
    -50,-30,-30,-20,-20,-30,-30,-50,
      -30,-20,-10,  0,  0,-10,-20,-30,
      -30,-10, 20, 30, 30, 20,-10,-30,
      -30,-10, 30, 40, 40, 30,-10,-30,
      -30,-10, 30, 40, 40, 30,-10,-30,
      -30,-10, 20, 30, 30, 20,-10,-30,
      -30,-30,  0,  0,  0,  0,-30,-30,
      -50,-30,-30,-30,-30,-30,-30,-50
  };
  // @formatter:on

  private static int calcPosValueWhite(Square sq, int gamePhase, int[] posMidTable, int[] posEndTable) {
    return (gamePhase * posMidTable[63 - sq.ordinal()] + (GAME_PHASE_MAX - gamePhase) * posEndTable[63 - sq.ordinal()]) / GAME_PHASE_MAX;
  }

  private static int calcPosValueBlack(Square sq, int gamePhase, int[] posMidTable, int[] posEndTable) {
    return (gamePhase * posMidTable[sq.ordinal()] + (GAME_PHASE_MAX - gamePhase) * posEndTable[sq.ordinal()]) / GAME_PHASE_MAX;
  }

  static {
    // pre-compute piece on square values for mid and endgame and also for
    // all game phases
    for (Piece pc : Piece.values) {
      for (Square sq : Square.values) {
        if (sq == Square.SqNone) continue;
        for (int gp = GAME_PHASE_MAX; gp >= 0; gp--) {
          switch (pc) {
            case WhiteKing -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = kingMidGame[63 - sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = kingEndGame[63 - sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueWhite(sq, gp, kingMidGame, kingEndGame);
            }
            case WhitePawn -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = pawnsMidGame[63 - sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = pawnsEndGame[63 - sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueWhite(sq, gp, pawnsMidGame, pawnsEndGame);
            }
            case WhiteKnight -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = knightMidGame[63 - sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = knightEndGame[63 - sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueWhite(sq, gp, knightMidGame, knightEndGame);
            }
            case WhiteBishop -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = bishopMidGame[63 - sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = bishopEndGame[63 - sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueWhite(sq, gp, bishopMidGame, bishopEndGame);
            }
            case WhiteRook -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = rookMidGame[63 - sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = rookEndGame[63 - sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueWhite(sq, gp, rookMidGame, rookEndGame);
            }
            case WhiteQueen -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = queenMidGame[63 - sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = queenEndGame[63 - sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueWhite(sq, gp, queenMidGame, queenEndGame);
            }
            case BlackKing -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = kingMidGame[sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = kingEndGame[sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueBlack(sq, gp, kingMidGame, kingEndGame);
            }
            case BlackPawn -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = pawnsMidGame[sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = pawnsEndGame[sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueBlack(sq, gp, pawnsMidGame, pawnsEndGame);
            }
            case BlackKnight -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = knightMidGame[sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = knightEndGame[sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueBlack(sq, gp, knightMidGame, knightEndGame);
            }
            case BlackBishop -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = bishopMidGame[sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = bishopEndGame[sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueBlack(sq, gp, bishopMidGame, bishopEndGame);
            }
            case BlackRook -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = rookMidGame[sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = rookEndGame[sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueBlack(sq, gp, rookMidGame, rookEndGame);
            }
            case BlackQueen -> {
              posMidValue[pc.ordinal()][sq.ordinal()] = queenMidGame[sq.ordinal()];
              posEndValue[pc.ordinal()][sq.ordinal()] = queenEndGame[sq.ordinal()];
              posValue[pc.ordinal()][sq.ordinal()][gp] = calcPosValueBlack(sq, gp, queenMidGame, queenEndGame);
            }
          }
        }
      }
    }
  }

}
