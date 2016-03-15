package ux;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import utils.Workout;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class WeekSummaryController extends Controller implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Date newerThan = Date.valueOf(LocalDate.now().minusDays(7L));
            initGridPane(MainController.dbConnect.getWorkouts(newerThan));
            hourLabel.setText(Integer.toString(MainController.dbConnect.getSumDuration(newerThan)));
            minuteLabel.setText(Double.toString(MainController.dbConnect.getAvgDuration(newerThan)));
            amountLabel.setText(Integer.toString(MainController.dbConnect.getWorkouts(newerThan).size()));

        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
    }
    @FXML private GridPane gridPane;
    @FXML private Label hourLabel;
    @FXML private Label minuteLabel;
    @FXML private Label amountLabel;


    private void changeSceneToIndex() throws IOException {
        changeScene("index.fxml");
    }

    @FXML
    public void backButton() throws IOException{
        changeSceneToIndex();
    }

    private void initGridPane(List<Workout> workouts){
        setGridPaneElements(gridPane,workouts);
    }

}
