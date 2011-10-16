package br.org.ged.direto.model.service.impl;

import java.util.Date;
import java.util.List;
import br.org.direto.util.DataUtils;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.repository.UsuarioRepository;
import br.org.ged.direto.model.service.ContaService;
import br.org.ged.direto.model.service.PstGradService;
import br.org.ged.direto.model.service.SegurancaService;
import br.org.ged.direto.model.service.UsuarioService;
import br.org.ged.direto.model.service.security.IChangePassword;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("usuarioService")
@RemoteProxy(name = "usuarioJS")
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository usuarioRepository;
	private MessageSource messageSource;
	private IChangePassword changePasswordSecurity;
	private PstGradService pstGradService;
	private SegurancaService segurancaService;
	private ContaService contaService;
	
	@Autowired
	public void setContaService(ContaService contaService) {
		this.contaService = contaService;
	}

	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	public void setSegurancaService(SegurancaService segurancaService) {
		this.segurancaService = segurancaService;
	}
	
	@Autowired
	public void setPstGradService(PstGradService pstGradService) {
		this.pstGradService = pstGradService;
	}

	@Autowired
	public void setChangePasswordDao(IChangePassword changePasswordSecurity) {
		this.changePasswordSecurity = changePasswordSecurity;
	}

	@Autowired
	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Autowired
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public MessageSource getMessageSource() {
		return messageSource;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Usuario> getAll(Usuario usuario) {
		return this.usuarioRepository.getAll(usuario);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editUser(Usuario usuario) {
		Usuario userToChange = selectById(usuario.getIdUsuario());
		userToChange.setContas(usuario.getContas());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RemoteMethod
	public void save(Usuario usuario) throws Exception {
		try {
			usuario.setUsuNGuerra(usuario.getUsuNGuerra().toUpperCase());
			usuario.setUsuNome(usuario.getUsuNome().toUpperCase());
			this.usuarioRepository.save(usuario);
		} catch (Exception e) {
			throw new Exception("Não foi possível salvar o usuario."
					+ e.getMessage());
		}
	}
	
	@Override
	@RemoteMethod
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String editUser(String usuLogin, String usuNGuerra, String usuNome, String usuPapel, String usuSenha, int usuIdt, int idPstGrad, int idUsuario){
		try{
			
			if(idUsuario == 0){//Para cadastrar o usuario
				
				if(!validateUser(usuLogin)){
				
					Usuario userToAdd = new Usuario();
					userToAdd.setUsuLogin(usuLogin);
					userToAdd.setUsuNGuerra(usuNGuerra);
					userToAdd.setUsuNome(usuNome);
					userToAdd.setUsuPapel(usuPapel);
					userToAdd.setUsuSenha(segurancaService.md5(usuSenha));
					userToAdd.setUsuIdt(usuIdt);
					
					PstGrad pstGrad = pstGradService.getPstGradById(idPstGrad);
					userToAdd.setPstGrad(pstGrad);
					
					save(userToAdd);
					
					try{
						userToAdd = usuarioRepository.selectByLogin(usuLogin);
					}catch (NullPointerException e) {
						e.printStackTrace();
						return "Usuário não cadastrado ou não encontrado!";
					}
					
					return ""+userToAdd.getIdUsuario();
				
				}
				
			}else{//Editando o usuário
			
				if (!checkIfUserIsDuplicate(usuLogin, idUsuario))
					return "Este login já existe, escolha outro!";
					
				Usuario user = this.usuarioRepository.selectById(idUsuario);
				
				if (user.getPstGrad().getIdPstGrad() != idPstGrad){
					PstGrad pstGrad = pstGradService.getPstGradById(idPstGrad);
					user.setPstGrad(pstGrad);
				}
				
				user.setUsuLogin(usuLogin);
				user.setUsuNome(usuNome);
				user.setUsuNGuerra(usuNGuerra);
				user.setUsuIdt(usuIdt);
				user.setUsuPapel(usuPapel);
				
				if (!usuSenha.isEmpty())
					changePassword(usuLogin,usuSenha);
				return "Usuário atualizado com sucesso!\nAguarde verificação das contas...";
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "Não foi possível cadastrar o usuário!";
		}
		
		return "Usuário não cadastrado";
	}
	
	public boolean checkIfUserIsDuplicate(String usuLogin, int idUsuario){
		return usuarioRepository.checkIfUserIsDuplicate(usuLogin, idUsuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@RemoteMethod
	public Usuario selectById(Integer idUsuario) {
		return this.usuarioRepository.selectById(idUsuario);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Usuario selectByLogin(String usuLogin) {
		return this.usuarioRepository.selectByLogin(usuLogin);
	}

	@Override
	@RemoteMethod
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateUser(String usuLogin) {
		
		Object o = (Usuario) this.usuarioRepository.selectByLogin(usuLogin);
		
		return (o != null ? true : false);
	}

	@Override
	@RemoteMethod
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<DataUtils> listActivedContas(String usuLogin) {
		return (this.usuarioRepository.listActivedContas(usuLogin));
	}

	@Override
	public void changePassword(String usuLogin, String usuSenha) {
		if (usuSenha != "")
			changePasswordSecurity.changePassword(usuLogin, usuSenha);
	}

	@Override
	public String whoUser(int userid) {
		return this.usuarioRepository.whoUser(userid);
	}

	@Override
	public String editUser(String usuLogin, String usuNGuerra, String usuNome,
			int usuIdt, int idPstGrad, int idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> allUsersLoggedInSystem() {
		return sessionRegistry.getAllPrincipals();
	}

	@Override
	@RemoteMethod
	public int userIdentity(String usuLogin) {
		return selectByLogin(usuLogin).getUsuIdt();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateLoginDate() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario obj = (Usuario)auth.getPrincipal();
		
		Usuario usuario = selectById(obj.getIdUsuario());
		usuario.setUsuUltimoLogin(new Date());
	}

	@Override
	@RemoteMethod
	public String whereIsMyAccount(String usuLogin) {
		String retorno = "";
		
		try{
			Usuario usuario = selectByLogin(usuLogin);
			List<Conta> contas = contaService.getContasPrincipais(usuario.getIdUsuario());
			for(Conta conta : contas)
				retorno += "O "+conta.getUsuario().getUsuLogin()+" está resp pela conta "+conta.getCarteira().getCartAbr()+"["+conta.getCarteira().getOm().getOmAbr()+"]<br>";
			
			if(contas.size()==0)
				return "Você não tem nenhuma conta cadastrada, contate o adm sist.";
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

}
