import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;

class PlayButton {
    private static Button playButton = new Button();
    private PlayButton(){}
    static Button drawButton(double x, double y){
        String styleSheet = Paths.get("musicPlayer/resources/Stylesheet.css").toUri().toString();
        ImageView iv = new ImageView(Paths.get("musicPlayer/resources/playButton.png").toUri().toString());
        iv.setFitHeight(45);
        iv.setFitWidth(45);
        playButton.setGraphic(iv);
        playButton.getStylesheets().add(styleSheet);
        playButton.setId("PlayButton");
        playButton.setLayoutX(x);
        playButton.setLayoutY(y);
        Tooltip tp = new Tooltip();
        tp.setText("Play Music");
        playButton.setTooltip(tp);
        return playButton;
    }
    static ImageView getImage(){
        return new ImageView(Paths.get("musicPlayer/resources/playButton.png").toUri().toString());
    }
    static void setTooltips(boolean b){
        if(b){
            Tooltip tp = new Tooltip();
            tp.setText("Play Music");
            playButton.setTooltip(tp);
        }else{
            playButton.setTooltip(null);
        }
    }
}
