package trabajopractico01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


import java.util.Iterator;
import java.util.List;


public class SistemaDeTurnos {
  private List <Persona> registroDeVotantes;
  private List <Mesa> registroDeMesas;
  private List <Turno> registroDeTurnos;
  private Integer cantdegentequevoto=0;
  private String nombre;
  private int creaMesa(String tipoMesa, Persona pers, boolean existe) {
		if (tipoMesa == "Enf_Preex" && existe) {
			Mesa m = new Enf_Preex();
			m.agregarpresidente(pers);
			this.registroDeMesas.add(m);
			Turno nuevo = new Turno(8, m.getNumerodemesa(), pers.getDni());
			m.agregarturno(nuevo);
			this.registroDeTurnos.add(nuevo);
			return m.getNumerodemesa();
		}
		if (tipoMesa == "Mayor65" && existe) {
			Mesa m = new Mayor65();
			m.agregarpresidente(pers);
			this.registroDeMesas.add(m);
			Turno nuevo = new Turno(8, m.getNumerodemesa(), pers.getDni());
			// this.registroDeTurnos.add(nuevo);
			m.agregarturno(nuevo);
			this.registroDeTurnos.add(nuevo);
			return m.getNumerodemesa();
		}
		if (tipoMesa == "General" && existe) {
			Mesa m = new General();
			m.agregarpresidente(pers);
			this.registroDeMesas.add(m);
			Turno nuevo = new Turno(8, m.getNumerodemesa(), pers.getDni());
			// this.registroDeTurnos.add(nuevo);
			m.agregarturno(nuevo);
			this.registroDeTurnos.add(nuevo);
			return m.getNumerodemesa();
		} else {
			// if(tipoMesa == "Trabajador"&& existe) {
			Mesa m = new Trabajador();
			m.agregarpresidente(pers);
			this.registroDeMesas.add(m);
			Turno nuevo = new Turno(8, m.getNumerodemesa(), pers.getDni());
			// this.registroDeTurnos.add(nuevo);
			m.agregarturno(nuevo);
			this.registroDeTurnos.add(nuevo);
			return m.getNumerodemesa();
		}
  }
   
