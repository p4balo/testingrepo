import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    private Pane root;
    private ObservableList<PieChart.Data> pieChartData;
    private final ObservableList<TableData> data = FXCollections.observableArrayList();
    private TableView<TableData> table = new TableView<>();
    public Main(){
        pieChartData = FXCollections.observableArrayList();
        root = new Pane();
        table.setEditable(false);
        TableColumn item = new TableColumn("Item");
        item.setMinWidth(125);
        item.setCellValueFactory(new PropertyValueFactory<TableData, String>("item"));
        TableColumn priority = new TableColumn("Priority");
        priority.setMinWidth(125);
        priority.setCellValueFactory(new PropertyValueFactory<TableData, String>("priority"));
        table.setItems(data);
        table.getColumns().addAll(item,priority);
        table.setLayoutX(15);
        table.setLayoutY(100);
        root.getChildren().add(table);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        insertDataSet();
        Scene scene = new Scene(root,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Time Manager");
    }
    private void insertDataSet(){
        TextField tf = new TextField();
        tf.setFocusTraversable(false);
        tf.setPromptText("Item; priority");
        tf.setLayoutX(15);
        tf.setLayoutY(20);
        tf.setMaxSize(115,15);
        root.getChildren().add(tf);
        Button b = new Button("Enter Data");
        b.setLayoutX(150);
        b.setLayoutY(20);
        b.setOnAction(event -> {
            String dataText = tf.getText();
            int priority = -1;
            for(int i = 0; i<dataText.length(); i++){
                try {
                    priority = Integer.parseInt(""+dataText.charAt(i));
                    dataText = dataText.substring(0, i);
                    System.out.println(dataText);
                }catch (Exception e){ }
            }
            System.out.println(priority);
            data.add(new TableData(dataText, ""+priority));
        });
        root.getChildren().add(b);
    }
    private void createPieChart(){
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setLayoutX(275);
        pieChart.setLayoutY(0);
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(50);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(90);
        root.getChildren().add(pieChart);
    }
    public static class TableData{
        private final SimpleStringProperty item;
        private final SimpleStringProperty priority;
        private TableData(String item, String priority) {
            this.item = new SimpleStringProperty(item);
            this.priority = new SimpleStringProperty(priority);
        }
        public String getItem() { return item.get(); }
        public void setItem(String item) { this.item.set(item); }
        public String getPriority() { return priority.get(); }
        public void setPriority(String priority) { this.priority.set(priority); }
    }
}
