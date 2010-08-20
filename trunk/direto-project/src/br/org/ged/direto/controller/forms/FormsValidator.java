package br.org.ged.direto.controller.forms;

import java.lang.reflect.Method;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import org.springframework.util.StringUtils;

public abstract class FormsValidator implements Validator {
	
	private MessageSource messageSource;
	
	public FormsValidator(MessageSource messageSource){
		this.messageSource = messageSource;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		//return clazz.equals(Usuario.class);
		return LoginForm.class.isAssignableFrom(clazz);
	}
	
	@Override
	public abstract void validate(Object obj, Errors errors);
	
	public String getInputFieldValidationMessage(String formInputId, String formInputValue) {

		String validationMessage = "";

		try {
			Object formBackingObject = new LoginForm();
			Errors errors = new BindException(formBackingObject, "command"); 

			String capitalizedFormInputId = StringUtils.capitalize(formInputId);

			String accountMethodName = "set" + capitalizedFormInputId;
			Class<?> setterArgs[] = new Class[] { String.class };
			Method accountMethod = formBackingObject.getClass().getMethod(accountMethodName, setterArgs);
			accountMethod.invoke(formBackingObject,	new Object[] { formInputValue });

			String validationMethodName = "validate" + capitalizedFormInputId;
			Class<?> validationArgs[] = new Class[] { String.class, Errors.class };
			Method validationMethod = getClass().getMethod(validationMethodName, validationArgs);
			validationMethod.invoke(this, new Object[] { formInputValue, errors });
			
			validationMessage = getValidationMessage(errors, formInputId);
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("New code exception: " + e);
		}

		return validationMessage;
	}

	protected String getValidationMessage(Errors errors, String fieldName) {
		String message = "";

		FieldError fieldError = errors.getFieldError(fieldName);

		if (fieldError != null) 
			message = messageSource.getMessage(fieldError.getCode(), null, "Este campo é inválido.", Locale.ROOT);

		return message;
	}
	
	public abstract String getMessageValidator(String formInputId, String formInputValue);

}
