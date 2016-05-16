package common;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public interface InterfazBuscarElementos<E> {

	/**
	 * Añade el elemento [elemento]
	 */
	public void añadir(E elemento);

	/**
	 * Devuelve [cierto] si un elemento elemento pertenece al conjunto
	 * 
	 * @param elemento
	 * @return cierto
	 */
	public E buscarElemento(E elemento) throws NoObjectException;

	/**
	 * Devuelve los n elementos mas cercanos en una estructura [estructura]
	 * 
	 * @param estructura
	 *            , elemento, n
	 * @throws NoObjectException
	 */
	public InterfazGuardarBusqueda<E> getClosest(E elemento, int n)
			throws NoObjectException;

	/**
	 * Inicializa un iterador interno
	 */
	public void inicializarIterador();

	/**
	 * Devuelve [cierto] si hay elemento siguiente
	 * 
	 * @return cierto
	 */
	public boolean haySiguiente();

	/**
	 * Si hay siguiente elemento siguiente lo devuelve. En caso contrario se
	 * produce un error
	 * 
	 * @throws SiguienteException
	 */
	public E siguiente() throws SiguienteException;

}// Fin InterfazBuscarElementos
