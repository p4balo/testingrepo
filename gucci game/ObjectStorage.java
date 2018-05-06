import com.sun.istack.internal.NotNull;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class ObjectStorage {
    private ObjectStorage(){}
    public static Pane drawHouse(int x, int y){
        Pane root = new Pane();
        ArrayList<Color> colorList = new ArrayList<>();
        colorList.add(Color.GHOSTWHITE);
        colorList.add(Color.YELLOW);
        colorList.add(Color.MEDIUMAQUAMARINE);
        colorList.add(Color.SALMON);
        colorList.add(Color.LIGHTBLUE);
        colorList.add(Color.FORESTGREEN);
        int picker = (int) (Math.random()*colorList.size());

        Polygon py = new Polygon();
        py.getPoints().addAll(0.0+x, 200.0+y,
                0.0+x, 100.0+y,
                85.0+x, 35.0+y,
                170.0+x, 100.0+y,
                170.0+x, 200.0+y
        );
        py.setStroke(Color.SADDLEBROWN);
        py.setStrokeWidth(3);
        py.setFill(colorList.get(picker));
        py.setId("TestObject");
        root.getChildren().add(py);

        Line l1 = new Line(0.0+x,100.0+y,170.0+x,100.0+y);
        l1.setStroke(Color.SADDLEBROWN);
        l1.setStrokeWidth(3);
        l1.setId("TestObject");
        root.getChildren().add(l1);

        Polygon py2 = new Polygon(
                18.0+x, 120.0+y,
                18.0+x, 145.0+y,
                55.0+x, 145.0+y,
                55.0+x, 120.0+y
        );
        py2.setStroke(Color.SADDLEBROWN);
        py2.setStrokeWidth(3);
        py2.setFill(Color.CORNFLOWERBLUE);
        py2.setId("TestObject");
        root.getChildren().add(py2);

        Polygon py3 = new Polygon(
                85.0+x, 35.0+y,
                180.0+x, 0.0+y,
                235.0+x, 45.0+y,
                170.0+x, 100.0+y);
        py3.setStroke(Color.SADDLEBROWN);
        py3.setStrokeWidth(3);
        py3.setFill(Color.FIREBRICK);
        py3.setId("TestObject");
        root.getChildren().add(py3);

        Polygon py4 = new Polygon(
                170.0+x, 100.0+y,
                170.0+x, 200.0+y,
                240.0+x, 125.0+y,
                235.0+x, 45.0+y
        );
        py4.setStroke(Color.SADDLEBROWN);
        py4.setStrokeWidth(3);
        py4.setFill(colorList.get(picker));
        py4.setId("TestObject");
        root.getChildren().add(py4);

        Polygon py5 = new Polygon(
                125.0+x, 195.0+y,
                125.0+x, 157.0+y,
                150.0+x, 157.0+y,
                150.0+x, 195.0+y
        );
        py5.setStroke(Color.BLACK);
        py5.setStrokeWidth(3);
        py5.setFill(Color.SADDLEBROWN);
        py5.setId("TestObject");
        root.getChildren().add(py5);

        Circle c1 = new Circle(145.0+x, 178.0+y, 4);
        c1.setFill(Color.GOLDENROD);
        c1.setStroke(Color.GOLDENROD);
        root.getChildren().add(c1);
        return root;
    }
}
