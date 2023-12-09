package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Model;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button dashboard_btn;
    public Button manage_clubs_btn;
    public Button manage_events_btn;
    public Button manage_premises_btn;
    @FXML
    public Button logout_btn;
    public Button profile_btn;
    public Button manage_club_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
        logout_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
              Model.getInstance().getViewFactroy().showLoginWindow();
              Model.getInstance().setAdminLoginSuccessFlag(false);
            }
        });
    }
    private void addListeners () {
        dashboard_btn.setOnAction(event -> onDashboard());
       // manage_clubs_btn.setOnAction(event -> onManageClubs());
        manage_events_btn.setOnAction(event -> onManageEvents());
    }
    private void onDashboard (){

        Model.getInstance().getViewFactroy().getAdminSelectedMenuItem().set("Dashboard");
    }
    private void onManageClubs (){
        Model.getInstance().getViewFactroy().getAdminSelectedMenuItem().set("ManageClubs");
    }
    private void onManageEvents (){
        Model.getInstance().getViewFactroy().getAdminSelectedMenuItem().set("ManageEvents");
    }
}
