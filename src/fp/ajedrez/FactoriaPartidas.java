package fp.ajedrez;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import fp.utiles.Checkers;


/**
 * @author Toñi Reina
 *
 */
public class FactoriaPartidas {
	
	/**
	 * @param nomfich Nombre del fichero con datos de partidas.
	 * Devuelve un objeto de tipo Partidas con los datos del fichero.
	 */
	public static Partidas leerPartidas(String nomfich) {
		Partidas res=null;
		int i=0;
		try {
			List<Partida>  partidas=Files.lines(Paths.get(nomfich))
					.skip(1)
					.map(FactoriaPartidas::parsearPartida)
					.collect(Collectors.toList());
			
			res=new PartidasImpl(partidas);
			} catch(IOException e) {
			System.out.println("Fichero no encontrado: "+nomfich);
			e.printStackTrace();
		}
	return res;
	}
	
	private static Partida parsearPartida(String linea) {
		String[] trozos=linea.split(";");
		
		Boolean clasificatoria=parseaBooleano(trozos[0].trim());
		TipoVictoria estadoVictoria=TipoVictoria.valueOf(trozos[1].trim().toUpperCase());
		Resultado ganador=Resultado.valueOf(trozos[2].trim().toUpperCase()); 
		String jugadorBlancas=trozos[3].trim(); 
		Integer ratingBlancas=Integer.parseInt(trozos[4].trim()); 
		String jugadorNegras=trozos[5].trim();
		Integer ratingNegras=Integer.parseInt(trozos[6].trim());
		String movimientos=trozos[7].trim(); 
		String apertura=trozos[8].trim();
		LocalDate fecha=LocalDate.parse(trozos[9].trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Integer duracion=Integer.valueOf(trozos[10].trim());
		
		return new Partida(clasificatoria, estadoVictoria, ganador, jugadorBlancas, jugadorNegras,
				ratingBlancas, ratingNegras, movimientos, apertura, 
				fecha, duracion);
	}

	private static Boolean parseaBooleano(String cadena) {
		Boolean res = null;
		cadena = cadena.toUpperCase();
		if (cadena.equals("VERDADERO")) {
			res = true;
		}else {
			res = false;
		}
		return res;
	}
}
