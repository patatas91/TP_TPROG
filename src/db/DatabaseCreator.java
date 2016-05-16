package db;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.sql.*;
import java.util.Scanner;

public class DatabaseCreator {

	public void createDatabase(String driver, String username, String password)
			throws ClassNotFoundException {
		Connection con;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager.getConnection(driver, username, password);

			Statement stmt = con.createStatement();
			stmt.executeUpdate("CREATE TABLE users "
					+ "( Nombre char(100) UNIQUE NOT NULL,"
					+ " Latitud float NOT NULL," + " Longitud float NOT NULL)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Alfredo',41.0,1.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Ana',42.0,-4.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Paula',38.0,-1.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Sergio',40.0,4.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Sandra',37.0,1.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Antonio',40.0,-1.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Pedro',34.8,-4.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Alberto',35.0,9.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Laura',44,-3.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Gustavo',35.0,8.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('McManaman',40.0,3.5)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Fernando',38.0,8.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Perez',41.0,-8.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Ivan',36.2,-2.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Godofredo',39.4,9.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Apoño',44.3,5.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Patricia',35.9,8.0)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Maik',42.2,3.5)");
			stmt.executeUpdate("INSERT INTO users VALUES ('Guillermo',36.1,6.0)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, InstantiationException, IllegalAccessException {
		System.out.print("Propietario base de datos: ");
		Scanner sc = new Scanner(System.in);
		String owner = sc.nextLine();
		System.out.print("Contraseña: ");
		String password = sc.nextLine();
		DatabaseCreator creator = new DatabaseCreator();
		creator.createDatabase(
				"jdbc:oracle:thin:@hendrix-oracle.cps.unizar.es:1521:vicious",
				owner, password);
	}

}// Fin DatabaseCreator
