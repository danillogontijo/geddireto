package br.org.ged.direto.model.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

import br.org.ged.direto.model.entity.exceptions.FileUploadLimitExceededException;
import br.org.ged.direto.model.upload.UploadProgressListener;

@Service("uploadService")
//@RemoteProxy(name = "uploadJS")
public class UploadServiceImpl {
	
	
	/*private String charset;
	private Map<String,String[]> parameters;
	private Map<String,FileItem> files;
		
	private static long bytesTransferred = 0;
    private static long fileSize = -100;
    private long tenKBRead = -1;
    
    @RemoteMethod
    public String sendFile(FileInputStream arquivo) throws IOException{
    	//InputStream in = new FileInputStream(arquivo);
		//System.out.println("InputStream: "+in.read());
		
    	//System.out.println(arquivo.getHeight());
    	return arquivo.toString();
    	
		//return in.toString();
		
    }

    @RemoteMethod
    public String getFileUploadStatus() {
    	System.out.println("getFileUploadStatus()");
        String per = NumberFormat.getPercentInstance().format(
                        (double) bytesTransferred / (double) fileSize);
        return per.substring(0, per.length() - 1);
    }

    public void update(long bytesRead, long contentLength, int items) {
        long tenKB = bytesRead / 10240;
        if (tenKBRead == tenKB)
            return;
        tenKBRead = tenKB;

        bytesTransferred = bytesRead;
        if (fileSize != contentLength)
            fileSize = contentLength;
    }
	
	@SuppressWarnings("unchecked")
	public void build(HttpServletRequest request, File tempDir, long maxPostSize) throws IOException, FileUploadLimitExceededException {
        try {
            this.charset = request.getCharacterEncoding();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(tempDir);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(maxPostSize);
           
            // Adding the listener
            upload.setProgressListener(new UploadProgressListener());
            List<FileItem> items = upload.parseRequest(request);
            Map<String, List<String>> params = new HashMap<String, List<String>>();

            for (FileItem item : items) {
                // If it's a form field, add the string value to the list
                if (item.isFormField()) {
                    List<String> values = params.get(item.getFieldName());
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(item.getFieldName(), values);
                    }
                    values.add(charset == null ? item.getString() : item.getString(charset));
                }
                // Else store the file param
                else {
                    files.put(item.getFieldName(), item);
                }
            }

            // Now convert them down into the usual map of String->String[]
            for (Map.Entry<String, List<String>> entry : params.entrySet()) {
                List<String> values = entry.getValue();
                this.parameters.put(entry.getKey(), values.toArray(new String[values.size()]));
            }
        } catch (FileUploadBase.SizeLimitExceededException slee) {
            throw new FileUploadLimitExceededException(maxPostSize, slee.getActualSize());
        	
        	
        } catch (FileUploadException fue) {
            IOException ioe = new IOException("Could not parse and cache file upload data.");
            ioe.initCause(fue);
            throw ioe;
        }
    }
*/
}
