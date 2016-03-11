package ux;
import backend.DBConnect;
import backend.DBConnectMock;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainController  extends Application{

    public static final DBConnect dbConnect = new DBConnectMock();
    @Override
    public void start(Stage prevStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
        Pane pane = loader.load();
        Controller controller =  loader.getController();
        controller.setPrevStage(prevStage);
        Scene scene = new Scene(pane);
        prevStage.close();
        prevStage.setScene(scene);
        prevStage.show();
    }
}
