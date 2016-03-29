package org.jbbouille.lawnmower.parser;

import static org.jbbouille.lawnmower.business.CompassPoints.eastern;
import static org.jbbouille.lawnmower.business.CompassPoints.northern;
import static org.jbbouille.lawnmower.business.CompassPoints.southern;
import static org.jbbouille.lawnmower.business.CompassPoints.western;
import static org.jbbouille.lawnmower.business.Instruction.Direction.left;
import static org.jbbouille.lawnmower.business.Instruction.Direction.right;
import static org.jbbouille.lawnmower.business.Instruction.Direction.straight;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jbbouille.lawnmower.business.CompassPoints;
import org.jbbouille.lawnmower.business.Instruction;
import org.jbbouille.lawnmower.business.Instruction.Direction;
import org.jbbouille.lawnmower.business.Position;

public class InstructionParser {

    private final Path instructionPathFile;

    public InstructionParser(Path instructionPathFile) {
        this.instructionPathFile = instructionPathFile;
    }

    public List<Instruction> instructions() {
        List<String> input = lines().collect(Collectors.toList());
        List<Instruction> instructions = new ArrayList<>();

        input.remove(0);

        Position position = extractPosition(input.get(0));
        for (int i = 0; i < input.size(); i++) {
            if (isPositionLine(i)) {
                position = extractPosition(input.get(i));
                continue;
            }
            List<Direction> directions = extractDirections(input.get(i));

            instructions.add(new Instruction(position, directions));
        }

        return instructions;
    }

    private List<Direction> extractDirections(String s) {
        return s.codePoints()
                .filter(codePoint -> !Character.isWhitespace(codePoint))
                .map(Character::toLowerCase)
                .mapToObj(this::toDirection)
                .collect(Collectors.toList());
    }

    private Direction toDirection(int i) {
        switch (i) {
            case 's':
                return straight;
            case 'l':
                return left;
            case 'r':
                return right;
            default:
                throw new ParserException("Direction can only be of type s, r or l.");
        }
    }

    private Position extractPosition(String s) {
        List<Integer> values = s.codePoints()
                                .filter(codePoint -> !Character.isWhitespace(codePoint))
                                .map(Character::toLowerCase)
                                .boxed()
                                .collect(Collectors.toList());
        if (values.size() != 3) {
            throw new ParserException("Cannot parse position.");
        }

        return new Position(Character.digit(values.get(0), 10), Character.digit(values.get(1), 10), compassPoint(values.get(2)));
    }

    private CompassPoints compassPoint(int integer) {
        switch (integer) {
            case 'e':
                return eastern;
            case 's':
                return southern;
            case 'w':
                return western;
            case 'n':
                return northern;
            default:
                throw new ParserException("Cannot parse compass point.");
        }
    }

    private boolean isPositionLine(int i) {
        return i % 2 == 0;
    }

    private Stream<String> lines() {
        try {
            return Files.lines(instructionPathFile);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public int[][] field() {
        return lines().map(this::toField)
                      .findFirst()
                      .orElseThrow(() -> new ParserException("Cannot initialize field."));
    }

    private int[][] toField(String l) {
        List<Integer> values = l.codePoints()
                                .filter(codePoint -> !Character.isWhitespace(codePoint))
                                .filter(Character::isDigit)
                                .map(cp -> Character.digit(cp, 10))
                                .boxed()
                                .collect(Collectors.toList());

        if (values.size() != 2) {
            throw new ParserException("Wrong argument for Field initialisation.");
        }
        return new int[values.get(0) + 1][values.get(1) + 1];
    }
}
