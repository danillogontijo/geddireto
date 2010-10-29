package br.org.ged.direto.model.entity.exceptions;


public class DocumentNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 30;

    public DocumentNotFoundException(String msg) {
        super(msg);
    }
    
    public DocumentNotFoundException(String msg, Throwable causa) {
        super(msg,causa);
    }

}

