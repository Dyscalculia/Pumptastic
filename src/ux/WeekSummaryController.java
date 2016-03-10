package ux;

import javafx.fxml.Initializable;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WeekSummaryController extends Controller implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void changeSceneToIndex() throws IOException {
        changeScene("index.fxml");
    }




}
