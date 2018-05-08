import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.util.Timer;
import java.util.TimerTask;

public class Runner extends Application {
    private Pane root;
    private int currentTransitionCount;
    private int difficultyLevel;
    private int confirmed;
    private int currentPosition;
    private int cloutCount;
    private Pane player;
    public Runner(){
        root = new Pane();
        currentTransitionCount = 0;
        confirmed = -1;
    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        root.getChildren().add(drawTitleScreen(primaryStage));

        primaryStage.setScene(new Scene(root, 800,600));
        primaryStage.setTitle("Game");
        primaryStage.show();
    }
    private Pane drawTitleScreen(Stage stage){
        Pane miniPane = new Pane();

        //clout goggles
        ImageView iv1 = new ImageView(null);

        //supreme
        ImageView iv2 = new ImageView(null);

        //gucci
        ImageView iv3 = new ImageView(null);

        //cog
        ImageView iv4 = new ImageView(null);

        Text t1 = new Text("CloutTycoon");
        t1.setFont(new Font(32));
        t1.setLayoutX(275);
        t1.setLayoutY(50+t1.getFont().getSize());
        miniPane.getChildren().add(t1);

        Label l1 = new Label("Character \n Name");
        TextField tf1 = new TextField();
        l1.setLabelFor(tf1);
        tf1.setLayoutX(300);
        tf1.setLayoutY(250);
        //import stylesheet and also get rid of all the sides of the box except bottom
        tf1.setStyle(null);
        l1.setLayoutX(220);
        l1.setLayoutY(250);
        miniPane.getChildren().addAll(l1, tf1);

        Button b1 = new Button();
        //setGraphic to cog iv4
        b1.setGraphic(null);
        b1.setLayoutX(15);
        b1.setLayoutY(550);
        b1.setOnAction(event -> drawOptionsMenu(stage));
        miniPane.getChildren().add(b1);

        Button b2 = new Button("Begin your Quest");
        b2.setLayoutX(250);
        b2.setLayoutY(300);
        b2.setOnAction(event -> {
            if(tf1.getText()!=null&&!(tf1.getText().equals(""))) {
                CharacterInformation.setCharacterName(tf1.getText());
                try { transitionScreen(currentTransitionCount); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
            else {
                miniPane.getChildren().add(sendError(0));
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for(int i = 0; i<miniPane.getChildren().size(); i++){
                            if (miniPane.getChildren().get(i).toString().contains("Pane")){
                                Node n = miniPane.getChildren().get(i);
                                Pane p = (Pane) n;
                                for(int f = 0; f<p.getChildren().size(); f++){
                                    if(p.getChildren().get(f).toString().contains("Text[")){
                                        Node n1 = p.getChildren().get(f);
                                        Text t = (Text) n1;
                                        t.setText("");
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        t.cancel();
                    }
                },5000);
            }
        });
        miniPane.getChildren().add(b2);

        miniPane.setOnKeyPressed(event -> { if(event.getCode()==KeyCode.ESCAPE){ drawOptionsMenu(stage); } });
        return miniPane;
    }
    private void transitionScreen(int screenNumb) throws InterruptedException {
        if(screenNumb==0){
            final double[] fill = {0};
//            for(int i = 0; i<root.getChildren().size(); i++){
//                if(root.getChildren().get(i).toString().contains("Pane")){
//                    root.getChildren().remove(i);
//                    break;
//                }
//            }
            Rectangle r = new Rectangle(800,600);
            r.setFill(Color.BLACK);
            root.getChildren().add(r);
//            new Thread(() -> {
//                while(fill[0]<=.99){
//                    fill[0] += 0.001;
//                    r.setFill(new Color(0,0,0,fill[0]));
//                    System.out.println("lol");
//                    try { Thread.sleep(5); }
//                    catch (InterruptedException e) { e.printStackTrace(); }
//                }
//                for(int i = 0; i<root.getChildren().size(); i++){
//                    if(root.getChildren().get(i).toString().contains("Pane")){
//                        root.getChildren().remove(i);
//                        break;
//                    }
//                }
//            }).run();
//            r.setFill(Color.BLACK);
            Thread.sleep(50);
            Text t1 = new Text("Hello, "+CharacterInformation.getCharacterName()+", you are about to start your quest to become" +
                    " the biggest hyperbeast in the world the only problem is that you just realized it is your destiny. So, I Jesus will" +
                    " train you");
            t1.setWrappingWidth(575);
            t1.setLayoutY(75);
            t1.setLayoutX(150);
            t1.setFont(new Font(32));
            t1.setStroke(Color.WHITE);
            root.getChildren().add(t1);
            Thread.sleep(1000);

            Text t2 = new Text("Choose your mf difficulty");
            t2.setLayoutY(340);
            t2.setLayoutX(75);
            t2.setFont(new Font(28));
            t2.setStroke(Color.WHITE);
            root.getChildren().add(t2);


            //Stylize Buttons
            Button b1 = new Button("Yeezy");
            b1.setLayoutY(380);
            b1.setLayoutX(150);
            b1.setStyle(null);
            b1.setFocusTraversable(false);
            b1.setTextFill(Color.GREEN);
            b1.setOnAction(event -> {
                difficultyLevel = 0;
                currentTransitionCount++;
                try { transitionScreen(currentTransitionCount); }
                catch (InterruptedException e) { e.printStackTrace(); }
            });
            root.getChildren().add(b1);

            Button b2 = new Button("Litty");
            b2.setLayoutY(420);
            b2.setLayoutX(150);
            b2.setStyle(null);
            b2.setFocusTraversable(false);
            b2.setTextFill(Color.YELLOW);
            b2.setOnAction(event -> {
                difficultyLevel = 1;
                root.getChildren().clear();
                currentTransitionCount++;
                try { transitionScreen(currentTransitionCount); }
                catch (InterruptedException e) { e.printStackTrace(); }
            });
            root.getChildren().add(b2);

            Button b3 = new Button("Yikes");
            b3.setLayoutY(460);
            b3.setLayoutX(150);
            b3.setStyle(null);
            b3.setFocusTraversable(false);
            b3.setTextFill(Color.RED);
            b3.setOnAction(event -> {
                difficultyLevel = 2;
                root.getChildren().clear();
                currentTransitionCount++;
                try { transitionScreen(currentTransitionCount); }
                catch (InterruptedException e) { e.printStackTrace(); }
            });
            root.getChildren().add(b3);

            Button b4 = new Button("Real S**t");
            b4.setLayoutY(500);
            b4.setLayoutX(150);
            b4.setStyle(null);
            b4.setFocusTraversable(false);
            b4.setTextFill(Color.DARKRED);
            b4.setOnAction(event -> root.getChildren().add(sendError(1)));
            root.getChildren().add(b4);
        }else if(screenNumb==1){
            Rectangle r = new Rectangle(800,600,Color.BLACK);
            root.getChildren().add(r);
            if(difficultyLevel==0){
                Text t1 = new Text("I gachu just tryna have a ez time");
                t1.setLayoutX(75);
                t1.setLayoutY(150);
                t1.setFont(new Font(32));
                t1.setStroke(Color.WHITE);

                Text t2 = new Text("Ight so you tryna do or skip the tutorial");
                t2.setLayoutX(50);
                t2.setLayoutY(250);
                t2.setFont(new Font(28));
                t2.setStroke(Color.WHITE);

                Button b1 = new Button("Esskeetit");
                b1.setLayoutX(100);
                b1.setLayoutY(280);
                b1.setOnAction(event -> {
                    initTutorial();
                });

                Button b2 = new Button("Gettem outta here");
                b2.setLayoutX(230);
                b2.setLayoutY(280);
                b2.setOnAction(event -> {
                    startGame();
                });
                root.getChildren().addAll(t1,t2,b1,b2);
            }else if(difficultyLevel==1){
                Text t1 = new Text("Not to easy, not too hard just how I like it");
                t1.setLayoutX(75);
                t1.setLayoutY(150);
                t1.setFont(new Font(32));
                t1.setStroke(Color.WHITE);

                Text t2 = new Text("Ight so you tryna do or skip the tutorial");
                t2.setLayoutX(50);
                t2.setLayoutY(250);
                t2.setFont(new Font(28));
                t2.setStroke(Color.WHITE);

                Button b1 = new Button("Esskeetit");
                b1.setLayoutX(100);
                b1.setLayoutY(280);
                b1.setOnAction(event -> {
                    initTutorial();
                });

                Button b2 = new Button("Gettem outta here");
                b2.setLayoutX(230);
                b2.setLayoutY(280);
                b2.setOnAction(event -> {
                    startGame();
                });
                root.getChildren().addAll(t1,t2,b1,b2);
            }else if(difficultyLevel==2){
                Text t1 = new Text("I appreciate the hustle, true hypebeast");
                t1.setLayoutY(150);
                t1.setFont(new Font(32));
                t1.setStroke(Color.WHITE);

                Text t2 = new Text("Ight so you tryna do or skip the tutorial");
                t2.setLayoutX(50);
                t2.setLayoutY(250);
                t2.setFont(new Font(28));
                t2.setStroke(Color.WHITE);

                Button b1 = new Button("Esskeetit");
                b1.setLayoutX(100);
                b1.setLayoutY(280);
                b1.setOnAction(event -> {
                    initTutorial();
                });

                Button b2 = new Button("Gettem outta here");
                b2.setLayoutX(230);
                b2.setLayoutY(280);
                b2.setOnAction(event -> {
                    startGame();
                });
                root.getChildren().addAll(t1,t2,b1,b2);
            }else{
                Text t1 = new Text("Are you tryna be homeless what are u doing here");
                t1.setLayoutX(75);
                t1.setLayoutY(150);
                t1.setFont(new Font(32));
                t1.setStroke(Color.WHITE);

                Text t2 = new Text("Ight so you tryna do or skip the tutorial");
                t2.setLayoutX(50);
                t2.setLayoutY(250);
                t2.setFont(new Font(28));
                t2.setStroke(Color.WHITE);

                Button b1 = new Button("Esskeetit");
                b1.setLayoutX(100);
                b1.setLayoutY(280);
                b1.setOnAction(event -> {
                    initTutorial();
                });

                Button b2 = new Button("Gettem outta here");
                b2.setLayoutX(230);
                b2.setLayoutY(280);
                b2.setOnAction(event -> {
                    startGame();
                });
                root.getChildren().addAll(t1,t2,b1,b2);
            }
        }
    }
    private void initTutorial(){
        root.getChildren().clear();
        root.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.Q){
                currentPosition++;
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
                        if(root.getChildren().get(i).getId().contains("line")){
                            root.getChildren().remove(i);
                            i--;
                        }
                    }
                }
                drawPFP();
                drawMoney(10.99);
                drawClout(10);
                drawArrow(0);
            }
            if(currentPosition==5){
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).getId()!=null){
                        if (root.getChildren().get(i).getId().contains("editableText")) {
                            Node n = root.getChildren().get(i);
                            Text t = (Text) n;
                            t.setText("That reminds me, if forgot how you look lemme set you up");
                        }
                        if(root.getChildren().get(i).getId().contains("line")){
                            root.getChildren().remove(i);
                            i--;
                        }
                    }
                }
                drawArrow(1);
            }if(currentPosition==6){
                for(int i = 0; i<root.getChildren().size(); i++){
                    if(root.getChildren().get(i).getId()!=null){
                        if (root.getChildren().get(i).getId().contains("editableText")) {
                            Node n = root.getChildren().get(i);
                            Text t = (Text) n;
                            t.setText("Looks like you are all set, press ESC for help at anytime");
                        }
                        if(root.getChildren().get(i).getId().contains("line")){
                            root.getChildren().remove(i);
                            i--;
                        }
                    }
                }
            }
        });
        Rectangle r3 = new Rectangle(0,0,800,300);
        r3.setStroke(Color.SEAGREEN);
        r3.setFill(Color.SEAGREEN);
        root.getChildren().add(r3);

        Rectangle r1 = new Rectangle(98,15,310,90);
        r1.setStroke(Color.BLACK);
        r1.setStrokeWidth(3);
        r1.setFill(Color.WHITESMOKE);
        root.getChildren().add(r1);

        Text t1 = new Text("So this is the clout house \n Press Q to continue");
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

        root.getChildren().add(ObjectStorage.drawHouse(50,100));
        root.getChildren().add(ObjectStorage.drawHouse(250,100));
        root.getChildren().add(ObjectStorage.drawHouse(450,100));
        root.getChildren().add(ObjectStorage.drawHouse(650,100));
    }
    private void startGame(){

    }
    private void drawOptionsMenu(Stage stage){
        Popup p = new Popup();
        p.setX(500);
        p.setY(130);
        Pane root = new Pane();
        root.setPadding(new Insets(10, 0, 0, 10));
        root.setMaxSize(350,500);

        Rectangle r = new Rectangle(350,500);
        r.setStroke(Color.DARKGRAY);
        r.setStrokeWidth(3);
        r.setFill(Color.WHITESMOKE);
        root.getChildren().add(r);

        Text t1 = new Text("Options");
        t1.setLayoutX(120);
        t1.setLayoutY(40);
        t1.setFont(new Font(32));
        root.getChildren().add(t1);

        p.getContent().add(root);
        p.show(stage);
    }
    @NotNull
    private Pane sendError(int errorType){
        Pane errorContainer = new Pane();
        if(errorType==0){
            Text t = new Text("Enter in A Character Name");
            t.setFont(new Font(15));
            t.setLayoutX(230);
            t.setLayoutY(400);
            t.setStroke(Color.RED);
            errorContainer.getChildren().add(t);

            return errorContainer;
        }
        if(errorType==1){
            Text t = new Text("You Sure About that");
            t.setLayoutX(400);
            t.setLayoutY(390);
            t.setFont(new Font(24));
            t.setStroke(Color.WHITE);
            errorContainer.getChildren().add(t);

            //John Cena pic
            ImageView iv1 = new ImageView(null);

            Button b1 = new Button("Yea I'm gucci");
            b1.setLayoutX(390);
            b1.setLayoutY(400);
            b1.setOnAction(event -> {
                difficultyLevel = 3;
                currentTransitionCount++;
                root.getChildren().clear();
                try { transitionScreen(currentTransitionCount); }
                catch (InterruptedException e) { e.printStackTrace(); }
            });
            errorContainer.getChildren().add(b1);

            Button b2 = new Button("Naw I'm out");
            b2.setLayoutX(530);
            b2.setLayoutY(400);
            b2.setOnAction(event -> {
                errorContainer.getChildren().clear();
                confirmed = 0;
            });
            errorContainer.getChildren().add(b2);

            return errorContainer;
        }
        return null;
    }
    private void drawMoney(double moneyCount){
        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null) {
                if (root.getChildren().get(i).getId().equals("Money")) {
                    root.getChildren().remove(i);
                }
            }
        }
        Text t = new Text("$"+(int)moneyCount);
        t.setFont(new Font(12));
        t.setLayoutY(145);
        t.setLayoutX(753);
        t.setStroke(Color.GOLDENROD);
        t.setId("Money");
        root.getChildren().add(t);
    }
    private void drawClout(int cloutCount){
        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null) {
                if (root.getChildren().get(i).getId().equals("Clout")) {
                    root.getChildren().remove(i);
                }
            }
        }
        Text t = new Text(cloutCount+"C");
        t.setFont(new Font(20));
        t.setLayoutY(105);
        t.setLayoutX(753);
        t.setStroke(Color.GOLDENROD);
        t.setId("Clout");
        root.getChildren().add(t);
    }
    private void drawPFP(){
        Rectangle r1 = new Rectangle(725,0,75,75);
        r1.setStroke(Color.BLACK);
        r1.setStrokeWidth(3);
        r1.setFill(Color.TRANSPARENT);
        r1.setOnMousePressed(event -> {
            drawPFPselector();
        });
        root.getChildren().add(r1);
        Rectangle r2 = new Rectangle(750, 75, 50,150);
        r2.setStroke(Color.BLACK);
        r2.setStrokeWidth(3);
        r2.setFill(Color.WHITE);
        root.getChildren().add(r2);
    }
    private void drawPFPselector(){
        Pane p = new Pane();
        p.setId("Popup");
        p.setLayoutX(225);
        p.setLayoutY(15);
        Pane root = new Pane();
        root.setPadding(new Insets(10,0,0,10));

        Rectangle r1 = new Rectangle(300,550);
        r1.setFill(Color.WHITE);
        r1.setStrokeWidth(3);
        r1.setStroke(Color.BLACK);
        root.getChildren().add(r1);

        Text t1 = new Text("Profile Selector");
        t1.setStroke(Color.BLACK);
        t1.setLayoutX(15);
        t1.setFont(new Font(24));
        t1.setLayoutY(39);
        root.getChildren().add(t1);

        Text t3 = new Text("Race");
        t3.setFont(new Font(18));
        t3.setLayoutY(68);
        t3.setLayoutX(15);
        root.getChildren().add(t3);

        ToggleGroup tg1 = new ToggleGroup();

        RadioButton rb1 = new RadioButton("White");
        rb1.setLayoutX(15);
        rb1.setLayoutY(70);
        rb1.setFont(new Font(15));
        rb1.setToggleGroup(tg1);
        root.getChildren().add(rb1);

        RadioButton rb2 = new RadioButton("Black");
        rb2.setLayoutX(95);
        rb2.setLayoutY(70);
        rb2.setFont(new Font(15));
        rb2.setToggleGroup(tg1);
        root.getChildren().add(rb2);

        RadioButton rb3 = new RadioButton("Asian");
        rb3.setLayoutX(15);
        rb3.setLayoutY(100);
        rb3.setFont(new Font(15));
        rb3.setToggleGroup(tg1);
        root.getChildren().add(rb3);

        RadioButton rb4 = new RadioButton("Latino");
        rb4.setLayoutX(95);
        rb4.setLayoutY(100);
        rb4.setFont(new Font(15));
        rb4.setToggleGroup(tg1);
        root.getChildren().add(rb4);

        Text t2 = new Text("Hair Color");
        t2.setLayoutX(15);
        t2.setLayoutY(148);
        t2.setFont(new Font(18));
        root.getChildren().add(t2);

        ToggleGroup tg2 = new ToggleGroup();

        RadioButton rb5 = new RadioButton("Blond");
        rb5.setLayoutX(15);
        rb5.setLayoutY(150);
        rb5.setFont(new Font(15));
        rb5.setToggleGroup(tg2);
        root.getChildren().add(rb5);

        RadioButton rb6 = new RadioButton("RedHead");
        rb6.setLayoutX(95);
        rb6.setLayoutY(150);
        rb6.setFont(new Font(15));
        rb6.setToggleGroup(tg2);
        root.getChildren().add(rb6);

        RadioButton rb7 = new RadioButton("Brunette");
        rb7.setLayoutX(95);
        rb7.setLayoutY(180);
        rb7.setFont(new Font(15));
        rb7.setToggleGroup(tg2);
        root.getChildren().add(rb7);

        Text t4 = new Text("Character Strength");
        t4.setLayoutX(15);
        t4.setLayoutY(215);
        t4.setFont(new Font(18));
        root.getChildren().add(t4);

        ToggleGroup tg3 = new ToggleGroup();

        RadioButton rb8 = new RadioButton("Smart");
        rb8.setLayoutX(15);
        rb8.setLayoutY(217);
        rb8.setFont(new Font(15));
        rb8.setToggleGroup(tg3);
        root.getChildren().add(rb8);

        RadioButton rb9 = new RadioButton("Thrifty");
        rb9.setLayoutX(95);
        rb9.setLayoutY(217);
        rb9.setFont(new Font(15));
        rb9.setToggleGroup(tg3);
        root.getChildren().add(rb9);

        RadioButton rb10 = new RadioButton("Strong");
        rb10.setLayoutX(95);
        rb10.setLayoutY(245);
        rb10.setFont(new Font(15));
        rb10.setToggleGroup(tg3);
        root.getChildren().add(rb10);

        Text t5 = new Text("Gender");
        t5.setLayoutX(15);
        t5.setLayoutY(260);
        t5.setFont(new Font(18));
        root.getChildren().add(t5);

        ToggleGroup tg4 = new ToggleGroup();

        RadioButton rb11 = new RadioButton("Boy");
        rb11.setLayoutX(15);
        rb11.setLayoutY(263);
        rb11.setFont(new Font(15));
        rb11.setToggleGroup(tg4);
        root.getChildren().add(rb11);

        RadioButton rb12 = new RadioButton("Girl");
        rb12.setLayoutX(95);
        rb12.setLayoutY(263);
        rb12.setFont(new Font(15));
        rb12.setToggleGroup(tg4);
        root.getChildren().add(rb12);


        Rectangle r2 = new Rectangle(15,280,75,50);
        r2.setStroke(Color.BLACK);
        r2.setStrokeWidth(3);
        r2.setFill(Color.TRANSPARENT);
        root.getChildren().add(r2);

        //get Photoshop and make some images
        javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView();
        iv.setFitHeight(10);
        iv.setFitWidth(10);
//        iv.setLayoutX();
//        iv.setLayoutY();
        root.getChildren().add(iv);

        Button b1  = new Button("Set Character");
        b1.setOnAction(event -> {
            if(tg1.getSelectedToggle().toString().contains("White")){
                CharacterInformation.setCharacterRace("white");
            }else if(tg1.getSelectedToggle().toString().contains("Black")){
                CharacterInformation.setCharacterRace("black");
            }else if(tg1.getSelectedToggle().toString().contains("Asian")){
                CharacterInformation.setCharacterRace("asian");
            }else if(tg1.getSelectedToggle().toString().contains("Latino")){
                CharacterInformation.setCharacterRace("latino");
            }else{
                CharacterInformation.setCharacterRace(null);
            }


            if(tg2.getSelectedToggle().toString().contains("Brunette")){
                CharacterInformation.setCharacterHair("brunette");
            }else if(tg2.getSelectedToggle().toString().contains("RedHead")){
                CharacterInformation.setCharacterHair("red head");
            }else if(tg2.getSelectedToggle().toString().contains("Blond")){
                CharacterInformation.setCharacterHair("blond");
            }else{
                CharacterInformation.setCharacterHair(null);
            }

            if(tg3.getSelectedToggle().toString().contains("Smart")){
                CharacterInformation.setCharacterStrength("smart");
            }else if(tg3.getSelectedToggle().toString().contains("Thrifty")){
                CharacterInformation.setCharacterStrength("thrifty");
            }else if(tg3.getSelectedToggle().toString().contains("Strong")){
                CharacterInformation.setCharacterStrength("strong");
            }else{
                CharacterInformation.setCharacterStrength(null);
            }

            if(tg4.getSelectedToggle().toString().contains("Boy")){
                CharacterInformation.setCharacterGender("boy");
            }else if(tg4.getSelectedToggle().toString().contains("Girl")){
                CharacterInformation.setCharacterGender("girl");
            }else{
                CharacterInformation.setCharacterGender(null);
            }

            for(int i = 0; i<this.root.getChildren().size(); i++){
                if(this.root.getChildren().get(i).getId()!=null) {
                    if (this.root.getChildren().get(i).getId().contains("Popup")) {
                        this.root.getChildren().remove(i);
                        i--;
                    }
                }
            }

            drawCharacter(CharacterInformation.getCharacterRace(), CharacterInformation.getCharacterGender(), CharacterInformation.getCharacterHair());
            currentPosition++;
            System.out.println(CharacterInformation.getCharacterRace()+", "+CharacterInformation.getCharacterHair()+", "+CharacterInformation.getCharacterStrength()+", "+CharacterInformation.getCharacterGender());
        });
        b1.setLayoutY(400);
        b1.setLayoutX(15);
        root.getChildren().add(b1);


        p.getChildren().add(root);
        this.root.getChildren().add(p);
    }
    @NotNull
    private void drawCharacter(String race, String gender, String hair){
        player = new Pane();
        player.setId("Character");
        player.setLayoutX(150);
        player.setLayoutY(290);

        if(race.contains("white")){
            Circle c1 = new Circle(15);
            c1.setFill(Color.FLORALWHITE);
            c1.setStroke(Color.BLACK);
            c1.setStrokeWidth(2);
            player.getChildren().add(c1);

            Line l1 = new Line(0,15,0,45);
            l1.setStrokeWidth(2);
            l1.setStroke(Color.BLACK);
            player.getChildren().add(l1);

            Line l2 = new Line(0,45,10,60);
            l2.setStrokeWidth(2);
            l2.setStroke(Color.BLACK);
            player.getChildren().add(l2);

            Line l3 = new Line(0,45,-10,60);
            l3.setStrokeWidth(2);
            l3.setStroke(Color.BLACK);
            player.getChildren().add(l3);

            Polygon py1 = new Polygon(
                    136.0-150, 284.0-290,
                    136.0-150, 268.0-290,
                    145.0-150, 271.0-290,
                    137.0-150, 285.0-290,
                    139.0-150, 273.0-290,
                    144.0-150, 273.0-290,
                    150.0-150, 273.0-290,
                    156.0-150, 273.0-290,
                    162.0-150, 273.0-290,
                    162.0-150, 282.0-290,
                    156.0-150, 280.0-290,
                    150.0-150, 278.0-290,
                    142.0-150, 283.0-290
            );
            if(hair.contains("brunette")){
                py1.setStroke(Color.ROSYBROWN);
                py1.setFill(Color.ROSYBROWN);
            }else if(hair.contains("blond")){
                py1.setStroke(Color.GOLD);
                py1.setFill(Color.GOLD);
            }else{
                py1.setStroke(Color.INDIANRED);
                py1.setFill(Color.INDIANRED);
            }
            player.getChildren().add(py1);
        }
        root.getChildren().add(player);

        root.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.D){
                player.setLayoutX(player.getLayoutX()+3);
            }
            if(event.getCode()==KeyCode.D){
                player.setLayoutX(player.getLayoutX()+3);
            }
            if(event.getCode()==KeyCode.D){
                player.setLayoutX(player.getLayoutX()+3);
            }
            if(event.getCode()==KeyCode.D){
                player.setLayoutX(player.getLayoutX()+3);
            }
        });
    }
    private void drawArrow(int arrowNum) {
        if (arrowNum == 0) {
            Line l1 = new Line(675, 105, 735, 105);
            l1.setStroke(Color.RED);
            l1.setStrokeWidth(3.5);
            l1.setId("line");
            root.getChildren().add(l1);
            Line l2 = new Line(735, 105, 715, 90);
            l2.setStroke(Color.RED);
            l2.setStrokeWidth(3.5);
            l2.setId("line");
            root.getChildren().add(l2);
            Line l3 = new Line(735, 105, 715, 120);
            l3.setStroke(Color.RED);
            l3.setStrokeWidth(3.5);
            l3.setId("line");
            root.getChildren().add(l3);
        } else if (arrowNum == 1) {
            Line l1 = new Line(625, 35, 700, 35);
            l1.setStroke(Color.RED);
            l1.setStrokeWidth(3.5);
            l1.setId("line");
            root.getChildren().add(l1);
            Line l2 = new Line(700, 35, 685, 15);
            l2.setStroke(Color.RED);
            l2.setStrokeWidth(3.5);
            l2.setId("line");
            root.getChildren().add(l2);
            Line l3 = new Line(700, 35, 685, 55);
            l3.setStroke(Color.RED);
            l3.setStrokeWidth(3.5);
            l3.setId("line");
            root.getChildren().add(l3);
        }
    }
}
