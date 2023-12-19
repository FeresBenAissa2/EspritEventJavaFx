package com.esprit.espritevent.Services.Club;

import com.esprit.espritevent.Models.Club;
import com.esprit.espritevent.Models.ClubState;
import com.esprit.espritevent.Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

public class ServiceClub implements IServiceClub{
    Connection conn = DataSource.getInstance().getConn();
    PreparedStatement preparedStatement = null;
    @Override
    public void addClub(Club club) throws SQLException {
        try {
            preparedStatement = conn.prepareStatement("INSERT INTO `club` (`club_description`,`club_email`,`club_name`,`club_state`,`founding_date`,`president_id_user`) VALUES (?,?,?,?,?,?);");
            preparedStatement.setString(1, club.getClubDescription());
            preparedStatement.setString(2, club.getClubEmail());
            preparedStatement.setString(3, club.getClubName());
            preparedStatement.setString(4,club.getClubState().toString());
            preparedStatement.setDate(5,club.getFoundingDate());
            preparedStatement.setLong(6,club.getPresident().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Club> getAllClubs() throws SQLException {
        ObservableList<Club> clubs = FXCollections.observableArrayList();

        try {
            Statement ste = conn.createStatement();
            String req = "select * from Club";
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {

                Club club = new Club(
                        res.getLong("id_club"),
                        res.getString("club_name"),
                        res.getString("club_description"),
                        res.getDate("founding_date"),
                        res.getString("club_email"),
                        res.getString("club_state").equals(ClubState.ACTIVE.toString()) ?ClubState.ACTIVE:ClubState.INACTIVE
                );
                clubs.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    @Override

    public List<Club> getAllClubCreationRequests() throws SQLException {
        ObservableList<Club> clubs = FXCollections.observableArrayList();

        try  {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Club WHERE club_state = ?");
            ps.setString(1, ClubState.IN_PROGRESS.toString()); // Set the parameter for the WHERE clause
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Club club = new Club(
                        res.getLong("id_club"),
                        res.getString("club_name"),
                        res.getString("club_description"),
                        res.getDate("founding_date"),
                        res.getString("club_email"),
                        ClubState.valueOf(res.getString("club_state")) // Use valueOf for ClubState
                );
                clubs.add(club);
                System.out.println(club);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return clubs;
    }

    @Override
    public void updateClubStateToApproved(long id) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Club SET club_state = ? WHERE id_club = ?");
            ps.setString(1, ClubState.APPROVED.toString());
            ps.setLong(2, id);
            ps.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClubStateToRejected(long id) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Club SET club_state = ? WHERE id_club = ?");
            ps.setString(1, ClubState.REFUSED.toString());
            ps.setLong(2, id);
            ps.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateClub(Club club) throws SQLException {

    }

    @Override
    public void deleteClub(long id) throws SQLException {

    }
}
