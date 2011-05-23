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
@Table(name = "despachos")
public class Despacho implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4811744262856868230L;
	
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer idDespacho;
	
	@Column(name = "Despacho")
	private String despacho;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdMensagem", nullable = false)
	private DocumentoDetalhes documentoDetalhes;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdUsuario", nullable = false)
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataHora", nullable = true)
	private Date dataHoraDespacho;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdCarteira", nullable = false)
	private Carteira carteira;
	
	@Column(name = "IdUsuarioDestinatario")
	private int idUsuarioDestinatario;

	public int getIdUsuarioDestinatario() {
		return idUsuarioDestinatario;
	}

	public void setIdUsuarioDestinatario(int idUsuarioDestinatario) {
		this.idUsuarioDestinatario = idUsuarioDestinatario;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}

	public Integer getIdDespacho() {
		return idDespacho;
	}

	public void setIdDespacho(Integer idDespacho) {
		this.idDespacho = idDespacho;
	}

	public String getDespacho() {
		return despacho;
	}

	public void setDespacho(String despacho) {
		this.despacho = despacho;
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

	public Date getDataHoraDespacho() {
		return dataHoraDespacho;
	}

	public void setDataHoraDespacho(Date dataHoraDespacho) {
		this.dataHoraDespacho = dataHoraDespacho;
	}
	
	

}
