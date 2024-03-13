package com.esprit.espritevent.Services.User;

import com.esprit.espritevent.Models.User;
import com.esprit.espritevent.Utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceUser implements IServiceUser {
    Connection conn = DataSource.getInstance().getConn();
    PreparedStatement preparedStatement = null;
    @Override
    public void updateUser(User user) throws SQLException {
        System.out.println(user.getEmail());
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE `user` SET email = ?,nom =?,password = ?,phone =?,prenom = ?,username = ? WHERE `id_user` = ?;\n");
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getNom());
            ps.setString(3, user.getPassword());
            ps.setLong(4, user.getPhone());
            ps.setString(5, user.getPrenom());
            ps.setString(6, user.getUsername());
            ps.setLong(7, user.getId());
            ps.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUserById(User user) throws SQLException {


    }
}
