package com.esprit.espritevent.Controllers.Student;

import com.esprit.espritevent.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public BorderPane student_parent  ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactroy().getStudentSelectedMenuItem().addListener(((observableValue, oldVal, newVal) ->{
            switch (newVal) {
//                case "NewClubProposal" -> student_parent.setCenter(Model.getInstance().getViewFactroy().getManageClubsView());
                case "Profile" -> student_parent.setCenter(Model.getInstance().getViewFactroy().getProfileView());
                default -> student_parent.setCenter(Model.getInstance().getViewFactroy().getDahsboardView());
            }
        } ));
    }
}