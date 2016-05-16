package common;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class Main {
	/**
	 * @param args
	 * @throws StructureException
	 * @throws NoObjectException
	 * @throws SiguienteException
	 */
	public static void main(String[] args) throws NoObjectException,
			StructureException, SiguienteException {
		try {
			String usuario = (String) JOptionPane.showInputDialog(null,
					"Nombre de usuario base de datos:", "DB Login",
					JOptionPane.PLAIN_MESSAGE);
			JPasswordField jpf = new JPasswordField();
			JLabel titulo = new JLabel("Contraseña base de datos:");
			JOptionPane.showConfirmDialog(null, new Object[] { titulo, jpf },
					"DB contrasena", JOptionPane.PLAIN_MESSAGE);
			char p[] = jpf.getPassword();
			String pass = new String(p);
			gui.MainWindow window = new gui.MainWindow(
					new db.DatabaseConnection(
							"jdbc:oracle:thin:@hendrix-oracle.cps.unizar.es:1521:vicious",
							usuario, pass, args));
			window.start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// Fin main

}// Fin Main
