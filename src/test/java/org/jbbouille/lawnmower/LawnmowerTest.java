package org.jbbouille.lawnmower;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.jbbouille.lawnmower.business.CompassPoints.eastern;
import static org.jbbouille.lawnmower.business.CompassPoints.northern;
import static org.jbbouille.lawnmower.business.CompassPoints.western;
import static org.jbbouille.lawnmower.business.Instruction.Direction.left;
import static org.jbbouille.lawnmower.business.Instruction.Direction.right;
import static org.jbbouille.lawnmower.business.Instruction.Direction.straight;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.jbbouille.lawnmower.business.Instruction;
import org.jbbouille.lawnmower.business.Lawnmower;
import org.jbbouille.lawnmower.business.LawnmowerInitializationException;
import org.jbbouille.lawnmower.business.OutOfFieldException;
import org.jbbouille.lawnmower.business.Position;
import org.junit.Test;

public class LawnmowerTest {

    @Test
    public void first_example_test() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[6][6]);
        Instruction instruction = new Instruction(new Position(1, 2, northern), Arrays.asList(left, straight, left, straight, left, straight, left, straight, straight));

        // When
        List<Position> positions = lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThat(positions).extracting(p -> p.compassPoint).containsExactly(northern);
        assertThat(positions).extracting(p -> p.xAxis).containsExactly(1);
        assertThat(positions).extracting(p -> p.yAxis).containsExactly(3);
    }

    @Test
    public void second_example_test() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[6][6]);
        Instruction instruction = new Instruction(new Position(3, 3, eastern), Arrays.asList(straight, straight, right, straight, straight, right, straight, right, right, straight));

        // When
        List<Position> positions = lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThat(positions).extracting(p -> p.compassPoint).containsExactly(eastern);
        assertThat(positions).extracting(p -> p.xAxis).containsExactly(5);
        assertThat(positions).extracting(p -> p.yAxis).containsExactly(1);
    }

    @Test
    public void should_error_when_go_outside_of_the_field() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[5][5]);
        Instruction instruction = new Instruction(new Position(0, 0, northern), Arrays.asList(straight, straight, straight, straight, straight, straight));

        // When
        ThrowingCallable error = () -> lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThatThrownBy(error).isInstanceOf(OutOfFieldException.class);
    }

    @Test
    public void should_error_when_initial_position_outside_of_the_field() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[3][3]);
        Instruction instruction = new Instruction(new Position(3, 5, northern), Arrays.asList(straight, straight));

        // When
        ThrowingCallable error = () -> lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThatThrownBy(error).isInstanceOf(LawnmowerInitializationException.class);
    }

    @Test
    public void should_go_straight() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[2][2]);
        Instruction instruction = new Instruction(new Position(0, 0, northern), Arrays.asList(straight));

        // When
        List<Position> positions = lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThat(positions).extracting(p -> p.compassPoint).containsExactly(northern);
        assertThat(positions).extracting(p -> p.xAxis).containsExactly(0);
        assertThat(positions).extracting(p -> p.yAxis).containsExactly(1);
    }

    @Test
    public void should_turn_left() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[2][2]);
        Instruction instruction = new Instruction(new Position(0, 0, northern), Arrays.asList(left));

        // When
        List<Position> positions = lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThat(positions).extracting(p -> p.compassPoint).containsExactly(western);
        assertThat(positions).extracting(p -> p.xAxis).containsExactly(0);
        assertThat(positions).extracting(p -> p.yAxis).containsExactly(0);
    }

    @Test
    public void should_turn_right() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[2][2]);
        Instruction instruction = new Instruction(new Position(0, 0, northern), Arrays.asList(right));

        // When
        List<Position> positions = lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThat(positions).extracting(p -> p.compassPoint).containsExactly(eastern);
        assertThat(positions).extracting(p -> p.xAxis).containsExactly(0);
        assertThat(positions).extracting(p -> p.yAxis).containsExactly(0);
    }

    @Test
    public void should_mow_the_field() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(new int[3][3]);
        Instruction instruction = new Instruction(new Position(0, 0, northern), Arrays.asList(straight, straight));

        // When
        lawnmower.mow(Arrays.asList(instruction));

        // Then
        assertThat(lawnmower.getField()).contains(new int[]{1, 1, 1}, new int[]{0, 0, 0}, new int[]{0, 0, 0});
    }
}