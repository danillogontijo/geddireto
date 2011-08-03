package br.org.ged.direto.controller.forms.validators;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import br.org.ged.direto.controller.forms.LoginForm;
import br.org.ged.direto.model.service.UsuarioService;

@Component
@RemoteProxy(name="loginValidatorJS")
public class LoginValidator extends FormsValidator {

	private UsuarioService usuarioService;

	@Autowired
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Autowired
	public LoginValidator(MessageSource messageSource){
		super(messageSource);
	}
	
	/*@Override
	public boolean supports(Class<?> clazz) {
		//return clazz.equals(Usuario.class);
		return LoginForm.class.isAssignableFrom(clazz);
	}*/
	
	@Override
	public void validate(Object obj, Errors errors) {
		
		LoginForm u = (LoginForm) obj;

		validateJ_username(u.getJ_username(), errors);

	}

	public void validateJ_username(String usuLogin, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "j_username",
				"LOGIN_REQUIRED", "Digite um nome de login.");
		
		// Checa se o usuario existe
		if (errors.getAllErrors().size() == 0) {
			boolean existsUser = this.usuarioService.validateUser(usuLogin);
			if (!existsUser)
				errors.rejectValue("j_username", "error.login.USER_NO_EXISTS");
		}
	}

	@Override
	@RemoteMethod
	public String getMessageValidator(String className, String formInputId, String formInputValue) {
		super.setClassName(className);
		return super.getInputFieldValidationMessage(formInputId, formInputValue);
	}
	
	
	
	
	
	
}
