package game;

import com.google.gson.Gson;
import game.gameobject.ActionOption;
import game.gameobject.OperatingGameObject;
import game.gameobject.Projectile;
import game.gameobject.entity.Heading;
import game.gameobject.entity.Player;
import game.graphics.AnimatedImage;
import game.graphics.AnimatedMovableImage;
import game.graphics.PixelImage;
import game.level.Level;
import game.util.KeyHandler;
import game.util.LevelManager;
import game.util.graphics.ButtonCreator;
import game.util.graphics.GraphicsManager;
import game.util.graphics.text.Announcer;
import game.util.properties.Properties;
import game.util.state.State;
import game.util.state.StateManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import static game.util.properties.Properties.*;
import static game.util.properties.SpriteNames.*;
import static game.util.properties.SpriteProperties.*;

public class GameLoop extends TimerTask {

    private Level level;
    private Player player;

    private KeyHandler keyHandler;
    private GraphicsManager graphicsManager;
    private Announcer announcer;
    private StateManager stateManager;
    private LevelManager levelManager;

    private AnimationTimer animationTimer;

    private static GameLoop INSTANCE;

    private Group group;

    private Rectangle pauseGameShadeOverlay;
    private ToggleButton saveGameButton;
    private ToggleButton continueButton;
    private ToggleButton endGameButton;

    private double distanceX;
    private double distanceY;

    private final static Logger LOG = Logger.getLogger(GameLoop.class);

    private GameLoop(Group g, Scene s, StateManager sm) {
        Canvas c = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.group = g;
        group.getChildren().add(c);

        levelManager = new LevelManager();
        level = levelManager.getCurrentLevel();
        if (levelManager.getCurrentLevel() == null) System.exit(0); // come on, thats just lazy writing
        loadLevelImages();

        player = createPlayer();

        this.keyHandler = new KeyHandler(s);
        keyHandler.initKeyHandling(player);
        this.graphicsManager = new GraphicsManager(c.getGraphicsContext2D());
        this.stateManager = sm;

        announcer = new Announcer(c.getGraphicsContext2D());
        /*announcer.addDialogue(player.getImg().getFrame(0), "Hello my friend. My name is Fnieuffient II, but friends call me Fnieuff.",
                System.nanoTime() + 5000000000L, 5000000000L);*/
        LOG.info("Creating game loop");
    }

    private void loadLevelImages() {
        level.getEnemies().forEach(enemy -> {
            for (Heading dir : Heading.values()) {
                AnimatedImage animatedImage = enemy.getMovableImage().getDirectedSprite(dir);
                animatedImage.loadSprites(animatedImage.getSpriteName(), animatedImage.getNumberOfSpritesToAnimate());
            }
            enemy.setDead(new PixelImage(enemy.getDeadSpriteName(), enemy.getWidth(), enemy.getHeight()));
        });
        level.getOperatingGameObjects().forEach(operatingGameObject -> {
            AnimatedImage objectImg = operatingGameObject.getImg();
            objectImg.loadSprites(objectImg.getSpriteName(), objectImg.getNumberOfSpritesToAnimate());
            if (operatingGameObject.getOpenImgName() != null) {
                operatingGameObject.setOpenImg(new PixelImage(operatingGameObject.getOpenImgName(), operatingGameObject.getWidth(), operatingGameObject.getHeight()));
            }
        });
    }

    /**
     * Returns an instance of singleton class GameLoop.
     *
     * @param group        JavaFX group
     * @param scene        JavaFX scene
     * @param stateManager manager that holds current state
     * @return instance of GameLoop
     */
    public static GameLoop getInstance(Group group, Scene scene, StateManager stateManager) {
        if (INSTANCE == null) {
            INSTANCE = new GameLoop(group, scene, stateManager);
        }
        return INSTANCE;
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        animationTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate > 1000000000 / FPS) {
                    run();
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
    }

