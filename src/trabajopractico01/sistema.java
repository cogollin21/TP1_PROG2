package trabajopractico01;

import java.util.ArrayList;
import java.util.HashMap;

public class sistema {
   ArrayList <persona> RegistroDeVotantes;
   ArrayList <mesa> RegistroDeMesas;
   ArrayList <persona> PersonasQueVotaron;
   ArrayList <persona> PersonasConTurno;
   
   
  
public sistema () {
	this.PersonasQueVotaron= new ArrayList <persona>();
	this.RegistroDeMesas= new ArrayList <mesa>();
	this.RegistroDeVotantes= new ArrayList <persona>();
	
}

public boolean ExistePersona ( persona p ) {
	
	return this.RegistroDeVotantes.contains(p);

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


public int RegistrarMesa (String tipomesa , Integer dni) {
	boolean existe= false;
	persona pers=null;
	
	for (persona p : this.RegistroDeVotantes) {
		if (p.getDni()==dni && !p.isEs_presidente()) {
			existe=true;
			pers=p;
			p.setEs_presidente(true);
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
