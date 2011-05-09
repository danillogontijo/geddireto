package br.org.ged.direto.controller;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.org.ged.direto.model.entity.UploadItem;
import br.org.ged.direto.model.entity.exceptions.DocumentNotFoundException;
import br.org.ged.direto.model.entity.exceptions.FileUploadLimitExceededException;
import br.org.ged.direto.model.service.SegurancaService;
import br.org.ged.direto.model.service.impl.UploadServiceImpl;
import br.org.ged.direto.model.upload.UploadProgressListener;

@Controller
public class FileUploadController {
	
	//private static Logger logger = Logger.getLogger(ClubUploadController.class.getName());

	private String BASE_USER_DIRECTORY = "/home/danillo/users/";
	//private UploadItem uploadItem;
	private int MAX_MEMO = 999999999;//524288000;
	
	protected HttpSession session;
	
//	private Charset charset = Charset.forName("UTF-8");
	
	private String charset;
	//private Map<String,String[]> parameters;
	//private Map<String,FileItem> files;
	
	@Autowired
	private SegurancaService segurancaService;
	
	
	@RequestMapping(value = "/upload/sucesso.html",method = RequestMethod.GET)
	  public String getUploadForm(Model model)
	  {
	    model.addAttribute("uploadItem",new UploadItem());
	    return "upload/sucess";
	  }
		
	
	
	@SuppressWarnings("unchecked")
	//@RequestMapping(value = "/upload/upload.html", method = RequestMethod.POST)
	public String sendFiles(HttpServletRequest request) throws IOException, FileUploadLimitExceededException, InterruptedException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		File tempDir = new File("/home/danillo/users/"+auth.getName());
		long maxPostSize = MAX_MEMO;
		
