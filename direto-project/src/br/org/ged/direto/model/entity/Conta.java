package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "usuomsec")
public class Conta implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5134202519833014598L;

	private Integer idConta;
	private Integer ativado;
	private Integer contaPrincipal;
	
	private Date dataContaRec;
	private Date dataContaTransf;
	
	private Usuario usuario;
	private Carteira carteira;
	
	public Conta(){}
		
	public Conta(Integer idConta, Integer ativado, Date dataContaRec,
			Date dataContaTransf) {
		this.idConta = idConta;
		this.ativado = ativado;
		this.dataContaRec = dataContaRec;
		this.dataContaTransf = dataContaTransf;
	}
	
	@Id
	@GeneratedValue
	@Column(name="IdUsuOMSec")
	public Integer getIdConta() {
		return idConta;
	}
	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}
	
	@Column(name = "ContaPrincipal", nullable = false, length = 1)	
	public Integer getContaPrincipal() {
		return contaPrincipal;
	}

	public void setContaPrincipal(Integer contaPrincipal) {
		this.contaPrincipal = contaPrincipal;
	}

	@Column(name = "Ativado", nullable = false, length = 1)
	public Integer getAtivado() {
		return ativado;
	}
	public void setAtivado(Integer ativado) {
		this.ativado = ativado;
	}
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name = "DataContaRec", nullable = true)
	public Date getDataContaRec() {
		return dataContaRec;
	}
	public void setDataContaRec(Date dataContaRec) {
		this.dataContaRec = dataContaRec;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataContaTransf", nullable = true)
	public Date getDataContaTransf() {
		return dataContaTransf;
	}
	public void setDataContaTransf(Date dataContaTransf) {
		this.dataContaTransf = dataContaTransf;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdUsuario", nullable = true)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "IdCarteira", nullable = false)
	@OrderBy("cartAbr asc")
	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}
	
	@Transient
	public boolean isPrincipal() {
		
		//isContaPrincipal = (this.getContaPrincipal() == 1) ? true : false; 
		
		return (this.getContaPrincipal() == 1) ? true : false; 
	}
	
	
	
	
}
