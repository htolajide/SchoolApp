/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.schoolapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class MySqlDbConnection implements DbConnection  {
    private final String DB_URL = "jdbc:mysql://localhost:3306/school_data?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "olajide4me";
    @Override
    public Connection getConnection() {
        Connection con = null;
        
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException ex ) {
            Logger.getLogger(MySqlDbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassNotFoundException ex) {
                Logger.getLogger(MySqlDbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex){
            System.out.println("An Excedption occur" + ex.getMessage());
        }
        
        return con;
    }
}
