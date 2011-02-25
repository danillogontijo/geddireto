<!doctype html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
    <head>
        <title>Uploadify test</title>
        <link href="${pageContext.request.contextPath}/j/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
  
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadify/jquery-1.4.2.min.js"></script>
 
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadify/swfobject.js"></script>
 
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
 
        
        
 <style type="text/css"> 
    #custom-queue {
  border: 1px solid #E5E5E5;
  
margin-bottom: 10px;
  width: 570px;
}

.uploadifyQueueItem {
  background-color: #FFFFFF;
  border: none;
  border-bottom: 1px solid #E5E5E5;
  font: 10px Verdana, Geneva, sans-serif;
  height: 20px;
  margin-top: 0;
  padding: 0px;
  width: 570px;
}

.uploadifyError {
  background-color: #FDE5DD !important;
  border: none !important;
  border-bottom: 1px solid #FBCBBC !important;
}
.uploadifyQueueItem .cancel {
  float: right;
}
.uploadifyQueue .completed {
  color: #C5C5C5;
}
.uploadifyProgress {
  background-color: #E5E5E5;
  margin-top: 10px;
  width: 100%;
}
.uploadifyProgressBar {
  background-color: red;
  height: 3px;
  width: 1px;
}


        
        </style>
        
      
        
        
        
        
        
       <script type="text/javascript">
            $(document).ready(function() {
                $('#uploadify').uploadify({
                    'uploader': '${pageContext.request.contextPath}/js/uploadify/uploadify.swf',
                    'script': '${pageContext.request.contextPath}/upload/upload.html;jsessionid=${pageContext.request.session.id}',
                    //'folder': '/home/danillo/',
                    'cancelImg': '${pageContext.request.contextPath}/js/uploadify/cancel.png',
                    //'scriptData'     :  'sessionId: ${pageContext.request.session.id}',  
                    'queueID'        : 'custom-queue',
                    'fileDataName' : 'file',
                    'sizeLimit': 524288000, //50Mb
                  // 'simUploadLimit': 1,
                  'buttonText'  : 'Selecione',
                    //'auto'           : true, 
                    'multi'          : true, 
                    'removeCompleted': false, 
                    'onError'     : function (event,ID,fileObj,errorObj) {
                        $('#status-message').text(errorObj.type + ' Error: ' + errorObj.info);
                     },
                     'onSelectOnce'   : function(event,data) {
                         $('#status-message').text(data.filesSelected + ' arquivos foram adicionados com sucesso.');
                     }
                                    
                     
                });
                $('#upload').click(function() {
                    $('#uploadify').uploadifyUpload();
                    return false;
                });
            });
        </script>
        
        
    </head>
    <body>
    <div id="status-message"></div>
    <div id="custom-queue" class="uploadifyQueue"></div>
    <a id="upload" href="#">Upload</a>
     
       <input name="file" type="file" id="uploadify"/>   
        
       
          
       
    </body>
</html>