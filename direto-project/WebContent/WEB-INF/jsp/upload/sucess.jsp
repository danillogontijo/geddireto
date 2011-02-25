<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html xmlns="http://www.w3.org/1999/xhtml" >
    <head>		
    	<link href="${pageContext.request.contextPath}/js/client/fileuploader.css" rel="stylesheet" type="text/css">	
		
</head>
    <body>


	<div id="wrap">

            <h1>Choose (multiple) files or drag them onto drop zone below</h1>

            <form action="/direto-project/upload/upload.html" method="post" enctype="multipart/form-data">
            <div class="qq-upload-button" style="position: relative; overflow: hidden; direction: ltr;">
            	Selecione
            	    <input type="file" id="fileField" name="fileField" multiple style="position: absolute; right: 0pt; top: 0pt; font-family: Arial; font-size: 118px; margin: 0pt; padding: 0pt; cursor: pointer; opacity: 0;" />
            
            </div>
            
            
            
            
            </form>

            <div id="fileDrop" >
                <p>Solte os arquivos aqui</p>
            </div>

            <div id="files">
                <h2>Arquivos</h2>
                <a id="reset" href="#" title="Remove all files from list">Limpar lista</a>
                <ul id="fileList"></ul>
                <a id="upload" href="#" title="Upload all files in list">Enviar</a>
            </div>
        </div>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/FileAPI.js"></script>

  </body>
</html>