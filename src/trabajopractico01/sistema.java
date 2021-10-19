package trabajopractico01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.tools.javac.util.List;

public class sistema {
   ArrayList <persona> RegistroDeVotantes;//agrego comentario de prueba "ama gay "
   ArrayList <mesa> RegistroDeMesas;
   ArrayList <persona> PersonasConTurno;
   ArrayList <turno> RegistroDeTurnos;
   Integer Cantdegentequevoto=0;
   
   
  
public sistema () {
	
	this.RegistroDeMesas= new ArrayList <mesa>();
	this.RegistroDeVotantes= new ArrayList <persona>();
	
}

public boolean ExistePersona ( persona p ) {
	
	return this.RegistroDeVotantes.contains(p);

}

public boolean tieneturno (int dni) { //busca si una persona tiene turno por dni
	for (persona p : this.RegistroDeVotantes) {
		if (p.getDni()==dni && p.isTiene_turno()) {
			return true;
		}
	}
	return false;
}

public boolean mesatieneturnodispobible (String tipodemesa){ //me dice si hay una mesa especifica con turno libre
	int cupos;
	if (tipodemesa=="mesacomun") {
		cupos= 30;
	}
	if (tipodemesa=="mesaenfermedades") {
		cupos=20;
	}
	if(tipodemesa=="mesamayores") {
		cupos=10;
	}
	else {
		return false;
	}
	
	for ( mesa m : this.RegistroDeMesas) {
		if (m.getTipo()==tipodemesa) {
			
			for	(ArrayList <turno> arreglo : m.turnosdisponibles.values()) {
				if (arreglo.size()<cupos) {
					return true;
				}			
			}
			}
				
			}
	return false;
		}
	
public int  cantidaddevotantesenmesa (String tipodemesa) { //devuelve la cantidad de personas que votan en esa mesa
	int contador=0;
	for (mesa m : this.RegistroDeMesas) {
		if (m.getTipo()==tipodemesa) {
			contador=contador+m.getCantdevotantesenmesa();
		}
	}
	return contador;
	
}

public ArrayList <Tupla<String,Integer>> sinturnosegunmesa (){ // devuelve un arregloe de tuplas y la tupla tiene tipo de mesa y gente q espera ser asignada//a esa mesa
	ArrayList<Tupla<String,Integer>> devolver= new ArrayList <Tupla<String,Integer>> ();
	Tupla<String, Integer> turnoscomun = new Tupla<String,Integer> ("comun", 0);
	Tupla<String, Integer> turnosenfermedades = new Tupla<String,Integer> ("enfermedades", 0);
	Tupla<String, Integer> turnosmayores = new Tupla<String,Integer> ("mayores", 0);
	Tupla<String, Integer> turnostrabajadores = new Tupla<String,Integer> ("trabajadores", 0);
	int comun=0;
	int enfermedades=0;
	int mayores=0;
	int trabajadores=0;
	for ( persona p : this.RegistroDeVotantes) {				
		if (p.isEs_trabajador()&& !p.tiene_turno) {
			trabajadores++;
		}
		else {
			if (p.isTiene_enfermedad()&& !p.tiene_turno) {
				enfermedades++;
			}
			else {
				if ( p.getEdad()>65 && !p.tiene_turno) {
					mayores++;
				}
				else {
					comun++;
				}
			}
		}
	}
	turnoscomun.setY(comun);
	turnosenfermedades.setY(enfermedades);
	turnosmayores.setY(mayores);
	turnostrabajadores.setY(trabajadores);
	devolver.add(turnoscomun);
	devolver.add(turnosenfermedades);
	devolver.add(turnosmayores);
	devolver.add(turnostrabajadores);
	return devolver;
}



public Tupla <Integer,Integer> consultarturno (Integer dni){ //devuelve el turno que tiene asignado un dni
	for ( turno t : this.RegistroDeTurnos) {
		if (t.getDni()== dni) {
			return new Tupla <Integer,Integer> (t.getNum_demesa(),t.getFranja_horaria()); 
		}
	}
	return null;
}

public Map <Integer,ArrayList<Integer>> asignadosamesa (Integer numdemesa){ //devuelve un map con clave = franja horaria y valor arreglo de las personas que votan en esa franja
	Map <Integer,ArrayList<Integer>> reportedeturnos = new HashMap <Integer,ArrayList<Integer>> (); //creo el map que va a salir
	for ( mesa m : this.RegistroDeMesas) { //recorro todas las mesas
		 if (m.getNumerodemesa()==numdemesa) { //ubico la mesa correspondiente a ese numero
			 for (Entry<Integer,ArrayList<turno>> franjas : m.turnosdisponibles.entrySet()) {  //recorro la franjas horarias de esa mesa
				 ArrayList <turno> auxiliar = new ArrayList <turno>(); //creo una variable auxiliar que voy a recorrer
				 ArrayList <Integer> convertida = new ArrayList <Integer>(); // esta variable va a recibir los dni de turno de <turno> a integer
				 auxiliar=franjas.getValue();
				 for (turno t : auxiliar ) {		//con este for recorro el array de turno y lo paso a integer
					 convertida.add(t.getDni());
				 }
				 reportedeturnos.put(franjas.getKey(), convertida);
				 
			 }
		 }
	 }
	if (reportedeturnos.isEmpty()) {
		return null;
	}
	return reportedeturnos;
}
	


public boolean buscarpordni (int dni) {  //devuelve true si esta
	for (persona p : this.RegistroDeVotantes) {
		if (p.getDni()==dni) {
			return true;
		}
	}
	return false;
}


public boolean RegistrarVotantes (Integer dni ,String nombre , Integer edad ,boolean enfprevia ,boolean trabaja) {
	if (edad < 16) {
		return false;
	}
	persona gente = new persona (dni,nombre,edad,enfprevia,trabaja);
	
		if (this.RegistroDeVotantes.contains(gente)) {
			return false;
	
	}
	this.RegistroDeVotantes.add(gente);
	return true;	
}

public Tupla <Integer,Integer> asignarTurno (Integer dni){
	boolean corte=true;
	persona sujeto=devuelvepersonasegundni (dni);
	if (!buscarpordni (dni)) {
		return null;  //devuelve null porque la persona no esta 
	}
	if (tieneturno (dni)) { // pregunta si la persona ya tiene un turno
		for ( turno t : this.RegistroDeTurnos) {
			if ( t.getDni()==dni) {
				return new Tupla <Integer,Integer> (t.getNum_demesa(),t.getFranja_horaria()); //devuelvo el turno de la persona
			}
		}
	}
	if (sujeto.es_trabajador) { 
		int aleatorio = (int) (Math.random()*(11-8+1)+8);// genera numero aleatorio entre 11 y 8
			for ( mesa m : this.RegistroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="mesatrabajadores") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				for (Entry<Integer,ArrayList<turno>> franjas : m.turnosdisponibles.entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
					if (franjas.getKey()==aleatorio) { // *EL CORTE NO VA* RECORRO EL ARREGLO (VALUE) DE CADA FRANJA (KEY) ABER SI TENGO CUPO
						turno nuevo = new turno (franjas.getKey(),m.getNumerodemesa(),sujeto.getDni());// crea un turno nuevo
						franjas.getValue().add(nuevo); //AGREGO UN NUEVO TURNO
						sujeto.setTiene_turno(true); //MODIFICO EL ESTADO DE TURNO DE LA PERSONA
						this.RegistroDeTurnos.add(nuevo); //agrego el turno al registro de turnos
						m.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
						return new Tupla<Integer,Integer> (m.getNumerodemesa(),franjas.getKey()); //DEVUELVO LOS DATOS DEL TURNO
						
					}
					
				}
				
			
				}
					
				}
			
		
	}
	if (sujeto.tiene_enfermedad) {
		if (mesatieneturnodispobible ("mesaenfermedades")){  // ME FIJO SI TENGO TURNOS EN ESE TIPO DE MESAS
			int cupos= 20;	//CUPO MAXIMO DE TURNOS
			for ( mesa m : this.RegistroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="mesaenfermedades") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				for (Entry<Integer,ArrayList<turno>> franjas : m.turnosdisponibles.entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
					if (franjas.getValue().size()<cupos && corte) { // *EL CORTE NO VA* RECORRO EL ARREGLO (VALUE) DE CADA FRANJA (KEY) ABER SI TENGO CUPO
						turno nuevo = new turno (franjas.getKey(),m.getNumerodemesa(),sujeto.getDni());// crea un turno nuevo
						franjas.getValue().add(nuevo); //AGREGO UN NUEVO TURNOS
						sujeto.setTiene_turno(true); //MODIFICO EL ESTADO DE TURNO DE LA PERSONA
						this.RegistroDeTurnos.add(nuevo); //agrego el turno al registro de turnos
						m.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
						return new Tupla<Integer,Integer> (m.getNumerodemesa(),franjas.getKey()); //DEVUELVO LOS DATOS DEL TURNO
						
					}
					
				}
				
			
				}
					
				}
			
		}
	}
	if (sujeto.getEdad()>65) {
		if (mesatieneturnodispobible ("mesamayores")){  // ME FIJO SI TENGO TURNOS EN ESE TIPO DE MESAS
			int cupos= 10;	//CUPO MAXIMO DE TURNOS
			for ( mesa m : this.RegistroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="mesamayores") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				for (Entry<Integer,ArrayList<turno>> franjas : m.turnosdisponibles.entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
					if (franjas.getValue().size()<cupos && corte) { // *EL CORTE NO VA* RECORRO EL ARREGLO (VALUE) DE CADA FRANJA (KEY) ABER SI TENGO CUPO
						turno nuevo = new turno (franjas.getKey(),m.getNumerodemesa(),sujeto.getDni());// crea un turno nuevo
						franjas.getValue().add(nuevo); //AGREGO UN NUEVO TURNOS
						sujeto.setTiene_turno(true); //MODIFICO EL ESTADO DE TURNO DE LA PERSONA
						this.RegistroDeTurnos.add(nuevo); //agrego el turno al registro de turnos
						m.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
						return new Tupla<Integer,Integer> (m.getNumerodemesa(),franjas.getKey()); //DEVUELVO LOS DATOS DEL TURNO
						
					}
					
				}
				
			
				}
					
				}
			
		}
	}
	if (!sujeto.isEs_trabajador() && !sujeto.isTiene_enfermedad() && sujeto.getEdad()<=65) {
		if (mesatieneturnodispobible ("mesacomun")){  // ME FIJO SI TENGO TURNOS EN ESE TIPO DE MESAS
			int cupos= 30;	//CUPO MAXIMO DE TURNOS
			for ( mesa m : this.RegistroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="mesamayores") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				for (Entry<Integer,ArrayList<turno>> franjas : m.turnosdisponibles.entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
					if (franjas.getValue().size()<cupos && corte) { // *EL CORTE NO VA* RECORRO EL ARREGLO (VALUE) DE CADA FRANJA (KEY) ABER SI TENGO CUPO
						turno nuevo = new turno (franjas.getKey(),m.getNumerodemesa(),sujeto.getDni());// crea un turno nuevo
						franjas.getValue().add(nuevo); //AGREGO UN NUEVO TURNOS
						sujeto.setTiene_turno(true); //MODIFICO EL ESTADO DE TURNO DE LA PERSONA
						this.RegistroDeTurnos.add(nuevo); //agrego el turno al registro de turnos
						m.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
						return new Tupla<Integer,Integer> (m.getNumerodemesa(),franjas.getKey()); //DEVUELVO LOS DATOS DEL TURNO
						
					}
					
				}
				
			
				}
					
				}
			
		}
	}
	return null;
}

