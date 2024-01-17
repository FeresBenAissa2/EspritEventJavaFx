package com.esprit.espritevent.Services.Event;


import com.esprit.espritevent.Models.*;
import com.esprit.espritevent.Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class EventService implements IEventService{
    Connection conn = DataSource.getInstance().getConn();
    PreparedStatement preparedStatement = null;

    @Override
    public void addEvent(Event event) throws SQLException {
        try{
            preparedStatement = conn.prepareStatement("INSERT INTO `event`(`club_id_club`, `event_local_id_local`, `event_name`, `event_description`, `event_end_date`, `event_start_date`)VALUES (?,?,?,?,?,?);");
//            preparedStatement.setObject(1, event.getEventState());
            preparedStatement.setObject(1, event.getClub());
            preparedStatement.setObject(2, event.getEventLocal());
            preparedStatement.setString(3,event.getEventName());
            preparedStatement.setString(4,event.getEventDescription());
            preparedStatement.setDate(5,event.getEventStartDate());
            preparedStatement.setDate(6,event.getEventEndDate());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Event> getAllEvent() throws SQLException {
        ObservableList<Event> events = FXCollections.observableArrayList();
        try {
            Statement ste = conn.createStatement();
            String req = "select * from event";
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {

                Event event = new Event(
                        res.getLong("id_event"),
                        res.getString("event_name"),
                        res.getString("event_description"),
                        (Date) res.getDate("event_start_date"),
                        (Date) res.getDate("event_end_date"),
                        (EventState) res.getObject("event_state"),
                        (Club) res.getObject("club_id_club"),
                        (Local) res.getObject("event_local_id_local")
//                        (List<User>) res.getObject("participants")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return events;
    }

    @Override
    public void updateEvent(Event event) throws SQLException {
        try {

            preparedStatement = conn.prepareStatement("UPDATE `event` SET `club_id_club`=?,`event_local_id_local`=?,`id_event`=?,`event_name`=?,`event_description`=?,`event_end_date`=?,`event_start_date`=? WHERE `id_event` = ?;");
//            preparedStatement.setObject(1, event.getEventState());
            preparedStatement.setObject(1, event.getClub());
            preparedStatement.setObject(2, event.getEventLocal());
            preparedStatement.setLong(3,event.getIdEvent());
            preparedStatement.setString(4,event.getEventName());
            preparedStatement.setString(5,event.getEventDescription());
            preparedStatement.setDate(6,event.getEventEndDate());
            preparedStatement.setDate(7,event.getEventStartDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEvent(long idEvent) throws SQLException {
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM `event` WHERE id_event = ?;");
            preparedStatement.setLong(1, idEvent);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
