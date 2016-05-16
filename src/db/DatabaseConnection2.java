package db;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.sql.*;
import java.util.Vector;

public class DatabaseConnection2 {

	private String db_driver;
	private String db_username;
	private String db_password;

	public DatabaseConnection2(String driver, String username, String password) {
		db_driver = driver;
		db_username = username;
		db_password = password;

		if (driver.contains("oracle")) {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.err.println("Oracle driver not found");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Añade un nuevo usuario a la base de datos
	 * 
	 * @param user
	 * @param position
	 * @throws SQLException
	 */
	public void addUser(String user, common.Position position)
			throws SQLException {
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);

		// Get a statement from the connection
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("INSERT INTO users VALUES " + "('" + user + "',"
				+ position.latitude + "," + position.longitude + ")");

		// Close the statement and the connection
		stmt.close();
		connection.close();
	}// Fin addUser

	/**
	 * Realiza una consulta a la base de datos para averiguar la posicion
	 * geografica del usuario
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public common.Position getPosition(String user) throws SQLException {
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		common.Position position = new common.Position(0, 0);
		// Get a statement from the connection
		Statement stmt = connection.createStatement();
		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE Nombre='"
				+ user + "'");
		rs.next();
		common.Position p = new common.Position(rs.getDouble(2),
				rs.getDouble(3));
		position = p;
		// Return position
		return position;
	}// Fin getPosition

	public boolean loginUser(String user) throws SQLException {
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);

		// Get a statement from the connection
		Statement stmt = connection.createStatement();
		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE Nombre='"
				+ user + "'");

		// If there is a single result, we update latitude and longitude
		boolean sol = rs.next();

		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		connection.close();

		return sol;
	}// Fin loginUser

	/**
	 * Actualiza la posicion geografica del usuario en la base de datos
	 * 
	 * @param user
	 * @param position
	 * @throws SQLException
	 */
	public void updatePosition(String user, common.Position position)
			throws SQLException {
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);

		// Get a statement from the connection
		Statement stmt = connection.createStatement();
		// Execute the update
		stmt.executeUpdate("UPDATE users SET Latitud = " + position.latitude
				+ ", Longitud = " + position.longitude + " WHERE Nombre = '"
				+ user + "'");
		// Close the statement and the connection
		stmt.close();
		connection.close();
	}// updatePosition

	/**
	 * Devuelve un vector con los nombres y posiciones de los usuarios
	 * almacenados en la base de datos
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Vector<common.User> getUsers() throws SQLException {
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);

		// Get a statement from the connection
		Statement stmt = connection.createStatement();
		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM users");

		Vector<common.User> result = new Vector<common.User>();

		while (rs.next()) {

			String nombre = rs.getString(1);
			common.Position p = new common.Position(rs.getDouble(2),
					rs.getDouble(3));
			common.User user = new common.User(nombre, p);
			result.add(user);
		}

		// Close the statement and the connection
		stmt.close();
		connection.close();
		// Return vector
		return result;
	}// getUsers

	/**
	 * Devuelve un vector con los n usuarios mas proximos al usuario
	 * 
	 * @param user
	 * @param n
	 * @return
	 * @throws SQLException
	 */
	public Vector<common.User> getClotest(String user, int n)
			throws SQLException {
		Vector<common.User> tabla = getUsers();
		Vector<common.User> result = new Vector<common.User>();
		common.Position posicionUsuario = getPosition(user);
		common.User menorUsuario = tabla.get(0);
		common.Position maxima = getPosition(user);
		int indice = 0;
		while (indice <= n) {
			for (int i = 0; i < tabla.size(); i++) {
				common.Position p = tabla.get(i).position;
				if (p.distancia(posicionUsuario) > maxima
						.distancia(posicionUsuario)
						&& p.distancia(posicionUsuario) < menorUsuario.position
								.distancia(posicionUsuario)) {
					menorUsuario = new common.User(tabla.get(i).name, p);
				}
			}
			result.add(menorUsuario);
			maxima = menorUsuario.position;
			int indice2 = 0;
			boolean encontrado = false;
			while (!encontrado) {
				if (posicionUsuario.distancia(tabla.get(indice2).position) > posicionUsuario
						.distancia(menorUsuario.position)) {
					encontrado = true;
					menorUsuario = tabla.get(indice2);
				} else {
					indice2++;
				}
			}
		}
		return result;
	}// getClotest

}// Fin DatabaseConection2
