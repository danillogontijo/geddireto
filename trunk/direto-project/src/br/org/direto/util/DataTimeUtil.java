package br.org.direto.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeUtil {
	
	private static DateFormat BRAZIL_FORMAT_DATA = new SimpleDateFormat("dd/mm/yyyy"); 
	private static DateFormat BRAZIL_FORMAT_DATAHORA = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
	private static DateFormat US_FORMAT_DATAHORA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getBrazilFormatDataHora(Date date){
		return BRAZIL_FORMAT_DATAHORA.format(date);
	}
	
	public static String getBrazilFormatData(Date date){
		return BRAZIL_FORMAT_DATA.format(date);
	}
	
	public static String convertBrazilFormatToUSFormat(String brazilFormatDateTime) throws ParseException{
		Date dateBrazilFormat = BRAZIL_FORMAT_DATAHORA.parse(brazilFormatDateTime);
		return US_FORMAT_DATAHORA.format(dateBrazilFormat);
	}
	
	public static String getUSFormatDataHora(Date date){
		return US_FORMAT_DATAHORA.format(date);
	}
	
	public static String convertKeyUpFormatBrazilToUS(String brazilFormatDataTime){
		
		int length = brazilFormatDataTime.length();
		String[] aDate = brazilFormatDataTime.split("-");
		String day = "";
		String month = "";
		String year = "";
		
		if (aDate.length == 1){
			return aDate[0];
		}else if (aDate.length == 2){
			
			if (aDate[1].length() == 4)
				return aDate[1]+"-"+aDate[0];
			
			if (aDate[1].length() == 2)
				return aDate[1]+"-"+aDate[0];
			
			return aDate[0];
			
		}
		
		if (length >= 2){
			
			day = brazilFormatDataTime.substring(0, 2);
			if(day.length() == 2)
				day = "-"+day;
			
			if(length > 4 && length < 6){
				if (length > 4){
					month = brazilFormatDataTime.substring(3, 5);
					month = "-"+month;
				} else {
					month = brazilFormatDataTime.substring(3, 4);
				}
			}
			
			if(length >= 7){
				year = brazilFormatDataTime.substring(6, length);
				if(year.length() == 4)
					year += "-";
			}
			
			System.out.println("================"+year+month+day);
			
			return year+month+day;
			
		} else {
			return brazilFormatDataTime;
		}
		
	}

	
}
