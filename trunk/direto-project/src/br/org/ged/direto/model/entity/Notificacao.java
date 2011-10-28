package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notificacoes")
public class Notificacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7931349602524137017L;
	
	
	@Id
	@GeneratedValue
	@Column(name = "IdNotificacoes")
	private Integer idNotificacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdMensausu", nullable = false, insertable = true, updatable = false)
	private Documento documento;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataHora", nullable = true)
	private Date dataHoraNotificacao;
	
	@Column(name = "Notificacao")
	private String notificacao;

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Date getDataHoraNotificacao() {
		return dataHoraNotificacao;
	}

	public void setDataHoraNotificacao(Date dataHoraNotificacao) {
		this.dataHoraNotificacao = dataHoraNotificacao;
	}

	public String getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(String notificacao) {
		this.notificacao = notificacao;
	}

	public Integer getIdNotificacao() {
		return idNotificacao;
	}
	
}
