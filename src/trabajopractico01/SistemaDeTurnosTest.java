package trabajopractico01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class SistemaDeTurnosTest {
	private SistemaDeTurnos SistemaDeTurnos;
	private static final Fixture F = Fixture.INSTANCE;
	
	@Before
	public void setUp() {
		SistemaDeTurnos = new SistemaDeTurnos("Sede UNGS");
				
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
		
		// # Votantes = 7
		// Mayores de 65 = 4
		// Trabajadores = 4
		// EnfPrexistente = 2
	}
	
	/*
	 * Al agregar una mesa se genera un ID de mesa.
	 * Se espera que al presidente a cargo se le asigne un Turno
	 */
	@Test
	public void asignacionMesas() {
		final Integer numMesaEnfPreexistente = SistemaDeTurnos.
				agregarMesa(F.enfPreexistente, F.dniFrodo);  // frodo es el presidente
		
		final Integer numMesaMayor65 = SistemaDeTurnos.
				agregarMesa(F.mayor65, F.dniBilbo);
		
		final Integer numMesaGeneral = SistemaDeTurnos.
				agregarMesa(F.general, F.dniGaladriel);
		
		final Integer numMesaTrabajador = SistemaDeTurnos.
				agregarMesa(F.trabajador, F.dniGandalf);
		
		assertNotNull(numMesaEnfPreexistente);
		assertNotNull(numMesaMayor65);
		assertNotNull(numMesaGeneral);
		assertNotNull(numMesaTrabajador);
		
		assertNotNull(SistemaDeTurnos.consultarturno(F.dniFrodo));
		assertNotNull(SistemaDeTurnos.consultarturno(F.dniBilbo));
		assertNotNull(SistemaDeTurnos.consultarturno(F.dniGaladriel));
		assertNotNull(SistemaDeTurnos.consultarturno(F.dniGandalf));
	}
	
	/*
	 * Al querer crear una mesa sin un votante 
	 * registrado deberia devolver una excepcion
	 */
	@Test
	public void asignacionMesasDniInvalido() {
		try {
			SistemaDeTurnos.agregarMesa(F.trabajador, F.dniSinRegistrar);
			//Si llego hasta aca esta mal! deberia haber fallado
			assertTrue(false);
		}catch(Exception e) {
			//No hay mesa para el Dni sin registrar
			assertNotNull(e);
		}
	}
	
	/*
	 * Al querer crear una mesa con un tipo de mesa invalido
	 * deberia devolver una excepcion
	 */
	@Test
	public void asignacionMesasTipomMesaInvalido() {
		try {
			SistemaDeTurnos.agregarMesa(F.tipoMesaInvalida, F.dniFrodo);
			//Si llego hasta aca esta mal! deberia haber fallado
			assertTrue(false);
		} catch(Exception e) {
			//La mesa es invalida
			assertNotNull(e);
		}
	}
	
	/*
	 * Se deberia asignar un turno para cada votante, 
	 * dado que no superan la capacidad total de las mesas
	 * No valida que los votantes se hayan asignado a su mesa correspondiente
	 */
	@Test
	public void asignacionTest() {
		final int votantesEsperados = 3;
		
		SistemaDeTurnos.agregarMesa(F.enfPreexistente, F.dniFrodo);
		
		SistemaDeTurnos.agregarMesa(F.mayor65, F.dniBilbo);
		
		SistemaDeTurnos.agregarMesa(F.general, F.dniGaladriel);
		
		SistemaDeTurnos.agregarMesa(F.trabajador, F.dniGandalf);
		
		assertEquals(votantesEsperados, SistemaDeTurnos.asignarTurnos());
	}
	
	/*
	 * Agrego una mesa solo para votantes trabajadores
	 * Se espera que solo se asignen los votantes trabajadores + presidente mesa: 
	 * En este caso no se valida el IREP de la franja horaria en si
	 * Ya que cada algoritmo puede asignar a los votantes en distintas franjas
	 */
	@Test
	public void asignacionPorTipoMesaTest() {
		final Integer numMesaTrabajadores = SistemaDeTurnos.agregarMesa(F.trabajador, F.dniBilbo);
				
		
		SistemaDeTurnos.asignarTurnos();
		
		//Franja -> List<Dni>
		final Map<Integer, List<Integer>> franjaHoraria = SistemaDeTurnos.asignadosAMesa(numMesaTrabajadores);

		final Set<Integer> votantes = extraerVotantes(franjaHoraria.values());
		
		final Set<Integer> votantesEsperados = 
				new HashSet<>(Arrays.asList(F.dniGandalf, F.dniLegolas, F.dniGaladriel,F.dniArwen,F.dniBilbo)) ;
		
		
		assertFalse(franjaHoraria.isEmpty());
		assertEquals(votantesEsperados, votantes);
		
	}
	
	/*
	 * No se deberian asignar turnos a trabajadores (Ya que no hay una mesa)
	 */
	@Test
	public void votantesSinTurnoTest() {
		SistemaDeTurnos.agregarMesa(F.general, F.dniFrodo);
		SistemaDeTurnos.agregarMesa(F.enfPreexistente, F.dniEowyn);
		SistemaDeTurnos.agregarMesa(F.mayor65, F.dniBilbo);
		
		SistemaDeTurnos.asignarTurnos();
		
		// List<Tupla<TipoMesa, Cant Votantes Sin Turno>>
		final List<Tupla<String, Integer>> votantesSinTurno = SistemaDeTurnos.sinTurnoSegunTipoMesa();
		
		final Integer cantVotantesSinTurno = extraerVotantesSinTurno(votantesSinTurno);
		
		final Integer expectedVotantesSinTurno = 4;
		
		assertEquals(expectedVotantesSinTurno, cantVotantesSinTurno);
	}
	
	/*
	 * Agrego una mesa general, como Frodo esta en el padron, 
	 * deberia asignarse un Turno
	 */
	@Test
	public void asignarTurnoTest() {
		SistemaDeTurnos.agregarMesa(F.general, F.dniGaladriel);
		
		// <NumeroMesa, FranjaHoraria>
		final Tupla<Integer, Integer> turno = SistemaDeTurnos.asignarTurno(F.dniFrodo);
		// <NumeroMesa, FranjaHoraria>
		final Tupla<Integer, Integer> turnoAsignado = SistemaDeTurnos.consultarturno(F.dniFrodo);
		
		assertNotNull(turno);
		assertNotNull(turnoAsignado);
	}
	
	/*
	 * Agrego una mesa general, como el dniSinVotante no esta en el padron, 
	 * no deberia asignarse un Turno y lanza una excepcion
	 */
	@Test
	public void asignarTurnoDniInvalidoTest() {
		SistemaDeTurnos.agregarMesa(F.general, F.dniGaladriel);
		try {
			SistemaDeTurnos.asignarTurno(F.dniSinRegistrar);
			//Si llego hasta aca esta mal! deberia haber fallado
			assertTrue(false);
		}catch(Exception e) {
			// No deberia asignarse el turno
			assertNotNull(e);
		}
	}
	
	/*
	 * No hay mesas, intento agregar un turno y devuelve null
	 */
	@Test
	public void asignarTurnoInvalidoTest() {
		assertNull(SistemaDeTurnos.asignarTurno(F.dniFrodo));
	}
	
	/*
	 * Agrego una mesa General y va a votar Frodo
	 * Luego, intenta votar otra vez y devuelve False
	 */
	@Test
	public void votarTest() {
		SistemaDeTurnos.agregarMesa(F.general, F.dniGaladriel);
		
		SistemaDeTurnos.asignarTurno(F.dniFrodo);
		//Pudo votar
		Boolean voto = SistemaDeTurnos.votar(F.dniFrodo);
		assertTrue(voto);
		//No puede votar al intentar votar nuevamente
		assertFalse(SistemaDeTurnos.votar(F.dniFrodo));
		
	}
	/*
	 * La asignacion de turnos por franja horaria debe ser valida
	 */
	@Test
	public void irepFranjaHorariaTest() {
		final List<Integer> dnis = generarNDnis(F.cantDnis);
				
		final Integer numMesa = SistemaDeTurnos.agregarMesa(F.enfPreexistente, F.dniFrodo);
		
		//Una mesa con enfPreexistente soporta 20 votantes por franja horaria
		//Tengo 10 franjas horarias: 8,9,10....17
		//Deberia admitir como maximo 20 * 10 votantes = F.cantDnis
		
		//Cargo en el SistemaDeTurnos  F.cantDnis votantes
		// Todos utilizando su dni como nombre, con 70 de edad y con enf preexistente
		
		for(Integer dni: dnis) {
			SistemaDeTurnos.registrarVotante(
					dni
					, String.valueOf(dni)
					, F.edad
					, F.tieneEnfPrevia
					, !F.trabaja);
		}
		
		SistemaDeTurnos.asignarTurnos();
		
		// FranjaHoraria -> List<Dni>
		final Map<Integer,List<Integer>> asignadosXFranjaHoraria = 
				SistemaDeTurnos.asignadosAMesa(numMesa);
		
		for(List<Integer> franjaHoraria: asignadosXFranjaHoraria.values()) {
			//Cada franja tiene que tener exactamente 20 votantes
			assertEquals(F.cupoXFranjaHorariaEnfPreexistente, 
					(Integer)franjaHoraria.size());
		}
		
	
	}
	
	private List<Integer> generarNDnis(Integer n) {
		List<Integer> dnis = new ArrayList<Integer>();
		for(int i=0; i < F.cantDnis; i++) {
			dnis.add(i);
		}
		return dnis;
	}
	
	private Set<Integer> extraerVotantes(Collection<List<Integer>> votantesXFranjaHoraria) {
		Set<Integer> votantes = new HashSet<>();
		for(List<Integer> listasDnis: votantesXFranjaHoraria) {
			votantes.addAll(listasDnis);
		}
		return votantes;
	}
	
	private Integer extraerVotantesSinTurno(List<Tupla<String, Integer>> votantesSinTurno) {	
		Integer cantVotantesSinTurno = 0;
		for(Tupla<String, Integer> mesaXVotantesSinTurno : votantesSinTurno ) {
			cantVotantesSinTurno = cantVotantesSinTurno + mesaXVotantesSinTurno.getY();
		}
		return cantVotantesSinTurno;
	}
	
}
