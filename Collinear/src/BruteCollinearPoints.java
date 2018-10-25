

public class BruteCollinearPoints {
   private LineSegment[] segments = new LineSegment[5];
   private int segmentsQty = 0;
   
   public BruteCollinearPoints(Point[] points) {
     if (points == null) throw new java.lang.IllegalArgumentException();
     for (int i = 0; i < points.length; i++) {
       if (points[i] == null) throw new java.lang.IllegalArgumentException();
     }
     
       for (int i = 0; i < points.length; i++) {
           for (int j = i + 1; j < points.length; j++) {
             if (points[i].compareTo(points[j]) == 0)throw new java.lang.IllegalArgumentException(); 
               for (int k = j + 1; k < points.length; k++) {
                   if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[j])) {
                       for (int p = k + 1; p < points.length; p++) {
                           if (points[i].slopeTo(points[p]) == points[i].slopeTo(points[j])) {
                               
                               Point min1, min2, max1, max2;

                               if (points[i].compareTo(points[j]) < 0) {
                                   min1 = points[i];
                                   max1 = points[j];
                               } else {
                                   min1 = points[j];
                                   max1 = points[i];
                               }

                               if (points[k].compareTo(points[p]) < 0) {
                                   min2 = points[k];
                                   max2 = points[p];
                               } else {
                                   min2 = points[p];
                                   max2 = points[k];
                               }
                               
                               if (min2.compareTo(min1) < 0) min1 = min2;
                               if (max1.compareTo(max2) < 0) max1 = max2;
                               
                               if (segments.length == segmentsQty) resize();
                               segments[segmentsQty] = new LineSegment(min1, max1);
                               segmentsQty++;
                           }
                       }
                   }
               }
           }
       }
   }
   public int numberOfSegments() {
       return segmentsQty;
   }
   public LineSegment[] segments() {
     LineSegment[] allSegments = new LineSegment[segmentsQty];
     for (int i = 0; i < segmentsQty; i++) {
       allSegments[i] = segments[i];
     }     
     return allSegments;
   }
   private void resize() {
       LineSegment[] temp = new LineSegment[segmentsQty*2];
       for (int i = 0; i < segmentsQty; i++) {
           temp[i] = segments[i];
       }
       segments = temp;
   }
}