package com.esprit.espritevent.Controllers.Admin;

import com.esprit.espritevent.Models.Event;
import com.esprit.espritevent.Models.EventState;
import com.esprit.espritevent.Services.Event.EventService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageEventsController implements Initializable {
    public TextField event_name_fid;
    public TextField event_description_fid;
    public DatePicker event_start_date_fid;
    public DatePicker event_end_date_fid;
    public TextField event_state_fid;
    public TextField event_club_fid;
    public TextField event_local_fid;
    public TextField event_participants_fid;
    public Button save_btn_fid;
    public TableView<Event> event_table_view_fid;
    public TableColumn<Event, Long> column_event_id_fid;
    public TableColumn<Event, String> column_event_name_fid;
    public TableColumn<Event, String> column_event_description_fid;
    public TableColumn<Event, Date> column_event_start_date_fid;
    public TableColumn<Event, Date> column_event_end_date_fid;
    public TableColumn<Event, Date> column_event_club_fid;
    public TableColumn<Event, Date> column_event_local_fid;
    public TableColumn<Event, Date> column_event_participants_fid;

    public Button delete_btn_fid;
    public Button update_btn_fid;
    EventService eventService = new EventService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        save_btn_fid.setOnAction(event -> addNewEvent());
        update_btn_fid.setOnAction(event -> updateEvent());
        delete_btn_fid.setOnAction(event -> deleteEvent());
        initTable();
        refreshTable();
    }
    private void initTable() {
        column_event_name_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getEventName()));
        column_event_id_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getIdEvent()));
        column_event_description_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getEventDescription()));
        column_event_start_date_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getEventStartDate()));
        column_event_end_date_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getEventEndDate()));
//        column_event_club_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getClub()));
//        column_event_local_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getEventLocal()));
//        column_event_participants_fid.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getParticipants()));
        // Listen for selection changes and update the input fields
        event_table_view_fid.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateInputFields(newSelection);
            }
        });

    }

    private void refreshTable() {
        try {
            event_table_view_fid.setItems(eventService.getAllEvent());
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void addNewEvent() {
        try {
            Event event = new Event();
            event.setEventName(event_name_fid.getText());
            event.setEventDescription(event_description_fid.getText());
//            event.setEventState(EventState.valueOf(event_state_fid.getText()));
//            event.setEventStartDate(event_start_date_fid.getValue());
//            event.setEventEndDate(event_end_date_fid.getValue());
            eventService.addEvent(event);
            event_description_fid.setText("");
            event_name_fid.setText("");
            refreshTable();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    private void updateInputFields(Event selectedEvent) {
        event_name_fid.setText(selectedEvent.getEventName());
        // Uncomment and adjust the following lines based on your date fields
        // local_available_from_fid.setValue(selectedLocal.getLocalAvailableFrom());
        // local_available_until_fid.setValue(selectedLocal.getLocalAvailableUntil());
//        is_booked_fid.setText(String.valueOf(selectedEvent.getIsBooked()));
//        local_capacity_fid.setText(String.valueOf(selectedEvent.getLocalCapacity()));
    }
    private void updateEvent() {
        Event selectedEvent = event_table_view_fid.getSelectionModel().getSelectedItem();

        if (selectedEvent != null) {
            try {
                // Update the selectedLocal object directly
                selectedEvent.setEventName(event_name_fid.getText());
                // Uncomment and adjust the following lines based on your date fields
                // selectedLocal.setLocalAvailableFrom(local_available_from_fid.getValue());
                // selectedLocal.setLocalAvailableUntil(local_available_until_fid.getValue());
                selectedEvent.setEventDescription(event_description_fid.getText());
//                selectedEvent.setEventStartDate(event_start_date_fid);

                eventService.updateEvent(selectedEvent);
                refreshTable();
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please select a Local to update.");
        }
    }
    private void deleteEvent (){
        try {
            Event selectedEvent = event_table_view_fid.getSelectionModel().getSelectedItem();
            long id = selectedEvent.getIdEvent();
            System.out.println(id);
            eventService.deleteEvent(id);
            refreshTable();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
