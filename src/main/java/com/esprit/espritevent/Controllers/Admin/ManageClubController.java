package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Club;
import com.esprit.espritevent.Models.ClubStatus;
import com.esprit.espritevent.Models.MailSender;
import com.esprit.espritevent.Services.Club.ServiceClub;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.FileInputStream;
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
    public ComboBox<String> club_state_fid;
    public ComboBox<String> club_status_fid;

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
    public TableColumn <Club, String> column_club_status_fid;

    public Button refresh_btn_fid;
    public Button show_club_events_btn_fid;
    @FXML
    public Text errorClubName;
    @FXML
    public Text errorClubDescription;
    @FXML
    public Text errorFoundingDate;
    @FXML
    public Text errorClubEmail;
    @FXML
    public Text errorClubState;
    public Button export_excel_fid;

    public FileInputStream fileInputStream;

    ServiceClub serviceClub = new ServiceClub();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refresh_btn_fid.setOnAction(event -> refreshTable());
        update_btn_fid.setOnAction(event -> updateClub());
        export_excel_fid.setOnAction(event ->serviceClub.exportTableToExcel());
        show_club_events_btn_fid.setOnAction(event -> {
            MailSender mailSender = new MailSender();
            mailSender.sendEmail("mezenblr@gmail.com","test","test");
        });
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
        column_club_status_fid.setCellValueFactory(cell -> {
            ClubStatus clubStatus = cell.getValue().getClubStatus();
            String statusString = (clubStatus != null) ? clubStatus.toString() : ""; // Provide a default value
            return new SimpleObjectProperty<>(statusString);
        });
        column_president_name_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getPresident().getPrenom()+""+cell.getValue().getPresident().getPrenom()));
        club_table_view_fid.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateInputFields(newSelection);
            }
        });
        clearErrorMessages();
    }
    private void refreshTable() {
        try {
            club_table_view_fid.setItems(FXCollections.observableArrayList(serviceClub.getAllApprovedClubs()));
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    private void updateInputFields(Club selectedLocal) {
        System.out.println(selectedLocal);
        // Uncomment and adjust the following lines based on your date fields
        club_name_fid.setText(String.valueOf(selectedLocal.getClubName()));
        club_description_fid.setText(String.valueOf(selectedLocal.getClubDescription()));
        if (selectedLocal.getFoundingDate() != null) {
            founding_date_fid.setValue(selectedLocal.getFoundingDate().toLocalDate());
        }
        club_email_fid.setText(selectedLocal.getClubEmail());
        club_status_fid.setValue(String.valueOf(selectedLocal.getClubStatus()));


    }

    private void updateClub() {
        Club selectedClub = club_table_view_fid.getSelectionModel().getSelectedItem();
        clearErrorMessages(); // Clear previous error messages
        if (selectedClub != null) {
            try {
                if (validateInput()) {
                    // Update the selectedLocal object directly
                    selectedClub.setClubName(club_name_fid.getText());
                    // Uncomment and adjust the following lines based on your date fields
                    selectedClub.setClubDescription(club_description_fid.getText());
                    selectedClub.setFoundingDate(Date.valueOf(founding_date_fid.getValue()));
                    selectedClub.setClubEmail(club_email_fid.getText());
                    selectedClub.setClubStatus(ClubStatus.valueOf(club_status_fid.getValue()));
                    System.out.println("before sending to service");
                    System.out.println(selectedClub);

                    serviceClub.updateClub(selectedClub);
                    refreshTable();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please select a Club to update.");
        }
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (club_name_fid.getText().isEmpty()) {
            errorClubName.setText("Name cannot be empty.");
            isValid = false;
        }

        if (club_description_fid.getText().isEmpty() ) {
            errorClubDescription.setText("Description cannot be empty.");
            isValid = false;
        }

        if (founding_date_fid.getValue() == null) {
            errorFoundingDate.setText("Please select available from date.");
            isValid = false;
        }
        if (club_status_fid.getValue().isEmpty()) {
            errorClubState.setText(" Status cannot be empty.");
            isValid = false;
        }
        return isValid;
    }
//    public void exportTableToExcel (){
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sheet= wb.createSheet("Club Details ");
//        HSSFRow header = sheet.createRow(0);
//        header.createCell(0).setCellValue("ID");
//        header.createCell(1).setCellValue("NAME");
//        header.createCell(2).setCellValue("DESCRIPTION");
//        header.createCell(3).setCellValue("FOUNDING DATE");
//        header.createCell(4).setCellValue("CLUB EMAIl");
//        header.createCell(5).setCellValue("PRESIDENT NAME");
//        header.createCell(6).setCellValue("STATUS");
//
//        Connection conn = DataSource.getInstance().getConn();
//        try  {
//            PreparedStatement ps = conn.prepareStatement("SELECT Club.*, User.* FROM Club INNER JOIN User ON Club.president_id_user = User.id_user WHERE club_state = ?");
//            ps.setString(1, ClubState.APPROVED.toString()); // Set the parameter for the WHERE clause
//            ResultSet res = ps.executeQuery();
//            int index=1;
//            while (res.next()) {
//                HSSFRow row = sheet.createRow(index);
//                row.createCell(0).setCellValue(res.getLong("id_club"));
//                row.createCell(1).setCellValue(res.getString("club_name"));
//                row.createCell(2).setCellValue( res.getString("club_description"));
//                row.createCell(3).setCellValue(res.getDate("founding_date"));
//                row.createCell(4).setCellValue(res.getString("club_email"));
//                row.createCell(5).setCellValue(res.getString("nom")+res.getString("prenom"));
//                row.createCell(6).setCellValue(res.getString("club_status"));
//                index++;
//            }
//
//            FileOutputStream    fileOutputStream = new FileOutputStream("ClubDetails.xls");
//            wb.write(fileOutputStream);
//            fileOutputStream.close();
//        }catch (SQLException e) {
//
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
    private void clearErrorMessages() {
        errorClubName.setText("");
        errorClubDescription.setText("");
        errorFoundingDate.setText("");
        errorClubEmail.setText("");
        errorClubState.setText("");
    }

}
