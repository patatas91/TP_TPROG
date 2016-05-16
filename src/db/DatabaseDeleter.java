package db;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseDeleter {

	public void deleteDatabase(String driver, String username, String password)
			throws ClassNotFoundException {
		Connection con;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(driver, username, password);

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE users ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		System.out.print("Propietario base de datos: ");
		Scanner sc = new Scanner(System.in);
		String owner = sc.nextLine();
		System.out.print("Contrase�a: ");
		String password = sc.nextLine();
		DatabaseDeleter deleter = new DatabaseDeleter();
		deleter.deleteDatabase(
				"jdbc:oracle:thin:@hendrix-oracle.cps.unizar.es:1521:vicious",
				owner, password);
	}

}// Fin DatabaseDeleter
