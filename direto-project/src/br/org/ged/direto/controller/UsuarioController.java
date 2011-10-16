package br.org.ged.direto.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.org.ged.direto.controller.forms.UsuarioForm;
import br.org.ged.direto.controller.forms.validators.UsuarioValidator;
import br.org.ged.direto.model.entity.PstGrad;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.PstGradService;


@Controller
public class UsuarioController extends BaseController {

	@Autowired
	private PstGradService pstGradService;
	
	private Validator validator; 
	
	@Autowired
	public void setValidator(UsuarioValidator usuarioValidator) {
		this.validator = usuarioValidator;
	}
	
	@ModelAttribute("pstGradAll")
	public List<PstGrad> gruposCarteira(){
		return pstGradService.getAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/usuario.html")
	public String showUserForm(ModelMap model,HttpServletRequest request) {
		Usuario usuario = usuarioService.selectByLogin(getUsuarioLogado().getUsuLogin());
		
		UsuarioForm usuarioForm = new UsuarioForm();
		usuarioForm.setUsuIdt(String.valueOf(usuario.getUsuIdt()));
		usuarioForm.setUsuNGuerra(usuario.getUsuNGuerra());
		usuarioForm.setUsuNome(usuario.getUsuNome());
		usuarioForm.setUsu_pstGrad(usuario.getPstGrad().getIdPstGrad());
		
		boolean primeiroAcesso = (request.getParameter("login") != null ? true : false );
		model.addAttribute("primeiroAcesso",primeiroAcesso);
		
		if(primeiroAcesso)
			usuarioService.updateLoginDate();
		
		model.addAttribute("usuarioForm",usuarioForm);
			
		return "usuarioForm";
	}

	
	public String onSubmit(@ModelAttribute("usuario") Usuario usuario) throws Exception {
		usuarioService.save(usuario);
		return "redirect:usuario.html";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/usuario.html")
     public String processValidatinForm(@ModelAttribute("usuarioForm") UsuarioForm usuarioForm,
                     BindingResult result, ModelMap model) throws Exception {
             
			validator.validate(usuarioForm, result);
			if(!usuarioForm.getUsuSenha().equals(usuarioForm.getRepeatedPassword()))
				result.addError(new FieldError("UsuarioForm", "usuSenha", "Senhas não conferem."));
		
			if (result.hasErrors()) {
				//model.addAttribute("usuarioForm", usuarioForm);
				return "usuarioForm";
            }
            
			Usuario usuario = usuarioService.selectByLogin(getUsuarioLogado().getUsuLogin());
			usuario.setUsuIdt(Integer.parseInt(usuarioForm.getUsuIdt()));
			usuario.setUsuNGuerra(usuarioForm.getUsuNGuerra());
			usuario.setUsuNome(usuarioForm.getUsuNome());
			
			PstGrad pstGrad = pstGradService.getPstGradById(usuarioForm.getUsu_pstGrad());
			
			usuario.setPstGrad(pstGrad);
			
			usuarioService.save(usuario);
			
			if(usuarioForm.getRepeatedPassword() != "")
				usuarioService.changePassword(usuario.getUsuLogin(),usuarioForm.getUsuSenha());
			
			model.put("usuarioForm", usuarioForm);
           
            return "redirect:usuario.html";
     }

}
