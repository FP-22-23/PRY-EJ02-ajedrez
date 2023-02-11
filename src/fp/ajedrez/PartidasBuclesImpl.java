package fp.ajedrez;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fp.utiles.Checkers;

/**
 * @author reinaqu_2
 *
 */
public class PartidasBuclesImpl implements Partidas {

	private List<Partida> partidas;

	/**
	 * Crea un objeto de tipo partidas sin partidas.
	 */
	public PartidasBuclesImpl() {
		partidas = new ArrayList<Partida>();
	}

	/**
	 * @param partidas Colección de partidas. Crea un objeto de tipo Partidas a
	 *                 partir de una colección de partidas de ajedrez
	 */
	public PartidasBuclesImpl(Collection<Partida> partidas) {
		this.partidas = new ArrayList<Partida>(partidas);
	}

	/**
	 * @param partidas Stream de partidas Crea un objeto de tipo Partidas a partir
	 *                 de una colección de partidas de ajedrez.
	 */
	public PartidasBuclesImpl(Stream<Partida> partidas) {
		this.partidas = partidas.collect(Collectors.toList());
	}

	@Override
	public Integer getNumeroPartidas() {
		return partidas.size();
	}

	@Override
	public void agregarPartida(Partida p) {
		partidas.add(p);
	}
	
	
	public int hashCode() {
		return Objects.hash(partidas);
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartidasBuclesImpl other = (PartidasBuclesImpl) obj;
		return Objects.equals(partidas, other.partidas);
	}

	/**
	 * FUNCION TIPO 7
	 * 
	 * @param vic Tipo de victoria
	 * @return Devuelve la media de la duración media(en segundos) por turno de las partidas. Si la media no se puede
	 *         calcular, devuelve cero.
	 */
	@Override
	public Double getPromedioDuracionesMedias(TipoVictoria vic) {
		Double res = 0.0;
		Double suma =0.0;
		Integer cont = 0;
		for (Partida p: partidas) {
			if (p.tipoVictoria().equals(vic)) {
				suma+= p.duracion().toSeconds() / p.getNumMovimientos();
				cont++;
			}
		if (cont>0) {
			res = suma/cont;
			}
		}
		return res;
	}

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
	@Override
	public Map<String, Double> getPorcentajesSiguienteMovimiento(String movimiento, Integer numeroMovimiento){
		Checkers.check("Partidas.getPorcentajesSiguienteMovimiento:numeroMovimientos debe ser mayor que 0", numeroMovimiento>0);

		Map<String, Long> m = contarSiguientesMovimientos(movimiento, numeroMovimiento);
		Long totalMovimientos = sumarLongs(m.values());
		Map<String, Double> res = new HashMap<String, Double>();
		for (Map.Entry<String, Long> entry: m.entrySet()) {
			res.put(entry.getKey(), 100.*entry.getValue()/totalMovimientos);
		}
		return  res;
	}

	/**
	 * FUNCIÓN TIPO 5
	 * Función auxiliar
	 * @param numeros Collección de números de tipo Long
	 * @return La suma de esos números
	 */
	private Long sumarLongs(Collection<Long> numeros) {
		Long res = 0L;
		for (Long n: numeros) {
			res+=n;
		}
		return res;
	}

