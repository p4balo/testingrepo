import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FormulaGraphics extends Application {
    private Pane root = new Pane();
    private ComboBox<String> legSelector;
    private Text pythagoreanText;
    private Text quadraticText;
    private Text distanceFormulaText;
    private Text volumeConeText;
    private TextField inputField;
    private Button setItem;

    private double legA;
    private double legB;
    private double hypotenuse;

    private double a;
    private double b;
    private double c;
    private double root1;
    private double root2;

    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double distance;

    private double h;
    private double r;
    private double volume;

    private boolean itemAdded;
    private boolean calculated;
    
    private int previous = -1;
    private int current;

    private ObservableList<String> texts = FXCollections.observableArrayList();
    public FormulaGraphics(){
        texts.add("Leg A");
        texts.add("Hypotenuse");
        texts.add("Roots");
        texts.add("Volume Cone");
        texts.add("Distance Formula");

        pythagoreanText = new Text("Pythagorean Theorem \nLeg A: "+legA+"\nLeg B: "+legB+"\nHypotenuse: "+hypotenuse);
        pythagoreanText.setFont(new Font(18));
        pythagoreanText.setLayoutX(50);
        pythagoreanText.setLayoutY(200);
        pythagoreanText.setLineSpacing(20);

        quadraticText = new Text("Quadratic Formula \nA: "+a+"\nB: "+b+"\nC: "+c+"\nX1: "+root1+"\nX2: "+root2);
        quadraticText.setFont(new Font(18));
        quadraticText.setLayoutX(50);
        quadraticText.setLayoutY(200);
        quadraticText.setLineSpacing(20);

        volumeConeText = new Text("Volume Cone \nPi: "+Math.PI+"\nr: "+r+"\nH: "+h+"\nVolume: "+volume);
        volumeConeText.setFont(new Font(18));
        volumeConeText.setLayoutX(50);
        volumeConeText.setLayoutY(200);
        volumeConeText.setLineSpacing(20);

        distanceFormulaText = new Text("Distance Formula \nX1: "+x1+"\nX2: "+x2+"\nY1: "+"\nY2: "+y2+"\nDistance: "+distance);
        distanceFormulaText.setFont(new Font(18));
        distanceFormulaText.setLayoutX(50);
        distanceFormulaText.setLayoutY(200);
        distanceFormulaText.setLineSpacing(20);

        inputField = new TextField();
        inputField.setMaxSize(150,20);

        setItem = new Button("Set Item");
        setItem.setMaxSize(80,20);
    }

    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        Text infoText = new Text("What are you trying to find");
        infoText.setLayoutY(50);
        infoText.setLayoutX(200);
        infoText.setFont(new Font(18));
        root.getChildren().add(infoText);

        legSelector = new ComboBox<>();
        legSelector.setItems(texts);
        legSelector.setPromptText("Select An Item");
        legSelector.setLayoutX(250);
        legSelector.setLayoutY(70);
        root.getChildren().add(legSelector);

        primaryStage.setTitle("Formulas");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
        timer();
    }
    private void timer(){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(legSelector.getValue()!=null) {
                    if (legSelector.getValue().equals(texts.get(0))) {
                        current = 0;
                        if(previous!=current){
                            previous = 0;
                            itemAdded = false;
                        }
                        if (!itemAdded){
                            root.getChildren().remove(inputField);
                            root.getChildren().remove(pythagoreanText);
                            root.getChildren().remove(setItem);
                            root.getChildren().remove(quadraticText);
                            inputField.setText("");
                            pythagoreanText.setText("Pythagorean Theorem \nLeg A: " + legA + "\nLeg B: " + legB + "\nHypotenuse: " + hypotenuse);
                            root.getChildren().add(pythagoreanText);
                        }

                        if(legB==0 && hypotenuse==0 && !itemAdded) {
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(260);
                            inputField.setPromptText("Leg B");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(260);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    legB = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(hypotenuse==0 && !itemAdded && !calculated){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(300);
                            inputField.setPromptText("Hypotenuse");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(300);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    hypotenuse = Double.parseDouble(inputField.getText());
                                }
                                calculated = true;
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(calculated && !itemAdded){
                            if(hypotenuse>legB) {
                                legA = Math.sqrt((Math.pow(hypotenuse, 2)) - (Math.pow(legB, 2)));
                                root.getChildren().remove(pythagoreanText);
                                pythagoreanText.setText("Pythagorean Theorem \nLeg A: " + legA + "\nLeg B: " + legB + "\nHypotenuse: " + hypotenuse);
                                root.getChildren().add(pythagoreanText);
                            }else{
                                Text error = new Text("Hypotenuse is less than or equal to the leg");
                                error.setFont(new Font(20));
                                error.setLayoutX(200);
                                error.setLayoutY(120);
                                error.setFill(Color.RED);
                                root.getChildren().add(error);
                            }
                            Button reset = new Button("Reset");
                            reset.setLayoutX(400);
                            reset.setLayoutY(70);
                            reset.setOnAction(event -> {
                                legB = 0;
                                legA = 0;
                                hypotenuse = 0;
                                calculated = false;
                                itemAdded = false;
                            });
                            itemAdded = true;
                            root.getChildren().add(reset);
                        }
                    }
                    if (legSelector.getValue().equals(texts.get(1))) {
                        current = 1;
                        if(previous!=current){
                            previous = 1;
                            itemAdded = false;
                        }
                        if(!itemAdded) {
                            root.getChildren().remove(inputField);
                            root.getChildren().remove(pythagoreanText);
                            root.getChildren().remove(setItem);
                            root.getChildren().remove(quadraticText);
                            inputField.setText("");
                            pythagoreanText.setText("Pythagorean Theorem \nLeg A: " + legA + "\nLeg B: " + legB + "\nHypotenuse: " + hypotenuse);
                            root.getChildren().add(pythagoreanText);
                        }
                        if(legA==0 && !itemAdded && legB==0){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(220);
                            inputField.setPromptText("Leg A");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(220);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    legA = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(legB==0 && !itemAdded){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(260);
                            inputField.setPromptText("Leg A");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(260);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    legB = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                                calculated = true;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(!itemAdded && calculated){
                            hypotenuse = Math.sqrt((Math.pow(legA, 2)) + (Math.pow(legB, 2)));
                            root.getChildren().remove(pythagoreanText);
                            pythagoreanText.setText("Pythagorean Theorem \nLeg A: " + legA + "\nLeg B: " + legB + "\nHypotenuse: " + hypotenuse);
                            root.getChildren().add(pythagoreanText);
                            Button reset = new Button("Reset");
                            reset.setLayoutX(400);
                            reset.setLayoutY(70);
                            reset.setOnAction(event -> {
                                legB = 0;
                                legA = 0;
                                hypotenuse = 0;
                                calculated = false;
                                itemAdded = false;
                            });
                            itemAdded = true;
                            root.getChildren().add(reset);
                        }

                    }
                    if (legSelector.getValue().equals(texts.get(2))) {
                        current = 2;
                        if(previous!=current){
                            previous = 2;
                            itemAdded = false;
                        }
                        if(!itemAdded) {
                            root.getChildren().remove(pythagoreanText);
                            root.getChildren().remove(quadraticText);
                            root.getChildren().remove(setItem);
                            root.getChildren().remove(inputField);
                            inputField.setText("");
                            quadraticText.setText("Quadratic Formula \nA: " + a + "\nB: " + b + "\nC: " + c + "\nX1: " + root1 + "\nX2: " + root2);
                            root.getChildren().add(quadraticText);
                        }
                        if(a==0 && b==0 && c==0 && !itemAdded){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(220);
                            inputField.setPromptText("A");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(220);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    a = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(b==0 && c==0 && !itemAdded){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(260);
                            inputField.setPromptText("B");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(260);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    b = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(c==0 && !itemAdded){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(300);
                            inputField.setPromptText("A");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(300);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    c = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                                calculated = true;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(!itemAdded && calculated){
                            if(((Math.pow(b, 2)) - 4 * a * c)>=0) {
                                root1 = ((-1 * b) + (Math.sqrt(Math.pow(b, 2) - 4 * a * c))) / (2 * a);
                                root2 = ((-1 * b) - (Math.sqrt(Math.pow(b, 2) - 4 * a * c))) / (2 * a);
                                root.getChildren().remove(quadraticText);
                                quadraticText.setText("Quadratic Formula \nA: " + a + "\nB: " + b + "\nC: " + c + "\nX1: " + root1 + "\nX2: " + root2);
                                root.getChildren().add(quadraticText);
                            }else{
                                Text error = new Text("Solutions are non-real");
                                error.setFont(new Font(20));
                                error.setLayoutX(200);
                                error.setLayoutY(120);
                                error.setFill(Color.RED);
                                root.getChildren().add(error);
                            }
                            Button reset = new Button("Reset");
                            reset.setLayoutX(400);
                            reset.setLayoutY(70);
                            reset.setOnAction(event -> {
                                a = 0;
                                b = 0;
                                c = 0;
                                root1 = 0;
                                root2 = 0;
                                calculated = false;
                                itemAdded = false;
                            });
                            itemAdded = true;
                            root.getChildren().add(reset);
                        }

                    }
                    if (legSelector.getValue().equals(texts.get(3))) {
                        current = 3;
                        if(previous!=current){
                            previous = 3;
                            itemAdded = false;
                        }
                        if(!itemAdded) {
                            root.getChildren().remove(pythagoreanText);
                            root.getChildren().remove(quadraticText);
                            root.getChildren().remove(distanceFormulaText);
                            root.getChildren().remove(volumeConeText);
                            root.getChildren().remove(setItem);
                            root.getChildren().remove(inputField);
                            inputField.setText("");
                            volumeConeText.setText("Volume Cone \nPi: "+Math.PI+"\nr: "+r+"\nH: "+h+"\nVolume: "+volume);
                            root.getChildren().add(volumeConeText);
                        }
                    }
                    if (legSelector.getValue().equals(texts.get(4))) {
                        current = 4;
                        if(previous!=current){
                            previous = 4;
                            itemAdded = false;
                        }
                        if(!itemAdded) {
                            root.getChildren().remove(pythagoreanText);
                            root.getChildren().remove(distanceFormulaText);
                            root.getChildren().remove(volumeConeText);
                            root.getChildren().remove(quadraticText);
                            root.getChildren().remove(setItem);
                            root.getChildren().remove(inputField);
                            inputField.setText("");
                            distanceFormulaText.setText("Distance Formula \nX1: "+x1+"\nX2: "+x2+"\nY1: "+"\nY2: "+y2+"\nDistance: "+distance);
                            root.getChildren().add(distanceFormulaText);
                        }
                        if(!itemAdded && x1==0 && x2==0 && y1==0 && y2==0){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(220);
                            inputField.setPromptText("x1");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(220);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    x1 = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(!itemAdded && x2==0 && y1==0 && y2==0){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(260);
                            inputField.setPromptText("x2");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(300);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    x2 = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(!itemAdded && y1==0 && y2==0){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(300);
                            inputField.setPromptText("y1");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(300);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    y1 = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }else if(!itemAdded && y2==0){
                            inputField.setLayoutX(250);
                            inputField.setLayoutY(340);
                            inputField.setPromptText("y2");
                            root.getChildren().add(inputField);

                            setItem.setLayoutX(420);
                            setItem.setLayoutY(340);
                            setItem.setOnAction(event->{
                                if(inputField.getText()!=null) {
                                    y2 = Double.parseDouble(inputField.getText());
                                }
                                itemAdded = false;
                            });
                            root.getChildren().add(setItem);
                            itemAdded = true;
                        }
                    }
                }
            }
        }.start();
    }
}
