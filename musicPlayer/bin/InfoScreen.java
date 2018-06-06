import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.nio.file.Paths;

class InfoScreen {
    private InfoScreen(){}
    static Pane drawInfoScreen(double x, double y){
        Pane miniPane = new Pane();
        miniPane.setLayoutX(x);
        miniPane.setLayoutY(y);
        miniPane.setId("InfoPane");

        Rectangle background = new Rectangle(0,0,250,350);
        background.setFill(Color.WHITE);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(3);
        miniPane.getChildren().add(background);

        Text title = new Text("Options");
        title.setLayoutX(50);
        title.setLayoutY(35);
        title.setFont(new Font(24));
        miniPane.getChildren().add(title);

        CheckBox tooltips = new CheckBox();
        tooltips.setLayoutX(75);
        tooltips.setLayoutY(50);
        tooltips.fire();
        tooltips.setOnAction(event -> {
            if(tooltips.isSelected()){
                System.out.println("activate tooltips");
            }else{
                System.out.println("deactivate tooltips");
            }
        });
        Label l1 = new Label("Enable\nTooltips");
        l1.setLabelFor(tooltips);
        l1.setLayoutX(15);
        l1.setLayoutY(50);
        miniPane.getChildren().add(l1);
        miniPane.getChildren().add(tooltips);

        TextField pathText = new TextField();
        pathText.setText(Main.getPathname());
        System.out.println(Main.getPathname());
        pathText.setLayoutX(50);
        pathText.setLayoutY(100);
        pathText.setFont(new Font(16));
        pathText.setMaxWidth(190);
        Label l2 = new Label("Path");
        l2.setLayoutY(100);
        l2.setLayoutX(15);
        l2.setLabelFor(tooltips);
        miniPane.getChildren().add(l2);
        miniPane.getChildren().add(pathText);

        return miniPane;
    }
    static Button drawBackButton(double x, double y){
        Button back = new Button();
        ImageView iv = new ImageView(Paths.get("musicPlayer/resources/redX.png").toUri().toString());
        iv.setFitHeight(25);
        iv.setFitWidth(25);
        back.setGraphic(iv);
        back.setLayoutY(y);
        back.setLayoutX(x);
        return back;
    }
}
