package game.gameobject;

import game.gameobject.entity.Heading;
import game.graphics.AnimatedImage;

public class Projectile extends OperatingGameObject {

    private Heading heading;
    private double speed;
    private boolean toRemove = false;

    /**
     * Creates instance of Projectile.
     *
     * @param posX         position x
     * @param posY         position y
     * @param img          animated image of projectile
     * @param actionOption action that an operating game object can do
     * @param heading      direction the projectile is heading
     * @param speed        speed of projectile
     * @param width        width of projectile sprite
     * @param height       height of projectile sprite
     */
    public Projectile(double posX, double posY, AnimatedImage img, ActionOption actionOption, Heading heading, double speed, int width, int height) {
        super(posX, posY, img, actionOption, width, height);
        this.heading = heading;
        this.speed = speed;
    }

    /**
     * Updates position of projectile.
     */
    public void update() {
        // TODO implement this
    }

    /**
     * @return direction the projectile is heading
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * @return speed of projectile
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return if projectile should be removed in next iteration
     */
    public boolean isToRemove() {
        return toRemove;
    }

    /**
     * @param toRemove true if projectile should be removed in next iteration, false otherwise
     */
    public void setToRemove(boolean toRemove) {
        this.toRemove = toRemove;
    }
}
