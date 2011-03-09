package br.org.direto.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;

import br.org.direto.util.tree.BinarySearchTree;
import br.org.direto.util.tree.SimpleBTNode;
import br.org.ged.direto.model.entity.Documento;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.repository.DocumentosRepository;
import br.org.ged.direto.model.repository.hibernate.DocumentosRepositoryImpl;
import br.org.ged.direto.model.service.DocumentosService;
import br.org.ged.direto.model.service.impl.DocumentosServiceImpl;

@Component
public class DocumentosUtil {
	
	private final int idDocumento;
	private static int size = 0;
	
	public static final int LIMITE_POR_PAGINA = 15;
	
	public static final SortedMap<Integer,DocumentoDetalhes> documentos = new TreeMap<Integer,DocumentoDetalhes>();
	
	//public static final SortedMap<Integer,Integer> protocolos = new TreeMap<Integer,DocumentoDetalhes>();
	
	public static List<Integer> protocolos = new ArrayList<Integer>();
	
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public DocumentosUtil(int id, int v){
		this.idDocumento = id;
		//this.visualizacoes = v;
	}
	
	//@PostAuthorize("returnObject.idCarteira == principal.idCarteira")
	public synchronized static DocumentoDetalhes returnDocument(Integer id, Integer idCarteira, Documento doc) throws DocumentNotFoundException{
		System.out.println("STATIC RETURN DOCUMENT"+id+idCarteira);
		DocumentoDetalhes dd = doc.getDocumentoDetalhes();
				
		/*if (doc != null){
			 dd = DocumentosUtil.documentos.get(id);
			 
			 if (dd == null){
					
					dd = doc.getDocumentoDetalhes();
					
					if (dd != null){
						DocumentosUtil.documentos.put(dd.getIdDocumentoDetalhes(), dd);
						size++;
					}
				}
		}*/
		
		System.out.println(size);
		
		return dd;
		
	}
	
	public static int geraProtocolo(Integer nr){
	
		int protocolo = 0;
		DocumentoDetalhes doc = null;
		
		if (protocolos.size() == 0){
			protocolo = nr;
			protocolo++;
			
			protocolos.add(new Integer(protocolo));
			
			return protocolo;
		}else{
			
			protocolo = protocolos.get(0);
			protocolo = protocolo + protocolos.size();
			protocolos.add(new Integer(protocolo));
			
			return protocolo;
			
		}
		
		
	}

	
	public int hashCode() {
		int code = 0;
		int size = DocumentosUtil.documentos.size();
		
		return code;
	}
	
	//public static BinarySearchTree bst = new BinarySearchTree();
		
	/*public static void add(Integer key, DocumentoDetalhes doc){
		DocumentosUtil.documentos.put(key, doc);
	}*/

}


class CodeDocument{

	int id;
	int visualizacoes;
	
	public CodeDocument(){
		this.id = 0;
		this.visualizacoes = 0;
	}
	
	
}