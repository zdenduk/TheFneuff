package game.level;

import game.graphics.PixelImage;
import javafx.scene.image.Image;

import static game.util.properties.SpriteNames.*;
import static game.util.properties.SpriteProperties.BLOCK_SIZE;

/**
 * Block sprite with given size.
 */
public enum Tile {
    FLOOR1(new PixelImage(FLOOR_1, BLOCK_SIZE, BLOCK_SIZE)),
    FLOOR2(new PixelImage(FLOOR_2, BLOCK_SIZE, BLOCK_SIZE)),
    FLOOR3(new PixelImage(FLOOR_3, BLOCK_SIZE, BLOCK_SIZE)),
    FLOOR4(new PixelImage(FLOOR_4, BLOCK_SIZE, BLOCK_SIZE)),
    FLOOR5(new PixelImage(FLOOR_5, BLOCK_SIZE, BLOCK_SIZE)),
    FLOOR6(new PixelImage(FLOOR_6, BLOCK_SIZE, BLOCK_SIZE)),
    FLOOR7(new PixelImage(FLOOR_7, BLOCK_SIZE, BLOCK_SIZE)),
    FLOOR8(new PixelImage(FLOOR_8, BLOCK_SIZE, BLOCK_SIZE)),
    WALL_TOP_MID(new PixelImage("game/wall_top_mid.png", BLOCK_SIZE, BLOCK_SIZE)),
    WALL_BOTTOM_MID(new PixelImage("game/wall_bottom_mid.png", BLOCK_SIZE, BLOCK_SIZE)),
    WALL_SIDE_MID_RIGHT(new PixelImage("game/wall_side_mid_right.png", BLOCK_SIZE, BLOCK_SIZE)),
    WALL_SIDE_MID_LEFT(new PixelImage("game/wall_side_mid_left.png", BLOCK_SIZE, BLOCK_SIZE)),
    WALL_CORNER_TOP_LEFT(new PixelImage("game/wall_inner_corner_l_top_left.png", BLOCK_SIZE, BLOCK_SIZE)),
    WALL_CORNER_TOP_RIGHT(new PixelImage("game/wall_inner_corner_l_top_right.png", BLOCK_SIZE, BLOCK_SIZE)),
    WALL_CORNER_BOTTOM_LEFT(new PixelImage("game/wall_corner_bottom_left.png", BLOCK_SIZE, BLOCK_SIZE)),
    WALL_CORNER_BOTTOM_RIGHT(new PixelImage("game/wall_corner_bottom_right.png", BLOCK_SIZE, BLOCK_SIZE));

    private final Image image;

    Tile(PixelImage image) {
        this.image = image;
    }

    public Image getImg() {
        return image;
    }
}
