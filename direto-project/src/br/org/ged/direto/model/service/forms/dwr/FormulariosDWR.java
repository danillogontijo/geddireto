package br.org.ged.direto.model.service.forms.dwr;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.org.ged.direto.controller.forms.DocumentoForm;

@Component
@RemoteProxy(name = "formulariosJS")
public class FormulariosDWR {

	@Autowired
	private DocumentoForm documentoForm;

	@RemoteMethod
	public DocumentoForm getDocumentoForm() {
		return documentoForm;
	}
	
	
}
