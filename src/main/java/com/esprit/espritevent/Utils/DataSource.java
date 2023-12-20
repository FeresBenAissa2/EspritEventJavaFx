package com.esprit.espritevent.Utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {
    private static DataSource dataSource;
    private Connection conn ;
    private String url ="jdbc:mysql://localhost:3306/espriteventprojetjavafx";
    private String user ="root";
    private String pwd ="";


    // ne pas instancier la datasource
    private DataSource()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  Connection getConn() {
            return conn;
    }
    public static DataSource getInstance(){
        if(dataSource== null) {
            dataSource=new DataSource();
        }
        return dataSource;
    }
}
