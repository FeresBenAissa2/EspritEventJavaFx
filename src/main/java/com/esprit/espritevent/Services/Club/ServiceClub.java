package com.esprit.espritevent.Services.Club;

import com.esprit.espritevent.Models.Club;
import com.esprit.espritevent.Models.ClubState;
import com.esprit.espritevent.Models.ClubStatus;
import com.esprit.espritevent.Models.User;
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
                        res.getString("club_state").equals(ClubState.APPROVED.toString()) ?ClubState.APPROVED:ClubState.REFUSED
                );
                clubs.add(club);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clubs;
    }

    @Override
    public List<Club> getAllApprovedClubs() throws SQLException {
        ObservableList<Club> clubs = FXCollections.observableArrayList();

        try  {
            PreparedStatement ps = conn.prepareStatement("SELECT Club.*, User.* FROM Club INNER JOIN User ON Club.president_id_user = User.id_user WHERE club_state = ?");
            ps.setString(1, ClubState.APPROVED.toString()); // Set the parameter for the WHERE clause
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                User president = new User(
                        res.getInt("id_user"),
                        res.getString("username"), // Replace with the actual column name
                        res.getString("password"), // Replace with the actual column name
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("email"),
                        res.getLong("phone"),
                        res.getInt("age"),
                        res.getString("role") // Replace with the actual column name
                        // Add other attributes as needed
                );
                Club club = new Club(
                        res.getLong("id_club"),
                        res.getString("club_name"),
                        res.getString("club_description"),
                        res.getDate("founding_date"),
                        res.getString("club_email"),
                        ClubState.valueOf(res.getString("club_state")),
                        ClubStatus.valueOf(res.getString("club_status")),// Use valueOf for ClubState
                        president// Use valueOf for ClubState
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

    public List<Club> getAllClubCreationRequests() throws SQLException {
        ObservableList<Club> clubs = FXCollections.observableArrayList();

        try  {
            PreparedStatement ps = conn.prepareStatement("SELECT Club.*, User.* FROM Club INNER JOIN User ON Club.president_id_user = User.id_user WHERE club_state = ?");
            ps.setString(1, ClubState.IN_PROGRESS.toString()); // Set the parameter for the WHERE clause
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                User president = new User(
                        res.getInt("id_user"),
                        res.getString("username"), // Replace with the actual column name
                        res.getString("password"), // Replace with the actual column name
                        res.getString("nom"),
                        res.getString("prenom"),
                        res.getString("email"),
                        res.getLong("phone"),
                        res.getInt("age"),
                        res.getString("role")  // Replace with the actual column name
                        // Add other attributes as needed
                );
                Club club = new Club(
                        res.getLong("id_club"),
                        res.getString("club_name"),
                        res.getString("club_description"),
                        res.getDate("founding_date"),
                        res.getString("club_email"),
                        ClubState.valueOf(res.getString("club_state")),
                        president
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
    public long countClubs() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS club_count FROM Club")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("club_count");
            } else {
                throw new SQLException("Failed to retrieve club count");
            }
        }
    }


    @Override
    public void updateClub(Club club) throws SQLException {
        try {
            System.out.println("service update club");
            System.out.println(club);
            preparedStatement = conn.prepareStatement("UPDATE `club` SET `club_description`= ? ,`club_email`=? ,`club_name`=?,`club_status`=?,`club_state`=?,`founding_date`=?,`president_id_user`=? WHERE `id_club` = ?;");
            preparedStatement.setString(1, club.getClubDescription());
            preparedStatement.setString(2, club.getClubEmail());
            preparedStatement.setString(3, club.getClubName());
            preparedStatement.setString(4, club.getClubStatus().toString());
            preparedStatement.setString(5, club.getClubState().toString());
            preparedStatement.setDate(6,club.getFoundingDate());
            preparedStatement.setLong(7,club.getPresident().getId());
            preparedStatement.setLong(8,club.getIdClub());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClub(long id) throws SQLException {

    }

    @Override
    public int getAcceptedClubsCount() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS club_count FROM Club WHERE club_state = ?")) {
            ps.setString(1, ClubState.APPROVED.toString()); // Assuming ClubState is an enum
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("club_count");
            } else {
                throw new SQLException("Failed to retrieve club count");
            }
        }
    }

    @Override
    public int getRefusedClubsCount() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS club_count FROM Club WHERE club_state = ?")) {
            ps.setString(1, ClubState.REFUSED.toString()); // Assuming ClubState is an enum
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("club_count");
            } else {
                throw new SQLException("Failed to retrieve club count");
            }
        }
    }

    @Override
    public int getInProgressClubsCount() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS club_count FROM Club WHERE club_state = ?")) {
            ps.setString(1, ClubState.IN_PROGRESS.toString()); // Assuming ClubState is an enum
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("club_count");
            } else {
                throw new SQLException("Failed to retrieve club count");
            }
        }
    }
}
