package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Club;
import com.esprit.espritevent.Models.MailSender;
import com.esprit.espritevent.Services.Club.ServiceClub;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProcessClubCreationRequestController  implements Initializable  {
    public TableView<Club> Club_request_table_view_fid;
    public TableColumn<Club,Long> column_club_id_fid;
    public TableColumn <Club,String> column_club_name_fid;
    public TableColumn <Club,String> column_club_description_fid;
    public TableColumn <Club, LocalDate> column_founding_date_fid;
    public TableColumn <Club,String> column_club_email_fid;
    public TableColumn <Club, String> column_president_name_fid;
    public Button accept_btn_fid;
    public Button refuse_btn_fid;
    public Button refresh_btn_fid;
    ServiceClub serviceClub = new ServiceClub();
    MailSender mailSender = new MailSender();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh_btn_fid.setOnAction(event -> refreshTable());
        accept_btn_fid.setOnAction(event -> this.acceptClubCreationRequest());
        refuse_btn_fid.setOnAction(event -> this.refuseClubCreationRequest());
        initTable();
        refreshTable();
    }



    private void initTable() {
        column_club_name_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getClubName()));
        column_club_description_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getClubDescription()));
        column_club_description_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getClubDescription()));

        column_founding_date_fid.setCellValueFactory(cell -> {
            Date foundingDate = cell.getValue().getFoundingDate();
            LocalDate availableUntilDate = (foundingDate != null) ? foundingDate.toLocalDate() : null;
            return new SimpleObjectProperty<>(availableUntilDate);
        });
        column_club_email_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getClubEmail()));
        column_club_id_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getIdClub()));
        column_president_name_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPresident().getNom()+" "+cell.getValue().getPresident().getPrenom()));
    }
    private void refreshTable() {
        try {
            Club_request_table_view_fid.setItems(FXCollections.observableArrayList(serviceClub.getAllClubCreationRequests()));
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void acceptClubCreationRequest (){
        try {
            Club selectedLocal = Club_request_table_view_fid.getSelectionModel().getSelectedItem();
            if(selectedLocal==null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Selecting item to update state is mandatory ");
                alert.show();
            }else {
                long id = selectedLocal.getIdClub();
                serviceClub.updateClubStateToApproved(id);
                String subject ="Acceptation de votre demande de création de club";
                String body ="Cher"+selectedLocal.getPresident().getNom()+" "+selectedLocal.getPresident().getPrenom()+"\n\n"+
                        "Nous sommes ravis de vous informer que votre demande de création de club au sein \n"+
                        "de l'Université d'Esprit a été acceptée avec enthousiasme. Félicitations !\n\n"+
                        "Nous sommes impatients de voir les activités passionnantes que votre club apportera !\n"+
                        "à notre université. !\n\n"+
                        "Cordialement, !\n";
                mailSender.sendEmail(selectedLocal.getClubEmail(),subject,body);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null); // No header text for simplicity
                successAlert.setContentText("Operation completed successfully!");
                successAlert.showAndWait();
                refreshTable();
            }
        } catch (SQLException e) {
            System.out.println(e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Something went wrong!");
            errorAlert.showAndWait();
        }
        refreshTable();
    }
    private void refuseClubCreationRequest (){
        try {

            Club selectedLocal = Club_request_table_view_fid.getSelectionModel().getSelectedItem();
            if(selectedLocal==null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Selecting item to update state is mandatory ");
                alert.show();
            }else {
            long id = selectedLocal.getIdClub();
            serviceClub.updateClubStateToRejected(id);
                String subject =" Notification de refus de votre demande de création de club";
                String body ="Cher "+selectedLocal.getPresident().getNom()+" "+selectedLocal.getPresident().getPrenom()+"\n\n"+
                        "Nous avons examiné attentivement votre demande de création de club au sein de  \n"+
                        "l'Université d'Esprit. C'est avec regret que nous devons vous informer que votre !\n"+
                        "demande n'a pas été retenue pour le moment.!\n\n"+
                        "Merci de votre compréhension. \n"+
                        "Cordialement, !\n";
                mailSender.sendEmail(selectedLocal.getClubEmail(),subject,body);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null); // No header text for simplicity
                successAlert.setContentText("Operation completed successfully!");
                successAlert.showAndWait();
            refreshTable();}
        } catch (SQLException e) {
            System.out.println(e);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("An error occurred");
            errorAlert.setContentText("Something went wrong!");
            errorAlert.showAndWait();

        }
        refreshTable();
    }



}
