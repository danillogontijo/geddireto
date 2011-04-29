package br.org.ged.direto.model.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.cert.Certificate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.FileInputStream;

import org.springframework.stereotype.Service;

import br.org.ged.direto.model.service.SegurancaService;

import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import java.security.*;

@Service("segurancaService")
public class SegurancaServiceImpl implements SegurancaService {

	private static final String signatureAlgorithm = "MD5withRSA";	
	
	@Override
	public String md5(File arquivo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String md5(String texto) {
		String md5 = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
		md5 = hash.toString(16);			
		return md5;
	}

	@Override
	public String sh1withRSA(File arquivo) throws FileNotFoundException,IOException {
		
		byte fileContent[] = getBytesFromFile(arquivo);
		
		MessageDigest sha1 = null;
		try {
			sha1 = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, sha1.digest(fileContent));
		String sen = hash.toString(16);			
		
		return sen;
	}

	@Override
	public String sh1withRSA(String texto) {
		String sh1 = "";
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1withRSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
		sh1 = hash.toString(16);			
		return sh1;

	}
	
	
	
	public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
        
        System.out.println(length);
    
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

	/**
	 * Extrai a chave pública do arquivo.
	 */
	public PrivateKey getPrivateKeyFromFile( File cert, String alias, String password ) throws Exception {
		//System.out.println(KeyStore.getDefaultType ());
		KeyStore ks = KeyStore.getInstance ("PKCS12");
		char[] pwd = password.toCharArray();
		InputStream is = new FileInputStream( cert );
		ks.load( is, pwd );
		is.close();
		Key key = ks.getKey( alias, pwd );
		if( key instanceof PrivateKey ) {
			return (PrivateKey) key;
		}
		return null;
	}

	/**
	 * Extrai a chave pública do arquivo.
	 */
	public PublicKey getPublicKeyFromFile( File cert, String alias, String password ) throws Exception {
		KeyStore ks = KeyStore.getInstance ("PKCS12");
		char[] pwd = password.toCharArray();
		InputStream is = new FileInputStream( cert );
		ks.load( is, pwd );
		//Key key = ks.getKey( alias, pwd );
		Certificate c = (Certificate) ks.getCertificate( alias );
		PublicKey p = c.getPublicKey();
		return p;
	}
	
	public PublicKey getChavePublicFromFile(File certificado) throws Exception {
		InputStream is = new FileInputStream(certificado);
		CertificateFactory certF = CertificateFactory.getInstance("X.509"); 
		X509Certificate cert = (X509Certificate)certF.generateCertificate(is); 
		PublicKey puk = cert.getPublicKey(); 
		System.out.println(puk.getAlgorithm());
		return puk;
	}
	
	/**
	 * Retorna a assinatura para o buffer de bytes, usando a chave privada.
	 * @param key PrivateKey
	 * @param buffer Array de bytes a ser assinado.
	 */
	public byte[] createSignature( PrivateKey key, byte[] buffer ) throws Exception {
		Signature sig = Signature.getInstance(signatureAlgorithm);
		sig.initSign(key);
		sig.update(buffer, 0, buffer.length);
		return sig.sign();
	}
	
	public byte[] decripto( PublicKey key, byte[] signed ) throws Exception {
		Signature sig = Signature.getInstance(signatureAlgorithm);
		sig.initVerify(key);
		sig.update(signed, 0, signed.length);
		return sig.sign();
	}

	/**
	 * Verifica a assinatura para o buffer de bytes, usando a chave pública.
	 * @param key PublicKey
	 * @param buffer Array de bytes a ser verficado.
	 * @param sgined Array de bytes assinado (encriptado) a ser verficado.
	 */
	public boolean verifySignature( PublicKey key, byte[] buffer, byte[] signed ) throws Exception {
		Signature sig = Signature.getInstance(signatureAlgorithm);
		sig.initVerify(key);
		sig.update(buffer, 0, buffer.length);
		return sig.verify( signed );
	}
	
	
	/**
	 * Converte um array de byte em uma representação, em String, de seus hexadecimais.
	 */
	public String txt2Hexa(byte[] bytes) {
		if( bytes == null ) return null;
		String hexDigits = "0123456789abcdef";
		StringBuffer sbuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			int j = ((int) bytes[i]) & 0xFF;
			sbuffer.append(hexDigits.charAt(j / 16));
			sbuffer.append(hexDigits.charAt(j % 16));
		}
		return sbuffer.toString();
	}
	
	
	public PublicKey carregaChavePublica (File fPub) throws IOException, ClassNotFoundException {
	        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPub));
	        PublicKey ret = (PublicKey) ois.readObject();
	        ois.close();
	        return ret;
	}

	
}
