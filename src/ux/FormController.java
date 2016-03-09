package ux;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormController implements Initializable {

    private Stage prevStage;


    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    @FXML
    public void button_legg_til() throws IOException{
        changeScene();
    }


    private void changeScene() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
        Pane pane = loader.load();
        IndexController controller =  loader.getController();
        controller.setPrevStage(prevStage);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        prevStage.close();
        prevStage.setScene(scene);
        prevStage.show();
    }
}
