package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.Collection;

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



import br.org.ged.direto.model.entity.Pastas;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.PastasService;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
@RequestMapping("/principal.html")
@SessionAttributes({"usuario", "mt"})
public class PrincipalController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PastasService pastasService;
	
	@Autowired
	SessionRegistry sessionRegistry;
	
	@ModelAttribute("numUsers")
	public int getNumberOfUsers() {
		return sessionRegistry.getAllPrincipals().size();
	}
	
	@ModelAttribute("pastas")
	public Collection<Pastas> todasPastas() {
		
		List<Pastas> pastas = new ArrayList<Pastas>();
		
		pastas = pastasService.getAll(); 
		
		return pastas;
	}


	
	@RequestMapping(method = RequestMethod.GET)
	public String showUserDetails(@RequestParam("box") int box, ModelMap model) {
		
		if (box == 0)
			box = 1;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		//Usuario usuario = this.usuarioService.selectById(usuarioTemp.getIdUsuario());
		
		//PstGrad pstgrad = usuario.getPstGrad();
		
		//Iterator<PstGrad> ite_pstgrad = usuario.getPstGrad().iterator();
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("box",box);
		//model.addAttribute("menuTopo",menuTopo());
		//model.addAttribute(pstgrad);
		//model.addAttribute(usuarioService.listActivedContas(auth.getName()));
		
		//model.addAttribute(auth.getCredentials());
		
		//System.out.println(auth.toString());
		System.out.println(usuario.getContas().size());
		
		return "principal";
	
	}

}
