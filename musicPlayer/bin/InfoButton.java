import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;

class InfoButton{
    private static Button playButton = new Button();
    private InfoButton(){}
    static Button drawButton(double x, double y){
        String styleSheet = Paths.get("musicPlayer/resources/Stylesheet.css").toUri().toString();
        ImageView iv = new ImageView(Paths.get("musicPlayer/resources/infoButton.png").toUri().toString());
        iv.setFitHeight(30);
        iv.setFitWidth(30);
        playButton.setGraphic(iv);
        playButton.getStylesheets().add(styleSheet);
        playButton.setId("InfoButton");
        playButton.setLayoutX(x);
        playButton.setLayoutY(y);
        Tooltip tp = new Tooltip();
        tp.setText("Display Information");
        playButton.setTooltip(tp);
        return playButton;
    }
    static ImageView getImage() {
        return new ImageView(Paths.get("musicPlayer/resources/infoButton.png").toUri().toString());
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
