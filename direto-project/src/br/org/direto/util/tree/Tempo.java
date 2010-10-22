package br.org.direto.util.tree;

import java.util.Date;

public class Tempo {
	
	private Date dataInical,dataFinal;
	private Tipo tipo;
		
	public Tempo(Date dataInicial, Date dataFinal, Tipo tipo){
		this.dataInical = dataInicial;
		this.dataFinal = dataFinal;
		this.tipo = tipo;
	}
	
	public long getIntervalo(){
		long interval = dataFinal.getTime() - dataInical.getTime(); 
		int tempoDia = 1000 * 60 * 60 * 24;
		
		return ((interval / tempoDia)+1);
	}
	
	public Date getDataInical() {
		return dataInical;
	}
	
	public void setDataInical(Date dataInical) {
		this.dataInical = dataInical;
	}
	
	public Date getDataFinal() {
		return dataFinal;
	}
	
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
