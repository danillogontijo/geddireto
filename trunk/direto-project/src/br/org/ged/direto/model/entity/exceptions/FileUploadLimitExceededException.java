package br.org.ged.direto.model.entity.exceptions;

public class FileUploadLimitExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3695281240085529534L;
	
	public FileUploadLimitExceededException(String msg) {
        super(msg);
    }
    
    public FileUploadLimitExceededException(String msg, Throwable causa) {
        super(msg,causa);
    }

	public FileUploadLimitExceededException(long maxPostSize, long actualSize) {
		super(String.valueOf(maxPostSize));
	}

}
