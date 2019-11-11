package game.gameobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameObjectTest {

    private GameObject object1;
    private GameObject object2;
    private GameObject object3;

    @BeforeEach
    void setUp() {
        object1 = new GameObject(10, 10, null, 20, 20);
        object2 = new GameObject(20, 20, null, 10, 10);
        object3 = new GameObject(100, 100, null, 100, 100);
    }

    @Test
    void collidesWith_True_CollidingObjects() {
        assertTrue(object1.collidesWith(object2));
    }

    @Test
    void collidesWith_False_NotCollidingObjects() {
        assertFalse(object1.collidesWith(object3));
    }
}