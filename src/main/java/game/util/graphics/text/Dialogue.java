package game.util.graphics.text;

import javafx.scene.image.Image;

public class Dialogue {
    private Image img;
    private String text;
    private long startTime;
    private long endTime;

    /**
     * Creates instance of Dialogue.
     *
     * @param img       image of the object sending the message
     * @param text      text of the message
     * @param startTime start time of message
     * @param endTime   end time of message
     */
    public Dialogue(Image img, String text, long startTime, long endTime) {
        this.img = img;
        this.text = text;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * @return image of the object sending the message
     */
    public Image getImg() {
        return img;
    }

    /**
     * @return text of the message
     */
    public String getText() {
        return text;
    }

    /**
     * @return start time of message
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @return end time of message
     */
    public long getEndTime() {
        return endTime;
    }
}
