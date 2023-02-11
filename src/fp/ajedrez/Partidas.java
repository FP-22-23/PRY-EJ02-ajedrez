package fp.ajedrez;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

public interface Partidas {

	/**
	 * @return El n�mero de partidas almacenado en el objeto Partida.
	 */
	Integer getNumeroPartidas();

	/**
	 * @param p partida de ajedrez
	 * A�ade una partida de ajedrez al objeto-
	 */
	void agregarPartida(Partida p);

	/**
	 * FUNCION TIPO 7
	 * 
	 * @param vic Tipo de victoria
	 * @return Devuelve la media de la duraci�n media(en segundos) por turno de las partidas. Si la media no se puede
	 *         calcular, devuelve cero.
	 */
	Double getPromedioDuracionesMedias(TipoVictoria vic);

	/**
	 * FUNCI�N TIPO ??
	 * @param movimiento Movimiento concreto, por ejemplo, "Nc6" o "Bc4"
	 * @param numeroMovimiento N�mero movimiento
	 * @return Devuelve un Map en el que las claves son movimientos siguientes al dado como par�metro (seg�n el movimiento y 
	 *    la posici�n en la que se hace), y los valores el porcentaje de veces que se ha hecho ese movimiento. Por ejemplo,
	 *    si el movimiento es "Nc6" y el n�mero de movimiento es el 6, el Map contiene
	 *    como claves los movimientos hechos en s�ptimo lugar tras un movimiento "Nc6".
	 *    Los valores ser�n el porcentaje de veces que se han hecho esos movimientos.
	 *    
	 * @throws IllegalArgumentException si numeroMovimiento no es mayor o igual que uno.
	 */
	Map<String, Double> getPorcentajesSiguienteMovimiento(String movimiento, Integer numeroMovimiento);

	/**
	 * FUNCI�N TIPO ??
	 * @param apertura Nombre de la apertura utilizada.
	 * @param resultado Resultado de la partida.
	 * @return Devuelve
	 * el porcentaje de partidas que incluyen la cadena de apertura 
	 * en su apertura y cuyo resultado es el dado como par�metro
	 */
	Double getPorcentajeVictoriasDeApertura(String apertura, Resultado resultado);

	/**
	 * FUNCI�N TIPO 10
	 * @param anyo A�o
	 * @param n N�mero entero
	 * @return Un conjunto ordenado con las n partidas m�s cortas jugadas en el a�o dado como 
	 * par�metro. El conjunto estar� ordenado por el n�mero de movimientos de la partida.
	 */
	SortedSet<Partida> getNPartidasMasCortas(Integer anyo, Integer n);

	/**
	 * FUNCI�N TIPO ??
	 * @param anyo A�o
	 * @param n N�mero entero
	 * @return Una lista con los ids de los n jugadores con m�s victorias en el a�o dado como par�metro.
	 */
	List<String> getNMejoresJugadores(Integer anyo, Integer n);

	/**
	 * FUNCI�N TIPO 7/TIPO 2
	 * @param idJugador Identificador de un jugador
	 * @return El total de minutos jugados por el jugador dado como par�metro.
	 */
	Long getTiempoTotalJuego(String idJugador);

	/**
	 * FUNCION TIPO 9
	 * @param anyo Un a�o
	 * @param resultado Un tipo de resultado
	 * @return El id del usuario que ha ganado m�s partidas en
	 * el a�o y con el a�o dados como par�metro. 
	 * @throws NoSuchElementException Si no se puede calcular el m�ximo.
	 */
	String getJugadorMasVictorias(Integer anyo, Resultado resultado);

	/**
	 * FUNCI�N TIPO 6
	 * @param n N�mero 
	 * @return true si hay alg�n jugador que tenga m�s de n victorias
	 */
	Boolean hayJugadorConMasNVictorias(Integer n);

	/**
	 * FUNCI�N TIPO 13
	 * @param n N�mero entero n
	 * @return Un map en el que las claves son los tipos de victoria y el valor
	 * es el en�simo jugador con m�s rating entre los jugadores que han tenido
	 * victorias de ese tipo. Es decir, si hacemos un ranking de los jugadores
	 * seg�n su rating, nos quedar�amos con el que est� en la posici�n n
	 */
	Map<TipoVictoria, String> getGanadorNPorTipoVictoria(Integer n);

}