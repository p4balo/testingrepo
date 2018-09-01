import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

public class test extends Application {
    private int currentTimeSeconds;
    private int currentTimeMinutes;
    private int currentTimeHours;
    private int numberOfTimers;
    private double windowX, windowY;
    private double translateX, translateY;
    private double objectX, objectY;
    private double width, height;
    private boolean resizeA, resizeB;
    private boolean currentTimer = false;
    private boolean toFront = false;
    private ArrayList<Integer> timeKeeper;
    private EventHandler<MouseEvent> onMousePressed = event -> {
        windowX = event.getSceneX();
        windowY = event.getSceneY();
        translateX = ((Pane) (event.getSource())).getTranslateX();
        translateY = ((Pane) (event.getSource())).getTranslateY();
        width = ((Pane) (event.getSource())).getWidth();
        height = ((Pane) (event.getSource())).getHeight();
        objectX = translateX+50;
        objectY = translateY+80;

        if(event.getSceneY()>=objectY+height-3&&event.getSceneY()<=objectY+height&&event.getSceneX()>=objectX&&event.getSceneX()<=objectX+width){
            resizeB = true;
            System.out.println("test");
            ((Pane)(event.getSource())).setCursor(Cursor.S_RESIZE);
        }
        if(event.getSceneY()>=objectY&&event.getSceneY()<=objectY+height&&event.getSceneX()>=objectX+width-3&&event.getSceneX()<=objectX+width){
            System.out.println("test");
            resizeA = true;
            ((Pane)(event.getSource())).setCursor(Cursor.W_RESIZE);
        }
        if(resizeB&&resizeA){ ((Pane)(event.getSource())).setCursor(Cursor.NW_RESIZE); }

    };
    private EventHandler<MouseEvent> onRelease = event -> {
        resizeA = false;
        resizeB = false;
        ((Pane)(event.getSource())).setCursor(Cursor.HAND);
    };
    private EventHandler<MouseEvent> onDragEvent = event ->{
        if(!resizeA&&!resizeB) {
            double offsetX = event.getSceneX() - windowX;
            double offsetY = event.getSceneY() - windowY;
            double newTranslateX = translateX + offsetX;
            double newTranslateY = translateY + offsetY;
            ((Pane) (event.getSource())).setTranslateX(newTranslateX);
            ((Pane) (event.getSource())).setTranslateY(newTranslateY);
        }else if(resizeA&&resizeB){
            double offsetX = event.getSceneX() - windowX;
            double offsetY = event.getSceneY() - windowY;
            double newTranslateX = offsetX + width;
            double newTranslateY = offsetY + height;
            if(newTranslateX>=10&&newTranslateY>=10) {
                Node n = ((Pane) (event.getSource())).getChildren().get(0);
                Rectangle r = (Rectangle) n;
                r.setHeight(newTranslateY);
                r.setWidth(newTranslateX);
            }
        }
        else if(resizeA){
            double offsetX = event.getSceneX() - windowX;
            double newTranslateX = offsetX + width;
            if(newTranslateX>=10) {
                Node n = ((Pane) (event.getSource())).getChildren().get(0);
                Rectangle r = (Rectangle) n;
                r.setWidth(newTranslateX);
            }
        }else if(resizeB){
            double offsetY = event.getSceneY() - windowY;
            double newTranslateY = offsetY + height;
            if(newTranslateY>=10) {
                Node n = ((Pane) (event.getSource())).getChildren().get(0);
                Rectangle r = (Rectangle) n;
                r.setHeight(newTranslateY);
            }
        }
    };
    private Pane root;
    public test(){
        timeKeeper = new ArrayList<>();
        root = new Pane();
    }
    public static void main(String[] args) { launch(args);}
    private void startTime(Stage s){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(!currentTimer)
                    currentTimeSeconds++;
                if(currentTimer){
                    for(int i = 0; i<timeKeeper.size(); i++){
                        if(timeKeeper.get(i)==currentTimeSeconds){
                            for(int f = 0; f<root.getChildren().size(); f++){
                                if(root.getChildren().get(f).toString().contains("Pane")){
                                    Node n = root.getChildren().get(f);
                                    Pane p = (Pane) n;
                                    for(int j = 0; j<p.getChildren().size(); j++){
                                        if(p.getChildren().get(j).toString().contains("Text[")){
                                            Node n1 = p.getChildren().get(j);
                                            if(n1.getId()!=null){
                                                if(n1.getId().contains("Timer")){
                                                    Text t = (Text) n1;
                                                    t.setFont(new Font(t.getFont().getSize()-1.5));
                                                    t.setText("Time Over");
                                                    toFront = true;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            timeKeeper.remove(i);
                            numberOfTimers--;
                        }else {
                            for (int f = 0; f < root.getChildren().size(); f++) {
                                if (root.getChildren().get(f).toString().contains("Pane")) {
                                    Node n = root.getChildren().get(f);
                                    Pane p = (Pane) n;
                                    for (int j = 0; j < p.getChildren().size(); j++) {
                                        if (p.getChildren().get(j).toString().contains("Text[")) {
                                            Node n1 = p.getChildren().get(j);
                                            if (n1.getId() != null) {
                                                if (n1.getId().contains("Timer")) {
                                                    Text t = (Text) n1;
                                                    t.setText(formatTime(timeKeeper.get(i)-currentTimeSeconds));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(timeKeeper.size()==0){
                        currentTimer = false;
                    }
                    currentTimeSeconds++;
                }
                startTime(s);
            }
        },1000);
    }
    private String getTime(){
        if(currentTimeSeconds>60){
            currentTimeMinutes++;
            currentTimeSeconds-=60;
        }
        if(currentTimeMinutes>60){
            currentTimeHours++;
            currentTimeMinutes-=60;
        }
        if(currentTimeSeconds<10){
            if(currentTimeMinutes<10){
                if(currentTimeHours<10){
                    return "0"+currentTimeHours +":0"+ currentTimeMinutes +":0"+currentTimeSeconds+"";
                }
                return currentTimeHours +":0"+ currentTimeMinutes +":0"+currentTimeSeconds;
            }
            return currentTimeHours +":"+ currentTimeMinutes +":0"+currentTimeSeconds;
        }else if(currentTimeMinutes<10){
            if(currentTimeHours<10){
                return "0"+currentTimeHours +":0"+ currentTimeMinutes +":"+currentTimeSeconds;
            }
            return currentTimeHours +":0"+ currentTimeMinutes +":"+currentTimeSeconds;
        }else if(currentTimeHours<10){
            return "0"+currentTimeHours +":"+ currentTimeMinutes +":"+currentTimeSeconds;
        }
        return currentTimeHours +":"+ currentTimeMinutes +":"+currentTimeSeconds;
    }
    private String formatTime(int seconds){
        int minutes = 0;
        int hours = 0;
        if(seconds>60){
            while(seconds>60){
                minutes++;
                seconds-=60;
            }
        }
        if(minutes>60){
            while(minutes>60){
                hours++;
                minutes-=60;
            }
        }
        if(seconds<10){
            if(minutes<10){
                if(hours<10){
                    return "0"+hours +":0"+ minutes +":0"+seconds+"";
                }
                return hours +":0"+ minutes +":0"+seconds;
            }
            return hours +":"+ minutes +":0"+seconds;
        }else if(minutes<10){
            if(hours<10){
                return "0"+hours +":0"+ minutes +":"+seconds;
            }
            return hours +":0"+ minutes +":"+seconds;
        }else if(hours<10){
            return "0"+hours +":"+ minutes +":"+seconds;
        }
        return hours +":"+ minutes +":"+seconds;

    }
    private Pane createWindow(){
        Rectangle r = new Rectangle(100, 100);
        r.setFill(Color.WHITESMOKE);
        r.setStroke(Color.DIMGRAY);
        r.setStrokeWidth(3);
        r.setCursor(Cursor.HAND);

        Pane miniRoot = new Pane();
        Text t = new Text();
        t.setFont(new Font(17));
        t.setLayoutY(t.getFont().getSize());
        t.setLayoutX(5);

        TextField tf = new TextField();
        tf.setLayoutX(5);
        tf.setLayoutY(t.getFont().getSize()+tf.getHeight()+5);
        tf.setMaxWidth(90);

        miniRoot.getChildren().addAll(r, t, tf);
        miniRoot.setLayoutX(50);
        miniRoot.setLayoutY(80);
        miniRoot.setOnMousePressed(onMousePressed);
        miniRoot.setOnMouseDragged(onDragEvent);
        miniRoot.setOnMouseReleased(onRelease);
        miniRoot.setId("ContainsTimer");
        return miniRoot;
    }
    private Pane createWindow(int time){
        Rectangle r = new Rectangle(100, 100);
        r.setFill(Color.WHITESMOKE);
        r.setStroke(Color.DIMGRAY);
        r.setStrokeWidth(3);
        r.setCursor(Cursor.HAND);

        Pane miniRoot = new Pane();
        Text t = new Text();
        timeKeeper.add(time+currentTimeSeconds);
        t.setText(formatTime(timeKeeper.get(timeKeeper.size()-1)-currentTimeSeconds));
        currentTimer=true;
        numberOfTimers++;
        t.setFont(new Font(17));
        t.setLayoutY(t.getFont().getSize());
        t.setLayoutX(5);
        t.setId("Timer"+numberOfTimers);

        TextField tf = new TextField();
        tf.setLayoutX(5);
        tf.setLayoutY(t.getFont().getSize()+tf.getHeight()+5);
        tf.setMaxWidth(90);

        miniRoot.getChildren().addAll(r, t, tf);
        miniRoot.setLayoutX(50);
        miniRoot.setLayoutY(80);
        miniRoot.setOnMousePressed(onMousePressed);
        miniRoot.setOnMouseDragged(onDragEvent);
        miniRoot.setOnMouseReleased(onRelease);
        miniRoot.setId("ContainsTimer");
        return miniRoot;
    }
    private Pane createWindow(String text){
        Rectangle r = new Rectangle(100, 100);
        r.setFill(Color.WHITESMOKE);
        r.setStroke(Color.DIMGRAY);
        r.setStrokeWidth(3);
        r.setCursor(Cursor.HAND);

        Pane miniRoot = new Pane();

        Text t2 = new Text(text);
        t2.setLayoutX(5);
        t2.setLayoutY(t2.getFont().getSize()+5);
        t2.setWrappingWidth(90);

        miniRoot.getChildren().addAll(r, t2);
        miniRoot.setLayoutX(50);
        miniRoot.setLayoutY(80);
        miniRoot.setOnMousePressed(onMousePressed);
        miniRoot.setOnMouseDragged(onDragEvent);
        miniRoot.setOnMouseReleased(onRelease);
        miniRoot.setId("ContainsTimer");
        return miniRoot;
    }
    private Pane createWindow(int time, String text){
        Rectangle r = new Rectangle(100, 100);
        r.setFill(Color.WHITESMOKE);
        r.setStroke(Color.DIMGRAY);
        r.setStrokeWidth(3);
        r.setCursor(Cursor.HAND);

        Pane miniRoot = new Pane();
        Text t = new Text();
        System.out.println(time);
        timeKeeper.add(time+currentTimeSeconds);
        t.setText(formatTime(timeKeeper.get(timeKeeper.size()-1)-currentTimeSeconds));
        currentTimer=true;
        numberOfTimers++;
        t.setFont(new Font(17));
        t.setLayoutY(t.getFont().getSize());
        t.setLayoutX(5);
        t.setId("Timer"+numberOfTimers);

        Text t2 = new Text(text);
        t2.setLayoutX(5);
        t2.setLayoutY(t.getFont().getSize()+t2.getFont().getSize()+5);
        t2.setWrappingWidth(90);

        miniRoot.getChildren().addAll(r, t, t2);
        miniRoot.setLayoutX(50);
        miniRoot.setLayoutY(80);
        miniRoot.setOnMousePressed(onMousePressed);
        miniRoot.setOnMouseDragged(onDragEvent);
        miniRoot.setOnMouseReleased(onRelease);
        miniRoot.setId("ContainsTimer");
        return miniRoot;
    }
    public void start(Stage primaryStage) throws Exception {
        startTime(primaryStage);
        TextField t = new TextField();
        t.setLayoutX(65);
        t.setLayoutY(5);
        root.getChildren().add(t);
        Label l1 = new Label("Time\n(in secs)");
        l1.setLabelFor(t);
        l1.setLayoutY(5);
        l1.setLayoutX(5);
        root.getChildren().add(l1);

        TextField t2 = new TextField();
        t2.setLayoutX(65);
        t2.setLayoutY(45);
        root.getChildren().add(t2);
        Label l2 = new Label("Text");
        l2.setLabelFor(t);
        l2.setLayoutY(45);
        l2.setLayoutX(5);
        root.getChildren().add(l2);

        Button b2 = new Button("New Field");
        b2.setLayoutX(245);
        b2.setLayoutY(5);
        b2.setOnAction(event -> {
            if((t.getText()==null||t.getText().equals(""))&&t2.getText()==null){
                root.getChildren().addAll(createWindow());
            }else if(t.getText()==null||t.getText().equals("")){
                root.getChildren().addAll(createWindow(t2.getText()));
            }
            else if(t2.getText()!=null) {
                root.getChildren().addAll(createWindow(Integer.parseInt(t.getText()), t2.getText()));
            }
            else {
                root.getChildren().add(createWindow(Integer.parseInt(t.getText())));
            }
            t.setText("");
            t2.setText("");
        });
        root.getChildren().add(b2);

        Button b3 = new Button("350x300");
        b3.setOnAction(event -> {
            primaryStage.setMaxWidth(350);
            primaryStage.setMaxHeight(300);
            primaryStage.setMinWidth(350);
            primaryStage.setMinHeight(300);
        });
        b3.setLayoutY(45);
        b3.setLayoutX(245);
        root.getChildren().add(b3);
        Button b4 = new Button("900x700");
        b4.setOnAction(event -> {
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(700);
            primaryStage.setMaxWidth(900);
            primaryStage.setMaxHeight(700);
        });
        b4.setLayoutY(85);
        b4.setLayoutX(245);
        root.getChildren().add(b4);

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setX(0);
        primaryStage.setY(0);

        primaryStage.setScene(new Scene(root, 900,700));
        primaryStage.setTitle("test");
        primaryStage.show();
    }
}
