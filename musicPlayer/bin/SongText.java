import javafx.scene.text.Font;
import javafx.scene.text.Text;

class SongText {
    private SongText(){}
    static Text drawText(double x, double y){
        Text t = new Text();
        t.setLayoutX(x);
        t.setLayoutY(y+24);
        t.setFont(new Font(24));
        return t;
    }
}
