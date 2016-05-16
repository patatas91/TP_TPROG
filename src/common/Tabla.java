package common;

/**
 * 
 * CRISTIAN SIMON MORENO      NIP: 611487
 * 
 */
import java.util.Vector;

public class Tabla<E extends Observar> implements InterfazGuardarBusqueda<E>,
		InterfazBuscarElementos<E> {

	Vector<E> v;
	int iterador;
	InterfazGuardarBusqueda<E> estructura;

	/**
	 * Crea un vector vacio con una estructura para guardar los elementos como
	 * parametro
	 */
	public Tabla(InterfazGuardarBusqueda<E> estructur) {
		v = new Vector<E>();
		iterador = 0;
		estructura = estructur;
	}// Fin Tabla

	/**
	 * Crea un vector vacio
	 */
	public Tabla() {
		v = new Vector<E>();
		iterador = 0;
	}// Fin Tabla

	/**
	 * Añade un elemento al Vector
	 */
	public void añadir(E elemento) {
		v.add(elemento);
	}// Fin añadir

	/**
	 * Incializa el interador interno del Vector
	 */
	public void inicializarIterador() {
		iterador = 0;
	}// Fin inicializarIterador

	/**
	 * Devuelve cierto si hay elemento siguiente
	 */
	public boolean haySiguiente() {
		return iterador != v.size();
	}// Fin haySiguiente

	/**
	 * Devuelve el elemento siguiente del vector. En caso de que el elemento no
	 * este devuelve null
	 * 
	 * @return elemento
	 * @throws SiguienteException
	 */
	public E siguiente() throws SiguienteException {
		if (haySiguiente()) {
			E elemento = v.get(iterador);
			iterador += 1;
			return elemento;
		} else {
			throw new SiguienteException("Error, no hay "
					+ "elemento siguiente");
		}
	}// Fin siguiente

	/**
	 * Busca en el Vector el elemento [elemento]
	 * 
	 * @return elemento
	 * @throws NoObjectException
	 */
	public E buscarElemento(E elemento) throws NoObjectException {
		if (!v.isEmpty()) {
			E aux = null;
			for (int i = 0; i < v.size(); i++) {
				if (v.get(i).getClave().equalsIgnoreCase(elemento.getClave())) {
					aux = v.get(i);
				}
			}
			return aux;
		} else {
			throw new NoObjectException("Error, no hay elementos");
		}
	}// Fin buscarElemento

	/**
	 * Devuelve los n elementos mas cercanos en una estructura [estructura]
	 * 
	 * @param estructura
	 *            , elemento, n
	 * @throws NoObjectException
	 */
	public InterfazGuardarBusqueda<E> getClosest(E elemento, int n)
			throws NoObjectException {
		if (n <= v.size() && !v.isEmpty()) {

			// Crea un vector auxiliar
			Vector<E> aux = v;
			// Elimina los datos existentes en la estructura
			estructura.vaciar(n);
			InterfazGuardarBusqueda<E> St = estructura;

			E usuarioBase = buscarElemento(elemento);
			// System.out.println(usuarioBase.toString());
			Position posicionBase = usuarioBase.getPosition();
			aux.remove(usuarioBase);

			int iteraciones = 1;

			while (iteraciones < n) {
				// Coge como menor el primer elemento del vector auxiliar
				E menor = aux.get(0);
				for (int i = 0; i < aux.size(); i++) {
					// Busca el menor
					if (aux.get(i).getPosition().distancia(posicionBase) < menor
							.getPosition().distancia(posicionBase)) {
						// Si encuentra otro dato menor que el anterior lo
						// cambia
						menor = aux.get(i);
					}
				}
				// lo añade a la estructura y lo elimina del vector auxiliar
				St.añadir(menor);
				aux.remove(menor);
				iteraciones++;
			}
			return St;
		} else {
			throw new NoObjectException("Error, no hay elementos");
		}
	}// Fin getClotest

	/**
	 * Vacia el vector
	 */
	public void vaciar(int n) {
		v = new Vector<E>();
	}// Fin vaciar

}// Fin Tabla
