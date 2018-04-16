import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.css.Rect;

import java.awt.event.KeyEvent;
import java.beans.EventHandler;
import java.io.*;
import java.util.ArrayList;


public class passwordGenerator extends Application {
    private Pane root = new Pane();
    private String[] basicCharList;
    private String[] specialCharsList;
    private String[] capitalCharsList;
    private String[] numberCharList;
    private boolean repeatingChars;
    private boolean containsSpecialChars;
    private boolean containsCapitalChars;
    private boolean containsNumberChars;
    private TableView<PasswordData> table = new TableView<>();
    private final ObservableList<PasswordData> data =
            FXCollections.observableArrayList();

    public passwordGenerator(){
        basicCharList = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        specialCharsList = new String[]{"!","@","#","$","%","^","&","*","(",")","_","-","+","=","{","}","[","]","\\","|",";",":","\'","\"","<",">",",",".","/","?","~","`"};
        capitalCharsList = new String[basicCharList.length];
        for(int i = 0; i<basicCharList.length; i++){
            capitalCharsList[i] = basicCharList[i].toUpperCase();
        }
        numberCharList = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    }
    public static void main(String[] args) { launch(args); }
    public void start(Stage primaryStage) {
        Rectangle r1 = new Rectangle(20,20,760,560);
        r1.setStrokeWidth(3);
        r1.setFill(Color.TRANSPARENT);
        r1.setStroke(Color.DIMGRAY);
        root.getChildren().addAll(r1);

        TextField tf1 =  new TextField("8");
        Label l1 = new Label("Amount of \nCharacters");
        tf1.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 3 ? change : null));
        tf1.setLayoutY(45);
        tf1.setLayoutX(105);
        tf1.setMaxWidth(50);
        l1.setLabelFor(tf1);
        l1.setLayoutX(30);
        l1.setLayoutY(45);
        l1.setFont(new Font(12));
        root.getChildren().addAll(tf1, l1);

        TextField tf2 =  new TextField();
        Label l5 = new Label("Password For");
        tf2.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 32 ? change : null));
        tf2.setLayoutY(295);
        tf2.setLayoutX(115);
        tf2.setMaxWidth(150);
        l5.setLabelFor(tf1);
        l5.setLayoutX(30);
        l5.setLayoutY(295);
        l5.setFont(new Font(12));
        root.getChildren().addAll(tf2, l5);

        CheckBox cb1 = new CheckBox();
        Label l2 = new Label("Contains Special \nCharacters");
        cb1.setLayoutX(135);
        cb1.setLayoutY(95);
        l2.setLayoutY(95);
        l2.setLayoutX(30);
        l2.setFont(new Font(12));
        l2.setLabelFor(cb1);
        root.getChildren().addAll(cb1, l2);

        CheckBox cb2 = new CheckBox();
        Label l3 = new Label("Contains Capital \nLetters");
        cb2.setLayoutX(135);
        cb2.setLayoutY(145);
        l3.setLayoutY(145);
        l3.setLayoutX(30);
        l3.setFont(new Font(12));
        l3.setLabelFor(cb1);
        root.getChildren().addAll(cb2, l3);

        CheckBox cb3 = new CheckBox();
        Label l4 = new Label("Repeating \nCharacters");
        cb3.fire();
        cb3.setLayoutX(135);
        cb3.setLayoutY(245);
        l4.setLayoutY(245);
        l4.setLayoutX(30);
        l4.setFont(new Font(12));
        l4.setLabelFor(cb1);
        root.getChildren().addAll(cb3, l4);

        CheckBox cb4 = new CheckBox();
        Label l7 = new Label("Contains \nNumbers");
        cb4.setLayoutX(135);
        cb4.setLayoutY(195);
        l7.setLayoutY(195);
        l7.setLayoutX(30);
        l7.setFont(new Font(12));
        l7.setLabelFor(cb1);
        root.getChildren().addAll(cb4, l7);

        Label l6 = new Label("Password: ");
        Label l8 = new Label("For: ");
        TextArea ta1 = new TextArea();
        Text t1 = new Text();
        l6.setLabelFor(ta1);
        l6.setLayoutX(200);
        l6.setLayoutY(45);
        l8.setLabelFor(t1);
        l8.setLayoutX(250);
        l8.setLayoutY(125);
        l6.setFont(new Font(18));
        l8.setFont(new Font(15));
        ta1.setFont(new Font(18));
        t1.setFont(new Font(15));
        t1.setLayoutY(140);
        t1.setLayoutX(300);
        ta1.setLayoutX(300);
        ta1.setLayoutY(45);
        ta1.setMaxSize(250,1);
        ta1.setEditable(false);
        root.getChildren().addAll(l6,ta1,l8,t1);


        Button b1 = new Button("Generate Password");
        b1.setLayoutX(45);
        b1.setLayoutY(345);
        b1.setOnAction(event -> {
            boolean proceed = true;
            int checker = 0;
            for(int i = 0; i<tf1.getText().length(); i++){
                for(int f = 0; f<numberCharList.length; f++) {
                    if (tf1.getText().charAt(i) != numberCharList[f].charAt(0)){
                        checker++;
                    }
                }
            }
            if(checker%10==0){
                proceed = false;
            }
            if(proceed) {
                int numberOfChoices = 0;
                if (cb1.isSelected()) {
                    numberOfChoices++;
                    containsSpecialChars = true;

                }
                if (cb2.isSelected()) {
                    numberOfChoices++;
                    containsCapitalChars = true;

                }
                if (cb3.isSelected()) { repeatingChars = true; }
                if (cb4.isSelected()) {
                    numberOfChoices++;
                    containsNumberChars = true;
                }
                if (containsNumberChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 0) {
                            t.append(basicCharList[(int) (Math.random() * basicCharList.length)]);
                        }
                        if (randSelector == 1) {
                            t.append(numberCharList[(int) (Math.random() * numberCharList.length)]);
                        }
                    }
                    ta1.setText(t.toString());
                }
                if (containsCapitalChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) {
                            t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]);
                        }
                        if (randSelector == 0) {
                            t.append(basicCharList[(int) (Math.random() * basicCharList.length)]);
                        }
                    }
                    ta1.setText(t.toString());
                }
                if (containsSpecialChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) {
                            t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]);
                        }
                        if (randSelector == 0) {
                            String c = basicCharList[(int) (Math.random() * basicCharList.length)];
                            t.append(c);
                        }
                    }
                    ta1.setText(t.toString());
                }
                if (containsNumberChars && containsCapitalChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) {
                            t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]);
                        }
                        if (randSelector == 0) {
                            t.append(basicCharList[(int) (Math.random() * basicCharList.length)]);
                        }
                        if (randSelector == 2) {
                            t.append(numberCharList[(int) (Math.random() * numberCharList.length)]);
                        }
                    }
                    ta1.setText(t.toString());
                }
                if (containsNumberChars && containsSpecialChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) {
                            t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]);
                        }
                        if (randSelector == 2) {
                            t.append(numberCharList[(int) (Math.random() * numberCharList.length)]);
                        }
                        if (randSelector == 0) {
                            t.append(basicCharList[(int) (Math.random() * basicCharList.length)]);
                        }
                    }
                    ta1.setText(t.toString());
                }
                if (containsCapitalChars && containsSpecialChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 0) {
                            t.append(basicCharList[(int) (Math.random() * basicCharList.length)]);
                        }
                        if (randSelector == 1) {
                            t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]);
                        }
                        if (randSelector == 2) {
                            t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]);
                        }
                    }
                    ta1.setText(t.toString());
                }
                if (containsSpecialChars && containsCapitalChars && containsNumberChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 0) {
                            t.append(basicCharList[(int) (Math.random() * basicCharList.length)]);
                        }
                        if (randSelector == 1) {
                            t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]);
                        }
                        if (randSelector == 2) {
                            t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]);
                        }
                        if (randSelector == 3) {
                            t.append(numberCharList[(int) (Math.random() * numberCharList.length)]);
                        }
                    }
                    ta1.setText(t.toString());
                }
                if (!containsNumberChars && !containsCapitalChars && !containsSpecialChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        t.append(basicCharList[(int) (Math.random() * basicCharList.length)]);
                    }
                    ta1.setText(t.toString());
                }
                t1.setText(tf2.getText());
                containsSpecialChars = false;
                containsNumberChars = false;
                containsCapitalChars = false;
            }
        });
        root.getChildren().addAll(b1);

        table.setEditable(true);
        //table.setMaxSize();

        TableColumn firstNameCol = new TableColumn("What For");
        firstNameCol.setMinWidth(125);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<PasswordData, String>("whatFor"));

        TableColumn lastNameCol = new TableColumn("Password");
        lastNameCol.setMinWidth(150);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<PasswordData, String>("password"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);
        vbox.setLayoutX(475);
        vbox.setLayoutY(160);
        root.getChildren().addAll(vbox);

        Button b3 = new Button("Save Password");
        b3.setLayoutX(45);
        b3.setLayoutY(395);
        b3.setOnAction(event ->{
            data.addAll(new PasswordData(t1.getText(),ta1.getText()));
            writeToJson();
        });
        root.getChildren().addAll(b3);

        Button b4 = new Button("Load Saved Passwords");
        b4.setLayoutX(45);
        b4.setLayoutY(445);
        b3.setId("TempButton");
        b4.setOnAction(event -> signInPopup("1Apollorow", primaryStage));
        root.getChildren().add(b4);

        primaryStage.setTitle("Password Generator");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }
    private void signInPopup(String correctPassword, Stage stage){
        Popup p = new Popup();
        p.setX(500);
        p.setY(200);
        Pane box = new Pane();
        box.setPadding(new Insets(10, 0, 0, 10));
        box.setMaxSize(300,200);

        Rectangle r = new Rectangle(300,200);
        r.setStroke(Color.DARKGRAY);
        r.setStrokeWidth(3);
        r.setFill(Color.WHITESMOKE);
        box.getChildren().add(r);

        Text t = new Text("Enter in Master Key");
        t.setLayoutY(15);
        t.setLayoutX(15);
        t.setFont(new Font(20));
        box.getChildren().add(t);

        PasswordField tf = new PasswordField();
        tf.setLayoutX(15);
        tf.setLayoutY(45);
        tf.setMaxWidth(175);
        box.getChildren().add(tf);

        Button submit = new Button("Enter");
        submit.setLayoutY(85);
        submit.setLayoutX(15);
        submit.setOnAction(event -> {
            System.out.println(event.getEventType());
            if(tf.getText().equals(correctPassword)){
                readJSON(data);
                p.hide();
            }else{
                Text t1 = new Text("Incorrect Password");
                t1.setFont(new Font(12));
                t1.setStroke(Color.RED);
                t1.setLayoutX(10);
                t1.setLayoutY(150);
                box.getChildren().add(t1);

                Button close = new Button("Close");
                close.setLayoutX(85);
                close.setLayoutY(85);
                close.setOnAction(event1 -> p.hide());
                box.getChildren().add(close);
            }
        });
        tf.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ENTER){
                if(tf.getText().equals(correctPassword)){
                    p.hide();
                    readJSON(data);
                }else{
                    Text t1 = new Text("Incorrect Password");
                    t1.setFont(new Font(12));
                    t1.setStroke(Color.RED);
                    t1.setLayoutY(150);
                    t1.setLayoutX(10);
                    box.getChildren().add(t1);

                    Button close = new Button("Close");
                    close.setLayoutX(85);
                    close.setLayoutY(85);
                    close.setOnAction(event1 -> p.hide());
                    box.getChildren().add(close);
                }
            }
        });
        box.getChildren().addAll(submit);
        p.getContent().add(box);
        p.show(stage);

    }
    public static class PasswordData {

        private final SimpleStringProperty whatFor;
        private final SimpleStringProperty password;

        private PasswordData(String whatFor, String password) {
            this.whatFor = new SimpleStringProperty(whatFor);
            this.password = new SimpleStringProperty(password);
        }
        public String getWhatFor() { return whatFor.get(); }
        public void setWhatFor(String fName) { whatFor.set(fName); }
        public String getPassword() { return password.get(); }
        public void setPassword(String fName) { password.set(fName); }
    }
    private void writeToJson(){
        JSONArray totaldata = new JSONArray();
        for(int i = 0; i<data.size(); i++){
            JSONArray array = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("For",data.get(i).getWhatFor());
            array.put(obj);
            JSONObject obj1 = new JSONObject();
            obj.put("Password",data.get(i).getPassword());
            array.put(obj1);
            totaldata.put(array);
        }
        try {
            String path = "data.json";
            FileWriter file = new FileWriter(path);
            String json = totaldata.toString();
            file.write(json);
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void readJSON(ObservableList<PasswordData> e){
        String jsonText = "";
        try{
            String path = "data.json";
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);
            jsonText = reader.readLine();
            file.close();
        }catch(IOException pejo){ pejo.printStackTrace(); }
        StringBuilder forWhat;
        StringBuilder password;
        ArrayList<String> collectionWhatFor = new ArrayList<>();
        ArrayList<String> collectionPassword = new ArrayList<>();
        if(jsonText!=null) {
            for (int i = 0; i < jsonText.length(); i++) {
                if (jsonText.charAt(i) == 'F') {
                    forWhat = new StringBuilder();
                    for (int f = i + 6; f < jsonText.length(); f++) {
                        if (jsonText.charAt(f) == '\"') {
                            break;
                        }
                        forWhat.append(jsonText.charAt(f));
                    }
                    collectionWhatFor.add(forWhat.toString());
                }
                if (jsonText.charAt(i) == 'P') {
                    password = new StringBuilder();
                    for (int f = i + 11; f < jsonText.length(); f++) {
                        if (jsonText.charAt(f) == '\"') {
                            break;
                        }
                        password.append(jsonText.charAt(f));
                    }
                    collectionPassword.add(password.toString());
                }
            }
            for (int i = 0; i < collectionPassword.size(); i++) {
                e.add(new PasswordData(collectionWhatFor.get(i), collectionPassword.get(i)));
            }
        }
    }
}