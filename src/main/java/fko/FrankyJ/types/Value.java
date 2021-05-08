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
 * Evaluation constants and functions for a chess games.
 * The values are int (Java does no support a typedef
 * or enum as int.
 */
public class Value {
  public static final int ValueZero               = 0;
  public static final int ValueDraw               = 0;
  public static final int ValueOne                = 1;
  public static final int ValueInf                = 15_000;
  public static final int ValueNA                 = -ValueInf;
  public static final int ValueMax                = 10_000;
  public static final int ValueMin                = -ValueMax;
  public static final int ValueCheckMate          = ValueMax;
  public static final int ValueCheckMateThreshold = ValueMax - 127;

  private Value() {
  }

  public static boolean validValue(final int value) {
    return value >= ValueMin && value <= ValueMax;
  }

  /**
   * IsCheckMateValue returns true if value is above the check mate threshold
   * which typically is set to check mate value minus the maximum search depth
   */
  public static boolean isCheckMateValue(final int value) {
    return Math.abs(value) > ValueCheckMateThreshold;
  }

  /**
   * Returns a UCI value string (e.g. cp 50, mate 2)
   */
  public static String toString(int v) {
    StringBuilder sb = new StringBuilder();
    if (isCheckMateValue(v)) {
      sb.append("mate ");
      if (v < ValueZero) {
        sb.append("-");
      }
      int i = ValueCheckMate - Math.abs(v);
      int i2 = (i + 1) / 2;
      sb.append(i2);
    } else if (v == ValueNA) {
      sb.append("N/A");
    } else {
      sb.append("cp ");
      sb.append(v);
    }
    return sb.toString();
  }
}
