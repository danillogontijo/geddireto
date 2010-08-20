package br.org.ged.direto.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="secao", uniqueConstraints = {
		@UniqueConstraint(columnNames = "IdSecao")})
public class Secao {
	
	private Integer idSecao;
	private String secaoDesc;
	private String secaoAbr;
	
	@Id
	@GeneratedValue
	@Column(name="IdSecao")
	public Integer getIdSecao() {
		return idSecao;
	}
	public void setIdSecao(Integer idSecao) {
		this.idSecao = idSecao;
	}
	
	@Column(name = "SecDesc", nullable = false, length = 60)
	public String getSecaoDesc() {
		return secaoDesc;
	}
	public void setSecaoDesc(String secaoDesc) {
		this.secaoDesc = secaoDesc;
	}
	
	@Column(name = "SecAbr", nullable = false, length = 30)
	public String getSecaoAbr() {
		return secaoAbr;
	}
	public void setSecaoAbr(String secaoAbr) {
		this.secaoAbr = secaoAbr;
	}
	
	

}