	/**
	 * FUNCION TIPO 5- Función auxiliar 
	 * @param movimiento Movimiento concreto, por ejemplo, "Nc6" o "Bc4"
	 * @param numeroMovimiento Número movimiento
	 * @return Devuelve un Map en el que las claves son los movimientos siguientes al dado como parámetro,
	 *     y los valores el número de veces que se repite ese movimiento al continuación del dado
	 *     como parámetro.
	 */
	private Map<String, Long> contarSiguientesMovimientos(String movimiento, Integer numeroMovimiento) {

		Map<String, Long> res = new HashMap<String, Long>();
		for (Partida p: partidas) {
			if (p.getNumMovimientos() > numeroMovimiento 
				   && p.getMovimiento(numeroMovimiento).equals(movimiento)) {
				String clave = p.getMovimiento(numeroMovimiento+1);
				if (res.containsKey(clave)) {
					res.put(clave, res.get(clave)+1);
				}else {
					res.put(clave, 1L);
				}
			}
		}
		return res;
	}

	
	/**
	 * FUNCIÓN TIPO 5
	 * @param apertura Nombre de la apertura utilizada.
	 * @param resultado Resultado de la partida.
	 * @return Devuelve
	 * el porcentaje de partidas que incluyen la cadena de apertura 
	 * en su apertura y cuyo resultado es el dado como parámetro
	 */
	@Override
	public Double getPorcentajeVictoriasDeApertura(String apertura, Resultado resultado) {
		Long cuenta = 0L;
		Double res = 0.0;
		for (Partida p: partidas) {
			if (p.apertura().contains(apertura) && p.resultado().equals(resultado)) {
				cuenta++;
			}
		}
		if (getNumeroPartidas()>0) {
			res = 100.* cuenta/getNumeroPartidas();
		}
		return res;
	}
	
	/**
	 * FUNCIÓN TIPO 5
	 * @param anyo Año
	 * @param n Número entero
	 * @return Un conjunto ordenado con las n partidas más cortas jugadas en el año dado como 
	 * parámetro. El conjunto estará ordenado por el número de movimientos de la partida.
	 */
	@Override
	public SortedSet<Partida> getNPartidasMasCortas(Integer anyo, Integer n){
		Comparator<Partida> c = Comparator.comparing(Partida::getNumMovimientos)
										.thenComparing(Comparator.naturalOrder());
		
		List<Partida> filtradas = new ArrayList<Partida>();
		for (Partida p: partidas) {
			if (p.fecha().getYear() == anyo) {
				filtradas.add(p);
			}
		}
		Collections.sort(filtradas, Comparator.comparing(Partida::duracion));
		SortedSet<Partida> ss = new TreeSet<Partida>(c);
		ss.addAll(filtradas.subList(0,n));
		return ss;
	}
	
	/**
	 * FUNCIÓN TIPO ??
	 * @param anyo Año
	 * @param n Número entero
	 * @return Una lista con los ids de los n jugadores con más victorias en al año dado como parámetro.
	 */
	@Override
	public List<String> getNMejoresJugadores(Integer anyo, Integer n){ 
		
		Predicate<Partida> filtro = p->p.fecha().getYear() == anyo && p.getJugadorGanador()!= null;
		Map<String, Long> mc = getNumVictoriasPorJugador(filtro);
		Comparator<String> c = Comparator.comparing(jugador->mc.get(jugador));
		List<String> jugadores = new ArrayList<String>(mc.keySet());
		Collections.sort(jugadores, c);
		return new ArrayList<String>(jugadores.subList(0, n));		 
	}
		
	/**
	 * @param filtro Predicado para filtrar las partidas que se van a contar
	 * @return Un Map que asocia los ids de los usuarios con el número de victorias
	 * que tras filtrar con el predicado dado como parámetro
	 */
	private Map<String, Long> getNumVictoriasPorJugador(Predicate<Partida> filtro) {
		Map<String, Long> res = new HashMap<String,Long>();
		for (Partida p: partidas) {
			if (filtro.test(p)
					) {
				String clave = p.getJugadorGanador();
				if (res.containsKey(clave)) {
					res.put(clave, res.get(clave)+1);
				}else {
					res.put(clave, 1L);
				}
			}
		}
		return res;		
	}

