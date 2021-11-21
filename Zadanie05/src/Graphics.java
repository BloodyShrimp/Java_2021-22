import java.util.*;

public class Graphics implements GraphicsInterface {
    public class Position2D implements Position {

        private final int col;
        private final int row;

        public Position2D(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public String toString() {
            return "Position2D [col=" + col + ", row=" + row + "]";
        }

        @Override
        public int hashCode() {
            return Objects.hash(col, row);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Position2D other = (Position2D) obj;
            return col == other.col && row == other.row;
        }
    }

    private CanvasInterface plotno;
    private Deque<Position> pointQueue;
    private List<Position> checkedPoints;

    public void setCanvas(CanvasInterface canvas) {
        plotno = canvas;
        pointQueue = new ArrayDeque<>();
        checkedPoints = new ArrayList<>();
    }

    public void fillWithColor(Position startingPosition, Color color) throws WrongStartingPosition, NoCanvasException {

        if (plotno == null) {
            throw new NoCanvasException();
        } else {
            pointQueue.clear();
            checkedPoints.clear();
        }
        Position currentPoint = startingPosition;
        Position nextPoint;
        pointQueue.add(currentPoint);
        while (pointQueue.size() > 0) {
            currentPoint = pointQueue.poll();
            checkedPoints.add(currentPoint);

            try {
                plotno.setColor(currentPoint, color);
                nextPoint = new Position2D(currentPoint.getCol() - 1, currentPoint.getRow());
                if (!checkedPoints.contains((Position2D) nextPoint)) {
                    pointQueue.add(nextPoint);
                    checkedPoints.add(nextPoint);
                }
                nextPoint = new Position2D(currentPoint.getCol() + 1, currentPoint.getRow());
                if (!checkedPoints.contains((Position2D) nextPoint)) {
                    pointQueue.add(nextPoint);
                    checkedPoints.add(nextPoint);
                }
                nextPoint = new Position2D(currentPoint.getCol(), currentPoint.getRow() - 1);
                if (!checkedPoints.contains((Position2D) nextPoint)) {
                    pointQueue.add(nextPoint);
                    checkedPoints.add(nextPoint);
                }
                nextPoint = new Position2D(currentPoint.getCol(), currentPoint.getRow() + 1);
                if (!checkedPoints.contains((Position2D) nextPoint)) {
                    pointQueue.add(nextPoint);
                    checkedPoints.add(nextPoint);
                }
            } catch (CanvasInterface.BorderColorException e) {
                try {
                    plotno.setColor(currentPoint, e.previousColor);
                    if (currentPoint.equals(startingPosition)) {
                        throw new WrongStartingPosition();
                    }
                } catch (CanvasInterface.BorderColorException x) {
                } catch (CanvasInterface.CanvasBorderException x) {
                }
            } catch (CanvasInterface.CanvasBorderException e) {

                if (currentPoint.equals(startingPosition)) {
                    throw new WrongStartingPosition();
                }
            }

        }
    }
}
