package fp.ajedrez.test;

import fp.ajedrez.Resultado;
import fp.ajedrez.TipoVictoria;
import java.util.Map;
import java.util.SortedSet;

import fp.ajedrez.FactoriaPartidas;
import fp.ajedrez.Partida;
import fp.ajedrez.Partidas;

public class TestPartidas {

	public static void main(String[] args) {
		Partidas partidas = FactoriaPartidas.leerPartidas("data/chess.csv");

		System.out.println("\ntestGetPromedioDuracionesMedias");
		System.out.println("================================");
		testGetPromedioDuracionesMedias(partidas, TipoVictoria.MATE);
		testGetPromedioDuracionesMedias(partidas, TipoVictoria.OUTOFTIME);

		System.out.println("\ntestGetPorcentajesSiguienteMovimiento");
		System.out.println("=======================================");
		testGetPorcentajesSiguienteMovimiento (partidas, "Nf6", 2);
		testGetPorcentajesSiguienteMovimiento(partidas, "Bc4", 5);

		System.out.println("\ntestGetPorcentajeVictoriasDeApertura");
		System.out.println("======================================");
		testGetPorcentajeVictoriasDeApertura(partidas,"Queen's Gambit", Resultado.WHITE);
		testGetPorcentajeVictoriasDeApertura(partidas,"Sicilian Defense", Resultado.BLACK);

		System.out.println("\ntestGetNPartidasMasCortas");
		System.out.println("===========================");
		testGetNPartidasMasCortas(partidas, 2019, 5);
		testGetNPartidasMasCortas(partidas, 2017, 3);

		System.out.println("\ntestGetNMejoresJugadores");
		System.out.println("==========================");
		testGetNMejoresJugadores(partidas, 2020, 4);
		testGetNMejoresJugadores(partidas, 2017, 3);
	
		System.out.println("\ntestGetJugadorMasVictorias");
		System.out.println("============================");
		testGetJugadorMasVictorias(partidas, 2019, Resultado.WHITE);
		testGetJugadorMasVictorias(partidas, 2020, Resultado.BLACK);
			
		System.out.println("\ntestHayJugadorConMasNVictorias");
		System.out.println("===============================");		
		testHayJugadorConMasNVictorias(partidas, 20);
		testHayJugadorConMasNVictorias(partidas, 200);
		
		System.out.println("\ntestGetGanadorNPorTipoVictoria");
		System.out.println("================================");				
		testGetGanadorNPorTipoVictoria (partidas, 1);
		testGetGanadorNPorTipoVictoria (partidas, 4);
	}

	private static void testGetGanadorNPorTipoVictoria(Partidas partidas, Integer n) {
		try {
			String msg = String.format(
					"Los jugadores en la posición %d del ranking según el tipo de victoria son \n%s", 
					n, partidas.getGanadorNPorTipoVictoria(n));
			System.out.println(msg);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}		
	}

	private static void testHayJugadorConMasNVictorias(Partidas partidas, Integer n) {
		try {
			String msg = String.format(
					"¿Hay algún jugador con más de %d victorias? %s ", 
					n, partidas.hayJugadorConMasNVictorias(n));
			System.out.println(msg);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}
		
	}

	private static void testGetJugadorMasVictorias(Partidas partidas, Integer anyo, Resultado resultado) {
		try {
			String msg = String.format(
					"El jugador con más victorias en el año %d y con resultado %s es %s", 
					anyo, resultado,
					partidas.getJugadorMasVictorias(anyo, resultado));
			System.out.println(msg);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}
		
	}

	public static void testGetPromedioDuracionesMedias(Partidas partidas, TipoVictoria tipoVictoria) {

		try {
			String msg = String.format(
					"El promedio de las duraciones medias para las partidas con tipo victoria %s es %f", tipoVictoria,
					partidas.getPromedioDuracionesMedias(tipoVictoria));
			System.out.println(msg);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}

	}

	public static void testGetPorcentajesSiguienteMovimiento(Partidas partidas, String movimiento,
			Integer numMovimiento) {
		try {
			String msg = String.format("El porcejante de los siguientes movimientos a %s en la posición %d es", movimiento, numMovimiento);
			System.out.println(msg);
			Map<String, Double> m = partidas.getPorcentajesSiguienteMovimiento(movimiento, numMovimiento);
			System.out.println(m);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}
	}

	public static void testGetPorcentajeVictoriasDeApertura (Partidas partidas, String apertura, Resultado resultado) {
		try {
			String msg = String.format(
					"El porcentaje de las partidas con apertura %s y resultado %s es %f", apertura, resultado,
					partidas.getPorcentajeVictoriasDeApertura(apertura, resultado));
			System.out.println(msg);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}

	}
	
	public static void testGetNPartidasMasCortas(Partidas partidas, Integer anyo, Integer n) {
		try {
			String msg = String.format(
					"Las %d partidas más cortas del año %d son", n, anyo);
			System.out.println(msg);
			SortedSet<Partida> ss = partidas.getNPartidasMasCortas(anyo,n);
			ss.stream()
			  .forEach(System.out::println);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}
	
	}
	
	public static void testGetNMejoresJugadores(Partidas partidas, Integer anyo, Integer n) {
		try {
			String msg = String.format(
					"Los %d mejores jugadores del año %d son %s", 
					n, anyo, partidas.getNMejoresJugadores(anyo, n));
			System.out.println(msg);
		} catch (Exception e) {
			System.err.println("Capturada excepción inesperada: " + e.getMessage());
		}
	}
}
