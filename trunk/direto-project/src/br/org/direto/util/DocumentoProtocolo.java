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
	
	private List<Usuario> userList = new LinkedList<Usuario>();
	
	private String sRetorno;
	
	private int lastID = 0;
	
	private Usuario user;
	
	public static int idCount = 101;
	
	
	
	
	@RemoteMethod
	public String getsRetorno() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario userRequesting = (Usuario) auth.getPrincipal();
		
		System.out.println("getsRetorno: "+userRequesting.getIdUsuario());
		System.out.println(userList.indexOf(userRequesting) + " usuário(s) na fila");
		
		try {
			synchronized (user) {
				user.wait();
			}
		} catch (InterruptedException e) {
			System.out.println("Deu pau!");
			return null;
		}
		
		if (user == userRequesting)
			return sRetorno;
		
		int userNumberInList = userList.indexOf(userRequesting);
		
		if (userNumberInList == -1){
			return "Finalizado";
		}else if (userNumberInList == 0) {
			return "Você é o próximo, aguarde...";
		}else{
			return (userNumberInList+1) + " usuário(s) na fila";
		}
		
	}
	
	public DocumentoForm getFormulario() {
		return formulario;
	}

	public void setDocumentosService(DocumentosService documentosService) {
		this.documentosService = documentosService;
	}

	public void start(DocumentoForm formulario, Usuario user){
		try{
			System.out.println("\nSTART - Iniciando envio para o user: "+Thread.currentThread().getName());
			userList.add(user);
			lastIdFromDataBase(formulario);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private synchronized void lastIdFromDataBase(DocumentoForm formulario){
		try{
			this.user = userList.get(0);
			this.formulario = formulario;
			sRetorno = "";
			
			System.out.println("\nLAST_ID_FROM_Formulario: "+this.formulario.getRemetente());
			System.out.println("Buscando ultimo id para.... "+Thread.currentThread().getName());
	        
			
			synchronized (user) {
				sRetorno += "Iniciando envio...<br />Usuário: "+Thread.currentThread().getName()+" - Aguarde..<br>";
				user.notifyAll();
			}
			
			//Thread.sleep(5000);
			
	  		this.lastID = documentosService.getLastId()+1;
	        System.out.println("IDCount = "+lastID);
	        
	        //Thread.sleep(10000);
	        saveOnList();
	        wait();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void saveOnList(){
		try{
			Thread.sleep(500);  
	        System.out.println("\nSAVE_ON_LIST_Gravando documento na lista statica.... "+Thread.currentThread().getName());
	        
	        DocumentoDetalhes documento = new DocumentoDetalhes();
	        
			documento.setIdDocumentoDetalhes(lastID);
			documento.setNrProtocolo("201103"+lastID);
			documento.setAssunto(Thread.currentThread().getName());
			documento.setNrDocumento(formulario.getNrDocumento());
			
			this.documento = documento;
			
			DocumentosUtil.listaProtocolo.add(documento);
			saveOnDataBase();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private synchronized void saveOnDataBase(){
		try{
			//Thread.sleep(5000);  
			
			System.out.println("\nSAVE_ON_DATA_Gravando documento no Banco de Dados.... "+Thread.currentThread().getName());
			int index = DocumentosUtil.listaProtocolo.indexOf(documento);
			
			
			//documentosService.saveNewDocumento(DocumentosUtil.listaProtocolo.get(index));
			
			
			/*System.out.println("POSICAO: "+index);
			System.out.println("ID Gravado: " +DocumentosUtil.listaProtocolo.get(index).getIdDocumentoDetalhes());
			System.out.println("Thread Gravada: " +DocumentosUtil.listaProtocolo.get(index).getTipoDocumento());*/
			//mostraLista();
			//Thread.sleep(5000);
			
			synchronized (user) {
				sRetorno += ("Gravando documento no BD: "+Thread.currentThread().getName()+"<br />");
				sRetorno += ("Finalizado");
				user.wait(1000);
				user.notifyAll();
				userList.remove(0);
				notifyAll();
				System.out.println("\nNotificado\n");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
}
