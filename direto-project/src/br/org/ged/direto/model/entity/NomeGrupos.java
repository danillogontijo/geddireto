package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="nomegrupos", uniqueConstraints = {
		@UniqueConstraint(columnNames = "IdNomeGrupos")})
public class NomeGrupos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8772995341707212096L;
	
	@Id
	@GeneratedValue
	@Column(name="IdNomeGrupos")
	private Integer idNomeGrupo;
	
	@Column(name="GrupoAbr")
	private String grupoAbr;

	@Column(name="GrupoDesc")
	private String grupoDesc;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="IdNomeGrupo")
	private List<Grupos> grupos;
	
	public List<Grupos> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupos> grupos) {
		this.grupos = grupos;
	}

	public String getGrupoAbr() {
		return grupoAbr;
	}

	public void setGrupoAbr(String grupoAbr) {
		this.grupoAbr = grupoAbr;
	}

	public String getGrupoDesc() {
		return grupoDesc;
	}

	public void setGrupoDesc(String grupoDesc) {
		this.grupoDesc = grupoDesc;
	}

	public Integer getIdNomeGrupo() {
		return idNomeGrupo;
	}
	
	

}
