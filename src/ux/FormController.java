package ux;


import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Exercise;
import utils.ExerciseTrainingInstance;
import utils.Time;
import utils.Workout;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FormController extends Controller implements Initializable {


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
    @FXML private TextField formField;
    @FXML private TextField logField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        dateField.textProperty().addListener(dateFieldListener);
        try {
            List<Exercise> exercises = MainController.dbConnect.getExercises(0);
            comboBox.getItems().addAll("hei","hopp"); //TODO: Legg til exercises navn istedet for hei hopp
            comboBox.getSelectionModel().selectFirst();
        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

    }




    // Workout(int id, Date date, Time time, int duration, int performance, String log)
    @FXML
    public void submitButton() throws IOException{
        String[] dates = dateField.getText().split("/");
        Date date = new Date(Integer.valueOf(dates[0]),Integer.valueOf(dates[1]),Integer.valueOf(dates[2]));
        Workout workout = new Workout(0,date,new Time("00:00:00"),1,0,""); //TODO: Endre slik at dette blir riktig
        MainController.dbConnect.createWorkout(workout);
        changeSceneToIndex();
    }

    private boolean matcher(Pattern pattern, String string){
        return pattern.matcher(string).matches();
    }
    private static final Pattern numberPattern = Pattern.compile("[0-9]+");
    private boolean isValidRepField(){
        return matcher(numberPattern,repField.getText());
    }
    private boolean isValidSettField(){
        return matcher(numberPattern,settField.getText());
    }
    private boolean isValidWeightField(){
        return matcher(numberPattern,weightField.getText());
    }

    @FXML
    public void button_legg_til(){
        if(isValidRepField()&& isValidSettField() && isValidWeightField()){
            ExerciseTrainingInstance exerciseTrainingInstance = new ExerciseTrainingInstance(
                    comboBox.getValue().toString(),
                    Integer.valueOf(settField.getText()),
                    Integer.valueOf(repField.getText()),
                    Integer.valueOf(weightField.getText())
            );
            table.getItems().add(exerciseTrainingInstance);
            repField.clear();
            weightField.clear();
            settField.clear();

        }else{
            //TODO: Legg til feil tilbakemelding. Prøvde å legge til en exercise når feltene er feil! Vebjorn.
        }


    }

    private void setupTable(){ //TODO: Set opp width slik at det blir bra. Vebjørn.
        TableColumn<ExerciseTrainingInstance,String> nameColumn = new TableColumn<>("Navn");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ExerciseTrainingInstance,Integer> repColumn = new TableColumn<>("Repetisjoner");
        repColumn.setMinWidth(100);
        repColumn.setCellValueFactory(new PropertyValueFactory<>("reps"));

        TableColumn<ExerciseTrainingInstance,Integer> settColumn = new TableColumn<>("Sett");
        settColumn.setMinWidth(100);
        settColumn.setCellValueFactory(new PropertyValueFactory<>("sett"));

        TableColumn<ExerciseTrainingInstance,Integer> weightColumn = new TableColumn<>("Vekt");
        weightColumn.setMinWidth(100);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        table.getColumns().clear();
        table.getColumns().addAll(nameColumn,repColumn,settColumn,weightColumn);
    }

    @FXML
    public void buttonRemove() throws IOException{
        ObservableList<ExerciseTrainingInstance> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
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


    private void changeSceneToIndex() throws IOException {
        changeScene("index.fxml");

    }

    @FXML
    public void cancelButton()throws IOException{
        changeSceneToIndex();
    }
    @FXML
    public void saveButton() throws IOException{
        submitButton();
    }









    private static final Pattern dateFormat = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2}");
    private ChangeListener<? super String> dateFieldListener = ((observable, oldValue, newValue) -> {
        if(matcher(dateFormat,newValue)){
            //TODO: LEGG TIL RIKIT I LABEL!!! Vebjørn.
        }
        else {
            //TODO: LEGG TIL FEIL I LABEL!! Vebjørn.
        }
    });









}
