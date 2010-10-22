package br.org.direto.util;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SortedMap;
import java.util.TreeMap;

import br.org.direto.util.tree.BinarySearchTree;
import br.org.direto.util.tree.SimpleBTNode;
import br.org.ged.direto.model.entity.DocumentoDetalhes;
import br.org.ged.direto.model.entity.Usuario;

public class DocumentosUtil {
	
	public static final int LIMITE_POR_PAGINA = 2;
	
	public static final SortedMap<Integer,DocumentoDetalhes> documentos = new TreeMap<Integer,DocumentoDetalhes>();
	
	//public static BinarySearchTree bst = new BinarySearchTree();
		
	/*public static void add(Integer key, DocumentoDetalhes doc){
		DocumentosUtil.documentos.put(key, doc);
	}*/

}
