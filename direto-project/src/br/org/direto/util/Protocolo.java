package br.org.direto.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.ged.direto.controller.forms.DocumentoForm;
import br.org.ged.direto.model.entity.Usuario;

public class Protocolo implements Runnable {

	DocumentoForm formulario;
	
	DocumentoProtocolo documentoProtocolo;
	
	Usuario user;
	
	public void setUser(Usuario user) {
		this.user = user;
	}

	public void setDocumentoProtocolo(DocumentoProtocolo documentoProtocolo) {
		this.documentoProtocolo = documentoProtocolo;
	}
	
	public void setFormulario(DocumentoForm formulario) {
		this.formulario = formulario;
	}
	
	@Override
	public void run() {
		System.out.println("Iniciando..."+Thread.currentThread().getName()+"-"+documentoProtocolo.toString());
		documentoProtocolo.start(formulario,user);
	} 

}
