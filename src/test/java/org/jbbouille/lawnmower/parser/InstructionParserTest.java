package org.jbbouille.lawnmower.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.jbbouille.lawnmower.business.CompassPoints.eastern;
import static org.jbbouille.lawnmower.business.CompassPoints.northern;
import static org.jbbouille.lawnmower.business.Instruction.Direction.left;
import static org.jbbouille.lawnmower.business.Instruction.Direction.right;
import static org.jbbouille.lawnmower.business.Instruction.Direction.straight;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jbbouille.lawnmower.business.Instruction;
import org.junit.Test;

public class InstructionParserTest {

    @Test
    public void should_extract_field() throws Exception {
        // Given
        Path filePath = Paths.get("src/test/resources/instruction");
        InstructionParser parser = new InstructionParser(filePath);

        // When
        int[][] field = parser.field();

        // Then
        assertThat(field).contains(new int[6][6]);
    }

    @Test
    public void should_extract_instruction() throws Exception {
        // Given
        Path filePath = Paths.get("src/test/resources/instruction");
        InstructionParser parser = new InstructionParser(filePath);

        // When
        List<Instruction> instructions = parser.instructions();

        // Then
        assertThat(instructions).extracting(i -> i.position).extracting(p -> p.xAxis).contains(1, 3);
        assertThat(instructions).extracting(i -> i.position).extracting(p -> p.yAxis).contains(2, 3);
        assertThat(instructions).extracting(i -> i.position).extracting(p -> p.compassPoint).contains(northern, eastern);
        assertThat(instructions).extracting(i -> i.directions).contains(Arrays.asList(left, straight, left, straight, left, straight, left, straight, straight),
                                                                        Arrays.asList(straight, straight, right, straight, straight, right, straight, right, right, straight));
    }

    @Test
    public void should_fail_when_bad_instruction_file() throws Exception {
        // Given
        Path filePath = Paths.get("src/test/resources/instructionFail");
        InstructionParser parser = new InstructionParser(filePath);

        // When
        ThrowingCallable error = () -> parser.field();

        // Then
        assertThatThrownBy(error).isInstanceOf(ParserException.class);
    }

}