package pl.polsl.cotbd;


import pl.polsl.cotbd.model.Image;
import pl.polsl.cotbd.model.annotation.Annotation;

/**
 * @author krzys
 * @version 1.0
 * @created 03-gru-2021 21:30:41
 */
public class ImageManager {

    //private array annotationsArray;
    private int currentRotation;
    private int currentZoom;
    private final CalculationOperations calculation;
    private final Controller controller;
    private Image currentImage;


    public ImageManager(CalculationOperations calculation, Controller controller) {
        this.calculation = calculation;
        this.controller = controller;
    }

    public CalculationOperations getCalculation() {
        return calculation;
    }

    public Controller getController() {
        return controller;
    }

    public void getFrame() {

    }

    public void setImage() {

    }

    public void rotate(int parameter) {

    }

    public void annotate(Annotation annotation) {

    }


    public void zoom(int value) {

    }
}//end ImageManager