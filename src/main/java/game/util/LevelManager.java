package game.util;

import com.google.gson.Gson;
import game.GameLoop;
import game.level.Level;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static game.util.properties.Properties.CURRENT_LEVEL;
import static game.util.properties.Properties.LEVELS_LOCATION;

/**
 * Level manager holds current level status and is used for loading and saving levels.
 */
public class LevelManager {
    private int currentLevel;

    private final static Logger LOG = Logger.getLogger(LevelManager.class);

    /**
     * Creates instance of LevelManager.
     */
    public LevelManager() {
        Gson gson = new Gson();
        try {
            currentLevel = gson.fromJson(new FileReader(CURRENT_LEVEL), int.class);
        } catch (FileNotFoundException e) {
            LOG.error("Could not load level " + e);
        }
    }

    /**
     * Loads current level from file.
     * Note that images can't be loaded and thus must be loaded when needed,
     * e.g. function loadLevelImages in GameLoop.
     *
     * @return current level or null if level can't be loaded
     * @see GameLoop#loadLevelImages()
     */
    public Level getCurrentLevel() {
        Level level;
        Gson gson = new Gson();
        try {
            level = gson.fromJson(new FileReader(LEVELS_LOCATION + "" + currentLevel), Level.class);
        } catch (FileNotFoundException e) {
            LOG.error("Could not load level " + e);
            return null;
        }
        return level;
    }

    /**
     * Saves number of current level to file.
     */
    public void saveCurrentLevel() {
        Gson gson = new Gson();
        try {
            FileWriter fw = new FileWriter(CURRENT_LEVEL);
            gson.toJson(currentLevel, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            LOG.error("Could not save current level" + e);
        }
    }

    /**
     * Sets level number to next level number.
     */
    public void nextLevel() {
        this.currentLevel++;
        saveCurrentLevel();
        LOG.info("Level set to " + currentLevel);
    }
}
