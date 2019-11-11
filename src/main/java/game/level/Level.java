package game.level;

import game.gameobject.OperatingGameObject;
import game.gameobject.entity.Entity;

import java.util.List;

/**
 * Each instance represents one level in the game.
 */
public class Level {
    private String name;

    private TileMap background;
    private TileMap walls;
    private List<OperatingGameObject> operatingGameObjects;
    private List<Entity> enemies;

    /**
     * Creates instance of Level.
     */
    public Level() {
    }

    /**
     * Creates instance of level.
     *
     * @param name                 name of level
     * @param background           level background
     * @param walls                walls in level
     * @param operatingGameObjects operating game objects in level (e.g. spikes, coins etc.)
     * @param enemies              enemy entities in level
     */
    public Level(String name, TileMap background, TileMap walls, List<OperatingGameObject> operatingGameObjects, List<Entity> enemies) {
        this.name = name;
        this.background = background;
        this.walls = walls;
        this.operatingGameObjects = operatingGameObjects;
        this.enemies = enemies;
    }

    public Level(String name, TileMap background, List<OperatingGameObject> operatingGameObjects, List<Entity> enemies) {
        this.name = name;
        this.background = background;
        this.operatingGameObjects = operatingGameObjects;
        this.enemies = enemies;
    }

    public String getName() {
        return name;
    }

    public List<Entity> getEnemies() {
        return enemies;
    }

    public TileMap getBackground() {
        return background;
    }

    public List<OperatingGameObject> getOperatingGameObjects() {
        return operatingGameObjects;
    }

    public void setOperatingGameObjects(List<OperatingGameObject> operatingGameObjects) {
        this.operatingGameObjects = operatingGameObjects;
    }

    public void setWalls(TileMap walls) {
        this.walls = walls;
    }

    public TileMap getWalls() {
        return walls;
    }
}
