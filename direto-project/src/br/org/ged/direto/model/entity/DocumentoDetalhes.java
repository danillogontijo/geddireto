package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mensagens")
public class DocumentoDetalhes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8737487864886359495L;
	
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer idDocumentoDetalhes;
	
	@Column(name = "Tipo")
	private char tipo;
	
	@Column(name = "Prioridade")
	private char prioridade;
	
	@Column(name = "Assunto")
	private String Assunto;
	
	/*@OneToMany(fetch = FetchType.LAZY, mappedBy = "mensagens", cascade = CascadeType.ALL)
	private Set<Documento> documentos = new HashSet<Documento>(0);

	public Set<Documento> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<Documento> documentos) {
		this.documentos = documentos;
	}*/

	public Integer getIdDocumentoDetalhes() {
		return idDocumentoDetalhes;
	}

	public void setIdDocumentoDetalhes(Integer idDocumentoDetalhes) {
		this.idDocumentoDetalhes = idDocumentoDetalhes;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}

	public char getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(char prioridade) {
		this.prioridade = prioridade;
	}

	public String getAssunto() {
		return Assunto;
	}

	public void setAssunto(String assunto) {
		Assunto = assunto;
	}
	
	

}
