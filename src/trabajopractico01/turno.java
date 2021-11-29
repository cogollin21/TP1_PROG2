package trabajopractico01;

import java.util.Objects;

public class turno {
private	Integer franja_horaria;
private	Integer num_demesa;
private	Integer dni;
	
public turno ( Integer franja , Integer numdemesa , Integer dni) {
	this.franja_horaria=franja;
	this.num_demesa=numdemesa;
	this.dni=dni;
}

public Integer getFranja_horaria() {
	return franja_horaria;
}

public void setFranja_horaria(Integer franja_horaria) {
	this.franja_horaria = franja_horaria;
}

public Integer getNum_demesa() {
	return num_demesa;
}

public void setNum_demesa(Integer num_demesa) {
	this.num_demesa = num_demesa;
}

public Integer getDni() {
	return dni;
}

public void setDni(Integer dni) {
	this.dni = dni;
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
	turno other = (turno) obj;
	return Objects.equals(dni, other.dni);
}
	
	
}
