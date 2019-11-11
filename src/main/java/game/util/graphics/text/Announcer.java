package game.util.graphics.text;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import static game.util.properties.Properties.WINDOW_HEIGHT;
import static game.util.properties.Properties.WINDOW_WIDTH;

/**
 * Announcer manages showing of dialogues(messages).
 */
public class Announcer {
    private GraphicsContext gc;
    private Font f;
    private LinkedList<Dialogue> dialogues;

    /**
     * Creates instance of Announcer.
     *
     * @param gc graphics context
     */
    public Announcer(GraphicsContext gc) {
        this.gc = gc;
        try {
            f = Font.loadFont(new FileInputStream(new File("FaceType - Loki.ttf")), 32);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dialogues = new LinkedList<>();
    }

    /**
     * Renders dialogue in bottom part of the screen.
     *
     * @param image image of the object sending the message
     * @param text  text of the message
     */
    private void drawDialogue(Image image, String text) {
        gc.setFill(Color.rgb(197, 96, 37));
        gc.fillRect(0, 950, WINDOW_WIDTH, WINDOW_HEIGHT - 950);
        gc.setFill(Color.rgb(252, 203, 163));
        gc.fillRect(5, 955, WINDOW_WIDTH - 10, WINDOW_HEIGHT - 960);
        gc.setFill(Color.BLACK);
        gc.drawImage(image, 20, 970);
        gc.setFont(f);
        gc.fillText(text, 105, 1020);
    }

    /**
     * Adds dialogue to the queue of dialogues.
     *
     * @param img       image of the object sending the message
     * @param text      text of the message
     * @param startTime start time of message
     * @param time      for how long message is displayed
     */
    public void addDialogue(Image img, String text, long startTime, long time) {
        dialogues.push(new Dialogue(img, text, startTime, startTime + time));
    }

    /**
     * Updates list of dialogues and renders dialogue in specified time.
     */
    public void updateDialogues() {
        if (!dialogues.isEmpty()) {
            Dialogue dialogue = dialogues.getFirst();
            long currentTime = System.nanoTime();
            if (dialogue.getStartTime() <= currentTime) drawDialogue(dialogue.getImg(), dialogue.getText());
            if (dialogue.getEndTime() <= currentTime) dialogues.pop();
        }
    }
}
