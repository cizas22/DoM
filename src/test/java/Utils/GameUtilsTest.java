package Utils;

import Models.GameDto;
import org.junit.jupiter.api.Test;

import static Utils.GameUtils.isDead;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameUtilsTest {
    @Test
    void checkIfPlayerDead() {
        var gameDto = GameDto.builder().lives(0).build();
        assertTrue(isDead(gameDto));
    }

}