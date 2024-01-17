package com.esprit.espritevent.Controllers;

import com.esprit.espritevent.Models.Model;
import com.gluonhq.maps.MapView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
public class LoginController implements Initializable {
    @FXML
    public TextField username_fid;
    @FXML
    public TextField password_fid;
    @FXML
    public Button login_btn;
    @FXML
    public Text error_lbl;
    public Button signup_btn;
    public VBox vbox_map_fid;

    private final MapPoint espritCharguia = new MapPoint(48.8583702,2.2944813);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MapView mapView = createMapView();
        vbox_map_fid.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
//        login_btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                Database.logInUser(event,username_fid.getText(),password_fid.getText());
//            }
//        });
        login_btn.setOnAction(event -> onLogin());
        signup_btn.setOnAction(event -> {
            Stage stage = (Stage) username_fid.getScene().getWindow();
            Model.getInstance().getViewFactroy().closeStage(stage);
            Model.getInstance().getViewFactroy().showSignUpWindow();
        }
        );
    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(500, 400);

        // Add a MapLayer with a MapPoint (espritCharguia)
        MapLayer mapLayer = new MapLayer();

        return mapView;
    }
    private void onLogin () {
        Stage stage = (Stage) username_fid.getScene().getWindow();
        Model.getInstance().evaluateUserCred(username_fid.getText(),password_fid.getText());
        if( Model.getInstance().getAdminLoginSuccessFlag()) {
            Model.getInstance().getViewFactroy().closeStage(stage);
            Model.getInstance().getViewFactroy().showAdminWindow();
        }else if( Model.getInstance().getStudentLoginSuccessFlag()){
            Model.getInstance().getViewFactroy().closeStage(stage);
            Model.getInstance().getViewFactroy().showStudentWindow();
        }else if( Model.getInstance().getPresidentLoginSuccessFlag()){
            Model.getInstance().getViewFactroy().closeStage(stage);
            Model.getInstance().getViewFactroy().showAdminWindow();
        }
        else {
            username_fid.setText("");
            password_fid.setText("");
            error_lbl.setText("No such Login credentials");
        }

    }
}
