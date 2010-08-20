package br.org.ged.direto.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pstgrad")
public class PstGrad implements Serializable
 {
	
	private static final long serialVersionUID = 6835708632546027637L;
	private Integer idPstGrad;
	private String pstgradDesc;
	private String pstgradNome;
	
	public PstGrad(){}
	
	public PstGrad(Integer idPstGrad, String pstgradDesc, String pstgradNome) {
		this.idPstGrad = idPstGrad;
		this.pstgradDesc = pstgradDesc;
		this.pstgradNome = pstgradNome;
	}
	@Id
	@GeneratedValue
	@Column(name = "IdPstGrad")
	public Integer getIdPstGrad() {
		return idPstGrad;
	}
	public void setIdPstGrad(Integer idPstGrad) {
		this.idPstGrad = idPstGrad;
	}
	
	@Column(name = "PstGradDesc")
	public String getPstgradDesc() {
		return pstgradDesc;
	}
	public void setPstgradDesc(String pstgradDesc) {
		this.pstgradDesc = pstgradDesc;
	}
	
	@Column(name = "PstGradNome")
	public String getPstgradNome() {
		return pstgradNome;
	}
	public void setPstgradNome(String pstgradNome) {
		this.pstgradNome = pstgradNome;
	}
	
	

}
