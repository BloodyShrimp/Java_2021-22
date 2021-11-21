import java.util.*;

public class BusLine implements BusLineInterface {
    private Map<String, List<Position>> StartFinishMap;
    private Map<String, List<LineSegment>> SegmentsMap;
    private Map<String, List<Position>> PointsMap;
    private Map<String, List<Position>> getLinesMap;
    private Map<String, List<Position>> getIntersectionPositionsMap;
    private Map<String, List<String>> getIntersectionsWithLinesMap;
    private Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPairMap;

    static class LinesPair implements BusLineInterface.LinesPair {
        private String FirstLine;
        private String SecondLine;

        public LinesPair(String FirstLine, String SecondLine) {
            this.FirstLine = FirstLine;
            this.SecondLine = SecondLine;
        }

        public String getFirstLineName() {
            return FirstLine;
        }

        public String getSecondLineName() {
            return SecondLine;
        }

        @Override
        public int hashCode() {
            return Objects.hash(FirstLine, SecondLine);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            LinesPair other = (LinesPair) obj;
            return FirstLine.equals(other.FirstLine) && SecondLine.equals(other.SecondLine);
        }

        @Override
        public String toString() {
            return "LinesPair [FirstLine=" + FirstLine + ", SecondLine=" + SecondLine + "]";
        }
    }

    public BusLine() {
        StartFinishMap = new HashMap<>();
        SegmentsMap = new HashMap<>();
        PointsMap = new HashMap<>();
        getLinesMap = new HashMap<>();
        getIntersectionPositionsMap = new HashMap<>();
        getIntersectionsWithLinesMap = new HashMap<>();
        getIntersectionOfLinesPairMap = new HashMap<>();
    }

    public void addBusLine(String busLineName, Position firstPoint, Position lastPoint) {
        List<Position> tempPosition = new ArrayList<Position>();
        tempPosition.add(firstPoint);
        tempPosition.add(lastPoint);
        StartFinishMap.put(busLineName, tempPosition);
        List<LineSegment> tempSegment = new ArrayList<LineSegment>();
        SegmentsMap.put(busLineName, tempSegment);
        List<Position> tempPoints = new ArrayList<Position>();
        PointsMap.put(busLineName, tempPoints);
        List<Position> tempLines = new ArrayList<>();
        getLinesMap.put(busLineName, tempLines);
        List<Position> tempIntersectionPositions = new ArrayList<>();
        getIntersectionPositionsMap.put(busLineName, tempIntersectionPositions);
        List<String> temIntersectionsWithLines = new ArrayList<>();
        getIntersectionsWithLinesMap.put(busLineName, temIntersectionsWithLines);
    }

    public void addLineSegment(String busLineName, LineSegment lineSegment) {
        SegmentsMap.get(busLineName).add(lineSegment);
    }

    public void findIntersections() {
        createListOfPoints();
        for (var entry : StartFinishMap.entrySet()) {
            var firstLineName = entry.getKey();
            for (var otherEntry : StartFinishMap.entrySet()) {
                var secondLineName = otherEntry.getKey();
                Set<Position> tempSet = new HashSet<>();
                LinesPair tempPair = new LinesPair(firstLineName, secondLineName);
                getIntersectionOfLinesPairMap.put(tempPair, tempSet);
            }
        }
        for (var entry : PointsMap.entrySet()) {
            var currentList = entry.getValue();
            var currentLine = entry.getKey();
            for (var firstLinePoint : currentList) {
                for (var otherEntry : PointsMap.entrySet()) {
                    var otherLine = otherEntry.getKey();
                    if (checkForIntersection(currentLine, otherLine, firstLinePoint)) {
                        getIntersectionPositionsMap.get(currentLine).add(firstLinePoint);
                        getIntersectionsWithLinesMap.get(currentLine).add(otherLine);
                        getIntersectionOfLinesPairMap.get(new LinesPair(currentLine, otherLine)).add(firstLinePoint);

                    }
                }
            }
        }

        for (var entry : StartFinishMap.entrySet()) {
            var keys = entry.getKey();
            if(getIntersectionPositionsMap.get(keys).isEmpty()) {
                PointsMap.remove(keys);
                getIntersectionPositionsMap.remove(keys);
                getIntersectionsWithLinesMap.remove(keys);
            }
        }
    }

