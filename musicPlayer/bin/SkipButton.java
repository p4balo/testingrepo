import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

import java.nio.file.Paths;

class SkipButton {
    private SkipButton(){}
    static Button drawButton(){
        String styleSheet = Paths.get("musicPlayer/resources/Stylesheet.css").toUri().toString();
        Button skipButton = new Button();
        ImageView iv2 = new ImageView(Paths.get("musicPlayer/resources/skipButton.png").toUri().toString());
        iv2.setFitHeight(50);
        iv2.setFitWidth(50);
        skipButton.setGraphic(iv2);
        skipButton.getStylesheets().add(styleSheet);
        skipButton.setId("SkipButton");
        skipButton.setLayoutX(150);
        skipButton.setLayoutY(100);
        skipButton.setTranslateZ(skipButton.getBoundsInLocal().getWidth()/2);
        skipButton.setRotationAxis(Rotate.Y_AXIS);
        skipButton.setRotate(180);
        return skipButton;
    }
    static ImageView getImage(){
        return new ImageView(Paths.get("musicPlayer/resources/skipButton.png").toUri().toString());
    }
}