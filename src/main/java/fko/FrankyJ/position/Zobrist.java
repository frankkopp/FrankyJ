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

import fko.FrankyJ.types.CastlingRights;
import fko.FrankyJ.types.File;
import fko.FrankyJ.types.Piece;
import fko.FrankyJ.types.Square;

import java.util.Random;

public class Zobrist {
  // Zobrist key constants
  static final long[][] pieceZobrist          = new long[Piece.values.length][Square.values.length];
  static final long[]   castling_Zobrist      = new long[CastlingRights.CastlingLength];
  static final long[]   enPassantFile_Zobrist = new long[File.values.length];
  static final long     nextPlayer_Zobrist;

  // Static Initialization for zobrist key generation
  static {
    Random random = new Random(0);
    // all pieces on all squares
    for (Piece p : Piece.values) {
      for (Square s : Square.values) {
        pieceZobrist[p.ordinal()][s.ordinal()] = Math.abs(random.nextLong());
      }
    }
    // all castling combinations
    for (int cr = 0; cr < CastlingRights.CastlingLength; cr++) {
      castling_Zobrist[cr] = Math.abs(random.nextLong());
    }
    // all possible positions of the enpassant
    for (File f : File.values) {
      enPassantFile_Zobrist[f.ordinal()] = Math.abs(random.nextLong());
    }
    // set or unset this for the two color options
    nextPlayer_Zobrist = Math.abs(random.nextLong());
  }

  private Zobrist() {
  }
}
