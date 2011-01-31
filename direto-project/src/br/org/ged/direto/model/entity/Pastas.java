package br.org.ged.direto.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "pastas")
public class Pastas implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4805607521619811891L;
	
	@Id
	@Column(name = "IdPasta",updatable=false)
	@OrderBy("idPasta desc")  
	private Integer idPasta;
	
	@Column(name = "NomePasta",updatable=false)
	private String nomePasta;
	
	public Integer getIdPasta() {
		return idPasta;
	}
	public void setIdPasta(Integer idPasta) {
		this.idPasta = idPasta;
	}
	public String getNomePasta() {
		return nomePasta;
	}
	public void setNomePasta(String nomePasta) {
		this.nomePasta = nomePasta;
	}
	
	
}
	
