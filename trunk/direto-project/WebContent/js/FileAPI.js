var qq = qq || {};


qq.attach = function(element, type, fn){
    if (element.addEventListener){
        element.addEventListener(type, fn, false);
    } else if (element.attachEvent){
        element.attachEvent('on' + type, fn);
    }
};
qq.detach = function(element, type, fn){
    if (element.removeEventListener){
        element.removeEventListener(type, fn, false);
    } else if (element.attachEvent){
        element.detachEvent('on' + type, fn);
    }
};

qq.preventDefault = function(e){
    if (e.preventDefault){
        e.preventDefault();
    } else{
        e.returnValue = false;
    }
};



qq.contains = function(parent, descendant){       
    // compareposition returns false in this case
    if (parent == descendant) return true;
    
    if (parent.contains){
        return parent.contains(descendant);
    } else {
        return !!(descendant.compareDocumentPosition(parent) & 8);
    }
};


qq.getItemByFileId = function(id){
	 var el = document.getElementById('fileList');
     var item = el.firstChild;
     
     while (item){            
         if (item.fileId == id) return item;            
         item = item.nextSibling;
     }   
          
};
qq.hasClass = function(element, name){
    var re = new RegExp('(^| )' + name + '( |$)');
    return re.test(element.className);
};
qq.addClass = function(element, name){
    if (!qq.hasClass(element, name)){
        element.className += ' ' + name;
    }
};
qq.removeClass = function(element, name){
    var re = new RegExp('(^| )' + name + '( |$)');
    element.className = element.className.replace(re, ' ').replace(/^\s+|\s+$/g, "");
};


