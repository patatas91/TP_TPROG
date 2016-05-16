package db;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.sql.*;
import common.Factory;
import common.InterfazBuscarElementos;
import common.InterfazGuardarBusqueda;
import common.NoObjectException;
import common.Position;
import common.StructureException;
import common.User;

public class DatabaseConnection {

	private String db_driver;
	private String db_username;
	private String db_password;
	private String[] factory;

	// public String usuario;

	public DatabaseConnection(String driver, String username, String password,
			String[] args) {
		db_driver = driver;
		db_username = username;
		db_password = password;
		// usuario = null;
		factory = args;

		if (driver.contains("oracle")) {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				System.err.println("Oracle driver not found");
				e.printStackTrace();
			}
		}
	}// Fin DatabaseConnection

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

		// Close the result set, statement and the connection
		rs.close();
		stmt.close();
		connection.close();

		// Return position
		return position;
	}// Fin getPosition

	/**
	 * Identifica al usuario [user] con la base de datos
	 * 
	 * @param user
	 * @return boolean
	 * @throws SQLException
	 */
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
		// usuario = user;
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
	}// Fin updatePosition

	/**
	 * Dado un string con el nombre del usuario, devuelve un objeto User con los
	 * datos del usuario con dicho nombre
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User getReferencia(String user) throws SQLException {
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);
		// Get a statement from the connection
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM users WHERE Nombre LIKE '" + user
						+ "'");
		String nombre = null;
		Position posicion = null;
		boolean acabar = false;
		while (rs.next() && !acabar) {
			nombre = rs.getString("Nombre");
			posicion = new Position(rs.getFloat("Latitud"),
					rs.getFloat("Longitud"));
			acabar = true;
		}
		return new User(nombre, posicion);
	}// Fin getReferencia

	/**
	 * Devuelve una estructura con los nombres y posiciones de los usuarios
	 * almacenados en la base de datos
	 * 
	 * @return
	 * @throws SQLException
	 * @throws StructureException
	 */
	public InterfazBuscarElementos<User> getUsers() throws SQLException,
			StructureException {
		Connection connection = DriverManager.getConnection(db_driver,
				db_username, db_password);

		// Get a statement from the connection
		Statement stmt = connection.createStatement();

		// Execute the query
		ResultSet rs = stmt.executeQuery("SELECT * FROM users");

		InterfazBuscarElementos<User> result = new Factory<common.User>()
				.SearchSt(factory);

		while (rs.next()) {

			String nombre = rs.getString(1);
			common.Position p = new common.Position(rs.getDouble(2),
					rs.getDouble(3));
			common.User user = new common.User(nombre.trim(), p);
			result.añadir(user);
		}

		// Close the statement and the connection
		stmt.close();
		connection.close();

		// Return structure
		return result;
	}// Fin getUsers

	/**
	 * Devuelve una estructura con los n usuarios mas proximos al usuario
	 * 
	 * @param user
	 * @param n
	 * @throws SQLException
	 * @throws NoObjectException
	 * @throws StructureException
	 */
	public InterfazGuardarBusqueda<User> getClosest(String user, int n)
			throws SQLException, NoObjectException, StructureException {
		InterfazBuscarElementos<common.User> users = getUsers();
		User aux = new User(user, getPosition(user));
		return users.getClosest(aux, n);
	}// Fin getClotest

}// Fin DatabaseConection
