package br.org.ged.direto.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="funcao", uniqueConstraints = {@UniqueConstraint(columnNames = "IdFuncao")})
public class Funcao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8763792704942723220L;
	private Integer idFuncao;
	private String funcDesc;
	private String funcAbr;
	
	@Id
	@GeneratedValue
	@Column(name="IdFuncao")
	public Integer getIdFuncao() {
		return idFuncao;
	}
	public void setIdFuncao(Integer idFuncao) {
		this.idFuncao = idFuncao;
	}
	
	@Column(name = "FuncDesc", nullable = false, length = 60)
	public String getFuncDesc() {
		return funcDesc;
	}
	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}
	
	@Column(name = "FuncAbr", nullable = false, length = 30)
	public String getFuncAbr() {
		return funcAbr;
	}
	public void setFuncAbr(String funcAbr) {
		this.funcAbr = funcAbr;
	}
	
	

}
