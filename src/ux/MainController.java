package ux;
import Backend.DBConnect;
import Backend.DBConnectMock;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController  extends Application{

    private final DBConnect dbConnect;

    public MainController(DBConnect dbConnect){
        this.dbConnect =dbConnect;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader index = new FXMLLoader(MainController.class.getResource("index.fxml"));
        index.setController(new IndexController(dbConnect));
        FXMLLoader form = new FXMLLoader(MainController.class.getResource("form.fxml"));
        form.setController(new FormController(dbConnect));
        Parent root = index.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        DBConnect dbConnect = new DBConnectMock();
        MainController mainController = new MainController(dbConnect);
        mainController.launch(args);
    }

}
