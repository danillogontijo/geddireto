<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ include file="include_taglibs.jsp" %>
<%@ include file="include_head.jsp" %>


<div style="width: 822px; text-align:left; background-color: #B8C9DD; float: left; line-height:30px; position: static; width: 822px; height:30px; vertical-align: middle;" class="menu2">
		
				
			<a href="#grupos" style="margin-left: 5px;" class="menu2" name="modal">Encaminhar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Responder</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Despachar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Anotar</a> |
			<a href="" style="margin-left: 5px;" class="menu2">Anexar</a> |
			
		
</div>

<style type="text/css">
.upload-button {
    display:block; /* or inline-block */
    width: 822px; 
    padding: 2px 0; 
    text-align:center;    
    background:#1e90ff; 
    border-bottom:1px solid #ddd;
    color:#fff;
}


#files {
   /* margin: 0 0 0 400px;*/
    width: 778px;
    padding: 20px 20px 40px 20px;
    border: solid 2px #ccc;
    background: #fefefe;
    min-height: 240px;
    position: relative;
}

#fileDrop,
#files {
    -moz-box-shadow: 0 0 20px #ccc;
}

#fileDrop p {
	padding-top: 7px; 
	text-align: center; 
	font-size: medium;
	height: 100px;
	line-height: 50px;
}

#fileList {
    list-style: none;
    padding: 0;
    margin: 0;
}

    #fileList li {
        margin: 0;
        padding: 10px 0;
        margin: 0;
        overflow: auto;
        border-bottom: solid 1px #ccc;
        position: relative;
    }

        #fileList li img {
            width: 120px;
            border: solid 1px #999;
            padding: 6px;
            margin: 0 10px 0 0;
            background-color: #eee;
            display: block;
            float: left;
        }
        
        #fileList li p {
		    margin: 0;
		    padding: 0;
		    text-align: right;
		    float: left;
		}

#reset {
    top: 10px;
    right: 10px;
    color: #ccc;
    text-decoration: none;
    position: absolute;
}

#reset:hover {
    color: #333;
}

#files #upload {
    color: #fff;
    position: absolute;
    display: block;
    bottom: 10px;
    right: 10px;
    width: auto;
    background-color: #777;
    padding: 4px 6px;
    text-decoration: none;
    font-weight: bold;
    -moz-border-radius: 6px;
    
}

#files #upload:hover {
    background-color: #333;
}

.loader {
    position: absolute;
    bottom: 10px;
    right: 0;
    color: orange;
}

.loadingIndicator {
    width: 0%;
    height: 2px;
    background-color: orange;
    position: absolute;
    bottom: 0;
    left: 0;
}

.imagePreview {
    width: 300px;
    padding: 10px;
    border: solid 1px #ccc;
    position: absolute;
    background-color: white;
}

    .imagePreview img {
        max-width: 100%;
        display: block;
        margin: 0 auto;
    }
    
    
h1 {
    font-size: 1.6em;
    margin: 30px 0;
    padding: 0;
}

h2 {
    font-size: 1.4em;
    padding: 0 0 6px 0;
    margin: 0;
    border-bottom: solid 1px #ccc;
}

h3 {
    font-size: 1.2em;
    margin: 0 0 10px 0;
    padding: 0;
    text-align: left;
    width: 770px; 
    float: left
}

form {
    padding: 0 0 30px 0;
}

.file_list_font {
 font: 12px "trebuchet MS", arial, sans-serif;
 color: #777;
}

.remove {
	
	/*margin-left: 690px;
	position: relative;*/
	float: right; 
	width: 10px
	
	}

</style>

<script type="text/javascript">

function validateDocumentInputField(element)
{

	documentoValidatorJS.getMessageValidator('DocumentoForm',element.id, element.value, {
		callback:function(dataFromServer) {
			/*if (dataFromServer == "")
				usuarioJS.listActivedContas(element.value,montaContas);*/
			setInputFieldStatus(element.id, dataFromServer);
  		}
  	});
 	  	
}

function setInputFieldStatus(elementId, message)
{
	document.getElementById("error").innerHTML = message;
}

</script>

<div style="width:100%; text-align:center; float: left; position: static; width: 822px; vertical-align: middle;">
<!--<form action="/direto-project/upload/upload.html" method="post" enctype="multipart/form-data">-->

<span id="error" style="color: red;"></span>

<form:form modelAttribute="documentoForm" method="post" enctype="multipart/form-data">

Nr. documento:
<spring:bind path="documentoForm.nrDocumento">
	<form:input path="nrDocumento" id="${status.expression}" 
		value="${status.value}"	onBlur="validateDocumentInputField(this);"/>
</spring:bind>

<br>

Tipo Documento:
<spring:bind path="documentoForm.tipoDocumento">  
	<form:input path="tipoDocumento" id="${status.expression}" 
		value="${status.value}"	onBlur="validateDocumentInputField(this);"/>		
</spring:bind>




<div id="wrap" style="width: 100%; padding-top: 10px;">

<div class="upload-button" style="position: relative; overflow: hidden; direction: ltr;">
     	Clique aqui para adicionar os documentos
        <input type="file" id="fileField" name="fileField" multiple style="position: absolute; right: 0pt; top: 0pt; font-family: Arial; font-size: 118px; margin: 0pt; padding: 0pt; cursor: pointer; opacity: 0;" />
    </div>

    <div id="files" class="file_list_font">
       <h2>Arquivos</h2>
        <a id="reset" href="#" title="Remove todos os arquivos da lista">Limpar lista</a>
       <ul id="fileList"></ul>
       <a id="upload" href="#" title="Upload all files in list">Enviar</a>
     </div>
     
     <div id="fileDrop" style="height: 80px;">
    	<p style="padding-top: 7px; text-align: center; font-size: medium;">Solte os arquivos aqui</p>
    </div>
     
     
</div>

</form:form>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/FileAPI.js"></script>




</div>



		

	
<%@ include file="include_foot.jsp" %>