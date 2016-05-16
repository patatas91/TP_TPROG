package common;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.util.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Main2 {
	/**
	 * @param args
	 */

	public static void main(String[] args) {

		System.out.print("Propietario base de datos: ");
		Scanner sc = new Scanner(System.in);
		String owner = sc.nextLine();
		System.out.print("Contraseña: ");
		String password = sc.nextLine();

		db.DatabaseConnection2 datab = new db.DatabaseConnection2(
				"jdbc:oracle:thin:@hendrix-oracle.cps.unizar.es:1521:vicious",
				owner, password);
		System.out.print("Usuario: ");
		String user = sc.nextLine();
		System.out.println();
		try {
			if (!datab.loginUser(user))
				datab.addUser(user, new common.Position(0.0, 0.0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String dummy, dummy2;
		Vector<common.User> vdummy;
		common.Position dummypos = new common.Position(0.0, 0.0);

		boolean quit = false;
		int menuItem;
		do {
			// Text menu
			System.out.println("1. Listar usuarios");
			System.out.println("2. Añadir usuario");
			System.out.println("3. Mostrar posicion");
			System.out.println("4. Actualizar posicion");
			System.out.println("5. Mostrar n mas cercanos");
			System.out.println("0. Salir");
			System.out.print("Elige una opcion: ");
			menuItem = sc.nextInt();
			dummy = sc.nextLine();
			switch (menuItem) {
			case 1:
				System.out.println("Listado de usuarios");
				try {
					vdummy = datab.getUsers();
					for (int i = 0; i < vdummy.size(); i++) {
						System.out.println("Usuario: "
								+ vdummy.get(i).name.trim() + "; posicion: "
								+ vdummy.get(i).position.longitude + "º E; "
								+ vdummy.get(i).position.latitude + "º N");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case 2:
				System.out.print("Usuario a añadir: ");
				dummy = sc.nextLine();
				System.out.print("Nueva longitud: ");
				dummy2 = sc.nextLine();
				try {
					dummypos.longitude = Double.valueOf(dummy2).doubleValue();
				} catch (NumberFormatException nfe) {
					System.out.println("Formato incorrecto");
					continue;
				}
				System.out.print("Nueva latitud: ");
				dummy2 = sc.nextLine();
				try {
					dummypos.latitude = Double.valueOf(dummy2).doubleValue();
				} catch (NumberFormatException nfe) {
					System.out.println("Formato incorrecto");
					continue;
				}

				try {
					datab.addUser(dummy, dummypos);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case 3:
				try {
					dummypos = datab.getPosition(user);
					System.out.println("Longitud: " + dummypos.longitude
							+ "º E; Latitud: " + dummypos.latitude + "º N");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case 4:
				System.out.print("Nueva longitud: ");
				dummy = sc.nextLine();
				try {
					dummypos.longitude = Double.valueOf(dummy).doubleValue();
				} catch (NumberFormatException nfe) {
					System.out.println("Formato incorrecto");
					continue;
				}
				System.out.print("Nueva latitud: ");
				dummy = sc.nextLine();
				try {
					dummypos.latitude = Double.valueOf(dummy).doubleValue();
				} catch (NumberFormatException nfe) {
					System.out.println("Formato incorrecto");
					continue;
				}
				try {
					datab.updatePosition(user, dummypos);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case 5:
				System.out.println("Cuantos usuarios?: ");
				Scanner entrada = new Scanner(System.in);
				int usuarios = entrada.nextInt();
				try {
					datab.getClotest(user, usuarios);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			case 0:
				System.out.println("Hasta luego");
				quit = true;
				break;
			default:
				System.out.println("Opcion invalida");
			}
		} while (!quit);

	}

}
