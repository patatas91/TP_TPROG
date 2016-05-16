package common;

import java.util.Comparator;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class CompararDistancias<E extends Observar> implements Comparator<E> {

	Position referencia;

	/**
	 * 
	 * @param x
	 */
	public CompararDistancias(Position elemento) {
		referencia = elemento;
	}

	/**
	 * 
	 */
	public int compare(E argumento1, E argumento2) {
		
		if (argumento1.getPosition().distancia(referencia) > argumento2
				.getPosition().distancia(referencia)) {
			return -1;
		} else if (argumento1.getPosition().distancia(referencia) < argumento2
				.getPosition().distancia(referencia)) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
