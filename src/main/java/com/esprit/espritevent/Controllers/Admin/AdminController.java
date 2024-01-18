package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    public BorderPane admin_parent ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactroy().getAdminSelectedMenuItem().addListener(((observableValue, oldVal, newVal) ->{
            switch (newVal) {
                case "ManageClubs" -> admin_parent.setCenter(Model.getInstance().getViewFactroy().getManageClubsView());
                case "ManageEvents" -> admin_parent.setCenter(Model.getInstance().getViewFactroy().getManageEventsView());
                case "ManageLocals" -> admin_parent.setCenter(Model.getInstance().getViewFactroy().getManageLocalsView());
                case "ProcessClubCreationRequest" -> admin_parent.setCenter(Model.getInstance().getViewFactroy().getProcessClubCreationRequestView());
                case "Profile" -> admin_parent.setCenter(Model.getInstance().getViewFactroy().getProfileView());
                default -> admin_parent.setCenter(Model.getInstance().getViewFactroy().getDahsboardView());
            }
        } ));
    }
}
