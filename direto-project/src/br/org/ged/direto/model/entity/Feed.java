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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "feed")
public class Feed implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3878588325507927456L;
	
	@Id
	@GeneratedValue
	@Column(name = "IdFeed")
	private Integer idFeed;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCarteira", nullable = false)
	private Carteira carteira;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUsuario", nullable = false)
	private Usuario usuario;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCarteiraRem", nullable = false)
	private Carteira carteiraRem;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUsuarioRem", nullable = false)
	private Usuario usuarioRem;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdDocumento", nullable = false)
	private DocumentoDetalhes documentoDetalhes;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataHora", nullable = true)
	private Date dataHora;
	
	@Column(name = "acao", nullable = false, length = 1024)
	private String acao;
	
	@Column(name="IdAnotacao",nullable=false)
	private Integer IdAnotacao;
	
	@Column(name="IdDespacho",nullable=false)
	private Integer IdDespacho;
	
	public Integer getIdFeed() {
		return idFeed;
	}

	public void setIdFeed(Integer idFeed) {
		this.idFeed = idFeed;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Carteira getCarteiraRem() {
		return carteiraRem;
	}

	public void setCarteiraRem(Carteira carteiraRem) {
		this.carteiraRem = carteiraRem;
	}

	public Usuario getUsuarioRem() {
		return usuarioRem;
	}

	public void setUsuarioRem(Usuario usuarioRem) {
		this.usuarioRem = usuarioRem;
	}

	public DocumentoDetalhes getDocumentoDetalhes() {
		return documentoDetalhes;
	}

	public void setDocumentoDetalhes(DocumentoDetalhes documentoDetalhes) {
		this.documentoDetalhes = documentoDetalhes;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Integer getIdAnotacao() {
		return IdAnotacao;
	}

	public void setIdAnotacao(Integer idAnotacao) {
		IdAnotacao = idAnotacao;
	}

	public Integer getIdDespacho() {
		return IdDespacho;
	}

	public void setIdDespacho(Integer idDespacho) {
		IdDespacho = idDespacho;
	}
	
}