    /**
     * This method is repetitively called by AnimationTimer.
     *
     * @see AnimationTimer
     */
    @Override
    public synchronized void run() {
        update();
        render();
    }

    private void update() {
        if (player.isDead()) {
            stopGame();
        } else {
            keyHandler.update(player, level, stateManager);
            player.validateShots();
            checkProjectileCollisions();
            player.getShots().forEach(Projectile::update);
            checkOperatingGameObjectCollisions();
            enemiesTrackPlayerMovement();
        }
    }

    private void render() {
        graphicsManager.clear();
        graphicsManager.renderBackground(level.getBackground());
        graphicsManager.renderOperatingGameObjects(level.getOperatingGameObjects());
        graphicsManager.renderEnemies(level.getEnemies());
        graphicsManager.renderProjectiles(player.getShots());
        graphicsManager.renderWalls(level.getWalls());
        graphicsManager.renderPlayer(player);
        graphicsManager.renderHP(player.getCurrentHealth(), player.getMAX_HEALTH());
        graphicsManager.renderInventory(player.getInventory());
        announcer.updateDialogues();
    }

    /**
     * Pauses game and creates Pause GUI.
     */
    public void pauseGame() {
        keyHandler.unbindKeyHandling();
        animationTimer.stop();

        pauseGameShadeOverlay = new Rectangle();
        pauseGameShadeOverlay.setX(0);
        pauseGameShadeOverlay.setY(0);
        pauseGameShadeOverlay.setWidth(WINDOW_WIDTH);
        pauseGameShadeOverlay.setHeight(WINDOW_HEIGHT);
        pauseGameShadeOverlay.setFill(new Color(0, 0, 0, 0.5));
        group.getChildren().add(pauseGameShadeOverlay);

        continueButton = ButtonCreator.createButton("ui/continue.png",
                WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 - 90);
        continueButton.setOnMouseClicked(event -> stateManager.changeState(State.GAME));
        group.getChildren().add(continueButton);

        saveGameButton = ButtonCreator.createButton("ui/save.png", "ui/save_clicked.png",
                WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 + 100 - 90);
        saveGameButton.setOnMouseClicked(event -> saveGame());
        group.getChildren().add(saveGameButton);

        endGameButton = ButtonCreator.createButton("ui/end.png",
                WINDOW_WIDTH / 2 - 80, WINDOW_HEIGHT / 2 + 110);
        endGameButton.setOnMouseClicked(event -> exitGame());
        group.getChildren().add(endGameButton);
    }

    /**
     * Continues game.
     */
    public void continueGame() {
        LOG.info("Stopping game");
        keyHandler.initKeyHandling(player);
        animationTimer.start();
        group.getChildren().remove(pauseGameShadeOverlay);
        group.getChildren().remove(saveGameButton);
        group.getChildren().remove(continueButton);
        group.getChildren().remove(endGameButton);
    }

    private void stopGame() {
        LOG.info("Stopping game");
        keyHandler.unbindKeyHandling();
        animationTimer.stop();
        stateManager.setState(State.MENU);
        INSTANCE = null; // so the level actually restarts
        Platform.runLater(() -> stateManager.update());
    }

    private void exitGame() {
        //saveGame();
        LOG.info("Exiting game");
        System.exit(0);
    }

    private void saveGame() {
        LOG.info("Saving game");
        Gson gson = new Gson();
        try {
            FileWriter playerWriter = new FileWriter(PLAYER_SAVE_FILE);
            gson.toJson(player, playerWriter);
            playerWriter.flush();
            playerWriter.close();
        } catch (IOException e) {
            LOG.error("Could not save player" + e);
        }
        try {
            FileWriter levelWriter = new FileWriter(LEVEL_SAVE_FILE);
            gson.toJson(level, levelWriter);
            levelWriter.flush();
            levelWriter.close();
        } catch (IOException e) {
            LOG.error("Could not save level" + e);
        }
        LOG.info("Game saved");
    }

