package game.gameobject;

import game.gameobject.entity.Heading;
import game.graphics.AnimatedImage;
import game.level.Tile;
import game.level.TileMap;

import static game.util.properties.SpriteProperties.BLOCK_SIZE;

public class GameObject {
    private double posX;
    private double posY;
    private int width;
    private int height;

    private AnimatedImage img;

    /**
     * Creates instance of GameObject.
     *
     * @param posX   position x
     * @param posY   position y
     * @param img    sprite
     * @param width  game object width
     * @param height game object height
     */
    public GameObject(double posX, double posY, AnimatedImage img, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.img = img;
        this.height = height;
        this.width = width;
    }

    /**
     * @return game object position x
     */
    public double getPosX() {
        return posX;
    }

    /**
     * @return game object position y
     */
    public double getPosY() {
        return posY;
    }

    /**
     * Updates position x by specified amount.
     *
     * @param update by how much is object position x updated
     */
    public void updatePosX(double update) {
        this.posX += update;
    }

    /**
     * Updates position y by specified amount.
     *
     * @param update by how much is object position y updated
     */
    public void updatePosY(double update) {
        this.posY += update;
    }

    /**
     * Checks whether two game objects collide.
     *
     * @param o investigated object
     * @return true if objects collide, false otherwise
     */
    public boolean collidesWith(GameObject o) {
        int halfWidth = width / 2;
        int objectHalfWidth = o.getWidth() / 2;
        if (Math.abs((posX + halfWidth) - (o.getPosX() + objectHalfWidth)) < halfWidth + objectHalfWidth) {
            int halfHeight = height / 2;
            int objectHalfHeight = o.getHeight() / 2;
            return Math.abs((posY + halfHeight) - (o.getPosY() + objectHalfHeight)) < halfHeight + objectHalfHeight;
        }
        return false;
    }

    /**
     * @return game object sprite
     */
    public AnimatedImage getImg() {
        return img;
    }

    /**
     * @return game object width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return game object height
     */
    public int getHeight() {
        return height;
    }

