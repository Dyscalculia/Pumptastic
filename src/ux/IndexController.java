package ux;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import utils.Workout;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class IndexController extends Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML private GridPane gridPane;





    @FXML
    public void button_registrer_ny_trening(ActionEvent actionEvent) throws IOException{
        changeSceneToForm();
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
    public void weekSummary()throws IOException{
        changeToWeekSummaryScene();
    }

    private void changeSceneToForm() throws IOException{
        changeScene("form.fxml");

    }

    private void changeToWeekSummaryScene() throws IOException{
        changeScene("weeksummary.fxml");
    }

}
