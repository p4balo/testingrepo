import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;

class VolumeSlider {
    private static Slider s = new Slider();
    private VolumeSlider(){ }
    static Slider drawSlider(double x, double y){
        s.setLayoutX(x);
        s.setLayoutY(y);
        s.setMin(0);
        s.setMax(1);
        s.setBlockIncrement(.01);
        s.setValue(.5);
        Tooltip tp = new Tooltip();
        tp.setText("Adjust Volume");
        s.setTooltip(tp);
        return s;
    }
    static void setTooltips(boolean b){
        if(b){
            Tooltip tp = new Tooltip();
            tp.setText("Adjust Volume");
            s.setTooltip(tp);
        }else{
            s.setTooltip(null);
        }
    }
}
