package br.org.direto.util;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import br.org.ged.direto.model.entity.DocumentoDetalhes;

public class MainTest {
	public static void main(String[] args) {
		
		execute();
		
		
		
		
	}
	
	@SuppressWarnings("static-access")
	public static void execute(){
		
		Protocolo p1 = new Protocolo();
		/*Protocolo p2 = new Protocolo();
		Protocolo p3 = new Protocolo();
		Protocolo p4 = new Protocolo();
		
		Thread t1 = new Thread(p1);
		t1.setName("t1");
		Thread t2 = new Thread(p2);
		t2.setName("t2");
		Thread t3 = new Thread(p3);
		t3.setName("t3");
		Thread t4 = new Thread(p4);
		t4.setName("t4");

		t1.start();
		t3.start();
		t2.start();
		t4.start();*/
		
		Thread[] t = new Thread[15];
		
		/*for (int i=0;i<t.length;i++){
			t[i] = new Thread(p1);
			t[i].setName("t"+(i+1));
			t[i].start();
		}*/
		
		List<Integer> random = new ArrayList<Integer>();
	    for (int i = 0; i < t.length; ++i) {
	        random.add(i);
	    }
	    Collections.shuffle(random);
	    
	    // Imprimindo as primeiras 4 cartas que foram embaralhadas...
	    for (int i = 0; i < t.length; ++i) {
	        //System.out.println (random.get (i));
	    	int k = random.get(i);
	        
	        t[k] = new Thread(p1);
			t[k].setName("t"+(k+1));
			t[k].start();
	        
	    }
		
	    Thread t3 = new Thread(p1);
	    t3.setName("tULTIMA");
	    t3.start();
		
			
	}

}

class MostraLista implements Runnable{
	
	
	public void mostraLista(){
		System.out.println(DocumentosUtil.listaProtocolo.size());
		
		Iterator<DocumentoDetalhes> ite = DocumentosUtil.listaProtocolo.iterator();
		
		int c = 0;
		while (ite.hasNext()){
			
			System.out.println("INDEX: "+c+" - "+ite.next().getIdDocumentoDetalhes());
			c++;
		}
	}

	@Override
	public void run() {
		mostraLista();
		
	}
	

}
