/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.schoolapp.db;

import com.mycompany.schoolapp.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class StudentDB {
    public static Optional<Student> save(Student std) {
        //int id = std.id();
        String fname = std.firstName();
        String lname = std.lastName();
        LocalDate dob = std.dob();
        Connection con = new MySqlDbConnection().getConnection();
        String query = "INSERT INTO student SET first_name=?, last_name=?, dob=? RETURNING *";
        Student st = null;
        try {
            PreparedStatement stmt = con.prepareStatement(query);
            //stmt.setInt(1, id);
            stmt.setString(1, fname);
            stmt.setString(2, lname);
            stmt.setString(3, dob.toString());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                st = new Student(rs.getInt("id"), rs.getString("first_name"), 
                        rs.getString("last_name"), LocalDate.parse(rs.getString("dob")));
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
            return Optional.of(st);
        }
    public static Optional<Student> findById(int id) {
        String query = "SELECT id, first_name, last_name, dob FROM student WHERE id=?";
        Student st = null;
       
        try {
            Connection con = new MySqlDbConnection().getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                st = new Student(rs.getInt("id"), rs.getString("first_name"), 
                        rs.getString("last_name"), LocalDate.parse(rs.getString("dob")));
                System.out.println("Student is " + rs.getString(2));
            }
            con.close();
            return Optional.of(st);
        } catch (SQLException ex) {
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
       return Optional.of(st); 
    }
    
    public static ArrayList<Integer> loadStudentId() {
        String query = "SELECT id FROM student";
        var result = new ArrayList<Integer>();
        
        try {
            Connection con = new MySqlDbConnection().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                result.add(rs.getInt("id"));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return result;   
    }
    
    public static ArrayList<Student> getAll() {
        String query = "SELECT * FROM student";
        ArrayList<Student> result = new ArrayList<>();
        Student st = null;
        try{
            Connection con = new MySqlDbConnection().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                st = new Student(rs.getInt("id"), rs.getString("first_name"), 
                        rs.getString("last_name"), LocalDate.parse(rs.getString("dob")));
                        result.add(st);
            }
            stmt.close();
            con.close();
            return result;
        }catch(SQLException ex){
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return result;
    }
}
