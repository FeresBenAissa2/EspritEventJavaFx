module com.esprit.espritevent {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires mysql.connector.j;
    requires com.gluonhq.maps;
    requires org.apache.poi.poi;

    opens com.esprit.espritevent to javafx.fxml;
    exports com.esprit.espritevent;
    exports com.esprit.espritevent.Controllers;
    exports com.esprit.espritevent.Controllers.Admin;
    exports com.esprit.espritevent.Controllers.Student;
    exports com.esprit.espritevent.Models;

}