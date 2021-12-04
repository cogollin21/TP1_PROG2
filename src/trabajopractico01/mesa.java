package trabajopractico01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Mesa {
	protected String tipo;
	protected Persona presidente;
	protected Integer numerodemesa;
	protected static	Integer cantmesas=0;
	protected Integer cantdevotantesenmesa=0;
	protected Map <Integer , ArrayList <Turno>> turnosdisponibles= new HashMap <Integer,ArrayList <Turno>>();;
	


public  void agregarpresidente (Persona p) {
	this.presidente = p;
	
}
abstract Turno asignarturnomesa(Persona p);

public void agregarturno (Turno t) { //agrega un Turno en la primer franja , es para el presidente
	this.turnosdisponibles.get(8).add(t);
	
}

public Integer getCantdevotantesenmesa() {
	return cantdevotantesenmesa;
}

public Map<Integer, ArrayList<Turno>> getTurnosdisponibles() {
	return turnosdisponibles;
}

public void setTurnosdisponibles(Map<Integer, ArrayList<Turno>> turnosdisponibles) {
	this.turnosdisponibles = turnosdisponibles;
}

public void setCantdevotantesenmesa(Integer cantdevotantesenmesa) {
	this.cantdevotantesenmesa = cantdevotantesenmesa;
}

public void sumarvotante () {
	this.cantdevotantesenmesa++;
}




public String getTipo() {
	return tipo;
}




public void setTipo(String tipo) {
	this.tipo = tipo;
}




@Override
public int hashCode() {
	return Objects.hash(numerodemesa);
}




@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Mesa other = (Mesa) obj;
	return Objects.equals(numerodemesa, other.numerodemesa);
}




public Persona getPresidente() {
	return presidente;
}





@Override
 abstract public String toString();

public Integer getNumerodemesa() {
	return numerodemesa;
}


public void setNumerodemesa(Integer numerodemesa) {
	this.numerodemesa = numerodemesa;
}


public static Integer getCantmesas() {
	return cantmesas;
}


public static void setCantmesas(Integer cantmesas) {
	Mesa.cantmesas = cantmesas;
}




	


}