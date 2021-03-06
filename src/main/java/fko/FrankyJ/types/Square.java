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

public enum Square {
  // @formatter:off
  SqA1, SqB1, SqC1, SqD1, SqE1, SqF1, SqG1, SqH1,
  SqA2, SqB2, SqC2, SqD2, SqE2, SqF2, SqG2, SqH2,
  SqA3, SqB3, SqC3, SqD3, SqE3, SqF3, SqG3, SqH3,
  SqA4, SqB4, SqC4, SqD4, SqE4, SqF4, SqG4, SqH4,
  SqA5, SqB5, SqC5, SqD5, SqE5, SqF5, SqG5, SqH5,
  SqA6, SqB6, SqC6, SqD6, SqE6, SqF6, SqG6, SqH6,
  SqA7, SqB7, SqC7, SqD7, SqE7, SqF7, SqG7, SqH7,
  SqA8, SqB8, SqC8, SqD8, SqE8, SqF8, SqG8, SqH8,
  SqNone;
  // @formatter:on

  // precomputed values
  private long    bitBoard;

  // precomputed file and rank
  private       File file;
  private       Rank rank;
  private final Square[] neighbours = new Square[8];

  // initialization and pre-computation
  static {
    // pre-compute fields
    for (Square s : Square.values()) {

      if (s == SqNone) {
        s.bitBoard = 0L;
        s.file = File.NoFile;
        s.rank = Rank.NoRank;
        continue;
      }

      s.bitBoard = 1L << s.ordinal();
      s.file = File.values()[s.ordinal() & 7];
      s.rank = Rank.values()[s.ordinal() >>> 3];
      for (Direction d : Direction.values()) {
        int idx = s.ordinal();
        switch (d) {
          case North, South -> {
            idx += d.get();
          }
          case East, NorthEast, Southeast -> {
            if (s.file.ordinal() < File.h.ordinal()) {
              idx += d.get();
            } else {
              idx = SqNone.ordinal();
            }
          }
          case West, Southwest, Northwest -> {
            if (s.file.ordinal() > File.a.ordinal()) {
              idx += d.get();
            } else {
              idx = SqNone.ordinal();
            }
          }
        }
        if (idx >= 0 && idx < SqNone.ordinal()) {
          s.neighbours[d.ordinal()] = getSquare(idx);
        } else {
            s.neighbours[d.ordinal()] = SqNone;
        }
      }
    }
  }

  /**
   * @param sq index
   * @return Square for index
   */
  public static Square getSquare(int sq) {
    if (sq >= SqNone.ordinal()) return SqNone;
    return Square.values()[sq];
  }

  /**
   * Returns the Square for the given file and rank
   * @param f
   * @param r
   * @return the Square for the given file and rank
   */
  public static Square getSquare(File f, Rank r) {
    if (f == File.NoFile|| r == Rank.NoRank) return SqNone;
    // index starts with 0 while file and rank start with 1 - decrease
    return Square.values()[(r.ordinal() << 3) + f.ordinal()];
  }

  public Square to(Direction d) {
    return this.neighbours[d.ordinal()];
  }

  public Square pawnPush(Color c) {
    return c == Color.White ? this.neighbours[Direction.North.ordinal()] : this.neighbours[Direction.South.ordinal()];
  }

  public long Bb() {
    return bitBoard;
  }

  public File getFile() {
    return file;
  }

  public Rank getRank() {
    return rank;
  }

  @Override
  public String toString() {
    if (this.ordinal() >= SqNone.ordinal()) {
      return "--";
    }
    return file.toString()+rank.toString();
  }
}