public int asignarturnosautomaticamente () {
	int turnosasignados=0;
	for (persona p : this.RegistroDeVotantes) {
		if (!p.isTiene_turno()) {
			Tupla<Integer, Integer> asignacion =asignarTurno (p.getDni()); 
			turnosasignados++;
		}
	}
	return turnosasignados;

}

public boolean votar (Integer dni) {
	persona sujeto=null;
	if (!buscarpordni(dni)) { //se fija si la persona esta en el registro
	return false;   //debe enviar una excepcion 	
	}
	sujeto=devuelvepersonasegundni(dni);
	if (sujeto.isYa_voto()) {
		return false; //debe enviar una excepcion
	}
	sujeto.setYa_voto(true);
	this.Cantdegentequevoto++;
	return true;
	
}

public persona devuelvepersonasegundni (Integer dni) {		//me trae a la persona segun el dni
	persona sujeto =null;
	for (persona p : this.RegistroDeVotantes) {
		if (p.getDni()==dni) {
			sujeto=p;
			return sujeto;
		}
	}
	return sujeto;
}

public int RegistrarMesa (String tipomesa , Integer dni) {
	boolean existe= false;
	persona pers=null;
	
	for (persona p : this.RegistroDeVotantes) {
		if (p.getDni()==dni && !p.isEs_presidente()&& !p.isTiene_turno()) {
			existe=true;
			pers=p;
			p.setEs_presidente(true);
			p.setTiene_turno(true);
			
		}
	}
	
	
	//for (persona p : this.RegistroDeVotantes)
	
	if(tipomesa == "Enf_Preex" && existe) { 
	 mesa m = new mesaenfermedades ();
	 m.agregarpresidente(pers);
	 this.RegistroDeMesas.add(m);
	 return m.getNumerodemesa();
	}
	if(tipomesa == "Mayor65"&& existe) { 
	mesa m = new mesamayores ();
	m.agregarpresidente(pers);
	 this.RegistroDeMesas.add(m);
	 return m.getNumerodemesa();
	}
	if(tipomesa == "General”"&& existe) { 
	 mesa m = new mesacomun ();
	 m.agregarpresidente(pers);
	 this.RegistroDeMesas.add(m);
	 return m.getNumerodemesa();
	}
	if(tipomesa == "Trabajador"&& existe) { 
		mesa m = new mesatrabajadores ();
		m.agregarpresidente(pers);
		 this.RegistroDeMesas.add(m);
		 return m.getNumerodemesa();
	}
	return -1;
	
	







}
}
