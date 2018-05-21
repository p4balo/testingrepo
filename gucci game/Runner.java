import com.sun.istack.internal.NotNull;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Runner extends Application {
    private HashMap<KeyCode, Boolean> pressedKeys = new HashMap<>();
    private HashMap<Quest, Boolean> currentActiveQuest = new HashMap<>();
    private ArrayList<KeyCode> presetKeys = new ArrayList<>();
    private ArrayList<Image> idlePosePlayer = new ArrayList<>();
    private ArrayList<Image> movingPosePlayer = new ArrayList<>();
    private ArrayList<Image> runningPosePlayer = new ArrayList<>();
    private ArrayList<Image> idlePoseknight = new ArrayList<>();
    private ArrayList<Image> idlePoseninja = new ArrayList<>();
    private ArrayList<String> leavingCharacters = new ArrayList<>();
    private ArrayList<String> inventoryNames = new ArrayList<>();
    private ObservableList<InventoryData> inventory = FXCollections.observableArrayList();
    private Pane root;
    private Quest currentQuest = new Quest();
    private ImageView background;
    private ImageView player;
    private ImageView knight;
    private ImageView ninja;
    private ImageView belt;
    private ImageView backpack;
    private Rectangle playerHitbox;
    private Rectangle knightHitbox;
    private Rectangle ninjaHitbox;
    private Rectangle beltHitbox;
    private Rectangle backpackHitbox;
    private Point2D knightXY;
    private Point2D ninjaXY;
    private Point2D beltXY;
    private Point2D backpackXY;
    private int cloutCount;
    private int currentDialog;
    private int dialogCounter = 0;
    private int currentIdleImagePlayer = 0;
    private int currentMovingImage = 0;
    private int currentMovingDecrement = 5;
    private int idleCountNPC;
    private int currentScene;
    private double moneyCount;
    private double movementIncrement;
    private boolean moving = false;
    private boolean running = false;
    private boolean activeDialog;
    private boolean containsBackpack;
    private boolean openInventory;
    private boolean activeQuest;
    private boolean buisnessOwner;
    private boolean landLord;
    private boolean updated;
    private String questFor;
    private String cssPath = "Stylesheet.css";

    //make it so you cant get money if you don't have wallet, but don't have wallet images rn
    private boolean containsWallet;

    public Runner() {
        currentDialog = -2;

        knight = new ImageView("resources/Knight/idle/0000.png");
        knight.setLayoutX(500);
        knight.setLayoutY(100);
        knight.setFitWidth(125);
        knight.setFitHeight(200);
        knight.setTranslateZ(knight.getBoundsInLocal().getWidth()/2);
        knight.setRotationAxis(Rotate.Y_AXIS);
        knight.setRotate(0);
        knightHitbox = new Rectangle(knight.getLayoutX()+25,knight.getLayoutY()+45,knight.getFitWidth()-30,knight.getFitHeight()-55);
        knightHitbox.setStroke(Color.TRANSPARENT);
        knightHitbox.setStrokeWidth(3);
        knightHitbox.setFill(Color.TRANSPARENT);
        knightHitbox.setId("objK");
        knightXY = new Point2D(knight.getLayoutX()+25,knight.getLayoutY()+45);

        ninja = new ImageView("resources/Ninja/Idle/0000.png");
        ninja.setLayoutX(450);
        ninja.setLayoutY(300);
        ninja.setFitWidth(160);
        ninja.setFitHeight(200);
        ninja.setTranslateZ(ninja.getBoundsInLocal().getWidth()/2);
        ninja.setRotationAxis(Rotate.Y_AXIS);
        ninja.setRotate(0);
        ninjaHitbox = new Rectangle(ninja.getLayoutX()+35,ninja.getLayoutY()+45,ninja.getFitWidth()-80,ninja.getFitHeight()-65);
        ninjaHitbox.setStroke(Color.TRANSPARENT);
        ninjaHitbox.setStrokeWidth(3);
        ninjaHitbox.setFill(Color.TRANSPARENT);
        ninjaHitbox.setId("objN");
        ninjaXY = new Point2D(ninja.getLayoutX()+35,ninja.getLayoutY()+45);

        belt = new ImageView("resources/Objects/belt.png");
        belt.setFitWidth(150);
        belt.setFitHeight(60);
        belt.setLayoutX(100);
        belt.setLayoutY(400);
        belt.setId("obBelt");
        beltHitbox = new Rectangle(belt.getLayoutX(),belt.getLayoutY()+20,belt.getFitWidth(),belt.getFitHeight()-40);
        beltHitbox.setStroke(Color.TRANSPARENT);
        beltHitbox.setStrokeWidth(3);
        beltHitbox.setFill(Color.TRANSPARENT);
        beltHitbox.setId("objInventoryBelt");
        beltXY = new Point2D(belt.getLayoutX(),belt.getLayoutY()+20);

        backpack = new ImageView("resources/Objects/backpack.png");
        backpack.setLayoutX(300);
        backpack.setLayoutY(200);
        backpack.setFitWidth(70);
        backpack.setFitHeight(70);
        backpack.setId("obBackpack");
        backpackHitbox = new Rectangle(backpack.getLayoutX(),backpack.getLayoutY(),backpack.getFitWidth(),backpack.getFitHeight());
        backpackHitbox.setStroke(Color.TRANSPARENT);
        backpackHitbox.setStrokeWidth(3);
        backpackHitbox.setFill(Color.TRANSPARENT);
        backpackHitbox.setId("objInventoryBackpack");
        backpackXY = new Point2D(backpack.getLayoutX(),backpack.getLayoutY());

        root = new Pane();

        presetKeys.add(KeyCode.W);
        presetKeys.add(KeyCode.A);
        presetKeys.add(KeyCode.S);
        presetKeys.add(KeyCode.D);
        presetKeys.add(KeyCode.I);
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
            idlePoseknight.add(iv3);
            Image iv4 = new Image("resources/Ninja/Idle/000"+i+".png");
            idlePoseninja.add(iv4);
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
            currentMovingDecrement = 15;
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
                moving = pressedKeys.values().contains(true);
                if(moving){
                    if((currentMovingImage/currentMovingDecrement)==idlePosePlayer.size()){
                        currentMovingImage = 0;
                    }
                    if(currentMovingImage%currentMovingDecrement==0){
                        player.setImage(movingPosePlayer.get(currentMovingImage/currentMovingDecrement));
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
                    if(presetKeys.get(i)!=KeyCode.I) {
                        if (pressedKeys.get(presetKeys.get(i))) {
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
                        if(currentDialog==-1){
                            initDialog(null);
                        }
                    }
                }
                if((idleCountNPC/10)==idlePoseknight.size()){
                    idleCountNPC = 0;
                }
                if(idleCountNPC%10==0){
                    knight.setImage(idlePoseknight.get(idleCountNPC/10));
                    ninja.setImage(idlePoseninja.get(idleCountNPC/10));
                }
                idleCountNPC++;
                if(currentActiveQuest.size()>0){
                    List<Quest> list = new ArrayList<>(currentActiveQuest.keySet());
                    Quest q = list.get(0);
                    if(q.questComplete()){
                        currentActiveQuest.remove(currentQuest);
                        removeQuest();
                        activeQuest = false;
                    }
                    drawQuest(q);
                    checkReqs(q);
                }
                if(leavingCharacters.size()>0){
                    for(int i = 0; i<leavingCharacters.size(); i++){
                        if(leavingCharacters.get(i).equals("Ninja")){
                            if(ninja.getLayoutX()<850) {
                                moveOffScreen("Ninja");
                            }
                        }else if(leavingCharacters.get(i).equals("Knight")){
                            moveOffScreen("Knight");
                        }
                    }
                }
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
    @SuppressWarnings("Duplicates")
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
        b1.setId("StartGame");
        b1.setFont(new Font("Comic Sans MS", 16));
        b1.getStylesheets().add(cssPath);
        b1.setLayoutX(300);
        b1.setLayoutY(200);
        b1.setOnAction(event -> {
            initGame();
            root.getChildren().remove(miniPane);
        });
        miniPane.getChildren().add(b1);

        Button b2 = new Button();
        b2.setLayoutX(0);
        b2.setLayoutY(550);
        ImageView resource1 = new ImageView("resources/Objects/cog.png");
        resource1.setFitWidth(60);
        resource1.setFitHeight(50);
        b2.setGraphic(resource1);
        b2.setId("Options");
        b2.getStylesheets().add(cssPath);
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
        root.getChildren().add(knight);
        root.getChildren().add(knightHitbox);
        root.getChildren().add(ninja);
        root.getChildren().add(ninjaHitbox);
        if(currentDialog<3) {
            root.getChildren().add(belt);
            root.getChildren().add(beltHitbox);
        }
        if(currentDialog<2) {
            root.getChildren().add(backpack);
            root.getChildren().add(backpackHitbox);
        }
        if(currentDialog==-2) {
            initDialog(null);
        }
        root.setOnKeyPressed(event -> {
            if(!openInventory) {
                for (int i = 0; i < presetKeys.size(); i++) {
                    if (presetKeys.get(i) == event.getCode()) {
                        pressedKeys.replace(presetKeys.get(i), true);
                    }
                }
                if (event.getCode() == KeyCode.E) {
                    checkBounds(player.getLayoutX() + 20, player.getLayoutY() + 60, playerHitbox.getWidth(), playerHitbox.getHeight());
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    for (int i = 0; i < root.getChildren().size(); i++) {
                        Node n = root.getChildren().get(i);
                        n.setEffect(new GaussianBlur());
                    }
                    drawOptionsMenu();
                }
                if (event.getCode() == KeyCode.I) {
                    openInventory = true;
                    for (int i = 0; i < root.getChildren().size(); i++) {
                        Node n = root.getChildren().get(i);
                        n.setEffect(new GaussianBlur());
                    }
                    drawInventory();
                }
                if(event.getCode() == KeyCode.P && activeQuest){
                    for(int i = 0; i<currentQuest.getReqs().size(); i++){
                        currentQuest.completeReqAtPos(i);
                    }
                    updated = false;
                    drawQuest(currentQuest);
                    if(questFor.equals("KnightNRQ")) {
                        inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/Rolex.png")), "Rolex"));
                        inventoryNames.add("Rolex");
                        buisnessOwner = true;
                        moneyCount+=100;
                        currentDialog++;
                    }else if(questFor.equals("KnightNFQ")){
                        cloutCount+=10;
                        inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/hoodie.png")),"Supreme Hoodie"));
                        inventoryNames.add("Supreme Hoodie");
                        landLord = true;
                        currentDialog++;
                    }
                    activeQuest = false;
                }
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
                            if (((x > knightXY.getX() && x < knightXY.getX() + r.getWidth()) || (x + width > knightXY.getX() && x + width < knightXY.getX() + r.getWidth()) || (((x + (width/2)) < knightXY.getX()+r.getWidth())&&((x + (width/2)) > knightXY.getX()))) &&
                                    ((y > knightXY.getY() && y < knightXY.getY() + r.getHeight()) || (y + height > knightXY.getY() && y + height < knightXY.getY() + r.getHeight()) || (((y + (height/2)) < knightXY.getY()+r.getHeight())&&((y + (height/2)) > knightXY.getY())))) {
                                initDialog("Knight");
                            }
                            break;
                        case "objN":
                            if (((x > ninjaXY.getX() && x < ninjaXY.getX() + r.getWidth()) || (x + width > ninjaXY.getX() && x + width < ninjaXY.getX() + r.getWidth()) || (((x + (width/2)) < ninjaXY.getX()+r.getWidth())&&((x + (width/2)) > ninjaXY.getX()))) &&
                                    ((y > ninjaXY.getY() && y < ninjaXY.getY() + r.getHeight()) || (y + height > ninjaXY.getY() && y + height < ninjaXY.getY() + r.getHeight()) || (((y + (height/2)) < ninjaXY.getY()+r.getHeight())&&((y + (height/2)) > ninjaXY.getY())))) {
                                initDialog("Ninja");
                            }
                            break;
                        case "objInventoryBelt":
                            if (((x > beltXY.getX() && x < beltXY.getX() + r.getWidth()) || (x + width > beltXY.getX() && x + width < beltXY.getX() + r.getWidth()) || (((x + (width/2)) < beltXY.getX()+r.getWidth())&&((x + (width/2)) > beltXY.getX()))) &&
                                    ((y > beltXY.getY() && y < beltXY.getY() + r.getHeight()) || (y + height > beltXY.getY() && y + height < beltXY.getY() + r.getHeight()) || (((y + (height/2)) < beltXY.getY()+r.getHeight())&&((y + (height/2)) > beltXY.getY())))) {
                                initDialog("Gucci Belt");
                            }
                            break;
                        case "objInventoryBackpack":
                            if (((x > backpackXY.getX() && x < backpackXY.getX() + r.getWidth()) || (x + width > backpackXY.getX() && x + width < backpackXY.getX() + r.getWidth()) || (((x + (width/2)) < backpackXY.getX()+r.getWidth())&&((x + (width/2)) > backpackXY.getX()))) &&
                                    ((y > backpackXY.getY() && y < backpackXY.getY() + r.getHeight()) || (y + height > backpackXY.getY() && y + height < backpackXY.getY() + r.getHeight()) || (((y + (height/2)) < backpackXY.getY()+r.getHeight())&&((y + (height/2)) > backpackXY.getY())))) {
                                initDialog("LV Backpack");
                            }
                            break;
                        case "a":
                            break;
                        case "b":
                            break;
                        case "c":
                            break;
                        case "d":
                            break;
                        case "e":
                            break;
                    }

                }
            }
        }
    }
    private void initDialog(String character){
        activeDialog = true;
        Rectangle r = new Rectangle(0,500,800,100);
        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        r.setStrokeWidth(3);
        r.setId("dialog");
        root.getChildren().add(r);
        if(currentDialog==-2&&character==null){
            Text t = new Text("Player:\nPress W, A, S, D to move, and E to interact");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            currentDialog++;
            System.out.println(currentDialog);
        } else if(currentDialog==-1&&character==null){
            Text t = new Text("Player:\nPress "+presetKeys.get(presetKeys.size()-1)+" to open inventory");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            currentDialog++;
            System.out.println(currentDialog);
        } else if (currentDialog==0&&character.equals("Ninja")){
            Text t = new Text("Ninja:\nHey man, I just lost my gucci belt it would be so helpful if you found it");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            currentDialog++;
            System.out.println(currentDialog);
        } else if(currentDialog==1&&character.equals("Ninja")){
            Text t = new Text("Ninja:\nWusspopin my boy over their looks like he needs some help");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
        }
        else if(currentDialog==0&&character.equals("Knight")){
            Text t = new Text("Knight:\nWusspopin my boy over their looks like he needs some help");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
        } else if(character.equals("Gucci Belt")&&!containsBackpack){
            Text t = new Text("Player:\nSorry broskii can't carry dont have a backpack");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
        } else if(character.equals("Gucci Belt")&&containsBackpack){
            currentDialog=2;
            Text t = new Text("Player:\nNow we can give him his belt back");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/belt.png")),"Gucci Belt"));
            inventoryNames.add("Gucci Belt");
            root.getChildren().remove(belt);
            root.getChildren().remove(beltHitbox);
            currentDialog++;
            System.out.println(currentDialog);
            root.getChildren().add(t);
        } else if(character.equals("LV Backpack")){
            Text t = new Text("Player:\nAyy now we can carry stuff");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            root.getChildren().remove(backpack);
            root.getChildren().remove(backpackHitbox);
            containsBackpack = true;
            System.out.println(currentDialog);
        }else if(character.equals("Ninja")&&currentDialog==3){
            Text t = new Text("Ninja:\nThanks man I appreciate it, here have some dough");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            cloutCount++;
            moneyCount+=10;
            currentDialog++;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Gucci Belt")){
                    inventory.remove(i);
                }
            }
            for(int i = 0; i<inventoryNames.size(); i++){
                if(inventoryNames.get(i).equals("Gucci Belt")){
                    inventoryNames.remove(i);
                }
            }
            System.out.println(currentDialog);
            /**
             * @NinjaQuest
             */
        }else if(character.equals("Ninja")&&currentDialog==4&&moneyCount==10){
            Text t = new Text("Ninja:\nHey you're pretty useful I have a quest you can do for me");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            activeQuest = true;
            currentDialog++;
            currentQuest.setQuestName("Quest For Ninja");
            ArrayList<String> requirements = new ArrayList<>();
            requirements.add("Get Ninja's Weapon");
            requirements.add("Achieve the Clouts");
            requirements.add("Acquire 50$");
            currentQuest.setReqs(requirements);
            currentActiveQuest.put(currentQuest,false);
            System.out.println(currentDialog);
            /**
             * @For_Ease_Of_Use
             */
            questFor = "NinjaNQ";


            /**
             * @NinjaFriendlyQuest
             */
        }else if(character.equals("Knight")&&currentDialog==4&&moneyCount==10){
            Text t = new Text("Knight:\nYo mane I have a quest yo could do for me");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            activeQuest = true;
            currentDialog++;
            currentQuest.setQuestName("Quest For Clout");
            ArrayList<String> requirements = new ArrayList<>();
            requirements.add("Get over 10C");
            requirements.add("Get a Hoddie");
            requirements.add("Be a Landlord");
            currentQuest.setReqs(requirements);
            currentActiveQuest.put(currentQuest,false);
            System.out.println(currentDialog);
            /**
             * @For_Ease_Of_Use
             */
            questFor = "KnightNFQ";


        } else if(character.equals("Knight")&&currentDialog==5&&moneyCount==10&&activeQuest){

        } else if(character.equals("Knight")&&currentDialog==3){
            Text t = new Text("Knight:\nWell I don't need it but I'll take it, here's some cash");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            moneyCount+=50;
            currentDialog++;
            System.out.println(currentDialog);
            inventory.remove(0);
        }else if(character.equals("Ninja")&&moneyCount==50){
            Text t = new Text("Ninja:\nWhat the heck did you do with my gucci belt");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            cloutCount-=5;
            leavingCharacters.add("Ninja");

        }
        /**
         * @NinjaRejectQuest
         */
        else if(character.equals("Knight")&&currentDialog==4&&moneyCount==50){
            Text t = new Text("Knight:\nHey I have a quest that you can complete for me");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
            currentDialog++;
            currentQuest.setQuestName("Quest For Money");
            ArrayList<String> requirements = new ArrayList<>();
            requirements.add("Get Over $100");
            requirements.add("Acquire a rolex");
            requirements.add("Found a business");
            currentQuest.setReqs(requirements);
            currentActiveQuest.put(currentQuest,false);
            activeQuest = true;
            System.out.println(currentDialog);
            /**
             * @For_Ease_Of_Use
             */
            questFor = "KnightNRQ";


        }else if(currentDialog==5&&character.equals("Knight")&&moneyCount==50&&activeQuest){
            Text t = new Text("Knight:\nHey you complete that quest yet?");
            t.setLayoutX(15);
            t.setLayoutY(530);
            t.setFont(new Font(28));
            t.setWrappingWidth(760);
            t.setId("dialog");
            root.getChildren().add(t);
        }

    }
    private void moveUp(){
        player.setLayoutY(player.getLayoutY()-movementIncrement);
        playerHitbox.setLayoutY(playerHitbox.getLayoutY()-movementIncrement);
    }
    private void moveLeft(){
        player.setLayoutX(player.getLayoutX()-movementIncrement);
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
        exit.setId("Exit");
        exit.getStylesheets().add(cssPath);
        exit.getStylesheets().add("Stylesheet.css");
        miniPane.getChildren().add(exit);

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
    private void drawInventory(){
        Pane miniPane = new Pane();
        miniPane.setLayoutX(200);
        miniPane.setLayoutY(50);

        Rectangle r = new Rectangle(350, 450);
        r.setStrokeWidth(3);
        r.setStroke(Color.BLACK);
        r.setFill(Color.WHITE);
        miniPane.getChildren().add(r);

        Text t1 = new Text("Inventory");
        t1.setFont(new Font(32));
        t1.setLayoutY(47);
        t1.setLayoutX(30);
        miniPane.getChildren().add(t1);

        Text t2 = new Text("Money: $"+moneyCount);
        t2.setFont(new Font(12));
        t2.setLayoutY(25);
        t2.setLayoutX(190);
        miniPane.getChildren().add(t2);

        Text t3 = new Text("NetWorth: $"+(moneyCount+totalValueInventory()));
        t3.setFont(new Font(12));
        t3.setLayoutY(40);
        t3.setLayoutX(190);
        miniPane.getChildren().add(t3);

        Text t4 = new Text("Clout: "+cloutCount+"C");
        t4.setFont(new Font(12));
        t4.setLayoutY(55);
        t4.setLayoutX(190);
        miniPane.getChildren().add(t4);

        if(containsBackpack) {
            TableColumn tc1 = new TableColumn<>("Picture");
            tc1.setCellValueFactory(new PropertyValueFactory<InventoryData, ImageView>("image"));
            tc1.setMinWidth(70);

            TableColumn tc2 = new TableColumn<>("Text");
            tc2.setCellValueFactory(new PropertyValueFactory<InventoryData, String>("itemName"));
            tc2.setMinWidth(60);

            TableView<InventoryData> table = new TableView<>();
            table.setEditable(false);
            table.getColumns().addAll(tc1, tc2);
            table.setItems(inventory);

            table.setMaxSize(170, 350);

            final VBox vbox = new VBox();
            vbox.setId("tablebox");
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(table);
            vbox.setLayoutX(15);
            vbox.setLayoutY(75);

            if(inventory.size()!=0) {
                miniPane.getChildren().add(vbox);
            }else{
                Text t = new Text("Y'all don't have anything in yo backpack");
                t.setFont(new Font(28));
                t.setLayoutY(100);
                t.setLayoutX(10);
                t.setWrappingWidth(335);
                miniPane.getChildren().add(t);
            }
        }else{
            Text t = new Text("You can't carry anything y'all don't got a backpack");
            t.setFont(new Font(28));
            t.setLayoutY(100);
            t.setLayoutX(10);
            t.setWrappingWidth(335);
            miniPane.getChildren().add(t);
        }


        root.requestFocus();
        root.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.I){
                root.getChildren().remove(miniPane);
                for(int i = 0; i<root.getChildren().size(); i++){
                    Node n = root.getChildren().get(i);
                    n.setEffect(null);
                }
                openInventory = false;
                initGame();
            }
        });

        root.getChildren().add(miniPane);
    }
    public static class InventoryData{
        private ImageView image;
        private String itemName;
        private InventoryData(ImageView i, String s){
            image = i;
            itemName = s;
        }
        public void setImage(Image i){
            image.setImage(i);
        }
        public ImageView getImage(){
            return image;
        }
        public void setItemName(String s){
            itemName = s;
        }
        public String getItemName(){
            return itemName;
        }
    }
    private ImageView formatImage(@NotNull ImageView image){
        image.setFitHeight(70);
        image.setFitWidth(70);
        image.setEffect(null);
        return image;
    }
    private double totalValueInventory(){
        double total = 0;
        for(int i = 0; i<inventory.size(); i++){
            if(inventory.get(i).getItemName().equals("Gucci Belt")){
                total+=300;
            }
            if(inventory.get(i).getItemName().equals("Rolex")){
                total+=1000;
            }
            if(inventory.get(i).getItemName().equals("Supreme Hoodie")){
                total+=150;
            }
        }
        return total;
    }
    private void drawQuest(Quest q){
        boolean alreadyDrawn = false;
        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null){
                if(root.getChildren().get(i).getId().contains(q.getQuestName())){
                    alreadyDrawn = true;
                }else if(root.getChildren().get(i).getId().contains("QuestReq")&&!updated){
                    root.getChildren().remove(i);
                    i--;
                }
            }
        }
        if(!alreadyDrawn) {
            Pane miniRoot = new Pane();
            miniRoot.setLayoutY(0);
            miniRoot.setLayoutX(650);
            miniRoot.setId(q.getQuestName()+"qwxz");

            Color colorA = new Color(.9, .9, .9, .7);

            Rectangle r1 = new Rectangle(150, 175);
            r1.setFill(colorA);
            r1.setStrokeWidth(1);
            r1.setStroke(colorA);
            r1.setId("qwxz");
            miniRoot.getChildren().add(r1);

            root.getChildren().add(miniRoot);

            Text t1 = new Text(currentQuest.getQuestName());
            t1.setFill(Color.BLACK);
            t1.setLayoutX(652);
            t1.setLayoutY(20);
            t1.setFont(new Font(15));
            t1.setId("qwxz"+"1");
            root.getChildren().add(t1);
        }
        if(!updated) {
            for (int i = 0; i < q.getReqs().size(); i++) {
                CheckBox cb1 = new CheckBox();
                cb1.setId("QuestReq"+"qwxz");
                cb1.setLayoutY(35 + (i * 30));
                cb1.setLayoutX(652);
                cb1.setMaxSize(7, 7);
                if (q.getReqAtPos(i)) {
                    cb1.fire();
                }
                cb1.setDisable(true);
                root.getChildren().add(cb1);

                Text t1 = new Text(q.getReqNameAtPos(i));
                t1.setId("QuestReq"+"qwxz");
                t1.setLayoutX(673);
                t1.setLayoutY(50 + (i * 30));
                t1.setFont(new Font(12));
                t1.setStroke(Color.BLACK);
                t1.setFill(Color.BLACK);
                if (q.getReqAtPos(i)) {
                    t1.setStrikethrough(true);
                }
                root.getChildren().add(t1);
            }
            updated = true;
        }
    }
    private void removeQuest(){
        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null){
                if(root.getChildren().get(i).toString().contains("Pane")){
                    Pane p = (Pane) root.getChildren().get(i);
                    for(int f = 0; f<p.getChildren().size(); f++){
                        p.getChildren().remove(f);
                        root.getChildren().remove(i);
                        root.getChildren().add(p);
                    }
                }
                else if(root.getChildren().get(i).getId().contains("qwxz")){
                    root.getChildren().remove(i);
                    i--;
                }
            }
        }
    }
    private void checkReqs(Quest q){
        switch (questFor) {
            case "KnightNRQ":
                for (String inventoryName : inventoryNames) {
                    if (inventoryName.equals("Rolex")) {
                        q.completeReqAtPos(0);
                    }
                }
                if (moneyCount >= 100) {
                    q.completeReqAtPos(1);
                }
                if (buisnessOwner) {
                    q.completeReqAtPos(2);
                }
                break;
            case "KnightNFQ":
                if (cloutCount >= 10) {
                    q.completeReqAtPos(0);
                }
                for (String itemName : inventoryNames) {
                    if (itemName.equals("Supreme Hoodie")) {
                        q.completeReqAtPos(1);
                    }
                }
                if (landLord) {
                    q.completeReqAtPos(2);
                }
                break;
            case "NinjaNQ":
                for (String itemName : inventoryNames) {
                    if (itemName.equals("Ninja's Weapon")) {
                        q.completeReqAtPos(0);
                    }
                    if (itemName.equals("Clouts")) {
                        q.completeReqAtPos(1);
                    }
                }
                if (moneyCount >= 50) {
                    q.completeReqAtPos(2);
                }
                break;
        }
    }
    private void moveOffScreen(@NotNull String s){
        if(s.equals("Ninja")){
            ninja.setRotate(180);
            ninja.setLayoutX(ninja.getLayoutX()+1);
            ninjaHitbox.setLayoutX(ninjaHitbox.getLayoutX()+1);
        }
    }
}