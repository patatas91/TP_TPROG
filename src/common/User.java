package common;

import java.text.DecimalFormat;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class User implements Observar {

	/*
	 * Atributos de la clase
	 */
	public String name;
	public Position position;

	/**
	 * Constructor de la clase
	 * 
	 * @param n
	 * @param p
	 */
	public User(String n, Position p) {
		name = n;
		position = p;
	}// Fin User

	/**
	 * Devuelve el nombre del usuario
	 */
	public String getNombre() {
		return name;
	}// Fin getNombre

	/**
	 * Devuelve la clave del usuario
	 */
	public String getClave() {
		return name;
	}// Fin getClave

	/**
	 * Devuelve la posicion del usuario
	 */
	public Position getPosition() {
		return position;
	}// Fin getPosition

	public String toString() {
		DecimalFormat df = new DecimalFormat("##.##");
		return name.trim() + ": " + df.format(position.latitude) + " N; "
				+ df.format(position.longitude) + " O";
	}// Fin toString

}// Fin User

