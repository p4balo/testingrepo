import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class passwordGenerator extends Application{
    private Pane root = new Pane();
    private String[] basicCharList;
    private String[] specialCharsList;
    private String[] capitalCharsList;
    private String[] numberCharList;
    private String masterKey;
    private boolean repeatingChars;
    private boolean containsSpecialChars;
    private boolean containsCapitalChars;
    private boolean containsNumberChars;
    private TableView<PasswordData> table = new TableView<>();
    private final ObservableList<PasswordData> data = FXCollections.observableArrayList();
    private ComboBox<String> options;

    public passwordGenerator(){
        basicCharList = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        specialCharsList = new String[]{"!","@","#","$","%","^","&","*","(",")","_","-","+","=","{","}","[","]","\\","|",";",":","\'","\"","<",">",",",".","/","?","~","`"};
        capitalCharsList = new String[basicCharList.length];
        for(int i = 0; i<basicCharList.length; i++){
            capitalCharsList[i] = basicCharList[i].toUpperCase();
        }
        numberCharList = new String[]{"0","1","2","3","4","5","6","7","8","9"};

        readJSON(data, "PasswordGen/masterkey.json", 1);
        System.out.println(masterKey);
    }
    public static void main(String[] args) { launch(args); }
    public void start(Stage primaryStage) {

        ToolBar tb1 = new ToolBar();
        tb1.setOrientation(Orientation.HORIZONTAL);
        tb1.setMinWidth(800);

        options = new ComboBox<>();

        ObservableList<String> list = FXCollections.observableArrayList(
                "Options",
                "Change Master Key",
                "Manual Input",
                "View Storage Type"
        );

        options.setOnAction(event -> {
            if(options.getValue().equals("Change Master Key")){ changePasswordPopup(primaryStage); }
            if(options.getValue().equals("Manual Input")){ manualInputPopup(primaryStage); }
            if(options.getValue().equals("View Storage Type")){ viewDataPopup(primaryStage);}
        });


        options.setItems(list);
        options.getSelectionModel().selectFirst();

        tb1.getItems().addAll(options);
        root.getChildren().addAll(tb1);

        Rectangle r1 = new Rectangle(20,40,760,540);
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

        TextField tf3 =  new TextField();
        Label l9 = new Label("Miscellaneous");
        tf3.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 32 ? change : null));
        tf3.setLayoutY(345);
        tf3.setLayoutX(115);
        tf3.setMaxWidth(150);
        l9.setLabelFor(tf1);
        l9.setLayoutX(30);
        l9.setLayoutY(345);
        l9.setFont(new Font(12));
        root.getChildren().addAll(tf3, l9);

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
        b1.setLayoutY(395);
        b1.setOnAction(event -> {
            boolean proceed = true;
            int checker = 0;
            for(int i = 0; i<tf1.getText().length(); i++){
                for (String aNumberCharList : numberCharList) {
                    if (tf1.getText().charAt(i) != aNumberCharList.charAt(0)) { checker++; }
                }
            }
            if(checker%10==0){ proceed = false; }
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
                        if (randSelector == 0) { t.append(basicCharList[(int) (Math.random() * basicCharList.length)]); }
                        if (randSelector == 1) { t.append(numberCharList[(int) (Math.random() * numberCharList.length)]); }
                    }
                    ta1.setText(t.toString());
                }
                if (containsCapitalChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) { t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]); }
                        if (randSelector == 0) { t.append(basicCharList[(int) (Math.random() * basicCharList.length)]); }
                    }
                    ta1.setText(t.toString());
                }
                if (containsSpecialChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) { t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]); }
                        if (randSelector == 0) { t.append(basicCharList[(int) (Math.random() * basicCharList.length)]); }
                    }
                    ta1.setText(t.toString());
                }
                if (containsNumberChars && containsCapitalChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) { t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]); }
                        if (randSelector == 0) { t.append(basicCharList[(int) (Math.random() * basicCharList.length)]); }
                        if (randSelector == 2) { t.append(numberCharList[(int) (Math.random() * numberCharList.length)]); }
                    }
                    ta1.setText(t.toString());
                }
                if (containsNumberChars && containsSpecialChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 1) { t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]); }
                        if (randSelector == 2) { t.append(numberCharList[(int) (Math.random() * numberCharList.length)]); }
                        if (randSelector == 0) { t.append(basicCharList[(int) (Math.random() * basicCharList.length)]); }
                    }
                    ta1.setText(t.toString());
                }
                if (containsCapitalChars && containsSpecialChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 0) { t.append(basicCharList[(int) (Math.random() * basicCharList.length)]); }
                        if (randSelector == 1) { t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]); }
                        if (randSelector == 2) { t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]); }
                    }
                    ta1.setText(t.toString());
                }
                if (containsSpecialChars && containsCapitalChars && containsNumberChars) {
                    StringBuilder t = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(tf1.getText()); i++) {
                        int randSelector = (int) (Math.random() * (numberOfChoices + 1));
                        if (randSelector == 0) { t.append(basicCharList[(int) (Math.random() * basicCharList.length)]); }
                        if (randSelector == 1) { t.append(specialCharsList[(int) (Math.random() * specialCharsList.length)]); }
                        if (randSelector == 2) { t.append(capitalCharsList[(int) (Math.random() * capitalCharsList.length)]); }
                        if (randSelector == 3) { t.append(numberCharList[(int) (Math.random() * numberCharList.length)]); }
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

        TableColumn whatFor = new TableColumn("What For");
        whatFor.setMinWidth(125);
        whatFor.setCellValueFactory(new PropertyValueFactory<PasswordData, String>("whatFor"));

        TableColumn password = new TableColumn("Password");
        password.setMinWidth(125);
        password.setCellValueFactory(new PropertyValueFactory<PasswordData, String>("password"));

        TableColumn misc = new TableColumn("Miscellaneous");
        misc.setMinWidth(125);
        misc.setCellValueFactory(new PropertyValueFactory<PasswordData, String>("misc"));

        table.setItems(data);
        table.getColumns().addAll(whatFor, password, misc);

        final VBox vbox = new VBox();
        vbox.setId("tablebox");
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);
        vbox.setLayoutX(370);
        vbox.setLayoutY(160);
        root.getChildren().addAll(vbox);

        Button b3 = new Button("Save Password");
        b3.setLayoutX(45);
        b3.setLayoutY(445);
        b3.setOnAction(event ->{
            if(tf3.getText()==null||tf3.getText().equals("")) { data.addAll(new PasswordData(t1.getText(), ta1.getText())); }
            else{ data.addAll(new PasswordData(t1.getText(), ta1.getText(), tf3.getText())); }
            writeToJson("PasswordGen/data.json",0);
            deletePasswords(data);
        });
        root.getChildren().addAll(b3);

        primaryStage.setTitle("Password Generator");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();

        //Load Previous Data
        signInPopup(masterKey, primaryStage);
    }
    private void viewDataPopup(Stage stage){
        Popup p = new Popup();
        p.setX(500);
        p.setY(200);
        Pane root = new Pane();
        root.setPadding(new Insets(10,0,0,10));

        Rectangle r1 = new Rectangle(300,200);
        r1.setStroke(Color.DARKGRAY);
        r1.setStrokeWidth(3);
        r1.setFill(Color.WHITESMOKE);
        root.getChildren().add(r1);
    }
    private void manualInputPopup(Stage stage){
        Popup p = new Popup();
        p.setX(500);
        p.setY(200);
        Pane root = new Pane();
        root.setPadding(new Insets(10,0,0,10));

        Rectangle r1 = new Rectangle(300,240);
        r1.setStroke(Color.DARKGRAY);
        r1.setStrokeWidth(3);
        r1.setFill(Color.WHITESMOKE);
        root.getChildren().add(r1);

        Text t1 = new Text("Enter in Info");
        t1.setFont(new Font(20));
        t1.setLayoutX(15);
        t1.setLayoutY(25);
        root.getChildren().add(t1);

        Label l1 = new Label("Enter in \nWhat For");
        l1.setLayoutX(15);
        l1.setLayoutY(45);
        l1.setFont(new Font(12));
        root.getChildren().add(l1);

        Label l2 = new Label("Enter in \nPassword");
        l2.setLayoutX(15);
        l2.setLayoutY(95);
        l2.setFont(new Font(12));
        root.getChildren().add(l2);

        Label l3 = new Label("Enter in \nMiscellaneous");
        l3.setLayoutX(15);
        l3.setLayoutY(145);
        l3.setFont(new Font(12));
        root.getChildren().add(l3);


        TextField tf1 = new TextField();
        tf1.setLayoutX(100);
        tf1.setLayoutY(45);
        root.getChildren().add(tf1);

        TextField tf2 = new TextField();
        tf2.setLayoutX(100);
        tf2.setLayoutY(95);
        root.getChildren().add(tf2);

        TextField tf3 = new TextField();
        tf3.setLayoutX(100);
        tf3.setLayoutY(145);
        root.getChildren().add(tf3);

        Button submit = new Button("Enter");
        submit.setLayoutX(15);
        submit.setLayoutY(195);
        submit.setOnAction(event -> {
            data.add(new PasswordData(tf1.getText(),tf2.getText(),tf3.getText()));
            writeToJson("PasswordGen/data.json",0);
            p.hide();
            options.getSelectionModel().selectFirst();
        });
        root.getChildren().add(submit);

        p.getContent().add(root);
        p.show(stage);
    }
    private void changePasswordPopup(Stage stage){
        Popup p = new Popup();
        p.setX(500);
        p.setY(200);
        Pane root = new Pane();
        root.setPadding(new Insets(10,0,0,10));

        Rectangle r1 = new Rectangle(300,200);
        r1.setStroke(Color.DARKGRAY);
        r1.setStrokeWidth(3);
        r1.setFill(Color.WHITESMOKE);
        root.getChildren().add(r1);

        Text t1 = new Text("Enter in Password");
        t1.setFont(new Font(20));
        t1.setLayoutX(15);
        t1.setLayoutY(25);
        root.getChildren().add(t1);

        Label l1 = new Label("Enter in New \nPassword");
        l1.setLayoutX(15);
        l1.setFont(new Font(12));

        Label l2 = new Label("Enter in Old \nPassword");
        l2.setLayoutX(15);
        l2.setFont(new Font(12));

        PasswordField tf1 = new PasswordField();
        tf1.setLayoutX(100);
        tf1.setLayoutY(45);
        PasswordField tf2 = new PasswordField();
        tf2.setLayoutX(100);
        tf2.setLayoutY(45);
        PasswordField tf3 = new PasswordField();
        tf3.setLayoutX(100);
        tf3.setLayoutY(95);

        boolean previousPassword = checkPreviousPassword();

        if(previousPassword){
            root.getChildren().add(tf2);
            root.getChildren().add(tf3);
            l2.setLayoutY(45);
            l1.setLayoutY(95);
            root.getChildren().add(l1);
            root.getChildren().add(l2);
        }else{
            root.getChildren().add(tf1);
            l1.setLayoutY(45);
            root.getChildren().add(l1);
        }

        Button submit = new Button("Enter");
        submit.setLayoutX(15);
        if(previousPassword) submit.setLayoutY(145);
        else submit.setLayoutY(95);
        submit.setOnAction(event ->{
            if(previousPassword){
                if(tf2.getText().equals(masterKey)) {
                    masterKey = tf3.getText();
                    writeToJson("PasswordGen/masterkey.json",1);
                    options.getSelectionModel().selectFirst();
                    p.hide();
                }else{
                    Text ty1 = new Text("Incorrect Password");
                    ty1.setFont(new Font(12));
                    ty1.setStroke(Color.RED);
                    ty1.setLayoutY(190);
                    ty1.setLayoutX(10);
                    root.getChildren().add(ty1);

                    Button close = new Button("Close");
                    close.setLayoutY(145);
                    close.setLayoutX(85);
                    close.setOnAction(event1 -> p.hide());
                    root.getChildren().add(close);
                }
            }else{
                p.hide();
                masterKey = tf1.getText();
                writeToJson("PasswordGen/masterkey.json",1);
                options.getSelectionModel().selectFirst();
            }
        });
        root.setOnKeyPressed(event -> {
            if(event.getCode()==KeyCode.ENTER){
                if(previousPassword){
                    if(tf2.getText().equals(masterKey)) {
                        masterKey = tf3.getText();
                        writeToJson("PasswordGen/masterkey.json",1);
                        p.hide();
                        options.getSelectionModel().selectFirst();
                    }else{
                        Text ty1 = new Text("Incorrect Password");
                        ty1.setFont(new Font(12));
                        ty1.setStroke(Color.RED);
                        ty1.setLayoutX(10);
                        ty1.setLayoutY(190);
                        root.getChildren().add(ty1);

                        Button close = new Button("Close");
                        close.setLayoutY(145);
                        close.setLayoutX(85);
                        close.setOnAction(event1 -> p.hide());
                        root.getChildren().add(close);
                    }
                }else{
                    masterKey = tf1.getText();
                    p.hide();
                    writeToJson("PasswordGen/masterkey.json",1);
                    options.getSelectionModel().selectFirst();
                }
            }
        });
        root.getChildren().add(submit);
        p.getContent().add(root);
        p.show(stage);
    }
    private boolean checkPreviousPassword(){ return masterKey != null && !masterKey.equals(""); }
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

        if(masterKey!=null) {
            Text t = new Text("Enter in Master Key");
            if(!checkData("PasswordGen/data.json")){ t.setText("No Data Detected"); }
            t.setLayoutY(25);
            t.setLayoutX(15);
            t.setFont(new Font(20));
            box.getChildren().add(t);
        }else{
            Text t = new Text("No Master Key Detected \nWould you Like to Load \nPrevious Data");
            if(!checkData("PasswordGen/data.json")){
                t.setText("No Data Detected");
                if(masterKey==null){ t.setText("No Data Detected \nand No Master Key Set"); }
            }
            t.setLayoutY(25);
            t.setLayoutX(15);
            t.setFont(new Font(20));
            box.getChildren().add(t);

            Button b1 = new Button("Load");
            b1.setLayoutY(85);
            b1.setLayoutX(15);
            b1.setOnAction(event -> {
                readJSON(data, "PasswordGen/data.json", 0);
                p.hide();
                deletePasswords(data);
            });
            if(checkData("PasswordGen/data.json")) {
                box.getChildren().add(b1);
            }

            Button b2 = new Button("Close");
            b2.setLayoutX(85);
            b2.setLayoutY(85);
            if(!checkData("PasswordGen/data.json")){
                b2.setLayoutX(15);
            }
            b2.setOnAction(event -> p.hide());
            box.getChildren().add(b2);

            if(masterKey==null){
                Button b3 = new Button("Set Masterkey");
                b3.setLayoutX(155);
                b3.setLayoutY(85);
                b3.setOnAction(event -> {
                    p.hide();
                    changePasswordPopup(stage);
                });
                box.getChildren().add(b3);
            }
        }

        if(masterKey!=null) {
            if(checkData("PasswordGen/data.json")) {
                PasswordField tf = new PasswordField();
                tf.setLayoutX(15);
                tf.setLayoutY(45);
                tf.setMaxWidth(175);
                box.getChildren().add(tf);

                Button submit = new Button("Enter");
                submit.setLayoutY(85);
                submit.setLayoutX(15);
                submit.setOnAction(event -> {
                    if (tf.getText().equals(correctPassword)) {
                        readJSON(data, "PasswordGen/data.json", 0);
                        p.hide();
                        deletePasswords(data);
                    } else {
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
                    if (event.getCode() == KeyCode.ENTER) {
                        if (tf.getText().equals(correctPassword)) {
                            p.hide();
                            readJSON(data, "PasswordGen/data.json", 0);
                            deletePasswords(data);
                        } else {
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
            }else{
                Button close = new Button("Close");
                close.setLayoutX(15);
                close.setLayoutY(85);
                close.setOnAction(event1 -> p.hide());
                box.getChildren().add(close);
            }
        }
        p.getContent().add(box);
        p.show(stage);

    }
    public static class PasswordData {
        private final SimpleStringProperty whatFor;
        private final SimpleStringProperty password;
        private final SimpleStringProperty misc;

        private PasswordData(String whatFor, String password) {
            this.whatFor = new SimpleStringProperty(whatFor);
            this.password = new SimpleStringProperty(password);
            this.misc = new SimpleStringProperty("null");
        }
        private PasswordData(String whatFor, String password, String misc){
            this.whatFor = new SimpleStringProperty(whatFor);
            this.password = new SimpleStringProperty(password);
            this.misc = new SimpleStringProperty(misc);
        }
        public String getWhatFor() { return whatFor.get(); }
        public void setWhatFor(String fName) { whatFor.set(fName); }
        public String getPassword() { return password.get(); }
        public void setPassword(String fName) { password.set(fName); }
        public String getMisc() { return misc.get(); }
        public void setMisc(String fName) { misc.set(fName); }
    }
    private void writeToJson(String p, int type){
        JSONArray totaldata = new JSONArray();
        if(type==0) {
            for (int i = 0; i < data.size(); i++) {
                JSONArray array = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("For", data.get(i).getWhatFor());
                array.put(obj);
                JSONObject obj1 = new JSONObject();
                obj.put("Password", data.get(i).getPassword());
                array.put(obj1);
                JSONObject obj2 = new JSONObject();
                obj.put("Misc", data.get(i).getMisc());
                array.put(obj2);
                totaldata.put(array);
            }
        }else if(type==1){
            JSONObject obj = new JSONObject();
            obj.put("MasterKey",masterKey);
            totaldata.put(obj);
        }
        try {
            FileWriter file = new FileWriter(p);
            String json = totaldata.toString();
            file.write(json);
            file.close();
        }catch(IOException e){ e.printStackTrace(); }
    }
    private void readJSON(ObservableList<PasswordData> e, String path, int type){
        String jsonText = "";
        try {
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);
            jsonText = reader.readLine();
            file.close();
        } catch (IOException pejo) {
            pejo.printStackTrace();
        }
        if(type==0) {
            StringBuilder forWhat;
            StringBuilder password;
            StringBuilder misc;
            ArrayList<String> collectionWhatFor = new ArrayList<>();
            ArrayList<String> collectionPassword = new ArrayList<>();
            ArrayList<String> collectionMisc = new ArrayList<>();
            if (jsonText != null) {
                if (!jsonText.equals("[]")) {
                    for (int i = 0; i < jsonText.length(); i++) {
                        if (jsonText.charAt(i) == 'F' && jsonText.charAt(i+1) == 'o') {
                            forWhat = new StringBuilder();
                            for (int f = i + 6; f < jsonText.length(); f++) {
                                if (jsonText.charAt(f) == '\"') { break; }
                                forWhat.append(jsonText.charAt(f));
                            }
                            collectionWhatFor.add(forWhat.toString());
                        }
                        if (jsonText.charAt(i) == 'P' && jsonText.charAt(i+1)=='a') {
                            password = new StringBuilder();
                            for (int f = i + 11; f < jsonText.length(); f++) {
                                if (jsonText.charAt(f) == '\"') { break; }
                                password.append(jsonText.charAt(f));
                            }
                            collectionPassword.add(password.toString());
                        }
                        if (jsonText.charAt(i) == 'c' && jsonText.charAt(i+1)=='\"'){
                            misc = new StringBuilder();
                            for(int f = i+4; f<jsonText.length(); f++){
                                if(jsonText.charAt(f) == '\"'){ break; }
                                misc.append(jsonText.charAt(f));
                            }
                            if(misc.toString().equals("")){ collectionMisc.add(null);
                            }else { collectionMisc.add(misc.toString()); }
                        }
                    }
                    for (int i = 0; i < collectionPassword.size(); i++) {
                        e.add(new PasswordData(collectionWhatFor.get(i), collectionPassword.get(i), collectionMisc.get(i)));
                    }
                }else { readJSON(e, "null", 2); }
            }
        }else if(type==1){
            StringBuilder password = new StringBuilder();
            if(jsonText!=null){
                if(!jsonText.equals("[]"))
                for(int i = 0; i<jsonText.length(); i++){
                    if(jsonText.charAt(i)=='y'){
                        for(int k = i+4; k<jsonText.length(); k++){
                            if(jsonText.charAt(k)=='\"'){ break; }
                            password.append(jsonText.charAt(k));

                        }
                    }
                }
                masterKey = password.toString();
            }
            else{ readJSON(e, "null", 2); }
        }if(type==2){ error(0); }
    }
    private void error(int errorType){
        if(errorType==0){
            Text t = new Text("No Saved Passwords");
            t.setFont(new Font(12));
            t.setLayoutY(560);
            t.setLayoutX(45);
            t.setStroke(Color.RED);
            root.getChildren().add(t);
        }
    }
    private void deletePasswords(ObservableList<PasswordData> e){
        VBox test = new VBox();
        if(e.size()!=0) {
            for (int i = 0; i < e.size(); i++) {
                ImageView iv = new ImageView("redX.png");
                iv.setFitHeight(10);
                iv.setFitWidth(10);
                test.getChildren().add(iv);

                Button b = new Button();
                b.setGraphic(iv);
                b.setMaxSize(5,5);
                b.setId(""+i);
                int f = i;
                b.setOnAction(event -> {
                    if(b.getId().equals(""+f)){
                        for(int j = 0; j<root.getChildren().size(); j++) {
                            if (root.getChildren().get(j).toString().contains("VBox")) {
                                Node v = root.getChildren().get(j);
                                if (v.getId() != null) {
                                    if (!v.getId().equals("tablebox")) {
                                        root.getChildren().remove(j);
                                        j--;
                                    }
                                }else{
                                    root.getChildren().remove(j);
                                    j--;
                                }
                            }
                        };
                        data.remove(f);
                        writeToJson("PasswordGen/data.json",0);
                        root.getChildren().remove(test);
                        deletePasswords(e);
                    }
                });
                test.getChildren().add(b);
            }
            test.setLayoutX(320);
            test.setLayoutY(200);
            root.getChildren().add(test);
        }
        if(e.size()==0){
            root.getChildren().remove(test);
        }
    }
    private boolean checkData(String path){
        String jsonText = "";
        try{
            FileReader file = new FileReader(path);
            BufferedReader reader = new BufferedReader(file);
            jsonText = reader.readLine();
            file.close();
        }catch(Exception e){ e.printStackTrace(); }
        return jsonText!=null&&!(jsonText.equals("[]"));
    }
}