    /**
     * Loads game from files located at destination specified in Properties file.
     *
     * @see Properties
     */
    public void loadGame() {
        Gson gson = new Gson();
        loadPlayer(gson);
        loadLevel(gson);
        pauseGame();
    }

    private void loadLevel(Gson gson) {
        try {
            level = gson.fromJson(new FileReader(LEVEL_SAVE_FILE), Level.class);
        } catch (FileNotFoundException e) {
            LOG.error("Could not load level " + e);
        }
        loadLevelImages();
    }

    private void loadPlayer(Gson gson) {
        try {
            player = gson.fromJson(new FileReader(Properties.PLAYER_SAVE_FILE), Player.class);
        } catch (FileNotFoundException e) {
            LOG.error("Could not load player: " + e);
        }
        for (Heading dir : Heading.values()) {
            AnimatedImage animatedImage = player.getMovableImage().getDirectedSprite(dir);
            animatedImage.loadSprites(animatedImage.getSpriteName(), animatedImage.getNumberOfSpritesToAnimate());
        }
        AnimatedImage playerImg = player.getImg();
        playerImg.loadSprites(playerImg.getSpriteName(), playerImg.getNumberOfSpritesToAnimate());
        player.setDead(new PixelImage(player.getDeadSpriteName(), player.getWidth(), player.getHeight()));
    }

    private void checkProjectileCollisions() {
        player.getShots().forEach(projectile -> {
            double update = projectile.getSpeed();
            if (projectile.getHeading() == Heading.LEFT || projectile.getHeading() == Heading.UP) {
                update = -projectile.getSpeed();
            } else if (projectile.getHeading() == Heading.RIGHT || projectile.getHeading() == Heading.DOWN) {
                update = projectile.getSpeed();
            }
            if (projectile.hitWall(level.getWalls(), update, projectile.getHeading())) {
                projectile.setToRemove(true);
            }
            level.getEnemies().forEach(enemy -> {
                if (enemy.collidesWith(projectile) && !enemy.isDead()) {
                    LOG.info(projectile.toString() + " hit " + enemy.toString() + " for " + player.getAttack() + " damage");
                    enemy.damaged(player.getAttack());
                    projectile.setToRemove(true);
                }
            });
        });
    }

    private void enemiesTrackPlayerMovement() {
        level.getEnemies().forEach(enemy -> {
            if (!enemy.isDead()) {
                distanceX = enemy.getPosX() - player.getPosX();
                distanceY = enemy.getPosY() - player.getPosY();
                if (Math.abs(distanceX) < 300 && Math.abs(distanceY) < 300) {
                    if (Math.abs(distanceX) >= Math.abs(distanceY)) {
                        if ((distanceX) < 0) {
                            enemy.updatePosX(enemy.getSpeed(), level.getWalls());
                            enemy.setHeading(Heading.RIGHT);
                        } else {
                            enemy.updatePosX(-enemy.getSpeed(), level.getWalls());
                            enemy.setHeading(Heading.LEFT);
                        }
                    } else {
                        if ((distanceY) < 0) {
                            enemy.updatePosY(enemy.getSpeed(), level.getWalls());
                            enemy.setHeading(Heading.DOWN);
                        } else {
                            enemy.updatePosY(-enemy.getSpeed(), level.getWalls());
                            enemy.setHeading(Heading.UP);
                        }
                    }
                } else {
                    if (enemy.getHeading() == Heading.LEFT) {
                        enemy.setHeading(Heading.IDLE_LEFT);
                    } else if (enemy.getHeading() == Heading.RIGHT) {
                        enemy.setHeading(Heading.IDLE_RIGHT);
                    } else if (enemy.getHeading() == Heading.UP) {
                        enemy.setHeading(Heading.IDLE_UP);
                    } else if (enemy.getHeading() == Heading.DOWN) {
                        enemy.setHeading(Heading.IDLE_DOWN);
                    }
                }
                if (enemy.collidesWith(player)) {
                    LOG.info(player + " collides with " + enemy);
                    player.damaged(enemy.getAttack());
                }
            }
        });
    }

