package game.gameobject;

import game.graphics.AnimatedImage;
import game.graphics.PixelImage;
import javafx.scene.image.Image;

public class OperatingGameObject extends GameObject {
    private String name;

    private ActionOption actionOption;
    private boolean opened;

    private transient Image openImg;
    private String openImgName;

    /**
     * Creates instance of OperatingGameObject.
     *
     * @param posX         position x
     * @param posY         position y
     * @param img          animated image
     * @param actionOption action that an operating game object can do
     * @param width        width of sprite
     * @param height       height of sprite
     */
    public OperatingGameObject(double posX, double posY, AnimatedImage img, ActionOption actionOption, int width, int height) {
        super(posX, posY, img, width, height);
        this.actionOption = actionOption;
        this.opened = false;
    }

    /**
     * Creates instance of OperatingGameObject with image after it is used.
     *
     * @param posX         position x
     * @param posY         position y
     * @param img          animated image
     * @param actionOption action that an operating game object can do
     * @param width        width of sprite
     * @param height       height of sprite
     * @param openImg      image after object is used
     */
    public OperatingGameObject(double posX, double posY, AnimatedImage img, ActionOption actionOption, int width, int height, String openImg) {
        super(posX, posY, img, width, height);
        this.actionOption = actionOption;
        this.openImg = new PixelImage(openImg, width, height);
        this.openImgName = openImg;
    }

    /**
     * @return action that an operating game object can do
     */
    public ActionOption getActionOption() {
        return actionOption;
    }

    /**
     * @param actionOption action that an operating game object can do
     */
    public void setActionOption(ActionOption actionOption) {
        this.actionOption = actionOption;
    }

    /**
     * @return true if operating game object was used, false otherwise
     */
    public boolean isOpened() {
        return opened;
    }

    /**
     * @param opened true if operating game object was used, false if it was not used
     */
    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    /**
     * @return image of used operating game object
     */
    public Image getOpenImg() {
        return openImg;
    }

    /**
     * @return url of sprite of used operating game object
     */
    public String getOpenImgName() {
        return openImgName;
    }

    /**
     * @param openImg image of used operating game object
     */
    public void setOpenImg(Image openImg) {
        this.openImg = openImg;
    }
}
