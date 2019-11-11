package game.gameobject.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void damaged_LessHPByOne_PlayerFullHealth() {
        int healthBeforeDamage = player.getCurrentHealth();
        player.damaged(1);
        assertEquals(player.getCurrentHealth() + 1, healthBeforeDamage);
    }

    @Test
    void damaged_LessHPByOne_PlayerFullHealthDamagedTwiceAtOnce() {
        int healthBeforeDamage = player.getCurrentHealth();
        player.damaged(1);
        player.damaged(1);
        assertEquals(player.getCurrentHealth() + 1, healthBeforeDamage);
    }
}