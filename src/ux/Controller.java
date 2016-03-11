package ux;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class Controller {

    protected Stage prevStage;

    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }

    protected void changeScene(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
        Pane pane = loader.load();
        Controller controller =  loader.getController();
        controller.setPrevStage(prevStage);
        Scene scene = new Scene(pane);
        prevStage.close();
        prevStage.setScene(scene);
        prevStage.show();
    }
}
