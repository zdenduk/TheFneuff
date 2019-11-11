package game.graphics;

import javafx.scene.image.Image;

/**
 * This class helps create sharp images without smoothing, resizing etc.
 * Best use with 8x8 or 16x16 pixel images.
 */
public class PixelImage extends Image {

    /**
     * Creates instance of Image with preserveRatio set to true and smooth set to false.
     *
     * @param url             sprite url
     * @param requestedWidth  sprite width
     * @param requestedHeight sprite height
     */
    public PixelImage(String url, double requestedWidth, double requestedHeight) {
        super(url, requestedWidth, requestedHeight, true, false);
    }
}
