import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;

public class Runner extends Application {
    private ArrayList<String> idList = new ArrayList<>();
    private HashMap<KeyCode, Boolean> pressedKeys = new HashMap<>();
    private ArrayList<KeyCode> presetKeys = new ArrayList<>();
    private ArrayList<String> inventory = new ArrayList<>();
    private Pane root;
    private Pane player;
    private ImageView NPC1;
    private ImageView NPC2;
    private ImageView object1;
    private int cloutCount;
    private int currentStep;
    private long diologTimer;
    private boolean keyHeld;
    private boolean gameStart = false;
    private boolean idle = false;
    private boolean currentDiolog = false;
    private ArrayList<ImageView> idlePose;

    public Runner() {
        currentStep = 0;

        root = new Pane();
        player = new Pane();
        player.setLayoutY(100);
        player.setLayoutX(100);

        presetKeys.add(KeyCode.W);
        presetKeys.add(KeyCode.A);
        presetKeys.add(KeyCode.S);
        presetKeys.add(KeyCode.D);

        pressedKeys.put(KeyCode.W, false);
        pressedKeys.put(KeyCode.A, false);
        pressedKeys.put(KeyCode.S, false);
        pressedKeys.put(KeyCode.D, false);

        idlePose = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ImageView iv = new ImageView("resources/Main_Character/idle/000" + i + ".png");
            iv.setFitHeight(75);
            iv.setFitWidth(50);
            idlePose.add(iv);
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void timer(){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                for(int i = 0; i<presetKeys.size(); i++){
                    if(pressedKeys.get(presetKeys.get(i))) {
                        if (presetKeys.get(i) == KeyCode.W) {
                            moveUp();
                        }
                        if (presetKeys.get(i) == KeyCode.A) {
                            moveLeft();
                        }
                        if (presetKeys.get(i) == KeyCode.S) {
                            moveDown();
                        }
                        if (presetKeys.get(i) == KeyCode.D) {
                            moveRight();
                        }
                    }
                }
                if(currentDiolog){
                    diologTimer++;
                    if(diologTimer==350){
                        for(int i = 0; i<root.getChildren().size(); i++){
                            if(root.getChildren().get(i).getId()!=null){
                                if(root.getChildren().get(i).getId().contains("dialog")){
                                    root.getChildren().remove(i);
                                    i--;
                                }
                            }
                        }
                        currentDiolog = false;
                        diologTimer = 0;
                    }
                }
            }
        }.start();
    }
    public void start(Stage primaryStage) throws Exception {
        root.getChildren().add(titleScreen());
        ImageView iv = new ImageView("resources/Main_Character/idle/0000.png");
        iv.setFitWidth(125);
        iv.setFitHeight(200);
        player.getChildren().add(iv);
        player.setMaxSize(125,200);

        NPC1 = new ImageView("resources/Ninja/Idle/0000.png");
        NPC1.setFitHeight(200);
        NPC1.setFitWidth(125);
        NPC1.setLayoutY(100);
        NPC1.setLayoutX(500);
        NPC1.setId("objN");

        NPC2 = new ImageView("resources/Pirate/Idle/0000.png");
        NPC2.setFitHeight(200);
        NPC2.setFitWidth(125);
        NPC2.setLayoutY(400);
        NPC2.setLayoutX(200);
        NPC2.setId("objP");

        object1 = new ImageView("resources/Objects/belt.png");
        object1.setFitHeight(90);
        object1.setFitWidth(150);
        object1.setLayoutY(50);
        object1.setLayoutX(100);
        object1.setId("objB");
        timer();

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        primaryStage.setTitle("Gucci Game");
        primaryStage.getIcons().add(new Image("resources/Objects/Gucci.png"));
    }
    private Pane titleScreen() {
        Pane miniPane = new Pane();
        miniPane.setId("Title");

        ImageView iv6 = new ImageView("resources/Backgrounds/patternthing.png");
        iv6.setLayoutY(0);
        iv6.setLayoutX(0);
        iv6.setFitHeight(600);
        iv6.setFitWidth(800);
        miniPane.getChildren().add(iv6);

        ImageView iv1 = new ImageView("resources/Objects/belt.png");
        iv1.setLayoutY(70);
        iv1.setLayoutX(75);
        iv1.setFitHeight(200);
        iv1.setFitWidth(250);
        miniPane.getChildren().add(iv1);

        ImageView iv2 = new ImageView("resources/Objects/backpack.png");
        iv2.setLayoutY(50);
        iv2.setLayoutX(550);
        iv2.setFitHeight(200);
        iv2.setFitWidth(200);
        miniPane.getChildren().add(iv2);

        ImageView iv3 = new ImageView("resources/Objects/googles.png");
        iv3.setLayoutY(225);
        iv3.setLayoutX(175);
        iv3.setFitHeight(225);
        iv3.setFitWidth(300);
        miniPane.getChildren().add(iv3);

        ImageView iv4 = new ImageView("resources/Objects/hoodie.png");
        iv4.setLayoutY(250);
        iv4.setLayoutX(550);
        iv4.setFitHeight(200);
        iv4.setFitWidth(200);
        miniPane.getChildren().add(iv4);

        ImageView iv5 = new ImageView("resources/Objects/Rolex.png");
        iv5.setLayoutY(400);
        iv5.setLayoutX(300);
        iv5.setFitHeight(200);
        iv5.setFitWidth(125);
        miniPane.getChildren().add(iv5);

        Rectangle r1 = new Rectangle(180 + 75, 15, 250, 50);
        r1.setFill(Color.RED);
        r1.setStroke(Color.RED);
        r1.setStrokeWidth(1);
        miniPane.getChildren().add(r1);

        Text t1 = new Text("Gucci Game");
        t1.setLayoutY(50);
        t1.setLayoutX(225 + 60);
        t1.setFont(new Font(32));
        t1.setStroke(Color.WHITE);
        t1.setFill(Color.WHITE);
        miniPane.getChildren().add(t1);


        Button b1 = new Button("Start Quest");
        b1.setFont(new Font("Comic Sans MS", 16));
        b1.setLayoutX(300);
        b1.setLayoutY(200);
        b1.setOnAction(event -> {
            initGame();
            root.getChildren().remove(miniPane);
        });
        miniPane.getChildren().add(b1);

        /*cssify button
        remove background
        remove boarders
        */
        Button b2 = new Button();
        b2.setLayoutX(0);
        b2.setLayoutY(550);
        ImageView resource1 = new ImageView("resources/Objects/cog.png");
        resource1.setFitWidth(60);
        resource1.setFitHeight(50);
        b2.setGraphic(resource1);
        idList.add("OptionButton");
        b2.setId(idList.get(idList.size() - 1));
        b2.getStylesheets().add("Stylesheet.css");
        b2.setOnAction(event -> {
            for (int i = 0; i < miniPane.getChildren().size(); i++) {
                Node n = miniPane.getChildren().get(i);
                n.setEffect(new GaussianBlur());
            }
            drawOptionsMenu();
        });
        miniPane.getChildren().add(b2);

        root.setFocusTraversable(true);
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                for (int i = 0; i < miniPane.getChildren().size(); i++) {
                    Node n = miniPane.getChildren().get(i);
                    n.setEffect(new GaussianBlur());
                }
                drawOptionsMenu();
            }
        });

        return miniPane;
    }
    private void initGame() {
        root.getChildren().clear();
        root.requestFocus();
        root.getChildren().add(player);
        root.getChildren().add(NPC1);
        root.getChildren().add(NPC2);
        root.getChildren().add(object1);

        root.setOnKeyPressed(event -> {
            for(int i = 0; i<presetKeys.size(); i++){
                if(presetKeys.get(i)==event.getCode()){
                    pressedKeys.replace(presetKeys.get(i), true);
                }
            }
            if(event.getCode()==KeyCode.E){

                checkPosition(player.getLayoutX(),player.getLayoutY());
            }
        });
        root.setOnKeyReleased(event -> {
            Set<KeyCode> keys = pressedKeys.keySet();
            for(int k = 0; k<pressedKeys.size(); k++) {
                if(pressedKeys.values().contains(true)) {
                    for (int i = 0; i < presetKeys.size(); i++) {
                        for (int f = 0; f < keys.size(); f++) {
                            if (keys.contains(presetKeys.get(i))) {
                                pressedKeys.replace(presetKeys.get(i), false);
                            }
                        }
                    }
                }
            }
        });

    }
    private void moveUp(){
        player.setLayoutY(player.getLayoutY()-1);
    }
    private void moveLeft(){
        player.setLayoutX(player.getLayoutX()-1);
    }
    private void moveRight(){
        player.setLayoutX(player.getLayoutX()+1);
    }
    private void moveDown(){
        player.setLayoutY(player.getLayoutY()+1);
    }
    private void drawOptionsMenu() {
        Pane miniPane = new Pane();
        miniPane.setLayoutX(200);
        miniPane.setLayoutY(50);

        Rectangle r = new Rectangle(275, 400);
        r.setStrokeWidth(3);
        r.setStroke(Color.BLACK);
        r.setFill(Color.WHITE);
        miniPane.getChildren().add(r);

        Text t1 = new Text("Options");
        t1.setFont(new Font(32));
        t1.setLayoutY(47);
        t1.setLayoutX(75);
        miniPane.getChildren().add(t1);

        CheckBox cb1 = new CheckBox();
        cb1.fire();
        Label l1 = new Label("Curse Filter");
        l1.setLabelFor(cb1);
        l1.setLayoutX(15);
        l1.setLayoutY(60);
        cb1.setLayoutY(60);
        cb1.setLayoutX(100);
        miniPane.getChildren().add(cb1);
        miniPane.getChildren().add(l1);

        /* cssify button
        remove boarders
        remove background */
        Button exit = new Button();
        ImageView resource = new ImageView("resources/Objects/redX.png");
        resource.setFitWidth(25);
        resource.setFitHeight(25);
        exit.setGraphic(resource);
        exit.setLayoutX(225);
        exit.setLayoutY(5);
        exit.setOnAction(event -> {
            root.getChildren().clear();
            root.getChildren().remove(miniPane);
            root.getChildren().add(titleScreen());
        });
        idList.add("ExitButton");
        exit.setId(idList.get(idList.size() - 1));
        exit.getStylesheets().add("Stylesheet.css");
        miniPane.getChildren().add(exit);

        //figure out how to make this work
        root.setFocusTraversable(true);
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                root.getChildren().clear();
                root.getChildren().remove(miniPane);
                root.getChildren().add(titleScreen());
            }
        });

        root.getChildren().add(miniPane);
    }
    private void checkPosition(double x, double y){
        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null){
                if(root.getChildren().get(i).getId().contains("obj")){
                    Rectangle r1 = new Rectangle(root.getChildren().get(i).getLayoutX(),root.getChildren().get(i).getLayoutY(), 125,200);
                    root.getChildren().add(r1);
                    //System.out.println(root.getChildren().get(i).getId().contains("N"));
                    System.out.println(r1.intersects(x,y,1,1));
//                    System.out.println(r1.contains(115,515));
                    if(r1.contains(x,y)){
                        System.out.println("test");
                        if(root.getChildren().get(i).getId().contains("N")){
                            initDiolauge(currentStep,"Ninja");
                        }
                    }
                }
            }
        }
        if(NPC1.contains(x,y)){
            initDiolauge(currentStep, "Ninja");
        }
        if(NPC2.contains(x,y)){
            initDiolauge(currentStep, "Pirate");
        }
        if(object1.contains(x,y)){
            collectItem("Belt");
        }
    }
    private void initDiolauge(int step, String character){
        currentDiolog = true;
        Rectangle r = new Rectangle(0,500,800,100);
        r.setId("dialog");
        r.setStrokeWidth(5);
        r.setStroke(Color.BLACK);
        r.setFill(Color.WHITE);
        root.getChildren().add(r);
        if(step==-1&&character.equals("item")){
            int selector = (int) (Math.random()*(3));
            Text t = new Text();
            t.setId("dialog");
            if(selector==0){
                t.setText("Player:\nNice we copped that new grucci");
            }
            if(selector==1){
                t.setText("Player:\nHellllllo epic department");
            }
            if(selector==2){
                t.setText("Player:\nWe got that new supreme drop");
            }
            t.setFont(new Font(28));
            t.setLayoutY(535);
            t.setLayoutX(20);
            t.setWrappingWidth(760);
            root.getChildren().add(t);
        }else if(step==0&&character.equals("Ninja")){
            Text t = new Text("Ninja:\nBruh do you got that grucci belt?");
            t.setId("dialog");
            t.setFont(new Font(28));
            t.setLayoutY(535);
            t.setLayoutX(20);
            t.setWrappingWidth(760);
            root.getChildren().add(t);
        }else if(step==0&&character.equals("Pirate")){
            Text t = new Text("Pirate:\nAyo yall got my gucci belt");
            t.setId("dialog");
            t.setFont(new Font(28));
            t.setLayoutY(535);
            t.setLayoutX(20);
            t.setWrappingWidth(760);
            root.getChildren().add(t);
        }
    }
    private void collectItem(String item){
        inventory.add(item);
        initDiolauge(-1,"item");
    }
}
