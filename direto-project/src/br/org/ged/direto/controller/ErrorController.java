package br.org.ged.direto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error.html")
public class ErrorController {

	
	@RequestMapping(method=RequestMethod.GET)
	public String showChangePasswordPage() {		
		return "error";
	}
	
}
