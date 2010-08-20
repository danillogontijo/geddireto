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
@Table(name = "carteira")
public class Carteira implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8573420165799069326L;
	private Integer idCarteira;
	private String cartDesc;
	private String cartAbr;
	private Set<Conta> contas = new HashSet<Conta>(0);
	private Funcao funcao;
	private Secao secao;
	private OM om;

	
	public Carteira(){	}
	
	public Carteira(Integer idCarteira, String cartDesc, String cartAbr,
			Integer idSecao, Integer idOM) {
		this.idCarteira = idCarteira;
		this.cartDesc = cartDesc;
		this.cartAbr = cartAbr;
		
	}

	@Id
	@GeneratedValue
	@Column(name = "IdCarteira")
	public Integer getIdCarteira() {
		return idCarteira;
	}

	public void setIdCarteira(Integer idCarteira) {
		this.idCarteira = idCarteira;
	}

	@Column(name = "CartDesc")
	public String getCartDesc() {
		return cartDesc;
	}

	public void setCartDesc(String cartDesc) {
		this.cartDesc = cartDesc;
	}

	@Column(name = "CartAbr")
	public String getCartAbr() {
		return this.cartAbr;
	}

	public void setCartAbr(String cartAbr) {
		this.cartAbr = cartAbr;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "carteira")
	public Set<Conta> getContas() {
		return this.contas;
	}

	public void setContas(Set<Conta> contas) {
		this.contas = contas;
	}
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdFuncao", nullable = false)	
	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdSecao", nullable = false)
	public Secao getSecao() {
		return secao;
	}

	public void setSecao(Secao secao) {
		this.secao = secao;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdOM", nullable = false)
	public OM getOm() {
		return om;
	}

	public void setOm(OM om) {
		this.om = om;
	}


}
