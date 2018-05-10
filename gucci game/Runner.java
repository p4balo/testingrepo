import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Runner extends Application {
    private Pane p;
    private int cloutCount;
    public Runner(){ p = new Pane();}
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {

        
        primaryStage.setScene(new Scene(p,800,600));
        primaryStage.show();
        primaryStage.setTitle("Gucci Game");
    }
}
