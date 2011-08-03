package br.org.ged.direto.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ged.direto.model.entity.Usuario;

@Controller
public class ContaController extends BaseController {
	
	@RequestMapping(value="/alternar.html",method=RequestMethod.GET)
	public String alternarConta(@RequestParam("id") int idCarteira, HttpServletRequest request) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario o = (Usuario) auth.getPrincipal();
		o.setIdCarteira(idCarteira);
		
		request.getSession(true).setAttribute("j_usuario_conta", String.valueOf(idCarteira));
		//return new Integer(Integer.parseInt((String)session.getAttribute("j_usuario_conta")));
		
		return "redirect:principal.html?box=1&pr=0&filtro=todas";
		//return "alternar";
	}
	
	@RequestMapping(value="/passarConta.html",method=RequestMethod.GET)
	public String passarConta(HttpServletRequest request){
		
		return "passarConta";
	}

}
