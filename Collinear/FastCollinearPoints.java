import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lines = new ArrayList<LineSegment>();
  
   public FastCollinearPoints(Point[] points) {
     if (points == null) throw new java.lang.IllegalArgumentException();
     
     for (int i = 0; i < points.length; i++) {
       if (points[i] == null) throw new java.lang.IllegalArgumentException();
     }
     Point[] myPoints = Arrays.copyOf(points, points.length);
     Arrays.sort(myPoints);
     for (int i = 1; i < myPoints.length; i++) {
       if (myPoints[i].compareTo(myPoints[i-1]) == 0) throw new java.lang.IllegalArgumentException();
     }
     
     for (int i = 0; i < myPoints.length - 1; i++) {
       Point p = myPoints[i];
       Arrays.sort(myPoints, i+1, myPoints.length, p.slopeOrder());
       int begin = i+1;
       int end = i+1;
       while (begin < myPoints.length) {
         Point min = p;
         Point max = p;
         while (end < myPoints.length && p.slopeTo(myPoints[begin]) == p.slopeTo(myPoints[end])) {
           if (min.compareTo(myPoints[end]) > 0) min = myPoints[end];
           if (max.compareTo(myPoints[end]) < 0) max = myPoints[end];
           end++;
         }
         if ((end - begin) > 2) {
             lines.add(new LineSegment(min, max));
         }
         begin = end;
       }
     }
   }
   public int numberOfSegments() {
     // the number of line segments
     return lines.size();
   }
   public LineSegment[] segments() {
     LineSegment[] lineSegments = new LineSegment[lines.size()];
     for (int i = 0; i < lines.size(); i++) {
         lineSegments[i] = lines.get(i);
     }
     return lineSegments;
   }
}
