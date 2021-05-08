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
  * This enum represents all ranks of a chess board If used in a loop via values() omit NORANK.
  */
 public enum Rank {

   r1, r2, r3, r4, r5, r6, r7, r8, NoRank;

   // pre-filled list with all squares
   public static final Rank[] values;

   static {
     values = Rank.values();
   }

   // pre-filled list with all squares
   public final long Bb;

   Rank() {
     final long a = 0b00000000_00000000_00000000_00000000_00000000_00000000_00000000_11111111L;
     if (ordinal() < 8) Bb = a << (ordinal()*8);
     else Bb = 0;
   }

   /**
    * returns the enum Rank for a given rank number
    */
   public static Rank getRank(int rank) {
     if (rank < 0 || rank > 7) return NoRank;
     return Rank.values[rank];
   }

   // returns the distance in moves between ranks
   public static int distance(Rank lhs, Rank rhs) {
     return Math.abs(lhs.ordinal()-rhs.ordinal());
   }

   @Override
   public String toString() {
     if (this == NoRank) {
       return "-";
     }
     return "" + (this.ordinal() + 1);
   }
 }

