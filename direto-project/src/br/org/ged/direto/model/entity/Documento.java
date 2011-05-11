package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="idmensausu", uniqueConstraints = {
		@UniqueConstraint(columnNames = "Id")})
public class Documento implements Serializable
 {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -879591813980925898L;
	//private byte[] contents;
	
	@Id
	@GeneratedValue
	@Column(name="Id")
	private Integer idDocumento;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdMensagem", nullable = false)
	private DocumentoDetalhes documentoDetalhes;
	
	@Column(name="Status")
	private char status;
	
	@Column(name="Observacao",nullable=true)
	private String observacao;
	
	@Column(name="UsuObs",nullable=true)
	private Integer encaminhadoPor;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdCarteira", nullable = true)
	private Carteira carteira;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataHora", nullable = true)
	private Date dataHora;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UltimaNotificacao", nullable = true, updatable=true)
	private Date dataHoraNotificacao;
	
	@Column(name="Notificar")
	private Integer notificar;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdMensausu", nullable = true)
	@OrderBy("idNotificacao asc") 
	private Set<Notificacao> notificacoes = new HashSet<Notificacao>(0);
	
	@Transient
	private boolean granted = false;
	
	public boolean isGranted() {
		return granted;
	}


	public void setGranted(boolean granted) {
		this.granted = granted;
	}


	public Documento(){}
	
	
	public Documento(Integer idDocumento, DocumentoDetalhes documentoDetalhes,
			char status, Carteira carteira) {
		this.idDocumento = idDocumento;
		this.documentoDetalhes = documentoDetalhes;
		this.status = status;
		this.carteira = carteira;
	}

	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	public Integer getEncaminhadoPor() {
		return encaminhadoPor;
	}


	public void setEncaminhadoPor(Integer encaminhadoPor) {
		this.encaminhadoPor = encaminhadoPor;
	}


	public Set<Notificacao> getNotificacoes() {
		return notificacoes;
	}


	public void setNotificacoes(Set<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}


	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public DocumentoDetalhes getDocumentoDetalhes() {
		return documentoDetalhes;
	}

	public void setDocumentoDetalhes(DocumentoDetalhes documentoDetalhes) {
		this.documentoDetalhes = documentoDetalhes;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}


	public Date getDataHoraNotificacao() {
		return dataHoraNotificacao;
	}


	public void setDataHoraNotificacao(Date dataHoraNotificacao) {
		this.dataHoraNotificacao = dataHoraNotificacao;
	}


	public Integer getNotificar() {
		return notificar;
	}


	public void setNotificar(Integer notificar) {
		this.notificar = notificar;
	}
	
	
	
}
