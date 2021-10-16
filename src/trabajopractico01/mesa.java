package trabajopractico01;

public abstract class mesa {
	persona presidente;
	Integer numerodemesa;
	static	Integer cantmesas=0;



public  void agregarpresidente (persona p) {
	this.presidente = p;
	
}

public boolean equalsto ( mesa m) {
	if (this.numerodemesa==m.getNumerodemesa()) {
		return true;
	}
	else {
		return false;
	}
}


public persona getPresidente() {
	return presidente;
}





@Override
public String toString() {
	return "mesa [presidente=" + presidente + ", numerodemesa=" + numerodemesa + "]";
}

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
	mesa.cantmesas = cantmesas;
}




	


}