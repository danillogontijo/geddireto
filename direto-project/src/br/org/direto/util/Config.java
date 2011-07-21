package br.org.direto.util;

public class Config {
	
	public String baseDir = "/home/direto/work_direto";
	public String tempDir = "/home/direto/work_direto/temp";
	public String certificatesDir = "/home/direto/work_direto/certificados";
	public int fileMaxSize = 100;
	public int pageSizeLimit = 15;
	
	public Config(String baseDir, String tempDir, String certificatesDir, int fileMaxSize) {
		super();
		this.baseDir = baseDir;
		this.tempDir = tempDir;
		this.certificatesDir = certificatesDir;
		this.fileMaxSize = fileMaxSize*1024*1024;
	}
	
	public Config(){
		
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public String getCertificatesDir() {
		return certificatesDir;
	}

	public void setCertificatesDir(String certificatesDir) {
		this.certificatesDir = certificatesDir;
	}

	public int getFileMaxSize() {
		return fileMaxSize;
	}

	public void setFileMaxSize(int fileMaxSize) {
		this.fileMaxSize = fileMaxSize;
	}

	public int getPageSizeLimit() {
		return pageSizeLimit;
	}

	public void setPageSizeLimit(int pageSizeLimit) {
		this.pageSizeLimit = pageSizeLimit;
	}
	
	
	
	

}
