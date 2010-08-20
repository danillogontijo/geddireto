package br.org.ged.direto.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="om", uniqueConstraints = {
		@UniqueConstraint(columnNames = "IdOM")})
public class OM implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1275557580783906925L;
	private Integer idOM;
	private String omDesc;
	private String omAbr;
	
	@Id
	@GeneratedValue
	@Column(name="IdOM")
	public Integer getIdOM() {
		return idOM;
	}
	public void setIdOM(Integer idOM) {
		this.idOM = idOM;
	}
	
	@Column(name = "OMDesc", nullable = false, length = 60)
	public String getOmDesc() {
		return omDesc;
	}
	public void setOmDesc(String omDesc) {
		this.omDesc = omDesc;
	}
	
	@Column(name = "OMAbr", nullable = false, length = 30)
	public String getOmAbr() {
		return omAbr;
	}
	public void setOmAbr(String omAbr) {
		this.omAbr = omAbr;
	}
	
	
	

}
