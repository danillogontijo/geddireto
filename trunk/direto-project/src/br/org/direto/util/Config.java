package br.org.direto.util;

public class Config {
	
	public String baseDir;
	public String tempDir;
	public String certificatesDir;
	public int fileMaxSize;
	public int pageSizeLimit = 15;
	
	public Config(String baseDir, String tempDir, String certificatesDir, int fileMaxSize) {
		super();
		this.baseDir = baseDir;
		this.tempDir = tempDir;
		this.certificatesDir = certificatesDir;
		this.fileMaxSize = fileMaxSize*1024*1024;
	}
	
	

}
