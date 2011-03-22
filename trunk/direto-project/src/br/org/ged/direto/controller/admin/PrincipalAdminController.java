package br.org.ged.direto.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PrincipalAdminController {

	@RequestMapping(method = RequestMethod.GET,value="/admin/index.html")
	public String showAdmin(){
		return "admin/index";
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/admin/blank.html")
	public void blank(){
		
	}
}
