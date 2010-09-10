package br.org.ged.direto.controller;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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



import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.Pastas;

import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.PastasService;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
@RequestMapping("/principal.html")
@SessionAttributes({"usuario", "mt"})
public class PrincipalController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DocumentosService documentosService;
	
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
	
	@ModelAttribute("documentos")
	public Collection<Documento> todosDocumentos() {
		
		List<Documento> pastas = new ArrayList<Documento>();
		
		pastas = this.documentosService.listByLimited(); 
		
		return pastas;
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public String showUserDetails(@RequestParam("box") int box, ModelMap model, HttpServletRequest request) {
		
		if (box == 0)
			box = 1;
		
		//String request.getParameter("");
		
		//System.out.println(this.documentosService.selectByIdCarteira(new Integer(2)));
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpSession session = request.getSession(true);
		
		Usuario usuario = usuarioService.selectByLogin(auth.getName());
		//Usuario usuario = this.usuarioService.selectById(usuarioTemp.getIdUsuario());
		
		System.out.println("j_conta: "+session.getAttribute("j_usuario_conta"));
		
		//PstGrad pstgrad = usuario.getPstGrad();
		
		//Iterator<PstGrad> ite_pstgrad = usuario.getPstGrad().iterator();
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("box",box);
		model.addAttribute("contaAtual", session.getAttribute("j_usuario_conta"));
		//model.addAttribute(pstgrad);
		//model.addAttribute(usuarioService.listActivedContas(auth.getName()));
		
		//model.addAttribute(auth.getCredentials());
		
		//System.out.println(auth.toString());
		System.out.println(usuario.getContas().size());
		
		return "principal";
	
	}

}
