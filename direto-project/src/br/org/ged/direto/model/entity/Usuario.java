package br.org.ged.direto.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
//import org.springframework.security.core.userdetails.User.AuthorityComparator;


 
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
	@OrderBy("idPstGrad asc")
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
	@OrderBy("contaPrincipal desc, carteira asc")  
	private Set<Conta> contas = new HashSet<Conta>(0);
	
	@Transient
	private int idCarteira;
	
	@Transient
	private final Collection<GrantedAuthority> authorities = getAuthorities();
	@Transient
    private final boolean accountNonExpired = true;
	@Transient
    private final boolean accountNonLocked =  true;
	@Transient
    private final boolean credentialsNonExpired = true;
	@Transient
    private final boolean enabled = true;
	
	public Usuario(){}
	
	/*public Usuario(){
		this.enabled = true;
		this.accountNonExpired = true;
		this.credentialsNonExpired = true;
		this.accountNonLocked = true;
		this.authorities = (Set<GrantedAuthority>) getAuthorities();
	}
	
	public Usuario(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities){
		
		this.usuLogin = username;
		this.usuSenha = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = (Set<GrantedAuthority>) authorities;
		
	}
	
	public Usuario(Integer idUsuario, PstGrad pstGrad, String usuNome,
			String usuLogin, Integer usuIdt, String usuNGuerra,
			String usuPapel, String usuSenha, Date usuUltimoLogin,boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities) {
		
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = (Set<GrantedAuthority>) authorities;
		
		this.idUsuario = idUsuario;
		this.pstGrad = pstGrad;
		this.usuNome = usuNome;
		this.usuLogin = usuLogin;
		this.usuIdt = usuIdt;
		this.usuNGuerra = usuNGuerra;
		this.usuPapel = usuPapel;
		this.usuSenha = usuSenha;
		this.usuUltimoLogin = usuUltimoLogin;
	}*/
	
	/*public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
*/
	public int getIdCarteira() {
		return idCarteira;
	}

	public void setIdCarteira(int idCarteira) {
		this.idCarteira = idCarteira;
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
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
	public int hashCode() {
        int code = 9792;

        for (GrantedAuthority authority : getAuthorities()) {
            code = code * (authority.hashCode() % 7);
        }

        if (this.getPassword() != null) {
            code = code * (this.getPassword().hashCode() % 7);
        }

        if (this.getUsername() != null) {
            code = code * (this.getUsername().hashCode() % 7);
        }
        
        if (this.getIdUsuario()!= null) {
        	code = code * (this.getIdUsuario().hashCode() % 7);
        }
        
        if (this.getUsuIdt() != null) {
        	code = code * (this.getUsuIdt().hashCode() % 7);
        }

        if (this.isAccountNonExpired()) {
            code = code * -2;
        }

        if (this.isAccountNonLocked()) {
            code = code * -3;
        }

        if (this.isCredentialsNonExpired()) {
            code = code * -5;
        }

        if (this.isEnabled()) {
            code = code * -7;
        }

        return code;
    }
	
	public boolean equals(Object rhs) {
        if (!(rhs instanceof Usuario) || (rhs == null)) {
            return false;
        }

        Usuario user = (Usuario) rhs;

        // We rely on constructor to guarantee any User has non-null
        // authorities
        if (!authorities.equals(user.authorities)) {
            return false;
        }

        // We rely on constructor to guarantee non-null username and password
        return (this.getPassword().equals(user.getPassword()) && this.getUsername().equals(user.getUsername())
                && (this.isAccountNonExpired() == user.isAccountNonExpired())
                && (this.isAccountNonLocked() == user.isAccountNonLocked())
                && (this.isCredentialsNonExpired() == user.isCredentialsNonExpired())
                && (this.isEnabled() == user.isEnabled()));
    }
	
	public String toString(){
		 StringBuilder sb = new StringBuilder();
	        sb.append(super.toString()).append(": ");
	        sb.append("Username: ").append(this.usuLogin).append("; ");
	        sb.append("Password: [PROTECTED]; ");
	        sb.append("Enabled: ").append(this.isEnabled()).append("; ");
	        sb.append("AccountNonExpired: ").append(this.isAccountNonExpired()).append("; ");
	        sb.append("credentialsNonExpired: ").append(this.isCredentialsNonExpired()).append("; ");
	        sb.append("AccountNonLocked: ").append(this.isAccountNonLocked()).append("; ");

	        if (!getAuthorities().isEmpty()) {
	            sb.append("Granted Authorities [atual]: ");

	            boolean first = true;
	            for (GrantedAuthority auth : getAuthorities()) {
	                if (!first) {
	                    sb.append(",");
	                }
	                first = false;

	                sb.append(auth);
	            }
	        } else {
	            sb.append("Not granted any authorities");
	        }

	        return sb.toString();
	}

	
}
	
	