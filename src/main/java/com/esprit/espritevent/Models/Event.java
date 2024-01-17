package com.esprit.espritevent.Models;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Event {
    private  long idEvent ;
    private String eventName;
    private String eventDescription ;
    private Date eventStartDate;
    private Date eventEndDate;
//    private EventState eventState;
    private Club club;
    private Local eventLocal;
//    private List<User> participants;


    public Event() {

    }

    @Override
    public String toString() {
        return "Event{" +
                "idEvent=" + idEvent +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventStartDate=" + eventStartDate +
                ", eventEndDate=" + eventEndDate +
//                ", eventState=" + eventState +
                ", club=" + club +
                ", eventLocal=" + eventLocal +
//                ", participants=" + participants +
                '}';
    }

    public long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(long idEvent) {
        this.idEvent = idEvent;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public java.sql.Date getEventStartDate() {
        return (java.sql.Date) eventStartDate;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public Event(long idEvent, String eventName, String eventDescription, Date eventStartDate, Date eventEndDate, EventState eventState, Club club, Local eventLocal) {
        this.idEvent = idEvent;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
//        this.eventState = eventState;
        this.club = club;
        this.eventLocal = eventLocal;
//        this.participants = participants;
    }

    public java.sql.Date getEventEndDate() {
        return (java.sql.Date) eventEndDate;
    }

    public void setEventEndDate(Date eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

//    public EventState getEventState() {
//        return eventState;
//    }

//    public void setEventState(EventState eventState) {
//        this.eventState = eventState;
//    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Local getEventLocal() {
        return eventLocal;
    }

    public void setEventLocal(Local eventLocal) {
        this.eventLocal = eventLocal;
    }

//    public List<User> getParticipants() {
//        return participants;
//    }

//    public void setParticipants(List<User> participants) {
//        this.participants = participants;
//    }


}
