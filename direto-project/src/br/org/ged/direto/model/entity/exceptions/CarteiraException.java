package br.org.ged.direto.model.entity.exceptions;

public class CarteiraException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CarteiraException(String msg) {
        super(msg);
    }
    
    public CarteiraException(String msg, Throwable causa) {
        super(msg,causa);
    }

}
