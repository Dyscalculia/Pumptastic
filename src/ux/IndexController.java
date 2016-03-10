package ux;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Workout;
import ux.test.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonShowAll();

    }
    private Stage prevStage;
    @FXML private GridPane gridPane;



    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }


    @FXML
    public void button_registrer_ny_trening(ActionEvent actionEvent) throws IOException{
        changeScene();
    }


    @FXML
    public void buttonGetTopTen(){
        try{
            setGridPaneElements(MainController.dbConnect.getWorkouts()); //TODO: Endre getWorkouts til topTen...

        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
    }

    private void setGridPaneElements(List<Workout> workouts){
        gridPane.getChildren().clear();
        int maxSize = workouts.size();
        for (int index = 0;index<maxSize;index++){
            gridPane.add(new Label("TODO LEGG TIL NOE"),0,index);
        }

    }
    @FXML
    public void buttonShowAll(){
        try{
            setGridPaneElements(MainController.dbConnect.getWorkouts());

        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }

    }

    private void changeScene() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form.fxml"));
        Pane pane = loader.load();
        FormController controller =  loader.getController();
        controller.setPrevStage(prevStage);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        prevStage.close();
        prevStage.setScene(scene);
        prevStage.show();
    }
}
