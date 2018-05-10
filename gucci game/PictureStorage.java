import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class PictureStorage {
    private static ArrayList<ImageView> pictureContainer;
    private PictureStorage(){
        pictureContainer = new ArrayList<>();

        ImageView iv = new ImageView("");
        pictureContainer.add(iv);
        iv = new ImageView("")
        pictureContainer.add(iv);
    }
}
