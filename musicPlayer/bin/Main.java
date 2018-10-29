import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application implements MusicPlayer{
    private HashMap<Media, Boolean> musicList;
    private MusicBar mb;
    private Pane root;
    private Pane infoPane;
    private Dimension screen;
    private Text currentSongText;
    private Text nextSongText;
    private Text previousSongText;
    private MediaPlayer mp;
    private Rectangle musicBar;
    private Duration timeHolder;
    private int currentSongIndex;
    private double currentSongTime;
    private double totalSongTime;
    private double currentVolume;
    private double previousVolume;
    private Button previousButton;
    private Button skipButton;
    private Button playButton;
    private Button infoButton;
    private Button volumeButton;
    private Slider volumeSlider;
    private String styleSheet = Paths.get("musicPlayer/resources/Stylesheet.css").toUri().toString();
    private boolean musicStoped = true;
    private boolean activeSong;
    private boolean infoDrawn;
    public static boolean songInfo;
    public Main(){
        infoPane = InfoScreen.drawInfoScreen(175,25);
        mb = new MusicBar();
        musicBar = new Rectangle();
        root = new Pane();
        musicList = new HashMap<>();
        previousSongText = new Text();
        nextSongText = new Text();
        try(Stream<Path> paths = Files.walk(Paths.get("musicPlayer/music/"))) {
            List<Path> pathContainer = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            for(Path path : pathContainer){
                if(path.toString().contains(".mp3")) {
                    musicList.put(new Media(Paths.get(path.toString()).toUri().toString()), false);
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        screen = Toolkit.getDefaultToolkit().getScreenSize();
    }
    public static void main(String[] args) {
        launch(args);
    }
    private void timer(){
        new AnimationTimer(){
            public void handle(long now) {
                if(activeSong){
                    root.getChildren().remove(musicBar);
                    if(!(mp.getStatus()==null)){
                        if(mp.getStatus()==MediaPlayer.Status.STOPPED){
                            currentSongTime = timeHolder.toSeconds();
                        }else{
                            currentSongTime = mp.getCurrentTime().toSeconds();
                        }
                    }
                    musicBar = mb.drawMusicBar((currentSongTime/totalSongTime)*700);
                    root.getChildren().add(musicBar);
                    currentSongText.toFront();
                }else{
                    root.getChildren().remove(musicBar);
                    root.getChildren().remove(currentSongText);
                }
                if(nextSongText.getText().equals(currentSongText.getText())){
                    drawNextSong(true);
                }
            }
        }.start();
    }
    public void start(Stage primaryStage) throws Exception {
        playButton = PlayButton.drawButton(100,200);
        ImageView iv = PlayButton.getImage();
        iv.setFitWidth(50);
        iv.setFitHeight(50);
        playButton.setOnAction(event -> {
            if(musicStoped){
                iv.setImage(new Image(Paths.get("musicPlayer/resources/pauseButton.png").toUri().toString()));
                playButton.setGraphic(iv);
                musicStoped = false;
                printInfo("playing");
                play();
                activeSong = true;
            }else{
                iv.setImage(new Image(Paths.get("musicPlayer/resources/playButton.png").toUri().toString()));
                playButton.setGraphic(iv);
                musicStoped = true;
                printInfo("stopped");
                stopSong();
            }
        });
        root.getChildren().add(playButton);

        skipButton = SkipButton.drawButton();
        skipButton.toFront();
        skipButton.setOnAction(event -> {
            skipSong();
            activeSong = true;
            iv.setImage(new Image(Paths.get("musicPlayer/resources/pauseButton.png").toUri().toString()));
            printInfo("skipped");
        });
        root.getChildren().add(skipButton);

        previousButton = PreviousButton.drawButton();
        previousButton.toFront();
        previousButton.setOnAction(event -> {
            if(currentSongIndex-1>=0) {
                previousSong();
                activeSong = true;
                iv.setImage(new Image(Paths.get("musicPlayer/resources/pauseButton.png").toUri().toString()));
                printInfo("previous");
            }else{
                System.out.println("no previous songs");
            }
        });
        root.getChildren().add(previousButton);

        infoButton = InfoButton.drawButton(755,5);
        infoButton.toFront();
        infoButton.setOnAction(event ->{
            if(!infoDrawn) {
                if (activeSong) {
                    stopSong();
                    musicStoped = true;
                    iv.setImage(new Image(Paths.get("musicPlayer/resources/playButton.png").toUri().toString()));
                    playButton.setGraphic(iv);
                    printInfo("stopped");
                }
                for (int i = 0; i < root.getChildren().size(); i++) {
                    Node n = root.getChildren().get(i);
                    n.setEffect(new GaussianBlur());
                }
                root.getChildren().add(infoPane);
                Button b = InfoScreen.drawBackButton(375, 25);
                b.getStylesheets().add(styleSheet);
                b.setOnAction(event1 -> {
                    root.getChildren().remove(b);
                    root.getChildren().remove(infoPane);
                    for (int i = 0; i < root.getChildren().size(); i++) {
                        Node n = root.getChildren().get(i);
                        n.setEffect(null);
                    }
                    System.out.println("Displaying Song Information: "+songInfo);
                    if (songInfo) {
                        TextArea ta = drawSongInfo();
                        ta.setId("songInfo");
                        root.getChildren().add(ta);
                        System.out.println("Current Song Index: "+currentSongIndex);
                    }
                    if(!songInfo){
                        for(int i = 0; i<root.getChildren().size(); i++){
                            if(root.getChildren().get(i).getId()!=null){
                                if(root.getChildren().get(i).getId().equals("songInfo")){
                                    root.getChildren().remove(i);
                                }
                            }
                        }
                    }
                    infoDrawn = false;
                });
                root.getChildren().add(b);
                infoDrawn = true;
            }
        });
        root.getChildren().add(infoButton);

        root.getChildren().add(mb.drawPlaceHolder());

        volumeButton = VolumeButton.drawButton(220,205,.5);
        volumeButton.toFront();
        root.getChildren().add(volumeButton);

        volumeSlider = VolumeSlider.drawSlider(270,225);
        volumeSlider.toFront();
        currentVolume = 0.5;
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->{
            if(mp != null){
                root.getChildren().remove(volumeButton);
                setVolume(newValue.doubleValue());
                currentVolume = newValue.doubleValue();
                volumeButton = VolumeButton.drawButton(220,205, newValue.doubleValue());
                root.getChildren().add(volumeButton);
            }
        });
        root.getChildren().add(volumeSlider);

        volumeButton.setOnAction(event -> {
            if(currentVolume>0){
                previousVolume = volumeSlider.getValue();
                root.getChildren().remove(volumeSlider);
                volumeSlider.setValue(0);
                volumeButton = VolumeButton.drawButton(220,205, 0.0);
                setVolume(0.0);
                currentVolume = 0;
                root.getChildren().add(volumeSlider);
            }else{
                root.getChildren().remove(volumeSlider);
                volumeSlider.setValue(previousVolume);
                volumeButton = VolumeButton.drawButton(220, 205, volumeSlider.getValue());
                setVolume(volumeSlider.getValue());
                currentVolume = volumeSlider.getValue();
                root.getChildren().add(volumeSlider);
            }
        });

        primaryStage.setScene(new Scene(root,800,350));
        primaryStage.setTitle("Music Player");
        primaryStage.show();
        primaryStage.getIcons().add(new Image(Paths.get("musicPlayer/resources/playIcon.png").toUri().toString()));
        timer();
        currentSongText = SongText.drawText(MusicBar.getX()+15, MusicBar.getY()+7);
        currentSongText.setText("No Active Song");
        drawPreviousSong();
        drawNextSong(false);
    }
    private List<Media> getMedia(){
        return new ArrayList<>(musicList.keySet());
    }
    public void play(){
        if(activeSong){
            mp.setStartTime(timeHolder);
        }else {
            mp = new MediaPlayer(getMedia().get(currentSongIndex));
            drawPreviousSong();
            drawNextSong(false);
            mp.setVolume(volumeSlider.getValue());
            currentSongText.setText(getMusicName(getMedia().get(currentSongIndex).getSource()));
            root.getChildren().remove(currentSongText);
            root.getChildren().add(currentSongText);
            totalSongTime = mp.getTotalDuration().toSeconds();
            mp.setOnReady(()-> totalSongTime = mp.getTotalDuration().toSeconds());
            mp.setOnEndOfMedia(()->{
                mp.dispose();
                activeSong = false;
                System.out.println("musicFinished");
                System.out.println("List Size"+getMedia().size()+", Current Song"+currentSongIndex+", Amount of Time"+currentSongTime+"s");
                if(!(currentSongIndex>musicList.size())) {
                    mb = new MusicBar();
                    skipSong();
                }
                activeSong = true;
            });
        }
        mp.play();
        printInfo("Current time: "+mp.getCurrentTime().toString());
    }
    public void stopSong(){
        timeHolder = mp.getCurrentTime();
        System.out.println("Stopped at: "+timeHolder.toString());
        mp.stop();
    }
    public void skipSong() {
        currentSongIndex++;
        mb = new MusicBar();
        printInfo("File Location"+getMedia().get(currentSongIndex).getSource());
        stopSong();
        dispose();
        activeSong = false;
        timeHolder = null;
        play();
    }
    public void previousSong() {
        currentSongIndex--;
        mb = new MusicBar();
        printInfo("File Location"+getMedia().get(currentSongIndex).getSource());
        stopSong();
        dispose();
        activeSong = false;
        timeHolder = null;
        play();
    }
    public void shuffleList() {

    }
    public void dispose(){
        mp.dispose();
    }
    public void setVolume(double volume){
        mp.setVolume(volume);
    }
    private void drawPreviousSong(){
        root.getChildren().remove(previousSongText);
        if(currentSongIndex-1>=0) {
            previousSongText.setText(getMusicName(getMedia().get(currentSongIndex - 1).getSource()));
        }
        else{
            previousSongText.setText("No Previous Songs");
        }
        previousSongText.setFont(new Font(20));
        previousSongText.setLayoutY(MusicBar.getY()-30);
        previousSongText.setLayoutX(MusicBar.getX()+15);
        root.getChildren().add(previousSongText);
    }
    private void drawNextSong(boolean force){
        root.getChildren().remove(nextSongText);
        if(currentSongIndex+1<getMedia().size()) {
            if(force){
                nextSongText.setText(getMusicName(getMedia().get(currentSongIndex + 1).getSource()));
            } else if(currentSongIndex!=0) {
                nextSongText.setText(getMusicName(getMedia().get(currentSongIndex + 1).getSource()));
            }else{
                nextSongText.setText(getMusicName(getMedia().get(currentSongIndex).getSource()));
            }
        }
        else{
            nextSongText.setText("No Future Songs");
        }
        nextSongText.setFont(new Font(20));
        nextSongText.setLayoutY(MusicBar.getY()+70+nextSongText.getFont().getSize());
        nextSongText.setLayoutX(MusicBar.getX()+15);
        root.getChildren().add(nextSongText);
    }
    private String getMusicName(String name){
        name = name.substring(name.lastIndexOf('/')+1,name.lastIndexOf('.'));
        StringBuilder stringifiedName = new StringBuilder();
        for(int i = 0; i<name.length(); i++){
            if(name.charAt(i)=='%'&&name.charAt(i+1)=='2'&&name.charAt(i+2)=='0'){
                stringifiedName.append(' ');
                i+=2;
            }else{
                stringifiedName.append(name.charAt(i));
            }
        }
        return stringifiedName.toString();
    }
    private Node nodeItterator(String id){
        Node n = null;
        for(int i = 0; i<root.getChildren().size(); i++){
            if(root.getChildren().get(i).getId()!=null){
                if(root.getChildren().get(i).getId().equals(id)){
                    n = root.getChildren().get(i);
                    break;
                }
            }
        }
        return n;
    }
    private TextArea drawSongInfo(){
        TextArea ta = new TextArea();
        StringBuilder s = new StringBuilder();
        for(int i = 0; i<getMedia().size(); i++) {
            s.append(getMusicName(getMedia().get(i).getSource()));
            if(getMedia().size()-1!=i) {
                s.append('\n');
            }
        }
        ta.setText(s.toString());
        ta.setFont(new Font(12));
        ta.setEditable(false);
        ta.setLayoutX(430);
        ta.setLayoutY(200);
        ta.setMaxSize(325,140);
        ta.setId("Test");
        ta.getStylesheets().add(styleSheet);
        Platform.runLater(() -> ta.selectRange(13, 18));
        return ta;
    }
    private static void printInfo(String s){
        System.out.println(s);
    }
    private static void printInfo(int s){
        System.out.println(s);
    }
    private static void printInfo(double s){
        System.out.println(s);
    }
    static String getPathname(){
        return Paths.get("musicPlayer/music/").toAbsolutePath().toString();
    }
}