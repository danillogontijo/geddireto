package br.org.direto.util;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.io.*;

import javax.servlet.ServletOutputStream;
import javax.swing.*;
 

public class AppletTest extends JApplet implements ActionListener
    {
    
     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton jbutton = null;
     String documento = "";
     
     
     public void init() {
    	 documento = getParameter("documento");
         jbutton = new JButton("Send file "+documento);
         jbutton.addActionListener(this);
         this.getContentPane().add(jbutton);
     }
    
     public void actionPerformed(ActionEvent ae)
         {
         if(ae.getSource() == jbutton)
             {
             try
                 {
                 /*File file = new File("/home/danillo/teste.odt");// arquivo no meu disco
                
                 FileInputStream in = new FileInputStream(file);
                 byte[] buf=new byte[in.available()];
                 int bytesread = 0;
                */
            	 
                 String toservlet = "http://localhost:8080/direto-project/arquivos/teste.odt"; // endereço do meu servlets
                
                 URL servleturl = new URL(toservlet);
                 URLConnection servletconnection = servleturl.openConnection();
                 servletconnection.setDoInput(true);
                 servletconnection.setDoOutput(true);
                 servletconnection.setUseCaches(false);
                 servletconnection.setDefaultUseCaches(false);
                 
                 DataInputStream inputFromClient = new DataInputStream(servletconnection.getInputStream());
                 
                 inputFromClient.readByte();
                 
                 OutputStream fos = new FileOutputStream("/home/danillo/arquivo_carregado.odt");
                 
                 byte[] buf = new byte[1024];
                 int bytesread;
                 while( (bytesread = inputFromClient.read( buf )) > -1 )
                 {
                	 fos.write( buf, 0, bytesread );
                 }
                 
                 //File file = new File("");
                 //file.
                 
                 //FileInputStream fis = new File
                
                 //DataOutputStream out = new DataOutputStream(servletconnection.getOutputStream());
                
                 /*while( (bytesread = in.read( buf )) > -1 )
                     {
                     out.write( buf, 0, bytesread );
                 }*/
                
                /* out.flush();
                 out.close();
                 in.close();
                
                 DataInputStream inputFromClient = new DataInputStream(servletconnection.getInputStream());
                */ 
                 inputFromClient.close();
             }
             catch(Exception e)
                 {
                 e.printStackTrace();
             }
            
            
         }
     }
     
    }
