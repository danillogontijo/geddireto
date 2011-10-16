package br.org.ged.direto.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="grupo", uniqueConstraints = {
		@UniqueConstraint(columnNames = "IdGrupo")})
public class Grupos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2703542890960455913L;
	
	@Id
	@GeneratedValue
	@Column(name="IdGrupo")
	private Integer idGrupo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdNomeGrupo", nullable = false)
	private NomeGrupos nomeGrupo;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCarteira", nullable = false)
	private Carteira carteira; 

	public NomeGrupos getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(NomeGrupos nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	public void setCarteira(Carteira carteira) {
		this.carteira = carteira;
	}
	

}
