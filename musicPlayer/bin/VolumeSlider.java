import javafx.scene.control.Slider;

class VolumeSlider {
    private VolumeSlider(){ }
    static Slider drawSlider(double x, double y){
        Slider s = new Slider();
        s.setLayoutX(x);
        s.setLayoutY(y);
        s.setMin(0);
        s.setMax(1);
        s.setBlockIncrement(.01);
        s.setValue(.5);
        return s;
    }

}
