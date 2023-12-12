package com.esprit.espritevent.Models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class Club {
    private long idClub;
    private String clubName;
    private String clubDescription;
    private Date foundingDate;
    private String clubEmail;
    private ClubState clubState;
    private User president;
    private List<Event> events;

    public Club(long idClub, String clubName, String clubDescription, Date foundingDate, String clubEmail, ClubState clubState, User president, List<Event> events) {
        this.idClub = idClub;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.foundingDate = foundingDate;
        this.clubEmail = clubEmail;
        this.clubState = clubState;
        this.president = president;
        this.events = events;
    }

    public Club(long idClub, String clubName, String clubDescription, Date foundingDate, String clubEmail, ClubState clubState, User president) {
        this.idClub = idClub;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.foundingDate = foundingDate;
        this.clubEmail = clubEmail;
        this.clubState = clubState;
        this.president = president;
    }
    public Club(long idClub, String clubName, String clubDescription, Date foundingDate, String clubEmail, ClubState clubState) {
        this.idClub = idClub;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
        this.foundingDate = foundingDate;
        this.clubEmail = clubEmail;
        this.clubState = clubState;
    }
    public long getIdClub() {
        return idClub;
    }

    public void setIdClub(long idClub) {
        this.idClub = idClub;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubDescription() {
        return clubDescription;
    }

    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
    }

    public Date getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(Date foundingDate) {
        this.foundingDate = foundingDate;
    }

    public String getClubEmail() {
        return clubEmail;
    }

    public void setClubEmail(String clubEmail) {
        this.clubEmail = clubEmail;
    }

    public ClubState getClubState() {
        return clubState;
    }

    public void setClubState(ClubState clubState) {
        this.clubState = clubState;
    }

    public User getPresident() {
        return president;
    }

    public void setPresident(User president) {
        this.president = president;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

}
