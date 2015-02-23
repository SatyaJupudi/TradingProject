package com.trader.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OpenConnection {
	private String jdbcDriver ;
	private String databaseUrl;
	private Connection cn;
	private boolean isConnected;
	
	public OpenConnection(String database) {
		jdbcDriver = "com.mysql.jdbc.Driver";
		databaseUrl = "jdbc:mysql://localhost/" + database + "?user=root&password=password";
		isConnected = false;
	}
	
	public void start() {
		loadClass();
		loadDatabase();
		isConnected = true;
	}
	
	private void loadClass() {
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading JDBC driver: " + e);
		}
	}
	
	private void loadDatabase() {
		try {
			cn = DriverManager.getConnection(databaseUrl);
		} catch (SQLException e) {
			System.out.println("Error connection to a database: " + e);
		}
	}
	
	public Connection getConnection() {
		if (isConnected) {
			return cn;
		} else {
			System.out.println("Haven't started connection");
			return null;
		}
	}
	
	public void close() throws SQLException {
		cn.close();
	}
}
