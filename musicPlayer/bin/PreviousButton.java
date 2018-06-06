import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

import java.nio.file.Paths;

class PreviousButton {
    private static Button previousButton = new Button();
    private PreviousButton(){}
    static Button drawButton(){
        String styleSheet = Paths.get("musicPlayer/resources/Stylesheet.css").toUri().toString();
        ImageView iv3 = new ImageView(Paths.get("musicPlayer/resources/skipButton.png").toUri().toString());
        iv3.setFitHeight(50);
        iv3.setFitWidth(50);
        previousButton.setGraphic(iv3);
        previousButton.getStylesheets().add(styleSheet);
        previousButton.setId("SkipButton");
        previousButton.setLayoutX(50);
        previousButton.setLayoutY(100);
        previousButton.setTranslateZ(previousButton.getBoundsInLocal().getWidth()/2);
        previousButton.setRotationAxis(Rotate.Y_AXIS);
        previousButton.setRotate(0);
        Tooltip tp = new Tooltip();
        tp.setText("Go Back a Song");
        previousButton.setTooltip(tp);
        return previousButton;
    }
    static void setTooltips(boolean b){
        if(b){
            Tooltip tp = new Tooltip();
            tp.setText("Go Back a Song");
            previousButton.setTooltip(tp);
        }else{
            previousButton.setTooltip(null);
        }
    }
}
