package trabajopractico01;

import java.util.Objects;

public class persona {
	protected Integer dni;
	protected String nombre;
	protected Integer edad;
	protected boolean tiene_enfermedad;
	protected boolean es_trabajador;
	protected boolean es_presidente;
	protected boolean tiene_turno;



@Override
	public String toString() {
		return "persona [dni=" + dni + ", nombre=" + nombre + ", edad=" + edad + ", tiene_enfermedad="
				+ tiene_enfermedad + ", es_trabajador=" + es_trabajador + ", es_presidente=" + es_presidente + "]";
	}



public boolean isTiene_turno() {
	return tiene_turno;
}



public void setTiene_turno(boolean tiene_turno) {
	this.tiene_turno = tiene_turno;
}



public persona (Integer dni , String nombre , Integer edad ,boolean enfermedad , boolean trabajador ){
	this.dni=dni;
	this.nombre=nombre;
	this.edad=edad;
	this.tiene_enfermedad=enfermedad;
	this.es_trabajador=trabajador;
	this.es_presidente=false;
	this.tiene_turno=false;	
}



@Override
public int hashCode() {
	return Objects.hash(dni);
}



@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	persona other = (persona) obj;
	return Objects.equals(dni, other.dni);
}



public Integer getDni() {
	return dni;
}



public void setDni(Integer dni) {
	this.dni = dni;
}



public String getNombre() {
	return nombre;
}



public void setNombre(String nombre) {
	this.nombre = nombre;
}



public Integer getEdad() {
	return edad;
}



public void setEdad(Integer edad) {
	this.edad = edad;
}



public boolean isTiene_enfermedad() {
	return tiene_enfermedad;
}



public void setTiene_enfermedad(boolean tiene_enfermedad) {
	this.tiene_enfermedad = tiene_enfermedad;
}



public boolean isEs_trabajador() {
	return es_trabajador;
}



public void setEs_trabajador(boolean es_trabajador) {
	this.es_trabajador = es_trabajador;
}



public boolean isEs_presidente() {
	return es_presidente;
}



public void setEs_presidente(boolean es_presidente) {
	this.es_presidente = es_presidente;
}






}