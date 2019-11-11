package game.util.graphics;

import javafx.beans.binding.Bindings;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ButtonCreator {

    /**
     * Creates button positioned at given position
     * that has img1 as placeholder image and img2 as image after the button is clicked.
     *
     * @param img1Url url of placeholder image
     * @param img2Url url of image after button is clicked
     * @param posX    position x
     * @param posY    position y
     * @return positioned button with different images before and after clicking
     */
    public static ToggleButton createButton(String img1Url, String img2Url, int posX, int posY) {
        ToggleButton toggle = new ToggleButton();

        final Image img1 = new Image(img1Url);
        final Image img2 = new Image(img2Url);
        final ImageView view = new ImageView();
        toggle.setGraphic(view);
        view.imageProperty().bind(Bindings
                .when(toggle.selectedProperty())
                .then(img2)
                .otherwise(img1)
        );
        toggle.setStyle("-fx-padding:0; -fx-background-color: transparent;");
        toggle.setLayoutX(posX);
        toggle.setLayoutY(posY);

        return toggle;
    }

    /**
     * Creates image button positioned at given position.
     *
     * @param imgUrl url of image
     * @param posX   position x
     * @param posY   position y
     * @return positioned image button
     */
    public static ToggleButton createButton(String imgUrl, int posX, int posY) {
        ToggleButton toggle = new ToggleButton();

        final Image img1 = new Image(imgUrl);
        final ImageView view = new ImageView();
        toggle.setGraphic(view);
        view.imageProperty().bind(Bindings
                .when(toggle.selectedProperty())
                .then(img1)
                .otherwise(img1)
        );
        toggle.setStyle("-fx-padding:0; -fx-background-color: transparent;");
        toggle.setLayoutX(posX);
        toggle.setLayoutY(posY);

        return toggle;
    }

    /**
     * Creates unpositioned button that has img1 as placeholder image and img2 as image after the button is clicked.
     *
     * @param img1Url url of placeholder image
     * @param img2Url url of image after button is clicked
     * @return unpositioned button with different images before and after clicking
     */
    public static ToggleButton createButton(String img1Url, String img2Url) {
        ToggleButton toggle = new ToggleButton();

        final Image img1 = new Image(img1Url);
        final Image img2 = new Image(img2Url);
        final ImageView view = new ImageView();
        toggle.setGraphic(view);
        view.imageProperty().bind(Bindings
                .when(toggle.selectedProperty())
                .then(img2)
                .otherwise(img1)
        );
        toggle.setStyle("-fx-padding:0; -fx-background-color: transparent;");

        return toggle;
    }

    /**
     * Creates unpositioned image button
     *
     * @param imgUrl url of image
     * @return unpositioned image button
     */
    public static ToggleButton createButton(String imgUrl) {
        ToggleButton toggle = new ToggleButton();

        final Image img1 = new Image(imgUrl);
        final ImageView view = new ImageView();
        toggle.setGraphic(view);
        view.imageProperty().bind(Bindings
                .when(toggle.selectedProperty())
                .then(img1)
                .otherwise(img1)
        );
        toggle.setStyle("-fx-padding:0; -fx-background-color: transparent;");

        return toggle;
    }

}
