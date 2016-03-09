package ux;
import Backend.DBConnect;
import Backend.DBConnectMock;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController  extends Application{

    private static final DBConnect dbConnect = new DBConnectMock();
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("index.fxml"));
        Pane pane =  loader.load();
        IndexController indexController = loader.getController();
        System.out.println("HEY!");
        indexController.setPrevStage(primaryStage);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
