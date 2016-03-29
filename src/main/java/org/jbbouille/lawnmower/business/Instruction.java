package org.jbbouille.lawnmower.business;

import java.util.List;

public class Instruction {
    public final Position position;
    public final List<Direction> directions;

    public Instruction(Position position, List<Direction> directions) {
        this.position = position;
        this.directions = directions;
    }

    public enum Direction {
        right, left, straight
    }
}
