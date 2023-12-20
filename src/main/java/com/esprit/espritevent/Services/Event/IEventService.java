package com.esprit.espritevent.Services.Event;

import com.esprit.espritevent.Models.Event;

import java.sql.SQLException;
import java.util.List;

public interface IEventService {
    void addEvent(Event event)throws SQLException;
    List<Event> getAllEvent() throws SQLException;
    void updateEvent(Event event) throws  SQLException;
    void deleteEvent(long idEvent)throws  SQLException;
}
