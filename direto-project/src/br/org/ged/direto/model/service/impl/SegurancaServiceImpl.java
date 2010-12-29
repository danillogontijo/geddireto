package br.org.ged.direto.model.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.FileInputStream;

import org.springframework.stereotype.Service;

import br.org.ged.direto.model.service.SegurancaService;

@Service("segurancaService")
public class SegurancaServiceImpl implements SegurancaService {

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
		//FileInputStream fis = new FileInputStream(arquivo);
		
		byte fileContent[] = getBytesFromFile(arquivo);
		
		//fis.read(fileContent);
		
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

}
