package ux;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.Workout;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

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
//<Button mnemonicParsing="false" prefHeight="36.0" prefWidth="219.0" stylesheets="@assets/datebutton.css" text="8/3/16 06:32" />

    protected Button createButton(Workout workout){
        Button button = new Button();
        button.setText("TODO: LEGG TIL TEXT");
        button.setMnemonicParsing(false);
        button.setPrefHeight(36.0);
        button.setPrefWidth(219);
        button.setStyle("-fx-text-fill: #fff;-fx-background-color: #028090;-fx-font-size: 16px;-fx-text-alignment: center;-fx-border-radius: 0px;-fx-font-family: 'Avenir'");
        return button;
    }
    protected void setGridPaneElements(GridPane gridPane, List<Workout> workouts){
        gridPane.getChildren().clear();
        gridPane.setVgap(10);
        int maxSize = workouts.size();
        for (int index = 0;index<maxSize;index++){
            Button button = new Button();
            gridPane.add(createButton(workouts.get(index)),0,index);
        }
    }
}
