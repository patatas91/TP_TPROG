package common;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class MonticuloElementos<E extends Observar> implements
		InterfazGuardarBusqueda<E> {

	PriorityQueue<E> M;
	Iterator<E> I;
	Comparator<E> comparador;

	/**
	 * Crea un monticulo vacio
	 */
	public MonticuloElementos(int elementos, Comparator<E> comparador) {
		this.comparador = comparador;
		M = new PriorityQueue<E>();
	}// Fin MonticuloElementos

	/**
	 * Añade un elemento al monticulo
	 */
	public void añadir(E elemento) {
		M.add(elemento);
	}// Fin añadir

	/**
	 * Inicializa el iterador interno del monticulo
	 */
	public void inicializarIterador() {
		I = M.iterator();
	}// Fin inicializarIterador

	/**
	 * Devuelve cierto si hay elemento siguiente en el monticulo
	 */
	public boolean haySiguiente() {
		return I.hasNext();
	}// Fin haySiguiente

	/**
	 * Devuelve el elemento siguiente del monticulo
	 * 
	 * @throws SiguienteException
	 */
	public E siguiente() throws SiguienteException {
		if (haySiguiente()) {
			return I.next();
		} else {
			throw new SiguienteException("Error, no hay "
					+ "elemento siguiente");
		}
	}// Fin siguiente

	/**
	 * Vacia el monticulo
	 */
	public void vaciar(int n) {
		M = new PriorityQueue<E>(n, comparador);
	}// Fin vaciar

}// Fin MonticuloElementos
