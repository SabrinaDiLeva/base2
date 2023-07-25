package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
	private static ConexionMySQL instancia;
	private Connection connect;

	private ConexionMySQL() {
		try {
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdMySQL", "root", "admin123");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static ConexionMySQL getInstancia() {
		if (instancia == null)
			instancia = new ConexionMySQL();
		return instancia;
	}
	public Connection getConnection() {
		return connect;
	}
}