    public boolean hitWall(TileMap walls, double update, Heading heading) {
        if (walls == null) return false;
        for (int x = 0; x < walls.getMap().length; x++) {
            for (int y = 0; y < walls.getMap()[0].length; y++) {
                int posX = x * BLOCK_SIZE;
                int posY = y * BLOCK_SIZE;

                Tile wall = walls.getMap()[x][y];
                if (wall == Tile.WALL_SIDE_MID_LEFT || wall == Tile.WALL_CORNER_BOTTOM_LEFT || wall == Tile.WALL_CORNER_TOP_LEFT) {
                    if (heading == Heading.LEFT) {
                        if (this.getPosX() + update <= posX + 12 && this.getPosX() >= posX
                                && ((this.getPosY() < posY && posY - this.getPosY() <= this.getHeight()) || (this.getPosY() >= posY && this.getPosY() - posY <= BLOCK_SIZE))) {
                            return true;
                        }
                    } else if (heading == Heading.RIGHT) {
                        if (this.getPosX() + getWidth() + update >= posX && this.getPosX() <= posX && getPosY() + getHeight() >= posY && getPosY() <= posY + BLOCK_SIZE) {
                            return true;
                        }
                    } else if (heading == Heading.UP) {
                        if (this.getPosY() + update <= posY + BLOCK_SIZE && this.getPosY() >= posY && getPosX() + getWidth() >= posX && getPosX() <= posX + 12) {
                            return true;
                        }
                    } else if (heading == Heading.DOWN) {
                        if (this.getPosY() + getHeight() + update >= posY && this.getPosY() <= posY && this.getPosX() <= posX + 12 && this.getPosX() + getWidth() >= posX) {
                            return true;
                        }
                    }
                }
                if (wall == Tile.WALL_SIDE_MID_RIGHT || wall == Tile.WALL_CORNER_BOTTOM_RIGHT || wall == Tile.WALL_CORNER_TOP_RIGHT) {
                    if (heading == Heading.LEFT) {
                        if (this.getPosX() + update <= posX + BLOCK_SIZE && this.getPosX() >= posX && getPosY() <= posY + BLOCK_SIZE && getPosY() + getHeight() >= posY) {
                            return true;
                        }
                    } else if (heading == Heading.RIGHT) {
                        if (this.getPosX() + getWidth() + update >= posX + BLOCK_SIZE - 12 && this.getPosX() <= posX && getPosY() <= posY + BLOCK_SIZE && getPosY() + getHeight() >= posY) {
                            return true;
                        }
                    } else if (heading == Heading.UP) {
                        if (this.getPosY() + update <= posY + BLOCK_SIZE && this.getPosY() >= posY && this.getPosX() <= posX + BLOCK_SIZE - 12 && this.getPosX() + getWidth() >= posX + BLOCK_SIZE - 12) {
                            return true;
                        }
                    } else if (heading == Heading.DOWN) {
                        if (this.getPosY() + getHeight() + update >= posY && this.getPosY() <= posY && this.getPosX() <= posX + BLOCK_SIZE - 12 && this.getPosX() + getWidth() >= posX + BLOCK_SIZE - 12) {
                            return true;
                        }
                    }
                }
                if (wall == Tile.WALL_TOP_MID || wall == Tile.WALL_CORNER_TOP_LEFT || wall == Tile.WALL_CORNER_TOP_RIGHT) {
                    if (heading == Heading.LEFT) {
                        if (this.getPosX() + update <= posX + BLOCK_SIZE && this.getPosX() >= posX && ((this.getPosY() < posY && posY - this.getPosY() <= getHeight()) || (this.getPosY() >= posY && this.getPosY() - posY <= 12))) {
                            return true;
                        }
                    } else if (heading == Heading.RIGHT) {
                        if (this.getPosX() + getWidth() + update >= posX && this.getPosX() <= posX && this.getPosX() <= posX && ((this.getPosY() < posY && posY - this.getPosY() <= getHeight()) || (this.getPosY() >= posY && this.getPosY() - posY <= 12))) {
                            return true;
                        }
                    } else if (heading == Heading.UP) {
                        if (this.getPosY() + update <= posY + 12 && this.getPosY() + update >= posY && Math.abs(this.getPosX() - posX) <= getWidth()) {
                            return true;
                        }
                    } else if (heading == Heading.DOWN) {
                        if (this.getPosY() + getHeight() + update >= posY && this.getPosY() <= posY && Math.abs(this.getPosX() - posX) <= getWidth()) {
                            return true;
                        }
                    }
                }
                if (wall == Tile.WALL_BOTTOM_MID || wall == Tile.WALL_CORNER_BOTTOM_LEFT || wall == Tile.WALL_CORNER_BOTTOM_RIGHT) {
                    if (heading == Heading.LEFT) {
                        if (this.getPosX() + update <= posX + BLOCK_SIZE && this.getPosX() >= posX && ((this.getPosY() <= posY && (posY + BLOCK_SIZE - 12) - this.getPosY() <= getHeight()) || (this.getPosY() >= posY && this.getPosY() - (posY + BLOCK_SIZE - 12) <= 12))) {
                            return true;
                        }
                    } else if (heading == Heading.RIGHT) {
                        if (this.getPosX() + getWidth() + update >= posX && this.getPosX() <= posX && ((this.getPosY() <= posY && (posY + BLOCK_SIZE - 12) - this.getPosY() <= getHeight()) || (this.getPosY() >= posY && this.getPosY() - (posY + BLOCK_SIZE - 12) <= 12))) {
                            return true;
                        }
                    } else if (heading == Heading.UP) {
                        if (this.getPosY() + update <= posY + BLOCK_SIZE && this.getPosY() >= posY && Math.abs(this.getPosX() - posX) <= getWidth()) {
                            return true;
                        }
                    } else if (heading == Heading.DOWN) {
                        if (this.getPosY() + getHeight() + update >= posY + BLOCK_SIZE - 12 && this.getPosY() <= posY && Math.abs(this.getPosX() - posX) <= getWidth()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
