package br.org.ged.direto.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/index.html")
public class PrincipalAdminController {

	@RequestMapping(method = RequestMethod.GET)
	public String showAdmin(){
		return "admin/index";
	}
}
