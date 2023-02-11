package fp.ajedrez;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import fp.utiles.Checkers;

/**
 * @author To�i Reina
 *
 */
public record Partida(Boolean clasificatoria, TipoVictoria tipoVictoria, Resultado resultado, String jugadorBlancas,
		String jugadorNegras, Integer ratingBlancas, Integer ratingNegras, List<String> movimientos, String apertura,
		LocalDate fecha, Duration duracion) implements Comparable<Partida> {

	private static final List<String> APERTURAS = List.of("h3", "h4", "g3", "g4", "f3", "f4", "e3", "e4", "d3", "d4",
			"c3", "c4", "b3", "b4", "a3", "a4", "Nh3", "Nf3", "Nc3", "Na3");

	public Partida {
		Checkers.check("La duraci�n debe estar entre 1 y 60 " + duracion,
				duracion.compareTo(Duration.ofMinutes(0)) > 0 && duracion.compareTo(Duration.ofMinutes(60)) <= 0);
		Checkers.check("El movimiento inicial no es un movimiento de apertura " + movimientos.get(0),
				APERTURAS.contains(movimientos.get(0)));
		Checkers.check("El rating de las blancas debe ser mayor que cero " + ratingBlancas, ratingBlancas > 0);
		Checkers.check("El rating de las negras debe ser mayor que cero " + ratingNegras, ratingNegras > 0);
	}

	/**
	 * @param clasificatoria Indica si la partida es clasificatoria o amistosa (si el valor es false). 
	 * @param tipoVictoria Indica el estado al que se ha llegado en la vistoria
	 * @param resultado Indica si han ganado las blancas, las negras o la partida ha quedado en tabla.
	 * @param jugadorBlancas Identificador del jugador con las blancas
	 * @param jugadorNegras Identificador del jugador con las negras
	 * @param ratingBlancas Rating del jugador con las blancas
	 * @param ratingNegras Rating del jugador con las negras
	 * @param movimientos Cadena que contiene la lista de movimientos de la partida separados por espacios en blanco
	 * @param apertura Tipo de apertura usado en la partida.
	 * @param fecha Fecha en la que se jug� la partida
	 * @param duracion Duraci�n de la partida.
	 * @throws IllegalArgumentException si la duraci�n no est� entre 1 y 60
	 * @throws IllegalArgumentException si el movimiento inicial no est� entre los movimientos conocidos de las aperturas
	 * @throws IllegalArgumentException si el rating de las blancas no es mayor que cero
	 * @throws IllegalArgumentException si el rating de las negras no es mayor que cero
	 */
	public Partida(Boolean clasificatoria, TipoVictoria tipoVictoria, Resultado resultado, 
			String jugadorBlancas,
			String jugadorNegras, Integer ratingBlancas, Integer ratingNegras, String movimientos, String apertura,
			LocalDate fecha, Integer duracion) {
		this(clasificatoria, tipoVictoria, resultado, jugadorBlancas, jugadorNegras, ratingBlancas, ratingNegras,
				Arrays.asList(movimientos.split(" ")), apertura, fecha, Duration.ofMinutes(duracion));
	}

	// Propiedades derivadas
	/**
	 * @return El movimiento con el que se realiza la apertura de la partida
	 */
	public String getMovimientoApertura() {
		return movimientos().get(0);
	}

	/**
	 * @return El n�mero total de movimientos realizados en la partida
	 */
	public Integer getNumMovimientos() {
		return movimientos().size();
	}

	/**
	 * @return El identificador del jugador que ha ganado la partida. Si la partida
	 *         ha quedado en tablas, devuelve null.
	 */
	public String getJugadorGanador() {
		String res = null;
		if (resultado.equals(Resultado.BLACK)) {
			res = jugadorNegras();
		} else if (resultado.equals(Resultado.WHITE)) {
			res = jugadorBlancas();
		}
		return res;
	}

	/**
	 * @return El rating del jugador que ha ganado la partida. Si la partida ha
	 *         quedado en tablas, devuelve null.
	 * 
	 */
	public Integer getRatingGanador() {
		Integer res = null;
		if (resultado().equals(Resultado.WHITE)) {
			res = ratingBlancas();
		} else if (resultado().equals(Resultado.BLACK)) {
			res = ratingNegras();
		}
		return res;
	}

	/**
	 * @return La diferencia de ratings entre los dos jugadores de la partida.
	 */
	public Integer getDiferenciaRating() {
		return Math.abs(ratingBlancas() - ratingNegras());
	}

	/**
	 * @param numMovimiento El n�mero de movimiento.
	 * @return El movimiento dado por el n�mero numMovimiento.
	 * @throws IllegalArgumentException si numMovimiento no est� en el intervalo [1, getNumMovimientos()]
	 */
	public String getMovimiento(Integer numMovimiento) {
		String msg = String.format("El movimiento ha de estar entre 1 y %d.", getNumMovimientos());
		Checkers.check(msg, numMovimiento > 0 && numMovimiento <= getNumMovimientos());
		return movimientos().get(numMovimiento-1);
	}

	/**
	 * Las partidas se ordenan por fecha, duraci�n y n�mero de movimientos
	 */
	public int compareTo(Partida p) {
		int r;
		r = fecha().compareTo(p.fecha());
		if (r == 0) {
			r = duracion().compareTo(p.duracion());
			if (r == 0) {
				r = getNumMovimientos().compareTo(p.getNumMovimientos());
			}
		}
		return r;
	}

}
