package trabajopractico01;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Trabajador extends mesa {
	
	public Trabajador () {
		this.numerodemesa= mesa.cantmesas++;
		this.tipo="Trabajador";
		this.turnosdisponibles.put(8,new ArrayList <turno>());
		this.turnosdisponibles.put(9,new ArrayList <turno>());
		this.turnosdisponibles.put(10,new ArrayList <turno>());
		this.turnosdisponibles.put(11,new ArrayList <turno>());
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "presidente " +presidente+ "numero de mesa "+numerodemesa+" "+"mesa de tipo trabajadores";
	}

	@Override
	turno asignarturnomesa(persona p) {
		// TODO Auto-generated method stub
		int aleatorio = (int) (Math.random()*(11-8+1)+8);
		for (Entry<Integer,ArrayList<turno>> franjas : this.getTurnosdisponibles().entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
			if (franjas.getKey()==aleatorio) { // *EL CORTE NO VA* RECORRO EL ARREGLO (VALUE) DE CADA FRANJA (KEY) ABER SI TENGO CUPO
				turno nuevo = new turno (franjas.getKey(),this.getNumerodemesa(),p.getDni());// crea un turno nuevo
				franjas.getValue().add(nuevo); //AGREGO UN NUEVO TURNO
				p.setTiene_turno(true); //MODIFICO EL ESTADO DE TURNO DE LA PERSONA
				this.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
				return nuevo; //DEVUELVO LOS DATOS DEL TURNO
				
			}
			
		}
		return null;
		
	
		}
		

 

	
}

