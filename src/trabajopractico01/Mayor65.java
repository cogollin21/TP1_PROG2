package trabajopractico01;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Mayor65 extends Mesa {
	public Mayor65 () {
		this.numerodemesa= Mesa.cantmesas++;
		this.tipo="Mayor65";
		this.turnosdisponibles.put(8,new ArrayList <Turno>());
		this.turnosdisponibles.put(9,new ArrayList <Turno>());
		this.turnosdisponibles.put(10,new ArrayList <Turno>());
		this.turnosdisponibles.put(11,new ArrayList <Turno>());
		this.turnosdisponibles.put(12,new ArrayList <Turno>());
		this.turnosdisponibles.put(13,new ArrayList <Turno>());
		this.turnosdisponibles.put(14,new ArrayList <Turno>());
		this.turnosdisponibles.put(15,new ArrayList <Turno>());
		this.turnosdisponibles.put(16,new ArrayList <Turno>());
		this.turnosdisponibles.put(17,new ArrayList <Turno>());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "presidente " +presidente+ "numero de mesa "+numerodemesa+" "+"mesa de tipo mayores";
	}



	@Override
	Turno asignarturnomesa(Persona p) {
		// TODO Auto-generated method stub
		int cupos= 10;
		for (Entry<Integer,ArrayList<Turno>> franjas : this.getTurnosdisponibles().entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
			if (franjas.getValue().size()<cupos ) {
				Turno nuevo = new Turno (franjas.getKey(),this.getNumerodemesa(),p.getDni());// crea un Turno nuevo
				franjas.getValue().add(nuevo); //AGREGO UN NUEVO Turno
				p.setTiene_turno(true); //MODIFICO EL ESTADO DE Turno DE LA PERSONA
				this.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
				return nuevo;
			}
		
	}
		
		return null;
	}
		
	

	

	
		
	}
	
	
	
	
	
	


