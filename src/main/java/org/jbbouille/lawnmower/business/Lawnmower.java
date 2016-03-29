package org.jbbouille.lawnmower.business;

import java.util.List;
import java.util.stream.Collectors;
import org.jbbouille.lawnmower.business.Position.PositionBuilder;

public class Lawnmower {

    private final int[][] field;

    public Lawnmower(int[][] field) {
        this.field = field;
    }

    public List<Position> mow(List<Instruction> instructions) {
        return instructions.stream()
                           .map(this::move)
                           .collect(Collectors.toList());
    }

    private Position move(Instruction instruction) {
        validate(instruction.position);
        mowCase(instruction.position.xAxis, instruction.position.yAxis);

        PositionBuilder builder = Position.builder(instruction.position);

        for (Instruction.Direction direction : instruction.directions) {
            switch (direction) {
                case straight:
                    straight(builder);
                    break;
                case left:
                    builder.compassPoint(builder.compassPoint.left());
                    break;
                case right:
                    builder.compassPoint(builder.compassPoint.right());
                    break;
                default:
                    throw new UnsupportedOperationException("Not suppose to happen, direction can only be straight, left or right.");
            }
        }

        return builder.build();
    }

    private void validate(Position position) {
        try {
            field[position.xAxis][position.yAxis] = 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new LawnmowerInitializationException(String.format("Cannot put Lawnmower on field at [%d][%d]. Field is %d %d.", position.xAxis, position.yAxis, field.length, field[0].length));
        }
    }


    private void straight(PositionBuilder builder) {
        switch (builder.compassPoint) {
            case northern:
                builder.yAxis++;
                break;
            case southern:
                builder.yAxis--;
                break;
            case eastern:
                builder.xAxis++;
                break;
            case western:
                builder.xAxis--;
                break;
            default:
                throw new UnsupportedOperationException("Not suppose to happen, compass point can only be northern, southern, eastern or western.");
        }

        mowCase(builder.xAxis, builder.yAxis);
    }

    private void mowCase(int xAxis, int yAxis) {
        try {
            field[xAxis][yAxis] = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new OutOfFieldException(String.format("Cannot mow out of the field [%d][%d]. Field is %d %d.", xAxis, yAxis, field.length, field[0].length), e);
        }
    }

    public int[][] getField() {
        return field;
    }
}
