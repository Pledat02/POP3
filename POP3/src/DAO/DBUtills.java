package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtills {
	public static Connection connectDb() {
		String url = "jdbc:ucanaccess://C:\\Users\\MSI DAT\\LTM\\Database31.accdb";
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection connection = DriverManager.getConnection(url);
			
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public static void main(String[] args) {
		System.out.println(connectDb());;
	}

}
