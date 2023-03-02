import java.util.ArrayList;

import edu.princeton.cs.algs4.Queue;

public class Board {

	private final int[] board;
	private final int offset = 1;
	private final int width;

	public Board(int[][] blocks) {

		if (blocks == null) throw new java.lang.IllegalArgumentException();

		if (blocks.length != blocks[0].length) throw new java.lang.IllegalArgumentException();

		width = blocks.length;
		board = new int[width * width];

		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				board[xyTo1D(i, j)] = blocks[i][j];
			}
		}
	}


	public int dimension() {
		return width;

	}

	public int hamming() {
		int sum = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != i + offset && board[i] != 0) sum++;
		}
		return sum;

	}

	public int manhattan() {
		int sum = 0;
		int x1;
		int x2;
		int y1;
		int y2;
		for (int i = 0; i < board.length; i++) {
			if (board[i] != i + offset && board[i] != 0) {
				x1 = xyFrom1D(i)[0];
				y1 = xyFrom1D(i)[1];
				x2 = xyFrom1D(board[i] - offset)[0];
				y2 = xyFrom1D(board[i] - offset)[1];
				sum = sum + (Math.abs(x1 - x2) + Math.abs(y1 - y2));
			}
		}
		return sum;

	}

	public boolean isGoal() {
		for (int i = 0; i < board.length - offset; i++)
			if (board[i] != i + 1) return false;
		return true;

	}

	public Board twin() {
		int row ;
		int col ;
		int up , right, down, left;

		int[][] twin2d;
		int[] twin1d = new int[board.length];

		System.arraycopy(board, 0, twin1d, 0, board.length);

		int i = 0;
		while (i < twin1d.length) {
			if (twin1d[i] != 0) {
				col = xyFrom1D(i)[0];
				row = xyFrom1D(i)[1];

				if (checkBoundary(row - 1, col)) {
					up = xyTo1D(row - 1, col);
					if (twin1d[up] != 0) {
						exch1D(twin1d, i, up);
						break;
					}
				}

				if (checkBoundary(row, col + 1)) {
					right = xyTo1D(row, col + 1);
					if (twin1d[right] != 0) {
						exch1D(twin1d, i, right);
						break;
					}
				}

				if (checkBoundary(row + 1, col)) {
					down = xyTo1D(row + 1, col);
					if (twin1d[down] != 0) {
						exch1D(twin1d, i, down);
						break;
					}
				}

				if (checkBoundary(row, col - 1)) {
					left = xyTo1D(row, col - 1);
					if (twin1d[left] != 0) {
						exch1D(twin1d, i, left);
						break;
					}
				}
			}
			i++;
		}

		twin2d = copy1DTo2D(twin1d, this.width);

		return new Board(twin2d);

	}

	public boolean equals(Object y) {
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		Board that = (Board) y;
		if (that.dimension() != this.dimension()) return false;
		for (int i = 0; i < board.length; i++) {
			if (this.board[i] != that.board[i]) return false;
		}
		return true;

	}

	public Iterable<Board> neighbors() {
		Queue<Board> neighbors = new Queue<>();
		int emptyIndex;
		int row;
		int col;
		int up, right, down, left;
		ArrayList<Integer> blocks = new ArrayList<>();

		for (emptyIndex = 0; emptyIndex < board.length; emptyIndex++) {
			if (board[emptyIndex] == 0) {

				col = xyFrom1D(emptyIndex)[0];
				row = xyFrom1D(emptyIndex)[1];

				if (checkBoundary(row - 1, col)) {
					up = xyTo1D(row - 1, col);
					blocks.add(up);
				}

				if (checkBoundary(row, col + 1)) {
					right = xyTo1D(row, col + 1);
					blocks.add(right);
				}

				if (checkBoundary(row + 1, col)) {
					down = xyTo1D(row + 1, col);
					blocks.add(down);
				}

				if (checkBoundary(row, col - 1)) {
					left = xyTo1D(row, col - 1);
					blocks.add(left);
				}

				break;
			}
		}
		for (Integer block : blocks) {
			int[] temp1d;
			int[][] temp2d;
			temp1d = copy1DTo1D(board);
			exch1D(temp1d, emptyIndex, block);
			temp2d = copy1DTo2D(temp1d, this.width);
			Board tempBoard = new Board(temp2d);
			neighbors.enqueue(tempBoard);
		}

		return neighbors;

	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(width + "\n");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				s.append(String.format("%2d ", board[xyTo1D(i, j)]));
			}
			s.append("\n");
		}
		return s.toString();
	}

	private int xyTo1D(int row, int col) {
		return ((col) % width) + (width * (row));
	}

	private int[] xyFrom1D(int i) {
		int[] xy = new int[2];
		xy[0] = i % width; // col
		xy[1] = i / width; // row
		return xy;
	}

	private void exch1D(int[] arr, int i, int j) {
		int temp;
		temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	private int[][] copy1DTo2D(int[] arr, int width) {
		int[][] arr2D = new int[width][width];
		for (int i = 0; i < arr.length; i++) {
			int x = xyFrom1D(i)[0];
			int y = xyFrom1D(i)[1];
			arr2D[y][x] = arr[i];
		}
		return arr2D;
	}

	private int[] copy1DTo1D(int[] a) {
		int[] b = new int[a.length];
		System.arraycopy(a, 0, b, 0, a.length);

		return b;
	}

	private boolean checkBoundary(int row, int col) {
		return row >= 0 && row < width && col >= 0 && col < width;
	}

	private static int[][] boardFromArray(int[] arr) {
		int[][] test = new int[3][3];
		int idx = 0;

		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < test.length; j++) {
				test[i][j] = arr[idx++];
			}
		}

		return test;
	}

	public static void main(String[] args) {

		int[][] test;
		int[] numbers = {8, 1, 3, 4, 0, 2, 7, 6, 5};


		test = boardFromArray(numbers);


		Board board = new Board(test);
		System.out.println(board);

		System.out.println("Is it goal board? " + board.isGoal());
		System.out.println("Manhattan: " + board.manhattan());
		System.out.println("Hamming: " + board.hamming());
		System.out.println();

		int n = 1;
		Iterable<Board> neighbors = board.neighbors();
		for (Board b : neighbors) {
			System.out.println("Neighbor " + n++);
			System.out.println(b);
		}

		Board twin;
		twin = board.twin();
		System.out.println("Twin");
		System.out.println(twin);

		Board boardClone = new Board(test);
		System.out.println("Is board equal to twin? " + board.equals(twin));
		System.out.println("Is board equal to board? " + board.equals(board));
		System.out.println("Is board equal to null? " + board.equals(null));
		System.out.println("Is board equal to boardClone? " + board.equals(boardClone));
	}
}