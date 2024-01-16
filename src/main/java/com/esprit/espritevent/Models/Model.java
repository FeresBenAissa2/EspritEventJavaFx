package com.esprit.espritevent.Models;

import com.esprit.espritevent.Utils.DataSource;
import com.esprit.espritevent.Views.ViewFactroy;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Model {
    private final ViewFactroy viewFactroy;
    private static Model model;
    // Admin data section
    private User user;
    private boolean adminLoginSuccessFlag;
    private boolean studentLoginSuccessFlag;
    private boolean presidentLoginSuccessFlag;
    private Model() {

        this.viewFactroy = new ViewFactroy();
        // admin data section
        this.adminLoginSuccessFlag = false;
        this.studentLoginSuccessFlag = false;
        this.presidentLoginSuccessFlag = false;
        this.user = new User();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactroy getViewFactroy() {
        return viewFactroy;
    }

    // Admin method section

    public boolean getAdminLoginSuccessFlag() {
        return adminLoginSuccessFlag;
    }

    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {
        this.adminLoginSuccessFlag = adminLoginSuccessFlag;
    }


    public boolean getStudentLoginSuccessFlag() {
        return studentLoginSuccessFlag;
    }

    public void setStudentLoginSuccessFlag(boolean studentLoginSuccessFlag) {
        this.studentLoginSuccessFlag = studentLoginSuccessFlag;
    }

    public boolean getPresidentLoginSuccessFlag() {
        return presidentLoginSuccessFlag;
    }

    public void setPresidentLoginSuccessFlag(boolean presidentLoginSuccessFlag) {
        this.presidentLoginSuccessFlag = presidentLoginSuccessFlag;
    }

    public User getUser() {
        return user;
    }

    public void setAdmin(User admin) {
        this.user = user;
    }
    public void evaluateUserCred(String username ,String password ){
            // na3mel login
            // naffecti el data kenou admin wela kenou student ila5
        try {
        Connection conn = DataSource.getInstance().getConn();
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * From user where username = ? ");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.isBeforeFirst()) {
            System.out.println("User not found in database ");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Provided credentials are incorrect ! ");
            alert.show();
        } else {
            while (resultSet.next()) {
                String retrievedPassword = resultSet.getString("password");
                String retrievedRole = resultSet.getString("role");
                System.out.println(retrievedPassword);
                System.out.println(retrievedRole);
                if (retrievedPassword.equals(password)) {
                    // ba3ed selon el role mais taw juste bech na3ml 3ala ases admin puisque me 3andi ken admin
                    this.user.setId(resultSet.getInt("id_user"));
                    this.user.setUsername(resultSet.getString("username"));
                    this.user.setPassword(resultSet.getString("password"));
                    this.user.setAge(resultSet.getInt("age"));
                    this.user.setNom(resultSet.getString("nom"));
                    this.user.setPrenom(resultSet.getString("prenom"));
                    this.user.setPhone(resultSet.getLong("phone"));
                    this.user.setRole(resultSet.getString("role"));
                    if(this.user.getRole().equals(Roles.ADMIN.name())) {
                        this.adminLoginSuccessFlag = true;
                        this.studentLoginSuccessFlag = false;
                        this.presidentLoginSuccessFlag = false;
                    }else if (this.user.getRole().equals(Roles.STUDENT.name())){
                        this.adminLoginSuccessFlag = false;
                        this.studentLoginSuccessFlag = true;
                        this.presidentLoginSuccessFlag = false;
                    } else{
                        this.adminLoginSuccessFlag = false;
                        this.studentLoginSuccessFlag = false;
                        this.presidentLoginSuccessFlag = true;
                    }
                } else {
                    System.out.println("Password does not match");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided credentials are incorrect ! ");
                    alert.show();

                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
