package com.esprit.espritevent.Services.Local;

import com.esprit.espritevent.Models.Local;

import java.sql.SQLException;
import java.util.List;

public interface IServiceLocal {

    void addLocal(Local local)throws SQLException;
    List<Local> getAllLocals() throws SQLException;
    long countLocals()throws SQLException;
    void updateLocal(Local local) throws  SQLException;
    void deleteLocal(long id )throws  SQLException;
    int getBookedLocals()throws  SQLException;
    int getUnbookedLocals()throws  SQLException;
}
