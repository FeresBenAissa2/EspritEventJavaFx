package com.esprit.espritevent.Services.User;

import com.esprit.espritevent.Models.User;

import java.sql.SQLException;

public interface IServiceUser {
    void updateUser(User user)throws SQLException;
    void getUserById(User user)throws SQLException;
}
