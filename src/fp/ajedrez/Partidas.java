package fp.ajedrez;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface Partidas {

	/**
	 * @return El número de partidas almacenado en el objeto Partida.
	 */
	Integer getNumeroPartidas();

	/**
	 * @param p partida de ajedrez
	 * Añade una partida de ajedrez al objeto-
	 */
	void agregarPartida(Partida p);

	/**
	 * FUNCION TIPO 7
	 * 
	 * @param vic Tipo de victoria
	 * @return Devuelve la media de la duración media(en segundos) por turno de las partidas. Si la media no se puede
	 *         calcular, devuelve cero.
	 */
	Double getPromedioDuracionesMedias(TipoVictoria vic);

	/**
	 * FUNCIÓN TIPO ??
	 * @param movimiento Movimiento concreto, por ejemplo, "Nc6" o "Bc4"
	 * @param numeroMovimiento Número movimiento
	 * @return Devuelve un Map en el que las claves son movimientos siguientes al dado como parámetro (según el movimiento y 
	 *    la posición en la que se hace), y los valores el porcentaje de veces que se ha hecho ese movimiento. Por ejemplo,
	 *    si el movimiento es "Nc6" y el número de movimiento es el 6, el Map contiene
	 *    como claves los movimientos hechos en séptimo lugar tras un movimiento "Nc6".
	 *    Los valores serán el porcentaje de veces que se han hecho esos movimientos.
	 *    
	 * @throws IllegalArgumentException si numeroMovimiento no es mayor o igual que uno.
	 */
	Map<String, Double> getPorcentajesSiguienteMovimiento(String movimiento, Integer numeroMovimiento);

	/**
	 * FUNCIÓN TIPO ??
	 * @param apertura Nombre de la apertura utilizada.
	 * @param resultado Resultado de la partida.
	 * @return Devuelve
	 * el porcentaje de partidas que incluyen la cadena de apertura 
	 * en su apertura y cuyo resultado es el dado como parámetro
	 */
	Double getPorcentajeVictoriasDeApertura(String apertura, Resultado resultado);

	/**
	 * FUNCIÓN TIPO 10
	 * @param anyo Año
	 * @param n Número entero
	 * @return Un conjunto ordenado con las n partidas más cortas jugadas en el año dado como 
	 * parámetro. El conjunto estará ordenado por el número de movimientos de la partida.
	 */
	SortedSet<Partida> getNPartidasMasCortas(Integer anyo, Integer n);

	/**
	 * FUNCIÓN TIPO ??
	 * @param anyo Año
	 * @param n Número entero
	 * @return Una lista con los ids de los n jugadores con más victorias en el año dado como parámetro.
	 */
	List<String> getNMejoresJugadores(Integer anyo, Integer n);

	/**
	 * FUNCIÓN TIPO 7/TIPO 2
	 * @param idJugador Identificador de un jugador
	 * @return El total de minutos jugados por el jugador dado como parámetro.
	 */
	Long getTiempoTotalJuego(String idJugador);

	/**
	 * FUNCION TIPO 9
	 * @param anyo Un año
	 * @param resultado Un tipo de resultado
	 * @return El id del usuario que ha ganado más partidas en
	 * el año y con el año dados como parámetro. 
	 * @throws NoSuchElementException Si no se puede calcular el máximo.
	 */
	String getJugadorMasVictorias(Integer anyo, Resultado resultado);

	/**
	 * FUNCIÓN TIPO 6
	 * @param n Número 
	 * @return true si hay algún jugador que tenga más de n victorias
	 */
	Boolean hayJugadorConMasNVictorias(Integer n);

	/**
	 * FUNCIÓN TIPO 13
	 * @param n Número entero n
	 * @return Un map en el que las claves son los tipos de victoria y el valor
	 * es el enésimo jugador con más rating entre los jugadores que han tenido
	 * victorias de ese tipo. Es decir, si hacemos un ranking de los jugadores
	 * según su rating, nos quedaríamos con el que está en la posición n
	 */
	Map<TipoVictoria, String> getGanadorNPorTipoVictoria(Integer n);

}