    public void sortSegments() {
        for (var entry : SegmentsMap.entrySet()) {
            var list = entry.getValue();
            var currentLine = entry.getKey();
            var beg = StartFinishMap.get(currentLine).get(0);
            var end = StartFinishMap.get(currentLine).get(1);

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFirstPosition().equals(beg))
                    Collections.swap(list, i, 0);
            }
            
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getLastPosition().equals(end))
                    Collections.swap(list, i, list.size() - 1);
            }

            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getLastPosition().equals(list.get(i + 1).getFirstPosition())) {
                    ;
                } else {
                    for (int j = i + 2; j < list.size(); j++) {
                        if (list.get(i).getLastPosition().equals(list.get(j).getFirstPosition())) {
                            Collections.swap(list, i + 1, j);
                        }
                    }
                }
            }
        }
    }

    public void createListOfPoints() {
        sortSegments();
        for (var entry : SegmentsMap.entrySet()) {
            var list = entry.getValue();
            var currentLine = entry.getKey();
            var tempPointsList = new ArrayList<Position>();
            for (var segment : list) {
                int ax = segment.getFirstPosition().getCol();
                int ay = segment.getFirstPosition().getRow();
                int bx = segment.getLastPosition().getCol();
                int by = segment.getLastPosition().getRow();
                int colComp = Integer.compare(ax, bx);
                int rowComp = Integer.compare(ay, by);
                if (colComp == 0 && rowComp == 0) {
                    tempPointsList.add(new Position2D(ax, ay));
                } else if (colComp == 1 && rowComp == 0) {
                    for (int i = ax; i > bx; i--) {
                        tempPointsList.add(new Position2D(i, ay));
                    }
                } else if (colComp == -1 && rowComp == 0) {
                    for (int i = ax; i < bx; i++) {
                        tempPointsList.add(new Position2D(i, ay));
                    }
                } else if (colComp == 0 && rowComp == -1) {
                    for (int i = ay; i < by; i++) {
                        tempPointsList.add(new Position2D(ax, i));
                    }
                } else if (colComp == 1 && rowComp == -1) {
                    int j = 0;
                    for (int i = ax; i > bx; i--) {
                        tempPointsList.add(new Position2D(i, ay + j));
                        j++;
                    }
                } else if (colComp == -1 && rowComp == -1) {
                    int j = 0;
                    for (int i = ax; i < bx; i++) {
                        tempPointsList.add(new Position2D(i, ay + j));
                        j++;
                    }
                } else if (colComp == 0 && rowComp == 1) {
                    for (int i = ay; i > by; i--) {
                        tempPointsList.add(new Position2D(ax, i));
                    }
                } else if (colComp == 1 && rowComp == 1) {
                    int j = 0;
                    for (int i = ax; i > bx; i--) {
                        tempPointsList.add(new Position2D(i, ay - j));
                        j++;
                    }
                } else if (colComp == -1 && rowComp == 1) {
                    int j = 0;
                    for (int i = ax; i < bx; i++) {
                        tempPointsList.add(new Position2D(i, ay - j));
                        j++;
                    }
                }
            }
            tempPointsList.add(StartFinishMap.get(currentLine).get(1));
            PointsMap.put(currentLine, tempPointsList);
        }
    }

    // 1 - horizontal 2 - vertical 3 - falling slope 4 - rising slope
    public boolean checkForIntersection(String firstLine, String secondLine, Position point) {
        var firstPoint = PointsMap.get(firstLine).indexOf(point);
        var secondPoint = PointsMap.get(secondLine).indexOf(point);
        if (firstLine.equals(secondLine)) {
            secondPoint = PointsMap.get(secondLine).lastIndexOf(point);
        }
        int firstLineOrientation = checkLineOrientation(firstLine, firstPoint);
        int secondLineOrientation = checkLineOrientation(secondLine, secondPoint);

        if ((firstLineOrientation == 1 && secondLineOrientation == 2)
                || (firstLineOrientation == 2 && secondLineOrientation == 1)) {
            return true;
        } else if ((firstLineOrientation == 3 && secondLineOrientation == 4)
                || (firstLineOrientation == 4 && secondLineOrientation == 3)) {
            return true;
        } else {
            return false;
        }
    }

    // 1 - horizontal 2 - vertical 3 - falling slope 4 - rising slope
    public int checkLineOrientation(String line, int index) {
        var linePoints = PointsMap.get(line);
        var PointIndex = index;
        if ((PointIndex <= 0) || PointIndex >= linePoints.size() - 1) {
            return -1;
        }
        var point = linePoints.get(PointIndex);
        var LPoint = new Position2D(point.getCol() - 1, point.getRow());
        var RPoint = new Position2D(point.getCol() + 1, point.getRow());
        var UPoint = new Position2D(point.getCol(), point.getRow() - 1);
        var DPoint = new Position2D(point.getCol(), point.getRow() + 1);
        var LUPoint = new Position2D(point.getCol() - 1, point.getRow() - 1);
        var LDPoint = new Position2D(point.getCol() - 1, point.getRow() + 1);
        var RUPoint = new Position2D(point.getCol() + 1, point.getRow() - 1);
        var RDPoint = new Position2D(point.getCol() + 1, point.getRow() + 1);
        int orientation = 0;

        if ((linePoints.get(PointIndex - 1).equals(LPoint) && linePoints.get(PointIndex + 1).equals(RPoint))
                || (linePoints.get(PointIndex - 1).equals(RPoint) && linePoints.get(PointIndex + 1).equals(LPoint))) {
            orientation = 1;
        } else if ((linePoints.get(PointIndex - 1).equals(DPoint) && linePoints.get(PointIndex + 1).equals(UPoint))
                || (linePoints.get(PointIndex - 1).equals(UPoint) && linePoints.get(PointIndex + 1).equals(DPoint))) {
            orientation = 2;
        } else if (((linePoints.get(PointIndex - 1).equals(LUPoint) && linePoints.get(PointIndex + 1).equals(RDPoint))
                || (linePoints.get(PointIndex - 1).equals(RDPoint)
                        && linePoints.get(PointIndex + 1).equals(LUPoint)))) {
            orientation = 3;
        } else if (((linePoints.get(PointIndex - 1).equals(RUPoint) && linePoints.get(PointIndex + 1).equals(LDPoint))
                || (linePoints.get(PointIndex - 1).equals(LDPoint)
                        && linePoints.get(PointIndex + 1).equals(RUPoint)))) {
            orientation = 4;
        }

        return orientation;
    }

    public Map<String, List<Position>> getLines() {
        return PointsMap;
    }

    public Map<String, List<Position>> getIntersectionPositions() {
        return getIntersectionPositionsMap;
    }

    public Map<String, List<String>> getIntersectionsWithLines() {
        return getIntersectionsWithLinesMap;
    }

    public Map<BusLineInterface.LinesPair, Set<Position>> getIntersectionOfLinesPair() {
        return getIntersectionOfLinesPairMap;
    }
}
