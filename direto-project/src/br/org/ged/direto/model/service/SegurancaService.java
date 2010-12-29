package br.org.ged.direto.model.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface SegurancaService {
	
	public String sh1withRSA(File arquivo) throws FileNotFoundException, IOException;
	public String sh1withRSA(String texto);
	
	public String md5(File arquivo);
	public String md5(String texto);

}
