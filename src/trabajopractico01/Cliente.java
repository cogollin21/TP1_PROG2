package trabajopractico01;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cliente {
	
	private static final Fixture F = Fixture.INSTANCE;
	
	public static void main(String[] args) {
		
		SistemaDeTurnos SistemaDeTurnos = new SistemaDeTurnos("SistemaDeTurnos");
				
		SistemaDeTurnos.registrarVotante(
				F.dniFrodo, 
				"Frodo", 
				23, 
				!F.tieneEnfPrevia, 
				!F.trabaja
		);
		SistemaDeTurnos.registrarVotante(
				F.dniEowyn,
				"Eowyn",
				25, 
				F.tieneEnfPrevia, 
				!F.trabaja
		);
		SistemaDeTurnos.registrarVotante(
				F.dniBilbo,
				"Bilbo", 
				65, 
				F.tieneEnfPrevia, 
				!F.trabaja
		);
		SistemaDeTurnos.registrarVotante(
				F.dniGandalf, 
				"Gandalf", 
				70, 
				!F.tieneEnfPrevia, 
				F.trabaja
		);
		SistemaDeTurnos.registrarVotante(
				F.dniLegolas, 
				"Legolas", 
				80,
				!F.tieneEnfPrevia, 
				F.trabaja
		);
		SistemaDeTurnos.registrarVotante(
				F.dniGaladriel, 
				"Galadriel", 
				81, 
				!F.tieneEnfPrevia, 
				F.trabaja
		);
		SistemaDeTurnos.registrarVotante(
				F.dniArwen, 
				"Arwen", 
				50, 
				!F.tieneEnfPrevia, 
				F.trabaja
			);
		
		
		// frodo es el presidente
		// lo registra como votante y le asigna turno
		final Integer numMesaEnfPreexistente = SistemaDeTurnos.
				agregarMesa(F.enfPreexistente, F.dniFrodo); 
		
		final Integer numMesaMayor65 = SistemaDeTurnos.
				agregarMesa(F.mayor65, F.dniBilbo);
		
		final Integer numMesaGeneral = SistemaDeTurnos.
				agregarMesa(F.general, F.dniGaladriel);
		
		final Integer numMesaTrabajador = SistemaDeTurnos.
				agregarMesa(F.trabajador, F.dniGandalf);
		
		
		System.out.println("Numeros de mesa generados: " + 
				numMesaEnfPreexistente + " " + numMesaMayor65  + " " + numMesaGeneral  + " " + numMesaTrabajador);
	
		// hacer el toString de tupla!
		System.out.println("Turnos generados [Paso 1]: "); 
		System.out.println("\t- " + SistemaDeTurnos.consultarturno(F.dniFrodo));
		System.out.println("\t- " + SistemaDeTurnos.consultarturno(F.dniBilbo));
		System.out.println("\t- " + SistemaDeTurnos.consultarturno(F.dniGaladriel));
		System.out.println("\t- " + SistemaDeTurnos.consultarturno(F.dniGandalf));

		System.out.println("\n======================================================"); 
		System.out.println("Estado SistemaDeTurnos De Turnos: ");
		System.out.println("------------------------- ");
		System.out.println(SistemaDeTurnos.toString());
		System.out.println("======================================================\n"); 
		
		
		SistemaDeTurnos.registrarVotante(1, "Nombre1", 30, false, false);
		SistemaDeTurnos.registrarVotante(2, "Nombre2", 70, false, false);
		SistemaDeTurnos.registrarVotante(3, "Nombre3", 30, true, false);
		SistemaDeTurnos.registrarVotante(4, "Nombre4", 30, false, true);
			
		
		SistemaDeTurnos.asignarTurnos();
		

		// List<Tupla<TipoMesa, Cant Votantes Sin Turno>>
		List<Tupla<String, Integer>> votantesSinTurno = SistemaDeTurnos.sinTurnoSegunTipoMesa();

		System.out.println("Cant votantes sin turno :" + votantesSinTurno.size());
	
		Map<Integer,List<Integer>> MesaEnfPreexistente = SistemaDeTurnos.asignadosAMesa(numMesaEnfPreexistente);
		Map<Integer,List<Integer>> MesaMayor65 = SistemaDeTurnos.asignadosAMesa(numMesaMayor65);
		Map<Integer,List<Integer>> MesaGeneral = SistemaDeTurnos.asignadosAMesa(numMesaGeneral);
		Map<Integer,List<Integer>> MesaTrabajador = SistemaDeTurnos.asignadosAMesa(numMesaTrabajador);
		
		System.out.println("Cant Turnos generados [Paso 2]:"); 
		System.out.println("\t- " + MesaEnfPreexistente.size());
		System.out.println("\t- " + MesaMayor65.size());
		System.out.println("\t- " + MesaGeneral.size());
		System.out.println("\t- " + MesaTrabajador.size());

		//Franja -> List<Dni>
		Map<Integer, List<Integer>> franjaHoraria1 = SistemaDeTurnos.asignadosAMesa(numMesaEnfPreexistente);
		Map<Integer, List<Integer>> franjaHoraria2 = SistemaDeTurnos.asignadosAMesa(numMesaMayor65);
		Map<Integer, List<Integer>> franjaHoraria3 = SistemaDeTurnos.asignadosAMesa(numMesaGeneral);
		Map<Integer, List<Integer>> franjaHoraria4 = SistemaDeTurnos.asignadosAMesa(numMesaTrabajador);
		
		System.out.println("Cant Turnos generados [Paso 3]:");
		System.out.println("\t- " + franjaHoraria1.size());
		System.out.println("\t- " + franjaHoraria2.size());
		System.out.println("\t- " + franjaHoraria3.size());
		System.out.println("\t- " + franjaHoraria4.size());

		
		System.out.println("\n======================================================"); 
		System.out.println("Estado SistemaDeTurnos De Turnos: ");
		System.out.println("------------------------- ");
		System.out.println(SistemaDeTurnos.toString());
		System.out.println("======================================================\n"); 
		
		
	}

}
