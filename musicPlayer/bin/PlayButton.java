import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;

class PlayButton {
    private PlayButton(){}
    static Button drawButton(double x, double y){
        String styleSheet = Paths.get("musicPlayer/resources/Stylesheet.css").toUri().toString();
        Button playButton = new Button();
        ImageView iv = new ImageView(Paths.get("musicPlayer/resources/playButton.png").toUri().toString());
        iv.setFitHeight(45);
        iv.setFitWidth(45);
        playButton.setGraphic(iv);
        playButton.getStylesheets().add(styleSheet);
        playButton.setId("PlayButton");
        playButton.setLayoutX(x);
        playButton.setLayoutY(y);
        return playButton;
    }
    static ImageView getImage(){
        return new ImageView(Paths.get("musicPlayer/resources/playButton.png").toUri().toString());
    }
}
