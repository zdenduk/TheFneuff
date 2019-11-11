package game.level;

/**
 * Two dimensional array consisting of tiles,
 * can represent i.e. background of level.
 */
public class TileMap {
    private Tile[][] map;

    /**
     * Creates instance of TileMap.
     *
     * @param map two dimensional array of tiles
     */
    public TileMap(Tile[][] map) {
        this.map = map;
    }

    /**
     * @return  two dimensional array of tiles
     */
    public Tile[][] getMap() {
        return map;
    }

}
