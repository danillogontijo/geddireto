package br.org.ged.direto.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
public class PrincipalAdminController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	/*@SuppressWarnings("unchecked")
	@ModelAttribute("allUsersLogged")
	public List<Usuario> getallUsersLogged() {
		return (List<Usuario>)(List<?>) sessionRegistry.getAllPrincipals();
	}*/
	
	@SuppressWarnings("unchecked")
	@ModelAttribute("activeUsers")
	public Map<Usuario, Date> listActiveUsers() {
		Map<Usuario, Date> lastActivityDates = new HashMap<Usuario, Date>();
		
		for (Usuario principal : (List<Usuario>)(List<?>)sessionRegistry.getAllPrincipals()) {
			// a principal may have multiple active sessions
			for (SessionInformation session : sessionRegistry.getAllSessions(
					principal, false)) {
				// no last activity stored
				if (lastActivityDates.get(principal) == null) {
					lastActivityDates.put(principal, session.getLastRequest());
				} else {
					// check to see if this session is newer than the last
					// stored
					Date prevLastRequest = lastActivityDates.get(principal);
					if (session.getLastRequest().after(prevLastRequest)) {
						// update if so
						lastActivityDates.put(principal,
								session.getLastRequest());
					}
				}
			}
		}
		
		return lastActivityDates;
	}

	
	@RequestMapping(method = RequestMethod.GET,value="/admin/index.html")
	public String showAdmin(){
		return "admin/index";
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/admin/blank.html")
	public void blank(){
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/admin/config_usuarios.html")
	public void config(){
		System.out.println("Setando IdUsuarioProprietário da tabela usuario...");
		List<Usuario> usuarios = usuarioService.getAll(new Usuario());
		for(Usuario usu : usuarios){
			Set<Conta> contasUsuario = usu.getContas();
			System.out.println("Quantidade de contas: "+contasUsuario.size());
			for(Conta conta : contasUsuario){
				System.out.println("Conta modificando: "+conta.getCarteira().getCartAbr());
				System.out.println("É principal? "+conta.isPrincipal());
				if(conta.isPrincipal())
					conta.setIdUsuarioProprietario(usu.getIdUsuario());
			}
			
			usuarioService.editUser(usu);
		}
		
	}
}
