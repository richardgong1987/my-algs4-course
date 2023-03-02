import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class RangeSearchVisualizer {

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

		double x0 = 0.0, y0 = 0.0;
		double x1 = 0.0, y1 = 0.0;
		boolean isDragging = false;

		StdDraw.clear();
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setPenRadius(.01);
		brute.draw();

		while (true) {
			StdDraw.show(40);

			// user starts to drag a rectangle
			if (StdDraw.mousePressed() && !isDragging) {
				x0 = StdDraw.mouseX();
				y0 = StdDraw.mouseY();
				isDragging = true;
				continue;
			}

			// user is dragging a rectangle
			else if (StdDraw.mousePressed() && isDragging) {
				x1 = StdDraw.mouseX();
				y1 = StdDraw.mouseY();
				continue;
			} else if (!StdDraw.mousePressed() && isDragging) {
				isDragging = false;
			}


			RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0, x1), Math.max(y0, y1));
			StdDraw.clear();
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius(.01);
			brute.draw();

			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.setPenRadius();
			rect.draw();

			StdDraw.setPenRadius(.03);
			StdDraw.setPenColor(StdDraw.RED);
			for (Point2D p : brute.range(rect))
				p.draw();

			StdDraw.setPenRadius(.02);
			StdDraw.setPenColor(StdDraw.BLUE);
			for (Point2D p : kdtree.range(rect))
				p.draw();

			StdDraw.show(40);
		}
	}
}