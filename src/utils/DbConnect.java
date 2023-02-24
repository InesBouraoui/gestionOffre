/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BOURAOUI
 */
public class DbConnect {    
    private static String HOST = "127.0.0.1";
    private final String URL = "jdbc:mysql://localhost:3306/gestionoffrev15";
    private static int PORT = 3306;
    private static String DB_NAME = "gestionoffrev15";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection;
    
    

    
       
    public static Connection getConnect (){
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return  connection;
        }
     
        
 
    private static DbConnect instance;
    private DbConnect() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connecting !!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static DbConnect getInstance() {
        if(instance == null) {
            instance = new DbConnect();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
 
}