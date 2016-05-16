package common;

/**
 * 
 * CRISTIAN SIMON MORENO NIP: 611487
 * 
 */
public class Position {

	public double latitude;
	public double longitude;

	/**
	 * Metodo constructor de la clase
	 * 
	 * @param lat
	 * @param lon
	 */
	public Position(double lat, double lon) {
		latitude = lat;
		longitude = lon;
	}// Fin Position

	/**
	 * Dada una posicion [a] devuelve la distancia hasta dicha posicion
	 * 
	 * @param a
	 * @return
	 */
	// public double distancia(double latitud, double longitud){
	/*
	 * return Math.sqrt((latitude-latitud)*(latitude-latitud)+
	 * (longitude-longitud)*(longitude-longitud)); }
	 */public double distancia(Position a) {
		double latitud = a.latitude - this.latitude;
		double longitud = a.longitude - this.longitude;
		return Math.sqrt(latitud * latitud + longitud * longitud);
	}// Fin distancia

	/**
	 * Devuelve un valor con una latitud y una longitud
	 */
	public double[] coordenadas() {
		double[] longlat;
		longlat = new double[2];
		longlat[0] = latitude;
		longlat[1] = longitude;
		return longlat;
	}

}// Fin Position
