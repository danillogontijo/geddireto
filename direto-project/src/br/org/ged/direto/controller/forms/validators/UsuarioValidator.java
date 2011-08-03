package br.org.ged.direto.controller.forms.validators;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import br.org.ged.direto.controller.forms.UsuarioForm;
import br.org.ged.direto.model.service.UsuarioService;

@Component
@RemoteProxy(name="usuarioValidatorJS")
public class UsuarioValidator extends FormsValidator {

	//@Autowired
	//private UsuarioService usuarioService;

	@Autowired
	public UsuarioValidator(MessageSource messageSource){
		super(messageSource);
	}

	@Override
	@RemoteMethod
	public String getMessageValidator(String className, String formInputId, String formInputValue) {
		super.setClassName(className);
		return super.getInputFieldValidationMessage(formInputId, formInputValue);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		UsuarioForm usuForm = (UsuarioForm) obj;

		validateUsuNome(usuForm.getUsuNome(), errors);
		validateUsuNGuerra(usuForm.getUsuNGuerra(), errors);
		validateUsuIdt(usuForm.getUsuIdt(), errors);
		validateRepeatedPassword(usuForm.getRepeatedPassword(), errors);
	}
	
	public void validateUsuNome(String usuNome, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuNome",
				"required");
	}
	
	public void validateUsuNGuerra(String usuNGuerra, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuNGuerra",
				"required");
	}
	
	public void validateUsuIdt(String usuIdt, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "usuIdt",
				"required");
	}
	
	public void validateRepeatedPassword(String usuSenhaConcat, Errors errors) {
		
		String senha[] = usuSenhaConcat.split("\\_");
		
		System.out.println(usuSenhaConcat);
		
		if(senha.length > 1)
			if(!senha[0].equals(senha[1]))
				errors.rejectValue("repeatedPassword", "PASSWORD_NOT_MATCH");
	}

}
