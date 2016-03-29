package org.jbbouille.lawnmower;

import static org.jbbouille.lawnmower.CompassPoints.Point.eastern;
import static org.jbbouille.lawnmower.CompassPoints.Point.northern;
import static org.jbbouille.lawnmower.CompassPoints.Point.southern;
import static org.jbbouille.lawnmower.CompassPoints.Point.western;
import java.util.Arrays;
import java.util.List;

public class CompassPoints {

    private final List<Point> points = Arrays.asList(northern, eastern, southern, western);

    private int position;

    public CompassPoints(Point point) {
        this.position = point.val;
    }

    public CompassPoints right() {
        if (position >= points.size() - 1) {
            position = 0;
            return this;
        }
        position++;
        return this;
    }

    public CompassPoints left() {
        if (position <= 0) {
            position = points.size() - 1;
            return this;
        }
        position--;
        return this;
    }

    public Point point() {
        return points.get(position);
    }

    @Override
    public String toString() {
        return point().name();
    }

    public enum Point {
        northern(0), eastern(1), southern(2), western(3);

        public int val;

        Point(int val) {
            this.val = val;
        }
    }
}
