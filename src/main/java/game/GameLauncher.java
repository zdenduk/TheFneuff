package game;

import game.util.state.StateManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import static game.util.properties.Properties.APP_TITLE;
import static game.util.properties.Properties.LOGO;

public class GameLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application
     *
     * @param stage JavaFX stage
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle(APP_TITLE);
        stage.getIcons().add(new Image(LOGO));
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        Group root = new Group();
        Scene theScene = new Scene(root);

        StateManager sm = new StateManager(stage, root, theScene);
        sm.update();

        stage.show();
    }

}