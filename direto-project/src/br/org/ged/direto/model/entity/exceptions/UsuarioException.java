package br.org.ged.direto.model.entity.exceptions;

public class UsuarioException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8088048962910484383L;
	
	public UsuarioException(String msg) {
        super(msg);
    }
    
    public UsuarioException(String msg, Throwable causa) {
        super(msg,causa);
    }

}
