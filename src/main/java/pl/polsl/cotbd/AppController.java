package pl.polsl.cotbd;

import com.ericbarnhill.niftijio.FourDimensionalArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.polsl.cotbd.model.Image;
import java.awt.image.BufferedImage;


import java.io.*;
import java.nio.IntBuffer;

public class AppController {
    private static final double ARRAY_LIMIT = 2000;
    private static final int DEFAULT_SLICE = 10;
    private static final int ALPHA = 24;
    private static final int RED = 16;
    private static final int BLUE = 0;
    private static final int GREEN = 8;
    private static final int DEFAULT_DIMENSION = 0;
    private static final int ALPHA_CHANNEL = 255;
    private static final int BYTE_MAX = 255;


    @FXML
    public Slider yDim_slider;
    @FXML
    public Slider zDim_slider;
    @FXML
    public ImageView imageView_1;
    @FXML
    public ImageView imageView_2;
    @FXML
    public ImageView imageView_3;
    @FXML
    public Slider xDim_slider;
    @FXML
    public Button openFile_button;
    @FXML
    public Button saveFile_button;
    @FXML
    public Label imageView_1Label;
    @FXML
    public Label imageView_2Label;
    @FXML
    public Label imageView_3Label;



    private int[] horizontalBuffer;
    private int[] verticalBuffer;
    private int[] depthBuffer;
    FourDimensionalArray arr;

    @FXML
    public void initialize() {
        initalizeFonts();

        this.xDim_slider.valueProperty().addListener((observable, oldValue, newValue) -> {

            Axis horizontal = Axis.HORIZONTAL;
            horizontal.toARGBArray(horizontalBuffer, arr, newValue.intValue());
            IntBuffer intBuffer = IntBuffer.wrap(horizontalBuffer);
            PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
            PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(arr.sizeX(), arr.sizeY(), intBuffer, pixelFormat);
            javafx.scene.image.Image img = new WritableImage(pixelBuffer);
            imageView_1.setImage(img);
        });
        this.yDim_slider.valueProperty().addListener((observable, oldValue, newValue) -> {

            Axis vertical = Axis.VERTICAL;
            vertical.toARGBArray(verticalBuffer, arr, newValue.intValue());
            IntBuffer intBuffer = IntBuffer.wrap(verticalBuffer);
            PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
            PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(arr.sizeY(), arr.sizeZ(), intBuffer, pixelFormat);
            javafx.scene.image.Image img = new WritableImage(pixelBuffer);
            imageView_2.setImage(img);
        });
        this.zDim_slider.valueProperty().addListener((observable, oldValue, newValue) -> {

            Axis depth = Axis.DEPTH;
            depth.toARGBArray(depthBuffer, arr, newValue.intValue());
            IntBuffer intBuffer = IntBuffer.wrap(depthBuffer);
            PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
            PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(arr.sizeX(), arr.sizeZ(), intBuffer, pixelFormat);
            javafx.scene.image.Image img = new WritableImage(pixelBuffer);
            imageView_3.setImage(img);
        });
    }

