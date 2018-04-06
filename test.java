import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class test extends Application{
    private Pane root;
    private double windowX, windowY;
    private double translateX, translateY;
    private double objectX, objectY;
    private double width, height;
    private int numberOfTimers;
    private int currentTimeSeconds;
    private boolean resizeA;
    private boolean resizeB;
    private String css;
    private ArrayList<Group> tasks = new ArrayList<>();
    private ArrayList<Boolean> containsTimer = new ArrayList<>();
    private ArrayList<Integer> startingTime = new ArrayList<>();
    private EventHandler<MouseEvent> onMousePressed = event -> {
        windowX = event.getSceneX();
        windowY = event.getSceneY();
        translateX = ((Rectangle) (event.getSource())).getTranslateX();
        translateY = ((Rectangle) (event.getSource())).getTranslateY();
        width = ((Rectangle) (event.getSource())).getWidth();
        height = ((Rectangle) (event.getSource())).getHeight();
        objectX = translateX+50;
        objectY = translateY+80;
        System.out.println(event.getSceneX()+", "+event.getSceneY());
        System.out.println((objectX+width)+", "+(objectY+height));
        if(event.getSceneY()>=objectY+height-3&&event.getSceneY()<=objectY+height&&event.getSceneX()>=objectX&&event.getSceneX()<=objectX+width){
            resizeB = true;
            ((Rectangle)(event.getSource())).setCursor(Cursor.S_RESIZE);
        }
        if(event.getSceneY()>=objectY&&event.getSceneY()<=objectY+height&&event.getSceneX()>=objectX+width-3&&event.getSceneX()<=objectX+width){
            resizeA = true;
            ((Rectangle)(event.getSource())).setCursor(Cursor.W_RESIZE);
        }
        if(resizeB&&resizeA) ((Rectangle)(event.getSource())).setCursor(Cursor.NW_RESIZE);
    };
    private EventHandler<MouseEvent> onRelease = event -> {
        resizeA = false;
        resizeB = false;
        ((Rectangle)(event.getSource())).setCursor(Cursor.HAND);
    };
    private EventHandler<MouseEvent> onDragEvent = event ->{
        if(!resizeA&&!resizeB) {
            double offsetX = event.getSceneX() - windowX;
            double offsetY = event.getSceneY() - windowY;
            double newTranslateX = translateX + offsetX;
            double newTranslateY = translateY + offsetY;
            ((Rectangle) (event.getSource())).setTranslateX(newTranslateX);
            ((Rectangle) (event.getSource())).setTranslateY(newTranslateY);
        }else if(resizeA&&resizeB){
            double offsetX = event.getSceneX() - windowX;
            double offsetY = event.getSceneY() - windowY;
            double newTranslateX = offsetX + width;
            double newTranslateY = offsetY + height;
            if(newTranslateX>=10&&newTranslateY>=10) {
                ((Rectangle) (event.getSource())).setWidth(newTranslateX);
                ((Rectangle) (event.getSource())).setHeight(newTranslateY);
            }
        }
        else if(resizeA){
            double offsetX = event.getSceneX() - windowX;
            double newTranslateX = offsetX + width;
            if(newTranslateX>=10) ((Rectangle) (event.getSource())).setWidth(newTranslateX);
        }else if(resizeB){
            double offsetY = event.getSceneY() - windowY;
            double newTranslateY = offsetY + height;
            if(newTranslateY>=10) ((Rectangle) (event.getSource())).setHeight(newTranslateY);
        }
    };
    public test(){
        css = this.getClass().getResource("Stylesheet.css").toExternalForm();
        root = new Pane();
    }
    public static void main(String[] args) { launch(args); }
    public void start(Stage primaryStage) throws Exception {
        Timer t = new Timer();
        t.schedule(new TimerTask() {public void run() { currentTimeSeconds++; }},1000);
        drawToolBar();
        drawWindow(false);
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
        primaryStage.setTitle("test");
    }
    private void drawWindow(boolean Timer){
        Group g = new Group();
        Rectangle r = new Rectangle(50, 80, 50, 50);
        r.setFill(Color.WHITESMOKE);
        r.setStroke(Color.DIMGRAY);
        r.setStrokeWidth(3);
        r.setCursor(Cursor.HAND);
        r.setOnMousePressed(onMousePressed);
        r.setOnMouseDragged(onDragEvent);
        r.setOnMouseReleased(onRelease);
        r.setId("Rectangle"+tasks.size());
        root.getChildren().addAll(r);
        Rectangle r2 = new Rectangle(50, 80, 50, 50);
        r2.setFill(Color.WHITESMOKE);
        r2.setStroke(Color.DIMGRAY);
        r2.setStrokeWidth(3);
        r2.setCursor(Cursor.HAND);
        r2.setOnMousePressed(onMousePressed);
        r2.setOnMouseDragged(onDragEvent);
        r2.setOnMouseReleased(onRelease);
        r2.setId("Rectangle"+tasks.size());
        g.getChildren().addAll(r2);
        tasks.add(g);
        for(Group g2 : tasks){
            System.out.println(g2.getChildren().toString());
        }
        if(Timer){
            containsTimer.add(true);
        }
    }
    private void drawToolBar(){
        Rectangle toolBarRectangle = new Rectangle(-10,-10,900,70);
        toolBarRectangle.setFill(Color.GHOSTWHITE);
        toolBarRectangle.setStroke(Color.BLACK);
        toolBarRectangle.setStrokeWidth(1);

        Text t = new Text("Task");
        t.setFont(new Font(30));
        t.setLayoutX(10);
        t.setLayoutY(40);

        Button addWindow = new Button();
        Image plus = new Image(getClass().getResourceAsStream("greenPlus.png"));
        ImageView view = new ImageView(plus);
        view.setFitHeight(30);
        view.setFitWidth(30);

        addWindow.setGraphic(view);
        addWindow.setLayoutX(90);
        addWindow.setLayoutY(10);
        addWindow.getStylesheets().add(css);
        addWindow.setOnAction(event -> drawWindow(false));

        CheckBox cb = new CheckBox();
        Label l = new Label("Timer");
        l.setLabelFor(cb);
        l.setLayoutY(10);
        l.setLayoutX(170);
        l.setFont(new Font(30));
        cb.setLayoutX(255);
        cb.setLayoutY(15);
        cb.getStylesheets().add(css);

        root.getChildren().addAll(toolBarRectangle, addWindow, t, cb, l);
    }
}


