package br.org.direto.util.tree;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;


public class BinarySearchTree {

	protected SimpleBTNode root;

	protected int size;
	
	protected GregorianCalendar cal = new GregorianCalendar();
	
	protected static int timeMilliDay = 1000 * 60 * 60 * 24;

	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	public void insert(Comparable e) {
		SimpleBTNode newNode = new SimpleBTNode(null,e,null);
		if (size == 0) {
			root = newNode;
		} else {
			SimpleBTNode parent = null;
			SimpleBTNode aux = root;
			
			parent = aux;
			int comp = newNode.compareTo(aux);
			
			if (newNode.compareTo(aux) == 0)
				return;
				
				if (newNode.getElement() instanceof Date){
					cal.setTimeInMillis(((Date)newNode.getElement()).getTime());
					if((cal.get(GregorianCalendar.DAY_OF_MONTH)%2) == 0){
						if (root.getRight() == null){
							parent.setRight(newNode);
							size++;
							return;
						}else{
							aux = aux.getRight();
							while (aux != null) {
								parent = aux;
								if (newNode.compareTo(aux) > 0)
									aux = aux.getRight();
								else if (newNode.compareTo(aux) < 0)
									aux = aux.getLeft();
								else
									return;
							}
						}
					}else{
						if (root.getLeft() == null){
							parent.setLeft(newNode);
							size++;
							return;
						}else{
							aux = aux.getLeft();
							while (aux != null) {
								parent = aux;
								if (newNode.compareTo(aux) > 0)
									aux = aux.getRight();
								else if (newNode.compareTo(aux) < 0)
									aux = aux.getLeft();
								else
									return;
							}
						}
					}
				}
			
			if (newNode.compareTo(parent) > 0)
				parent.setRight(newNode);
			else
				parent.setLeft(newNode);
		}
		size++;
	}


	public static void main(String[] args) {
		
		Tempo t;
		Tipo tipo;
		GregorianCalendar gCal = new GregorianCalendar();
		
		try {
			
			String dtI = "28/11/1110";
			String dtF = "30/11/2010";
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					
			Date dataI;
			Date dataF;
			
			dataI = new Date(format.parse(dtI).getTime());
			dataF = new Date(format.parse(dtF).getTime());
			
			tipo = new Tipo("Curso Comandos");
			t = new Tempo(dataI,dataF,tipo);
			
			System.out.println(t.getIntervalo());
			
			gCal.setTimeInMillis(dataI.getTime());
			
			BinarySearchTree a = new BinarySearchTree();
			gCal.add(GregorianCalendar.DATE, -1);
			for (int i=0;i<t.getIntervalo();i++){
				gCal.add(GregorianCalendar.DATE, 1);
				gCal.set(GregorianCalendar.HOUR, 0);
				//long milliDatePerDay = dataI.getTime() + (timeMilliDay*i); 
				Date datePerDay = gCal.getTime();
				//System.out.println(datePerDay+" | "+(i+1));
				a.insert(datePerDay);
				
				
			}
			
			
			
			
			
			
			dtI = "26/11/1810";
			dtF = "03/12/2010";
			dataI = new Date(format.parse(dtI).getTime());
			dataF = new Date(format.parse(dtF).getTime());
			tipo.setNomeTipo("Curso FE");
			t.setDataFinal(dataF);
			t.setDataInical(dataI);
			t.setTipo(tipo);
			
			gCal.setTimeInMillis(dataI.getTime());
			System.out.println("\n");
			
			gCal.add(GregorianCalendar.DATE, -1);
			for (int i=0;i<t.getIntervalo();i++){
				gCal.add(GregorianCalendar.DATE, 1);
				gCal.set(GregorianCalendar.HOUR, 0);
				Date datePerDay = gCal.getTime();
				//System.out.println(datePerDay+" | "+(i+1));
				a.insert(datePerDay);
			}
			
						
			
			System.out.println(a.size);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
		
	
}