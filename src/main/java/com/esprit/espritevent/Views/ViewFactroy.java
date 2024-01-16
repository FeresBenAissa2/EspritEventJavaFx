package com.esprit.espritevent.Views;

import com.esprit.espritevent.Controllers.Admin.AdminController;
import com.esprit.espritevent.Controllers.SignUpController;
import com.esprit.espritevent.Controllers.Student.StudentController;
import com.mysql.cj.conf.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactroy {
    //Admin views
    private final SimpleStringProperty adminSelectedMenuItem;
    private AnchorPane dahsboardView;
    private AnchorPane manageClubsView;
    private AnchorPane manageEventsView;
    private AnchorPane manageLocalsView;
    private AnchorPane ProcessClubCreationRequestView;
    Stage stage = new Stage();
    public ViewFactroy() {
            this.adminSelectedMenuItem = new SimpleStringProperty("");
    }

    public SimpleStringProperty getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }



    public AnchorPane getDahsboardView (){
        if(dahsboardView == null){
            try {
                dahsboardView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Dashboard.fxml")).load();
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        return dahsboardView;
    }
    public AnchorPane getManageClubsView (){
        if(manageClubsView == null){
            try {
                manageClubsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageClubs.fxml")).load();
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        return manageClubsView;
    }
    public AnchorPane getManageEventsView (){
        if(manageEventsView == null){
            try {
                manageEventsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageEvents.fxml")).load();
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        return manageEventsView;
    }

    public AnchorPane getManageLocalsView (){
        if(manageLocalsView == null){
            try {
                manageLocalsView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ManageLocals.fxml")).load();
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        return manageLocalsView;
    }
    public AnchorPane getProcessClubCreationRequestView (){
        if(ProcessClubCreationRequestView == null){
            try {
                ProcessClubCreationRequestView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ProcessClubCreationRequest.fxml")).load();
            }catch (Exception e ){
                e.printStackTrace();
            }

        }
        return ProcessClubCreationRequestView;
    }

    public void showLoginWindow (){
       FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }
    public void showSignUpWindow (){
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Fxml/Signup.fxml"));
        createStage(loader);
    }
    public void showAdminWindow (){
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
       createStage(loader);
    }

    public void showStudentWindow (){
        FXMLLoader loader =  new FXMLLoader(getClass().getResource("/Fxml/Student/Student.fxml"));
        StudentController studentController = new StudentController();
        loader.setController(studentController);
        createStage(loader);
    }


    
    private void createStage (FXMLLoader loader){
        Scene scene = null;
        try
        {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Esprit Event");
        stage.show();
    }

    public void closeStage(Stage stage){
        stage.close();
    }
}