		try {
            this.charset = request.getCharacterEncoding();
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(tempDir);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(maxPostSize);
           
            upload.setProgressListener(new UploadProgressListener());
            List<FileItem> items = upload.parseRequest(request);
            Map<String, List<String>> params = new HashMap<String, List<String>>();

            
            for (FileItem item : items) {
             
            	System.out.println("=====Start: "+item.getName());
            	
                if (item.isFormField()) {
                	
                	System.out.println("Uploaded file: "+item.getName());
                	
                    List<String> values = params.get(item.getFieldName());
                    if (values == null) {
                        values = new ArrayList<String>();
                        params.put(item.getFieldName(), values);
                    }
                    values.add(charset == null ? item.getString() : item.getString(charset));
                }else{
                	System.out.println("Uploaded file: "+item.getName());
                }
                
            }

            
        } catch (FileUploadBase.SizeLimitExceededException slee) {
            throw new FileUploadLimitExceededException(maxPostSize, slee.getActualSize());
        	
        	
        } catch (FileUploadException fue) {
            IOException ioe = new IOException("Could not parse and cache file upload data.");
            ioe.initCause(fue);
            throw ioe;
        }
		return "upload/noerrors";
   
    }
	
	@ExceptionHandler(RuntimeException.class)
	public ModelAndView handlerDocumentNotFoundException(RuntimeException ex){
		ModelAndView mav = new ModelAndView("error");
		mav.addObject("error", ex.getMessage());
		return mav;
	}
	
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/upload/upload.html", method = RequestMethod.POST)
	protected void save(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		System.out.println("upload.html");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usuLogin = auth.getName();
		String USER_DIRECTORY = BASE_USER_DIRECTORY+usuLogin+"/"; 
		
		
		System.out.println(USER_DIRECTORY);
		
		File diretorioUsuario = new File(USER_DIRECTORY);
		boolean diretorioCriado = false;
		
		if(!diretorioUsuario.exists()){
			diretorioCriado = diretorioUsuario.mkdir();
			
			if (!diretorioCriado)
				throw new RuntimeException("Não foi possível criar o diretório do usuário");
		}
		
		
		
		PrintWriter writer = null;
		InputStream is = null;
		FileOutputStream fos = null;
		
		
		try {
			writer = response.getWriter();
		} catch (IOException ex) {
			System.err.println(FileUploadController.class.getName() + "has thrown an exception: "	+ ex.getMessage());
		}

		String filename = request.getHeader("X-File-Name");
		try {
			is = request.getInputStream();
			fos = new FileOutputStream(new File(USER_DIRECTORY + filename));
			IOUtils.copy(is, fos);
			response.setStatus(response.SC_OK);
			writer.print("{success: true}");
		} catch (FileNotFoundException ex) {
			response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
			writer.print("{success: false}");
			System.err.println(FileUploadController.class.getName() + "has thrown an exception: "
					+ ex.getMessage());
		} catch (IOException ex) {
			response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
			writer.print("{success: false}");
			System.err.println(FileUploadController.class.getName() + "has thrown an exception: "
					+ ex.getMessage());
		} finally {
			try {
				fos.close();
				is.close();
			} catch (IOException ignored) {
			}
		}

		writer.flush();
		writer.close();
	}
	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/upload/check.html", method = RequestMethod.POST)
	protected void checkSign(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		System.out.println("check.html");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usuLogin = auth.getName();
		String USER_DIRECTORY = "/home/danillo/users/check/"+usuLogin+"/"; 
		
		System.out.println(USER_DIRECTORY);
		
		File diretorioUsuario = new File(USER_DIRECTORY);
		boolean diretorioCriado = false;
		
		if(!diretorioUsuario.exists()){
			diretorioCriado = diretorioUsuario.mkdirs();
			
			if (!diretorioCriado)
				throw new RuntimeException("Não foi possível criar o diretório do usuário");
		}
		
		PrintWriter writer = null;
		InputStream is = null;
		FileOutputStream fos = null;
		
		try {
			writer = response.getWriter();
		} catch (IOException ex) {
			System.err.println(FileUploadController.class.getName() + "has thrown an exception: "	+ ex.getMessage());
		}
		
		int idAnexo = Integer.parseInt(request.getHeader("X-File-Name"));

		try {
			is = request.getInputStream();
			fos = new FileOutputStream(new File(USER_DIRECTORY + idAnexo));
			IOUtils.copy(is, fos);
			
			File fileToCheck = new File(USER_DIRECTORY + idAnexo);
			boolean match = segurancaService.checkSignature(fileToCheck, idAnexo);
			fileToCheck.delete();
			response.setStatus(response.SC_OK);
			writer.print("{success: "+match+"}");
		} catch (FileNotFoundException ex) {
			response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
			writer.print("{success: false}");
			System.err.println(FileUploadController.class.getName() + "has thrown an exception: "
					+ ex.getMessage());
		} catch (IOException ex) {
			response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
			writer.print("{success: false}");
			System.err.println(FileUploadController.class.getName() + "has thrown an exception: "
					+ ex.getMessage());
		} finally {
			try {
				fos.close();
				is.close();
			} catch (IOException ignored) {
			}
		}

		writer.flush();
		writer.close();
	}
	
	
	
	
	 //public String processUpload(@RequestParam CommonsMultipartFile file,
	/*public String processUpload(UploadItem uploadItem, BindingResult result,
	   ModelMap modelMap, HttpServletRequest request) throws Exception {
	 
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error in uploading: " + error.getCode()
						+ " - " + error.getDefaultMessage());
			}
			return "upload/sucess";
		}
		
		CommonsMultipartFile file = uploadItem.getFileData();
		
		 //this.charset = request.getCharacterEncoding();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		File diretorioUsers = new File("/home/danillo/users/"+auth.getName());
		
		if (!diretorioUsers.exists())
			diretorioUsers.mkdirs();
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
		DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(diretorioUsers);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(MAX_MEMO);
				
		FileItem item1 = file.getFileItem();
		//upload.
		
		System.out.println(item1.getName());
		
		//this.session = request.getSession(true);
		//String usuLogin = (String)session.getAttribute("j_usuario_conta");
		
		
		
		System.out.println(diretorioUsers.list().length);
		
		//BASE_USER_DIRECTORY = "/home/danillo/users/"+auth.getName()+"/";
		
		
		//File userFile = new File(diretorioUsers,"testeUsuario.txt");  
		
		
		
		
		// System.out.println("isMultipart: "+isMultipart+auth.getName());
		 
		 
         

         List<FileItem> items;// = upload.parseRequest(request);
         Map<String, List<String>> params = new HashMap<String, List<String>>();
         
         try {
             items = upload.parseRequest(request);
             System.err.println(items.size());
             
             for (FileItem item : items) {
                 // If it's a form field, add the string value to the list
                 if (item.isFormField()) {
                     List<String> values = params.get(item.getFieldName());
                     if (values == null) {
                         values = new ArrayList<String>();
                         params.put(item.getFieldName(), values);
                     }
                     values.add(item.getString());
                 }
                 // Else store the file param
                 else {
                     //files.put(item.getFieldName(), item);
                 }
             }
             
         } catch (FileUploadException ex) {
             System.err.println(ex);
         }

         
		
		//CommonsMultipartFile cmpf = (CommonsMultipartFile) file;
		
		//uploadItem.setFileData(cmpf);
		
		File copy = new File(BASE_USER_DIRECTORY+auth.getName()+"/"+file.getOriginalFilename());
		
		file.transferTo(copy);
		
	 System.out.println("========= upload file:" + file.getOriginalFilename());
	                 //you can do something with the uploaded file
	  //UploadedFile uploaded = uploadService.save(file);
	 
	  //modelMap.addAttribute("file", uploaded);
	 
	 System.out.println(file.getOriginalFilename());

	   
		return "upload/upload";
	} */
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
	/*@RequestMapping(method = RequestMethod.POST)
	public String Uploadcreate(UploadItem uploadItem, BindingResult result, HttpServletRequest request) throws IOException {
		System.out.println("started uploading");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		CommonsMultipartFile multipartFile = (CommonsMultipartFile) multipartRequest.getFile("fileData");
		uploadItem.setFileData(multipartFile);
		
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				System.err.println("Error in uploading: " + error.getCode()
						+ " - " + error.getDefaultMessage());
			}
			return "upload/upload";
		}
		
		
		try {
			
			//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			//CommonsMultipartFile multipartFile = (CommonsMultipartFile) multipartRequest.getFile("fileData");
			
            MultipartFile file = (CommonsMultipartFile) request;//uploadItem.getFileData();
            String fileName = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            if (file.getSize() > 0) {
                    inputStream = file.getInputStream();
                    if (file.getSize() > 10000) {
                            System.out.println("File Size:::" + file.getSize());
                            return "upload/upload";
                    }
                    System.out.println("size::" + file.getSize());
                    fileName = file.getOriginalFilename();
                    outputStream = new FileOutputStream(destinationDir+fileName);
                    System.out.println("fileName:" + file.getOriginalFilename());

                    int readBytes = 0;
                    byte[] buffer = new byte[10000];
                    while ((readBytes = inputStream.read(buffer, 0, 10000)) != -1) {
                            outputStream.write(buffer, 0, readBytes);
                    }
                    outputStream.close();
                    inputStream.close();
            }

            // ..........................................
           // session.setAttribute("uploadFile", file.getOriginalFilename());
    } catch (Exception e) {
            e.printStackTrace();
    }



		
		
		
		
		
		
		
		
		

		// Some type of file processing...
		System.err.println("-------------------------------------------");

		//System.err.println("Test uploading file: "
		//		+ uploadItem.getFileData().getOriginalFilename());
		System.err.println("-------------------------------------------");

		return "upload/upload";
	}*/


}
