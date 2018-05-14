import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.*;

public class Runner extends Application {
    private HashMap<KeyCode, Boolean> pressedKeys = new HashMap<>();
    private ArrayList<String> idList = new ArrayList<>();
    private ArrayList<KeyCode> presetKeys = new ArrayList<>();
    private ArrayList<String> inventory = new ArrayList<>();
    private ArrayList<Image> idlePosePlayer = new ArrayList<>();
    private ArrayList<Image> movingPosePlayer = new ArrayList<>();
    private ArrayList<Image> runningPosePlayer = new ArrayList<>();
    private ArrayList<Image> idlePosenpc1 = new ArrayList<>();
    private ArrayList<Image> idlePosenpc2 = new ArrayList<>();
    private Pane root;
    private ImageView background;
    private ImageView player;
    private ImageView npc1;
    private ImageView npc2;
    private ImageView object1;
    private ImageView object2;
    private Rectangle playerHitbox;
    private Rectangle npc1Hitbox;
    private Rectangle npc2Hitbox;
    private Rectangle object1Hitbox;
    private Rectangle object2Hitbox;
    private Point2D npc1XY;
    private Point2D npc2XY;
    private Point2D object1XY;
    private Point2D object2XY;
    private int cloutCount;
    private int currentDialog;
    private int dialogCounter = 0;
    private int currentIdleImagePlayer = 0;
    private int currentMovingImage = 0;
    private int idleCountNPC;
    private int currentScene;
    private double movementIncrement;
    private boolean moving = false;
    private boolean running = false;
    private boolean activeDialog;