    private void checkOperatingGameObjectCollisions() {
        boolean levelNotFinished = true;
        List<OperatingGameObject> toKeep = new ArrayList<>();
        for (OperatingGameObject operatingGameObject : level.getOperatingGameObjects()) {
            if (operatingGameObject.collidesWith(player)) {
                if (operatingGameObject.getActionOption() == ActionOption.COLLECT) {
                    player.addItemToInventory(operatingGameObject);
                    LOG.info("Player collected item: " + operatingGameObject);
                } else if (operatingGameObject.getActionOption() == ActionOption.COLLECT_UNLOCK) {
                    operatingGameObject.setActionOption(ActionOption.UNLOCK);
                    player.addItemToInventory(operatingGameObject);
                    LOG.info("Player collected key: " + operatingGameObject);
                } else if (operatingGameObject.getActionOption() == ActionOption.DAMAGE) {
                    player.damaged(1);
                    toKeep.add(operatingGameObject);
                    LOG.info("Player colliding with damaging object " + operatingGameObject);
                } else if (operatingGameObject.getActionOption() == ActionOption.OPEN && player.inventoryContainsItemType(ActionOption.UNLOCK)) {
                    operatingGameObject.setOpened(true);
                    toKeep.add(operatingGameObject);
                    player.removeItemOfType(ActionOption.UNLOCK);
                    LOG.info("Player unlocked door");
                } else if (operatingGameObject.getActionOption() == ActionOption.HEAL) {
                    player.heal();
                    LOG.info("Player healed 1 HP");
                } else if (operatingGameObject.getActionOption() == ActionOption.SPEEDUP) {
                    player.updateSpeed(SPEEDUP_POTION_VALUE);
                    LOG.info("Player healed 1 HP");
                } else if (operatingGameObject.isOpened()) {
                    levelNotFinished = false;
                    LOG.info("Player finished level " + level.getName());
                    announcer.addDialogue(player.getImg().getFrame(0), "Finished level " + level.getName() + ". Looking forward to beat the next one!",
                            System.nanoTime(), 5000000000L);
                    levelManager.nextLevel();

                    player = createPlayer();

                    levelManager = new LevelManager();
                    level = levelManager.getCurrentLevel();
                    if (levelManager.getCurrentLevel() == null) System.exit(0); // come on, thats just lazy writing
                    loadLevelImages();
                } else toKeep.add(operatingGameObject);
            } else toKeep.add(operatingGameObject);
        }
        if (levelNotFinished) level.setOperatingGameObjects(toKeep);
    }

    private Player createPlayer() {
        AnimatedImage spriteLeft = new AnimatedImage(ELF_M_RUN_LEFT, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        AnimatedImage spriteRight = new AnimatedImage(ELF_M_RUN_RIGHT, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        AnimatedImage spriteUp = new AnimatedImage(ELF_M_RUN_UP, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        AnimatedImage spriteDown = new AnimatedImage(ELF_M_RUN_DOWN, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        AnimatedImage spriteIdleLeft = new AnimatedImage(ELF_M_IDLE_LEFT, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        AnimatedImage spriteIdleRight = new AnimatedImage(ELF_M_IDLE_RIGHT, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        AnimatedImage spriteIdleUp = new AnimatedImage(ELF_M_IDLE_UP, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        AnimatedImage spriteIdleDown = new AnimatedImage(ELF_M_IDLE_DOWN, HERO_ANIM_COUNT, HERO_ANIM_DURATION, HERO_WIDTH, HERO_HEIGHT);
        return new Player(128, 128, PLAYER_SPEED, new AnimatedMovableImage(spriteLeft, spriteRight, spriteUp, spriteDown, spriteIdleLeft, spriteIdleRight, spriteIdleUp, spriteIdleDown), ELF_M_DEAD);
    }
}
