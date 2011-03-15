package br.org.direto.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Protocolo implements Runnable {

	DocumentoProtocolo docProt;
	
	public void setDocProt(DocumentoProtocolo docProt) {
		this.docProt = docProt;
	}


	@Override
	public void run() {
		
		//DocumentoProtocolo docProt = DocumentoProtocolo.getInstance();
		System.out.println("Iniciando..."+Thread.currentThread().getName()+"-"+docProt.toString());
		docProt.pegaUltimoIdBD();
		//docProt.mostraLista();
		//MostraLista m = new MostraLista();
		//m.mostraLista();
	}

}
