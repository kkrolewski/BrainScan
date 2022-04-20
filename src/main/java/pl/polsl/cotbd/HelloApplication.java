package pl.polsl.cotbd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.ericbarnhill.niftijio.NiftiVolume;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Check out this brain damage!");
        stage.setScene(scene);
        stage.show();
    }

    static void test() {
        try {
            NiftiVolume volume = NiftiVolume.read("Task02_Heart/imagesTr/la_003.nii.gz");
            System.out.println(volume.data.sizeX());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
        test();
    }
}