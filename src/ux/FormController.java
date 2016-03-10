package ux;


import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Exercise;
import utils.Time;
import utils.Workout;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormController implements Initializable {

    private Stage prevStage;


    public void setPrevStage(Stage prevStage) {
        this.prevStage = prevStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateField.textProperty().addListener(dateFieldListener);
        try {
            List<Exercise> exercises = MainController.dbConnect.getExercises(0);
            comboBox.getItems().addAll("hei","hopp");
            comboBox.getSelectionModel().selectFirst();
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }

    private static final Pattern dateFormat = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2}");
    private ChangeListener<? super String> dateFieldListener = ((observable, oldValue, newValue) -> {
        Matcher matcher = dateFormat.matcher(newValue);
        if(matcher.matches()){
            //TODO: LEGG TIL RIKIT I LABEL!!!
        }
        else {
            //TODO: LEGG TIL FEIL I LABEL!!
        }
    });

    @FXML private TextField dateField;
    @FXML private TextField timeField;
    @FXML private TextField tempField;
    @FXML private TextField wField;
    @FXML private ToggleButton outButton;
    @FXML private ComboBox comboBox;
    @FXML private TextField repField;
    @FXML private TextField settField;
    @FXML private TextField weightField;
    @FXML private TableView table;

    // Workout(int id, Date date, Time time, int duration, int performance, String log)
    @FXML
    public void button_legg_til() throws IOException{
        String[] dates = dateField.getText().split("/");
        Date date = new Date(Integer.valueOf(dates[0]),Integer.valueOf(dates[1]),Integer.valueOf(dates[2]));
        Workout workout = new Workout(0,date,new Time("00:00:00"),1,0,""); //TODO: Endre slik at dette blir riktig
        MainController.dbConnect.createWorkout(workout);
        changeScene();
    }

    @FXML
    public void buttonRemove() throws IOException{
        changeScene();
    }

    @FXML
    public void buttonToggle(){
        if(outButton.isSelected()){
            tempField.setPromptText("Luft");
            wField.setPromptText("Tilskuere");
        }else{
            tempField.setPromptText("Temperatur");
            wField.setPromptText("Værforhold");
        }
    }


    private void changeScene() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
        Pane pane = loader.load();
        IndexController controller =  loader.getController();
        controller.setPrevStage(prevStage);
        Scene scene = new Scene(pane);
        prevStage.close();
        prevStage.setScene(scene);
        prevStage.show();
    }


}
