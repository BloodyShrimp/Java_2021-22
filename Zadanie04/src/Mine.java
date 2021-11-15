public class Mine {
    public static void main(String[] args) {
        var b = new BusLine();
        b.addBusLine("Linia 111", new Position2D(1, 1), new Position2D(2, 6));
        b.addLineSegment("Linia 111", new LineSegment(new Position2D(1, 1), new Position2D(7, 7))); // 1
        b.addLineSegment("Linia 111", new LineSegment(new Position2D(7, 1), new Position2D(2, 6))); // 3
        b.addLineSegment("Linia 111", new LineSegment(new Position2D(7, 7), new Position2D(7, 1))); // 2
        b.addBusLine("Linia 222", new Position2D(4, 7), new Position2D(7, 7));
        b.addLineSegment("Linia 222", new LineSegment(new Position2D(4, 7), new Position2D(7, 7)));
        b.addBusLine("Linia 333", new Position2D(1, 1), new Position2D(4, 2));
        b.addLineSegment("Linia 333", new LineSegment(new Position2D(1, 4), new Position2D(4, 4))); // 2
        b.addLineSegment("Linia 333", new LineSegment(new Position2D(8, 2), new Position2D(4, 2))); // 5
        b.addLineSegment("Linia 333", new LineSegment(new Position2D(1, 1), new Position2D(1, 4))); // 1
        b.addLineSegment("Linia 333", new LineSegment(new Position2D(4, 4), new Position2D(8, 4))); // 3
        b.addLineSegment("Linia 333", new LineSegment(new Position2D(8, 4), new Position2D(8, 2))); // 4

        b.findIntersections();
        System.out.println(b.getLines());
        System.out.println(b.getIntersectionPositions());
        System.out.println(b.getIntersectionsWithLines());
        System.out.println(b.getIntersectionOfLinesPair());
    }
}
