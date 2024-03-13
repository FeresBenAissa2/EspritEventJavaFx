package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Local;
import com.esprit.espritevent.Services.Local.ServiceLocal;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class ManageLocalsController implements Initializable {
    public TextField local_name_fid;
    public TextField local_capacity_fid;
    public DatePicker local_available_from_fid;
    public DatePicker local_available_until_fid;
    public ComboBox<String> is_booked_fid;
    public Button save_btn_fid;
    public TableView<Local> local_table_view_fid;
    public TableColumn<Local, Long> column_local_id_fid;
    public TableColumn<Local, String> column_local_name_fid;
    public TableColumn<Local, Long> column_local_capacity_fid;
    public TableColumn<Local, LocalDate> column_local_available_from_fid;
    public TableColumn<Local, LocalDate> column_local_available_until_fid;

    public TableColumn<Local, Boolean> column_is_booked_fid;
    public Button delete_btn_fid;
    public Button update_btn_fid;
    @FXML
    public Text errorTextName;
    @FXML
    public Text errorTextCapacity;
    @FXML
    public Text errorTextAvailableFrom;
    @FXML
    public Text errorTextAvailableUntil;
    @FXML
    public Text errorTextIsBooked;
    ServiceLocal serviceLocal = new ServiceLocal();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        save_btn_fid.setOnAction(event -> addNewLocal());
        update_btn_fid.setOnAction(event -> updateLocal());
        delete_btn_fid.setOnAction(event -> deleteLocal());
        initTable();
        refreshTable();
    }

    private void initTable() {
        column_local_name_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getLocalName()));
        column_local_available_from_fid.setCellValueFactory(cell -> {
            Date availableFrom = cell.getValue().getLocalAvailableFrom();
            LocalDate availableFromDate = (availableFrom != null) ? availableFrom.toLocalDate() : null;
            return new SimpleObjectProperty<>(availableFromDate);
        });

        column_local_available_until_fid.setCellValueFactory(cell -> {
            Date availableUntil = cell.getValue().getLocalAvailableUntil();
            LocalDate availableUntilDate = (availableUntil != null) ? availableUntil.toLocalDate() : null;
            return new SimpleObjectProperty<>(availableUntilDate);
        });
        column_local_id_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getIdLocal()));
        column_is_booked_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getIsBooked()));
        column_local_capacity_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getLocalCapacity()));
        // Listen for selection changes and update the input fields
        local_table_view_fid.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateInputFields(newSelection);
            }
        });
        clearErrorMessages();

    }

    private void refreshTable() {
        try {
            local_table_view_fid.setItems(serviceLocal.getAllLocals());
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void addNewLocal() {
        clearErrorMessages(); // Clear previous error messages
        try {
            if (validateInput()) {
                Local local = new Local();
                local.setLocalName(local_name_fid.getText());
                local.setLocalAvailableUntil(Date.valueOf(local_available_until_fid.getValue()));
                local.setLocalAvailableFrom(Date.valueOf(local_available_from_fid.getValue()));
                local.setIsBooked(Boolean.parseBoolean(is_booked_fid.getValue()));
                local.setLocalCapacity(Long.parseLong(local_capacity_fid.getText()));
                serviceLocal.addLocal(local);
                is_booked_fid.setValue(null);
                local_capacity_fid.setText("");
                local_name_fid.setText("");
                local_available_until_fid.setValue(null);
                local_available_from_fid.setValue(null);
                refreshTable();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void updateInputFields(Local selectedLocal) {
        local_name_fid.setText(selectedLocal.getLocalName());
        // Uncomment and adjust the following lines based on your date fields
        if (selectedLocal.getLocalAvailableFrom() != null) {
            local_available_from_fid.setValue(selectedLocal.getLocalAvailableFrom().toLocalDate());
        }
        if (selectedLocal.getLocalAvailableUntil() != null) {
            local_available_until_fid.setValue(selectedLocal.getLocalAvailableUntil().toLocalDate());
        }
        is_booked_fid.setValue(String.valueOf(selectedLocal.getIsBooked()));
        local_capacity_fid.setText(String.valueOf(selectedLocal.getLocalCapacity()));

    }

    private void updateLocal() {
        Local selectedLocal = local_table_view_fid.getSelectionModel().getSelectedItem();
        clearErrorMessages(); // Clear previous error messages
        if (selectedLocal != null) {
            try {
                if (validateInput()) {
                    // Update the selectedLocal object directly
                    selectedLocal.setLocalName(local_name_fid.getText());
                    // Uncomment and adjust the following lines based on your date fields
                    selectedLocal.setLocalAvailableFrom(Date.valueOf(local_available_from_fid.getValue()));
                    selectedLocal.setLocalAvailableUntil(Date.valueOf(local_available_until_fid.getValue()));
                    selectedLocal.setIsBooked(Boolean.parseBoolean(is_booked_fid.getValue()));
                    selectedLocal.setLocalCapacity(Long.parseLong(local_capacity_fid.getText()));

                    serviceLocal.updateLocal(selectedLocal);
                    refreshTable();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please select a Local to update.");
        }
    }

    private void deleteLocal() {
        try {
            Local selectedLocal = local_table_view_fid.getSelectionModel().getSelectedItem();
            long id = selectedLocal.getIdLocal();
            System.out.println(id);
            serviceLocal.deleteLocal(id);
            refreshTable();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (local_name_fid.getText().isEmpty()) {
            errorTextName.setText("Name cannot be empty.");
            isValid = false;
        }

        if (local_capacity_fid.getText().isEmpty() || !local_capacity_fid.getText().matches("\\d+")) {
            errorTextCapacity.setText("Invalid (Number) or empty capacity.");
            isValid = false;
        }

        if (local_available_from_fid.getValue() == null) {
            errorTextAvailableFrom.setText("Please select available from date.");
            isValid = false;
        }

        if (local_available_until_fid.getValue() == null) {
            errorTextAvailableUntil.setText("Please select available until date.");
            isValid = false;
        }

        if (is_booked_fid.getValue().isEmpty()) {
            errorTextIsBooked.setText("Is booked cannot be empty.");
            isValid = false;
        } else {
            try {
                Boolean.parseBoolean(is_booked_fid.getValue());
            } catch (NumberFormatException e) {
                errorTextIsBooked.setText("Invalid value for Is booked.");
                isValid = false;
            }
        }

        return isValid;
    }

    private void clearErrorMessages() {
        errorTextName.setText("");
        errorTextCapacity.setText("");
        errorTextAvailableFrom.setText("");
        errorTextAvailableUntil.setText("");
        errorTextIsBooked.setText("");
    }
}
