package br.org.direto.util;


import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.ged.direto.controller.forms.DocumentoForm;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.DocumentosService;

@RemoteProxy(name = "protocoloJS")
public class DocumentoProtocolo {
	
	//public static DocumentoProtocolo documentoProtocolo;
	public DocumentoDetalhes documento;// = new DocumentoDetalhes();
	
	private DocumentosService documentosService;
	
	private DocumentoForm formulario;
	
	private Map<Integer,String> documentsMessage = new Hashtable<Integer,String>();
	
	private List<Integer> list = new LinkedList<Integer>();
	
	private Integer key;
	
	private String sRetorno;
	
	private int lastID = 0;
	
	private Usuario user;
	
	public static int idCount = 101;
	
	
	@RemoteMethod
	public String getsRetorno() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario userRequesting = (Usuario) auth.getPrincipal();
		int key = userRequesting.getIdUsuario();
		
		System.out.println("getsRetorno: "+key);
		
		if (list.indexOf(key) == 0)
			return sRetorno;
		
		try {
			synchronized (list) {
				list.wait();
			}
		} catch (InterruptedException e) {
			System.out.println("Deu pau!");
			return null;
		}
		
		return list.indexOf(key) + "usuário(s) na fila";
	}
	
	public DocumentoForm getFormulario() {
		return formulario;
	}

	public void setDocumentosService(DocumentosService documentosService) {
		this.documentosService = documentosService;
	}

	public synchronized void start(DocumentoForm formulario, Usuario user){
		try{
			System.out.println("Iniciando envio para o user: "+Thread.currentThread().getName());
			
			this.user = user;
			
			key = this.user.getIdUsuario();
			
			list.add(key);
			
			this.formulario = formulario;
			
			sRetorno = "";
			
			Thread.sleep(10000);
			
			lastIdFromDataBase();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private synchronized void lastIdFromDataBase(){
		try{
			Thread.sleep(500);
			System.out.println("\nFormulario: "+this.formulario.getRemetente());
			System.out.println("Buscando ultimo id para.... "+Thread.currentThread().getName());
	        
			sRetorno += "Iniciando envio...<br />Usuário: "+Thread.currentThread().getName()+" - Aguarde..<br>";
	        //idCount++;
	        //System.out.println("IDCount = "+idCount);
	        //this.lastID = idCount;
	        this.lastID = documentosService.getLastId()+1;
	        System.out.println("IDCount = "+lastID);
	        //Thread.sleep(5000);
	        saveOnList();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void saveOnList(){
		try{
			Thread.sleep(500);  
	        System.out.println("\nGravando documento na lista statica.... "+Thread.currentThread().getName());
	        
	        DocumentoDetalhes documento = new DocumentoDetalhes();
	        
			documento.setIdDocumentoDetalhes(lastID);
			documento.setNrProtocolo("201103"+lastID);
			documento.setAssunto(Thread.currentThread().getName());
			documento.setAssinadoPor(formulario.getAssinadoPor());
			documento.setNrDocumento(formulario.getNrDocumento());
			
			this.documento = documento;
			
			DocumentosUtil.listaProtocolo.add(documento);
			saveOnDataBase();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public synchronized void saveOnDataBase(){
		try{
			Thread.sleep(5000);  
			
			sRetorno += ("Gravando documento no BD: "+Thread.currentThread().getName()+"<br />");
			
	        System.out.println("\nGravando documento no Banco de Dados.... "+Thread.currentThread().getName());
			int index = DocumentosUtil.listaProtocolo.indexOf(documento);
			
			
			//documentosService.saveNewDocumento(DocumentosUtil.listaProtocolo.get(index));
			
			
			/*System.out.println("POSICAO: "+index);
			System.out.println("ID Gravado: " +DocumentosUtil.listaProtocolo.get(index).getIdDocumentoDetalhes());
			System.out.println("Thread Gravada: " +DocumentosUtil.listaProtocolo.get(index).getTipoDocumento());*/
			//mostraLista();
			//Thread.sleep(5000);
			sRetorno += ("Finalizado");
			
			if (list.size() > 1){
			
				synchronized (list) {
					list.remove(0);
					list.notifyAll();
					System.out.println("\nNotificado");
				}
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
}