    enum Axis{
        HORIZONTAL{
            @Override
            public void toARGBArray(int[] buf,FourDimensionalArray data, int slice) {
                for(int x = 0; x <data.sizeX() - 1; x +=1){
                    for(int y= 0 ;y<data.sizeY();y++){

                        buf[x + y * data.sizeX()] = AppController.ALPHA_CHANNEL << AppController.ALPHA | // Alpha channel
                                ((int) ((data.get(x, y, slice, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.RED | // red channel
                                ((int) ((data.get(x, y, slice, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.GREEN | // green channel
                                ((int) ((data.get(x, y, slice, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.BLUE; //  channel

                    }
                }
            }
            @Override
            public javafx.scene.image.Image transform(FourDimensionalArray data, int[] buf) {
                this.toARGBArray(buf, data, AppController.DEFAULT_SLICE);
                IntBuffer intBuffer = IntBuffer.wrap(buf);
                PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
                PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(data.sizeX(), data.sizeY(), intBuffer, pixelFormat);
                javafx.scene.image.Image img = new WritableImage(pixelBuffer);
                return img;
            }
        },
        VERTICAL{
            @Override
            public void toARGBArray(int[] buf,FourDimensionalArray data, int slice) {
                for(int y = 0; y <data.sizeY() - 1; y +=1){
                    for(int z= 0 ;z<data.sizeZ();z++){

                        buf[y + z * data.sizeX()] = AppController.ALPHA_CHANNEL << AppController.ALPHA | // Alpha channel
                                ((int) ((data.get(slice, y, z, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.RED | // red channel
                                ((int) ((data.get(slice, y, z, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.GREEN | // green channel
                                ((int) ((data.get(slice, y, z, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.BLUE; //  channel

                    }
                }
            }
            @Override
            public javafx.scene.image.Image transform(FourDimensionalArray data, int[] buf) {
                this.toARGBArray(buf, data, AppController.DEFAULT_SLICE);
                IntBuffer intBuffer = IntBuffer.wrap(buf);
                PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
                PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(data.sizeY(), data.sizeZ(), intBuffer, pixelFormat);
                javafx.scene.image.Image img = new WritableImage(pixelBuffer);
                return img;
            }
        },
        DEPTH{
            @Override
            public void toARGBArray(int[] buf,FourDimensionalArray data, int slice) {
                for(int x = 0; x <data.sizeX() - 1; x +=1){
                    for(int z= 0 ;z<data.sizeZ();z++){

                        buf[x + z * data.sizeX()] = AppController.ALPHA_CHANNEL << AppController.ALPHA | // Alpha channel
                                ((int) ((data.get(x, slice, z, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.RED | // red channel
                                ((int) ((data.get(x, slice, z, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.GREEN | // green channel
                                ((int) ((data.get(x, slice, z, AppController.DEFAULT_DIMENSION) / AppController.ARRAY_LIMIT * AppController.BYTE_MAX))) << AppController.BLUE; //  channel

                    }
                }
            }

            @Override
            public javafx.scene.image.Image transform(FourDimensionalArray data, int[] buf) {
                this.toARGBArray(buf, data, AppController.DEFAULT_SLICE);
                IntBuffer intBuffer = IntBuffer.wrap(buf);
                PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
                PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer<>(data.sizeX(), data.sizeZ(), intBuffer, pixelFormat);
                javafx.scene.image.Image img = new WritableImage(pixelBuffer);
                return img;
            }
        };

        public abstract void toARGBArray(int[] buf,FourDimensionalArray data, int slice);
        public abstract javafx.scene.image.Image transform(FourDimensionalArray data, int[] buf);
    }



    public void openFile_onPressed(ActionEvent actionEvent) throws IOException  {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.nii", "*.nii");
        FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("*.nii.gz", "*.nii.gz");
        chooser.getExtensionFilters().add(extFilter);
        chooser.getExtensionFilters().add(extFilter2);
        chooser.setTitle("Open nifti file");
        File file =  chooser.showOpenDialog(new Stage());
        this.setImage(file.getAbsolutePath());
        this.xDim_slider.setDisable(false);
        this.yDim_slider.setDisable(false);
        this.zDim_slider.setDisable(false);
        this.xDim_slider.setValue(this.xDim_slider.getMax()/2);
        this.yDim_slider.setValue(this.yDim_slider.getMax()/2);
        this.zDim_slider.setValue(this.zDim_slider.getMax()/2);
        disableLabels();
    }
    void setImage(String path) throws IOException{
        Image img = Files.readNifti(path);
        this.arr = img.getData();
        Axis horizontal = Axis.HORIZONTAL;
        Axis vertical = Axis.VERTICAL;
        Axis depth = Axis.DEPTH;
        this.horizontalBuffer = new int[arr.sizeX() * arr.sizeY()];
        this.verticalBuffer = new int[arr.sizeY() * arr.sizeZ()];
        this.depthBuffer = new int[arr.sizeX() * arr.sizeZ()];
        this.imageView_1.setImage(horizontal.transform(arr, this.horizontalBuffer));
        this.imageView_2.setImage(vertical.transform(arr, this.verticalBuffer));
        this.imageView_3.setImage(depth.transform(arr, this.depthBuffer));
        this.xDim_slider.setMax(arr.sizeZ() -1);
        this.yDim_slider.setMax(this.arr.sizeX() -1);
        this.zDim_slider.setMax(this.arr.sizeY() -1);
    }

    public void saveFile_onPressed(ActionEvent actionEvent) {
    }

    private void initalizeFonts()
    {
        imageView_1Label.setFont(Font.font("Arial", 26));
        imageView_1Label.setTextFill(Color.CADETBLUE);
        imageView_2Label.setFont(Font.font("Arial", 26));
        imageView_2Label.setTextFill(Color.CADETBLUE);
        imageView_3Label.setFont(Font.font("Arial", 26));
        imageView_3Label.setTextFill(Color.CADETBLUE);
    }

    private void disableLabels()
    {
        imageView_1Label.setText("");
        imageView_2Label.setText("");
        imageView_3Label.setText("");
    }
}