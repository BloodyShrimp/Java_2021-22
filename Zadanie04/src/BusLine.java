import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class BusLine implements BusLineInterface {
    private Map<String, List<Position>> StartFinishMap;
    private Map<String, List<LineSegment>> SegmentsMap;
    public Map<String, List<Position>> PointsMap;

    public class MyLinesPair implements LinesPair {
        private String FirstLine;
        private String SecondLine;

        public MyLinesPair(String FirstLine, String SecondLine) {
            this.FirstLine = FirstLine;
            this.SecondLine = SecondLine;
        }

        public String getFirstLineName() {
            return FirstLine;
        }

        public String getSecondLineName() {
            return SecondLine;
        }
    }

    public BusLine() {
        StartFinishMap = new HashMap<String, List<Position>>();
        SegmentsMap = new HashMap<String, List<LineSegment>>();
        PointsMap = new HashMap<String, List<Position>>();
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
    }

    public void addLineSegment(String busLineName, LineSegment lineSegment) {
        SegmentsMap.get(busLineName).add(lineSegment);
    }

    public void findIntersections() {

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
                if (list.get(i).getLastPosition().equals(end))
                    Collections.swap(list, i, list.size() - 1);
            }

            for (int i = 0; i < list.size() - 1; i++) {
                if (list.get(i).getLastPosition().equals(list.get(i + 1).getFirstPosition())) {
                    break;
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

    public Map<String, List<Position>> getLines() {

    }

    public Map<String, List<Position>> getIntersectionPositions() {

    }

    public Map<String, List<String>> getIntersectionsWithLines() {

    }

    public Map<LinesPair, Set<Position>> getIntersectionOfLinesPair() {

    }
}
