package game.util;

import game.gameobject.entity.Heading;
import game.gameobject.entity.Player;
import game.level.Level;
import game.util.state.State;
import game.util.state.StateManager;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * Handles key inputs from user.
 */
public class KeyHandler {

    private Scene scene;

    private final BooleanProperty spacePressed = new SimpleBooleanProperty(false);
    private final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
    private final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
    private final BooleanProperty upPressed = new SimpleBooleanProperty(false);
    private final BooleanProperty downPressed = new SimpleBooleanProperty(false);
    private final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
    private final BooleanBinding spaceAndLeftPressed = spacePressed.and(leftPressed);
    private final BooleanBinding spaceAndUpPressed = spacePressed.and(upPressed);
    private final BooleanBinding spaceAndDownPressed = spacePressed.and(downPressed);
    private final BooleanProperty escapePressed = new SimpleBooleanProperty(false);

    /**
     * Creates instance of KeyHandler.
     *
     * @param scene JavaFX scene
     */
    public KeyHandler(Scene scene) {
        this.scene = scene;
    }

    /**
     * Initializes key handling from user.
     *
     * @param player player instance
     */
    public void initKeyHandling(Player player) {
        escapePressed.set(false); // setKeyReleased does not work on escape at all idk why
        scene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.SPACE) {
                spacePressed.set(true);
            } else if (ke.getCode() == KeyCode.D) {
                rightPressed.set(true);
            } else if (ke.getCode() == KeyCode.A) {
                leftPressed.set(true);
            } else if (ke.getCode() == KeyCode.W) {
                upPressed.set(true);
            } else if (ke.getCode() == KeyCode.S) {
                downPressed.set(true);
            } else if (ke.getCode() == KeyCode.ESCAPE) {
                escapePressed.set(true);
            }
        });

        scene.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.SPACE) {
                spacePressed.set(false);
            } else if (ke.getCode() == KeyCode.D) {
                rightPressed.set(false);
                player.setHeading(Heading.IDLE_RIGHT);
            } else if (ke.getCode() == KeyCode.A) {
                leftPressed.set(false);
                player.setHeading(Heading.IDLE_LEFT);
            } else if (ke.getCode() == KeyCode.W) {
                upPressed.set(false);
                player.setHeading(Heading.IDLE_UP);
            } else if (ke.getCode() == KeyCode.S) {
                downPressed.set(false);
                player.setHeading(Heading.IDLE_DOWN);
            } else if (ke.getCode() == KeyCode.ESCAPE) {
                escapePressed.set(false);
            }
        });
    }

    /**
     * Updates player position depending on user input.
     *
     * @param player       instance of player
     * @param stateManager manager that holds current state
     */
    public void update(Player player, Level level, StateManager stateManager) {
        if (spaceAndLeftPressed.get()) {
            player.setHeading(Heading.LEFT);
            player.updatePosX(-player.getSpeed(), level.getWalls());
            player.fire();
        } else if (spaceAndRightPressed.get()) {
            player.setHeading(Heading.RIGHT);
            player.updatePosX(player.getSpeed(), level.getWalls());
            player.fire();
        } else if (spaceAndUpPressed.get()) {
            player.setHeading(Heading.UP);
            player.updatePosY(-player.getSpeed(), level.getWalls());
            player.fire();
        } else if (spaceAndDownPressed.get()) {
            player.setHeading(Heading.DOWN);
            player.updatePosY(player.getSpeed(), level.getWalls());
            player.fire();
        } else if (spacePressed.get()) {
            player.fire();
        } else if (leftPressed.get()) {
            player.setHeading(Heading.LEFT);
            player.updatePosX(-player.getSpeed(), level.getWalls());
        } else if (rightPressed.get()) {
            player.setHeading(Heading.RIGHT);
            player.updatePosX(player.getSpeed(), level.getWalls());
        } else if (upPressed.get()) {
            player.setHeading(Heading.UP);
            player.updatePosY(-player.getSpeed(), level.getWalls());
        } else if (downPressed.get()) {
            player.setHeading(Heading.DOWN);
            player.updatePosY(player.getSpeed(), level.getWalls());
        }
        if (escapePressed.get()) {
            stateManager.setState(State.PAUSE);
            stateManager.update();
        }
    }

    /**
     * Unbinds all key binds.
     */
    public void unbindKeyHandling() {
        scene.setOnKeyPressed(e -> {
        });
        scene.setOnKeyReleased(e -> {
        });
    }

}
