package br.org.direto.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.service.DocumentosService;

public class DocumentoProtocolo {
	
	//public static DocumentoProtocolo documentoProtocolo;
	public DocumentoDetalhes documento;// = new DocumentoDetalhes();
	
	private DocumentosService documentosService;
	
	public void setDocumentosService(DocumentosService documentosService) {
		this.documentosService = documentosService;
	}

	private int lastID = 0;
	
	public static int idCount = 101;
	
	public static DocumentoProtocolo getInstance(){
		//if(documentoProtocolo == null)
	//		documentoProtocolo = new DocumentoProtocolo(); 
		
		//return documentoProtocolo;
		return new DocumentoProtocolo();
	}

	public synchronized void pegaUltimoIdBD(){
		try{
			//Thread.sleep(500);  
	        System.out.println("Buscando ultimo id para.... "+Thread.currentThread().getName());
	        idCount++;
	        //System.out.println("IDCount = "+idCount);
	        //this.lastID = idCount;
	        this.lastID = documentosService.getLastId()+1;
	        System.out.println("IDCount = "+lastID);
	       // Thread.sleep(500);
	        gravaNaLista();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void gravaNaLista(){
		try{
			//Thread.sleep(500);  
	        System.out.println("Gravando documento na lista statica.... "+Thread.currentThread().getName());
	        
	        DocumentoDetalhes documento = new DocumentoDetalhes();
	        
			documento.setIdDocumentoDetalhes(lastID);
			documento.setAssunto(Thread.currentThread().getName());
			this.documento = documento;
			
			DocumentosUtil.listaProtocolo.add(documento);
			//Thread.sleep(500);
			gravaNoBanco();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public synchronized void gravaNoBanco(){
		try{
			//Thread.sleep(500);  
	        System.out.println("\nGravando documento no Banco de Dados.... "+Thread.currentThread().getName());
			int index = DocumentosUtil.listaProtocolo.indexOf(documento);
			documentosService.saveNewDocumento(DocumentosUtil.listaProtocolo.get(index));
			/*System.out.println("POSICAO: "+index);
			System.out.println("ID Gravado: " +DocumentosUtil.listaProtocolo.get(index).getIdDocumentoDetalhes());
			System.out.println("Thread Gravada: " +DocumentosUtil.listaProtocolo.get(index).getTipoDocumento());*/
			//mostraLista();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void mostraLista(){
		
		Iterator<DocumentoDetalhes> ite = DocumentosUtil.listaProtocolo.iterator();
		
		int c = 0;
		while (ite.hasNext()){
			
			System.out.println("INDEX: "+c+" - "+ite.next().getIdDocumentoDetalhes());
			c++;
		}
	}
		
	

}
