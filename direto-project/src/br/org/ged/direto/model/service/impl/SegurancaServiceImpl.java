package br.org.ged.direto.model.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.cert.Certificate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileInputStream;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.org.direto.util.Base64Utils;
import br.org.direto.util.Config;
import br.org.ged.direto.model.entity.Anexo;
import br.org.ged.direto.model.entity.Usuario;
import br.org.ged.direto.model.service.AnexoService;
import br.org.ged.direto.model.service.SegurancaService;
import br.org.ged.direto.model.service.UsuarioService;

import java.security.*;

@Service("segurancaService")
@RemoteProxy(name = "segurancaJS")
public class SegurancaServiceImpl implements SegurancaService {

	private static final String signatureAlgorithm = "MD5withRSA";	
	private final File jks = new File("/home/danillo/springsource/tc-server-6.0.20.C/truststore.jks");
	private final String pwd = "ZDE0M3Qw";
	
	@Autowired
	private AnexoService anexoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private Config config;
	
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
		System.out.println(alias);
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
		KeyStore ks = KeyStore.getInstance (KeyStore.getDefaultType());
		char[] pwd = password.toCharArray();
		InputStream is = new FileInputStream( cert );
		ks.load( is, pwd );
		//Key key = ks.getKey( alias, pwd );
		Certificate c = (Certificate) ks.getCertificate( alias );
		PublicKey p = c.getPublicKey();
		return p;
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
	
	
	@Override
	@RemoteMethod
	public String signFile(String file, String alias, String password, int idAnexo){
		try{
			System.out.println(config.baseDir);
			Anexo anexoToSign = anexoService.selectById(idAnexo);
			if (anexoToSign.getAssinado() == 1)
				return "Arquivo já assinado.";
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Usuario signer = (Usuario)auth.getPrincipal();
			
			File certificado = new File(config.certificatesDir+formatFileName(signer.getUsuIdt())+".p12");
			
			File fileToSing = new File(config.baseDir+"sgt.danillo/"+file);
			FileInputStream fis = new FileInputStream(fileToSing);
			byte fileContent[] = new byte[(int)fileToSing.length()];
			fis.read(fileContent);
		
			PrivateKey privateKey = getPrivateKeyFromFile( certificado, alias, password );
									
			byte[] fileSigned = createSignature(privateKey, fileContent);
			String fileSignedHexa = txt2Hexa(fileSigned);
			
			System.out.println(fileSignedHexa);
			
			anexoToSign.setAssinado(1);
			anexoToSign.setAssinadoPor(signer.getUsuLogin());
			anexoToSign.setIdAssinadoPor(signer.getIdUsuario());
			anexoToSign.setAssinaturaHash(fileSignedHexa);
			
			anexoService.saveAnexo(anexoToSign);
			
			return "Documento assinado com sucesso!";
		
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.err.println("Arquivo nao encontrado.");
			return "Certificado digital nao encontrado!";
		} catch (IOException e) {
			e.printStackTrace();
			return "Erro de I/O!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Não foi possível assinar o documento!";
		}
		
		
		
	}

	@Override
	@RemoteMethod
	public boolean checkSignature(File fileToCheck, int idAnexo) {
		try{
			Anexo anexo = anexoService.selectById(idAnexo);
			if (anexo.getAssinado() == 0)
				return false;
			
			Usuario signerUser =  usuarioService.selectById(anexo.getIdAssinadoPor());
			byte[] hash = new BigInteger(anexo.getAssinaturaHash(), 16).toByteArray();
			
			byte pwdDecripto[] = Base64Utils.decode(pwd.getBytes());
	          
			FileInputStream fis = new FileInputStream(fileToCheck);
			byte fileContent[] = new byte[(int)fileToCheck.length()];
			fis.read(fileContent);
		
			PublicKey publicKey = getPublicKeyFromFile( jks, formatFileName(signerUser.getUsuIdt()), new String(pwdDecripto) );
			
			if( verifySignature( publicKey, fileContent, hash ) ) {
				System.out.println("Assinatura OK!");
				return true; 
			} else {
				System.out.println("Assinatura NOT OK!");
				return false;
			}
		
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.err.println("Arquivo nao encontrado.");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	public String formatFileName(int idt){
		String fileName = String.valueOf(idt);
		int sizeFileName = 10-fileName.length();
		
		if(sizeFileName != 0){
			String zeros = "";
			for (int i = 0; i<sizeFileName; i++)
				zeros += "0";
			fileName = zeros+fileName;
		}
		System.out.println(fileName);
		return fileName;
	}
	
}
