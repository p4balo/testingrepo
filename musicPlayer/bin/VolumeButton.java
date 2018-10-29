import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.nio.file.Paths;

class VolumeButton {
    private static Button volumeButton = new Button();
    private VolumeButton(){}
    static Button drawButton(int x, int y, double volume){
        String styleSheet = Paths.get("musicPlayer/resources/Stylesheet.css").toUri().toString();
        setImage(volume);
        volumeButton.getStylesheets().add(styleSheet);
        volumeButton.setId("VolumeButton");
        volumeButton.setLayoutX(x);
        volumeButton.setLayoutY(y);
        return volumeButton;
    }
    private static void setImage(double volumeQuantity){
        if(volumeQuantity==0){
            ImageView iv2 = new ImageView(Paths.get("musicPlayer/resources/muteButton.png").toUri().toString());
            iv2.setFitHeight(40);
            iv2.setFitWidth(40);
            volumeButton.setGraphic(iv2);
        }else if(volumeQuantity>0 && volumeQuantity<.33){
            ImageView iv2 = new ImageView(Paths.get("musicPlayer/resources/volumeButton3.png").toUri().toString());
            iv2.setFitHeight(40);
            iv2.setFitWidth(40);
            volumeButton.setGraphic(iv2);
        }else if(volumeQuantity>=.33 && volumeQuantity<.66){
            ImageView iv2 = new ImageView(Paths.get("musicPlayer/resources/volumeButton2.png").toUri().toString());
            iv2.setFitHeight(40);
            iv2.setFitWidth(40);
            volumeButton.setGraphic(iv2);
        }else{
            ImageView iv2 = new ImageView(Paths.get("musicPlayer/resources/volumeButton1.png").toUri().toString());
            iv2.setFitHeight(40);
            iv2.setFitWidth(40);
            volumeButton.setGraphic(iv2);
        }
    }
}
