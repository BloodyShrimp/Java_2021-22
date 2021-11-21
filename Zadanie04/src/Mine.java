public class Mine {
    public static void main(String[] args) {
        var b = new BusLine();
        b.addBusLine("A", new Position2D(1, 1), new Position2D(3, 16));
        b.addLineSegment("A", new LineSegment(new Position2D(1, 14), new Position2D(3, 16))); // 6
        b.addLineSegment("A", new LineSegment(new Position2D(1, 12), new Position2D(1, 14))); // 5
        b.addLineSegment("A", new LineSegment(new Position2D(1, 1), new Position2D(1, 4))); // 1
        b.addLineSegment("A", new LineSegment(new Position2D(6, 12), new Position2D(1, 12))); // 4
        b.addLineSegment("A", new LineSegment(new Position2D(1, 4), new Position2D(6, 4))); // 2
        b.addLineSegment("A", new LineSegment(new Position2D(6, 4), new Position2D(6, 12))); // 3

        b.addBusLine("B", new Position2D(1, 16), new Position2D(3, 2));
        b.addLineSegment("B", new LineSegment(new Position2D(3, 14), new Position2D(3, 2))); // 2
        b.addLineSegment("B", new LineSegment(new Position2D(1, 16), new Position2D(3, 14))); // 1

        b.addBusLine("C", new Position2D(1, 8), new Position2D(9, 12));
        b.addLineSegment("C", new LineSegment(new Position2D(11, 5), new Position2D(9, 5))); // 3
        b.addLineSegment("C", new LineSegment(new Position2D(11, 8), new Position2D(11, 5))); // 2
        b.addLineSegment("C", new LineSegment(new Position2D(9, 5), new Position2D(9, 12))); // 4
        b.addLineSegment("C", new LineSegment(new Position2D(1, 8), new Position2D(11, 8))); // 1

        b.addBusLine("D", new Position2D(1, 10), new Position2D(11, 10));
        b.addLineSegment("D", new LineSegment(new Position2D(3, 10), new Position2D(11, 10))); // 2
        b.addLineSegment("D", new LineSegment(new Position2D(1, 10), new Position2D(3, 10))); // 1

        b.findIntersections();
        // System.out.println(b.getLines());
        // System.out.println(b.getIntersectionPositions());
        // System.out.println(b.getIntersectionsWithLines());
        // System.out.println(b.getIntersectionOfLinesPair());
        // System.out.println(b.SegmentsMap);
    }
}
