package com.esprit.espritevent.Services.Local;

import com.esprit.espritevent.Models.Local;
import com.esprit.espritevent.Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceLocal implements IServiceLocal{
    Connection conn = DataSource.getInstance().getConn();
    PreparedStatement preparedStatement = null;

    @Override
    public void addLocal(Local local) throws SQLException {
        System.out.println("add Local service");
        System.out.println(local.getLocalAvailableFrom());
        try {

            preparedStatement = conn.prepareStatement("INSERT INTO `local` (`is_booked`,`local_available_from`,`local_available_until`,`local_capacity`,`local_name`) VALUES (?,?,?,?,?);");
            preparedStatement.setBoolean(1, local.getIsBooked());
            preparedStatement.setDate(2, local.getLocalAvailableFrom());
            preparedStatement.setDate(3, local.getLocalAvailableUntil());
            preparedStatement.setLong(4,local.getLocalCapacity());
            preparedStatement.setString(5,local.getLocalName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Local> getAllLocals() throws SQLException {
        ObservableList<Local> locals = FXCollections.observableArrayList();

        try {
            Statement ste = conn.createStatement();
            String req = "select * from Local";
            ResultSet res = ste.executeQuery(req);

            while (res.next()) {

                Local local = new Local(
                        res.getLong("id_local"),
                        res.getString("local_name"),
                        res.getLong("local_capacity"),
                        res.getDate("local_available_from"),
                        res.getDate("local_available_until"),
                        res.getBoolean("is_booked")
                        );
                locals.add(local);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locals;
    }

    @Override
    public void updateLocal(Local local) throws SQLException {
        try {

            preparedStatement = conn.prepareStatement("UPDATE `local` SET `is_booked`= ? ,`local_available_from`=? ,`local_available_until`=?,`local_capacity`=?,`local_name`=? WHERE `id_local` = ?;");
            preparedStatement.setBoolean(1, local.getIsBooked());
            preparedStatement.setDate(2, local.getLocalAvailableFrom());
            preparedStatement.setDate(3, local.getLocalAvailableUntil());
            preparedStatement.setLong(4,local.getLocalCapacity());
            preparedStatement.setString(5,local.getLocalName());
            preparedStatement.setLong(6,local.getIdLocal());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLocal(long id) throws SQLException {
        try {
            preparedStatement = conn.prepareStatement("DELETE FROM Local WHERE id_local = ?;\n");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
