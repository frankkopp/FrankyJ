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

import static fko.FrankyJ.types.MoveType.Promotion;
import static fko.FrankyJ.types.PieceType.Queen;
import static fko.FrankyJ.types.Square.SqH7;
import static fko.FrankyJ.types.Square.SqH8;
import static fko.FrankyJ.types.Value.ValueMax;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoveTest {

  @Test
  void createMove() {
    int move = Move.createMove(SqH8, SqH7, Promotion, Queen, ValueMax);
    System.out.println(Move.toString(move));
    System.out.println(Move.toVerboseString(move));
    System.out.println(Move.toBitString(move));
    assertEquals("h8h7Q", Move.toString(move));
    assertEquals("Move {to: h8 from: h7 type: Promotion prom: Queen value: 10000 (int: 1638432759)}", Move.toVerboseString(move));
    assertEquals("0110000110101000.01.11.111111.110111 (value.movetype.promtype.from.to)", Move.toBitString(move));
  }


}
