import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private ArrayList<LineSegment> segmentsList;

	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException("Argument is null");

		for (Point point : points) {
			if (point == null)
				throw new IllegalArgumentException("Point is null");
		}

		int length = points.length;
		for (int i = 0; i < length; i++) {
			for (int j = i + 1; j < length; j++) {
				if (points[i].compareTo(points[j]) == 0)
					throw new IllegalArgumentException("Repeated point detected");
			}
		}

		segmentsList = new ArrayList<>();
		Point[] pointsCopy = Arrays.copyOf(points, length);

		for (int i = 0; i < length - 3; i++) {
			for (int j = i + 1; j < length - 2; j++) {
				for (int k = j + 1; k < length - 1; k++) {
					for (int l = k + 1; l < length; l++) {
						double slope1 = pointsCopy[i].slopeTo(pointsCopy[j]);
						double slope2 = pointsCopy[i].slopeTo(pointsCopy[k]);
						double slope3 = pointsCopy[i].slopeTo(pointsCopy[l]);
						if (slope1 == slope2 && slope2 == slope3) {
							Arrays.sort(new Point[]{pointsCopy[i], pointsCopy[j], pointsCopy[k], pointsCopy[l]});
							segmentsList.add(new LineSegment(pointsCopy[0], pointsCopy[3]));
						}
					}
				}
			}
		}
	}

	public int numberOfSegments() {
		return segmentsList.size();
	}

	public LineSegment[] segments() {
		return segmentsList.toArray(new LineSegment[segmentsList.size()]);
	}
}


