package br.org.ged.direto.controller.forms.validators;

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
	private String className;
	
	
	public abstract String getMessageValidator(String className, String formInputId, String formInputValue);
	
	public FormsValidator(MessageSource messageSource){
		this.messageSource = messageSource;
	}

	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	private Class<?> getClassFromName(String className)
    {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("br.org.ged.direto.controller.forms."+className);
        } catch (ClassNotFoundException ex) {
            System.err.println("No class found: " + className);
        }
        
        return clazz;
    }
	
	//public abstract boolean supports(Class<?> clazz);

	@Override
	public boolean supports(Class<?> clazz) {
		String className = this.getClassName();
		Class<?> cl = this.getClassFromName(className);
		return cl.isAssignableFrom(clazz);
	}
	
	@Override
	public abstract void validate(Object obj, Errors errors);
	
	protected String getValidationMessage(Errors errors, String fieldName) {
		String message = "";

		FieldError fieldError = errors.getFieldError(fieldName);

		if (fieldError != null) 
			message = messageSource.getMessage(fieldError.getCode(), null, "Este campo é inválido.", Locale.ROOT);

		return message;
	}
	
	public String getInputFieldValidationMessage(String formInputId, String formInputValue) {

		String validationMessage = "";

		try {
			
			String className = this.getClassName();
		    Class<?> clazz = this.getClassFromName(className);
			
			Object formBackingObject = clazz.newInstance();// new DocumentoForm();
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

}
