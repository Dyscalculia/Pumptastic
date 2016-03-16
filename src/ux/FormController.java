package ux;


import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.Exercise;
import utils.Time;
import utils.Workout;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    @FXML private TextArea logField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTable();
        setupListeners();
        try {
            setUpComboBox();
        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void setUpComboBox(){
        Collection exercisesInTable = (Collection)table.getItems().stream().map( a-> ((Exercise) a).getName()).collect(Collectors.toList());
        try{
            if(table.getItems().size() == MainController.dbConnect.getExercisesLabels(null).size()){
                comboBox.getItems().clear();
                comboBox.getItems().addAll("No more exercises!");
                comboBox.getSelectionModel().selectFirst();
            }else {
                comboBox.getItems().clear();
                List<Exercise> exercises = MainController.dbConnect.getExercisesLabels(null);
                comboBox.getItems().addAll(exercises.stream().map(a -> a.getName()).filter(a -> !exercisesInTable.contains(a)).toArray());
                comboBox.getSelectionModel().selectFirst();
            }
        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }
    }




    // Workout(int id, Date date, Time time, int duration, int performance, String log)
    @FXML
    public void submitButton() throws IOException, SQLException{
        if(isValidFormField()   && isValidTimeFIeld()   && isValidDateField() && isValidTempField()
                ) {

            String[] dates = dateField.getText().split("/");

            GregorianCalendar calendar = new GregorianCalendar(Integer.valueOf(dates[2]),Integer.valueOf(dates[1])-1,Integer.valueOf(dates[0]));
            Date date = new Date(calendar.getTimeInMillis());
            Time time = new Time(timeField.getText());
            int form = Integer.valueOf(formField.getText());
            int tempValue = Integer.valueOf(tempField.getText());
            String wValue = wField.getText();
            String log = logField.getText();
            ObservableList e = table.getItems();
            Workout workout;
            //public Workout(Integer id, Date date, Time time, int duration, Integer performance, String log, String air, int audience,Exercise... exercises) {
            //Workout(Integer id, Date date, Time time, int duration, Integer performance, String log, Integer temperature, String weather,Collection<Exercise> exercises)
            if(outButton.isSelected()){ //ute
                workout = new Workout(null,date,time,1,form,log,wValue,tempValue,e);
            }else{ // inne
                workout = new Workout(null,date,time,1,form,log,tempValue,wValue,e);
            }
            MainController.dbConnect.insertWorkout(workout);
            changeSceneToIndex();
        }
        else {
            //TODO: Kan ha noe feedback når submitter at et eller annet felt er feil... Vejbørn.
        }
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
    private static final Pattern timeFieldPattern = Pattern.compile("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}");
    private boolean isValidTimeFIeld(){
        return matcher(timeFieldPattern,timeField.getText());
    }
    private boolean isValidFormField(){
        return matcher(numberPattern,formField.getText());
    }
    private boolean isValidDateField(){
        return matcher(dateFormat,dateField.getText());
    }
    private boolean isValidTempField(){
        return matcher(numberPattern,tempField.getText());
    }

    @FXML
    public void button_legg_til(){
        if(isValidRepField()&& isValidSettField() && isValidWeightField() && !comboBox.getItems().get(0).equals( "No more exercises!")){
            Exercise Exercise = new Exercise(
            		null,
                    comboBox.getValue().toString(),
                    Integer.valueOf(settField.getText()),
                    Integer.valueOf(repField.getText()),
                    Integer.valueOf(weightField.getText())
            );
            table.getItems().add(Exercise);
            repField.setText("");
            weightField.setText("");
            settField.setText("");
            setUpComboBox();

        }else{
            //TODO: Legg til feil tilbakemelding. Prøvde å legge til en exercise når feltene er feil! Vebjorn.
        }
    }

    private void setupTable(){ //TODO: Set opp width slik at det blir bra. Vebjørn.
        TableColumn<Exercise,String> nameColumn = new TableColumn<>("Navn");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Exercise,Integer> repColumn = new TableColumn<>("Repetisjoner");
        repColumn.setMinWidth(100);
        repColumn.setCellValueFactory(new PropertyValueFactory<>("reps"));

        TableColumn<Exercise,Integer> settColumn = new TableColumn<>("Sett");
        settColumn.setMinWidth(100);
        settColumn.setCellValueFactory(new PropertyValueFactory<>("sett"));

        TableColumn<Exercise,Integer> weightColumn = new TableColumn<>("Vekt");
        weightColumn.setMinWidth(100);
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));

        table.getColumns().clear();
        table.getColumns().addAll(nameColumn,repColumn,settColumn,weightColumn);
        table.setPlaceholder(new Label("Du har ikke lagt inn noen øvelser"));
    }

    @FXML
    public void buttonRemove() throws IOException{
        ObservableList<Exercise> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
    }

    @FXML
    public void buttonToggle(){
        if(outButton.isSelected()){
            tempField.setPromptText("Temperatur");
            wField.setPromptText("Værforhold");
        }else{
            tempField.setPromptText("Tilskuere");
            wField.setPromptText("Luftforhold");
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
    public void saveButton() throws IOException, SQLException{
        submitButton();
    }

    private void setTextFieldWrong(TextField textField){
        textField.setStyle("-fx-background-color: #DD1C1A; -fx-prompt-text-fill: white");
    }

    private void setTextFieldRight(TextField textField){
        textField.setStyle("-fx-background-color: white");
    }






    private void setupListeners(){
        dateField.textProperty().addListener(dateFieldListener);
        repField.textProperty().addListener(repFieldListener);
        settField.textProperty().addListener(settFieldListener);
        weightField.textProperty().addListener(weightFieldListener);
        timeField.textProperty().addListener(timeFieldListener);
        formField.textProperty().addListener(formFieldListener);
        tempField.textProperty().addListener(tempFieldListener);

        //legger til hva som er påkrevd
        setTextFieldWrong(dateField);
        setTextFieldWrong(formField);
        setTextFieldWrong(repField);
        setTextFieldWrong(settField);
        setTextFieldWrong(weightField);
        setTextFieldWrong(timeField);
        setTextFieldWrong(tempField);
    }

    private static final Pattern dateFormat = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}");
    private ChangeListener<? super String> dateFieldListener = ((observable, oldValue, newValue) -> {
        if(isValidDateField()){
            setTextFieldRight(dateField);
        }
        else {
            setTextFieldWrong(dateField);
        }
    });

    private ChangeListener<? super String> repFieldListener = ((observable, oldValue, newValue) -> {
        if(isValidRepField()){
            setTextFieldRight(repField);
        }
        else{
            setTextFieldWrong(repField);
        }
    });

    private ChangeListener<? super String> settFieldListener = ((observable, oldValue, newValue) -> {
       if(isValidSettField()){
           setTextFieldRight(settField);
       }else{
           setTextFieldWrong(settField);
       }
    });

    private ChangeListener<? super String> weightFieldListener =((observable, oldValue, newValue) -> {
       if(isValidWeightField()){
           setTextFieldRight(weightField);
       }else{
           setTextFieldWrong(weightField);
       }
    });

    private ChangeListener<? super String> timeFieldListener = ((observable, oldValue, newValue) -> {
        if(isValidTimeFIeld()){
            setTextFieldRight(timeField);
        }else{
            setTextFieldWrong(timeField);
        }
    });

    private ChangeListener<? super String> formFieldListener = ((observable, oldValue, newValue) -> {
       if (isValidFormField()){
           setTextFieldRight(formField);
       }else{
           setTextFieldWrong(formField);
       }
    });

    private ChangeListener<? super String> tempFieldListener = ((observable, oldValue, newValue) -> {
       if (isValidTempField()){
           setTextFieldRight(tempField);
       }else{
           setTextFieldWrong(tempField);
       }
    });
//TODO: ADD LISTENER TIL LUFT/TEMPERATUR & FORHOLD/TILSKUERE
}
