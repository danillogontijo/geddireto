package br.org.direto.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.org.ged.direto.controller.forms.DocumentoForm;

public class Protocolo implements Runnable {

	DocumentoForm formulario;
	
	DocumentoProtocolo docProt;
	
	public void setDocProt(DocumentoProtocolo docProt) {
		this.docProt = docProt;
	}
	
	public void setFormulario(DocumentoForm formulario) {
		this.formulario = formulario;
	}
	
	/*public Protocolo (DocumentoForm formulario){
		this.formulario = formulario;
	}*/


	@Override
	public void run() {
		//DocumentoProtocolo docProt = DocumentoProtocolo.getInstance();
		System.out.println("Iniciando..."+Thread.currentThread().getName()+"-"+docProt.toString());
		docProt.pegaUltimoIdBD(formulario);
		//docProt.mostraLista();
		//MostraLista m = new MostraLista();
		//m.mostraLista();
	}

}
