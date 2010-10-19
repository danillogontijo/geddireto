package br.org.direto.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;

public class MyThread extends Thread {
	private InputStream is;
	private OutputStream os;
	File file;
	ProcessBuilder pb = new ProcessBuilder("/usr/bin/openoffice.org");

	MyThread(String name, InputStream is, OutputStream os, File file)
			throws IOException {
		super(name);

		this.is = is;
		this.os = os;
		this.file = file;
	}

	public void run() {
		try {
			String line;
			Process p = pb.start();
			//byte[] b = ArquivoDireto.getBytesFromFile(file);
			BufferedReader input =
			 	new BufferedReader
			 	(new InputStreamReader(p.getInputStream()));
			 	while ((line = input.readLine()) != null) {
			 		System.out.println(line);
			 	}
			 	input.close();

		} catch (IOException e) {
		}
	}
}

class StreamThreads {
	public static void main(String[] args) throws IOException {
		File file = new File("/home/danillo/teste.odt");

		InputStream is = new FileInputStream(file);
		OutputStream os = new FileOutputStream("/home/danillo/teste2.odt");

		MyThread mt1 = new MyThread("src", is, os, file);
		
		mt1.start();

	}
}