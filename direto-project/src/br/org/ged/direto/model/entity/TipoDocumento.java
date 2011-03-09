package br.org.ged.direto.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="tiposdocumentos", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Id")})
public class TipoDocumento implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7037844993370693426L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer idTipoDocumento;
	
	@Column(name="TipoAbr")
	private String tipoDocumentoAbr;

	@Column(name="TipoNome")
	private String tipoDocumentoNome;

	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getTipoDocumentoAbr() {
		return tipoDocumentoAbr;
	}

	public void setTipoDocumentoAbr(String tipoDocumentoAbr) {
		this.tipoDocumentoAbr = tipoDocumentoAbr;
	}

	public String getTipoDocumentoNome() {
		return tipoDocumentoNome;
	}

	public void setTipoDocumentoNome(String tipoDocumentoNome) {
		this.tipoDocumentoNome = tipoDocumentoNome;
	}
	
	

}