	/**
	 * FUNCIÓN TIPO 2
	 * @param idJugador Identificador de un jugador
	 * @return El total de minutos jugados por el jugador dado como parámetro.
	 */
	@Override
	public Long getTiempoTotalJuego(String idJugador) {	
		Long total = 0L;
		for (Partida p: partidas) {
			if (p.jugadorBlancas().equals(idJugador) ||p.jugadorNegras().equals(idJugador)) {
				total+=p.duracion().toMinutes();
			}
		 }
		return total;
		}

	
	/**
	 * FUNCION TIPO ???
	 * @param anyo Un año
	 * @param resultado Un tipo de resultado
	 * @return El id del usuario que ha ganado más partidas en
	 * el año y con el año dados como parámetro. 
	 * @throws NoSuchElementException Si no se puede calcular el máximo.
	 */
	@Override
	public String getJugadorMasVictorias(Integer anyo, Resultado resultado) {

		Predicate<Partida> filtro = 
				p-> p.fecha().getYear() == anyo && p.resultado().equals(resultado);
		Map<String, Long> mc = getNumVictoriasPorJugador(filtro);
		
		Long m = null;
		String jugadorMaximo = null;
		for (String jugador: mc.keySet()) {
		Long cuenta = mc.get(jugador);
			if (m==null || m<cuenta ) {
				m = cuenta;
				jugadorMaximo = jugador;	
			}
		}
		
		if (jugadorMaximo == null) {
			throw new NoSuchElementException();
		}
		return jugadorMaximo;
	}
	
	/**
	 * FUNCIÓN TIPO 6
	 * @param n Número 
	 * @return true si hay algún jugador que tenga más de n victorias
	 */
	@Override
	public Boolean hayJugadorConMasNVictorias(Integer n) {
		Predicate<Partida> filtro = 
				p-> p.getJugadorGanador()!= null;
		Map<String, Long> mc = getNumVictoriasPorJugador(filtro);
		Boolean res = false;
		//Tratamiento existe
		for (Long num: mc.values()) {
			if (num>n) {
				res = true;
				break;
			}
		}
		
		return res;
	}
	
	
	/**
	 * FUNCIÓN TIPO 13
	 * @param n Número entero n
	 * @return Un map en el que las claves son los tipos de victoria y el valor
	 * es el enésimo jugador con más rating entre los jugadores que han tenido
	 * victorias de ese tipo. Es decir, si hacemos un ranking de los jugadores
	 * según su rating, nos quedaríamos con el que está en la posición n
	 */
	@Override
	public Map<TipoVictoria, String> getGanadorNPorTipoVictoria(Integer n){
		Map<TipoVictoria, List<Partida>> m = agruparPorTipoVictoria();
		Map<TipoVictoria, String> res = new HashMap<TipoVictoria, String>();
		for(Map.Entry<TipoVictoria, List<Partida>> e: m.entrySet()) {
			res.put(e.getKey(), seleccionarJugadorNSegunRating(e.getValue(), n));
		}
		return res;
	}	
	private Map<TipoVictoria, List<Partida>> agruparPorTipoVictoria(){
		Map<TipoVictoria, List<Partida>> res = new HashMap<TipoVictoria, List<Partida>>();
		for(Partida p:partidas) {
			if (p.getJugadorGanador()!=null) {
				TipoVictoria clave = p.tipoVictoria();
				if (res.containsKey(clave)) {
					res.get(clave).add(p);
				} else {
					List<Partida> partidas = new ArrayList<Partida>();
					partidas.add(p);
					res.put(clave, partidas);
				}
			}
		}
		return res;
	}
	private String seleccionarJugadorNSegunRating(List<Partida> lista, Integer n) {
		String res = null;
		if (lista.size()>=n) {
			Collections.sort(lista,Comparator.comparing(Partida::getRatingGanador) );
			res = lista.get(n).getJugadorGanador();
		}
		return res;
	}

	public String toString() {
		String partidasStr = partidas.stream().map(Object::toString).collect(Collectors.joining("\n"));
		return "Partidas [numPartidas =" + getNumeroPartidas() + " partidas=" + partidasStr + "]";
	}

}
