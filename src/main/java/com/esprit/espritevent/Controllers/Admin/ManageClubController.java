package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Club;
import com.esprit.espritevent.Models.Local;
import com.esprit.espritevent.Services.Club.ServiceClub;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ManageClubController implements Initializable {
    public TextField club_email_fid;
    public TextField club_name_fid;
    public TextField club_description_fid;
    public DatePicker founding_date_fid;
    public TextField club_state_fid;
    public Button save_btn_fid;
    public Button update_btn_fid;
    public Button delete_btn_fid;
    public TableView<Club> club_table_view_fid;
    public TableColumn<Club,Long> column_club_id_fid;
    public TableColumn <Club,String> column_club_name_fid;
    public TableColumn <Club,String> column_club_description_fid;
    public TableColumn <Club, LocalDate> column_founding_date_fid;
    public TableColumn <Club,String> column_club_email_fid;
    public TableColumn <Club, String> column_president_name_fid;
    public Button refresh_btn_fid;

    ServiceClub serviceClub = new ServiceClub();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh_btn_fid.setOnAction(event -> refreshTable());
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
    }
    private void refreshTable() {
        try {
            club_table_view_fid.setItems(FXCollections.observableArrayList(serviceClub.getAllApprovedClubs()));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
