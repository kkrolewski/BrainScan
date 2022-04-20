package pl.polsl.cotbd.model;

import com.ericbarnhill.niftijio.FourDimensionalArray;
import pl.polsl.cotbd.model.annotation.Annotation;

import java.util.LinkedList;
import java.util.List;

/**
 * @author krzys
 * @version 1.0
 * @created 03-gru-2021 21:30:40
 */
public class Image {

    private final List<Annotation> annotations;
    private final FourDimensionalArray data;
    private final float voxelSize;

    public Image(FourDimensionalArray data, float voxelSize) {
        this.data = data;
        this.voxelSize = voxelSize;
        this.annotations = new LinkedList<>();
    }

    public void addAnnotation(Annotation annotation) {
        this.annotations.add(annotation);
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public FourDimensionalArray getData() {
        return this.data;
    }

    public float getVoxelSize() {
        return this.voxelSize;
    }
}//end Image