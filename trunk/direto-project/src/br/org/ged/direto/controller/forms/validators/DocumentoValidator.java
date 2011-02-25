package br.org.ged.direto.controller.forms.validators;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import br.org.ged.direto.controller.forms.DocumentoForm;

@Component
@RemoteProxy(name="documentoValidatorJS")
public class DocumentoValidator extends FormsValidator {

	@Autowired
	public DocumentoValidator(MessageSource messageSource) {
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
		DocumentoForm docForm = (DocumentoForm)obj;
		validateTipoDocumento(docForm.getTipoDocumento(), errors);
		validateNrDocumento(docForm.getNrDocumento(), errors);
		
	}
	
	public void validateTipoDocumento(String usuLogin, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tipoDocumento",
				"TIPODOCUMENTO_REQUIRED", "Escolha o tipo de documento.");
	}
	
	public void validateNrDocumento(String usuLogin, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nrDocumento",
				"required", "Em branco.");
	}

}