   @Override
public String toString() {
	   StringBuilder sb = new StringBuilder();
	   sb.append("bienvenido al ");
	   sb.append(nombre);
	   sb.append(" llevan votando ");
	   sb.append(cantdegentequevoto);
	   sb.append(" personas");
	   
	return sb.toString()  ;
}

   
   
  
public SistemaDeTurnos (String  nombreSistema) {
	this.nombre= nombreSistema;
	this.registroDeMesas= new ArrayList <Mesa>();
	this.registroDeVotantes= new ArrayList <Persona>();
	this.registroDeTurnos=new ArrayList <Turno>();
	
	
}

public boolean ExistePersona ( Persona p ) {
	
	return this.registroDeVotantes.contains(p);

}

public boolean tieneturno (int dni) { //busca si una Persona tiene Turno por dni
	Iterator<Persona>  it = this.registroDeVotantes.iterator();
	while (it.hasNext()) {
		
	
		if (((Persona) it).getDni()==dni && ((Persona) it).isTiene_turno()) {
			return true;
		}
	}
	return false;
}

public boolean mesatieneturnodispobible (String tipodemesa){ //me dice si hay una mesa especifica con Turno libre
	int cupos=99999999;
	if (tipodemesa=="General") {
		cupos= 30;
	}
	if (tipodemesa=="Enf_Preex") {
		cupos=20;
	}
	if(tipodemesa=="Mayor65") {
		cupos=10;
	}
	
	for ( Mesa m : this.registroDeMesas) {
		if (m.getTipo()==tipodemesa) {
			
			for	(ArrayList <Turno> arreglo : m.turnosdisponibles.values()) {
				if (arreglo.size()<cupos) {
					return true;
				}			
			}
			}
				
			}
	return false;
		}
	
public int   votantesConTurno (String  tipoMesa) { //devuelve la cantidad de personas que votan en esa mesa
	int contador=0;
	if ( tipoMesa!="Enf_Preex" &&  tipoMesa!="Mayor65" &&  tipoMesa!="General"&&  tipoMesa!="Trabajador" ) {
		throw new RuntimeException("la mesa esta mal escrita");
	}
	for (Mesa m : this.registroDeMesas) {
		if (m.getTipo()== tipoMesa) {
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
	for ( Persona p : this.registroDeVotantes) {				
		if (p.isEs_trabajador()&& !p.isTiene_turno()) {
			trabajadores++;
		}
		else {
			if (p.isTiene_enfermedad()&& !p.isTiene_turno()) {
				enfermedades++;
			}
			else {
				if ( p.getEdad()>65 && !p.isTiene_turno()) {
					mayores++;
				}
				else {
					if (!p.isTiene_turno()) {
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



public Tupla <Integer,Integer> consultaTurno (Integer dni){ //devuelve el Turno que tiene asignado un dni
	if (!this.registroDeVotantes.contains(devuelvepersonasegundni(dni))){
		throw new RuntimeException("la Persona no se encuentra registrada");
	}else {
	for ( Turno t : this.registroDeTurnos) {
		if (t.getDni()== dni) {
			return new Tupla <Integer,Integer> (t.getNum_demesa(),t.getFranja_horaria()); 
		}
		
		
	}
	return null;

	
	
	
		
}
		
}

public Map <Integer,List<Integer>> asignadosAMesa (Integer numdeMesa){ //devuelve un map con clave = franja horaria y valor arreglo de las personas que votan en esa franja
	if (devuelvemesasegunnumero(numdeMesa)==null) {
		throw new RuntimeException("la mesa no se encuentra creada");
	}
	Map <Integer,List<Integer>> reportedeturnos = new HashMap <Integer,List<Integer>> (); //creo el map que va a salir
	for ( Mesa m : this.registroDeMesas) { //recorro todas las mesas
		 if (m.getNumerodemesa()==numdeMesa) { //ubico la mesa correspondiente a ese numero
			 for (Entry<Integer,ArrayList<Turno>> franjas : m.turnosdisponibles.entrySet()) {  //recorro la franjas horarias de esa mesa
				 ArrayList <Turno> auxiliar = new ArrayList <Turno>(); //creo una variable auxiliar que voy a recorrer
				 ArrayList <Integer> convertida = new ArrayList <Integer>(); // esta variable va a recibir los dni de Turno de <Turno> a integer
				 auxiliar=franjas.getValue();
				 for (Turno t : auxiliar ) {		//con este for recorro el array de Turno y lo paso a integer
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
	for (Persona p : this.registroDeVotantes) {
		if (p.getDni()==dni) {
			return true;
		}
	}
	return false;
}


public boolean registrarVotante (Integer dni ,String nombre , Integer edad ,boolean enfPrevia ,boolean trabaja) {
	if (edad < 16) {
		throw new RuntimeException("debe ser mayor de 16 años");
	}
	Persona gente = new Persona (dni,nombre,edad,enfPrevia,trabaja);
	
		if (this.registroDeVotantes.contains(gente)) {
			throw new RuntimeException("la Persona ya se encuentra registrada");
	
	}
		else {
	
	return this.registroDeVotantes.add(gente);}
		
}

public Tupla <Integer,Integer> asignarTurno (Integer dni){
	Tupla<Integer,Integer> retorno =new Tupla<Integer,Integer>(0,0);
	Turno turnodevuelto;
	boolean corte=true;
	Persona sujeto=devuelvepersonasegundni (dni);
	
	if (!this.registroDeVotantes.contains(devuelvepersonasegundni(dni))){
		throw new RuntimeException("la Persona no se encuentra registrada");
	}
	
//	if (!buscarpordni (dni)) {
//		return null;  //devuelve null porque la Persona no esta 
//	}
	if (sujeto.isTiene_turno()) { // pregunta si la Persona ya tiene un Turno
		for ( Turno t : this.registroDeTurnos) {
			if ( t.getDni()==dni) {
				return new Tupla <Integer,Integer> (t.getNum_demesa(),t.getFranja_horaria()); //devuelvo el Turno de la Persona
			}
		}
	}
	
	
	
	if (sujeto.isEs_trabajador()) { 
			for ( Mesa m : this.registroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="Trabajador") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				turnodevuelto=m.asignarturnomesa(sujeto);
						this.registroDeTurnos.add(turnodevuelto); //agrego el Turno al registro de turnos
						retorno.setX(turnodevuelto.getNum_demesa());
						retorno.setY(turnodevuelto.getFranja_horaria());
						return retorno; //DEVUELVO LOS DATOS DEL Turno
						
					}
					
				}
				
			
				}
					
				
			
		
				//revisar esto
	else if (sujeto.isTiene_enfermedad()) {  
		if (mesatieneturnodispobible ("Enf_Preex")){  // ME FIJO SI TENGO TURNOS EN ESE TIPO DE MESAS
			for ( Mesa m : this.registroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="Enf_Preex") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				turnodevuelto=m.asignarturnomesa(sujeto);
				this.registroDeTurnos.add(turnodevuelto); //agrego el Turno al registro de turnos
				retorno.setX(turnodevuelto.getNum_demesa());
				retorno.setY(turnodevuelto.getFranja_horaria());
				return retorno; //DEVUELVO LOS DATOS DEL Turno
					}
					
				}
				
			
				}
					
				}
			
		
		//revisar esto
	
	else  if (sujeto.getEdad()>65 ) {
		if (mesatieneturnodispobible ("Mayor65")){  // ME FIJO SI TENGO TURNOS EN ESE TIPO DE MESAS
				//CUPO MAXIMO DE TURNOS
			for ( Mesa m : this.registroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="Mayor65") { 
				turnodevuelto=m.asignarturnomesa(sujeto);
				this.registroDeTurnos.add(turnodevuelto); //agrego el Turno al registro de turnos
				retorno.setX(turnodevuelto.getNum_demesa());
				retorno.setY(turnodevuelto.getFranja_horaria());
				return retorno;// SI TENGO UNA MESA DE ESE TIPO ENTRO
				
				}
				
			
				}
					
				}
			
		}
	
	else {
		if (mesatieneturnodispobible ("General")){  // ME FIJO SI TENGO TURNOS EN ESE TIPO DE MESAS
			
			for ( Mesa m : this.registroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="General") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				turnodevuelto=m.asignarturnomesa(sujeto);
				this.registroDeTurnos.add(turnodevuelto); //agrego el Turno al registro de turnos
				retorno.setX(turnodevuelto.getNum_demesa());
				retorno.setY(turnodevuelto.getFranja_horaria());
				return retorno;//
				
					}
					
				}
				
			
				}
					
				}
			
		
	
	return null;
}

public int asignarTurnos () {
	int turnosasignados=0;
	for (Persona p : this.registroDeVotantes) {
		if (!p.isTiene_turno()) {
			Tupla<Integer, Integer> asignacion =asignarTurno (p.getDni()); 
			turnosasignados++;
		}
	}
	return turnosasignados;

}

public boolean votar (Integer dni) {
	
	Persona sujeto=null;
	if (!buscarpordni(dni)) { //se fija si la Persona esta en el registro
		throw new RuntimeException("la Persona no se encuentra registrada");
	}
	sujeto=devuelvepersonasegundni(dni);
	if (sujeto.isYa_voto()) {
		return false; //debe enviar una excepcion
	}
	sujeto.setYa_voto(true);
	this.cantdegentequevoto++;
	return true;
	
}
public Mesa devuelvemesasegunnumero (Integer numdemesa) {		//me trae la mesa segun el numero
	Mesa banca =null;
	for (Mesa m : this.registroDeMesas) {
		if (m.getNumerodemesa()==numdemesa) {
			banca=m;
			return banca;
		}
	}
	return banca;
}

public Persona devuelvepersonasegundni (Integer dni) {		//me trae a la Persona segun el dni
	Persona sujeto =null;
	for (Persona p : this.registroDeVotantes) {
		if (p.getDni()==dni) {
			sujeto=p;
			return sujeto;
		}
	}
	return sujeto;
}

public int agregarMesa (String tipoMesa , Integer dni) { //crea una mesa y devuelve el numero de mesa
	boolean existe= false;
	Persona pers=null;
	if (tipoMesa!="Enf_Preex" && tipoMesa!="Mayor65" && tipoMesa!="General"&&tipoMesa!="Trabajador" ) {
		throw new RuntimeException("la mesa esta mal escrita");
	}
	if (!this.registroDeVotantes.contains(devuelvepersonasegundni(dni))){
		throw new RuntimeException("la Persona no se encuentra registrada");
	}
	else {
	for (Persona p : this.registroDeVotantes) {
		if (p.getDni()==dni) {
			if (p.puedeSerPresidente()) {
				existe=true;
				pers=p;
				p.setEs_presidente(true);
				p.setTiene_turno(true);
							
			}
		}
		
		
	}
	//for (Persona p : this.registroDeVotantes)
	
	return this.creaMesa(tipoMesa, pers, existe);
	
	}
}
}
