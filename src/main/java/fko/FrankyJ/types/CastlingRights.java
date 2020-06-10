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

/**
 * CastlingRights encodes the castling state e.g. available castling
 * and defines functions to change this state
 * CastlingNone         CastlingRights = 0                                  // 0000
 * CastlingWhiteOO      CastlingRights = 1                                  // 0001
 * CastlingWhiteOOO                    = CastlingWhiteOO << 1               // 0010
 * CastlingWhite                       = CastlingWhiteOO | CastlingWhiteOOO // 0011
 * CastlingBlackOO                     = CastlingWhiteOO << 2               // 0100
 * CastlingBlackOOO                    = CastlingBlackOO << 1               // 1000
 * CastlingBlack                       = CastlingBlackOO | CastlingBlackOOO // 1100
 * CastlingAny                         = CastlingWhite | CastlingBlack      // 1111
 */
public class CastlingRights {

  public static final int CastlingNone     = 0;// 0b0000;
  public static final int CastlingWhiteOO  = 1;// 0b0001;
  public static final int CastlingWhiteOOO = CastlingWhiteOO << 1;// 0b0010;
  public static final int CastlingWhite    = CastlingWhiteOO | CastlingWhiteOOO;// 0b0011;
  public static final int CastlingBlackOO  = CastlingWhiteOO << 2;// 0b0100;
  public static final int CastlingBlackOOO = CastlingBlackOO << 1;// 0b1000;
  public static final int CastlingBlack    = CastlingBlackOO | CastlingBlackOOO;// 0b1100;
  public static final int CastlingAny      = CastlingWhite | CastlingBlack;// 0b1111;

  public static final int CastlingLength = 8;

  /**
   * checks if the state has the bit for the Castling right set and
   * therefore this castling is available
   */
  public static boolean hasCastlingRight(int cr1, int cr2) {
    return (cr1 & cr2) != 0;
  }

  /**
   *  Remove removes a castling right from the input state (deletes right)
   */
  public static int removeCastlingRight(int cr1, int cr2) {
    return cr1 & ~cr2;
  }

  /**
   *  Add adds a castling right ti the state
   */
  public static int addCastlingRight(int cr1, int cr2) {
    return cr1 | cr2;
  }

  public static String toString(int cr) {
    if (cr == CastlingNone) {
      return "-";
    }
    StringBuilder sb = new StringBuilder();
    if (hasCastlingRight(cr, CastlingWhiteOO)) {
      sb.append("K");
    }
    if (hasCastlingRight(cr, CastlingWhiteOOO)) {
     sb.append("Q");
    }
    if (hasCastlingRight(cr, CastlingBlackOO)) {
     sb.append("k");
    }
    if (hasCastlingRight(cr, CastlingBlackOOO)) {
     sb.append("q");
    }
    return sb.toString();
  }

}
