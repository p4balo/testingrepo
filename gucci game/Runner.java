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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.nio.file.Paths;
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
    private ArrayList<ImageView> activeScene = new ArrayList<>();
    private ArrayList<String> leavingCharacters = new ArrayList<>();
    private ArrayList<String> inventoryNames = new ArrayList<>();
    private ObservableList<InventoryData> inventory = FXCollections.observableArrayList();
    private Pane root;
    private MusicPlayer mp;
    private Quest currentQuest = new Quest();
    private ImageView background;
    private ImageView player;
    private ImageView knight;
    private ImageView ninja;
    private ImageView belt;
    private ImageView backpack;
    private ImageView brick;
    private ImageView goggles;
    private ImageView hoodie;
    private ImageView rolex;
    private ImageView wallet;
    private ImageView extinguisher;
    private ImageView ninjaWeapon;
    private ImageView sign1;
    private ImageView sign2;
    private ImageView sign3;
    private ImageView sign4;
    private ImageView sign5;
    private ImageView house;
    private ImageView crib;
    private ImageView endScreen;
    private ImageView insideHouse;
    private Rectangle playerHitbox;
    private Rectangle knightHitbox;
    private Rectangle ninjaHitbox;
    private Rectangle beltHitbox;
    private Rectangle backpackHitbox;
    private Rectangle brickHitbox;
    private Rectangle gogglesHitbox;
    private Rectangle hoodieHitbox;
    private Rectangle rolexHitbox;
    private Rectangle walletHitbox;
    private Rectangle extinguisherHitbox;
    private Rectangle ninjaWeaponHitbox;
    private Rectangle sign1HitBox;
    private Rectangle sign2HitBox;
    private Rectangle sign3HitBox;
    private Rectangle sign4HitBox;
    private Rectangle sign5HitBox;
    private Rectangle houseHitbox;
    private Rectangle cribHitbox;
    private Point2D knightXY;
    private Point2D ninjaXY;
    private Point2D beltXY;
    private Point2D backpackXY;
    private Point2D brickXY;
    private Point2D gogglesXY;
    private Point2D hoodieXY;
    private Point2D rolexXY;
    private Point2D walletXY;
    private Point2D extinguisherXY;
    private Point2D ninjaWeaponXY;
    private Point2D sign1XY;
    private Point2D sign2XY;
    private Point2D sign3XY;
    private Point2D sign4XY;
    private Point2D sign5XY;
    private Point2D houseXY;
    private Point2D cribXY;
    private int cloutCount;
    private int currentDialog;
    private int dialogCounter = 0;
    private int currentIdleImagePlayer = 0;
    private int currentMovingImage = 0;
    private int currentMovingDecrement = 5;
    private int idleCountNPC;
    private int currentScene;
    private int brickCount;
    private int previousScene;
    private int carryingCapacity = 1000;
    private int brickSceneNumb = 3;
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
    private boolean updatedQuests;
    private boolean givenAwayBelt;
    private boolean givenBeltBack;
    private boolean activeTransition;
    private boolean alreadyInScene;
    private boolean hasExtinguisher;
    private boolean hasHoodie;
    private boolean foundNinja;
    private boolean finalQuest;
    private boolean end;
    private String questFor;
    private String cssPath = "Stylesheet.css";

    //make it so you cant get money if you don't have wallet, but don't have wallet images rn
    private boolean containsWallet;

    public Runner() {
        mp = new MusicPlayer();
        currentScene = 1;
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

        sign1 = new ImageView("resources/Objects/sign01.png");
        sign1.setLayoutX(675);
        sign1.setLayoutY(250);
        sign1.setFitWidth(70);
        sign1.setFitHeight(70);
        sign1.setId("obSign1");
        sign1.setTranslateZ(sign1.getBoundsInLocal().getWidth()/2);
        sign1.setRotationAxis(Rotate.Y_AXIS);
        sign1.setRotate(0);
        sign1HitBox = new Rectangle(sign1.getLayoutX(),sign1.getLayoutY(),sign1.getFitWidth(),sign1.getFitHeight());
        sign1HitBox.setStroke(Color.TRANSPARENT);
        sign1HitBox.setStrokeWidth(3);
        sign1HitBox.setFill(Color.TRANSPARENT);
        sign1HitBox.setId("objSign1");
        sign1XY = new Point2D(sign1.getLayoutX(),sign1.getLayoutY());

        sign2 = new ImageView("resources/Objects/sign02.png");
        sign2.setLayoutX(25);
        sign2.setLayoutY(250);
        sign2.setFitWidth(70);
        sign2.setFitHeight(70);
        sign2.setId("obSign2");
        sign2.setTranslateZ(sign2.getBoundsInLocal().getWidth()/2);
        sign2.setRotationAxis(Rotate.Y_AXIS);
        sign2.setRotate(180);
        sign2HitBox = new Rectangle(sign2.getLayoutX(),sign2.getLayoutY(),sign2.getFitWidth(),sign2.getFitHeight());
        sign2HitBox.setStroke(Color.TRANSPARENT);
        sign2HitBox.setStrokeWidth(3);
        sign2HitBox.setFill(Color.TRANSPARENT);
        sign2HitBox.setId("objSign2");
        sign2XY = new Point2D(sign2.getLayoutX(),sign2.getLayoutY());

        sign3 = new ImageView("resources/Objects/sign03.png");
        sign3.setLayoutX(300);
        sign3.setLayoutY(200);
        sign3.setFitWidth(70);
        sign3.setFitHeight(70);
        sign3.setId("obSign3");
        sign3.setTranslateZ(sign3.getBoundsInLocal().getWidth()/2);
        sign3.setRotationAxis(Rotate.Y_AXIS);
        sign3.setRotate(0);
        sign3HitBox = new Rectangle(sign3.getLayoutX(),sign3.getLayoutY(),sign3.getFitWidth(),sign3.getFitHeight());
        sign3HitBox.setStroke(Color.TRANSPARENT);
        sign3HitBox.setStrokeWidth(3);
        sign3HitBox.setFill(Color.TRANSPARENT);
        sign3HitBox.setId("objSign3");
        sign3XY = new Point2D(sign3.getLayoutX(),sign3.getLayoutY());

        sign4 = new ImageView("resources/Objects/sign04.png");
        sign4.setLayoutX(300);
        sign4.setLayoutY(200);
        sign4.setFitWidth(70);
        sign4.setFitHeight(70);
        sign4.setId("obSign4");
        sign4.setTranslateZ(sign1.getBoundsInLocal().getWidth()/2);
        sign4.setRotationAxis(Rotate.Y_AXIS);
        sign4.setRotate(0);
        sign4HitBox = new Rectangle(sign4.getLayoutX(),sign4.getLayoutY(),sign4.getFitWidth(),sign4.getFitHeight());
        sign4HitBox.setStroke(Color.TRANSPARENT);
        sign4HitBox.setStrokeWidth(3);
        sign4HitBox.setFill(Color.TRANSPARENT);
        sign4HitBox.setId("objSign4");
        sign4XY = new Point2D(sign4.getLayoutX(),sign4.getLayoutY());

        sign5 = new ImageView("resources/Objects/sign05.png");
        sign5.setLayoutX(50);
        sign5.setLayoutY(200);
        sign5.setFitWidth(70);
        sign5.setFitHeight(70);
        sign5.setId("obSign5");
        sign5.setTranslateZ(sign1.getBoundsInLocal().getWidth()/2);
        sign5.setRotationAxis(Rotate.Y_AXIS);
        sign5.setRotate(180);
        sign5HitBox = new Rectangle(sign5.getLayoutX(),sign5.getLayoutY(),sign5.getFitWidth(),sign5.getFitHeight());
        sign5HitBox.setStroke(Color.TRANSPARENT);
        sign5HitBox.setStrokeWidth(3);
        sign5HitBox.setFill(Color.TRANSPARENT);
        sign5HitBox.setId("objSign5");
        sign5XY = new Point2D(sign5.getLayoutX(),sign5.getLayoutY());

        brick = new ImageView("resources/Objects/brick.png");
        brick.setLayoutX(100);
        brick.setLayoutY(250);
        brick.setFitWidth(70);
        brick.setFitHeight(70);
        brick.setId("obBrick");
        brick.setTranslateZ(brick.getBoundsInLocal().getWidth()/2);
        brick.setRotationAxis(Rotate.Y_AXIS);
        brick.setRotate(0);
        brickHitbox = new Rectangle(brick.getLayoutX(),brick.getLayoutY()+15,brick.getFitWidth(),brick.getFitHeight()-25);
        brickHitbox.setStroke(Color.TRANSPARENT);
        brickHitbox.setStrokeWidth(3);
        brickHitbox.setFill(Color.TRANSPARENT);
        brickHitbox.setId("objBrick");
        brickXY = new Point2D(brick.getLayoutX(),brick.getLayoutY());

        extinguisher = new ImageView("resources/Objects/extinguisher.png");
        extinguisher.setLayoutX(50);
        extinguisher.setLayoutY(50);
        extinguisher.setFitWidth(70);
        extinguisher.setFitHeight(100);
        extinguisher.setId("obExtinguisher");
        extinguisher.setTranslateZ(extinguisher.getBoundsInLocal().getWidth()/2);
        extinguisher.setRotationAxis(Rotate.Y_AXIS);
        extinguisher.setRotate(0);
        extinguisherHitbox = new Rectangle(extinguisher.getLayoutX(),extinguisher.getLayoutY(),extinguisher.getFitWidth(),extinguisher.getFitHeight());
        extinguisherHitbox.setStroke(Color.TRANSPARENT);
        extinguisherHitbox.setStrokeWidth(3);
        extinguisherHitbox.setFill(Color.TRANSPARENT);
        extinguisherHitbox.setId("objExtinguisher");
        extinguisherXY = new Point2D(extinguisher.getLayoutX(),extinguisher.getLayoutY());

        goggles = new ImageView("resources/Objects/goggles.png");
        goggles.setLayoutX(600);
        goggles.setLayoutY(400);
        goggles.setFitWidth(100);
        goggles.setFitHeight(50);
        goggles.setId("obGoggles");
        goggles.setTranslateZ(goggles.getBoundsInLocal().getWidth()/2);
        goggles.setRotationAxis(Rotate.Y_AXIS);
        goggles.setRotate(0);
        gogglesHitbox = new Rectangle(goggles.getLayoutX(),goggles.getLayoutY(),goggles.getFitWidth(),goggles.getFitHeight());
        gogglesHitbox.setStroke(Color.TRANSPARENT);
        gogglesHitbox.setStrokeWidth(3);
        gogglesHitbox.setFill(Color.TRANSPARENT);
        gogglesHitbox.setId("objGoggles");
        gogglesXY = new Point2D(goggles.getLayoutX(),goggles.getLayoutY());

        hoodie = new ImageView("resources/Objects/hoodie.png");
        hoodie.setLayoutX(500);
        hoodie.setLayoutY(500);
        hoodie.setFitWidth(70);
        hoodie.setFitHeight(70);
        hoodie.setId("obHoodie");
        hoodie.setTranslateZ(hoodie.getBoundsInLocal().getWidth()/2);
        hoodie.setRotationAxis(Rotate.Y_AXIS);
        hoodie.setRotate(0);
        hoodieHitbox = new Rectangle(hoodie.getLayoutX(),hoodie.getLayoutY(),hoodie.getFitWidth(),hoodie.getFitHeight());
        hoodieHitbox.setStroke(Color.TRANSPARENT);
        hoodieHitbox.setStrokeWidth(3);
        hoodieHitbox.setFill(Color.TRANSPARENT);
        hoodieHitbox.setId("objHoodie");
        hoodieXY = new Point2D(hoodie.getLayoutX(),hoodie.getLayoutY());

        rolex = new ImageView("resources/Objects/Rolex.png");
        rolex.setLayoutX(200);
        rolex.setLayoutY(50);
        rolex.setFitWidth(50);
        rolex.setFitHeight(70);
        rolex.setId("obRolex");
        rolex.setTranslateZ(rolex.getBoundsInLocal().getWidth()/2);
        rolex.setRotationAxis(Rotate.Y_AXIS);
        rolex.setRotate(0);
        rolexHitbox = new Rectangle(rolex.getLayoutX(),rolex.getLayoutY(),rolex.getFitWidth(),rolex.getFitHeight());
        rolexHitbox.setStroke(Color.TRANSPARENT);
        rolexHitbox.setStrokeWidth(3);
        rolexHitbox.setFill(Color.TRANSPARENT);
        rolexHitbox.setId("objRolex");
        rolexXY = new Point2D(rolex.getLayoutX(),rolex.getLayoutY());

        wallet = new ImageView("resources/Objects/wallet.png");
        wallet.setLayoutX(600);
        wallet.setLayoutY(500);
        wallet.setFitWidth(50);
        wallet.setFitHeight(50);
        wallet.setId("obWallet");
        wallet.setTranslateZ(wallet.getBoundsInLocal().getWidth()/2);
        wallet.setRotationAxis(Rotate.Y_AXIS);
        wallet.setRotate(0);
        walletHitbox = new Rectangle(wallet.getLayoutX(),wallet.getLayoutY(),wallet.getFitWidth(),wallet.getFitHeight());
        walletHitbox.setStroke(Color.TRANSPARENT);
        walletHitbox.setStrokeWidth(3);
        walletHitbox.setFill(Color.TRANSPARENT);
        walletHitbox.setId("objWallet");
        walletXY = new Point2D(wallet.getLayoutX(),wallet.getLayoutY());

        ninjaWeapon = new ImageView("resources/Ninja/NinjaWeapon.png");
        ninjaWeapon.setLayoutX(600);
        ninjaWeapon.setLayoutY(100);
        ninjaWeapon.setFitWidth(40);
        ninjaWeapon.setFitHeight(40);
        ninjaWeapon.setId("obNinjaWeapon");
        ninjaWeapon.setTranslateZ(ninjaWeapon.getBoundsInLocal().getWidth()/2);
        ninjaWeapon.setRotationAxis(Rotate.Y_AXIS);
        ninjaWeapon.setRotate(0);
        ninjaWeaponHitbox = new Rectangle(ninjaWeapon.getLayoutX(),ninjaWeapon.getLayoutY(),ninjaWeapon.getFitWidth(),ninjaWeapon.getFitHeight());
        ninjaWeaponHitbox.setStroke(Color.TRANSPARENT);
        ninjaWeaponHitbox.setStrokeWidth(3);
        ninjaWeaponHitbox.setFill(Color.TRANSPARENT);
        ninjaWeaponHitbox.setId("objNinjaWeapon");
        ninjaWeaponXY = new Point2D(ninjaWeapon.getLayoutX(),ninjaWeapon.getLayoutY());

        house = new ImageView("resources/Backgrounds/houseonfire.png");
        house.setLayoutX(200);
        house.setLayoutY(100);
        house.setFitWidth(300);
        house.setFitHeight(200);
        house.setId("obHouse");
        house.setTranslateZ(house.getBoundsInLocal().getWidth()/2);
        house.setRotationAxis(Rotate.Y_AXIS);
        house.setRotate(0);
        houseHitbox = new Rectangle(house.getLayoutX()+10,house.getLayoutY(),house.getFitWidth()-20,house.getFitHeight());
        houseHitbox.setStroke(Color.TRANSPARENT);
        houseHitbox.setStrokeWidth(3);
        houseHitbox.setFill(Color.TRANSPARENT);
        houseHitbox.setId("objHouse");
        houseXY = new Point2D(house.getLayoutX(),house.getLayoutY());

        crib = new ImageView("resources/Objects/redX.png");
        crib.setLayoutX(200);
        crib.setLayoutY(350);
        crib.setFitWidth(300);
        crib.setFitHeight(200);
        crib.setId("obCrib");
        crib.setTranslateZ(crib.getBoundsInLocal().getWidth()/2);
        crib.setRotationAxis(Rotate.Y_AXIS);
        crib.setRotate(0);
        cribHitbox = new Rectangle(crib.getLayoutX()+10,crib.getLayoutY(),crib.getFitWidth()-20,crib.getFitHeight());
        cribHitbox.setStroke(Color.TRANSPARENT);
        cribHitbox.setStrokeWidth(3);
        cribHitbox.setFill(Color.TRANSPARENT);
        cribHitbox.setId("objCrib");
        cribXY = new Point2D(crib.getLayoutX(),crib.getLayoutY());

        endScreen = new ImageView("resources/Backgrounds/endscreen.png");
        endScreen.setLayoutX(0);
        endScreen.setLayoutY(0);
        endScreen.setFitWidth(800);
        endScreen.setFitHeight(600);

        insideHouse = new ImageView("resources/Backgrounds/insideHouse.png");
        insideHouse.setLayoutX(0);
        insideHouse.setLayoutY(0);
        insideHouse.setFitWidth(800);
        insideHouse.setFitHeight(600);

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
        ImageView background4 = new ImageView("resources/Backgrounds/snow.png");
        background4.setFitWidth(800);
        background4.setFitHeight(600);
        background4.setLayoutY(0);
        background4.setLayoutX(0);
        ImageView background5 = new ImageView("resources/Backgrounds/grass2.jpg");
        background5.setFitWidth(800);
        background5.setFitHeight(600);
        background5.setLayoutY(0);
        background5.setLayoutX(0);
        activeScene.add(background1);
        activeScene.add(background2);
        activeScene.add(background3);
        activeScene.add(background5);
        activeScene.add(background4);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = screenSize.getWidth();
        if(screenWidth >1500){
            movementIncrement = 4;
        }else{
            movementIncrement = .5;
            currentMovingDecrement = 15;
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void timer(){
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if (!end) {
                    moving = pressedKeys.values().contains(true);
                    if (moving) {
                        if ((currentMovingImage / currentMovingDecrement) == idlePosePlayer.size()) {
                            currentMovingImage = 0;
                        }
                        if (currentMovingImage % currentMovingDecrement == 0) {
                            player.setImage(movingPosePlayer.get(currentMovingImage / currentMovingDecrement));
                        }
                        currentMovingImage++;
                    } else {
                        if ((currentIdleImagePlayer / 10) == idlePosePlayer.size()) {
                            currentIdleImagePlayer = 0;
                        }
                        if (currentIdleImagePlayer % 10 == 0) {
                            player.setImage(idlePosePlayer.get(currentIdleImagePlayer / 10));
                        }
                        currentIdleImagePlayer++;
                    }
                    /**
                     * @reference
                     * player.getLayoutX() + 20, player.getLayoutY() + 60, playerHitbox.getWidth(), playerHitbox.getHeight()
                     */
                    if ((player.getLayoutX() + 20) < -80 && !activeTransition) {
                        root.getChildren().clear();
                        if (currentScene == 1) {
                            previousScene = 1;
                            currentScene = 3;
                            activeTransition = true;
                            alreadyInScene = false;
                            updatedQuests = false;
                            initGame(currentScene);
                        } else if (currentScene == 2) {
                            previousScene = 2;
                            currentScene = 1;
                            activeTransition = true;
                            alreadyInScene = false;
                            updatedQuests = false;
                            initGame(currentScene);
                        }
                    } else if ((player.getLayoutX() + 20) > 830 && !activeTransition) {
                        root.getChildren().clear();
                        if (currentScene == 1) {
                            previousScene = 1;
                            currentScene = 2;
                            activeTransition = true;
                            updatedQuests = false;
                            alreadyInScene = false;
                            initGame(currentScene);
                        } else if (currentScene == 3) {
                            previousScene = 3;
                            currentScene = 1;
                            activeTransition = true;
                            updatedQuests = false;
                            alreadyInScene = false;
                            initGame(currentScene);
                        }
                    } else if ((player.getLayoutY()) > 600 && currentScene == 3 && !activeTransition) {
                        root.getChildren().clear();
                        previousScene = 3;
                        currentScene = 4;
                        activeTransition = true;
                        updatedQuests = false;
                        alreadyInScene = false;
                        initGame(currentScene);
                    } else if ((player.getLayoutY()) > 600 && currentScene == 2 && !activeTransition && hasHoodie) {
                        root.getChildren().clear();
                        previousScene = 2;
                        currentScene = 5;
                        activeTransition = true;
                        updatedQuests = false;
                        alreadyInScene = false;
                        initGame(currentScene);
                    } else if ((player.getLayoutY()) > 600 && currentScene == 2 && !activeTransition && !hasHoodie) {
                        initDialog("NoTravelSnow");
                        activeTransition = true;
                    } else if ((player.getLayoutY() + 60) < -70 && currentScene == 5 && !activeTransition) {
                        root.getChildren().clear();
                        previousScene = 5;
                        currentScene = 2;
                        activeTransition = true;
                        updatedQuests = false;
                        alreadyInScene = false;
                        initGame(currentScene);
                    } else if ((player.getLayoutY() + 60) < -70 && currentScene == 4 && !activeTransition) {
                        root.getChildren().clear();
                        previousScene = 4;
                        currentScene = 3;
                        activeTransition = true;
                        updatedQuests = false;
                        alreadyInScene = false;
                        initGame(currentScene);
                    }
                    if (activeTransition) {
                        if (((player.getLayoutX() + 20) > -80 && (player.getLayoutX() + 20) < 830) && ((player.getLayoutY() + 60) < 670 && (player.getLayoutY() + 60) > -70)) {
                            activeTransition = false;
                        }
                    }
                    for (KeyCode presetKey : presetKeys) {
                        if (presetKey != KeyCode.I) {
                            if (pressedKeys.get(presetKey)) {
                                if (presetKey == KeyCode.W) {
                                    moveUp();
                                }
                                if (presetKey == KeyCode.A) {
                                    moveLeft();
                                }
                                if (presetKey == KeyCode.S) {
                                    moveDown();
                                }
                                if (presetKey == KeyCode.D) {
                                    moveRight();
                                }
                            }
                        }
                    }
                    if (activeDialog) {
                        dialogCounter++;
                        if (dialogCounter == 270) {
                            if (currentDialog == 100) {
                                root.getChildren().clear();
                                root.getChildren().add(endScreen);
                            }
                            for (int i = 0; i < root.getChildren().size(); i++) {
                                if (root.getChildren().get(i).getId() != null) {
                                    if (root.getChildren().get(i).getId().contains("dialog")) {
                                        root.getChildren().remove(i);
                                        i--;
                                    }
                                }
                            }
                            dialogCounter = 0;
                            activeDialog = false;
                            if (currentDialog == -1) {
                                initDialog(null);
                            }
                        }
                    }
                    if ((idleCountNPC / 10) == idlePoseknight.size()) {
                        idleCountNPC = 0;
                    }
                    if (idleCountNPC % 10 == 0) {
                        knight.setImage(idlePoseknight.get(idleCountNPC / 10));
                        ninja.setImage(idlePoseninja.get(idleCountNPC / 10));
                    }
                    idleCountNPC++;
                    if (currentActiveQuest.size() > 0) {
                        List<Quest> list = new ArrayList<>(currentActiveQuest.keySet());
                        Quest q = list.get(0);
                        if (q.questComplete()) {
                            currentActiveQuest.remove(currentQuest);
                            removeQuest();
                            activeQuest = false;
                        } else if (activeQuest) {
                            checkReqs(q);
                            drawQuest(q);
                        }
                    }
                    if (leavingCharacters.size() > 0) {
                        for (String leavingCharacter : leavingCharacters) {
                            if (leavingCharacter.equals("Ninja")) {
                                if (ninja.getLayoutX() < 850) {
                                    moveOffScreen("Ninja");
                                }
                            } else if (leavingCharacter.equals("Knight")) {
                                moveOffScreen("Knight");
                            }
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
        playerHitbox.setStroke(Color.TRANSPARENT);
        playerHitbox.setStrokeWidth(3);
        playerHitbox.setFill(Color.TRANSPARENT);
        timer();

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        primaryStage.setTitle("Gucci Game");
        primaryStage.getIcons().add(new Image("resources/Objects/Gucci.png"));
    }
    private Pane titleScreen() {
        mp.play(2,true);
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

        ImageView iv3 = new ImageView("resources/Objects/goggles.png");
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
            mp.stop();
            mp.play(3,true);
            initGame(currentScene);
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
    private void initGame(int currentScene) {
        root.getChildren().clear();
        root.requestFocus();


        if(currentScene==1){
            if(previousScene==2&&!alreadyInScene){
                player.setLayoutX(820);
                playerHitbox.setLayoutX(720);
                alreadyInScene = true;
            }else if(previousScene==3&&!alreadyInScene){
                player.setLayoutX(-70);
                playerHitbox.setLayoutX(-170);
                alreadyInScene = true;
            }
            background = activeScene.get(0);
        }

        else if(currentScene==2){
            if(previousScene==1&&!alreadyInScene) {
                player.setLayoutX(-70);
                playerHitbox.setLayoutX(-170);
                alreadyInScene = true;
            }else if(previousScene==5&&!alreadyInScene){
                player.setLayoutY(600);
                playerHitbox.setLayoutY(500);
                alreadyInScene = true;
            }
            background = activeScene.get(1);
        }

        else if(currentScene==3){
            if(previousScene==1&&!alreadyInScene) {
                player.setLayoutX(820);
                playerHitbox.setLayoutX(720);
                alreadyInScene = true;
            }else if(previousScene==4&&!alreadyInScene){
                player.setLayoutY(600);
                playerHitbox.setLayoutY(500);
                alreadyInScene = true;
            }
            background = activeScene.get(2);
        }

        else if(currentScene==4&&!alreadyInScene){
            player.setLayoutY(-70);
            playerHitbox.setLayoutY(-170);
            alreadyInScene = true;
            background = activeScene.get(3);
        }

        else if(currentScene==5&&!alreadyInScene){
            player.setLayoutY(-70);
            playerHitbox.setLayoutY(-170);
            alreadyInScene = true;
            background = activeScene.get(4);
        }

        if(currentScene!=6) {
            root.getChildren().add(background);
            root.getChildren().add(player);
            root.getChildren().add(playerHitbox);
        }
        if(currentScene==6){
            root.getChildren().add(insideHouse);
            if(!givenBeltBack&&givenAwayBelt) {
                ninja.setLayoutY(300);
                ninja.setLayoutX(350);
                root.getChildren().add(ninja);
                foundNinja = true;
                initDialog("FoundNinja");
            }
        }


        if(currentScene==1) {
            boolean putBelt = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Gucci Belt")){
                    putBelt = false;
                    break;
                }
            }
            if(putBelt && (!givenAwayBelt && !givenBeltBack)){
                root.getChildren().add(belt);
                root.getChildren().add(beltHitbox);
            }
            root.getChildren().add(knight);
            root.getChildren().add(knightHitbox);
            root.getChildren().add(ninja);
            root.getChildren().add(ninjaHitbox);
            root.getChildren().add(sign1);
            root.getChildren().add(sign1HitBox);
            root.getChildren().add(sign2);
            root.getChildren().add(sign2HitBox);
        }


        if(currentScene==3){
            sign5.setRotate(0);
            sign5.setLayoutX(730);
            sign5HitBox.setLayoutX(730);
            double temp = sign5XY.getY();
            sign5XY = new Point2D(730,temp);
            root.getChildren().add(sign5);
            root.getChildren().add(sign5HitBox);
            sign4.setLayoutY(500);
            sign4.setRotate(0);
            sign4.setLayoutX(300);
            sign4XY = new Point2D(300,500);
            root.getChildren().add(sign4);
            root.getChildren().add(sign4HitBox);
            boolean putExtinguisher = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Supreme Extinguisher")){
                    putExtinguisher = false;
                    break;
                }
            }
            if(putExtinguisher){
                root.getChildren().add(extinguisher);
                root.getChildren().add(extinguisherHitbox);
            }
            boolean putHoodie = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Supreme Hoodie")){
                    putHoodie = false;
                    break;
                }
            }
            if(putHoodie){
                root.getChildren().add(hoodie);
                root.getChildren().add(hoodieHitbox);
            }
        }

        if(currentScene==2){
            sign5.setRotate(180);
            sign5.setLayoutX(50);
            double temp = sign5XY.getY();
            sign5XY = new Point2D(50,temp);
            sign5HitBox.setLayoutX(50);
            root.getChildren().add(sign5);
            root.getChildren().add(sign5HitBox);
            sign3.setLayoutY(500);
            sign3.setRotate(0);
            sign3.setLayoutX(300);
            sign3XY = new Point2D(300,500);
            root.getChildren().add(sign3);
            root.getChildren().add(sign3HitBox);
            if(!containsBackpack){
                root.getChildren().add(backpack);
                root.getChildren().add(backpackHitbox);
            }
            boolean putRolex = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Rolex")){
                    putRolex = false;
                    break;
                }
            }
            if(putRolex){
                root.getChildren().add(rolex);
                root.getChildren().add(rolexHitbox);
            }
            boolean putWallet = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("LV wallet")){
                    putWallet = false;
                    break;
                }
            }
            if(putWallet){
                root.getChildren().add(wallet);
                root.getChildren().add(walletHitbox);
            }
        }

        if(currentScene==4){
            root.getChildren().add(house);
            root.getChildren().add(houseHitbox);
            boolean putGoggles = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Clout Goggles")){
                    putGoggles = false;
                    break;
                }
            }
            if(putGoggles){
                root.getChildren().add(goggles);
                root.getChildren().add(gogglesHitbox);
            }
        }
        if(currentScene==5){
            root.getChildren().add(crib);
            root.getChildren().add(cribHitbox);
            boolean putWeapon = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Ninja\'s Weapon")){
                    putWeapon = false;
                    break;
                }
            }
            if(putWeapon){
                root.getChildren().add(ninjaWeapon);
                root.getChildren().add(ninjaWeaponHitbox);
            }
        }
        if(brickScene()&&brickCount!=4){
            root.getChildren().add(brick);
            root.getChildren().add(brickHitbox);
        }
        if(currentDialog==-2) { initDialog(null); }
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
                    updatedQuests = false;
                    if(questFor.equals("NinjaNQ")){
                        inventory.add(new InventoryData(formatImage(new ImageView("resources/Ninja/NinjaWeapon.png")),"Ninja\'s Weapon"));
                        inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/goggles.png")), "Goggles"));
                        moneyCount+=50;

                    }
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
                        case "objSign1":
                            if (((x > sign1XY.getX() && x < sign1XY.getX() + r.getWidth()) || (x + width > sign1XY.getX() && x + width < sign1XY.getX() + r.getWidth()) || (((x + (width/2)) < sign1XY.getX()+r.getWidth())&&((x + (width/2)) > sign1XY.getX()))) &&
                                    ((y > sign1XY.getY() && y < sign1XY.getY() + r.getHeight()) || (y + height > sign1XY.getY() && y + height < sign1XY.getY() + r.getHeight()) || (((y + (height/2)) < sign1XY.getY()+r.getHeight())&&((y + (height/2)) > sign1XY.getY())))) {
                                initDialog("Sign1");
                            }
                            break;
                        case "objSign2":
                            if (((x > sign2XY.getX() && x < sign2XY.getX() + r.getWidth()) || (x + width > sign2XY.getX() && x + width < sign2XY.getX() + r.getWidth()) || (((x + (width/2)) < sign2XY.getX()+r.getWidth())&&((x + (width/2)) > sign2XY.getX()))) &&
                                    ((y > sign2XY.getY() && y < sign2XY.getY() + r.getHeight()) || (y + height > sign2XY.getY() && y + height < sign2XY.getY() + r.getHeight()) || (((y + (height/2)) < sign2XY.getY()+r.getHeight())&&((y + (height/2)) > sign2XY.getY())))) {
                                initDialog("Sign2");
                            }
                            break;
                        case "objSign3":
                            if (((x > sign3XY.getX() && x < sign3XY.getX() + r.getWidth()) || (x + width > sign3XY.getX() && x + width < sign3XY.getX() + r.getWidth()) || (((x + (width/2)) < sign3XY.getX()+r.getWidth())&&((x + (width/2)) > sign3XY.getX()))) &&
                                    ((y > sign3XY.getY() && y < sign3XY.getY() + r.getHeight()) || (y + height > sign3XY.getY() && y + height < sign3XY.getY() + r.getHeight()) || (((y + (height/2)) < sign3XY.getY()+r.getHeight())&&((y + (height/2)) > sign3XY.getY())))) {
                                initDialog("Sign3");
                            }
                            break;
                        case "objSign4":
                            if (((x > sign4XY.getX() && x < sign4XY.getX() + r.getWidth()) || (x + width > sign4XY.getX() && x + width < sign4XY.getX() + r.getWidth()) || (((x + (width/2)) < sign4XY.getX()+r.getWidth())&&((x + (width/2)) > sign4XY.getX()))) &&
                                    ((y > sign4XY.getY() && y < sign4XY.getY() + r.getHeight()) || (y + height > sign4XY.getY() && y + height < sign4XY.getY() + r.getHeight()) || (((y + (height/2)) < sign4XY.getY()+r.getHeight())&&((y + (height/2)) > sign4XY.getY())))) {
                                initDialog("Sign4");
                            }
                            break;
                        case "objSign5":
                            if (((x > sign5XY.getX() && x < sign5XY.getX() + r.getWidth()) || (x + width > sign5XY.getX() && x + width < sign5XY.getX() + r.getWidth()) || (((x + (width/2)) < sign5XY.getX()+r.getWidth())&&((x + (width/2)) > sign5XY.getX()))) &&
                                    ((y > sign5XY.getY() && y < sign5XY.getY() + r.getHeight()) || (y + height > sign5XY.getY() && y + height < sign5XY.getY() + r.getHeight()) || (((y + (height/2)) < sign5XY.getY()+r.getHeight())&&((y + (height/2)) > sign5XY.getY())))) {
                                initDialog("Sign5");
                            }
                            break;
                        case "objBrick":
                            if (((x > brickXY.getX() && x < brickXY.getX() + r.getWidth()) || (x + width > brickXY.getX() && x + width < brickXY.getX() + r.getWidth()) || (((x + (width/2)) < brickXY.getX()+r.getWidth())&&((x + (width/2)) > brickXY.getX()))) &&
                                    ((y > brickXY.getY() && y < brickXY.getY() + r.getHeight()) || (y + height > brickXY.getY() && y + height < brickXY.getY() + r.getHeight()) || (((y + (height/2)) < brickXY.getY()+r.getHeight())&&((y + (height/2)) > brickXY.getY())))) {
                                initDialog("Brick");
                            }
                            break;
                        case "objExtinguisher":
                            if (((x > extinguisherXY.getX() && x < extinguisherXY.getX() + r.getWidth()) || (x + width > extinguisherXY.getX() && x + width < extinguisherXY.getX() + r.getWidth()) || (((x + (width/2)) < extinguisherXY.getX()+r.getWidth())&&((x + (width/2)) > extinguisherXY.getX()))) &&
                                    ((y > extinguisherXY.getY() && y < extinguisherXY.getY() + r.getHeight()) || (y + height > extinguisherXY.getY() && y + height < extinguisherXY.getY() + r.getHeight()) || (((y + (height/2)) < extinguisherXY.getY()+r.getHeight())&&((y + (height/2)) > extinguisherXY.getY())))) {
                                initDialog("Extinguisher");
                            }
                            break;
                        case "objGoggles":
                            if (((x > gogglesXY.getX() && x < gogglesXY.getX() + r.getWidth()) || (x + width > gogglesXY.getX() && x + width < gogglesXY.getX() + r.getWidth()) || (((x + (width/2)) < gogglesXY.getX()+r.getWidth())&&((x + (width/2)) > gogglesXY.getX()))) &&
                                    ((y > gogglesXY.getY() && y < gogglesXY.getY() + r.getHeight()) || (y + height > gogglesXY.getY() && y + height < gogglesXY.getY() + r.getHeight()) || (((y + (height/2)) < gogglesXY.getY()+r.getHeight())&&((y + (height/2)) > gogglesXY.getY())))) {
                                initDialog("Goggles");
                            }
                            break;
                        case "objHoodie":
                            if (((x > hoodieXY.getX() && x < hoodieXY.getX() + r.getWidth()) || (x + width > hoodieXY.getX() && x + width < hoodieXY.getX() + r.getWidth()) || (((x + (width/2)) < hoodieXY.getX()+r.getWidth())&&((x + (width/2)) > hoodieXY.getX()))) &&
                                    ((y > hoodieXY.getY() && y < hoodieXY.getY() + r.getHeight()) || (y + height > hoodieXY.getY() && y + height < hoodieXY.getY() + r.getHeight()) || (((y + (height/2)) < hoodieXY.getY()+r.getHeight())&&((y + (height/2)) > hoodieXY.getY())))) {
                                initDialog("Hoodie");
                            }
                            break;
                        case "objRolex":
                            if (((x > rolexXY.getX() && x < rolexXY.getX() + r.getWidth()) || (x + width > rolexXY.getX() && x + width < rolexXY.getX() + r.getWidth()) || (((x + (width/2)) < rolexXY.getX()+r.getWidth())&&((x + (width/2)) > rolexXY.getX()))) &&
                                    ((y > rolexXY.getY() && y < rolexXY.getY() + r.getHeight()) || (y + height > rolexXY.getY() && y + height < rolexXY.getY() + r.getHeight()) || (((y + (height/2)) < rolexXY.getY()+r.getHeight())&&((y + (height/2)) > rolexXY.getY())))) {
                                initDialog("Rolex");
                            }
                            break;
                        case "objWallet":
                            if (((x > walletXY.getX() && x < walletXY.getX() + r.getWidth()) || (x + width > walletXY.getX() && x + width < walletXY.getX() + r.getWidth()) || (((x + (width/2)) < walletXY.getX()+r.getWidth())&&((x + (width/2)) > walletXY.getX()))) &&
                                    ((y > walletXY.getY() && y < walletXY.getY() + r.getHeight()) || (y + height > walletXY.getY() && y + height < walletXY.getY() + r.getHeight()) || (((y + (height/2)) < walletXY.getY()+r.getHeight())&&((y + (height/2)) > walletXY.getY())))) {
                                initDialog("Wallet");
                            }
                            break;
                        case "objNinjaWeapon":
                            if (((x > ninjaWeaponXY.getX() && x < ninjaWeaponXY.getX() + r.getWidth()) || (x + width > ninjaWeaponXY.getX() && x + width < ninjaWeaponXY.getX() + r.getWidth()) || (((x + (width/2)) < ninjaWeaponXY.getX()+r.getWidth())&&((x + (width/2)) > ninjaWeaponXY.getX()))) &&
                                    ((y > ninjaWeaponXY.getY() && y < ninjaWeaponXY.getY() + r.getHeight()) || (y + height > ninjaWeaponXY.getY() && y + height < ninjaWeaponXY.getY() + r.getHeight()) || (((y + (height/2)) < ninjaWeaponXY.getY()+r.getHeight())&&((y + (height/2)) > ninjaWeaponXY.getY())))) {
                                initDialog("NinjaWeapon");
                            }
                            break;
                        case "objHouse":
                            if (((x > houseXY.getX() && x < houseXY.getX() + r.getWidth()) || (x + width > houseXY.getX() && x + width < houseXY.getX() + r.getWidth()) || (((x + (width/2)) < houseXY.getX()+r.getWidth())&&((x + (width/2)) > houseXY.getX()))) &&
                                    ((y > houseXY.getY() && y < houseXY.getY() + r.getHeight()) || (y + height > houseXY.getY() && y + height < houseXY.getY() + r.getHeight()) || (((y + (height/2)) < houseXY.getY()+r.getHeight())&&((y + (height/2)) > houseXY.getY())))) {
                                initDialog("House");
                            }
                            break;
                        case "objCrib":
                            if (((x > cribXY.getX() && x < cribXY.getX() + r.getWidth()) || (x + width > cribXY.getX() && x + width < cribXY.getX() + r.getWidth()) || (((x + (width/2)) < cribXY.getX()+r.getWidth())&&((x + (width/2)) > cribXY.getX()))) &&
                                    ((y > cribXY.getY() && y < cribXY.getY() + r.getHeight()) || (y + height > cribXY.getY() && y + height < cribXY.getY() + r.getHeight()) || (((y + (height/2)) < cribXY.getY()+r.getHeight())&&((y + (height/2)) > cribXY.getY())))) {
                                if(!finalQuest) {
                                    initDialog("Crib");
                                }else if(landLord&&finalQuest){
                                    initGame(6);
                                }
                            }
                            break;
