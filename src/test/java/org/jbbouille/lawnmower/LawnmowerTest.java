package org.jbbouille.lawnmower;

import static org.jbbouille.lawnmower.CompassPoints.Point.northern;
import static org.jbbouille.lawnmower.Instruction.Direction.straight;
import static org.jbbouille.lawnmower.Instruction.Direction.left;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class LawnmowerTest {

    @Test
    public void should_get_me_the_good() throws Exception {
        // Given
        Lawnmower lawnmower = new Lawnmower(5, 5);
        Instruction instruction = new Instruction(new Position(1, 2, new CompassPoints(northern)), Arrays.asList(left, straight, left, straight, left, straight, left, straight, straight));

        // When
        List<Position> positions = lawnmower.mow(Arrays.asList(instruction));

        // Then
        System.out.println(positions.get(0));
//        assertThat(positions).extracting(p -> p).containsExactly();
    }
}