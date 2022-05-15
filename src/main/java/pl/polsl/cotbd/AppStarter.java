package pl.polsl.cotbd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class AppStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppStarter.class.getResource("cotbd.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 700);
        scene.getRoot().setStyle("" +
                "-fx-background-image: url('https://c.wallhere.com/photos/26/51/line_light_background_bright-685894.jpg!d'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: 1000 700; " +
                "-fx-background-position: center center;");
        stage.setResizable(false);
        stage.setTitle("Check out This Brain Damage");
        stage.getIcons().add(new Image("https://images.freeimages.com/images/small-previews/e42/no-description-1301831.jpg"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}