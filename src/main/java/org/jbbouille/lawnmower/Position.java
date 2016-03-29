package org.jbbouille.lawnmower;

public class Position {
    final int xAxis;
    final int yAxis;
    final CompassPoints compassPoint;

    public Position(int xAxis, int yAxis, CompassPoints compassPoint) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.compassPoint = compassPoint;
    }

    public static PositionBuilder builder(Position position) {
        return new PositionBuilder(position.xAxis, position.yAxis, position.compassPoint);
    }

    @Override
    public String toString() {
        return "Position{" +
                "xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", compassPoint=" + compassPoint +
                '}';
    }

    public static class PositionBuilder {
        public int xAxis;
        public int yAxis;
        public CompassPoints compassPoint;

        public PositionBuilder(int xAxis, int yAxis, CompassPoints compassPoint) {
            this.xAxis = xAxis;
            this.yAxis = yAxis;
            this.compassPoint = compassPoint;
        }

        public Position.PositionBuilder compassPoint(CompassPoints compassPoint) {
            this.compassPoint = compassPoint;
            return this;
        }

        public Position build() {
            return new Position(xAxis, yAxis, compassPoint);
        }
    }
}
