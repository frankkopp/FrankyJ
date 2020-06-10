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
 * This enum represents all files of a chess board. If used in a loop via values() omit NOFILE.
 */
public enum File {

  a, b, c, d, e, f, g, h, NoFile;

  // pre-filled list with all squares
  public final long Bb;

  File() {
    final long a = 0b00000001_00000001_00000001_00000001_00000001_00000001_00000001_00000001L;
    if (ordinal() < 8) Bb = a << ordinal();
    else Bb = 0;
  }

  // returns the enum File for a given file number
  public static File get(int file) {
    return File.values()[file];
  }

  // returns the distance in moves between files
  public static int distance(File lhs, File rhs) {
    return Math.abs(lhs.ordinal()-rhs.ordinal());
  }

  @Override
  public String toString() {
    if (this == NoFile) {
      return "-";
    }
    return this.name();
  }

}
