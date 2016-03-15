package ux;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Workout;

import java.io.IOException;
import java.util.List;

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

    protected Button createButton(Workout workout){
        Button button = new Button();
        button.setText(workout.getDate().toString());
        button.setMnemonicParsing(false);
        button.setPrefHeight(36.0);
        button.setPrefWidth(219);
        button.setStyle("-fx-text-fill: #fff;-fx-background-color: #028090;-fx-font-size: 16px;-fx-text-alignment: center;-fx-border-radius: 0px;-fx-font-family: 'Avenir'");
        return button;
    }
    protected void setGridPaneElements(VBox gridPane, List<Workout> workouts){
        gridPane.getChildren().clear();
        int maxSize = workouts.size();
        for (int index = 0;index<maxSize;index++){
            gridPane.getChildren().add(index,createButton(workouts.get(index)));
        }
    }
}
