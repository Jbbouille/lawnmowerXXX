package org.jbbouille.lawnmower.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.jbbouille.lawnmower.business.Instruction;
import org.jbbouille.lawnmower.business.Lawnmower;
import org.jbbouille.lawnmower.business.Position;
import org.jbbouille.lawnmower.parser.InstructionParser;

public class Main {
    public static void main(String[] args) {
        String first = args.length > 0 && args[0].isEmpty() ? args[0] : "src/test/resources/instruction";

        Path filePath = Paths.get(first);
        InstructionParser parser = new InstructionParser(filePath);

        int[][] field = parser.field();
        List<Instruction> instructions = parser.instructions();

        List<Position> mowMow = new Lawnmower(field).mow(instructions);

        System.out.println(mowMow);
    }
}
