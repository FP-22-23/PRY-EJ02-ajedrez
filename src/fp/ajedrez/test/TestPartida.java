package fp.ajedrez.test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import fp.ajedrez.Resultado;
import fp.ajedrez.Partida;
import fp.ajedrez.TipoVictoria;

public class TestPartida {

	
	
	public static void mostrarPartida(Partida p) {
		System.out.println(p);
		System.out.println("getMovimientoApertura: "+p.getMovimientoApertura());
		System.out.println("getNumMovimientos: "+p.getNumMovimientos());
		System.out.println("getJugadorGanador: "+p.getJugadorGanador());
		System.out.println("getRatingGanador: "+p.getRatingGanador());
		System.out.println("getDiferenciaRating: "+p.getDiferenciaRating());
	}
	
	public static void testConstructor1(Boolean clasificatoria, TipoVictoria estadoVictoria, Resultado ganador, String jugadorBlancas,
			String jugadorNegras, Integer ratingBlancas, Integer ratingNegras, List<String> movimientos, String apertura,
			LocalDate fecha, Duration duracion) {
		
		try {
			Partida p = new Partida(clasificatoria, estadoVictoria, ganador, jugadorBlancas,
					jugadorNegras, ratingBlancas,  ratingNegras, movimientos, apertura,
					 fecha, duracion);	
			mostrarPartida(p);
		} catch(IllegalArgumentException e) {
			System.out.println("Excepción capturada:\n   " + e);	
		} catch (Exception e) {
			System.out.println("Excepción inesperada:\n   " + e);
		}

	}

	public static void testConstructor2(Boolean clasificatoria, TipoVictoria estadoVictoria, Resultado ganador, String jugadorBlancas,
			String jugadorNegras, Integer ratingBlancas, Integer ratingNegras, String movimientos, String apertura,
			LocalDate fecha, Integer duracion) {
		try {
			Partida p = new Partida(clasificatoria, estadoVictoria, ganador, jugadorBlancas,
					jugadorNegras, ratingBlancas,  ratingNegras, movimientos, apertura,
					 fecha, duracion);	
			mostrarPartida(p);
			
		} catch(IllegalArgumentException e) {
			System.out.println("Excepción capturada:\n   " + e);	
		} catch (Exception e) {
			System.out.println("Excepción inesperada:\n   " + e);
		}
		
	}
	
	private static void testGetMovimiento(Partida p, Integer numMovimiento) {
		try {
			System.out.println("movimiento " + numMovimiento + " " + p.getMovimiento(numMovimiento));
			
		} catch(IllegalArgumentException e) {
			System.out.println("Excepción capturada:\n   " + e);	
		} catch (Exception e) {
			System.out.println("Excepción inesperada:\n   " + e);
		}
	}
	public static void main(String [] args) {
		List<String> movimientos = List.of("e4","c5","Nc3");
		LocalDate fecha=LocalDate.of(2018, 2, 13);
		Duration dur=Duration.ofMinutes(43);
		
		Integer i= 1;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1407,1500,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);
		
		//Caso de prueba con duración 0
		i++;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		dur=Duration.ZERO;
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1407,1500,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);
		
		//Caso de prueba con duración >60
		i++;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		dur=Duration.ofMinutes(90);
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1407,1500,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);

		
		//Caso de prueba para movimiento inicial
		i++;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		movimientos = List.of("c5","Nc3");
		dur=Duration.ofMinutes(50);
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1407,1500,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);


		//Caso de prueba para rating Blancas
		i++;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		movimientos = List.of("e4","c5","Nc3");
		dur=Duration.ofMinutes(50);
		Integer ratingBlancas = 0;
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",ratingBlancas,1500,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);


		//Caso de prueba para rating Blancas
		i++;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		movimientos = List.of("e4","c5","Nc3");
		dur=Duration.ofMinutes(50);
		ratingBlancas = -1;
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",ratingBlancas,1500,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);


		//Caso de prueba para rating Blancas
		i++;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		movimientos = List.of("e4","c5","Nc3");
		dur=Duration.ofMinutes(50);
		Integer ratingNegras = 0;
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1550,ratingNegras,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);


		//Caso de prueba para rating Negras
		i++;
		System.out.println("====================================");
		System.out.println("Constructor 1 - Caso de prueba " + i);
		movimientos = List.of("e4","c5","Nc3");
		dur=Duration.ofMinutes(50);
		ratingNegras = -1;
		testConstructor1(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1550,ratingNegras,movimientos,"Sicilian Defense: Closed Variation",fecha,dur);

		i= 1;
		System.out.println("====================================");
		String m = "e4 c5 Nc3";
		System.out.println("Constructor 2 - Caso de prueba " + i);
		testConstructor2(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1407,1500,m,"Sicilian Defense: Closed Variation",fecha,15);

		i= 1;
		System.out.println("====================================");
		System.out.println("getMovimiento - Caso de prueba " + i);
		m = "e4 c5 Nc3";
		Partida p = new Partida(true, TipoVictoria.RESIGN,Resultado.WHITE,"caramiecho72",
				   "gerfsh",1407,1500,m,"Sicilian Defense: Closed Variation",fecha,15);
		testGetMovimiento(p, 2);
		
		i++;
		System.out.println("====================================");
		System.out.println("getMovimiento - Caso de prueba " + i);
		testGetMovimiento(p, 0);

		i++;
		System.out.println("====================================");
		System.out.println("getMovimiento - Caso de prueba " + i);
		testGetMovimiento(p, 4);

	}

	
}
