package fp.ajedrez.test;

import fp.ajedrez.FactoriaPartidas;
import fp.ajedrez.Partidas;

public class TestFactoriaPartidas {
	public static void main(String[] args) {
		testLeerPartidas("data/chess.csv");
	}

	private static void testLeerPartidas(String fichero) {
		System.out.println("\nTestLeerPartidas =============");
		Partidas partidas = FactoriaPartidas.leerPartidas(fichero); //USO UN CSV MÃ?S CORTO
		System.out.println("   Partidas: "+ partidas);
	}

}
