package br.org.ged.direto.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="anexos", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Id"),
		@UniqueConstraint(columnNames = "AnexoCaminho") })
public class Anexo {
	
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer idAnexo;
	
	@Column(name = "AnexoNome")
	private String anexoNome;
	
	@Column(name = "AnexoCaminho")
	private String anexoCaminho;
	
	@Column(name = "Assinado")
	private int assinado;
	
	@Column(name = "AssinadoPor")
	private String assinadoPor;
	
	@Column(name = "AssinaturaHash",columnDefinition="longtext")
	private String assinaturaHash;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdMensagem", nullable = false, insertable = true, updatable= false)
    private DocumentoDetalhes documentoDetalhes;
	
	@Transient
	private String hash;
	
	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Integer getIdAnexo() {
		return idAnexo;
	}

	public void setIdAnexo(Integer idAnexo) {
		this.idAnexo = idAnexo;
	}

	public String getAnexoNome() {
		return anexoNome;
	}

	public void setAnexoNome(String anexoNome) {
		this.anexoNome = anexoNome;
	}

	public String getAnexoCaminho() {
		return anexoCaminho;
	}

	public void setAnexoCaminho(String anexoCaminho) {
		this.anexoCaminho = anexoCaminho;
	}

	public int getAssinado() {
		return assinado;
	}

	public void setAssinado(int assinado) {
		this.assinado = assinado;
	}

	public String getAssinadoPor() {
		return assinadoPor;
	}

	public void setAssinadoPor(String assinadoPor) {
		this.assinadoPor = assinadoPor;
	}
	
	public String getAssinaturaHash() {
		return assinaturaHash;
	}

	public void setAssinaturaHash(String assinaturaHash) {
		this.assinaturaHash = assinaturaHash;
	}

	public DocumentoDetalhes getDocumentoDetalhes() {
		return documentoDetalhes;
	}

	public void setDocumentoDetalhes(DocumentoDetalhes documentoDetalhes) {
		this.documentoDetalhes = documentoDetalhes;
	}
	
	public String toString(){
		return this.anexoNome;
	}
	

}
