package org.example.bt_galleryalbum.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    Connection connection;

    public Connection getConnect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gallery_album","root","0601200318");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
