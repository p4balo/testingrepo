import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

class MusicBar {
    private Color col;
    MusicBar(){
        ArrayList<Color> colorLibrary = new ArrayList<>();
        colorLibrary.add(Color.RED);
        colorLibrary.add(Color.GREEN);
        colorLibrary.add(Color.FORESTGREEN);
        colorLibrary.add(Color.BLUE);
        colorLibrary.add(Color.SKYBLUE);
        colorLibrary.add(Color.MAROON);
        colorLibrary.add(Color.DARKSEAGREEN);
        colorLibrary.add(Color.ORANGE);
        colorLibrary.add(Color.PURPLE);
        col = colorLibrary.get((int)(Math.random()* colorLibrary.size()));
    }
    Rectangle drawPlaceHolder(){
        Rectangle placeHolder = new Rectangle();
        placeHolder.setLayoutX(50);
        placeHolder.setLayoutY(100);
        placeHolder.setWidth(700);
        placeHolder.setHeight(50);
        placeHolder.setStroke(Color.BLACK);
        placeHolder.setStrokeWidth(3);
        placeHolder.setFill(Color.TRANSPARENT);
        return placeHolder;
    }
    Rectangle drawMusicBar(double width){
        Rectangle musicBar = new Rectangle();
        musicBar.setLayoutX(50);
        musicBar.setLayoutY(100);
        musicBar.setWidth(width);
        musicBar.setHeight(50);
        musicBar.setFill(col);
        return musicBar;
    }
    static double getX(){
        return 50;
    }
    static double getY(){
        return 100;
    }
}