function FileAPI (t, d, f) {

    var fileList = t,
        fileField = f,
        dropZone = d,
        fileQueue = new Array()
        preview = null;
    
    var dropDisabled = false;
    var size = 0;
   
    this.init = function () {
        fileField.onchange = this.addFiles;
        /*dropZone.addEventListener("dragenter",  this.stopProp, false);
        dropZone.addEventListener("dragleave",  this.dragExit, false);
        //dropZone.addEventListener("dragover",  this.disableDropOutside, false);
        dropZone.addEventListener("dragover",  this.dragOver, false);
        dropZone.addEventListener("drop",  this.showDroppedFiles, false);
        //document.addEventListener("dragover",  this.disableDrop, false);
        //this.disableDropOutside();*/
        
        this.addEvents(dropZone);
        
        dropZone.style.display = 'none';

        qq.attach(document, 'dragenter', function(e){     
            dropZone.style.display = 'block'; 
        });      
        
        qq.attach(document, 'dragleave', function(e){
            
            var relatedTarget = document.elementFromPoint(e.clientX, e.clientY);
            // only fire when leaving document out
            if ( ! relatedTarget || relatedTarget.nodeName == "HTML"){               
                dropZone.style.display = 'none';                                            
            }
        });  
        
    }
    
    this.addEvents = function(ele){
    	
    	//var self = this;              
        
        qq.attach(ele, 'dragover', function(e){
        	e.stopPropagation();
            e.preventDefault();
        	/*ele.style["backgroundColor"] = "#ffffff";
            ele.style["borderColor"] = "#3DD13F";
            //ele.style["color"] = "#1D5987";
            ele.*/
            
            qq.addClass(ele,"fileDropOver");
        });
        
        qq.attach(ele, 'dragenter', function(e){
            //ele.style["backgroundColor"] = "#000";
            //ele.style["borderColor"] = "#3DD13F";
            ele.style["color"] = "red";
            e.stopPropagation();
            e.preventDefault();
            
        });
        
        qq.attach(ele, 'dragleave', function(e){
                  
            var relatedTarget = document.elementFromPoint(e.clientX, e.clientY);                      
            // do not fire when moving a mouse over a descendant
            if (qq.contains(this, relatedTarget)) return;
           
            /*ele.style["backgroundColor"] = "#FF7256";
            ele.style["borderColor"] = "#3DD13F";
            ele.style["color"] = "#1D5987";*/
            //ele.style["color"] = "red"
            qq.removeClass(ele,'fileDropOver');
            ele.style["color"] = "#FFF";
        });
                
        qq.attach(ele, 'drop', function(e){
            
            e.stopPropagation();
            e.preventDefault();
            var files = e.dataTransfer.files;
            addFileListItems(files);
            
            ele.style.display = 'none';
            
            /*ele.style["backgroundColor"] = "#FF7256";
            ele.style["borderColor"] = "#3DD13F";
            ele.style["color"] = "#3DD13F";*/
             
        });   
    	
    }
    
    this.addFiles = function () {
        addFileListItems(this.files);
    }

    this.showDroppedFiles = function (ev) {
        ev.stopPropagation();
        ev.preventDefault();
        var files = ev.dataTransfer.files;
        addFileListItems(files);
    }

    this.clearList = function (ev) {
        ev.preventDefault();
        while (fileList.childNodes.length > 0) {
            fileList.removeChild(
                fileList.childNodes[fileList.childNodes.length - 1]
            );
            size--;
        }
    }
    
    this.removeItem = function (ev) {
    	ev.stopPropagation();
    	ev.preventDefault();
        var self = this;
        var id = self.id;
        var list = document.getElementById('fileList');
        var itemToRemove = qq.getItemByFileId(id);
        
        list.removeChild(itemToRemove);
        fileQueue.splice((id-1), 1);
        
        size--;
                
    }

    /*this.dragOver = function (ev) {
        ev.stopPropagation();
        ev.preventDefault();
        this.style["backgroundColor"] = "#F0FCF0";
        this.style["borderColor"] = "#3DD13F";
        this.style["color"] = "#3DD13F";
       // dropZone.style.display = 'none';
        //dropZone.style.visibility= 'visible';
        
    }

    this.dragExit = function (ev) {
        ev.stopPropagation();
        ev.preventDefault();
        dropZone.style["backgroundColor"] = "#FEFEFE";
        dropZone.style["borderColor"] = "#CCC";
        dropZone.style["color"] = "#CCC";
        
        dropZone.style.display = 'none';
        dropDisabled = true;
        
        dropZone.removeEventListener("dragover",  this.dragOver, false);
        document.addEventListener("dragover",  this.disableDrop, false);
        	
        var relatedTarget = document.elementFromPoint(ev.clientX, ev.clientY);
        // only fire when leaving document out
        
        if ( ! relatedTarget || relatedTarget.nodeName == "HTML"){               
        	dropZone.style.display = 'none';
        	//dropZone.innerHTML(relatedTarget);
        }
        	
    }

    this.stopProp = function (ev) {
        ev.stopPropagation();
        ev.preventDefault();
        //dropZone.style.display = 'block'; 
    }*/

    this.uploadQueue = function (ev) {
    	//alert('u');
        ev.preventDefault();
        var erro = false;
        var count = fileQueue.length;
        
        while (fileQueue.length > 0) {
            var item = fileQueue.pop();
            var p = document.createElement("p");
            p.className = "loader";
            var pText = document.createTextNode("Enviando...");
            p.appendChild(pText);
            item.li.appendChild(p);
            if (item.file.size < 10485760) {
                uploadFile(item.file, item.li, count);
                count--;
            } else {
                p.textContent = "Tamanho excedido";
                p.style["color"] = "red";
                erro = true;
            }
        }
        if (!erro){
        	//setTimeout("document.getElementById('documentoForm').submit()",1000);
        	alert('enviado');
        }
    }
    
   
    var addFileListItems = function (files) {
    	size++;
        for (var i = 0; i < files.length; i++) {
            var fr = new FileReader();
            fr.file = files[i];
            fr.onloadend = showFileInList;
            fr.readAsDataURL(files[i]);
        }
    }

    var showFileInList = function (ev) {
        var file = ev.target.file;
        if (file) {
            var li = document.createElement("li");
            li.fileId = size;
            if (file.type.search(/image\/.*/) != -1) {
                var thumb = new Image();
                thumb.src = ev.target.result;
                thumb.addEventListener("mouseover", showImagePreview, false);
                thumb.addEventListener("mouseout", removePreview, false);
                li.appendChild(thumb);
            }
            var h3 = document.createElement("h3");
            var h3Text = (size == 1) ? document.createTextNode(file.name + ' - Documento principal') : document.createTextNode(file.name + ' - Anexo');
            var close = document.createElement("a");
            close.setAttribute('href','#');
            close.setAttribute('class','remove');
            close.setAttribute('id',size);
            close.appendChild(document.createTextNode('X'));
            
            close.onclick = FileAPI.removeItem;
            
            h3.appendChild(h3Text);
            h3.appendChild(close);
            
            li.appendChild(h3)
            var p = document.createElement("p");
            var pText = document.createTextNode(
                "Tipo: ("
                + file.type + ") - " +
                Math.round(file.size / 1024) + "KB"
            );
            p.appendChild(pText);
            li.appendChild(p);
            var divLoader = document.createElement("div");
            divLoader.className = "loadingIndicator";
            li.appendChild(divLoader);
            fileList.appendChild(li);
            fileQueue.push({
                file : file,
                li : li
            });
        }
       
    }

    var showImagePreview = function (ev) {
        var div = document.createElement("div");
        div.style["top"] = (ev.pageY + 10) + "px";
        div.style["left"] = (ev.pageX + 10) + "px";
        div.style["opacity"] = 0;
        div.className = "imagePreview";
        var img = new Image();
        img.src = ev.target.src;
        div.appendChild(img);
        document.body.appendChild(div);
        document.body.addEventListener("mousemove", movePreview, false);
        preview = div;
        fadePreviewIn();
    }

    var movePreview = function (ev) {
        if (preview) {
            preview.style["top"] = (ev.pageY + 10) + "px";
            preview.style["left"] = (ev.pageX + 10) + "px";
        }
    }

    var removePreview = function (ev) {
        document.body.removeEventListener("mousemove", movePreview, false);
        document.body.removeChild(preview);
    }

    var fadePreviewIn = function () {
        if (preview) {
            var opacity = preview.style["opacity"];
            for (var i = 10; i < 250; i = i+10) {
                (function () {
                    var level = i;
                    setTimeout(function () {
                        preview.style["opacity"] = opacity + level / 250;
                    }, level);
                })();
            }
        }
    }

    var uploadFile = function (file, li, count) {
        if (li && file) {
            var xhr = new XMLHttpRequest(),
                upload = xhr.upload;
            upload.addEventListener("progress", function (ev) {
                if (ev.lengthComputable) {
                    var loader = li.getElementsByTagName("div")[0];
                    loader.style["width"] = (ev.loaded / ev.total) * 100 + "%";
                }
            }, false);
            upload.addEventListener("load", function (ev) {
                var ps = li.getElementsByTagName("p");
                var div = li.getElementsByTagName("div")[0];
                div.style["width"] = "100%";
                div.style["backgroundColor"] = "#0f0";
                for (var i = 0; i < ps.length; i++) {
                    if (ps[i].className == "loader") {
                        ps[i].textContent = "Enviado";
                        ps[i].style["color"] = "#3DD13F";
                        break;
                    }
                }
            }, false);
            upload.addEventListener("error", function (ev) {console.log(ev);}, false);
            xhr.open(
                "POST",
                "/direto-project/upload/upload.html"
            );
            xhr.setRequestHeader("Cache-Control", "no-cache");
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            xhr.setRequestHeader("X-File-Name", count+file.name);
            xhr.send(file);
        }
    }
    
}

window.onload = function () {
    if (typeof FileReader == "undefined") alert ("Sorry your browser does not support the File API and this demo will not work for you");
    FileAPI = new FileAPI(
        document.getElementById("fileList"),
        document.getElementById("fileDrop"),
        document.getElementById("fileField")
    );
    FileAPI.init();
    var reset = document.getElementById("reset");
    reset.onclick = FileAPI.clearList;
   /* var upload = document.getElementById("upload");
    upload.onclick = FileAPI.uploadQueue;*/
}