package common;

import java.util.Comparator;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class Factory<E extends Observar> {

	public InterfazBuscarElementos<E> SearchSt(String[] args)
			throws StructureException {

		InterfazGuardarBusqueda<E> busqueda = null;
		Comparator<E> comparador = new CompararDistancias<E>(new Position(
				41.39, 0.53));
		/*
		 * Crea la estructura para guardar los elementos (VECTOR O MONTICULO)
		 */
		if (args[1].equalsIgnoreCase("vector")) {
			busqueda = new Tabla<E>();
		} else if (args[1].equalsIgnoreCase("monticulo")) {
			busqueda = new MonticuloElementos<E>(0, comparador);
		} else {
			throw new StructureException("Error, estructuras mal definidas");
		}

		/*
		 * Crea la estructura de busqueda de elementos (VECTOR O KDTREE)
		 */
		if (args[0].equalsIgnoreCase("vector")) {
			return new Tabla<E>(busqueda);
		} else if (args[0].equalsIgnoreCase("kdtree")) {
			return null;// new KdTree<E>(busqueda);
		} else {
			throw new StructureException("Error, estructuras mal definidas");
		}
	}// Fin SearchSt

}// Fin Factory
