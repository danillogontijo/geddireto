package br.org.ged.direto.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.PstGradService;
import br.org.ged.direto.model.service.UsuarioService;

@Controller
@RequestMapping("/teste.html")
@SessionAttributes("usuario")
public class TesteController {
	
		private PstGradService pstgradService;
		private UsuarioService usuarioService;
		
		@Autowired
		public void setPstgradService(PstGradService pstgradService) {
			this.pstgradService = pstgradService;
		}

		@Autowired
		public void setUsuarioService(UsuarioService usuarioService) {
			this.usuarioService = usuarioService;
		}

		@ModelAttribute("pstgradList")
		public Collection<PstGrad> populatePstGradTypes() {
	       
	        return this.pstgradService.getAll();
	    }

		@RequestMapping(method = RequestMethod.GET)
		public String setupForm(ModelMap model) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		     
			//model.addAttribute("usuario", (Usuario) auth.getPrincipal());
			model.addAttribute("usuario", this.usuarioService.selectByLogin(auth.getName()));
			return "teste";
		}

		@RequestMapping(method = RequestMethod.POST)
		public String processSubmit(@ModelAttribute("usuario")Usuario usuario, BindingResult result,
				SessionStatus status) {

		    System.out.println(usuario);

	        return "usuarios-index";
		}



}
