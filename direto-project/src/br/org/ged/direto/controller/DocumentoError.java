package br.org.ged.direto.controller;

import org.springframework.stereotype.Component;

import br.org.ged.direto.model.entity.DocumentoDetalhes;

@Component
public class DocumentoError {
	
	private DocumentoDetalhes documento;

	public DocumentoDetalhes getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoDetalhes documento) {
		this.documento = documento;
	}
	
	

}
