import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

	private SearchNode lastNode;
	private final boolean solvable;
	private final int minMoves;

	public Solver(Board initial) {
		if (initial == null) throw new java.lang.IllegalArgumentException();
		int moves = 0;
		int twinMoves = 0;

		Queue<Board> neighbors = new Queue<>();
		Queue<Board> twinNeighbors = new Queue<>();

		MinPQ<SearchNode> searchNodes = new MinPQ<>();
		MinPQ<SearchNode> twinNodes = new MinPQ<>();

		SearchNode searchNode = new SearchNode(initial, moves, null);
		SearchNode twinSearchNode = new SearchNode(initial.twin(), twinMoves, null);

		twinNodes.insert(twinSearchNode);
		searchNodes.insert(searchNode);

		boolean solved = false;
		boolean twinSolved = false;
		SearchNode current;

		while (!solved && !twinSolved) {
			current = searchNodes.delMin();
			SearchNode predecessor = current.getPredecessor();
			Board temp = current.getBoard();
			solved = temp.isGoal();

			SearchNode twinCurrent = twinNodes.delMin();
			SearchNode twinPredecessor = twinCurrent.getPredecessor();
			Board twinTemp = twinCurrent.getBoard();
			twinSolved = twinTemp.isGoal();

			for (Board b : temp.neighbors())
				neighbors.enqueue(b);

			for (Board b : twinTemp.neighbors())
				twinNeighbors.enqueue(b);


			while (neighbors.size() > 0) {
				Board board = neighbors.dequeue();
				int move = current.getMoves();
				move++;
				if (predecessor != null && predecessor.getBoard().equals(board)) continue;

				SearchNode neighborNode = new SearchNode(board, move, current);
				searchNodes.insert(neighborNode);
			}

			while (twinNeighbors.size() > 0) {
				Board board = twinNeighbors.dequeue();
				int twinMove = current.getMoves();
				twinMove++;
				if (twinPredecessor != null && twinPredecessor.getBoard().equals(board)) continue;

				SearchNode neighborNode = new SearchNode(board, twinMove, twinCurrent);
				twinNodes.insert(neighborNode);
			}

			moves = current.getMoves() + 1;
			lastNode = current;
		}

		solvable = !twinSolved;
		minMoves = moves - 1;
	}

	public boolean isSolvable() {
		return solvable;

	}

	public int moves() {
		if (!isSolvable()) return -1;
		return minMoves;

	}

	public Iterable<Board> solution() {
		Stack<Board> boards = new Stack<>();
		SearchNode lastNode;
		lastNode = this.lastNode;
		if (this.isSolvable()) {
			while (lastNode.getPredecessor() != null) {
				boards.push(lastNode.getBoard());
				lastNode = lastNode.getPredecessor();
			}
			boards.push(lastNode.getBoard());
			return boards;
		}
		return null;
	}

	private static class SearchNode implements Comparable<SearchNode> {

		private final SearchNode predecessor;
		private final Board current;
		private final int moves;
		private final int priority;

		public SearchNode(Board initial, int m, SearchNode pred) {
			predecessor = pred;
			moves = m;
			current = initial;

			priority = m + initial.manhattan();
		}

		public int getPriority() {
			return priority;
		}

		public Board getBoard() {
			return current;
		}

		public int getMoves() {
			return moves;
		}

		public SearchNode getPredecessor() {
			return predecessor;
		}

		@Override
		public int compareTo(SearchNode o) {
			return this.priority - o.priority;
		}
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		int n = in.readInt();
		int[][] blocks = new int[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		Solver solver = new Solver(initial);

		if (!solver.isSolvable()) StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}