    public Runner() {
        currentDialog = 0;

        npc1 = new ImageView("resources/Knight/idle/0000.png");
        npc1.setLayoutX(500);
        npc1.setLayoutY(100);
        npc1.setFitWidth(125);
        npc1.setFitHeight(200);
        npc1Hitbox = new Rectangle(npc1.getLayoutX()+25,npc1.getLayoutY()+45,npc1.getFitWidth()-30,npc1.getFitHeight()-55);
        npc1Hitbox.setStroke(Color.BLACK);
        npc1Hitbox.setStrokeWidth(3);
        npc1Hitbox.setFill(Color.TRANSPARENT);
        npc1Hitbox.setId("objK");
        npc1XY = new Point2D(npc1.getLayoutX()+25,npc1.getLayoutY()+45);

        npc2 = new ImageView("resources/Ninja/Idle/0000.png");
        npc2.setLayoutX(450);
        npc2.setLayoutY(300);
        npc2.setFitWidth(160);
        npc2.setFitHeight(200);
        npc2Hitbox = new Rectangle(npc2.getLayoutX()+35,npc2.getLayoutY()+45,npc2.getFitWidth()-80,npc2.getFitHeight()-65);
        npc2Hitbox.setStroke(Color.BLACK);
        npc2Hitbox.setStrokeWidth(3);
        npc2Hitbox.setFill(Color.TRANSPARENT);
        npc2Hitbox.setId("objN");
        npc2XY = new Point2D(npc2.getLayoutX()+35,npc2.getLayoutY()+45);

        object1 = new ImageView("resources/Objects/belt.png");
        object1.setFitWidth(150);
        object1.setFitHeight(60);
        object1.setLayoutX(100);
        object1.setLayoutY(400);
        object1Hitbox = new Rectangle(object1.getLayoutX(),object1.getLayoutY()+20,object1.getFitWidth(),object1.getFitHeight()-40);
        object1Hitbox.setStroke(Color.BLACK);
        object1Hitbox.setStrokeWidth(3);
        object1Hitbox.setFill(Color.TRANSPARENT);
        object1Hitbox.setId("objInventoryBelt");
        object1XY = new Point2D(object1.getLayoutX(),object1.getLayoutY()+20);

        object2 = new ImageView("resources/Objects/backpack.png");
        object2.setLayoutX(300);
        object2.setLayoutY(200);
        object2.setFitWidth(70);
        object2.setFitHeight(70);
        object2Hitbox = new Rectangle(object2.getLayoutX(),object2.getLayoutY(),object2.getFitWidth(),object2.getFitHeight());
        object2Hitbox.setStroke(Color.BLACK);
        object2Hitbox.setStrokeWidth(3);
        object2Hitbox.setFill(Color.TRANSPARENT);
        object2Hitbox.setId("objInventoryBackpack");
        object2XY = new Point2D(object2.getLayoutX(),object2.getLayoutY());

        root = new Pane();

        presetKeys.add(KeyCode.W);
        presetKeys.add(KeyCode.A);
        presetKeys.add(KeyCode.S);
        presetKeys.add(KeyCode.D);
        pressedKeys.put(KeyCode.W, false);
        pressedKeys.put(KeyCode.A, false);
        pressedKeys.put(KeyCode.S, false);
        pressedKeys.put(KeyCode.D, false);

        for (int i = 0; i < 8; i++) {
            Image iv = new Image("resources/Main_Character/idle/000" + i + ".png");
            idlePosePlayer.add(iv);
            Image iv2 = new Image("resources/Main_Character/walk/000"+i+".png");
            movingPosePlayer.add(iv2);
            Image iv3 = new Image("resources/Knight/idle/000"+i+".png");
            idlePosenpc1.add(iv3);
            Image iv4 = new Image("resources/Ninja/Idle/000"+i+".png");
            idlePosenpc2.add(iv4);
        }

        ImageView background1 = new ImageView("resources/Backgrounds/beach.jpg");
        background1.setFitWidth(800);
        background1.setFitHeight(600);
        background1.setLayoutY(0);
        background1.setLayoutX(0);
        ImageView background2 = new ImageView("resources/Backgrounds/forest.jpg");
        background2.setFitWidth(800);
        background2.setFitHeight(600);
        background2.setLayoutY(0);
        background2.setLayoutX(0);
        ImageView background3 = new ImageView("resources/Backgrounds/grass.jpg");
        background3.setFitWidth(800);
        background3.setFitHeight(600);
        background3.setLayoutY(0);
        background3.setLayoutX(0);
        ArrayList<ImageView> backgrounds = new ArrayList<>();
        backgrounds.add(background1);
        backgrounds.add(background2);
        backgrounds.add(background3);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        if(screenWidth >1500){
            movementIncrement = 4;
        }else{
            movementIncrement = .5;
        }

        background = backgrounds.get((int)(Math.random()* backgrounds.size()));
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void timer(){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                for(int k = 0; k<pressedKeys.size(); k++) {
                    moving = pressedKeys.values().contains(true);
                }
                if(moving){
                    if((currentMovingImage/5)==idlePosePlayer.size()){
                        currentMovingImage = 0;
                    }
                    if(currentMovingImage%5==0){
                        player.setImage(movingPosePlayer.get(currentMovingImage/5));
                    }
                    currentMovingImage++;
                }else{
                    if((currentIdleImagePlayer/10)==idlePosePlayer.size()){
                        currentIdleImagePlayer = 0;
                    }
                    if(currentIdleImagePlayer%10==0) {
                        player.setImage(idlePosePlayer.get(currentIdleImagePlayer / 10));
                    }
                    currentIdleImagePlayer++;
                }
                for(int i = 0; i<presetKeys.size(); i++){
                    if(pressedKeys.get(presetKeys.get(i))){
                        if(presetKeys.get(i)==KeyCode.W){
                            moveUp();
                        }
                        if(presetKeys.get(i)==KeyCode.A){
                            moveLeft();
                        }
                        if(presetKeys.get(i)==KeyCode.S){
                            moveDown();
                        }
                        if(presetKeys.get(i)==KeyCode.D){
                            moveRight();
                        }
                    }
                }
                if(activeDialog){
                    dialogCounter++;
                    if(dialogCounter==270){
                        for(int i = 0; i<root.getChildren().size(); i++){
                            if(root.getChildren().get(i).getId()!=null){
                                if(root.getChildren().get(i).getId().contains("dialog")){
                                    root.getChildren().remove(i);
                                    i--;
                                }
                            }
                        }
                        dialogCounter = 0;
                        activeDialog = false;
                    }
                }
                if((idleCountNPC/10)==idlePosenpc1.size()){
                    idleCountNPC = 0;
                }
                if(idleCountNPC%10==0){
                    npc1.setImage(idlePosenpc1.get(idleCountNPC/10));
                    npc2.setImage(idlePosenpc2.get(idleCountNPC/10));
                }
                idleCountNPC++;
            }
        }.start();
    }
    public void start(Stage primaryStage) throws Exception {
        root.getChildren().add(titleScreen());
        player = new ImageView("resources/Main_Character/idle/0000.png");
        player.setLayoutY(100);
        player.setLayoutX(100);
        player.setFitHeight(200);
        player.setFitWidth(125);
        player.setSmooth(true);
        player.setTranslateZ(player.getBoundsInLocal().getWidth()/2);
        player.setRotationAxis(Rotate.Y_AXIS);
        player.setRotate(180);
        player.setId("player");
        playerHitbox = new Rectangle(player.getLayoutX()+20,player.getLayoutY()+60,player.getFitWidth()-40,player.getFitHeight()-80);
        playerHitbox.setStroke(Color.BLACK);
        playerHitbox.setStrokeWidth(3);
        playerHitbox.setFill(Color.TRANSPARENT);
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
    @SuppressWarnings("Duplicates")
    private void initGame() {
        currentScene = 1;
        root.getChildren().clear();
        root.requestFocus();
        root.getChildren().add(background);
        root.getChildren().add(player);
        root.getChildren().add(playerHitbox);
        root.getChildren().add(npc1);
        root.getChildren().add(npc1Hitbox);
        root.getChildren().add(npc2);
        root.getChildren().add(npc2Hitbox);
        root.getChildren().add(object1);
        root.getChildren().add(object1Hitbox);
        root.getChildren().add(object2);
        root.getChildren().add(object2Hitbox);
        root.setOnKeyPressed(event -> {
            for(int i = 0; i<presetKeys.size(); i++){
                if(presetKeys.get(i)==event.getCode()){
                    pressedKeys.replace(presetKeys.get(i), true);
                }
            }
            if(event.getCode()==KeyCode.E){
                checkBounds(player.getLayoutX()+20, player.getLayoutY()+60, playerHitbox.getWidth(), playerHitbox.getHeight());
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                for (int i = 0; i < root.getChildren().size(); i++) {
                    Node n = root.getChildren().get(i);
                    n.setEffect(new GaussianBlur());
                }
                drawOptionsMenu();
            }
        });
        root.setOnKeyReleased(event -> {
            Set<KeyCode> keys = pressedKeys.keySet();
            for(int k = 0; k<pressedKeys.size(); k++) {
                if(pressedKeys.values().contains(true)) {
                    for (int f = 0; f < keys.size(); f++) {
                        if (keys.contains(event.getCode())) {
                            pressedKeys.replace(event.getCode(), false);
                        }
                    }
                }
            }
        });

    }
    private void checkBounds(double x1, double y1, double width1, double height1){
        int x = (int)x1;
        int y = (int)y1;
        int width = (int)width1;
        int height = (int)height1;

        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null){
                if(root.getChildren().get(i).getId().contains("obj")){
                    Rectangle r = (Rectangle) root.getChildren().get(i);

                    switch (root.getChildren().get(i).getId()) {
                        case "objK":
                            if (((x > npc1XY.getX() && x < npc1XY.getX() + r.getWidth()) || (x + width > npc1XY.getX() && x + width < npc1XY.getX() + r.getWidth())) &&
                                    ((y > npc1XY.getY() && y < npc1XY.getY() + r.getHeight()) || (y + height > npc1XY.getY() && y + height < npc1XY.getY() + r.getHeight()))) {
                                System.out.println("test");
                                initDialog("Knight");
                            }
                            break;
                        case "objN":
                            if (((x > npc2XY.getX() && x < npc2XY.getX() + r.getWidth()) || (x + width > npc2XY.getX() && x + width < npc2XY.getX() + r.getWidth())) &&
                                    ((y > npc2XY.getY() && y < npc2XY.getY() + r.getHeight()) || (y + height > npc2XY.getY() && y + height < npc2XY.getY() + r.getHeight()))) {
                                System.out.println("test");
                                initDialog("Ninja");
                            }
                            break;
                        case "objInventoryBelt":
                            if (((x > object1XY.getX() && x < object1XY.getX() + r.getWidth()) || (x + width > object1XY.getX() && x + width < object1XY.getX() + r.getWidth())) &&
                                    ((y > object1XY.getY() && y < object1XY.getY() + r.getHeight()) || (y + height > object1XY.getY() && y + height < object1XY.getY() + r.getHeight()))) {
                                System.out.println("test");
                                initDialog("ObjectBelt");
                            }
                            break;
                        case "objInventoryBackpack":
                            if (((x > object2XY.getX() && x < object2XY.getX() + r.getWidth()) || (x + width > object2XY.getX() && x + width < object2XY.getX() + r.getWidth())) &&
                                    ((y > object2XY.getY() && y < object2XY.getY() + r.getHeight()) || (y + height > object2XY.getY() && y + height < object2XY.getY() + r.getHeight()))) {
                                System.out.println("test");
                                initDialog("ObjectBackpack");
                            }
                            break;
                    }

                }
            }
        }
    }
    private void initDialog(String player){
        activeDialog = true;
        Rectangle r = new Rectangle(0,500,800,100);
        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(3);
        r.setId("dialog");
        root.getChildren().add(r);
        if(currentDialog==0){
            Text t = new Text("Ninja:\nHey could you get me my gucci belt");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            currentDialog++;
        }else if(currentDialog==1){
            Text t = new Text("Player:\nAyy we copped that new grucci");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            currentDialog++;
        }else if(currentDialog==2){
            Text t = new Text("Pirate:\nThanks Mane");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            currentDialog++;
        }
    }
    private void moveUp(){
        player.setLayoutY(player.getLayoutY()-movementIncrement);
        playerHitbox.setLayoutY(playerHitbox.getLayoutY()-movementIncrement);
    }
    private void moveLeft(){
        player.setLayoutX(player.getLayoutX()-4);
        playerHitbox.setLayoutX(playerHitbox.getLayoutX()-movementIncrement);
        player.setRotate(0);
    }
    private void moveDown(){
        player.setLayoutY(player.getLayoutY()+movementIncrement);
        playerHitbox.setLayoutY(playerHitbox.getLayoutY()+movementIncrement);
    }
    private void moveRight(){
        player.setLayoutX(player.getLayoutX()+movementIncrement);
        playerHitbox.setLayoutX(playerHitbox.getLayoutX()+movementIncrement);
        player.setRotate(180);
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
        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                root.getChildren().remove(miniPane);
                if(currentScene==0) {
                    root.getChildren().add(titleScreen());
                }else{
                    for(int i = 0; i<root.getChildren().size(); i++){
                        Node n = root.getChildren().get(i);
                        n.setEffect(null);
                    }
                    initGame();
                }
            }
        });

        root.getChildren().add(miniPane);
    }
}
