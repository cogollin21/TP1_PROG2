package trabajopractico01;

import java.util.ArrayList;

public class mesatrabajadores extends mesa {
	
	public mesatrabajadores () {
		this.numerodemesa= mesa.cantmesas++;
		this.tipo="mesatrabajadores";
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

	


	
}
