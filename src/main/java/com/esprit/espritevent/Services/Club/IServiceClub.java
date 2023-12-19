package com.esprit.espritevent.Services.Club;

import com.esprit.espritevent.Models.Club;
import com.esprit.espritevent.Models.Local;

import java.sql.SQLException;
import java.util.List;

public interface IServiceClub {
    void addClub(Club club)throws SQLException;
    List<Club> getAllClubs() throws SQLException;
    List<Club> getAllApprovedClubs() throws SQLException;
    List<Club> getAllClubCreationRequests() throws SQLException;
    void updateClubStateToApproved(long id)throws SQLException;
    void updateClubStateToRejected(long id)throws SQLException;
    long countClubs()throws SQLException;
    void updateClub(Club club) throws  SQLException;
    void deleteClub(long id )throws  SQLException;
}
