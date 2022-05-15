module pl.polsl.cotbd {
  requires javafx.controls;
  requires niftijio;
  requires javafx.fxml;
  requires java.desktop;
  opens pl.polsl.cotbd to javafx.fxml;
  exports pl.polsl.cotbd;
  exports pl.polsl.cotbd.view;
  opens pl.polsl.cotbd.view to javafx.fxml;
  exports pl.polsl.cotbd.model;
  opens pl.polsl.cotbd.model to javafx.fxml;
  exports pl.polsl.cotbd.model.annotation;
  opens pl.polsl.cotbd.model.annotation to javafx.fxml;
}