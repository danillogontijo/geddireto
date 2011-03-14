package br.org.ged.direto.controller.utils;

import java.io.Serializable;

import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Usuario;

public class DocumentoCompleto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4131708349389661416L;
	
	
	private Documento documento;
	private DocumentoDetalhes documentoDetalhes;
	
	public DocumentoCompleto(Documento documento,
			DocumentoDetalhes documentoDetalhes) {
		this.documento = documento;
		this.documentoDetalhes = documentoDetalhes;
	}
	
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public DocumentoDetalhes getDocumentoDetalhes() {
		return documentoDetalhes;
	}
	public void setDocumentoDetalhes(DocumentoDetalhes documentoDetalhes) {
		this.documentoDetalhes = documentoDetalhes;
	}
	
	public int hashCode() {
		return this.documentoDetalhes.getIdDocumentoDetalhes();
	}
	
	public boolean equals(Object obj){
		if (!(obj instanceof DocumentoCompleto) || (obj == null)) {
            return false;
        }
		
		DocumentoCompleto doc = (DocumentoCompleto)obj;
		
		if (this.hashCode() == doc.hashCode())
			return true;
		
		return false;
		
	}
}
