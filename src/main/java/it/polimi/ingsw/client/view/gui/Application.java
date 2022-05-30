package it.polimi.ingsw.client.view.gui;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage)throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/startNewGame.fxml"));
        Parent root = loader.load();
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getResource("startNewGame.fxml"));
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("gui/startNewGame.fxml")));

        Scene scene = new Scene(root, 600, 400);
        primaryStage.minHeightProperty().setValue(400);
        primaryStage.minWidthProperty().setValue(600);
        primaryStage.setTitle("Start New Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}