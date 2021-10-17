package trabajopractico01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class sistema {
   ArrayList <persona> RegistroDeVotantes;//agrego comentario de prueba "ama gay "
   ArrayList <mesa> RegistroDeMesas;
   ArrayList <persona> PersonasQueVotaron;
   ArrayList <persona> PersonasConTurno;
   ArrayList <turno> RegistroDeTurnos;
   
   
  
public sistema () {
	this.PersonasQueVotaron= new ArrayList <persona>();
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
	if (tieneturno (dni)) {
		//devolver tupla con turnos
	}
	if (sujeto.tiene_enfermedad) {
		if (mesatieneturnodispobible ("mesaenfermedades")){  // ME FIJO SI TENGO TURNOS EN ESE TIPO DE MESAS
			int cupos= 20;	//CUPO MAXIMO DE TURNOS
			for ( mesa m : this.RegistroDeMesas) {    //RECORRO TODAS LAS MESAS DE MI REGISTRO
			if (m.getTipo()=="mesaenfermedades") {   // SI TENGO UNA MESA DE ESE TIPO ENTRO
				for (Entry<Integer,ArrayList<turno>> franjas : m.turnosdisponibles.entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
					if (franjas.getValue().size()<cupos && corte) { // *EL CORTE NO VA* RECORRO EL ARREGLO (VALUE) DE CADA FRANJA (KEY) ABER SI TENGO CUPO
						franjas.getValue().add(new turno (franjas.getKey(),m.getNumerodemesa(),sujeto.getDni())); //AGREGO UN NUEVO TURNOS
						sujeto.setTiene_turno(true); //MODIFICO EL ESTADO DE TURNO DE LA PERSONA
						return new Tupla<Integer,Integer> (m.getNumerodemesa(),franjas.getKey()); //DEVUELVO LOS DATOS DEL TURNO
						
					}
					// FALTA HACER LO MISMO PARA TODOS LOS TIPOS DE MESA
				}
				
			
				}
					
				}
			
		}
	}
	
	
	
	
	
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
