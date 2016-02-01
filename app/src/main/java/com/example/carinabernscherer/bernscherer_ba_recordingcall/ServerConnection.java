package com.example.carinabernscherer.bernscherer_ba_recordingcall;

import android.os.StrictMode;
import android.util.Log;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by carinabernscherer on 29.01.16.
 *
 * Connection to the MySQL Uni Wien Server
 */
public class ServerConnection {

    /**
     * establish connection
     * creates table on the SQLServer
     * insert into the table on the SQLServer
     * @param file
     * @param username
     * @param password
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    public void connectToDatabase(File file, String username, String password) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        String path = "sftp://almighty.cs.univie.ac.at/home/" + username;
        path += file.getName();
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://mysql5.univie.ac.at:3306/" + username;

        System.setProperty(driver, "");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection con = DriverManager.getConnection(url, username, password);

        String a = "Connection zu MySQLServer";
        Log.i(a, "open");



        String q = "CREATE TABLE IF NOT EXISTS `FILES`( " +
                "NAME VARCHAR(100) UNIQUE, " +
                "TYPE TEXT, " +
                "SIZE TEXT, " +
                "PATH TEXT )";

        PreparedStatement preparedStatement1 = con.prepareStatement(q);
        preparedStatement1.executeUpdate();
        String query = "insert into FILES (Name,Type,Size, Path) values ('" + file.getName() + "','" + ".amr" + "','" + file.length() + "','" + path + "')";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.executeUpdate();


        con.close();


    }
}
