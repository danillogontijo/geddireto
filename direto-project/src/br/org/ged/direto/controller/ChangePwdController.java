package br.org.ged.direto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.org.ged.direto.model.service.security.CustomerUserDetailsService;



@Controller
@RequestMapping("/changePwd.html")
public class ChangePwdController {
	
	@Autowired
	private CustomerUserDetailsService userDetailsService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String showChangePasswordPage() {		
		return "changePwd";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String submitChangePasswordPage(@RequestParam("password") String newPassword) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = principal.toString();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		}
		
		userDetailsService.changePassword(username, newPassword);
		//SecurityContextHolder.clearContext(); //Efetua logout apos mudanca de senha
		
		return "redirect:principal.html";
	}

}
