package com.example.myproject.db;

import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Testmethoden für MySQL.
 * 
 * @author schmidt
 *
 */
public final class TestMySql {
	
	private TestMySql() { }
    
    public static void main(String[] args) {
        connect();
    }
    
    public static void connect() {
        DriverManager.setLogWriter(new PrintWriter(System.out));
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
             DriverManager.getConnection("jdbc:mysql://172.16.8.132:3306/master?profileSQL=true",
                    "schmidt",
                    "schmidt");
//            Connection con = DriverManager.getConnection("jdbc:mysql://<host>:3306/<database>?profileSQL=true",
//                                              "user",
//                                              "password");
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            e.printStackTrace();            e.printStackTrace();
        }
    }
}
