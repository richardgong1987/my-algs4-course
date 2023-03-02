import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
	private final List<Point[]> segments = new ArrayList<>();

	public FastCollinearPoints(Point[] points) {
		if (points == null) {
			throw new IllegalArgumentException("points cannot be null");
		}

		int n = points.length;

		// Check if points array or any of the points are null
		for (int i = 0; i < n; i++) {
			if (points[i] == null) {
				throw new IllegalArgumentException("point cannot be null");
			}
		}

		Point[] sortedPoints = points.clone();
		Arrays.sort(sortedPoints);

		// Check for repeated points
		for (int i = 1; i < n; i++) {
			if (sortedPoints[i].compareTo(sortedPoints[i - 1]) == 0) {
				throw new IllegalArgumentException("points cannot be repeated");
			}
		}

		for (int i = 0; i < n; i++) {
			Point p = sortedPoints[i];
			Point[] otherPoints = sortedPoints.clone();
			Arrays.sort(otherPoints, p.slopeOrder());

			int j = 1;
			while (j < n - 2) {
				double slope1 = p.slopeTo(otherPoints[j]);
				int count = 1;
				while (j + count < n && p.slopeTo(otherPoints[j + count]) == slope1) {
					count++;
				}
				if (count >= 3) {
					Point[] collinearPoints = new Point[count + 1];
					collinearPoints[0] = p;
					for (int k = 0; k < count; k++) {
						collinearPoints[k + 1] = otherPoints[j + k];
					}
					Arrays.sort(collinearPoints);
					if (collinearPoints[0] == p) {
						segments.add(collinearPoints);
					}
				}
				j += count;
			}
		}
	}

	public int numberOfSegments() {
		return segments.size();
	}

	public LineSegment[] segments() {
		int n = segments.size();
		LineSegment[] res = new LineSegment[n];
		for (int i = 0; i < n; i++) {
			res[i] = new LineSegment(segments.get(i)[0], segments.get(i)[segments.get(i).length - 1]);
		}
		return res;
	}
}




