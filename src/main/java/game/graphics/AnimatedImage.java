package game.graphics;

import javafx.scene.image.Image;

/**
 * Represents animated image consisting of frames.
 */
public class AnimatedImage {
    // assumes animation loops,
    // each image displays for equal time
    private transient Image[] frames;
    private double duration;
    private int width, height;

    private String spriteName;
    private int numberOfSpritesToAnimate;

    /**
     * Creates instance of AnimatedImage.
     *
     * @param spriteName               sprite url
     * @param numberOfSpritesToAnimate of how many frames animation consists
     * @param duration                 how long should one frame be displayed
     * @param width                    width of sprite
     * @param height                   height of sprite
     */
    public AnimatedImage(final String spriteName, int numberOfSpritesToAnimate, double duration, int width, int height) {
        this.duration = duration;
        this.width = width;
        this.height = height;
        this.spriteName = spriteName;
        this.numberOfSpritesToAnimate = numberOfSpritesToAnimate;
        loadSprites(spriteName, numberOfSpritesToAnimate);
    }

    // use only in loading after save -- javafx cant be done into json

    /**
     * Loads images from source.
     *
     * @param spriteName sprite url
     * @param number     of how many frames animation consists
     */
    public void loadSprites(final String spriteName, int number) {
        Image[] frames = new Image[number];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new PixelImage(spriteName + i + ".png", width, height);
        }
        this.frames = frames;
    }

    /**
     * @param time current time
     * @return current frame in animation depending on time
     */
    public Image getFrame(double time) {
        int index = (int) ((time % (frames.length * duration)) / duration);
        return frames[index];
    }

    /**
     * @return sprite width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return sprite height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return sprite url
     */
    public String getSpriteName() {
        return spriteName;
    }

    /**
     * @return number of how many frames animation consists
     */
    public int getNumberOfSpritesToAnimate() {
        return numberOfSpritesToAnimate;
    }
}