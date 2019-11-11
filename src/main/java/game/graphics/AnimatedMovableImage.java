package game.graphics;

import game.gameobject.entity.Heading;

/**
 * Represents animated image that has an image for every direction.
 */
public class AnimatedMovableImage {
    private AnimatedImage spriteLeft;
    private AnimatedImage spriteRight;
    private AnimatedImage spriteUp;
    private AnimatedImage spriteDown;
    private AnimatedImage spriteIdleLeft;
    private AnimatedImage spriteIdleRight;
    private AnimatedImage spriteIdleUp;
    private AnimatedImage spriteIdleDown;

    /**
     * Creates instance of AnimatedMovableImage.
     *
     * @param spriteLeft      animated image when heading left
     * @param spriteRight     animated image when heading right
     * @param spriteUp        animated image when heading up
     * @param spriteDown      animated image when heading down
     * @param spriteIdleLeft  animated image idling and last heading was left
     * @param spriteIdleRight animated image idling and last heading was right
     * @param spriteIdleUp    animated image idling and last heading was up
     * @param spriteIdleDown  animated image idling and last heading was down
     */
    public AnimatedMovableImage(AnimatedImage spriteLeft, AnimatedImage spriteRight, AnimatedImage spriteUp, AnimatedImage spriteDown, AnimatedImage spriteIdleLeft, AnimatedImage spriteIdleRight, AnimatedImage spriteIdleUp, AnimatedImage spriteIdleDown) {
        this.spriteLeft = spriteLeft;
        this.spriteRight = spriteRight;
        this.spriteUp = spriteUp;
        this.spriteDown = spriteDown;
        this.spriteIdleLeft = spriteIdleLeft;
        this.spriteIdleRight = spriteIdleRight;
        this.spriteIdleUp = spriteIdleUp;
        this.spriteIdleDown = spriteIdleDown;
    }

    /**
     * @param heading direction
     * @return animated image for specified direction
     */
    public AnimatedImage getDirectedSprite(Heading heading) {
        if (heading == Heading.RIGHT) return spriteRight;
        if (heading == Heading.LEFT) return spriteLeft;
        if (heading == Heading.UP) return spriteUp;
        if (heading == Heading.DOWN) return spriteDown;
        if (heading == Heading.IDLE_LEFT) return spriteIdleLeft;
        if (heading == Heading.IDLE_RIGHT) return spriteIdleRight;
        if (heading == Heading.IDLE_UP) return spriteIdleUp;
        return spriteIdleDown;
    }
}
