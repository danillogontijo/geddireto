package br.org.ged.direto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.org.ged.direto.controller.forms.LoginForm;

@Controller
@RequestMapping("/login.html")
@SessionAttributes("login")
public class LoginFormController {
	
	private LoginForm loginForm;
	
	@Autowired
	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showLoginForm(ModelMap model) {
		
		model.addAttribute(loginForm);
		
		return "login";
	}

}
