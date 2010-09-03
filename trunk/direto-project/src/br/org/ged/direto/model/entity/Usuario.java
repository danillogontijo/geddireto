package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import br.org.ged.direto.model.entity.security.Authority;

 
@Entity
@Table(name="usuario", uniqueConstraints = {
		@UniqueConstraint(columnNames = "IdUsuario"),
		@UniqueConstraint(columnNames = "UsuLogin") })
public class Usuario implements Serializable,UserDetails
 {
		
	/**
	 * 
	 */
	public static final long serialVersionUID = 5440047121962490468L;
	
	@Id
	@GeneratedValue
	@Column(name="IdUsuario")
	private Integer idUsuario;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "IdPstGrad", nullable = false)
	private PstGrad pstGrad;
	
	@Column(name = "UsuNome", nullable = false, length = 150)
	private String usuNome;
	
	@Column(name = "UsuLogin", unique = true, nullable = false, length = 60)
	private String usuLogin;
	
	@Column(name = "UsuIdt", nullable = false, length = 9)
	private Integer usuIdt;
	
	@Column(name = "UsuNGuerra", nullable = false, length = 60)
	private String usuNGuerra;
	
	@Column(name = "UsuPapel", nullable = false, length = 20)
	private String usuPapel;
	
	@Column(name = "UsuSenha", nullable = false, length = 255)
	private String usuSenha;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UsuUltimoLogin", nullable = true)
	private Date usuUltimoLogin;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL)
	@OrderBy("contaPrincipal desc,idConta asc")  
	private Set<Conta> contas = new HashSet<Conta>(0);
	
	private static String contaAtual;
	
	public Usuario(){
		//this.contaAtual = "";
	}
	
	public Usuario(Integer idUsuario, PstGrad pstGrad, String usuNome,
			String usuLogin, Integer usuIdt, String usuNGuerra,
			String usuPapel, String usuSenha, Date usuUltimoLogin, String contaAtual) {
		this.idUsuario = idUsuario;
		this.pstGrad = pstGrad;
		this.usuNome = usuNome;
		this.usuLogin = usuLogin;
		this.usuIdt = usuIdt;
		this.usuNGuerra = usuNGuerra;
		this.usuPapel = usuPapel;
		this.usuSenha = usuSenha;
		this.usuUltimoLogin = usuUltimoLogin;
		//this.contaAtual = contaAtual;
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getUsuNome() {
		return usuNome;
	}
	public void setUsuNome(String usuNome) {
		this.usuNome = usuNome;
	}
	
	public String getUsuLogin() {
		return usuLogin;
	}
	public void setUsuLogin(String usuLogin) {
		this.usuLogin = usuLogin;
	}
	
	public Integer getUsuIdt() {
		return usuIdt;
	}
	public void setUsuIdt(Integer usuIdt) {
		this.usuIdt = usuIdt;
	}
	
	public String getUsuNGuerra() {
		return usuNGuerra;
	}
	public void setUsuNGuerra(String usuNGuerra) {
		this.usuNGuerra = usuNGuerra;
	}
	
	public String getUsuPapel() {
		return usuPapel;
	}
	public void setUsuPapel(String usuPapel) {
		this.usuPapel = usuPapel;
	}
	
	public String getUsuSenha() {
		return usuSenha;
	}
	public void setUsuSenha(String usuSenha) {
		this.usuSenha = usuSenha;
	}
	
	public Date getUsuUltimoLogin() {
		return usuUltimoLogin;
	}
	public void setUsuUltimoLogin(Date usuUltimoLogin) {
		this.usuUltimoLogin = usuUltimoLogin;
	}
	
	public PstGrad getPstGrad() {
		return pstGrad;
	}
	public void setPstGrad(PstGrad pstGrad) {
		this.pstGrad = pstGrad;
	}

	public Set<Conta> getContas() {
		return contas;
	}

	public void setContas(Set<Conta> contas) {
		this.contas = contas;
	}
	
	public String getContaAtual() {
		return contaAtual;
	}

	public void setContaAtual(String contaAtual) {
		this.contaAtual = contaAtual;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		 
		 list.add(new GrantedAuthorityImpl("ROLE_"+this.getUsuPapel()));
         
        return (list);
	}

	@Override
	public String getPassword() {
		return this.getUsuSenha();
	}

	@Override
	public String getUsername() {
		return this.getUsuLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	
	public String toString(){
		return "Usuario: " + usuLogin;
	}

	
}
	
	