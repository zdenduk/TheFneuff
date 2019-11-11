package game.gameobject.entity;

import game.graphics.PixelImage;
import game.level.Tile;
import game.level.TileMap;
import javafx.scene.image.Image;
import game.gameobject.GameObject;
import game.graphics.AnimatedMovableImage;

import static game.util.properties.Properties.WINDOW_HEIGHT;
import static game.util.properties.Properties.WINDOW_WIDTH;
import static game.util.properties.SpriteProperties.BLOCK_SIZE;

public class Entity extends GameObject {

    private final int MAX_HEALTH = 3;
    private int currentHealth;
    private int attack;
    private double speed;

    private AnimatedMovableImage movableImage;
    private Heading heading;
    private transient Image dead;

    private String deadSpriteName;

    /**
     * Creates instance of Entity with position set to (0,0) and attack set to 0.
     *
     * @param speed        entity speed
     * @param movableImage animated image for each direction the Entity can move
     * @param dead         image of dead entity
     * @param width        entity sprite width
     * @param height       entity sprite height
     */
    public Entity(double speed, AnimatedMovableImage movableImage, final String dead, int width, int height) {
        super(0, 0, movableImage.getDirectedSprite(Heading.LEFT), width, height);
        currentHealth = MAX_HEALTH;
        attack = 0;
        this.speed = speed;
        this.movableImage = movableImage;
        deadSpriteName = dead;
        this.dead = new PixelImage(dead, width, height);
        heading = Heading.IDLE_RIGHT;
    }

    /**
     * FOR TEST PURPOSES ONLY
     *
     * @param speed
     * @param width
     * @param height
     */
    @Deprecated
    Entity(double speed, int width, int height) {
        super(0, 0, null, width, height);
        currentHealth = MAX_HEALTH;
        attack = 0;
        this.speed = speed;
        heading = Heading.IDLE_RIGHT;
    }

    /**
     * Creates instance of Entity.
     *
     * @param posX          entity initial position x
     * @param posY          entity initial position y
     * @param currentHealth entity current health
     * @param attack        entity attack
     * @param speed         entity speed
     * @param movableImage  animated image for each direction the entity can moves
     * @param dead          image of dead entity
     * @param width         entity sprite width
     * @param height        entity sprite height
     */
    public Entity(double posX, double posY, int currentHealth, int attack, double speed, AnimatedMovableImage movableImage, final String dead, int width, int height) {
        super(posX, posY, movableImage.getDirectedSprite(Heading.IDLE_RIGHT), width, height);
        this.currentHealth = currentHealth;
        this.attack = attack;
        this.speed = speed;
        this.movableImage = movableImage;
        deadSpriteName = dead;
        this.dead = new PixelImage(dead, width, height);
        heading = Heading.IDLE_RIGHT;
    }


    /**
     * Updates position x by parameter update.
     *
     * @param update amount of movement in direction x
     */
    public void updatePosX(double update, TileMap walls) {
        // TODO check for wall collisions
        super.updatePosX(update);
    }

    /**
     * Updates position y by parameter update.
     *
     * @param update amount of movement in direction y
     */
    public void updatePosY(double update, TileMap walls) {
        // TODO check for wall collisions
        super.updatePosY(update);
    }

    /**
     * Damage entity for given amount of HP.
     *
     * @param hp damage taken
     */
    public void damaged(int hp) {
        currentHealth -= hp;
    }

    /**
     * @param dead image of dead entity
     */
    public void setDead(PixelImage dead) {
        this.dead = dead;
    }

    /**
     * @return url of dead sprite
     */
    public String getDeadSpriteName() {
        return deadSpriteName;
    }

    /**
     * @param heading direction the entity is facing
     */
    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    /**
     * @return true if entity is dead, false otherwise
     */
    public boolean isDead() {
        return getCurrentHealth() <= 0;
    }

    /**
     * @return direction the entity is facing
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * @return animated image for each direction the entity can move
     */
    public AnimatedMovableImage getMovableImage() {
        return movableImage;
    }

    /**
     * @return current health of entity
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * @return attack of entity
     */
    public int getAttack() {
        return attack;
    }

    /**
     * @return image of dead entity
     */
    public Image getDeadImage() {
        return dead;
    }

    /**
     * @return maximum health of entity
     */
    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    /**
     * Heal 1 life unless health is full.
     */
    public void heal() {
        // TODO implement this
    }

    /**
     * @return speed of entity
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Add speed to entity for 5 seconds.
     *
     * @param update bonus speed for entity
     */
    public void updateSpeed(double update) {
        // TODO implement this
    }
}
