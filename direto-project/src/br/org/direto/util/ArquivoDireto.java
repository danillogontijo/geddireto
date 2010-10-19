package br.org.direto.util;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFileInputStream;

public class ArquivoDireto extends Thread {
	
	public void getFile(){
		
		File file;
		//FileInputStream in = null;
		//FileOutputStream out;
		
		try {
			
			file = new File("/home/danillo/teste.odt");
			
			byte[] buffer = getBytesFromFile(file);
			
			System.out.println(buffer.length);
							
			//in = new FileInputStream(file);
			//out = new FileOutputStream(file);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*finally
	    {
	        if(in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }*/
		
		
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
	
	public static void main(String[] args) throws Exception {
		
		/*ArquivoDireto app = new ArquivoDireto();
		app.getFile();
		*/
		System.out.println("Random");
		
		    /*RandomAccessFile f = new RandomAccessFile("/home/danillo/teste.odt", "rw");
		    FileChannel fc = f.getChannel();
		    MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, f.length());

		    int len = (int) f.length();
		    for (int i = 0, j = len - 1; i < j; i++, j--) {
		      byte b = mbb.get(i);
		      mbb.put(i, mbb.get(j));
		      mbb.put(j, b);
		    }*/
		    
		    File file = new File("/home/danillo/teste.odt");
		    
		    
		    //InputStream is = new FileInputStream("/home/danillo/teste.odt");
		    
		    Process p = Runtime.getRuntime().exec("/usr/bin/openoffice.org -writer");
	
		    //p.getOutputStream();
		    //p.getInputStream();
		    
		    byte[] b = getBytesFromFile(file);
		    
		    //System.out.println(b.length);
		    
		    //ProcessBuilder pb = new ProcessBuilder("/usr/bin/openoffice.org /home/danillo/teste.odt");
		    //Process p = pb.start();
		    
		    OutputStream pos = p.getOutputStream();
		    
		    //InputStream fis = new FileInputStream(file);
		    pos.write(b);
		    pos.flush();
		    
InputStream is = p.getInputStream();
		    
		    //System.out.println(pos.);
		    
		    
		    
		    
			   
		    //is.read(b);
		    
		    /*OutputStream os = p.getOutputStream();
		    os.write(b);
		    os.flush();*/
		    
		   
		    
		    //os.

		    //Runtime.getRuntime().
		    //Runtime.getRuntime().exec("/usr/bin/openoffice.org /home/danillo/teste.odt");
		    //f.seek(2);
		    
		    /*ProcessBuilder pb = new ProcessBuilder("/usr/bin/openoffice.org");
		    Process p = pb.start();
		    //p = pb.start();
		    p.
		    */
		    
		    //System.out.println(f.readLine());
		    
		    //fc.close();
		  }
		
	


}
