package fp.ajedrez;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
public class PartidasImpl implements Partidas {

	private List<Partida> partidas;

	/**
	 * Crea un objeto de tipo partidas sin partidas.
	 */
	public PartidasImpl() {
		partidas = new ArrayList<Partida>();
	}

	/**
	 * @param partidas Colección de partidas. Crea un objeto de tipo Partidas a
	 *                 partir de una colección de partidas de ajedrez
	 */
	public PartidasImpl(Collection<Partida> partidas) {
		this.partidas = new ArrayList<Partida>(partidas);
	}

	/**
	 * @param partidas Stream de partidas Crea un objeto de tipo Partidas a partir
	 *                 de una colección de partidas de ajedrez.
	 */
	public PartidasImpl(Stream<Partida> partidas) {
		this.partidas = partidas.collect(Collectors.toList());
	}

	public Integer getNumeroPartidas() {
		return partidas.size();
	}

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
		PartidasImpl other = (PartidasImpl) obj;
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
		return partidas.stream().filter(p -> p.getTipoVictoria().equals(vic))
				.mapToDouble(p -> p.getDuracion().toSeconds() / p.getNumMovimientos())
				.average()
				.orElse(0);
	}

	/**
	 * FUNCIÓN TIPO ??
	 * @param movimiento Movimiento concreto, por ejemplo, "Nc6" o "Bc4"
	 * @param numeroMovimiento Número movimiento
	 * @return Devuelve un Map en el que las claves son movimientos siguientes al dado como parámetro (según el movimiento y 
	 *    la posición en la que se hace), y los valores el porcentaje de veces que se ha hecho ese movimiento. Por ejemplo,
	 *    si el movimiento es "Nc6" y el Número de movimiento es el 6, el Map contiene
	 *    como claves los movimientos hechos en séptimo lugar tras un movimiento "Nc6".
	 *    Los valores serán el porcentaje de veces que se han hecho esos movimientos.
	 *    
	 * @throws IllegalArgumentException si numeroMovimiento no es mayor o igual que uno.
	 */
	public Map<String, Double> getPorcentajesSiguienteMovimiento(String movimiento, Integer numeroMovimiento){
		Checkers.check("Partidas.getPorcentajesSiguienteMovimiento:numeroMovimientos debe ser mayor que 0", numeroMovimiento>0);

		Map<String, Long> m = contarSiguientesMovimientos(movimiento, numeroMovimiento);
		Long totalMovimientos = m.values().stream()
									.mapToLong(num->num)
									.sum();
		return  m.entrySet().stream()
				 .collect(Collectors.toMap(e->e.getKey(),
						 				   e->100.*e.getValue()/totalMovimientos));
	}

	/**
	 * Función auxiliar
	 * @param movimiento Movimiento concreto, por ejemplo, "Nc6" o "Bc4"
	 * @param numeroMovimiento Número movimiento
	 * @return Devuelve un Map en el que las claves son los movimientos siguientes al dado como parámetro,
	 *     y los valores el número de veces que se repite ese movimiento al continuación del dado
	 *     como parámetro.
	 */
	private Map<String, Long> contarSiguientesMovimientos(String movimiento, Integer numeroMovimiento) {
		Predicate<Partida> pred = p -> p.getNumMovimientos() > numeroMovimiento 
				   && p.getMovimiento(numeroMovimiento).equals(movimiento);
		return partidas.stream()
				.filter(pred)
				.collect(Collectors.groupingBy(p -> p.getMovimiento(numeroMovimiento+1), 
												Collectors.counting()));
	}

	
	/**
	 * FUNCIÓN TIPO ??
	 * @param apertura Nombre de la apertura utilizada.
	 * @param resultado Resultado de la partida.
	 * @return Devuelve
	 * el porcentaje de partidas que incluyen la cadena de apertura 
	 * en su apertura y cuyo resultado es el dado como parámetro. Si no se puede calcular, devuelve 0
	 */
	@Override
	public Double getPorcentajeVictoriasDeApertura(String apertura, Resultado resultado) {
		Double res = 0.0;
		Long s = partidas.stream()
					.filter(p->p.getApertura().contains(apertura) && p.getResultado().equals(resultado) )
					.count();
		if (getNumeroPartidas()>0) {
			res =100.* s/getNumeroPartidas();
		}
		return res;
	}
	
	/**
	 * FUNCIÓN TIPO 10
	 * @param anyo año
	 * @param n Número entero
	 * @return Un conjunto ordenado con las n partidas más cortas jugadas en el año dado como 
	 * parámetro. El conjunto estará ordenado por el Número de movimientos de la partida.
	 */
	@Override
	public SortedSet<Partida> getNPartidasMasCortas(Integer anyo, Integer n){
		Comparator<Partida> c = Comparator.comparing(Partida::getNumMovimientos)
										.thenComparing(Comparator.naturalOrder());
		return partidas.stream()
				.filter(p->p.getFecha().getYear() == anyo)
				.sorted(Comparator.comparing(Partida::getDuracion))
				.limit(n)
				.collect(Collectors.toCollection(()->new TreeSet<Partida>(c)));
	}
	
	/**
	 * FUNCIÓN TIPO ??
	 * @param anyo año
	 * @param n Número entero
	 * @return Una lista con los ids de los n jugadores con más victorias en al año dado como parámetro.
	 */
	@Override
	public List<String> getNMejoresJugadores(Integer anyo, Integer n){ 
		
		Comparator<Map.Entry<String, Long>> c = Map.Entry.comparingByValue();
		Predicate<Partida> filtro = p->p.getFecha().getYear() == anyo && p.getJugadorGanador()!= null;
		Map<String, Long> mc = getNumVictoriasPorJugador(filtro);
		return mc.entrySet().stream()
 				 .sorted(c.reversed())
 				 .limit(n)
 				 .map(Map.Entry::getKey)
 				 .collect(Collectors.toList());
 				 
	}
		
	/**
	 * @param filtro Predicado para filtrar las partidas que se van a contar
	 * @return Un Map que asocia los ids de los usuarios con el Número de victorias
	 * que tras filtrar con el predicado dado como parámetro
	 */
	private Map<String, Long> getNumVictoriasPorJugador(Predicate<Partida> filtro) {
		return partidas.stream()
					.filter(filtro)
					.collect(Collectors.groupingBy(Partida::getJugadorGanador,
								Collectors.counting()));
		
	}

	/**
	 * FUNCIÓN TIPO 7
	 * @param idJugador Identificador de un jugador
	 * @return El total de minutos jugados por el jugador dado como parámetro.
	 */
	public Long getTiempoTotalJuego(String idJugador) {
		Predicate<Partida> pred = p->p.getJugadorBlancas().id().equals(idJugador) ||
				   p.getJugadorNegras().id().equals(idJugador);
				
		return partidas.stream()
				.filter(pred)
				.mapToLong(p->p.getDuracion().toMinutes())
				.sum();
	}

	
	/**
	 * FUNCION TIPO 9
	 * @param anyo Un año
	 * @param resultado Un tipo de resultado
	 * @return El id del usuario que ha ganado más partidas en
	 * el año y con el año dados como parámetro. 
	 * @throws NoSuchElementException Si no se puede calcular el máximo.
	 */
	public String getJugadorMasVictorias(Integer anyo, Resultado resultado) {
		
		Comparator<Map.Entry<String, Long>> c = Map.Entry.comparingByValue();
		Predicate<Partida> filtro = 
				p-> p.getFecha().getYear() == anyo && p.getResultado().equals(resultado);
		Map<String, Long> mc = getNumVictoriasPorJugador(filtro);
		return mc.entrySet().stream()
 				 .max(c)
 				 .get()
 				 .getKey();
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
		return mc.values().stream()
				.anyMatch (num -> num >n);
	}
	
	
	/**
	 * FUNCIÓN TIPO 13
	 * @param n Número entero n
	 * @return Un map en el que las claves son los tipos de victoria y el valor
	 * es el enésimo jugador con más rating entre los jugadores que han tenido
	 * victorias de ese tipo. Es decir, si hacemos un ranking de los jugadores
	 * según su rating, nos quedaríamos con el que está en la posición n
	 */
	public Map<TipoVictoria, String> getGanadorNPorTipoVictoria(Integer n){
		return partidas.stream()
				.filter(p->p.getJugadorGanador()!= null)
				.collect(Collectors.groupingBy(Partida::getTipoVictoria,
						Collectors.collectingAndThen(Collectors.toList(), 
								lista->seleccionarJugadorNSegunRating(lista, n))));
	}
	private String seleccionarJugadorNSegunRating(List<Partida> lista, Integer n) {
		String res = null;
		if (lista.size()>=n) {
			List<Partida> l = lista.stream()
					.sorted(Comparator.comparing(Partida::getRatingGanador))
					.collect(Collectors.toList());
			res = l.get(n).getJugadorGanador();
		}
		return res;
	}

	public String toString() {
		String partidasStr = partidas.stream().map(Object::toString).collect(Collectors.joining("\n"));
		return "Partidas [numPartidas =" + getNumeroPartidas() + " partidas=" + partidasStr + "]";
	}

}
