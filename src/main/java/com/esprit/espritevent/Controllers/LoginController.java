package com.esprit.espritevent.Controllers;

import com.esprit.espritevent.Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
