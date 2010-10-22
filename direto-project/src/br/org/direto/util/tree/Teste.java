package br.org.direto.util.tree;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Tempo t;
		Tipo tipo;
		
		try {
			
			String dtI = "07/05/2010";
			String dtF = "08/05/2010";
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
					
			Date dataI;
			Date dataF;
			
			dataI = new Date(format.parse(dtI).getTime());
			dataF = new Date(format.parse(dtF).getTime());
			
			tipo = new Tipo("Curso Comandos");
			t = new Tempo(dataI,dataF,tipo);
			
			System.out.println(t.getIntervalo());
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
