package org.jbbouille.lawnmower;

import java.util.List;

public class Instruction {
    final Position position;
    final List<Direction> directions;

    public Instruction(Position position, List<Direction> directions) {
        this.position = position;
        this.directions = directions;
    }

    public enum Direction {
        right, left, straight
    }
}
