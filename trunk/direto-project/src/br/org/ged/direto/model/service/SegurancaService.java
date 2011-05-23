package br.org.ged.direto.model.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;


public interface SegurancaService {
	
	public String sh1withRSA(File arquivo) throws FileNotFoundException, IOException;
	public String sh1withRSA(String texto);
	
	public String md5(File arquivo);
	public String md5(String texto);

	/*public PrivateKey getPrivateKeyFromFile( File cert, String alias, String password ) throws Exception;
	public PublicKey getPublicKeyFromFile( File cert, String alias, String password ) throws Exception;
	public PublicKey getChavePublicFromFile(File certificado) throws Exception;
	public byte[] createSignature( PrivateKey key, byte[] buffer ) throws Exception;
	public byte[] decripto( PublicKey key, byte[] signed ) throws Exception;
	public boolean verifySignature( PublicKey key, byte[] buffer, byte[] signed ) throws Exception;*/
	
	public String signFile(String alias, String password, int idAnexo);
	public boolean checkSignature(File fileToCheck, int idAnexo);
	public boolean haveCertificate(int usuIdt);
	public String blockEditDocument(int idAnexo);
	public String releaseDocumentEdition(int idAnexo);
	public boolean encrypt(int idAnexo);
	public boolean decrypt(int idAnexo);
	public String encryptMessage(String message, int idUserTo);
	public String decryptMessage(String hexa, String password, int idDespacho);
	
}
