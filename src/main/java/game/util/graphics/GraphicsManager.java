package game.util.graphics;

import game.gameobject.OperatingGameObject;
import game.gameobject.Projectile;
import game.gameobject.entity.Entity;
import game.gameobject.entity.Player;
import game.graphics.PixelImage;
import game.level.Tile;
import game.level.TileMap;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import static game.util.properties.Properties.WINDOW_HEIGHT;
import static game.util.properties.Properties.WINDOW_WIDTH;
import static game.util.properties.SpriteProperties.*;

public class GraphicsManager {

    private GraphicsContext gc;

    private long startNanoTime;

    private PixelImage fullHearthImage;
    private PixelImage emptyHearthImage;
    private Font f;

    /**
     * Creates instance of GraphicsManager.
     *
     * @param gc graphics context
     */
    public GraphicsManager(GraphicsContext gc) {
        this.gc = gc;
        this.startNanoTime = System.nanoTime();

        fullHearthImage = new PixelImage("game/ui_heart_full.png", HEARTH_WIDTH, HEARTH_HEIGHT);
        emptyHearthImage = new PixelImage("game/ui_heart_empty.png", HEARTH_WIDTH, HEARTH_HEIGHT);

        try {
            f = Font.loadFont(new FileInputStream(new File("FaceType - Loki.ttf")), 32);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT); // canvas width, canvas height
    }

    /**
     * Renders background of level.
     *
     * @param bg background
     * @see TileMap
     */
    public void renderBackground(TileMap bg) {
        Tile[][] map = bg.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                gc.drawImage(map[i][j].getImg(), i * BLOCK_SIZE, j * BLOCK_SIZE);
            }
        }
    }

    /**
     * Renders operating game objects (e.g. items, doors, spikes etc.).
     *
     * @param operatingGameObjects list of operating gameobjects
     */
    public void renderOperatingGameObjects(List<OperatingGameObject> operatingGameObjects) {
        operatingGameObjects.forEach(gameObject -> {
            if (!gameObject.isOpened()) {
                gc.drawImage(gameObject.getImg().getFrame((System.nanoTime() - startNanoTime) / 1000000000.0),
                        gameObject.getPosX(), gameObject.getPosY());
            } else {
                gc.drawImage(gameObject.getOpenImg(), gameObject.getPosX(), gameObject.getPosY());
                System.out.println("why");
            }
        });
    }

    /**
     * Renders player.
     *
     * @param player instance of player
     */
    public void renderPlayer(Player player) {
        if (!player.isDead()) {
            gc.drawImage(
                    player.getMovableImage().getDirectedSprite(player.getHeading()).getFrame(((System.nanoTime() - startNanoTime) / 1000000000.0)),
                    player.getPosX(), player.getPosY());
        } else {
            gc.drawImage(player.getDeadImage(), player.getPosX(), player.getPosY());
        }
    }

    /**
     * Renders projectiles.
     *
     * @param projectiles list of projectiles
     */
    public void renderProjectiles(List<Projectile> projectiles) {
        projectiles.forEach(projectile -> gc.drawImage(projectile.getImg().getFrame((System.nanoTime() - startNanoTime) / 1000000000.0), projectile.getPosX(), projectile.getPosY()));
    }

    /**
     * Draws symbols of full hearts for current health and symbols of empty hearts for missing health.
     *
     * @param curHealth current health
     * @param maxHealth maximum health
     */
    public void renderHP(int curHealth, int maxHealth) {
        for (int i = 0; i < curHealth; i++) {
            gc.drawImage(fullHearthImage, 10 + i * HEARTH_WIDTH, WINDOW_HEIGHT - 10 - HEARTH_HEIGHT);
        }
        for (int i = 0; i < maxHealth - curHealth; i++) {
            gc.drawImage(emptyHearthImage, curHealth * HEARTH_WIDTH + 10 + i * HEARTH_WIDTH, WINDOW_HEIGHT - 10 - HEARTH_HEIGHT);
        }
    }

    public void renderInventory(List<OperatingGameObject> inventory) {
        gc.setFont(f);
        gc.setFill(Color.WHITE);
        for (int i = 0; i < inventory.size(); i++) {
            OperatingGameObject object = inventory.get(i);
            gc.drawImage(object.getImg().getFrame(0), 240 + i * 2 * BLOCK_SIZE + 10, WINDOW_HEIGHT - 10 - BLOCK_SIZE);
            gc.fillText("x1", 240 + i * BLOCK_SIZE + 10 + BLOCK_SIZE * (i + 1), WINDOW_HEIGHT - 10 - BLOCK_SIZE / 2);
        }
    }

    /**
     * Draws image on given position.
     *
     * @param posx  position x
     * @param posy  position y
     * @param image image to be rendered
     */
    public void drawImage(double posx, double posy, Image image) {
        gc.drawImage(image, posx, posy);
    }

    /**
     * Renders enemies.
     *
     * @param enemies list of entities
     */
    public void renderEnemies(List<Entity> enemies) {
        enemies.forEach(enemy -> {
            if (!enemy.isDead()) {
                gc.drawImage(
                        enemy.getMovableImage().getDirectedSprite(enemy.getHeading()).getFrame(((System.nanoTime() - startNanoTime) / 1000000000.0)),
                        enemy.getPosX(), enemy.getPosY());
            } else {
                gc.drawImage(enemy.getDeadImage(), enemy.getPosX(), enemy.getPosY());
            }
        });
    }

    public void renderWalls(TileMap walls) {
        if (walls != null) {
            Tile[][] map = walls.getMap();
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] != null) gc.drawImage(map[i][j].getImg(), i * BLOCK_SIZE, j * BLOCK_SIZE);
                }
            }
        }
    }
}
