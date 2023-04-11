package fp.ajedrez;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fp.utiles.Checkers;

/**
 * @author Toñi Reina
 *
 */
public class Partida implements Comparable<Partida> {
	
	private Boolean clasificatoria;
	private TipoVictoria tipoVictoria;
	private Resultado resultado; 
	private Jugador jugadorBlancas;
	private Jugador jugadorNegras;
	private List<String> movimientos;
	private String apertura;
	private LocalDate fecha;
	private Duration duracion;
	private static final List<String> APERTURAS = List.of("h3", "h4", "g3", "g4", "f3", "f4", "e3", "e4", "d3", "d4",
			"c3", "c4", "b3", "b4", "a3", "a4", "Nh3", "Nf3", "Nc3", "Na3");

	public Partida(Boolean clasificatoria, TipoVictoria tipoVictoria, Resultado resultado, String jugadorBlancas,
			String jugadorNegras, Integer ratingBlancas, Integer ratingNegras, List<String> movimientos, String apertura,
			LocalDate fecha, Duration duracion) {
		Checkers.check("La duración debe estar entre 1 y 60 " + duracion,
				duracion.compareTo(Duration.ofMinutes(0)) > 0 && duracion.compareTo(Duration.ofMinutes(60)) <= 0);
		Checkers.check("El movimiento inicial no es un movimiento de apertura " + movimientos.get(0),
				APERTURAS.contains(movimientos.get(0)));
		Checkers.check("El rating de las blancas debe ser mayor que cero " + ratingBlancas, ratingBlancas > 0);
		Checkers.check("El rating de las negras debe ser mayor que cero " + ratingNegras, ratingNegras > 0);
		this.clasificatoria=clasificatoria;
		this.tipoVictoria = tipoVictoria;
		this.resultado = resultado;
		this.jugadorBlancas = new Jugador(jugadorBlancas, ratingBlancas);
		this.jugadorNegras = new Jugador(jugadorNegras, ratingNegras);
		this.movimientos = new ArrayList<>(movimientos);
		this.apertura = apertura;
		this.fecha = fecha;
		this.duracion = duracion;	
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
	 * @param duracion Duración de la partida.
	 * @throws IllegalArgumentException si la duración no está entre 1 y 60
	 * @throws IllegalArgumentException si el movimiento inicial no está entre los movimientos conocidos de las aperturas
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

	
	/**
	 * @return Si la partida es clasificatoria
	 */
	public Boolean esClasificatoria() {
		return clasificatoria;
	}


	/**
	 * @return El tipo de victoria.
	 */
	public TipoVictoria getTipoVictoria() {
		return tipoVictoria;
	}


	/**
	 * @return El resultado
	 */
	public Resultado getResultado() {
		return resultado;
	}


	/**
	 * @return El jugador de blancas
	 */
	public Jugador getJugadorBlancas() {
		return jugadorBlancas;
	}


	/**
	 * @return El jugador de negras
	 */
	public Jugador getJugadorNegras() {
		return jugadorNegras;
	}


	public List<String> getMovimientos() {
		return new ArrayList<>(movimientos);
	}


	public String getApertura() {
		return apertura;
	}


	public LocalDate getFecha() {
		return fecha;
	}


	public Duration getDuracion() {
		return duracion;
	}


	// Propiedades derivadas
	/**
	 * @return El movimiento con el que se realiza la apertura de la partida
	 */
	public String getMovimientoApertura() {
		return movimientos.get(0);
	}

	/**
	 * @return El número total de movimientos realizados en la partida
	 */
	public Integer getNumMovimientos() {
		return movimientos.size();
	}

	/**
	 * @return El identificador del jugador que ha ganado la partida. Si la partida
	 *         ha quedado en tablas, devuelve null.
	 */
	public String getJugadorGanador() {
		String res = null;
		if (resultado.equals(Resultado.BLACK)) {
			res = getJugadorNegras().id();
		} else if (resultado.equals(Resultado.WHITE)) {
			res = getJugadorBlancas().id();
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
		if (getResultado().equals(Resultado.WHITE)) {
			res = getJugadorBlancas().rating();
		} else if (getResultado().equals(Resultado.BLACK)) {
			res = getJugadorNegras().rating();
		}
		return res;
	}

	/**
	 * @return La diferencia de ratings entre los dos jugadores de la partida.
	 */
	public Integer getDiferenciaRating() {
		return Math.abs(getJugadorBlancas().rating() - getJugadorNegras().rating());
	}

	/**
	 * @param numMovimiento El número de movimiento.
	 * @return El movimiento dado por el número numMovimiento.
	 * @throws IllegalArgumentException si numMovimiento no está en el intervalo [1, getNumMovimientos()]
	 */
	public String getMovimiento(Integer numMovimiento) {
		String msg = String.format("El movimiento ha de estar entre 1 y %d.", getNumMovimientos());
		Checkers.check(msg, numMovimiento > 0 && numMovimiento <= getNumMovimientos());
		return getMovimientos().get(numMovimiento-1);
	}

	/**
	 * Las partidas se ordenan por fecha, duración y número de movimientos
	 */
	public int compareTo(Partida p) {
		int r;
		r = getFecha().compareTo(p.getFecha());
		if (r == 0) {
			r = getDuracion().compareTo(p.getDuracion());
			if (r == 0) {
				r = getNumMovimientos().compareTo(p.getNumMovimientos());
			}
		}
		return r;
	}

}
