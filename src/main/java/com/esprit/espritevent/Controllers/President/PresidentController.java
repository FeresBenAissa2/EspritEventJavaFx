package com.esprit.espritevent.Controllers.President;

import com.esprit.espritevent.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PresidentController implements Initializable {
    public BorderPane president_parent  ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactroy().getPresedentSelectedMenuItem().addListener(((observableValue, oldVal, newVal) ->{
            switch (newVal) {
//                case "NewClubProposal" -> president_parent.setCenter(Model.getInstance().getViewFactroy().getManageClubsView());
//                case "EventAvailable" -> president_parent.setCenter(Model.getInstance().getViewFactroy().getManageEventsView());
                case "Profile" -> president_parent.setCenter(Model.getInstance().getViewFactroy().getProfileView());
                default -> president_parent .setCenter(Model.getInstance().getViewFactroy().getDahsboardView());
            }
        } ));
    }
}