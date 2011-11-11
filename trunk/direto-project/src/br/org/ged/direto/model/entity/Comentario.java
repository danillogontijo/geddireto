package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.Date;
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
@Table(name = "comentarios")
public class Comentario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837979324473592296L;
	
	@Id
	@GeneratedValue
	@Column(name = "Id")
	private Integer idComentario;
	
	private String comentario;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdUsuario", nullable = false)
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DataHora", nullable = false)
	private Date dataHoraComentario;
	
	public Date getDataHoraComentario() {
		return dataHoraComentario;
	}

	public void setDataHoraComentario(Date dataHoraComentario) {
		this.dataHoraComentario = dataHoraComentario;
	}

	public Integer getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(Integer idComentario) {
		this.idComentario = idComentario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	

}
