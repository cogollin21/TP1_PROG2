package trabajopractico01;

import java.util.ArrayList;

public class Enf_Preex extends mesa {
	public Enf_Preex () {
		this.numerodemesa= mesa.cantmesas++;
		this.tipo="Enf_Preex";
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
		return "presidente " +presidente+ "numero de mesa "+numerodemesa+" "+"mesa de tipo enfermedades";
	}

	
	

}
