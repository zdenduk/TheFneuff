package game.gameobject.entity;

import game.gameobject.ActionOption;
import game.gameobject.GameObject;
import game.gameobject.OperatingGameObject;
import game.gameobject.Projectile;
import game.graphics.AnimatedImage;
import game.graphics.AnimatedMovableImage;
import game.level.TileMap;

import java.util.ArrayList;
import java.util.List;

import static game.util.properties.Properties.WINDOW_HEIGHT;
import static game.util.properties.Properties.WINDOW_WIDTH;
import static game.util.properties.SpriteNames.FIREBALL;
import static game.util.properties.SpriteProperties.*;

public class Player extends Entity {
    private List<OperatingGameObject> inventory;
    private List<Projectile> shots;

    private long timeLastFired;
    private long timeLastDamaged;

    /**
     * Creates instance of Player.
     *
     * @param posX         player initial position x
     * @param posY         player initial position y
     * @param speed        player speed
     * @param movableImage animated image for each direction the player can move
     * @param dead         image of dead player
     */
    public Player(double posX, double posY, double speed, AnimatedMovableImage movableImage, final String dead) {
        super(posX, posY, 3, 1, speed, movableImage, dead, movableImage.getDirectedSprite(Heading.LEFT).getWidth(), movableImage.getDirectedSprite(Heading.LEFT).getHeight());
        this.inventory = new ArrayList<>();
        this.shots = new ArrayList<>();
        timeLastFired = System.nanoTime();
    }

    /**
     * Creates instance of Player with position set to (0,0).
     *
     * @param speed        player speed
     * @param movableImage animated image for each direction the player can move
     * @param dead         image of dead player
     */
    public Player(double speed, AnimatedMovableImage movableImage, final String dead) {
        super(speed, movableImage, dead, movableImage.getDirectedSprite(Heading.LEFT).getWidth(), movableImage.getDirectedSprite(Heading.LEFT).getHeight());
        this.inventory = new ArrayList<>();
        this.shots = new ArrayList<>();
        timeLastFired = System.nanoTime();
    }

    /**
     * FOR TEST PURPOSES ONLY
     */
    @Deprecated
    Player() {
        super(0, 10, 10);
        this.inventory = new ArrayList<>();
        this.shots = new ArrayList<>();
        timeLastFired = System.nanoTime();
    }

    /**
     * Damage player for given amount of HP, but only once in a second (protection against instant death).
     *
     * @param hp damage taken
     */
    @Override
    public void damaged(int hp) {
        // TODO implement
    }

    /**
     * Player fires a projectile in direction in which he is standing.
     *
     * @see Projectile
     */
    public void fire() {
        Heading projectileHeading = getHeading();
        if (projectileHeading == Heading.IDLE_RIGHT) projectileHeading = Heading.RIGHT;
        else if (projectileHeading == Heading.IDLE_LEFT) projectileHeading = Heading.LEFT;
        else if (projectileHeading == Heading.IDLE_UP) projectileHeading = Heading.UP;
        else if (projectileHeading == Heading.IDLE_DOWN) projectileHeading = Heading.DOWN;
        long currentTime = System.nanoTime();
        if (currentTime - timeLastFired > 500000000) {
            shots.add(new Projectile(getPosX() + (HERO_WIDTH >> 2), getPosY() + (HERO_HEIGHT >> 2) + (FIREBALL_HEIGHT >> 1),
                    new AnimatedImage(FIREBALL, FIREBALL_ANIM_COUNT, FIREBALL_ANIM_DURATION, FIREBALL_WIDTH, FIREBALL_HEIGHT),
                    ActionOption.DAMAGE, projectileHeading, 30, FIREBALL_WIDTH, FIREBALL_HEIGHT));
            timeLastFired = currentTime;
        }
    }

    /**
     * Validates if shots should be kept for another iteration of GameLoop.
     */
    public void validateShots() {
        List<Projectile> toKeep = new ArrayList<>();
        shots.forEach(shot -> {
            if (shot.getPosX() < WINDOW_WIDTH && shot.getPosX() > 0 && shot.getPosY() < WINDOW_HEIGHT && shot.getPosY() > 0
                    && !shot.isToRemove()) {
                toKeep.add(shot);
            }
        });
        shots = toKeep;
    }

    /**
     * @return player inventory
     */
    public List<OperatingGameObject> getInventory() {
        return inventory;
    }

    /**
     * @param object object that is added to inventory
     */
    public void addItemToInventory(OperatingGameObject object) {
        inventory.add(object);
    }

    /**
     * Checks for item of type in inventory.
     *
     * @param actionOption type of item
     * @return true if type of item is in inventory, false otherwise
     * @see ActionOption
     */
    public boolean inventoryContainsItemType(ActionOption actionOption) {
        for (OperatingGameObject item : inventory) {
            if (item.getActionOption() == actionOption) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes all occurrences of item type from inventory.
     *
     * @param actionOption type of item
     */
    public void removeItemOfType(ActionOption actionOption) {
        inventory.removeIf(item -> item.getActionOption() == actionOption);
    }

    /**
     * Removes specified object from inventory.
     *
     * @param gameObject object to remove
     */
    public void removeItemFromInventory(GameObject gameObject) {
        inventory.remove(gameObject);
    }

    /**
     * @return shots fired by Player.
     */
    public List<Projectile> getShots() {
        return shots;
    }

}
