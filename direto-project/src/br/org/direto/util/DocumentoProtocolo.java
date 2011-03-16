package br.org.direto.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.org.ged.direto.controller.forms.DocumentoForm;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.service.DocumentosService;

@RemoteProxy(name = "protocoloJS")
public class DocumentoProtocolo {
	
	//public static DocumentoProtocolo documentoProtocolo;
	public DocumentoDetalhes documento;// = new DocumentoDetalhes();
	
	private DocumentosService documentosService;
	
	private DocumentoForm formulario;
	
	private String sRetorno = "";
	
	
	@RemoteMethod
	public String getsRetorno() {
		return this.sRetorno;
	}
	
	/*@RemoteMethod
	public DocumentoForm getF(){
		DocumentoForm f = new DocumentoForm();
		System.out.println("DocumentoFormNovo: "+f.toString());
		f.setNrDocumento("0000");
		f.setAssinadoPor("assindoPor Teste");
		return f;
	}
	
	@RemoteMethod
	public DocumentoForm setF(DocumentoForm f){
		System.out.println("DocumentoFormSetado: "+f.toString());
		f.setNrDocumento("00001");
		f.setAssinadoPor("mudado");
		return f;
	}*/

	public DocumentoForm getFormulario() {
		return formulario;
	}

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

	public synchronized void pegaUltimoIdBD(DocumentoForm formulario){
		try{
			this.formulario = formulario;
			sRetorno = "";
			
			System.out.println("Formulario: "+this.formulario.toString());
			//Thread.sleep(5000);  
	        System.out.println("Buscando ultimo id para.... "+Thread.currentThread().getName());
	        sRetorno += "Iniciando envio...<br>Aguarde..<br>";
	        //idCount++;
	        //System.out.println("IDCount = "+idCount);
	        //this.lastID = idCount;
	        this.lastID = documentosService.getLastId()+1;
	        System.out.println("IDCount = "+lastID);
	        //Thread.sleep(5000);
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
			documento.setNrProtocolo("201103"+lastID);
			documento.setAssunto(Thread.currentThread().getName());
			documento.setAssinadoPor(formulario.getAssinadoPor());
			documento.setNrDocumento(formulario.getNrDocumento());
			
			this.documento = documento;
			
			DocumentosUtil.listaProtocolo.add(documento);
			Thread.sleep(500);
			gravaNoBanco();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public synchronized void gravaNoBanco(){
		try{
			Thread.sleep(5000);  
			
			sRetorno += ("Gravando documento no Banco de Dados....<br>");
			
	        System.out.println("\nGravando documento no Banco de Dados.... "+Thread.currentThread().getName());
			int index = DocumentosUtil.listaProtocolo.indexOf(documento);
			
			
			//documentosService.saveNewDocumento(DocumentosUtil.listaProtocolo.get(index));
			
			
			/*System.out.println("POSICAO: "+index);
			System.out.println("ID Gravado: " +DocumentosUtil.listaProtocolo.get(index).getIdDocumentoDetalhes());
			System.out.println("Thread Gravada: " +DocumentosUtil.listaProtocolo.get(index).getTipoDocumento());*/
			//mostraLista();
			Thread.sleep(5000);
			sRetorno += ("Finalizado");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
