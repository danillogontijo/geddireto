package br.org.ged.direto.controller.admin;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
public class PrincipalAdminController {

	@Autowired
	UsuarioService usuarioService;
	
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
