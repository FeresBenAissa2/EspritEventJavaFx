package com.esprit.espritevent.Services.Event;

import com.esprit.espritevent.Utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceEvent implements IServiceEvent{

    Connection conn = DataSource.getInstance().getConn();
    PreparedStatement preparedStatement = null;
    @Override
    public long countEvent() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) AS event_count FROM Event")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("event_count");
            } else {
                throw new SQLException("Failed to retrieve club count");
            }
        }
    }
}
