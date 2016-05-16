package common;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public interface InterfazGuardarBusqueda<E> {

	/**
	 * Añade el elemento [elemento]
	 */
	public void añadir(E elemento);

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

	/**
	 * Vacia la estructura
	 */
	public void vaciar(int n);

}// Fin InterfazGuardarBusqueda
