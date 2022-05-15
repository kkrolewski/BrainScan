package pl.polsl.cotbd.model.annotation;

/**
 * <i>Annotation</i>
 *
 * @author krzys
 * @version 1.0
 * @created 03-gru-2021 21:30:30
 */
public abstract class Annotation {

  //private 2-D array pointsArray;
  private String name;

  public Annotation(String name) {
    this.name = name;
  }

	
  abstract void draw();
}//end Annotation