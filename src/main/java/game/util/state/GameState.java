package game.util.state;

import game.GameLoop;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Game window that wraps game loop.
 */
public class GameState {

    private GameLoop game;

    /**
     * Creates instance of GameState.
     *
     * @param stage        JavaFX stage
     * @param root         JavaFX group
     * @param scene        JavaFX scene
     * @param stateManager manager that holds current state
     */
    public GameState(Stage stage, Group root, Scene scene, StateManager stateManager) {
        game = GameLoop.getInstance(root, scene, stateManager);
        game.start();
        stage.setScene(scene);
        stage.setFullScreen(true);
    }

    /**
     * Pauses game loop.
     */
    public void pauseGame() {
        game.pauseGame();
    }

    /**
     * Continues game loop.
     */
    public void continueGame() {
        game.continueGame();
    }

    /**
     * Loads game from save file.
     */
    public void loadGame() {
        game.loadGame();
    }
}
