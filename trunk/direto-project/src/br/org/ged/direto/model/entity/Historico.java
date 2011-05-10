package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "historico")
public class Historico implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3937434284783381510L;

	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer idHistorico;
	
	@Column(name = "Acao")
	private String historico;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdMensagem", nullable = false, updatable = false)
	private DocumentoDetalhes documentoDetalhes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUsuario", nullable = false, updatable = false)
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataHora", nullable = true)
	private Date dataHoraHistorico;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdCarteira", nullable = false, updatable = false)
	private Carteira carteira;

	
	public Integer getIdHistorico() {
		return idHistorico;
	}

	public void setIdHistorico(Integer idHistorico) {
		this.idHistorico = idHistorico;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public DocumentoDetalhes getDocumentoDetalhes() {
		return documentoDetalhes;
	}

	public void setDocumentoDetalhes(DocumentoDetalhes documentoDetalhes) {
		this.documentoDetalhes = documentoDetalhes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataHoraHistorico() {
		return dataHoraHistorico;
	}

	public void setDataHoraHistorico(Date dataHoraHistorico) {
		this.dataHoraHistorico = dataHoraHistorico;
	}

	

	
	
	

}
