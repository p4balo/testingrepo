import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    public Runner(){
        root = new Pane();
        currentTransitionCount = 0;
        confirmed = -1;
    }
    public static void main(String[] args) {
        launch(args);
    }
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
        Polygon p1 = new Polygon();
        //use a point maker to get all the points
        p1.getPoints().addAll(

        );
        root.getChildren().add(p1);

        Text t1 = new Text("So the is the clout house");
        t1.setFont(new Font(24));
        t1.setLayoutX(100);
        t1.setLayoutY(50);
        root.getChildren().add(t1);

        Line l1 = new Line(50,100,100,150);
        l1.setStrokeWidth(5);
        l1.setStroke(Color.RED);
        root.getChildren().add(l1);

        Line l2 = new Line(100,150,110,130);
        l2.setStroke(Color.RED);
        l2.setStrokeWidth(5);
        root.getChildren().add(l2);

        Line l3 = new Line(100,150,80,150);
        l3.setStroke(Color.RED);
        l3.setStrokeWidth(5);
        root.getChildren().add(l3);

        root.getChildren().add(ObjectStorage.drawHouse(50,100));

        try { Thread.sleep(100); }
        catch (InterruptedException e) { e.printStackTrace(); }

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
}
