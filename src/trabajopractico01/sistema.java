package trabajopractico01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.util.List;


public class sistema {
   List <persona> RegistroDeVotantes;//agrego comentario de prueba "ama gay "
   List <mesa> RegistroDeMesas;
   List <turno> RegistroDeTurnos;
   Integer Cantdegentequevoto=0;
   String nombre;
   
   
  
public sistema (String f ) {
	this.nombre=f;
	this.RegistroDeMesas= new ArrayList <mesa>();
	this.RegistroDeVotantes= new ArrayList <persona>();
	this.RegistroDeTurnos=new ArrayList <turno>();
	
	
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
	int cupos=99999999;
	if (tipodemesa=="mesacomun") {
		cupos= 30;
	}
	if (tipodemesa=="mesaenfermedades") {
		cupos=20;
	}
	if(tipodemesa=="mesamayores") {
		cupos=10;
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
	if (tipodemesa!="Enf_Preex" && tipodemesa!="Mayor65" && tipodemesa!="General"&& tipodemesa!="Trabajador" ) {
		throw new RuntimeException("la mesa esta mal escrita");
	}
	for (mesa m : this.RegistroDeMesas) {
		if (m.getTipo()==tipodemesa) {
			contador=contador+m.getCantdevotantesenmesa();
		}
	}
	return contador;
	
}

public ArrayList <Tupla<String,Integer>> sinTurnoSegunTipoMesa (){ // devuelve un arregloe de tuplas y la tupla tiene tipo de mesa y gente q espera ser asignada//a esa mesa
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
					if (!p.tiene_turno) {
					comun++;
				}
			}}
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
	if (!this.RegistroDeVotantes.contains(devuelvepersonasegundni(dni))){
		throw new RuntimeException("la persona no se encuentra registrada");
	}else {
	for ( turno t : this.RegistroDeTurnos) {
		if (t.getDni()== dni) {
			return new Tupla <Integer,Integer> (t.getNum_demesa(),t.getFranja_horaria()); 
		}
		
		
	}
	return null;
	
}
}

public Map <Integer,List<Integer>> asignadosAMesa (Integer numdemesa){ //devuelve un map con clave = franja horaria y valor arreglo de las personas que votan en esa franja
	if (devuelvemesasegunnumero(numdemesa)==null) {
		throw new RuntimeException("la mesa no se encuentra creada");
	}
	Map <Integer,List<Integer>> reportedeturnos = new HashMap <Integer,List<Integer>> (); //creo el map que va a salir
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


public boolean registrarVotante (Integer dni ,String nombre , Integer edad ,boolean enfprevia ,boolean trabaja) {
	if (edad < 16) {
		throw new RuntimeException("debe ser mayor de 16 años");
	}
	persona gente = new persona (dni,nombre,edad,enfprevia,trabaja);
	
		if (this.RegistroDeVotantes.contains(gente)) {
			throw new RuntimeException("la persona ya se encuentra registrada");
	
	}
		else {
	this.RegistroDeVotantes.add(gente);
	return true;}
		
}

public Tupla <Integer,Integer> asignarTurno (Integer dni){
	if (!this.RegistroDeVotantes.contains(devuelvepersonasegundni(dni))){
		throw new RuntimeException("la persona no se encuentra registrada");
	}
	boolean corte=true;
	persona sujeto=devuelvepersonasegundni (dni);
//	if (!buscarpordni (dni)) {
//		return null;  //devuelve null porque la persona no esta 
//	}
	if (sujeto.tiene_turno) { // pregunta si la persona ya tiene un turno
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
	if (sujeto.tiene_enfermedad && !sujeto.es_trabajador) {
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
	if (sujeto.getEdad()>65 && !sujeto.es_trabajador) {
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

public int asignarTurnos () {
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
		throw new RuntimeException("la persona no se encuentra registrada");
	}
	sujeto=devuelvepersonasegundni(dni);
	if (sujeto.isYa_voto()) {
		return false; //debe enviar una excepcion
	}
	sujeto.setYa_voto(true);
	this.Cantdegentequevoto++;
	return true;
	
}
public mesa devuelvemesasegunnumero (Integer numdemesa) {		//me trae la mesa segun el numero
	mesa banca =null;
	for (mesa m : this.RegistroDeMesas) {
		if (m.getNumerodemesa()==numdemesa) {
			banca=m;
			return banca;
		}
	}
	return banca;
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

public int agregarMesa (String tipomesa , Integer dni) { //crea una mesa y devuelve el numero de mesa
	boolean existe= false;
	persona pers=null;
	if (tipomesa!="Enf_Preex" && tipomesa!="Mayor65" && tipomesa!="General"&&tipomesa!="Trabajador" ) {
		throw new RuntimeException("la mesa esta mal escrita");
	}
	if (!this.RegistroDeVotantes.contains(devuelvepersonasegundni(dni))){
		throw new RuntimeException("la persona no se encuentra registrada");
	}
	else {
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
	 turno nuevo = new turno (8,m.getNumerodemesa(),pers.getDni());
	 m.agregarturno(nuevo);
	 this.RegistroDeTurnos.add(nuevo);
	 return m.getNumerodemesa();
	}
	if(tipomesa == "Mayor65"&& existe) { 
	mesa m = new mesamayores ();
	m.agregarpresidente(pers);
	 this.RegistroDeMesas.add(m);
	 turno nuevo = new turno (8,m.getNumerodemesa(),pers.getDni());
	 //this.RegistroDeTurnos.add(nuevo);
	 m.agregarturno(nuevo);
	 this.RegistroDeTurnos.add(nuevo);
	 return m.getNumerodemesa();
	}
	if(tipomesa == "General"&& existe) { 
	 mesa m = new mesacomun ();
	 m.agregarpresidente(pers);
	 this.RegistroDeMesas.add(m);
	 turno nuevo = new turno (8,m.getNumerodemesa(),pers.getDni());
	// this.RegistroDeTurnos.add(nuevo);
	 m.agregarturno(nuevo);
	 this.RegistroDeTurnos.add(nuevo);
	 return m.getNumerodemesa();
	}
	else {
	//if(tipomesa == "Trabajador"&& existe) { 
		mesa m = new mesatrabajadores ();
		m.agregarpresidente(pers);
		 this.RegistroDeMesas.add(m);
		 turno nuevo = new turno (8,m.getNumerodemesa(),pers.getDni());
		// this.RegistroDeTurnos.add(nuevo);
		 m.agregarturno(nuevo);
		 this.RegistroDeTurnos.add(nuevo);
		 return m.getNumerodemesa();
	}








	}
}
}
