package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.org.ged.direto.controller.utils.MenuTopo;
import br.org.ged.direto.model.entity.Conta;
import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
@RequestMapping("/principal.html")
@SessionAttributes({"usuario", "mt"})
public class PrincipalController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	@ModelAttribute("numUsers")
	public int getNumberOfUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}
	
	/*@ModelAttribute("menuTopo")
	public Collection<String> menuTopo() {
		List<String> menuList = new ArrayList<String>();
		MenuTopo menu = new MenuTopo();
		
		menuList.add(menu.getAdmin());
		menuList.add(menu.getAlterarSenha());
		menuList.add(menu.getSugestoes());
		return menuList;
	}*/
	
	@ModelAttribute("types")
	public Collection<PstGrad> populateProductTypes() {
	List<PstGrad> types = new ArrayList<PstGrad>();
	types.add(new PstGrad(50,"teste","teste"));
	//types.add("CDs");
	//types.add("MP3 Players");
	return types;
	}


	
	@RequestMapping(method = RequestMethod.GET)
	public String showUserDetails(ModelMap model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		//Usuario usuario = this.usuarioService.selectById(usuarioTemp.getIdUsuario());
		
		//PstGrad pstgrad = usuario.getPstGrad();
		
		//Iterator<PstGrad> ite_pstgrad = usuario.getPstGrad().iterator();
		
		model.addAttribute("usuario",usuario);
		//model.addAttribute("menuTopo",menuTopo());
		//model.addAttribute(pstgrad);
		//model.addAttribute(usuarioService.listActivedContas(auth.getName()));
		
		//model.addAttribute(auth.getCredentials());
		
		//System.out.println(auth.toString());
		System.out.println(usuario.getContas().size());
		
		return "principal";
	
	}

}
