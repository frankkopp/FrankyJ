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

public class TimingTest {

  @Test
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
}
