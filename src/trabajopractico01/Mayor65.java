package trabajopractico01;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Mayor65 extends mesa {
	public Mayor65 () {
		this.numerodemesa= mesa.cantmesas++;
		this.tipo="Mayor65";
		this.turnosdisponibles.put(8,new ArrayList <turno>());
		this.turnosdisponibles.put(9,new ArrayList <turno>());
		this.turnosdisponibles.put(10,new ArrayList <turno>());
		this.turnosdisponibles.put(11,new ArrayList <turno>());
		this.turnosdisponibles.put(12,new ArrayList <turno>());
		this.turnosdisponibles.put(13,new ArrayList <turno>());
		this.turnosdisponibles.put(14,new ArrayList <turno>());
		this.turnosdisponibles.put(15,new ArrayList <turno>());
		this.turnosdisponibles.put(16,new ArrayList <turno>());
		this.turnosdisponibles.put(17,new ArrayList <turno>());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "presidente " +presidente+ "numero de mesa "+numerodemesa+" "+"mesa de tipo mayores";
	}



	@Override
	turno asignarturnomesa(persona p) {
		// TODO Auto-generated method stub
		int cupos= 10;
		for (Entry<Integer,ArrayList<turno>> franjas : this.getTurnosdisponibles().entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
			if (franjas.getValue().size()<cupos ) {
				turno nuevo = new turno (franjas.getKey(),this.getNumerodemesa(),p.getDni());// crea un turno nuevo
				franjas.getValue().add(nuevo); //AGREGO UN NUEVO TURNO
				p.setTiene_turno(true); //MODIFICO EL ESTADO DE TURNO DE LA PERSONA
				this.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
				return nuevo;
			}
		
	}
		
		return null;
	}
		
	

	

	
		
	}
	
	
	
	
	
	


