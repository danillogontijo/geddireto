package br.org.direto.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeUtil {
	
	public static DateFormat BRAZIL_FORMAT_DATA = new SimpleDateFormat("dd/mm/yyyy"); 
	public static DateFormat BRAZIL_FORMAT_DATAHORA = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 

	public static String getBrazilFormatDataHora(Date date){
		return BRAZIL_FORMAT_DATAHORA.format(date);
	}
	
	public static String getBrazilFormatData(Date date){
		return BRAZIL_FORMAT_DATA.format(date);
	}
	
}
