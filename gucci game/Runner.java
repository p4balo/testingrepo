import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Runner extends Application {
    private ArrayList<String> idList = new ArrayList<>();
    private Pane root;
    private int cloutCount;
    public Runner(){ root = new Pane();}
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        root.getChildren().add(titleScreen());

        
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
        primaryStage.setTitle("Gucci Game");
    }
    private Pane titleScreen(){
        Pane miniPane = new Pane();

        ImageView iv1 = new ImageView("resources/Objects/belt.png");
        iv1.setLayoutY(175);
        iv1.setLayoutX(50);
        iv1.setFitHeight(200);
        iv1.setFitWidth(250);
        root.getChildren().add(iv1);

        ImageView iv2 = new ImageView("resources/Objects/backpack.png");
        iv2.setLayoutY(150);
        iv2.setLayoutX(500);
        iv2.setFitHeight(200);
        iv2.setFitWidth(200);
        root.getChildren().add(iv2);

        Rectangle r1 = new Rectangle(180+75,15,250,50);
        r1.setFill(Color.RED);
        r1.setStroke(Color.RED);
        r1.setStrokeWidth(1);
        miniPane.getChildren().add(r1);

        Text t1 = new Text("Gucci Game");
        t1.setLayoutY(50);
        t1.setLayoutX(225+60);
        t1.setFont(new Font(32));
        t1.setStroke(Color.WHITE);
        t1.setFill(Color.WHITE);
        miniPane.getChildren().add(t1);


        Button b1 = new Button("Start Quest");
        b1.setFont(new Font("Comic Sans MS",16));
        b1.setLayoutX(300);
        b1.setLayoutY(200);
        b1.setOnAction(event -> {
            root.getChildren().remove(miniPane);
            initGame();
        });
        miniPane.getChildren().add(b1);

        //cssify button
        Button b2 = new Button();
        b2.setLayoutX(0);
        b2.setLayoutY(550);
        ImageView resource1 = new ImageView("resources/Objects/cog.png");
        resource1.setFitWidth(60);
        resource1.setFitHeight(50);
        b2.setGraphic(resource1);
        miniPane.getChildren().add(b2);
        idList.add("Option Button");
        b2.setId(idList.get(idList.size()-1));
        b2.getStylesheets().add("Stylesheet.css");

        return miniPane;
    }
    private void initGame(){

    }
}
