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
  * The Color class represents the two colors of a chess game and a
  * special color for empty fields (NONE).
  * This class can not be instantiated. It keeps public references
  * to the only possible instances White and Black
  * These instances are immutable. As it is not possible to have any
  * other instances of Colors the use of these instances is as fast
  * as if using an int.
  */
 public enum Color {

   // order has influence on Piece
   White(1),      // 0
   Black(-1),     // 1
   NoColor(0);    // 2

   /**
    * This is 1 for white and -1 for black. Useful in evaluation and pawn directions
    */
   public final int   pawnDir;
   private      Color opponentColor;
   private      char  shortName;

   Color(int pawnDir) {
     this.pawnDir = pawnDir;
   }

   public static final Color[] values = {White, Black};

   static {
     for (Color c : Color.values()) {
       switch (c) {
         case White -> {
           c.opponentColor = Black;
           c.shortName = 'w';
         }
         case Black -> {
           c.opponentColor = White;
           c.shortName = 'b';
         }
         case NoColor -> {
           c.opponentColor = NoColor;
           c.shortName = '-';
         }
       }
     }
   }

   /**
    * Returns the other Color.
    * @return int - as defined in Color
    */
   public Color flip() {
     return opponentColor;
   }

   /**
    * Returns a character to use for a String representation of the field.<br>
    * It accepts ChesslyColor.BLACK (X), ChesslyColor.WHITE (O), ChesslyColor.EMPTY (-) otherwise
    * returns
    * an empty character.
    * @return char - one of 'b', '-', 'w' or ' '
    */
   public char toChar() {
     return shortName;
   }

   /**
    * Convenience method to check if the instance is BLACK
    * @return true if black
    */
   public boolean isBlack() {
     return this == Black;
   }

   /**
    * Convenience method to check if the instance is WHITE
    * @return true if white
    */
   public boolean isWhite() {
     return this == White;
   }

 }

