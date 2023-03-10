
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class NearestNeighborVisualizer {

	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);

		StdDraw.show(0);

		PointSET brute = new PointSET();
		KdTree kdtree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
			brute.insert(p);
		}

		StdOut.print(kdtree.size());
		StdOut.print(" / ");
		StdOut.print(brute.size());

		while (true) {
			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();
			Point2D query = new Point2D(x, y);

			StdDraw.clear();
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.01);
			brute.draw();

			StdDraw.setPenRadius(.03);
			StdDraw.setPenColor(StdDraw.RED);
			brute.nearest(query).draw();
			StdDraw.setPenRadius(.02);

			StdDraw.setPenColor(StdDraw.BLUE);
			kdtree.nearest(query).draw();
			StdDraw.show(0);
			StdDraw.show(40);
		}
	}
}