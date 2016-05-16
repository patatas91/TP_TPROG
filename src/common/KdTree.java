package common;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */

public class KdTree<E extends Observar> implements InterfazBuscarElementos<E> {

	/**
	 * Clase Nodo anidada dentro de la clase KdTree
	 * 
	 * @author simon
	 * 
	 * @param <E>
	 */
	@SuppressWarnings("hiding")
	class Nodo<E extends Observar> {
		private Nodo<E> Izquierda;
		private Nodo<E> Derecha;
		private E elemento;
		private double posicion[];

		/**
		 * Contructor de la clase
		 * 
		 * @param elemento
		 */
		public Nodo(E elemento) {
			Izquierda = null;
			Derecha = null;
			this.elemento = elemento;
			posicion = elemento.getPosition().coordenadas();
		}

		public E getElemento() {
			return elemento;
		}

	}// Fin Nodo

	private Nodo<E> raiz;

	/**
	 * Añade un nuevo elemento al Arbol KD
	 */
	public void añadir(E elemento) {
		raiz = añadir(elemento, raiz, 0);
	}

	public Nodo<E> añadir(E elemento, Nodo<E> nodo, int altura) {
		if (nodo == null) {
			nodo = new Nodo<E>(elemento);
		} else if (elemento.getPosition().coordenadas()[altura] < nodo.posicion[altura]) {
			nodo.Izquierda = añadir(elemento, nodo.Izquierda, 1 - altura);
		} else {
			nodo.Derecha = añadir(elemento, nodo.Derecha, 1 - altura);
		}
		return nodo;
	}

	@Override
	public E buscarElemento(E elemento) throws NoObjectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InterfazGuardarBusqueda<E> getClosest(E elemento, int n)
			throws NoObjectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inicializarIterador() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean haySiguiente() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E siguiente() throws SiguienteException {
		// TODO Auto-generated method stub
		return null;
	}

}