//                        case "a":
//                            break;
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
        Text t = new Text();
        t.setLayoutX(15);
        t.setLayoutY(530);
        t.setFont(new Font(28));
        t.setWrappingWidth(760);
        t.setId("dialog");
        if(currentDialog==-2&&character==null){
            t.setText("Player:\nPress W, A, S, D to move, and E to interact");
            root.getChildren().add(t);
            currentDialog++;
            mp.play(1,false,3);
            System.out.println(currentDialog);
        }
        else if(currentDialog==-1&&character==null){
            t.setText("Player:\nPress "+presetKeys.get(presetKeys.size()-1)+" to open inventory");
            root.getChildren().add(t);
            currentDialog++;
            mp.play(1,false,3);
            System.out.println(currentDialog);
        }
        else if(character.equals("Sign1")){
            t.setText("Player:\nClout Street...");
            root.getChildren().add(t);
            mp.play(1,false,3);
            randomChanceEvent();
        }
        else if(character.equals("Sign2")){
            t.setText("Player:\nSad Road...");
            root.getChildren().add(t);
            mp.play(1,false,3);
            randomChanceEvent();
        }
        else if(character.equals("Sign3")){
            t.setText("Player:\nMain Street...");
            root.getChildren().add(t);
            mp.play(1,false,3);
            randomChanceEvent();
        }
        else if(character.equals("Sign4")){
            t.setText("Player:\n1st Avenue...");
            root.getChildren().add(t);
            mp.play(1,false,3);
            randomChanceEvent();
        }
        else if(character.equals("Sign5")){
            t.setText("Player:\nOceanView Street...");
            mp.play(1,false,3);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("Crib")&&landLord){
            t.setText("Player:\nWhat a beaut");
            mp.play(1,false,3);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("Crib")&&brickCount!=4){
            t.setText("Player:\nWe ain't got enough bricks");
            mp.play(1,false,3);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("Crib")&&brickCount==4&&!landLord){
            t.setText("Player:\nNice we finally built the clout house");
            mp.play(1,false,3);
            landLord = true;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Supreme Brick(s)")){
                    inventory.remove(i);
                }
            }
            crib.setImage(new Image("resources/Backgrounds/brickhouse.png"));
            brickCount = 0;
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("House")&&!hasExtinguisher){
            t.setText("Player:\nSorry fam we can't put out the fire");
            mp.play(1,false,3);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("House")&&hasExtinguisher){
            t.setText("Player:\nNice we put out the fire, we own the bizznizz now");
            mp.play(1,false,3);
            buisnessOwner = true;
            house.setImage(new Image("resources/Backgrounds/officebuilding.png"));
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("NoTravelSnow")){
            t.setText("Player:\nIt is way too cold to travel over there, but a good place to build our house");
            mp.play(1,false,3);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if((character.equals("Gucci Belt")||character.equals("Extinguisher")||character.equals("Goggles")||character.equals("Hoodie")||
                character.equals("Rolex")||character.equals("Wallet")||character.equals("NinjaWeapon")||character.equals("Brick"))&&!containsBackpack){
            t.setText("Player:\nSorry broskii can't carry dont have a backpack");
            mp.play(1,false,3);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("Extinguisher")){
            t.setText("Player:\nTime to put out the fire in the office building");
            mp.play(0,false);
            hasExtinguisher = true;
            root.getChildren().add(t);
            root.getChildren().remove(extinguisher);
            root.getChildren().remove(extinguisherHitbox);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/extinguisher.png")),"Supreme Extinguisher"));
            randomChanceEvent();
        }
        else if(character.equals("Goggles")){
            t.setText("Player:\nWe can see other parts of the map now");
            mp.play(0,false);
            root.getChildren().add(t);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/goggles.png")),"Clout Goggles"));
            root.getChildren().remove(goggles);
            root.getChildren().remove(gogglesHitbox);
            randomChanceEvent();
        }
        else if(character.equals("Hoodie")){
            t.setText("Player:\nWe can survive that cold weather now");
            mp.play(0,false);
            root.getChildren().add(t);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/hoodie.png")),"Supreme Hoodie"));
            root.getChildren().remove(hoodie);
            root.getChildren().remove(hoodieHitbox);
            hasHoodie = true;
            randomChanceEvent();
        }
        else if(character.equals("Rolex")){
            t.setText("Player:\nWe got the wrist iced out");
            mp.play(0,false);
            root.getChildren().add(t);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/Rolex.png")),"Rolex"));
            root.getChildren().remove(rolex);
            root.getChildren().remove(rolexHitbox);
            randomChanceEvent();
        }
        else if(character.equals("Wallet")){
            carryingCapacity = 100000;
            t.setText("Player:\nNow we can carry a larger amount of money");
            mp.play(0,false);
            root.getChildren().add(t);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/wallet.png")),"LV wallet"));
            root.getChildren().remove(wallet);
            root.getChildren().remove(walletHitbox);
            randomChanceEvent();
        }
        else if(character.equals("NinjaWeapon")&&(!givenAwayBelt && givenBeltBack)){
            t.setText("Player:\nThis is Ninja\'s weapon");
            mp.play(0,false);
            root.getChildren().add(t);
            root.getChildren().remove(ninjaWeapon);
            root.getChildren().remove(ninjaWeaponHitbox);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Ninja/NinjaWeapon.png")),"Ninja\'s Weapon"));
            randomChanceEvent();
        }
        else if(character.equals("NinjaWeapon")&&(givenAwayBelt && !givenBeltBack)){
            mp.play(0,false);
            t.setText("Player:\nThis is Ninja\'s weapon, no use for it now except for selling");
            root.getChildren().add(t);
            root.getChildren().remove(ninjaWeapon);
            root.getChildren().remove(ninjaWeaponHitbox);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Ninja/NinjaWeapon.png")),"Ninja\'s Weapon"));
        }
        else if(character.equals("NinjaWeapon")&&(!givenAwayBelt && !givenBeltBack)){
            mp.play(0,false);
            t.setText("Player:\nI mean I\'ll take it but I do not know who it belongs to");
            root.getChildren().add(t);
            root.getChildren().remove(ninjaWeapon);
            root.getChildren().remove(ninjaWeaponHitbox);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Ninja/NinjaWeapon.png")),"Ninja\'s Weapon"));
            randomChanceEvent();
        }
        else if(character.equals("Brick")&&brickCount==3){
            mp.play(0,false);
            brickCount++;
            t.setText("Player:\nNice we have all the bricks we need to make a house, now were again");
            root.getChildren().remove(brick);
            root.getChildren().remove(brickHitbox);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("Brick")&&brickCount==0){
            mp.play(0,false);
            brickCount++;
            root.getChildren().remove(brick);
            root.getChildren().remove(brickHitbox);
            placeBrick();
            t.setText("Player:\nOur first brick to the house");
            root.getChildren().add(t);
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/brick.png")),"Supreme Brick"));
            randomChanceEvent();
        }
        else if(character.equals("Brick")&&brickCount!=4){
            mp.play(0,false);
            brickCount++;
            t.setText("Player:\nWe need "+(4-brickCount)+" more to make a house");
            root.getChildren().remove(brick);
            root.getChildren().remove(brickHitbox);
            for (InventoryData data : inventory) {
                if (data.getItemName().equals("Supreme Brick")) {
                    data.setItemName("Supreme Brick(s)");
                }
            }
            placeBrick();
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if (currentDialog==0&&character.equals("Ninja")){
            mp.play(1,false,3);
            t.setText("Ninja:\nHey man, I just lost my gucci belt it would be so helpful if you found it");
            root.getChildren().add(t);
            currentDialog++;
            System.out.println(currentDialog);
            randomChanceEvent();
        }
        else if((currentDialog==0||currentDialog==1)&&character.equals("Knight")){
            mp.play(1,false,3);
            t.setText("Knight:\nWusspopin my boy over their looks like he needs some help");
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("Gucci Belt")&&containsBackpack){
            mp.play(0,false);
            currentDialog=2;
            t.setText("Player:\nNow we can give him his belt back");
            inventory.add(new InventoryData(formatImage(new ImageView("resources/Objects/belt.png")),"Gucci Belt"));
            inventoryNames.add("Gucci Belt");
            root.getChildren().remove(belt);
            root.getChildren().remove(beltHitbox);
            currentDialog++;
            System.out.println(currentDialog);
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(character.equals("LV Backpack")){
            mp.play(0,false);
            t.setText("Player:\nAyy now we can carry stuff");
            root.getChildren().add(t);
            root.getChildren().remove(backpack);
            root.getChildren().remove(backpackHitbox);
            containsBackpack = true;
            System.out.println(currentDialog);
            randomChanceEvent();
        }
        else if(character.equals("Ninja")&&currentDialog==3){
            mp.play(1,false,3);
            t.setText("Ninja:\nThanks man I appreciate it, here have some dough");
            root.getChildren().add(t);
            cloutCount++;
            if(carryingCapacity>=moneyCount) {
                moneyCount += 10;
            }
            currentDialog++;
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Gucci Belt")){
                    inventory.remove(i);
                    break;
                }
            }
            for(int i = 0; i<inventoryNames.size(); i++){
                if(inventoryNames.get(i).equals("Gucci Belt")){
                    inventoryNames.remove(i);
                }
            }
            givenAwayBelt = false;
            givenBeltBack = true;
            System.out.println(currentDialog);
            randomChanceEvent();
            /**
             * @NinjaQuest
             */
        }
        else if(character.equals("Ninja")&&currentDialog==4&&givenBeltBack){
            mp.play(1,false,3);
            t.setText("Ninja:\nHey you're pretty useful I have a quest you can do for me");
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
            randomChanceEvent();
            /**
             * @For_Ease_Of_Use
             */
            questFor = "NinjaNQ";


            /**
             * @NinjaFriendlyQuest
             */
        }
        else if(character.equals("Knight")&&currentDialog==4&&givenBeltBack){
            mp.play(1,false,3);
            t.setText("Knight:\nYo mane I have a quest yo could do for me");
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
            randomChanceEvent();


        }
        else if(character.equals("Knight")&&currentDialog==5&&givenBeltBack&&activeQuest){
            mp.play(1,false,3);
            t.setText("Knight:\nHey you complete that quest yet?");
            root.getChildren().add(t);
        }
        else if(character.equals("Knight")&&currentDialog==3){
            t.setText("Knight:\nWell I don't need it but I'll take it, here's some cash");
            mp.play(1,false,3);
            root.getChildren().add(t);
            if(carryingCapacity>=moneyCount) {
                moneyCount += 50;
            }
            for(int i = 0; i<inventory.size(); i++){
                if(inventory.get(i).getItemName().equals("Gucci Belt")){
                    inventory.remove(i);
                }
            }
            currentDialog++;
            System.out.println(currentDialog);
            inventory.remove(0);
            givenAwayBelt = true;
            givenBeltBack = false;
            randomChanceEvent();
        }
        else if(character.equals("Ninja")&&givenAwayBelt){
            t.setText("Ninja:\nWhat the heck did you do with my gucci belt");
            mp.play(1,false,3);
            root.getChildren().add(t);
            cloutCount-=5;
            leavingCharacters.add("Ninja");
            randomChanceEvent();
        }
        /**
         * @NinjaRejectQuest
         */
        else if(character.equals("Knight")&&currentDialog==4&&givenAwayBelt){
            mp.play(1,false,3);
            t.setText("Knight:\nHey I have a quest that you can complete for me");
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
            randomChanceEvent();
            /**
             * @For_Ease_Of_Use
             */
            questFor = "KnightNRQ";


        }
        else if(currentDialog==5&&character.equals("Knight")&&givenAwayBelt&&activeQuest){
            mp.play(1,false,3);
            t.setText("Knight:\nHey you complete that quest yet?");
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(currentDialog==5&&character.equals("Ninja")&&questFor.equals("NinjaNQ")&&!currentQuest.questComplete()){
            mp.play(1,false,3);
            t.setText("Ninja:\nI can see that you are making great progress");
            root.getChildren().add(t);
            randomChanceEvent();
        }
        else if(currentDialog==5&&character.equals("Ninja")&&questFor.equals("NinjaNQ")&&currentQuest.questComplete()){
            mp.play(1,false,3);
            t.setText("Ninja:\nNice Job, I have one more Quest for you");
            ArrayList<String> requirements = new ArrayList<>();
            requirements.add("Get 10,000");
            requirements.add("Build your house");
            requirements.add("Found a business");
            currentQuest.setReqs(requirements);
            currentQuest.setQuestName("Finale");
            currentActiveQuest.put(currentQuest,false);
            activeQuest = true;
            questFor = "NinjaFinale";
            root.getChildren().add(t);
            randomChanceEvent();
            currentDialog++;
        }
        else if(currentDialog==5&&character.equals("Knight")&&questFor.equals("KnightNRQ")&&currentQuest.questComplete()){
            mp.play(1,false,3);
            t.setText("Knight:\nSucks maan I feel so lonely out here, maybe you could find Ninja");
            ArrayList<String> requirements = new ArrayList<>();
            requirements.add("Find Ninja");
            currentQuest.setReqs(requirements);
            currentQuest.setQuestName("Finding Ninja");
            currentActiveQuest.put(currentQuest,false);
            activeQuest = true;
            finalQuest = true;
            questFor = "FindingNinja";
            root.getChildren().add(t);
            randomChanceEvent();
            currentDialog++;
        }
        else if(currentDialog==5&&character.equals("Knight")&&questFor.equals("KnightNFQ")&&currentQuest.questComplete()){
            mp.play(1,false,3);
            t.setText("Knight:\nThank you for all your hard work");
            ArrayList<String> requirements = new ArrayList<>();
            requirements.add("Get a Rolex");
            requirements.add("Build your house");
            requirements.add("Get the Clouts");
            currentQuest.setReqs(requirements);
            currentQuest.setQuestName("All Friends Now");
            currentActiveQuest.put(currentQuest,false);
            activeQuest = true;
            questFor = "FriendlyQuest";
            root.getChildren().add(t);
            randomChanceEvent();
            currentDialog++;
        }else if(character.equals("FoundNinja")){
            mp.play(1,false,3);
            t.setText("Ninja:\nThanks for playing");
            root.getChildren().add(t);
            currentDialog=100;
        }
    }
    private void moveUp() {
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
                    updatedQuests = false;
                    alreadyInScene = true;
                    initGame(currentScene);
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
            if(event.getCode()==KeyCode.I||event.getCode()==KeyCode.ESCAPE){
                activeTransition = true;
                root.getChildren().remove(miniPane);
                for(int i = 0; i<root.getChildren().size(); i++){
                    Node n = root.getChildren().get(i);
                    n.setEffect(null);
                }
                openInventory = false;
                updatedQuests = false;
                alreadyInScene = true;
                initGame(currentScene);
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
    public static class Quest{
        private String name;
        private HashMap<String, Boolean> reqs;
        Quest(){
            name = null;
            reqs = new HashMap<>();
        }
        Quest(String name,  ArrayList<String> requirements){
            this.name = name;
            reqs = new HashMap<>();
            for (String requirement : requirements) {
                reqs.put(requirement, false);
            }
        }
        String getQuestName(){
            return name;
        }
        void setQuestName(String name){
            this.name = name;
        }
        void setReqs(ArrayList<String> requirements){
            reqs.clear();
            for (String requirement : requirements) {
                reqs.put(requirement, false);
            }
        }
        boolean getReqAtPos(int position){
            Set<String> set = reqs.keySet();
            List<String> list = new ArrayList<>(set);
            for(int i = 0; i<list.size(); i++){
                if(position==i){
                    return reqs.get(list.get(i));
                }
            }
            return false;
        }
        void completeReqAtPos(int position){
            List<String> list = new ArrayList<>(reqs.keySet());
            for(int i = 0; i<list.size(); i++){
                if(position==i){
                    reqs.replace(list.get(i),true);
                }
            }
        }
        boolean questComplete(){
            for(int i = 0; i<reqs.size(); i++){
                if(!getReqAtPos(i)){
                    return false;
                }
            }
            return true;
        }
        void addReq( String requirement){
            reqs.put(requirement, false);
        }
        String getReqNameAtPos(int position){
            List<String> list = new ArrayList<>(reqs.keySet());
            for(int i = 0; i<list.size(); i++){
                if(position==i){
                    return list.get(i);
                }
            }
            return null;
        }
        List<String> getReqs(){
            return new ArrayList<>(reqs.keySet());
        }
    }
    public static class MusicPlayer{
        private ArrayList<Media> mediaLibrary;
        private MediaPlayer mp;
        MusicPlayer(){
            mediaLibrary = new ArrayList<>();
            mediaLibrary.add(new Media(Paths.get("resources/Sounds/pickingSometingUp.mp3").toUri().toString()));
            mediaLibrary.add(new Media(Paths.get("resources/Sounds/talking.mp3").toUri().toString()));
            mediaLibrary.add(new Media(Paths.get("resources/Sounds/titleMusic.mp3").toUri().toString()));
            mediaLibrary.add(new Media(Paths.get("resources/Sounds/gameMusic.mp3").toUri().toString()));
        }
        void play(int pos, boolean loop, int amountOfTime){
            mp = new MediaPlayer(mediaLibrary.get(pos));
            if(loop){
                mp.setCycleCount(MediaPlayer.INDEFINITE);
            }else{
                Duration d = new Duration(1000*amountOfTime);
                mp.setStopTime(d);
                mp.setOnStopped(mp::dispose);
                mp.setOnEndOfMedia(mp::dispose);
            }
            mp.play();
        }
        void play(int pos, boolean loop){
            mp = new MediaPlayer(mediaLibrary.get(pos));
            if(loop){
                mp.setCycleCount(MediaPlayer.INDEFINITE);
            }else{
                mp.setOnStopped(mp::dispose);
                mp.setOnEndOfMedia(mp::dispose);
            }
            mp.play();
        }
        void stop(){
            mp.stop();
            mp.dispose();
        }

    }
    private ImageView formatImage( ImageView image){
        image.setFitHeight(70);
        image.setFitWidth(70);
        image.setEffect(null);
        return image;
    }
    private double totalValueInventory(){
        double total = 0;
        for(int i = 0; i<inventory.size(); i++) {
            if (inventory.get(i).getItemName().equals("Gucci Belt")) {
                total += 300;
            }
            if (inventory.get(i).getItemName().equals("Rolex")) {
                total += 1000;
            }
            if (inventory.get(i).getItemName().equals("Supreme Hoodie")) {
                total += 150;
            }
            if (inventory.get(i).getItemName().equals("Supreme Extinguisher")) {
                total += 150;
            }
            if (inventory.get(i).getItemName().equals("Clout Goggles")) {
                total += 100;
            }
            if (inventory.get(i).getItemName().equals("LV wallet")){
                total+=1000;
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
                }else if(root.getChildren().get(i).getId().contains("QuestReq")&&!updatedQuests){
                    root.getChildren().remove(i);
                    i--;
                }
            }
        }
        if(!alreadyDrawn&&activeQuest) {
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
        if(!updatedQuests) {
            for (int i = 0; i < q.getReqs().size(); i++) {
                CheckBox cb1 = new CheckBox();
                cb1.setId("QuestReq"+"qwxz"+"cb"+i);
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
            updatedQuests = true;
        }
    }
    private void removeQuest(){
        activeQuest = false;
        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null){
                if(root.getChildren().get(i).toString().contains("Text")||root.getChildren().get(i).toString().contains("CheckBox")){
                    if(root.getChildren().get(i).getId().contains("qwxz") && !root.getChildren().get(i).getId().contains("qwxz1")){
                        root.getChildren().remove(i);
                        i--;
                    }
                }
            }
        }
        Text t = new Text();
        t.setFont(new Font(14));
        t.setLayoutY(35);
        t.setLayoutX(675);
        if(questFor.equals("NinjaNQ")){
            t.setText("Return to Ninja");
            root.getChildren().add(t);
        }else if(questFor.equals("KnightNFQ")||questFor.equals("KnightNRQ")){
            t.setText("Return to Knight");
            root.getChildren().add(t);
        }else if(questFor.equals("NinjaFinale")){
            root.getChildren().clear();
            root.getChildren().add(endScreen);
        }
    }
    private void checkReqs(Quest q){
        switch (questFor) {
            case "KnightNRQ":
                for(int i = 0; i<inventory.size(); i++){
                    if(inventory.get(i).getItemName().equals("Rolex")){
                        q.completeReqAtPos(0);
                    }
                }
                if (moneyCount >= 100) {
                    q.completeReqAtPos(1);
                }
                if (buisnessOwner) {
                    q.completeReqAtPos(2);
                }
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).toString().contains("CheckBox")){
                        CheckBox cb = (CheckBox) root.getChildren().get(i);
                        if(cb.getId().contains("2")&&q.getReqAtPos(2)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("1")&&q.getReqAtPos(1)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("0")&&q.getReqAtPos(0)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                    }
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
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).toString().contains("CheckBox")){
                        CheckBox cb = (CheckBox) root.getChildren().get(i);
                        if(cb.getId().contains("2")&&q.getReqAtPos(2)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("1")&&q.getReqAtPos(1)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("0")&&q.getReqAtPos(0)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                    }
                }
                break;
            case "NinjaNQ":
                for(int i = 0; i<inventory.size(); i++){
                    if(inventory.get(i).getItemName().equals("Ninja\'s Weapon")){
                        q.completeReqAtPos(2);
                    }
                    if(inventory.get(i).getItemName().equals("Clout Goggles")){
                        q.completeReqAtPos(1);
                    }
                }
                if (moneyCount >= 50) {
                    q.completeReqAtPos(0);
                }
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).toString().contains("CheckBox")){
                        CheckBox cb = (CheckBox) root.getChildren().get(i);
                        if(cb.getId().contains("2")&&q.getReqAtPos(2)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("1")&&q.getReqAtPos(1)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("0")&&q.getReqAtPos(0)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                    }
                }
                break;
            case "NinjaFinale":
                System.out.println(buisnessOwner+", "+landLord+", "+(moneyCount>=10000));
                if(landLord){
                    q.completeReqAtPos(0);
                }else if(moneyCount>=10000){
                    q.completeReqAtPos(1);
                }else if(buisnessOwner){
                    q.completeReqAtPos(2);
                }for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).toString().contains("CheckBox")){
                        CheckBox cb = (CheckBox) root.getChildren().get(i);
                        if(cb.getId().contains("2")&&q.getReqAtPos(2)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("1")&&q.getReqAtPos(1)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("0")&&q.getReqAtPos(0)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                    }
                }
                if(moneyCount>=10000&landLord&&buisnessOwner){
                    end = true;
                    root.getChildren().clear();
                    root.getChildren().add(endScreen);
                }
                if(q.questComplete()){
                    end = true;
                    root.getChildren().clear();
                    root.getChildren().add(endScreen);
                }
                break;
            case "FindingNinja":
                if(foundNinja){
                    q.completeReqAtPos(0);
                }
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).toString().contains("CheckBox")){
                        CheckBox cb = (CheckBox) root.getChildren().get(i);
                        if(cb.getId().contains("0")&&q.getReqAtPos(0)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                    }
                }
                break;
            case "FriendlyQuest":
                boolean test1 = false, test2 = false;
                for(int i = 0; i<inventory.size(); i++){
                    if(inventory.get(i).getItemName().equals("Rolex")){
                        test1 = true;
                        q.completeReqAtPos(0);
                    }
                    if(inventory.get(i).getItemName().equals("Clout Goggles")){
                        test2 = true;
                        q.completeReqAtPos(1);
                    }
                }
                if(landLord){
                    q.completeReqAtPos(2);
                }
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).toString().contains("CheckBox")){
                        CheckBox cb = (CheckBox) root.getChildren().get(i);
                        if(cb.getId().contains("2")&&q.getReqAtPos(2)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("1")&&q.getReqAtPos(1)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                        if(cb.getId().contains("0")&&q.getReqAtPos(0)&&!cb.isSelected()){
                            updatedQuests = false;
                        }
                    }
                }
                if(landLord&&test1&&test2){
                    end = true;
                    root.getChildren().clear();
                    root.getChildren().add(endScreen);
                }
                if(q.questComplete()){
                    end = true;
                    root.getChildren().clear();
                    root.getChildren().add(endScreen);
                }
                break;
        }
    }
    private void moveOffScreen( String s){
        if(s.equals("Ninja")){
            ninja.setRotate(180);
            ninja.setLayoutX(ninja.getLayoutX()+1);
            ninjaHitbox.setLayoutX(ninjaHitbox.getLayoutX()+1);
        }
    }
    private void placeBrick(){
        brickSceneNumb = (int) (Math.random()*(activeScene.size()-1)+1);
        System.out.println("Scene: "+brickSceneNumb);
    }
    private boolean brickScene(){
        return currentScene == brickSceneNumb;
    }
    private void randomChanceEvent(){
        moneyCount+=(int)(Math.random()*200);
        cloutCount+=(int)(Math.random()*10);
    }
}