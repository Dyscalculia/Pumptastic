package ux;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import utils.Workout;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class IndexController extends Controller implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultGridPaneElements();

    }
    @FXML private VBox gridPane;

    @FXML
    public void button_registrer_ny_trening(ActionEvent actionEvent) throws IOException{
        changeSceneToForm();
    }


    @FXML
    public void buttonGetTopTen(){
        try{
            List<Workout> workouts = MainController.dbConnect.getWorkouts(null);
            workouts.sort((a,b) -> b.getPerformance().compareTo(a.getPerformance()));
            setGridPaneElements(workouts);

        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
    }

    private void setGridPaneElements(List<Workout> workouts){
        setGridPaneElements(gridPane,workouts);
    }

    private void setDefaultGridPaneElements(){
        try{
            setGridPaneElements(MainController.dbConnect.getWorkoutsLabels(null));
        }catch (Exception e){
            System.out.println(e.getStackTrace());
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
