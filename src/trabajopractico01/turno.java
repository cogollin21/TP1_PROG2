package trabajopractico01;

import java.util.Objects;

public class turno {
	Integer franja_horaria;
	Integer num_demesa;
	Integer dni;
	
public turno ( Integer franja , Integer numdemesa , Integer dni) {
	this.franja_horaria=franja;
	this.num_demesa=numdemesa;
	this.dni=dni;
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
