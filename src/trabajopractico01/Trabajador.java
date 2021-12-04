package trabajopractico01;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Trabajador extends Mesa {
	
	public Trabajador () {
		this.numerodemesa= Mesa.cantmesas++;
		this.tipo="Trabajador";
		this.turnosdisponibles.put(8,new ArrayList <Turno>());
		this.turnosdisponibles.put(9,new ArrayList <Turno>());
		this.turnosdisponibles.put(10,new ArrayList <Turno>());
		this.turnosdisponibles.put(11,new ArrayList <Turno>());
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "presidente " +presidente+ "numero de mesa "+numerodemesa+" "+"mesa de tipo trabajadores";
	}

	@Override
	Turno asignarturnomesa(Persona p) {
		// TODO Auto-generated method stub
		int aleatorio = (int) (Math.random()*(11-8+1)+8);
		for (Entry<Integer,ArrayList<Turno>> franjas : this.getTurnosdisponibles().entrySet()) { //RECORRO LOS TURNOS DE TODAS LAS FRANJAS HORARIAS
			if (franjas.getKey()==aleatorio) { // *EL CORTE NO VA* RECORRO EL ARREGLO (VALUE) DE CADA FRANJA (KEY) ABER SI TENGO CUPO
				Turno nuevo = new Turno (franjas.getKey(),this.getNumerodemesa(),p.getDni());// crea un Turno nuevo
				franjas.getValue().add(nuevo); //AGREGO UN NUEVO Turno
				p.setTiene_turno(true); //MODIFICO EL ESTADO DE Turno DE LA PERSONA
				this.sumarvotante(); // sumo un votante a la cantidad de votantes en mesa
				return nuevo; //DEVUELVO LOS DATOS DEL Turno
				
			}
			
		}
		return null;
		
	
		}
		

 

	
}

