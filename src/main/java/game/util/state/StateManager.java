package game.util.state;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * Manager that holds current state of application.
 */
public class StateManager {

    private Stage stage;
    private Group root;
    private Scene scene;

    private State state;
    private State prevState;

    private GameState gs;

    private final static Logger LOG = Logger.getLogger(StateManager.class);

    /**
     * Creates instance of StateManager.
     *
     * @param stage JavaFX stage
     * @param root  JavaFX group
     * @param scene JavaFX scene
     */
    public StateManager(Stage stage, Group root, Scene scene) {
        this.stage = stage;
        this.root = root;
        this.scene = scene;

        this.state = State.MENU;
        this.prevState = null;
    }

    /**
     * Changes state to another state.
     *
     * @param state state to be changed to
     */
    public void changeState(State state) {
        setState(state);
        update();
        LOG.info("State change to: " + state);
    }

    /**
     * @param state state to be set
     */
    public void setState(State state) {
        this.prevState = this.state;
        this.state = state;
    }

    /**
     * Decides which window to show depending on current state.
     */
    public void update() {
        switch (state) {
            case MENU:
                new MenuState(stage, this);
                break;
            case GAME:
                if (prevState == State.MENU)
                    gs = new GameState(stage, root, scene, this);
                else gs.continueGame();
                break;
            case PAUSE:
                gs.pauseGame();
                break;
            case LOAD:
                gs = new GameState(stage, root, scene, this);
                gs.loadGame();
        }
    }
}
