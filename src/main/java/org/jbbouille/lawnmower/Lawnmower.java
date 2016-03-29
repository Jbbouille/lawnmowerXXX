package org.jbbouille.lawnmower;

import java.util.List;
import java.util.stream.Collectors;
import org.jbbouille.lawnmower.Instruction.Direction;
import org.jbbouille.lawnmower.Position.PositionBuilder;

public class Lawnmower {

    private final int[][] zone;

    public Lawnmower(int lenght, int height) {
        this.zone = new int[lenght][height];
    }

    public List<Position> mow(List<Instruction> instructions) {
        return instructions.stream()
                           .map(this::move)
                           .collect(Collectors.toList());
    }

    private Position move(Instruction instruction) {
        PositionBuilder position = Position.builder(instruction.position);

        for (Direction direction : instruction.directions) {
            switch (direction) {
                case straight:
                    straight(position);
                    break;
                case left:
                    position.compassPoint(position.compassPoint.left());
                    break;
                case right:
                    position.compassPoint(position.compassPoint.right());
                    break;
                default:
                    throw new UnsupportedOperationException("There is a big problem");
            }
        }

        return position.build();
    }


    private void straight(PositionBuilder position) {
        switch (position.compassPoint.point()) {
            case northern:
                position.yAxis++;
                break;
            case southern:
                position.yAxis--;
                break;
            case eastern:
                position.xAxis++;
                break;
            case western:
                position.xAxis--;
                break;
            default:
                throw new RuntimeException("Big deep shit problem");
        }

        try {
            zone[position.xAxis][position.yAxis] = 1;
        } catch (Exception e) {
            System.out.println("TOTO");
        }
    }
}
