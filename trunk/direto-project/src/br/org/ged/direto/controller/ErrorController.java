package br.org.ged.direto.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorController {

	@ExceptionHandler(Exception.class)
	@RequestMapping(method=RequestMethod.GET,value="/error.html")
	public String showError(Exception ex) {		
		ex.printStackTrace();
		
		return "error";
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/accessDenied.html")
	public String accessDenied(ModelMap model, HttpServletRequest request) {
		AccessDeniedException ex = (AccessDeniedException) request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY);
		StringWriter sw = new StringWriter();
		model.addAttribute("errorDetails", ex.getMessage());
		ex.printStackTrace(new PrintWriter(sw));
		model.addAttribute("errorTrace", sw.toString());
		
		return "error";
	}
	
}
