package poise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This code connects the program to MySQL servers
 * <p>
 * 
 * @author Samuel Wendi Kariuki
 * @version 3.1 11 June 2022
 */

public class Jdbc {

	static Connection connection;
	static Statement statement;
	static ResultSet results;

	/**
	 *
	 * A Method to create a JDBC connection <br>
	 *
	 */
	public void createConnection() {
		try {
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisepms?useSSL=false", "otheruser",
					"urmomgayyy");
			statement = connection.createStatement();

			results = statement.executeQuery("select * from projects");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
