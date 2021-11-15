public class SavedFunction {
    // 1 - horizontal 2 - vertical 3 - falling slope 4 - rising slope 5 - cross 6 -
    // angled cross
    public int checkLineOrientation(String line, Position point) {
    var linePoints = PointsMap.get(line);
    int orientation = 0;

    if ((linePoints.contains(new Position2D(point.getCol(), point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() - 1, point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() + 1, point.getRow())))
    && (linePoints.contains(new Position2D(point.getCol(), point.getRow()))
    && linePoints.contains(new Position2D(point.getCol(), point.getRow() - 1))
    && linePoints.contains(new Position2D(point.getCol(), point.getRow() + 1))))
    {
    orientation = 5;
    } else if ((linePoints.contains(new Position2D(point.getCol(),
    point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() - 1, point.getRow() -
    1))
    && linePoints.contains(new Position2D(point.getCol() + 1, point.getRow() +
    1)))
    && (linePoints.contains(new Position2D(point.getCol(), point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() - 1, point.getRow() +
    1))
    && linePoints.contains(new Position2D(point.getCol() + 1, point.getRow() -
    1)))) {
    orientation = 6;
    } else if (linePoints.contains(new Position2D(point.getCol(),
    point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() - 1, point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() + 1, point.getRow()))) {
    orientation = 1;
    } else if (linePoints.contains(new Position2D(point.getCol(),
    point.getRow()))
    && linePoints.contains(new Position2D(point.getCol(), point.getRow() - 1))
    && linePoints.contains(new Position2D(point.getCol(), point.getRow() + 1))) {
    orientation = 2;
    } else if (linePoints.contains(new Position2D(point.getCol(),
    point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() - 1, point.getRow() -
    1))
    && linePoints.contains(new Position2D(point.getCol() + 1, point.getRow() +
    1))) {
    orientation = 3;
    } else if (linePoints.contains(new Position2D(point.getCol(),
    point.getRow()))
    && linePoints.contains(new Position2D(point.getCol() - 1, point.getRow() +
    1))
    && linePoints.contains(new Position2D(point.getCol() + 1, point.getRow() -
    1))) {
    orientation = 4;
    }

    return orientation;
    }
}
