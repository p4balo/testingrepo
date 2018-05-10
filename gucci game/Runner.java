import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class Runner extends Application {
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

        Rectangle r1 = new Rectangle(180+75,15,250,50);
        r1.setFill(Color.RED);
        r1.setStroke(Color.RED);
        r1.setStrokeWidth(1);
        miniPane.getChildren().add(r1);

        Text t1 = new Text("Gucci Game");
        t1.setLayoutY(50);
        t1.setLayoutX(225+75);
        t1.setFont(new Font("Comic Sans MS",32));
        t1.setStroke(Color.WHITE);
        t1.setFill(Color.WHITE);
        miniPane.getChildren().add(t1);

        Button b1 = new Button("Start Quest");
        b1.setLayoutX(300);
        b1.setLayoutY(200);
        b1.setOnAction(event -> {
            root.getChildren().remove(miniPane);
            initGame();
        });
        miniPane.getChildren().add(b1);

        Button b2 = new Button();
        b2.setLayoutX(0);
        b2.setLayoutY(575);
        //Cog
        //b2.setGraphic();
        miniPane.getChildren().add(b2);

        return miniPane;
    }
    private void initGame(){

    }
}
