package br.org.ged.direto.model.upload;

import java.text.NumberFormat;

import org.apache.commons.fileupload.ProgressListener;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Component;

/**
 * @author Danillo da S. Gontijo
 * @version $Id$
 *
 */
@Component
@RemoteProxy(name = "UploadProgressListener")
public class UploadProgressListener implements ProgressListener {

	private static long bytesTransferred = 0;
    private static long fileSize = -100;
    private long tenKBRead = -1;
    
    @RemoteMethod
    public String getFileUploadStatus() {
        String per = NumberFormat.getPercentInstance().format((double) bytesTransferred / (double) fileSize);
        //System.out.println(per.substring(0, per.length() - 1));
        return per.substring(0, per.length() - 1);
    }

    public void update(long bytesRead, long contentLength, int items) {
        long tenKB = bytesRead / 10240;
        if (tenKBRead == tenKB)
            return;
        tenKBRead = tenKB;

        bytesTransferred = bytesRead;
        //System.out.println(bytesTransferred);
        if (fileSize != contentLength)
            fileSize = contentLength;
    }
	
	
	/*private static long num100Ks = 0;

	private static long theBytesRead = 0;
	private static long theContentLength = -1;
	private static int whichItem = 0;
	private static int percentDone = 0;
	private static boolean contentLengthKnown = false;

    @RemoteMethod
    public String getFileUploadStatus() {
    	if (theContentLength == -1) {
			return "" + theBytesRead + " of Unknown-Total bytes have been read.";
		} else {
			return "" + theBytesRead + " of " + theContentLength + " bytes have been read (" + percentDone + "% done).";
		}

    }

    public void update(long bytesRead, long contentLength, int items) {
    	if (contentLength > -1) {
			contentLengthKnown = true;
		}
		theBytesRead = bytesRead;
		theContentLength = contentLength;
		whichItem = items;

		long nowNum100Ks = bytesRead / 100000;
		// Only run this code once every 100K
		if (nowNum100Ks > num100Ks) {
			num100Ks = nowNum100Ks;
			if (contentLengthKnown) {
				percentDone = (int) Math.round(100.00 * bytesRead / contentLength);
			}
			System.out.println(getFileUploadStatus());
		}

    }*/
    
    

}