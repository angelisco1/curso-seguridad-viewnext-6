package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

	public static final String URL = "jdbc:h2:~/Pronoide/mis-cursos/curso-seguridad-viewnext-6/detectives-privados/src/main/resources/H2/bbdd_detectives";
	public static final String USER = "sa";
	public static final String PASSWORD = "";
	public static final String DRIVER = "org.h2.Driver";
	
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
