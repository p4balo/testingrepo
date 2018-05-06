import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

import java.util.ArrayList;

public class PolyMaker extends Application {
    private Pane root;
    private ArrayList<Point2D> pointCollection;
    private int shapeType = 0;
    private int currentPosition;
    private double sliderValue;
    public PolyMaker(){
        root = new Pane();
        pointCollection = new ArrayList<>();

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        ComboBox<String> typeOfShape = new ComboBox<>();
        typeOfShape.getItems().addAll("Polygon","Line");
        typeOfShape.setLayoutY(500);
        typeOfShape.setLayoutX(250);
        typeOfShape.setOnAction(event -> {
            if(typeOfShape.getValue().equals("Polygon")){
                shapeType = 0;
//                for(int i = 0; i<root.getChildren().size(); i++){
//                    if(root.getChildren().get(i).toString().contains("Pane")){
//                        if(root.getChildren().get(i).getId().contains("Clout"))
//                        root.getChildren().remove(i);
//                        i--;
//                    }
//                }
            }else{
                shapeType = 1;
                root.getChildren().remove(drawSlider());
            }
        });
        root.getChildren().add(typeOfShape);

        root.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.A){
                currentPosition++;
                System.out.println(currentPosition);
            }
            if(currentPosition==1){
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).getId()!=null) {
                        if (root.getChildren().get(i).getId().contains("line")) {
                            root.getChildren().remove(i);
                            i--;
                        }
                    }
                    if(root.getChildren().get(i).getId()!=null){
                        if (root.getChildren().get(i).getId().contains("editableText")) {
                            Node n = root.getChildren().get(i);
                            Text t = (Text) n;
                            t.setText("And your job is to collect as much clout as possible");
                        }
                    }
                }
            }
            if(currentPosition==2){
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).getId()!=null){
                        if (root.getChildren().get(i).getId().contains("editableText")) {
                            Node n = root.getChildren().get(i);
                            Text t = (Text) n;
                            t.setText("This is done in multiple ways such as getting designer clothes");
                        }
                    }
                }
            }
            if(currentPosition==3){
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).getId()!=null){
                        if (root.getChildren().get(i).getId().contains("editableText")) {
                            Node n = root.getChildren().get(i);
                            Text t = (Text) n;
                            t.setText("But designer cloths do not come for free, so you need to grind");
                        }
                    }
                }
            }
            if(currentPosition==4){
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).getId()!=null){
                        if (root.getChildren().get(i).getId().contains("editableText")) {
                            Node n = root.getChildren().get(i);
                            Text t = (Text) n;
                            t.setText("Collect money to increase your inventory and your street creds");
                        }
                    }
                }
                drawMoney();
                drawClout();
                drawPFP();
            }
            if(currentPosition==5){
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).getId()!=null){
                        if (root.getChildren().get(i).getId().contains("editableText")) {
                            Node n = root.getChildren().get(i);
                            Text t = (Text) n;
                            t.setText("That reminds me, if forgot how you look lemme set you up");
                        }
                    }
                }
            }
        });
        root.setOnMousePressed(event -> {
            System.out.println(event.getSceneX()+", "+event.getSceneY());
            Point2D p2d = new Point2D(event.getSceneX(),event.getSceneY());
            pointCollection.add(p2d);
            Circle c = new Circle(p2d.getX(), p2d.getY(), 1);
            root.getChildren().add(c);
        });
        Button b = new Button("Create");
        b.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                if(shapeType==0) {
                    Polygon py = new Polygon();
                    for (Point2D aPointCollection : pointCollection) {
                        py.getPoints().addAll(aPointCollection.getX(), aPointCollection.getY());
                    }
                    for (int i = 0; i < root.getChildren().size(); i++) {
                        if (root.getChildren().get(i).toString().contains("Circle")) {
                            root.getChildren().remove(i);
                        }
                    }
                    root.getChildren().add(py);
                    System.out.println("{");
                    for (int i = 0; i < root.getChildren().size(); i++) {
                        System.out.print(pointCollection.get(i).getX() + ", " + pointCollection.get(i).getY() + ",\n");
                    }
                    System.out.println("}");
                    pointCollection.clear();
                }else{
                    Line l = new Line();
                    l.setStartX(pointCollection.get(0).getX());
                    l.setStartY(pointCollection.get(0).getY());
                    l.setEndX(pointCollection.get(1).getX());
                    l.setEndY(pointCollection.get(1).getY());
                    root.getChildren().add(l);
                    System.out.println("{");
                    for (int i = 0; i < pointCollection.size(); i++) {
                        System.out.print(pointCollection.get(i).getX() + ", " + pointCollection.get(i).getY() + ",\n");
                    }
                    System.out.println("}");
                    pointCollection.clear();

                }
            }
        });
        b.setOnAction(event ->{
            if(shapeType==0) {
                Polygon py = new Polygon();
                for (Point2D aPointCollection : pointCollection) {
                    py.getPoints().addAll(aPointCollection.getX(), aPointCollection.getY());
                }
                for (int i = 0; i < root.getChildren().size(); i++) {
                    if (root.getChildren().get(i).toString().contains("Circle")) {
                        root.getChildren().remove(i);
                        i--;
                    }
                }
                root.getChildren().add(py);
                System.out.println("{");
                for (Point2D aPointCollection : pointCollection) {
                    System.out.print(aPointCollection.getX() + ", " + aPointCollection.getY() + ",\n");
                }
                System.out.print("}");
                pointCollection.clear();
            }else{
                Line l = new Line();
                l.setStartX(pointCollection.get(0).getX());
                l.setStartY(pointCollection.get(0).getY());
                l.setEndX(pointCollection.get(1).getX());
                l.setEndY(pointCollection.get(1).getY());
                l.setStrokeWidth(sliderValue);
                root.getChildren().add(l);
                System.out.println("{");
                for (int i = 0; i < pointCollection.size(); i++) {
                    System.out.print(pointCollection.get(i).getX() + ", " + pointCollection.get(i).getY() + ",\n");
                }
                System.out.println("}");
                System.out.println(sliderValue);
                pointCollection.clear();

            }
        });
        b.setLayoutY(500);
        root.getChildren().add(b);

        Button b2 = new Button("Clear");
        b2.setOnAction(event -> {
            for (int i = 0; i < root.getChildren().size(); i++) {
                if (root.getChildren().get(i).toString().contains("Polygon")||
                        root.getChildren().get(i).toString().contains("Circle")||
                        root.getChildren().get(i).toString().contains("Line")) {
                    if(root.getChildren().get(i).getId()!=null) {
                        if (!root.getChildren().get(i).getId().equals("TestObject")) {
                            root.getChildren().remove(i);
                            i--;
                        }
                    }else{
                        root.getChildren().remove(i);
                        i--;
                    }
                }
            }
        });
        b2.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER){
                for (int i = 0; i < root.getChildren().size(); i++) {
                    if (root.getChildren().get(i).toString().contains("Polygon")||
                            root.getChildren().get(i).toString().contains("Circle")||
                            root.getChildren().get(i).toString().contains("Line")) {
                        if(root.getChildren().get(i).getId()!=null) {
                            if (!root.getChildren().get(i).getId().equals("TestObject")) {
                                root.getChildren().remove(i);
                                i--;
                            }
                        }else{
                            root.getChildren().remove(i);
                            i--;
                        }
                    }
                }
            }
        });
        b2.setLayoutY(500);
        b2.setLayoutX(75);
        root.getChildren().add(b2);

        Button b3 = new Button("Undo Point");
        b3.setOnAction(event -> {
            pointCollection.remove(pointCollection.size() - 1);
            root.getChildren().remove(root.getChildren().size()-1);
        });
        b3.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER){
                pointCollection.remove(pointCollection.size()-1);
                root.getChildren().remove(root.getChildren().size()-1);
            }
        });
        b3.setLayoutY(500);
        b3.setLayoutX(150);
        root.getChildren().add(b3);

        testShape();
        root.getChildren().add(ObjectStorage.drawHouse(50,100));
        root.getChildren().add(ObjectStorage.drawHouse(250,100));
        root.getChildren().add(ObjectStorage.drawHouse(450,100));
        root.getChildren().add(ObjectStorage.drawHouse(650,100));

        primaryStage.setScene(new Scene(root, 800,600));
        primaryStage.setTitle("PolyMaker");
        primaryStage.show();

    }
    @NotNull
    private Pane drawSlider(){
        Pane miniPane = new Pane();

        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(7.5);
        slider.setBlockIncrement(.25);
        slider.setShowTickLabels(true);
        slider.setOnMouseDragged(event -> {
            sliderValue = slider.getValue();
            System.out.println(sliderValue);
        });
        slider.setLayoutY(500);
        slider.setLayoutX(375);
        root.getChildren().add(slider);

        return miniPane;
    }
    private void testShape(){
        Rectangle r3 = new Rectangle(0,0,800,300);
        r3.setStroke(Color.SEAGREEN);
        r3.setFill(Color.SEAGREEN);
        root.getChildren().add(r3);

        Rectangle r1 = new Rectangle(98,15,310,90);
        r1.setStroke(Color.BLACK);
        r1.setStrokeWidth(3);
        r1.setFill(Color.WHITESMOKE);
        root.getChildren().add(r1);

        Text t1 = new Text("So this is the clout house \n Press A to continue");
        t1.setFont(new Font(24));
        t1.setLayoutX(100);
        t1.setLayoutY(41);
        t1.setWrappingWidth(302);
        t1.setId("editableText");
        root.getChildren().add(t1);

        for(int i = 0; i<16; i++) {
            Rectangle r = new Rectangle(50 * i, 300, 50, 50);
            r.setStrokeWidth(3);
            r.setStroke(Color.DARKGRAY);
            r.setFill(Color.LIGHTGRAY);
            root.getChildren().add(r);
        }
        for(int i = 0; i<16; i++) {
            Rectangle r = new Rectangle(50 * i, 425, 50, 50);
            r.setStrokeWidth(3);
            r.setStroke(Color.DARKGRAY);
            r.setFill(Color.LIGHTGRAY);
            root.getChildren().add(r);
        }

        Line l1 = new Line(60.0, 95.0, 95.0,145.0);
        l1.setStrokeWidth(3);
        l1.setStroke(Color.RED);
        l1.setId("line");
        root.getChildren().add(l1);

        Line l2 = new Line(95,145,105,115);
        l2.setStrokeWidth(3);
        l2.setStroke(Color.RED);
        l2.setId("line");
        root.getChildren().add(l2);

        Line l3 = new Line(95,145,65,140);
        l3.setStrokeWidth(3);
        l3.setStroke(Color.RED);
        l3.setId("line");
        root.getChildren().add(l3);

        Rectangle r2 = new Rectangle(0,350,800,75);
        r2.setFill(Color.BLACK);
        root.getChildren().add(r2);

        for(int i = 0; i<20; i++){
            Rectangle r = new Rectangle(15+(50*i),387.5,25,2);
            r.setFill(Color.YELLOW);
            r.setStroke(Color.YELLOW);
            root.getChildren().add(r);
        }

        Polygon py1 = new Polygon(
                345.0, 105.0,
                360.0, 125.0,
                370.0, 105.0
        );
        py1.setFill(Color.TRANSPARENT);
        py1.setStroke(Color.BLACK);
        py1.setStrokeWidth(3);
        root.getChildren().add(py1);
    }
    private void drawMoney(){

    }
    private void drawClout(){
    }
    private void drawPFP(){
        Rectangle r1 = new Rectangle(725,0,75,75);
        r1.setStroke(Color.BLACK);
        r1.setStrokeWidth(3);
        r1.setFill(Color.TRANSPARENT);
        root.getChildren().add(r1);
        Rectangle r2 = new Rectangle(750, 75, 50,150);
        r2.setStroke(Color.BLACK);
        r2.setStrokeWidth(3);
        r2.setFill(Color.WHITE);
        root.getChildren().add(r2);
    }
    private void drawArrow(int arrowNum){
        Line l1 = new Line(625, 35,700,35);
        l1.setStroke(Color.RED);
        l1.setStrokeWidth(3.5);
        l1.setId("line");
    }
}
