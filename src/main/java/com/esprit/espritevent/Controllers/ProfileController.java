package com.esprit.espritevent.Controllers;

import com.esprit.espritevent.Models.Model;
import com.esprit.espritevent.Models.User;
import com.esprit.espritevent.Services.User.ServiceUser;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileController implements Initializable {
    public TextField name_fid;
    public TextField prenom_fid;
    public TextField role_fid;
    public VBox vbox_map_fid;
    public TextField username_fid;
    public TextField password_fid;
    public TextField email_fid;
    public Button update_profile_btn;
    public User user;
    public ServiceUser serviceUser = new ServiceUser();
    public Text nom_err_fid;
    public Text prenom_err_fid;
    public Text role_err_fid;
    public Text username_err_fid;
    public Text password_err_fid;
    public Text email_err_fid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        user = Model.getInstance().getUser();
        System.out.println(user);
        if (user != null) {
            // Set values of text fields with user data
            username_fid.setText(user.getUsername());
            password_fid.setText(user.getPassword());
            name_fid.setText(user.getNom());
            prenom_fid.setText(user.getPrenom());
            role_fid.setText(user.getRole());
            email_fid.setText(user.getEmail());
            role_fid.setDisable(true);
            // Set other fields accordingly
        }

        update_profile_btn.setOnAction(event ->handleUpdateProfile());
    }

    private void handleUpdateProfile() {
        // Get updated values from text fields
        String updatedUsername = username_fid.getText();
        String updatedPassword = password_fid.getText();
        String updatedNom = name_fid.getText();
        String updatedPrenom = prenom_fid.getText();
        String updatedEmail = email_fid.getText();

        // Update the user object and set error messages for empty fields
        if (isEmpty(updatedUsername)) {
            username_err_fid.setText("Username is required.");
        }
        if (isEmpty(updatedPassword)) {
            password_err_fid.setText("Password is required.");
        }
        if (isEmpty(updatedNom)) {
            nom_err_fid.setText("Nom is required.");
        }
        if (isEmpty(updatedPrenom)) {
            prenom_err_fid.setText("Prenom is required.");
        }
        if (isEmpty(updatedEmail)) {
            email_err_fid.setText("Email is required.");
        }
        // Check email format
        if (!isValidEmail(email_fid.getText())) {
            // Set error message for invalid email format
            email_err_fid.setText("Invalid email format.");
            return;
        }
        // Check if any field is empty
        if (isEmpty(updatedUsername) || isEmpty(updatedPassword) || isEmpty(updatedNom) || isEmpty(updatedPrenom) || isEmpty(updatedEmail)) {
            return; // Exit early if any field is empty
        }

        user.setEmail(updatedEmail);
        user.setPassword(updatedPassword);
        user.setNom(updatedNom);
        user.setPrenom(updatedPrenom);
        user.setUsername(updatedUsername);
        System.out.println(user.getEmail());
        // Clear previous error messages
        clearErrorMessages();

        // Call the service method to update the user in the database
        try {
            serviceUser.updateUser(user);
            // Display a success message or handle success scenario
        } catch (SQLException e) {
            e.printStackTrace();
            // Display an error message or handle the error scenario
        }
    }
    private void clearErrorMessages() {
        // Clear all error messages
        username_err_fid.setText("");
        password_err_fid.setText("");
        nom_err_fid.setText("");
        prenom_err_fid.setText("");
        email_err_fid.setText("");
    }
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
