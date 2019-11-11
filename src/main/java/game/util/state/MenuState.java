package game.util.state;

import game.util.graphics.ButtonCreator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static game.util.properties.Properties.WINDOW_HEIGHT;
import static game.util.properties.Properties.WINDOW_WIDTH;

/**
 * Menu window.
 */
public class MenuState {

    /**
     * Creates instance of MenuState.
     *
     * @param stage        JavaFX stage
     * @param stateManager manager that holds current state
     */
    public MenuState(Stage stage, StateManager stateManager) {
        final ToggleButton startGameButton = ButtonCreator.createButton("ui/start.png",
                WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 - 90);
        startGameButton.setOnMouseClicked(event -> startGame(stateManager));

        final ToggleButton loadGameButton = ButtonCreator.createButton("ui/load.png", "ui/load_clicked.png",
                WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 - 90 + 100);
        loadGameButton.setOnMouseClicked(event -> loadGame(stateManager));

        final ToggleButton exitGameButton = ButtonCreator.createButton("ui/end.png",
                WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 - 90 + 200);
        exitGameButton.setOnMouseClicked(event -> System.exit(0));

        VBox vbox = new VBox(10);
        vbox.setMinWidth(WINDOW_WIDTH);
        vbox.setMinHeight(WINDOW_HEIGHT);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startGameButton, loadGameButton, exitGameButton);

        StackPane stackPane = new StackPane();
        stackPane.setMinWidth(WINDOW_WIDTH);
        stackPane.setMinHeight(WINDOW_HEIGHT);
        stackPane.getChildren().add(vbox);
        StackPane.setAlignment(vbox, Pos.CENTER);

        stage.setScene(new Scene(stackPane));
        stage.setFullScreen(true);
    }

    /**
     * Starts game.
     *
     * @param stateManager manager that holds current state
     */
    private void startGame(StateManager stateManager) {
        stateManager.changeState(State.GAME);
    }

    /**
     * Loads game from save file.
     *
     * @param stateManager manager that holds current state
     */
    private void loadGame(StateManager stateManager) {
        stateManager.changeState(State.LOAD);
    }
}
