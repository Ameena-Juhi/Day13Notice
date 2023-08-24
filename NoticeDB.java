package com.learning.hello.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

public class NoticeDB {
	public static void main(String[] args) {
	PreparedStatement insert = null;
	PreparedStatement update = null;
	Statement stmt = null;
    Connection cnx = null;
    ResultSet rs = null;
    
    String insertNotice = "INSERT INTO Notice(id,name,contact) VALUES (?,?,?)";
    String updateName = "UPDATE Notice SET name=?, contact=?;";
    
    try {
		cnx =  DriverManager.getConnection("jdbc:mysql://localhost:3306/persistence","Ameena","Ameena");
		insert = cnx.prepareStatement(insertNotice);
		update = cnx.prepareStatement(updateName);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter notice subject in name: ");	
		String name = sc.nextLine();
		System.out.println("Enter contact number");
		String contact = sc.nextLine();
		
		insert.setString(2, name);
		insert.setString(3, contact);
		insert.executeUpdate();
	}
	
	 catch(SQLException e) {
		
		e.printStackTrace();
	}
	
}
}
